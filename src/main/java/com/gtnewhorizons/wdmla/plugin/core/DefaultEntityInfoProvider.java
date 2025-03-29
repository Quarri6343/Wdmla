package com.gtnewhorizons.wdmla.plugin.core;

import static mcp.mobius.waila.api.SpecialChars.*;

import com.gtnewhorizons.wdmla.impl.ui.component.EntityComponent;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Padding;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Size;
import com.gtnewhorizons.wdmla.plugin.PluginsConfig;
import net.minecraft.entity.EntityLiving;
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
        ITooltip row = tooltip.horizontal();
        if(PluginsConfig.core.defaultEntity.showEntity && accessor.getEntity() instanceof EntityLiving living) {
            row.child(new EntityComponent(living).padding(new Padding(6,0,10, 0)).size(new Size(12, 12)));
        }

        ITooltip row_vertical = row.vertical();
        if (PluginsConfig.core.defaultEntity.showEntityName) {
            row_vertical.child(
                    new TextComponent(WHITE + accessor.getEntity().getCommandSenderName())
                            .tag(Identifiers.ENTITY_NAME));
        }
        if (PluginsConfig.core.defaultEntity.showModName) {
            row_vertical.child(
                    new TextComponent(BLUE + ITALIC + ModIdentification.nameFromEntity(accessor.getEntity()))
                            .tag(Identifiers.MOD_NAME));
        }
    }
}
