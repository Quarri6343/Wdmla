package com.gtnewhorizons.wdmla.plugin.vanilla;

import net.minecraft.block.BlockJukebox;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;

public enum JukeboxProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        String name = accessor.getServerData().getString("name");
        if (name.isEmpty()) {
            return;
        }

        ItemRecord record = ItemRecord.getRecord("records." + name);
        if (record == null) {
            return;
        }

        tooltip.child(
                ThemeHelper.INSTANCE.value(
                        StatCollector.translateToLocal("hud.msg.wdmla.now.playing"),
                        record.getRecordNameLocal()));
    }

    @Override
    public void appendServerData(NBTTagCompound data, BlockAccessor accessor) {
        if (accessor.getTileEntity() instanceof BlockJukebox.TileEntityJukebox tileEntity) {
            ItemStack recordStack = tileEntity.func_145856_a();
            if (recordStack == null || !(recordStack.getItem() instanceof ItemRecord record)) {
                return;
            }

            data.setString("name", record.recordName);
        }
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.JUKEBOX;
    }
}
