package com.gtnewhorizons.wdmla.plugin.vanilla;

import com.gtnewhorizons.wdmla.api.format.ITimeFormatterAccessor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.StringUtils;

import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IEntityComponentProvider;
import com.gtnewhorizons.wdmla.api.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;

import mcp.mobius.waila.cbcore.LangUtil;

public enum PrimedTNTProvider implements IEntityComponentProvider, IServerDataProvider<EntityAccessor>, ITimeFormatterAccessor {

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor) {
        tooltip.child(
                ThemeHelper.INSTANCE.value(
                        StatCollector.translateToLocal("hud.msg.wdmla.fuse"),
                        getDefaultTimeFormatter().tickFormatter.apply((int) accessor.getServerData().getByte("Fuse"))));
    }

    @Override
    public void appendServerData(NBTTagCompound data, EntityAccessor accessor) {
        accessor.getEntity().writeToNBT(data);
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.PRIMED_TNT;
    }
}
