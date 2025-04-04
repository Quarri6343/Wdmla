package com.gtnewhorizons.wdmla.plugin.vanilla;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.plugin.PluginsConfig;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.StringUtils;

public enum CommandBlockProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        if(accessor.getTileEntity() instanceof TileEntityCommandBlock) {
            String command = accessor.getServerData().getString("command");
            if (StringUtils.isBlank(command)) {
                return;
            }

            tooltip.text("> " + command);
        }
    }

    @Override
    public void appendServerData(NBTTagCompound data, BlockAccessor accessor) {
        if(accessor.getTileEntity() instanceof TileEntityCommandBlock commandBlock
                && MinecraftServer.getServer().isCommandBlockEnabled() && accessor.getPlayer().canCommandSenderUseCommand(2, "")) {
            String command = commandBlock.func_145993_a().func_145753_i();
            int maxLength = PluginsConfig.vanilla.commandBlock.maxCommandLength;
            if (command.length() > maxLength) {
                command = command.substring(0, maxLength - 3) + "...";
            }
            data.setString("command", command);
        }
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.COMMAND_BLOCK;
    }
}
