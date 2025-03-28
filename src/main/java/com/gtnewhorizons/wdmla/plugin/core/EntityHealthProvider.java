package com.gtnewhorizons.wdmla.plugin.core;

import static mcp.mobius.waila.api.SpecialChars.GRAY;
import static mcp.mobius.waila.api.SpecialChars.WHITE;

import com.gtnewhorizons.wdmla.plugin.PluginsConfig;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.utils.Constants;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IEntityComponentProvider;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.HealthComponent;
import net.minecraftforge.common.config.Configuration;

public enum EntityHealthProvider implements IEntityComponentProvider {
    INSTANCE;

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.HEAD;
    }

    @Override
    public ResourceLocation getUid() {
        return Identifiers.ENTITY_HEALTH;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor) {
        if (!(accessor.getEntity() instanceof EntityLivingBase livingEntity)) {
            return;
        }

        float health = livingEntity.getHealth() / 2.0f;
        float maxhp = livingEntity.getMaxHealth() / 2.0f;

        int maxHPForText = ConfigHandler.instance().getConfig(Configuration.CATEGORY_GENERAL, Constants.CFG_WAILA_MAXHP, 40);
        if (livingEntity.getMaxHealth() > maxHPForText) tooltip.text(
                String.format(
                        "HP : " + WHITE + "%.0f" + GRAY + " / " + WHITE + "%.0f",
                        livingEntity.getHealth(),
                        livingEntity.getMaxHealth()));
        else {
            tooltip.child(new HealthComponent(health, maxhp));
        }
    }
}
