package com.gtnewhorizons.wdmla.addon.core;

import static mcp.mobius.waila.api.SpecialChars.GRAY;
import static mcp.mobius.waila.api.SpecialChars.WHITE;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IEntityComponentProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.HealthComponent;

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
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
        if (!(accessor.getEntity() instanceof EntityLivingBase livingEntity)) {
            return;
        }

        float health = livingEntity.getHealth() / 2.0f;
        float maxhp = livingEntity.getMaxHealth() / 2.0f;

        int maxHPForText = config.getInteger(Identifiers.CONFIG_MAX_ENTITY_HEALTH_FOR_TEXT);
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
