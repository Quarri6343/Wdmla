package com.gtnewhorizons.wdmla.plugin.vanilla;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.impl.ui.component.HPanelComponent;

import mcp.mobius.waila.cbcore.LangUtil;

public enum BeaconProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        int levels = accessor.getServerData().getInteger("Levels");
        tooltip.child(
                ThemeHelper.INSTANCE.value(LangUtil.translateG("hud.msg.wdmla.level"), String.valueOf(levels))
        );

        int primary = accessor.getServerData().getInteger("Primary");
        if (primary > 0) {
            tooltip.child(
                    ThemeHelper.INSTANCE.value(LangUtil.translateG("hud.msg.wdmla.beacon.primary"),
                            LangUtil.translateG(Potion.potionTypes[primary].getName()))
            );
        }

        int secondary = accessor.getServerData().getInteger("Secondary");
        if (secondary > 0) {
            tooltip.child(
                    ThemeHelper.INSTANCE.value(LangUtil.translateG("hud.msg.wdmla.beacon.secondary"),
                            LangUtil.translateG(Potion.potionTypes[secondary].getName()))
            );
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
