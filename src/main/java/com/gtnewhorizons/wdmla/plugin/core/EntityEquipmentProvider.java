package com.gtnewhorizons.wdmla.plugin.core;

import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IEntityComponentProvider;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.ItemComponent;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public enum EntityEquipmentProvider implements IEntityComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor) {
        if(!accessor.showDetails()) {
            return;
        }

        ITooltip itemRow = tooltip.horizontal();
        for (int i = 0; i < 5; i++) {
            ItemStack equipment = ((EntityLiving) accessor.getEntity()).getEquipmentInSlot(i);
            if (equipment != null) {
               itemRow.child(new ItemComponent(equipment).doDrawOverlay(false));
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return Identifiers.EQUIPMENT;
    }
}
