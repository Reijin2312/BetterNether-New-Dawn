package org.betterx.betternether.particles;

import org.betterx.betternether.registry.NetherParticles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

class BNDripHangParticle extends DripParticle {
    private final ParticleOptions fallingParticle;

    public BNDripHangParticle(
            ClientLevel clientLevel,
            double d,
            double e,
            double f,
            Fluid fluid,
            ParticleOptions particleOptions,
            TextureAtlasSprite sprite
    ) {
        super(clientLevel, d, e, f, fluid, sprite);
        this.fallingParticle = particleOptions;
        this.gravity *= 0.02F;
        this.lifetime = 40;
    }

    public void setup(boolean isGlowing, float gravityFactor, int lifetime) {
        this.isGlowing = isGlowing;
        this.gravity *= gravityFactor;
        this.lifetime = lifetime;
    }

    @Override
    protected void preMoveUpdate() {
        if (this.lifetime-- <= 0) {
            this.remove();
            this.level.addParticle(this.fallingParticle, this.x, this.y, this.z, this.xd, this.yd, this.zd);
        }
    }

    @Override
    protected void postMoveUpdate() {
        this.xd *= 0.02;
        this.yd *= 0.02;
        this.zd *= 0.02;
    }
}

class BNFallAndLandParticle extends DripParticle {
    private final ParticleOptions landParticle;

    public BNFallAndLandParticle(
            ClientLevel clientLevel,
            double d,
            double e,
            double f,
            Fluid fluid,
            ParticleOptions particleOptions,
            TextureAtlasSprite sprite
    ) {
        super(clientLevel, d, e, f, fluid, sprite);
        this.landParticle = particleOptions;
        this.lifetime = (int) (64.0 / (Math.random() * 0.8 + 0.2));
    }

    public void setup(boolean isGlowing, float gravity) {
        this.isGlowing = isGlowing;
        this.gravity = gravity;
    }

    @Override
    protected void postMoveUpdate() {
        if (this.onGround) {
            this.remove();
            this.level.addParticle(this.landParticle, this.x, this.y, this.z, 0.0, 0.0, 0.0);
        }
    }
}

class BNDripLandParticle extends DripParticle {
    public BNDripLandParticle(
            ClientLevel clientLevel,
            double d,
            double e,
            double f,
            Fluid fluid,
            TextureAtlasSprite sprite
    ) {
        super(clientLevel, d, e, f, fluid, sprite);
        this.lifetime = (int) (16.0 / (Math.random() * 0.8 + 0.2));
    }

    public void setup(boolean isGlowing, int lifetime) {
        this.isGlowing = isGlowing;
        this.lifetime = lifetime;
    }
}

public class BNParticleProvider<S extends ParticleType<SimpleParticleType>> {
    private static final float BLUE_DRIP_R = 0x12 / 255.0f;
    private static final float BLUE_DRIP_G = 0x5a / 255.0f;
    private static final float BLUE_DRIP_B = 0xf9 / 255.0f;

    public static class ObsidianTearLandProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public ObsidianTearLandProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(
                SimpleParticleType simpleParticleType,
                ClientLevel clientLevel,
                double d,
                double e,
                double f,
                double g,
                double h,
                double i,
                RandomSource randomSource
        ) {
            BNDripLandParticle dripParticle = new BNDripLandParticle(
                    clientLevel,
                    d,
                    e,
                    f,
                    Fluids.EMPTY,
                    this.sprite.get(randomSource)
            );
            dripParticle.setup(true, (int) (28.0D / (Math.random() * 0.8D + 0.2D)));
            dripParticle.setColor(BLUE_DRIP_R, BLUE_DRIP_G, BLUE_DRIP_B);
            return dripParticle;
        }
    }

    public static class ObsidianTearFallProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public ObsidianTearFallProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(
                SimpleParticleType simpleParticleType,
                ClientLevel clientLevel,
                double d,
                double e,
                double f,
                double g,
                double h,
                double i,
                RandomSource randomSource
        ) {
            BNFallAndLandParticle dripParticle = new BNFallAndLandParticle(
                    clientLevel,
                    d,
                    e,
                    f,
                    Fluids.EMPTY,
                    NetherParticles.BLUE_LANDING_OBSIDIAN_TEAR,
                    this.sprite.get(randomSource)
            );
            dripParticle.setup(true, 0.01F);
            dripParticle.setColor(BLUE_DRIP_R, BLUE_DRIP_G, BLUE_DRIP_B);
            return dripParticle;
        }
    }

    public static class ObsidianTearHangProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public ObsidianTearHangProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(
                SimpleParticleType simpleParticleType,
                ClientLevel clientLevel,
                double d,
                double e,
                double f,
                double g,
                double h,
                double i,
                RandomSource randomSource
        ) {
            BNDripHangParticle dripHangParticle = new BNDripHangParticle(
                    clientLevel,
                    d,
                    e,
                    f,
                    Fluids.EMPTY,
                    NetherParticles.BLUE_FALLING_OBSIDIAN_TEAR,
                    this.sprite.get(randomSource)
            );
            dripHangParticle.setup(true, 0.01F, 100);
            dripHangParticle.setColor(BLUE_DRIP_R, BLUE_DRIP_G, BLUE_DRIP_B);
            return dripHangParticle;
        }
    }

    public static class ObsidianWeepLandProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public ObsidianWeepLandProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(
                SimpleParticleType simpleParticleType,
                ClientLevel clientLevel,
                double d,
                double e,
                double f,
                double g,
                double h,
                double i,
                RandomSource randomSource
        ) {
            BNDripLandParticle dripParticle = new BNDripLandParticle(
                    clientLevel,
                    d,
                    e,
                    f,
                    Fluids.EMPTY,
                    this.sprite.get(randomSource)
            );
            dripParticle.setup(true, (int) (Math.random() * 10) + 1);
            dripParticle.setColor(BLUE_DRIP_R, BLUE_DRIP_G, BLUE_DRIP_B);
            return dripParticle;
        }
    }

    public static class ObsidianWeepFallProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public ObsidianWeepFallProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(
                SimpleParticleType simpleParticleType,
                ClientLevel clientLevel,
                double d,
                double e,
                double f,
                double g,
                double h,
                double i,
                RandomSource randomSource
        ) {
            BNFallAndLandParticle dripParticle = new BNFallAndLandParticle(
                    clientLevel,
                    d,
                    e,
                    f,
                    Fluids.EMPTY,
                    NetherParticles.BLUE_LANDING_OBSIDIAN_WEEP,
                    this.sprite.get(randomSource)
            );
            dripParticle.setup(true, 0.01F);
            dripParticle.setColor(BLUE_DRIP_R, BLUE_DRIP_G, BLUE_DRIP_B);
            return dripParticle;
        }
    }

    public static class ObsidianWeepHangProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public ObsidianWeepHangProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(
                SimpleParticleType simpleParticleType,
                ClientLevel clientLevel,
                double d,
                double e,
                double f,
                double g,
                double h,
                double i,
                RandomSource randomSource
        ) {
            BNDripHangParticle dripHangParticle = new BNDripHangParticle(
                    clientLevel,
                    d,
                    e,
                    f,
                    Fluids.EMPTY,
                    NetherParticles.BLUE_FALLING_OBSIDIAN_WEEP,
                    this.sprite.get(randomSource)
            );
            dripHangParticle.setup(true, 0.01F, 5 + (int) (Math.random() * 10));
            dripHangParticle.setColor(BLUE_DRIP_R, BLUE_DRIP_G, BLUE_DRIP_B);
            return dripHangParticle;
        }
    }

    private static final float DRIP_R = 0.51171875F;
    private static final float DRIP_G = 0.03125F;
    private static final float DRIP_B = 0.890625F;

    public static class ObsidianVanillaWeepLandProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public ObsidianVanillaWeepLandProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(
                SimpleParticleType simpleParticleType,
                ClientLevel clientLevel,
                double d,
                double e,
                double f,
                double g,
                double h,
                double i,
                RandomSource randomSource
        ) {
            BNDripLandParticle dripParticle = new BNDripLandParticle(
                    clientLevel,
                    d,
                    e,
                    f,
                    Fluids.EMPTY,
                    this.sprite.get(randomSource)
            );
            dripParticle.setup(true, (int) (Math.random() * 10) + 1);
            dripParticle.setColor(DRIP_R, DRIP_G, DRIP_B);
            return dripParticle;
        }
    }

    public static class ObsidianVanillaWeepFallProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public ObsidianVanillaWeepFallProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(
                SimpleParticleType simpleParticleType,
                ClientLevel clientLevel,
                double d,
                double e,
                double f,
                double g,
                double h,
                double i,
                RandomSource randomSource
        ) {
            BNFallAndLandParticle dripParticle = new BNFallAndLandParticle(
                    clientLevel,
                    d,
                    e,
                    f,
                    Fluids.EMPTY,
                    NetherParticles.LANDING_OBSIDIAN_WEEP,
                    this.sprite.get(randomSource)
            );
            dripParticle.setup(true, 0.01F);
            dripParticle.setColor(DRIP_R, DRIP_G, DRIP_B);
            return dripParticle;
        }
    }

    public static class ObsidianVanillaWeepHangProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;

        public ObsidianVanillaWeepHangProvider(SpriteSet spriteSet) {
            this.sprite = spriteSet;
        }

        @Override
        public Particle createParticle(
                SimpleParticleType simpleParticleType,
                ClientLevel clientLevel,
                double d,
                double e,
                double f,
                double g,
                double h,
                double i,
                RandomSource randomSource
        ) {
            BNDripHangParticle dripHangParticle = new BNDripHangParticle(
                    clientLevel,
                    d,
                    e,
                    f,
                    Fluids.EMPTY,
                    NetherParticles.FALLING_OBSIDIAN_WEEP,
                    this.sprite.get(randomSource)
            );
            dripHangParticle.setup(true, 0.01F, 5 + (int) (Math.random() * 10));
            dripHangParticle.setColor(DRIP_R, DRIP_G, DRIP_B);
            return dripHangParticle;
        }
    }
}
