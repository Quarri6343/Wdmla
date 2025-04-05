package com.gtnewhorizons.wdmla.plugin.vanilla;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.ResourceLocation;

import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.StringUtils;

import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IEntityComponentProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;

import mcp.mobius.waila.cbcore.LangUtil;

public enum HorseProvider implements IEntityComponentProvider {

    INSTANCE;

    // https://minecraft.fandom.com/wiki/Horse?so=search#Movement_speed
    public static final double SPEED_UNIT_TO_BLOCKS_PER_SECOND_RATE = 42.16;

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor) {
        if (accessor.getEntity() instanceof EntityHorse horse) {
            double jumpStrength = horse.getHorseJumpStrength();
            tooltip.child(
                    ThemeHelper.INSTANCE.value(
                            StatCollector.translateToLocal("hud.msg.wdmla.jumpStrength"),
                            String.format("%.2f", getJumpHeight(jumpStrength)) + StringUtils.SPACE
                                    + StatCollector.translateToLocal("hud.msg.wdmla.blocks")));
            if (accessor.showDetails()) {
                tooltip.text("(" + String.format("%.2f", jumpStrength) + ")");
            }

            double movementSpeed = horse.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
            tooltip.child(
                    ThemeHelper.INSTANCE.value(
                            StatCollector.translateToLocal("hud.msg.wdmla.speed"),
                            String.format("%.2f", movementSpeed * SPEED_UNIT_TO_BLOCKS_PER_SECOND_RATE)
                                    + StringUtils.SPACE
                                    + StatCollector.translateToLocal("hud.msg.wdmla.blockspersecond")));
            if (accessor.showDetails()) {
                tooltip.text("(" + String.format("%.2f", movementSpeed) + ")");
            }
        }
    }

    // copied from Jade
    private static double getJumpHeight(double jumpStrength) {
        return -0.1817584952 * jumpStrength * jumpStrength * jumpStrength + 3.689713992 * jumpStrength * jumpStrength
                + 2.128599134 * jumpStrength
                - 0.343930367;
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.HORSE;
    }
}
