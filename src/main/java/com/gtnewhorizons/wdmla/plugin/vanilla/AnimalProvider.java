package com.gtnewhorizons.wdmla.plugin.vanilla;

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

public enum AnimalProvider implements IEntityComponentProvider {

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
            int absTimeToGrowInSeconds = Math.abs(animal.getGrowingAge() / 20);
            tooltip.child(
                    ThemeHelper.INSTANCE.value(
                            StatCollector.translateToLocal("hud.msg.wdmla.animal.growth"),
                            absTimeToGrowInSeconds + StringUtils.EMPTY + StatCollector.translateToLocal("hud.msg.wdmla.seconds")));
        }
    }

    private void breedCooldownTooltip(ITooltip tooltip, EntityAccessor accessor) {
        if (!PluginsConfig.vanilla.animal.showBreedCooldown) {
            return;
        }

        if (accessor.getEntity() instanceof EntityAnimal animal && !animal.isChild() && animal.getGrowingAge() != 0) {
            int absTimeBreedCooldownInSeconds = Math.abs(animal.getGrowingAge() / 20);
            tooltip.child(
                    ThemeHelper.INSTANCE.value(
                            StatCollector.translateToLocal("hud.msg.wdmla.animal.breedcooldown"),
                            absTimeBreedCooldownInSeconds + StringUtils.EMPTY
                                    + StatCollector.translateToLocal("hud.msg.wdmla.seconds")));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.ANIMAL;
    }
}
