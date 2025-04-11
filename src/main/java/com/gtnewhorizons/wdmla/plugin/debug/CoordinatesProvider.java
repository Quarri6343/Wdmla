package com.gtnewhorizons.wdmla.plugin.debug;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;

import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.accessor.BlockAccessor;
import com.gtnewhorizons.wdmla.api.accessor.EntityAccessor;
import com.gtnewhorizons.wdmla.api.provider.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.provider.IEntityComponentProvider;
import com.gtnewhorizons.wdmla.api.provider.IToggleableProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.util.FormatUtil;

import mcp.mobius.waila.api.SpecialChars;

public class CoordinatesProvider implements IToggleableProvider {

    public static ForBlock getBlock() {
        return ForBlock.INSTANCE;
    }

    public static ForEntity getEntity() {
        return ForEntity.INSTANCE;
    }

    public static class ForBlock extends CoordinatesProvider implements IBlockComponentProvider {

        private static final ForBlock INSTANCE = new ForBlock();

        @Override
        public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
            if (!accessor.showDetails()) {
                tooltip.child(
                        ThemeHelper.INSTANCE.value(
                                StatCollector.translateToLocal("hud.msg.wdmla.seeing.block"),
                                StatCollector.translateToLocalFormatted(
                                        "hud.msg.wdmla.coordinates",
                                        SpecialChars.RED
                                                + FormatUtil.STANDARD_NO_GROUP.format(accessor.getHitResult().blockX)
                                                + SpecialChars.RESET,
                                        SpecialChars.GREEN
                                                + FormatUtil.STANDARD_NO_GROUP.format(accessor.getHitResult().blockY)
                                                + SpecialChars.RESET,
                                        SpecialChars.BLUE
                                                + FormatUtil.STANDARD_NO_GROUP.format(accessor.getHitResult().blockZ)
                                                + SpecialChars.RESET)));
            } else {
                Vec3 hitVec = accessor.getHitResult().hitVec;
                tooltip.child(
                        ThemeHelper.INSTANCE.value(
                                StatCollector.translateToLocal("hud.msg.wdmla.seeing"),
                                StatCollector.translateToLocalFormatted(
                                        "hud.msg.wdmla.coordinates",
                                        SpecialChars.RED + FormatUtil.STANDARD_NO_GROUP.format(hitVec.xCoord)
                                                + SpecialChars.RESET,
                                        SpecialChars.GREEN + FormatUtil.STANDARD_NO_GROUP.format(hitVec.yCoord)
                                                + SpecialChars.RESET,
                                        SpecialChars.BLUE + FormatUtil.STANDARD_NO_GROUP.format(hitVec.zCoord)
                                                + SpecialChars.RESET)));
            }
        }
    }

    public static class ForEntity extends CoordinatesProvider implements IEntityComponentProvider {

        private static final ForEntity INSTANCE = new ForEntity();

        @Override
        public void appendTooltip(ITooltip tooltip, EntityAccessor accessor) {
            if (!accessor.showDetails()) {
                tooltip.child(
                        ThemeHelper.INSTANCE.value(
                                StatCollector.translateToLocal("hud.msg.wdmla.seeing.entity"),
                                String.format(
                                        "%s:%s:%s",
                                        SpecialChars.RED
                                                + FormatUtil.STANDARD_NO_GROUP.format(accessor.getEntity().posX)
                                                + SpecialChars.RESET,
                                        SpecialChars.GREEN
                                                + FormatUtil.STANDARD_NO_GROUP.format(accessor.getEntity().posY)
                                                + SpecialChars.RESET,
                                        SpecialChars.BLUE
                                                + FormatUtil.STANDARD_NO_GROUP.format(accessor.getEntity().posZ)
                                                + SpecialChars.RESET)));
            } else {
                Vec3 hitVec = accessor.getHitResult().hitVec;
                tooltip.child(
                        ThemeHelper.INSTANCE.value(
                                StatCollector.translateToLocal("hud.msg.wdmla.seeing"),
                                String.format(
                                        "%s:%s:%s",
                                        SpecialChars.RED + FormatUtil.STANDARD_NO_GROUP.format(hitVec.xCoord)
                                                + SpecialChars.RESET,
                                        SpecialChars.GREEN + FormatUtil.STANDARD_NO_GROUP.format(hitVec.yCoord)
                                                + SpecialChars.RESET,
                                        SpecialChars.BLUE + FormatUtil.STANDARD_NO_GROUP.format(hitVec.zCoord)
                                                + SpecialChars.RESET)));
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return Identifiers.COORDINATES;
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.TAIL;
    }

    @Override
    public boolean enabledByDefault() {
        return false;
    }
}
