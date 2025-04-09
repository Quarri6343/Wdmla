package com.gtnewhorizons.wdmla.plugin.vanilla;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import com.gtnewhorizons.wdmla.api.accessor.EntityAccessor;
import com.gtnewhorizons.wdmla.api.provider.IEntityComponentProvider;
import com.gtnewhorizons.wdmla.api.provider.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.provider.ITimeFormatConfigurable;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.config.WDMlaConfig;
import com.gtnewhorizons.wdmla.impl.format.TimeFormattingPattern;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;

public enum ChickenProvider
        implements IEntityComponentProvider, IServerDataProvider<EntityAccessor>, ITimeFormatConfigurable {

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor) {
        if (accessor.getServerData().hasKey("nextEgg")) {
            TimeFormattingPattern timePattern = WDMlaConfig.instance().getTimeFormatter(this);
            tooltip.child(
                    ThemeHelper.INSTANCE.value(
                            StatCollector.translateToLocal("hud.msg.wdmla.nextegg"),
                            timePattern.tickFormatter.apply(accessor.getServerData().getInteger("nextEgg"))));
        }
    }

    @Override
    public void appendServerData(NBTTagCompound data, EntityAccessor accessor) {
        if (accessor.getEntity() instanceof EntityChicken chicken) {
            data.setInteger("nextEgg", chicken.timeUntilNextEgg);
        }
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.CHICKEN;
    }

    @Override
    public TimeFormattingPattern getDefaultTimeFormatter() {
        return TimeFormattingPattern.HOUR_MIN_SEC;
    }
}
