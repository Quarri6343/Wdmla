package com.gtnewhorizons.wdmla.plugin.vanilla;

import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IEntityComponentProvider;
import com.gtnewhorizons.wdmla.api.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.format.ITimeFormatConfigurable;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.config.WDMlaConfig;
import com.gtnewhorizons.wdmla.impl.format.TimeFormattingPattern;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public enum ZombieVillagerProvider implements IEntityComponentProvider, IServerDataProvider<EntityAccessor>, ITimeFormatConfigurable {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor) {
        if(accessor.getServerData().hasKey("ConversionTime")) {
            int conversionTime = accessor.getServerData().getInteger("ConversionTime");
            if(conversionTime != -1) {
                TimeFormattingPattern timePattern = WDMlaConfig.instance().getTimeFormatter(this);
                tooltip.child(ThemeHelper.INSTANCE.value(
                        StatCollector.translateToLocal("hud.msg.wdmla.conversion.time"),
                        timePattern.tickFormatter.apply(conversionTime)
                ));
            }
        }
    }

    @Override
    public void appendServerData(NBTTagCompound data, EntityAccessor accessor) {
        accessor.getEntity().writeToNBT(data);
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.ZOMBIE_VILLAGER;
    }

    @Override
    public TimeFormattingPattern getDefaultTimeFormatter() {
        return TimeFormattingPattern.HOUR_MIN_SEC;
    }
}
