package mcp.mobius.waila.addons;

import static mcp.mobius.waila.api.SpecialChars.*;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.elements.IProbeInfo;
import mcp.mobius.waila.handlers.HUDHandlerBlocks;
import mcp.mobius.waila.utils.ModIdentification;

public class DefaultProbeInfoProvider {

    public static void addStandardBlockInfo(ItemStack itemStack, IProbeInfo probeInfo, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {

        HUDHandlerBlocks hudHandlerBlocks = new HUDHandlerBlocks();
        IProbeInfo row = probeInfo.horizontal();
        row.item(itemStack);
        IProbeInfo row_vertical = row.vertical();
        row_vertical.text(hudHandlerBlocks.getWailaHead(itemStack, new ArrayList<>(), accessor, config).get(0));
        String modName = ModIdentification.nameFromStack(itemStack);
        if (modName != null && !modName.isEmpty()) {
            row_vertical.text(BLUE + ITALIC + modName);
        }
    }
}
