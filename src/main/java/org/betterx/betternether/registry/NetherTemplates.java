package org.betterx.betternether.registry;

import org.betterx.betternether.BetterNether;
import org.betterx.wover.item.api.smithing.SmithingTemplates;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.List;

import net.neoforged.neoforge.registries.RegisterEvent;
import net.minecraft.core.registries.Registries;

public class NetherTemplates {
    public static final Identifier EMPTY_SLOT_BOWL = BetterNether.C.id("container/slot/bowl");
    public static final Identifier EMPTY_SLOT_BLOCK = BetterNether.C.id("container/slot/block");

    public static SmithingTemplateItem NETHER_BOWL_SMITHING_TEMPLATE;

    public static SmithingTemplateItem FLAMING_RUBY_TEMPLATE;

    public static SmithingTemplateItem CINCINNASITE_DIAMOND_TEMPLATE;

    public static void register(RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registries.ITEM)) return;

        event.register(Registries.ITEM, helper -> {
            var registry = NetherItems.getItemRegistry();

            NETHER_BOWL_SMITHING_TEMPLATE = registry.registerSmithingTemplateItem(
                    "bowl_upgrade",
                    List.of(EMPTY_SLOT_BOWL),
                    List.of(SmithingTemplates.EMPTY_SLOT_INGOT)
            );
            helper.register(BetterNether.C.mk("bowl_upgrade_smithing_template"), NETHER_BOWL_SMITHING_TEMPLATE);

            FLAMING_RUBY_TEMPLATE = registry.registerSmithingTemplateItem(
                    "flaming_ruby_upgrade",
                    SmithingTemplates.ARMOR_AND_TOOLS,
                    List.of(EMPTY_SLOT_BLOCK)
            );
            helper.register(BetterNether.C.mk("flaming_ruby_upgrade_smithing_template"), FLAMING_RUBY_TEMPLATE);

            CINCINNASITE_DIAMOND_TEMPLATE = registry.registerSmithingTemplateItem(
                    "cincinnasite_diamond_upgrade",
                    List.of(SmithingTemplates.EMPTY_SLOT_DIAMOND),
                    List.of(SmithingTemplates.EMPTY_SLOT_INGOT)
            );
            helper.register(
                    BetterNether.C.mk("cincinnasite_diamond_upgrade_smithing_template"),
                    CINCINNASITE_DIAMOND_TEMPLATE
            );
        });
    }
}
