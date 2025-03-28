package com.gtnewhorizons.wdmla.addon.core;

import static mcp.mobius.waila.api.SpecialChars.*;

import com.gtnewhorizons.wdmla.addon.AddonsConfig;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IEntityComponentProvider;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.TextComponent;

import mcp.mobius.waila.utils.ModIdentification;

public enum DefaultEntityInfoProvider implements IEntityComponentProvider {
    INSTANCE;

    @Override
    public ResourceLocation getUid() {
        return Identifiers.DEFAULT_ENTITY;
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.DEFAULT_INFO;
    }

    @Override
    public boolean isPriorityFixed() {
        return true;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor) {
        if (AddonsConfig.core.defaultEntity.showEntityName) {
            tooltip.child(
                    new TextComponent(WHITE + accessor.getEntity().getCommandSenderName())
                            .tag(Identifiers.ENTITY_NAME));
        }
        if (AddonsConfig.core.defaultEntity.showModName) {
            tooltip.child(
                    new TextComponent(BLUE + ITALIC + ModIdentification.nameFromEntity(accessor.getEntity()))
                            .tag(Identifiers.MOD_NAME));
        }
    }
}
