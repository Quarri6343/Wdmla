package com.gtnewhorizons.wdmla.plugin.vanilla;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.config.General;

/**
 * special class for providing only custom name for specific tile entities. <br>
 * There is no interface or rule to see whether te has custom name or not, but it is common in classes implement
 * IInventory. <br>
 * Please do not implement this on TileEntity.class. It will just cause tons of unnecessary packet transaction. <br>
 * Client Side:
 * 
 * @see com.gtnewhorizons.wdmla.plugin.core.DefaultBlockInfoProvider#appendTooltip(ITooltip, BlockAccessor)
 */
public enum TECustomNameHeaderProvider implements IServerDataProvider<BlockAccessor> {

    INSTANCE;

    @Override
    public void appendServerData(NBTTagCompound data, BlockAccessor accessor) {
        if (General.customNameOverride) {
            return;
        }
        NBTTagCompound nameTag = new NBTTagCompound();
        accessor.getTileEntity().writeToNBT(nameTag);
        if (nameTag.hasKey("CustomName")) {
            data.setString("CustomName", nameTag.getString("CustomName"));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.TE_CUSTOM_NAME_HEADER;
    }
}
