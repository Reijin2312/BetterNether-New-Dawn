package org.betterx.betternether.integrations.jade;

import org.betterx.betternether.BetterNether;
import org.betterx.betternether.blocks.BlockCommonPlant;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.JadeIds;
import snownee.jade.api.config.IPluginConfig;

public enum NetherPlantProvider implements IBlockComponentProvider {
    INSTANCE;

    public static final ResourceLocation ID = BetterNether.C.mk("plant_growth");

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (config.get(JadeIds.MC_CROP_PROGRESS)) {
            // Jade already renders crop progress for AGE_* plants, avoid duplicate line.
            return;
        }

        if (accessor.getBlock() instanceof BlockCommonPlant crop) {
            float growthValue = accessor.getBlockState().getValue(crop.getAgeProperty()) / (float) crop.getMaxAge();
            addMaturityTooltip(tooltip, growthValue);
        }
    }

    private static void addMaturityTooltip(ITooltip tooltip, float growthValue) {
        growthValue *= 100.0F;

        if (growthValue < 100.0F) {
            String percentString = String.format("%.0f%%", growthValue);
            tooltip.add(Component.translatable("tooltip.jade.crop_growth", percentString));
        } else {
            tooltip.add(Component.translatable("tooltip.jade.crop_mature"));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ID;
    }
}
