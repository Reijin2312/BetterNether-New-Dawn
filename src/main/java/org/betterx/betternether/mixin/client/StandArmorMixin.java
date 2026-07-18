package org.betterx.betternether.mixin.client;

import org.betterx.betternether.config.Configs;

import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.object.armorstand.ArmorStandArmorModel;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.ArmorStandRenderState;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ArmorStandRenderer.class, remap = false)
public abstract class StandArmorMixin extends LivingEntityRenderer<ArmorStand, ArmorStandRenderState, ArmorStandArmorModel> {
    protected StandArmorMixin(EntityRendererProvider.Context context, ArmorStandArmorModel model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    @Inject(method = "<init>*", at = @At("RETURN"))
    private void onInit(EntityRendererProvider.Context ctx, CallbackInfo info) {
        if (!Configs.CLIENT.thinArmor.get()) {
            return;
        }

        for (int i = 0; i < this.layers.size(); i++) {
            RenderLayer<ArmorStandRenderState, ArmorStandArmorModel> feature = this.layers.get(i);
            if (feature instanceof HumanoidArmorLayer<?, ?, ?>) {
                this.layers.remove(i);
                break;
            }
        }

        this.layers.add(
                0,
                new HumanoidArmorLayer<>(
                        this,
                        ArmorModelSet.bake(ModelLayers.PLAYER_SLIM_ARMOR, ctx.getModelSet(), ArmorStandArmorModel::new),
                        ctx.getEquipmentRenderer()
                )
        );
    }
}
