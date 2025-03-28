package com.gtnewhorizons.wdmla.plugin.harvestability.proxy;

import java.lang.reflect.Method;

import net.minecraft.block.Block;

import com.gtnewhorizons.wdmla.api.Mods;

public class ProxyCreativeBlocks {

    private static Method isCreativeBlock = null;

    static {
        if (Mods.CREATIVEBLOCKS.isLoaded()) {
            try {
                Class<?> creativeBlocks = Class.forName("squeek.creativeblocks.CreativeBlocks");
                isCreativeBlock = creativeBlocks.getDeclaredMethod("isCreativeBlock", Block.class, int.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isCreativeBlock(Block block, int meta) {
        if (isCreativeBlock != null) {
            try {
                return (Boolean) isCreativeBlock.invoke(null, block, meta);
            } catch (Exception e) {}
        }
        return false;
    }
}
