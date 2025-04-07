package com.gtnewhorizons.wdmla.plugin.vanilla;

import com.gtnewhorizons.wdmla.api.format.ITimeFormatterAccessor;
import com.gtnewhorizons.wdmla.impl.format.TimeFormattingPattern;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.ResourceLocation;

import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.StringUtils;

import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IEntityComponentProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.plugin.PluginsConfig;

import mcp.mobius.waila.cbcore.LangUtil;

public enum AnimalProvider implements IEntityComponentProvider, ITimeFormatterAccessor {

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor) {
        ageTooltip(tooltip, accessor);
        breedCooldownTooltip(tooltip, accessor);
    }

    private void ageTooltip(ITooltip tooltip, EntityAccessor accessor) {
        if (!PluginsConfig.vanilla.animal.showAnimalGrowth) {
            return;
        }

        if (accessor.getEntity() instanceof EntityAnimal animal && animal.isChild() && animal.getGrowingAge() != 0) {
            int absTimeToGrow = Math.abs(animal.getGrowingAge());
            tooltip.child(
                    ThemeHelper.INSTANCE.value(
                            StatCollector.translateToLocal("hud.msg.wdmla.animal.growth"),
                            getDefaultTimeFormatter().tickFormatter.apply(absTimeToGrow)));
        }
    }

    private void breedCooldownTooltip(ITooltip tooltip, EntityAccessor accessor) {
        if (!PluginsConfig.vanilla.animal.showBreedCooldown) {
            return;
        }

        if (accessor.getEntity() instanceof EntityAnimal animal && !animal.isChild() && animal.getGrowingAge() != 0) {
            int absTimeBreedCooldown = Math.abs(animal.getGrowingAge());
            tooltip.child(
                    ThemeHelper.INSTANCE.value(
                            StatCollector.translateToLocal("hud.msg.wdmla.animal.breedcooldown"),
                            getDefaultTimeFormatter().tickFormatter.apply(absTimeBreedCooldown)));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.ANIMAL;
    }

    @Override
    public TimeFormattingPattern getDefaultTimeFormatter() {
        return TimeFormattingPattern.HOUR_MIN_SEC;
    }
}
