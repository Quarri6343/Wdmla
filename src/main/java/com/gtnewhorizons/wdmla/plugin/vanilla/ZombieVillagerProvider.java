package com.gtnewhorizons.wdmla.plugin.vanilla;

import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IEntityComponentProvider;
import com.gtnewhorizons.wdmla.api.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public enum ZombieVillagerProvider implements IEntityComponentProvider, IServerDataProvider<EntityAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor) {
        if(accessor.getServerData().hasKey("ConversionTime")) {
            int conversionTime = accessor.getServerData().getInteger("ConversionTime");
            if(conversionTime != -1) {
                tooltip.child(ThemeHelper.INSTANCE.value(
                        StatCollector.translateToLocal("hud.msg.wdmla.conversion.time"),
                        conversionTime + StatCollector.translateToLocal("hud.msg.wdmla.ticks")
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
    //        tagCompound.setInteger("ConversionTime", this.isConverting() ? this.conversionTime : -1);
}
