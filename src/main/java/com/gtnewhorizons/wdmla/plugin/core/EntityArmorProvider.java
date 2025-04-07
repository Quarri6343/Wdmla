package com.gtnewhorizons.wdmla.plugin.core;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IEntityComponentProvider;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.ArmorComponent;

public enum EntityArmorProvider implements IEntityComponentProvider {

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor) {
        float armorValue = ((EntityLiving) accessor.getEntity()).getTotalArmorValue() / 2.0f;
        if (armorValue > 0) {
            if (accessor.getEntity() instanceof EntityPlayer) {
                tooltip.child(new ArmorComponent(armorValue, 10));
            } else {
                // in vanilla Minecraft, armor point past than 20 has no effect
                // TODO:support https://github.com/GTNewHorizons/OverloadedArmorBar
                tooltip.child(new ArmorComponent(armorValue, Math.min(armorValue, 10)));
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return Identifiers.ARMOR;
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.HEAD;
    }
}
