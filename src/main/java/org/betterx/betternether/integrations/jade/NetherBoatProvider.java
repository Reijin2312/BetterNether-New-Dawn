package org.betterx.betternether.integrations.jade;

import org.betterx.bclib.items.boat.BoatTypeOverride;
import org.betterx.bclib.items.boat.CustomBoatTypeOverride;
import org.betterx.betternether.BetterNether;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import net.neoforged.fml.ModList;
import snownee.jade.api.Accessor;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.JadeIds;
import snownee.jade.api.ui.BoxElement;

public final class NetherBoatProvider {
    private NetherBoatProvider() {
    }

    public static String itemModName(ItemStack stack) {
        if (stack.getItem() instanceof CustomBoatTypeOverride customBoat && isBetterNetherType(customBoat.bcl_getCustomType())) {
            return getModName();
        }

        return null;
    }

    public static void replaceEntityModName(BoxElement tooltip, Accessor<?> accessor) {
        if (accessor instanceof EntityAccessor entityAccessor &&
                entityAccessor.getEntity() instanceof CustomBoatTypeOverride customBoat &&
                isBetterNetherType(customBoat.bcl_getCustomType())) {
            tooltip.getTooltip().replace(
                    JadeIds.CORE_MOD_NAME,
                    Component.literal(getModName()).withStyle(ChatFormatting.BLUE, ChatFormatting.ITALIC)
            );
        }
    }

    private static boolean isBetterNetherType(BoatTypeOverride type) {
        return type != null && BetterNether.MOD_ID.equals(type.id.getNamespace());
    }

    private static String getModName() {
        return ModList.get()
                      .getModContainerById(BetterNether.MOD_ID)
                      .map(container -> container.getModInfo().getDisplayName())
                      .orElse("BetterNether");
    }
}
