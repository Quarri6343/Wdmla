package com.gtnewhorizons.wdmla.plugin.vanilla;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;

import mcp.mobius.waila.cbcore.LangUtil;
import net.minecraft.util.StatCollector;

public enum BeaconProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        int levels = accessor.getServerData().getInteger("Levels");
        tooltip.child(ThemeHelper.INSTANCE.value(StatCollector.translateToLocal("hud.msg.wdmla.level"), String.valueOf(levels)));

        int primary = accessor.getServerData().getInteger("Primary");
        if (primary > 0) {
            tooltip.child(
                    ThemeHelper.INSTANCE.value(
                            StatCollector.translateToLocal("hud.msg.wdmla.primary"),
                            StatCollector.translateToLocal(Potion.potionTypes[primary].getName())));
        }

        int secondary = accessor.getServerData().getInteger("Secondary");
        if (secondary > 0) {
            tooltip.child(
                    ThemeHelper.INSTANCE.value(
                            StatCollector.translateToLocal("hud.msg.wdmla.secondary"),
                            StatCollector.translateToLocal(Potion.potionTypes[secondary].getName())));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.BEACON;
    }

    @Override
    public void appendServerData(NBTTagCompound data, BlockAccessor accessor) {
        if (accessor.getTileEntity() != null) {
            accessor.getTileEntity().writeToNBT(data);
        }
    }
}
