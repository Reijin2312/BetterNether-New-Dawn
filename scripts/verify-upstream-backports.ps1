param()

$ErrorActionPreference = 'Stop'
$workspace = Split-Path -Parent (Split-Path -Parent $PSScriptRoot)
$repos = @(
    'BetterNether', 'BetterNether-Fabric-1.21.1',
    'BetterNether-1.21.11', 'BetterNether-Fabric-1.21.11',
    'BetterNether-26.1', 'BetterNether-Fabric-26.1',
    'BetterNether-26.2', 'BetterNether-Fabric-26.2',
    'BetterNether-26.3', 'BetterNether-Fabric-26.3'
)
$failures = [System.Collections.Generic.List[string]]::new()
$checks = 0

function Assert-Check([bool]$condition, [string]$repo, [string]$name) {
    $script:checks++
    if (-not $condition) { $script:failures.Add("$repo`: $name") }
}

function Read-Relative([string]$root, [string]$path) {
    $full = Join-Path $root $path
    Assert-Check (Test-Path -LiteralPath $full) (Split-Path $root -Leaf) "missing $path"
    if (Test-Path -LiteralPath $full) { return Get-Content -LiteralPath $full -Raw }
    return ''
}

function Assert-Match([string]$text, [string]$pattern, [string]$repo, [string]$name) {
    Assert-Check ([regex]::IsMatch($text, $pattern, [Text.RegularExpressions.RegexOptions]::Singleline)) $repo $name
}

function Assert-NoMatch([string]$text, [string]$pattern, [string]$repo, [string]$name) {
    Assert-Check (-not [regex]::IsMatch($text, $pattern, [Text.RegularExpressions.RegexOptions]::Singleline)) $repo $name
}

function Assert-Json([string]$root, [string]$path, [string]$repo) {
    $text = Read-Relative $root $path
    if (-not $text) { return '' }
    try {
        $null = $text | ConvertFrom-Json
        Assert-Check $true $repo "valid JSON: $path"
    } catch {
        Assert-Check $false $repo "invalid JSON: $path ($($_.Exception.Message))"
    }
    return $text
}

$lootFiles = @(
    'bone_cincinnasite_door', 'bone_reed_door', 'gloomwood_transition_planks',
    'hook_mushroom', 'moss_cover', 'soul_lily_sapling', 'whispering_gourd_lantern'
)
$vineLoot = @('black_vine', 'blooming_vine', 'gloomsculk_vine')
$tagFiles = @(
    'src/main/generated/data/minecraft/tags/block/buttons.json',
    'src/main/generated/data/minecraft/tags/block/logs.json',
    'src/main/generated/data/minecraft/tags/block/flower_pots.json',
    'src/main/generated/data/minecraft/tags/block/washed_away_by_fluids.json',
    'src/main/generated/data/minecraft/tags/item/buttons.json'
)

foreach ($repo in $repos) {
    Write-Host "[BetterNether backports] $repo"
    $root = Join-Path $workspace $repo
    Assert-Check (Test-Path -LiteralPath $root) $repo 'repository is missing'
    if (-not (Test-Path -LiteralPath $root)) { continue }

    $subjects = git -C $root log --format=%s -16
    Assert-Check ($subjects -contains 'Backport upstream gameplay and data fixes') $repo 'upstream gameplay/data commit is missing'
    Assert-Check ($subjects -contains 'Fix nether city generation conflicts') $repo 'Nether City conflict commit is missing'

    foreach ($loot in $lootFiles) {
        $lootText = Assert-Json $root "src/main/generated/data/betternether/loot_table/blocks/$loot.json" $repo
        Assert-Match $lootText 'betternether:' $repo "$loot has a BetterNether drop"
    }
    foreach ($vine in $vineLoot) {
        $lootText = Assert-Json $root "src/main/generated/data/betternether/loot_table/blocks/$vine.json" $repo
        Assert-Match $lootText ('"name"\s*:\s*"betternether:' + $vine + '"') $repo "$vine drops itself"
        Assert-Match $lootText '"rolls"\s*:\s*1(?:\.0)?' $repo "$vine loot rolls exactly once"
    }
    foreach ($tag in $tagFiles) {
        $null = Assert-Json $root $tag $repo
    }

    $cityAvoids = Assert-Json $root 'src/main/resources/data/betternether/tags/worldgen/structure/city_avoids.json' $repo
    Assert-Match $cityAvoids 'minecraft:fortress' $repo 'cities yield to vanilla fortresses'
    Assert-Match $cityAvoids 'minecraft:bastion_remnant' $repo 'cities yield to vanilla bastions'

    $forestLitter = Assert-Json $root 'src/main/generated/data/betternether/worldgen/placed_feature/forest_litter.json' $repo
    Assert-Match $forestLitter 'betternether:avoid_nether_city' $repo 'forest litter avoids Nether Cities'

    $allJava = (Get-ChildItem -LiteralPath (Join-Path $root 'src/main/java') -Recurse -File -Filter '*.java' |
        ForEach-Object { Get-Content -LiteralPath $_.FullName -Raw }) -join "`n"
    Assert-Match $allJava 'FLUID_SPRINGS' $repo 'Nether City generation stage follows solid structures'

    $city = Read-Relative $root 'src/main/java/org/betterx/betternether/world/structures/city/CityStructure.java'
    Assert-Match $city 'NearbyBastions' $repo 'city checks nearby bastions'
    Assert-Match $city 'StructureAvoidingProcessor' $repo 'city buildings preserve conflicting structure pieces'
    Assert-Match $city 'buildingsWithin' $repo 'city exposes its building footprint to features'

    $avoid = Read-Relative $root 'src/main/java/org/betterx/betternether/world/structures/city/StructureAvoidingProcessor.java'
    Assert-Match $avoid 'CITY_AVOIDS' $repo 'structure processor uses configurable avoided-structure tag'
    Assert-Match $avoid '(getStructureWithPieceAt|startsForStructure)' $repo 'structure processor tests real structure pieces'

    $anchor = Read-Relative $root 'src/main/java/org/betterx/betternether/world/features/AnchorTreeFeature.java'
    Assert-Match $anchor 'buildingsWithin' $repo 'large trees avoid city buildings'
    $features = Read-Relative $root 'src/main/java/org/betterx/betternether/registry/NetherFeatures.java'
    Assert-Match $features 'AVOID_NETHER_CITY' $repo 'city-avoidance placement modifier is registered'

    $firefly = Read-Relative $root 'src/main/java/org/betterx/betternether/entity/EntityFirefly.java'
    Assert-Match $firefly 'ColorBlue' $repo 'legacy firefly blue component is read'
    Assert-Match $firefly 'ColorUtil(?:ARGB32)?\.color\(\(int\) \(r \* 0xFF\), \(int\) \(g \* 0xFF\), \(int\) \(b \* 0xFF\)\)' $repo 'legacy firefly blue component is packed from b'
    Assert-NoMatch $firefly 'ColorUtil(?:ARGB32)?\.color\(\(int\) \(r \* 0xFF\), \(int\) \(g \* 0xFF\), \(int\) \(g \* 0xFF\)\)' $repo 'legacy firefly blue=green regression'
    Assert-NoMatch $firefly '\(byte\)\s*\([^\r\n]+\*\s*255F' $repo 'signed-byte firefly color regression'

    foreach ($sapling in @(
        'BlockCommonSapling', 'BlockGiantMoldSapling', 'BlockGloomwoodSapling',
        'BlockJellyfishMushroomSapling', 'BlockMushroomFirSapling', 'BlockRubeusSapling', 'BlockWartSeed'
    )) {
        $saplingText = Read-Relative $root "src/main/java/org/betterx/betternether/blocks/$sapling.java"
        Assert-Match $saplingText 'isBonemealSuccess' $repo "$sapling has vanilla-like bonemeal probability"
    }

    $eyeVine = Read-Relative $root 'src/main/java/org/betterx/betternether/blocks/BlockEyeVine.java'
    if ($repo -match '26\.3') {
        Assert-Match $eyeVine 'updateShape' $repo 'Eye Vine uses the 26.3 neighbour-update hook'
    } else {
        Assert-NoMatch $eyeVine '(updateShape|updateState)\s*\(' $repo 'Eye Vine must inherit the final base neighbour-update hook'
    }

    foreach ($survivalBlock in @('BlockMold', 'BlockMushroomFir', 'BlockPottedPlant', 'BlockRubeusCone')) {
        $survival = Read-Relative $root "src/main/java/org/betterx/betternether/blocks/$survivalBlock.java"
        Assert-Match $survival 'canSurvive' $repo "$survivalBlock validates its supporting block"
        Assert-Match $survival '(scheduleTick|destroyBlock|Blocks\.AIR)' $repo "$survivalBlock removes itself after losing support"
    }

    $statue = Read-Relative $root 'src/main/java/org/betterx/betternether/blocks/BlockStatueRespawner.java'
    Assert-Match $statue 'setRespawnPosition' $repo 'Nether statue sets player respawn'

    $rubyFire = Read-Relative $root 'src/main/java/org/betterx/betternether/enchantments/RubyFire.java'
    Assert-Match $rubyFire 'rubyFire\s*==\s*null' $repo 'Ruby Fire tolerates a missing enchantment holder'

    $rubeusLeaves = Read-Relative $root 'src/main/java/org/betterx/betternether/blocks/BlockRubeusLeaves.java'
    Assert-Match $rubeusLeaves 'extends\s+(BaseLeavesBlock|TintedParticleLeavesBlock|LeavesBlock)' $repo 'Rubeus leaves use vanilla decay behavior'
    $rubeusTree = Read-Relative $root 'src/main/java/org/betterx/betternether/world/features/RubeusTreeFeature.java'
    Assert-Match $rubeusTree 'updateDistances' $repo 'Rubeus crowns calculate vanilla leaf distances'
    Assert-Match $rubeusTree '(LeavesBlock|BlockStateProperties)\.DISTANCE' $repo 'Rubeus leaf distance values are written'

    foreach ($modelName in @('feather_fern_1', 'feather_fern_2')) {
        $model = Assert-Json $root "src/main/resources/assets/betternether/models/block/$modelName.json" $repo
        Assert-Match $model '"parent"\s*:\s*"betternether:block/feather_fern"' $repo "$modelName uses the correct model parent"
        Assert-Match $model '"particle"' $repo "$modelName defines a particle texture"
    }

    $is1211 = $repo -in @('BetterNether', 'BetterNether-Fabric-1.21.1')
    if (-not $is1211) {
        $mixins = Read-Relative $root 'src/main/resources/betternether.mixins.common.json'
        Assert-Match $mixins 'CopperGolemAiMixin' $repo 'Copper Golem compatibility mixin is enabled'
        $armor = Read-Relative $root 'src/main/java/org/betterx/betternether/mixin/client/PlayerArmorMixin.java'
        Assert-Match $armor '(SLIM|slim)' $repo 'slim-player armor model fix'
    }
}

if ($failures.Count) {
    Write-Host "`nFAILED: $($failures.Count) of $checks BetterNether backport checks" -ForegroundColor Red
    $failures | ForEach-Object { Write-Host " - $_" -ForegroundColor Red }
    exit 1
}
Write-Host "PASSED: $checks BetterNether upstream-backport checks across $($repos.Count) projects." -ForegroundColor Green
