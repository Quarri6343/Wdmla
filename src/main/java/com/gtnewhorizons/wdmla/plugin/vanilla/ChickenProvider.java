package com.gtnewhorizons.wdmla.plugin.vanilla;

import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IEntityComponentProvider;
import com.gtnewhorizons.wdmla.api.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public enum ChickenProvider implements IEntityComponentProvider, IServerDataProvider<EntityAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor) {
        if (accessor.getServerData().hasKey("nextEgg")) {
            tooltip.child(ThemeHelper.INSTANCE.value(
                    StatCollector.translateToLocal("hud.msg.wdmla.nextegg"),
                    accessor.getServerData().getInteger("nextEgg") / 20 + StatCollector.translateToLocal("hud.msg.wdmla.second")));
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
}
