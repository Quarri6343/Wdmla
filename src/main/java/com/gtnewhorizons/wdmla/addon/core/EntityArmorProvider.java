package com.gtnewhorizons.wdmla.addon.core;

import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IEntityComponentProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.ArmorComponent;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public enum EntityArmorProvider implements IEntityComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
        float armorValue = ((EntityLiving) accessor.getEntity()).getTotalArmorValue() / 2.0f;
        if (armorValue > 0) {
            if(accessor.getEntity() instanceof EntityPlayer) {
                tooltip.child(new ArmorComponent(armorValue, 10));
            }
            else {
                tooltip.child(new ArmorComponent(armorValue));
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
