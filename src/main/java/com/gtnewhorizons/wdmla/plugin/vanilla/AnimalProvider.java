package com.gtnewhorizons.wdmla.plugin.vanilla;

import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import com.gtnewhorizons.wdmla.api.accessor.EntityAccessor;
import com.gtnewhorizons.wdmla.api.provider.IEntityComponentProvider;
import com.gtnewhorizons.wdmla.api.provider.ITimeFormatConfigurable;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.config.PluginsConfig;
import com.gtnewhorizons.wdmla.config.WDMlaConfig;
import com.gtnewhorizons.wdmla.impl.format.TimeFormattingPattern;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;

public enum AnimalProvider implements IEntityComponentProvider, ITimeFormatConfigurable {

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
            TimeFormattingPattern timePattern = WDMlaConfig.instance().getTimeFormatter(this);
            tooltip.child(
                    ThemeHelper.INSTANCE.value(
                            StatCollector.translateToLocal("hud.msg.wdmla.animal.growth"),
                            timePattern.tickFormatter.apply(absTimeToGrow)));
        }
    }

    private void breedCooldownTooltip(ITooltip tooltip, EntityAccessor accessor) {
        if (!PluginsConfig.vanilla.animal.showBreedCooldown) {
            return;
        }

        if (accessor.getEntity() instanceof EntityAnimal animal && !animal.isChild() && animal.getGrowingAge() != 0) {
            int absTimeBreedCooldown = Math.abs(animal.getGrowingAge());
            TimeFormattingPattern timePattern = WDMlaConfig.instance().getTimeFormatter(this);
            tooltip.child(
                    ThemeHelper.INSTANCE.value(
                            StatCollector.translateToLocal("hud.msg.wdmla.animal.breedcooldown"),
                            timePattern.tickFormatter.apply(absTimeBreedCooldown)));
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
