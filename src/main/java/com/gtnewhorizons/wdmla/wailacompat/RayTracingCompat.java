package com.gtnewhorizons.wdmla.wailacompat;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;

import org.jetbrains.annotations.Nullable;

import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.api.impl.DataAccessorCommon;
import mcp.mobius.waila.api.impl.ModuleRegistrar;

public enum RayTracingCompat {

    INSTANCE;

    private final Minecraft mc = Minecraft.getMinecraft();

    public @Nullable Entity getWailaEntity(MovingObjectPosition target) {
        ArrayList<Entity> ents = new ArrayList<>();

        if (target == null) return null;

        if (ModuleRegistrar.instance().hasOverrideEntityProviders(target.entityHit)) {
            for (List<IWailaEntityProvider> listProviders : ModuleRegistrar.instance()
                    .getOverrideEntityProviders(target.entityHit).values()) {
                for (IWailaEntityProvider provider : listProviders) {
                    ents.add(provider.getWailaOverride(DataAccessorCommon.instance, ConfigHandler.instance()));
                }
            }
        }

        if (!ents.isEmpty()) return ents.get(0);
        else return null;
    }

    public @Nullable ItemStack getWailaStack(MovingObjectPosition target) {
        ArrayList<ItemStack> items = new ArrayList<>();
        int x = target.blockX;
        int y = target.blockY;
        int z = target.blockZ;

        Block mouseoverBlock = mc.theWorld.getBlock(x, y, z);
        TileEntity tileEntity = mc.theWorld.getTileEntity(x, y, z);
        if (mouseoverBlock == null) return null;

        if (ModuleRegistrar.instance().hasStackProviders(mouseoverBlock)) {
            for (List<IWailaDataProvider> providersList : ModuleRegistrar.instance().getStackProviders(mouseoverBlock)
                    .values()) {
                for (IWailaDataProvider provider : providersList) {
                    ItemStack providerStack = provider
                            .getWailaStack(DataAccessorCommon.instance, ConfigHandler.instance());
                    if (providerStack != null) {

                        if (providerStack.getItem() == null) return null;

                        items.add(providerStack);
                    }
                }
            }
        }

        if (tileEntity != null && ModuleRegistrar.instance().hasStackProviders(tileEntity)) {
            for (List<IWailaDataProvider> providersList : ModuleRegistrar.instance().getStackProviders(tileEntity)
                    .values()) {

                for (IWailaDataProvider provider : providersList) {
                    ItemStack providerStack = provider
                            .getWailaStack(DataAccessorCommon.instance, ConfigHandler.instance());
                    if (providerStack != null) {

                        if (providerStack.getItem() == null) return null;

                        items.add(providerStack);
                    }
                }
            }
        }

        if (items.isEmpty()) return null;

        items.sort((stack0, stack1) -> stack1.getItemDamage() - stack0.getItemDamage());

        return items.get(0);
    }
}
