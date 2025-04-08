package com.gtnewhorizons.wdmla.plugin.vanilla;

import net.minecraft.entity.item.EntityPainting;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IEntityComponentProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;

public enum PaintingProvider implements IEntityComponentProvider {

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor) {
        if (accessor.getEntity() instanceof EntityPainting painting) {
            tooltip.text(painting.art.title); // Hope nobody wants translation of this...
        }
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.PAINTING;
    }
}
