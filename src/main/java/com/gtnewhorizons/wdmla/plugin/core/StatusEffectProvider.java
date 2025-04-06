package com.gtnewhorizons.wdmla.plugin.core;

import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IEntityComponentProvider;
import com.gtnewhorizons.wdmla.api.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.StringUtils;

//TODO: render status icon
public enum StatusEffectProvider implements IEntityComponentProvider, IServerDataProvider<EntityAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor) {
        if(!accessor.getServerData().hasKey("ActiveEffects")) {
            return;
        }

        NBTTagList activeEffects = accessor.getServerData().getTagList("ActiveEffects", 10);
        for (int i = 0; i < activeEffects.tagCount(); i++) {
            PotionEffect effect = PotionEffect.readCustomPotionEffectFromNBT(activeEffects.getCompoundTagAt(i));
            if(effect != null) {
                String amplifier;
                if(effect.getAmplifier() >= 0 && effect.getAmplifier() <= 9) {
                    amplifier = StatCollector.translateToLocal("enchantment.level." + (effect.getAmplifier() + 1));
                }
                else {
                    amplifier = String.valueOf(effect.getAmplifier() + 1);
                }
                String effectName = StatCollector.translateToLocal(effect.getEffectName()) + StringUtils.SPACE + amplifier;
                String duration;
                if (effect.getIsPotionDurationMax()) {
                    duration = StatCollector.translateToLocal("hud.msg.wdmla.infinity.icon");
                } else {
                    duration = net.minecraft.util.StringUtils.ticksToElapsedTime(effect.getDuration());
                }

                String builtLine = String.format(StatCollector.translateToLocal("hud.msg.wdmla.effect.format"), effectName, duration);
                if(Potion.potionTypes[effect.getPotionID()].isBadEffect()) {
                    tooltip.child(ThemeHelper.INSTANCE.danger(builtLine));
                }
                else {
                    tooltip.child(ThemeHelper.INSTANCE.success(builtLine));
                }
            }
        }
    }

    @Override
    public void appendServerData(NBTTagCompound data, EntityAccessor accessor) {
        accessor.getEntity().writeToNBT(data);
    }

    @Override
    public ResourceLocation getUid() {
        return Identifiers.STATUS_EFFECT;
    }
}
