package com.gtnewhorizons.wdmla.addon.vanilla;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;
import com.gtnewhorizons.wdmla.api.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.impl.ui.component.HPanelComponent;
import mcp.mobius.waila.cbcore.LangUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class BeaconProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        int levels = accessor.getServerData().getInteger("Levels");
        tooltip.child(
                new HPanelComponent()
                        .text(String.format("%s: ", LangUtil.translateG("hud.msg.level")))
                        .child(ThemeHelper.INSTANCE.info(String.format("%d", levels)))
        );

        int primary = accessor.getServerData().getInteger("Primary");
        if(primary > 0) {
            tooltip.child(
                    new HPanelComponent()
                            .text(String.format("%s: ", LangUtil.translateG("hud.msg.beacon.primary")))
                            .child(ThemeHelper.INSTANCE.info(LangUtil.translateG(Potion.potionTypes[primary].getName())))
            );
        }

        int secondary = accessor.getServerData().getInteger("Secondary");
        if(secondary > 0) {
            tooltip.child(
                    new HPanelComponent()
                            .text(String.format("%s: ", LangUtil.translateG("hud.msg.beacon.secondary")))
                            .child(ThemeHelper.INSTANCE.info(LangUtil.translateG(Potion.potionTypes[secondary].getName())))
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
