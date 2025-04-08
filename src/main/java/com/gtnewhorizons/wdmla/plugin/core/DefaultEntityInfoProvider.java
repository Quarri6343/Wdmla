package com.gtnewhorizons.wdmla.plugin.core;

import static mcp.mobius.waila.api.SpecialChars.*;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IEntityComponentProvider;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.api.ui.MessageType;
import com.gtnewhorizons.wdmla.config.General;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.impl.ui.component.EntityComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.HPanelComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.TextComponent;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Padding;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Size;
import com.gtnewhorizons.wdmla.impl.ui.style.TextStyle;
import com.gtnewhorizons.wdmla.plugin.PluginsConfig;

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
        if (PluginsConfig.core.defaultEntity.showEntity) {
            if (accessor.getEntity() instanceof EntityLiving living) {
                row.child(
                        new EntityComponent(living).padding(new Padding(6, 0, 10, 0)).size(new Size(12, 12))
                                .tag(Identifiers.ENTITY));
            } else {
                row.child(new HPanelComponent().tag(Identifiers.ENTITY));
            }
        }

        ITooltip row_vertical = row.vertical();
        if (PluginsConfig.core.defaultEntity.showEntityName) {
            String name = accessor.getEntity().getCommandSenderName();
            if (accessor.getEntity() instanceof EntityLiving living && living.hasCustomNameTag()
                    && General.customNameOverride) {
                name = ITALIC + living.getCustomNameTag();
            }
            row_vertical.child(ThemeHelper.INSTANCE.title(name).tag(Identifiers.ENTITY_NAME));
        }
        if (PluginsConfig.core.defaultEntity.showModName) {
            row_vertical.child(
                    new TextComponent(ITALIC + ModIdentification.nameFromEntity(accessor.getEntity()))
                            .style(new TextStyle().color(General.currentTheme.get().textColor(MessageType.MOD_NAME)))
                            .tag(Identifiers.MOD_NAME));
        }
    }
}
