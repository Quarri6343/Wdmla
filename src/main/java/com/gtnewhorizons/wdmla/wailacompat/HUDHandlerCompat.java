package com.gtnewhorizons.wdmla.wailacompat;

import static mcp.mobius.waila.api.SpecialChars.ITALIC;
import static mcp.mobius.waila.api.SpecialChars.WHITE;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.overlay.DisplayUtil;
import mcp.mobius.waila.utils.Constants;

public class HUDHandlerCompat {

    public static void getBlocksWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor) {
        String name = null;
        try {
            String s = DisplayUtil.itemDisplayNameShort(itemStack);
            if (s != null && !s.endsWith("Unnamed")) name = s;

            if (name != null) currenttip.add(name);
        } catch (Exception ignored) {}

        if (itemStack.getItem() == Items.redstone) {
            int md = accessor.getMetadata();
            String s = "" + md;
            if (s.length() < 2) s = " " + s;
            currenttip.set(currenttip.size() - 1, name + " " + s);
        }

        if (currenttip.isEmpty()) currenttip.add("< Unnamed >");
    }

    public static void getEntitiesWailaHead(Entity entity, List<String> currenttip) {
        try {
            currenttip.add(WHITE + entity.getCommandSenderName());
        } catch (Exception e) {
            currenttip.add(WHITE + "Unknown");
        }
    }
}
