package mcp.mobius.waila.test;

import java.util.List;

import net.minecraft.block.BlockFurnace;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.GameData;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.SpecialChars;
import mcp.mobius.waila.api.impl.ModuleRegistrar;

/**
 * Test code to see compatibility between old TTRender and WDMla component
 */
public class LegacyTTRenderTest implements IWailaDataProvider {

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        int cookTime = accessor.getNBTData().getShort("CookTime");

        ItemStack[] items = new ItemStack[3];
        NBTTagList itemsTag = accessor.getNBTData().getTagList("Items", 10);
        for (int i = 0; i < itemsTag.tagCount(); i++) {
            NBTTagCompound itemTag = itemsTag.getCompoundTagAt(i);
            byte slot = itemTag.getByte("Slot");

            if (slot >= 0 && slot < items.length) {
                items[slot] = ItemStack.loadItemStackFromNBT(itemTag);
            }
        }

        String renderStr = "";
        if (items[1] != null) {
            ItemStack stack = items[1];
            String name = GameData.getItemRegistry().getNameForObject(stack.getItem());
            renderStr += SpecialChars.getRenderString(
                    "waila.stack",
                    "1",
                    name,
                    String.valueOf(stack.stackSize),
                    String.valueOf(stack.getItemDamage()));
        }
        if (items[0] != null) {
            ItemStack stack = items[0];
            String name = GameData.getItemRegistry().getNameForObject(stack.getItem());
            renderStr += SpecialChars.getRenderString(
                    "waila.stack",
                    "1",
                    name,
                    String.valueOf(stack.stackSize),
                    String.valueOf(stack.getItemDamage()));
        }

        renderStr += SpecialChars.getRenderString("waila.progress", String.valueOf(cookTime), String.valueOf(200));

        if (items[2] != null) {
            ItemStack stack = items[2];
            String name = GameData.getItemRegistry().getNameForObject(stack.getItem());
            renderStr += SpecialChars.getRenderString(
                    "waila.stack",
                    "1",
                    name,
                    String.valueOf(stack.stackSize),
                    String.valueOf(stack.getItemDamage()));
        }

        currenttip.add(renderStr);

        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x,
            int y, int z) {
        if (te != null) te.writeToNBT(tag);
        return tag;
    }

    public static void register() {
        ModuleRegistrar.instance().registerBodyProvider(new LegacyTTRenderTest(), BlockFurnace.class);
        ModuleRegistrar.instance().registerNBTProvider(new LegacyTTRenderTest(), BlockFurnace.class);
    }
}
