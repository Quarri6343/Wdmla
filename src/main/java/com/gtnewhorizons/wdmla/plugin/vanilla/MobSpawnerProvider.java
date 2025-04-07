package com.gtnewhorizons.wdmla.plugin.vanilla;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.format.ITimeFormatConfigurable;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.config.WDMlaConfig;
import com.gtnewhorizons.wdmla.impl.format.TimeFormattingPattern;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;

public enum MobSpawnerProvider
        implements IBlockComponentProvider, IServerDataProvider<BlockAccessor>, ITimeFormatConfigurable {

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        if (accessor.getServerData().hasKey("delay")) {
            int delay = accessor.getServerData().getInteger("delay");
            TimeFormattingPattern timePattern = WDMlaConfig.instance().getTimeFormatter(this);
            tooltip.child(
                    ThemeHelper.INSTANCE.value(
                            StatCollector.translateToLocal("hud.msg.wdmla.delay"),
                            timePattern.tickFormatter.apply(delay)));
        }
    }

    @Override
    public void appendServerData(NBTTagCompound data, BlockAccessor accessor) {
        if (accessor.getTileEntity() instanceof TileEntityMobSpawner spawner) {
            int delay = spawner.func_145881_a().spawnDelay;
            data.setInteger("delay", delay);
        }
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.MOB_SPAWNER;
    }

    @Override
    public boolean canToggleInGui() {
        return false;
    }

    @Override
    public boolean enabledByDefault() {
        return false;
    }
}
