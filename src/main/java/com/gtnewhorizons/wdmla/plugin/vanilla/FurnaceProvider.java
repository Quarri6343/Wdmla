package com.gtnewhorizons.wdmla.plugin.vanilla;

import java.util.Arrays;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.ui.IComponent;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;

import mcp.mobius.waila.cbcore.LangUtil;

public enum FurnaceProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        int cookTime = accessor.getServerData().getShort("CookTime");
        cookTime = Math.round(cookTime / 20.0f);

        ItemStack[] items = new ItemStack[3];
        NBTTagList itemsTag = accessor.getServerData().getTagList("Items", 10);

        boolean allEmpty = true;
        for (int i = 0; i < itemsTag.tagCount(); i++) {
            NBTTagCompound itemTag = itemsTag.getCompoundTagAt(i);
            byte slot = itemTag.getByte("Slot");

            if (slot >= 0 && slot < items.length) {
                items[slot] = ItemStack.loadItemStackFromNBT(itemTag);
                if (items[slot] != null) {
                    allEmpty = false;
                }
            }
        }

        if (items[0] != null && items[2] == null) {
            ItemStack resultStack = FurnaceRecipes.smelting().getSmeltingResult(items[0]);
            if (resultStack != null) {
                items[2] = resultStack.copy();
                items[2].stackSize = 0;
            }
        }

        if (!allEmpty) {
            IComponent legacyProcessText = null;
            if (cookTime != 0) {
                legacyProcessText = ThemeHelper.INSTANCE.value(
                        LangUtil.translateG("hud.msg.progress"),
                        String.format(LangUtil.translateG("hud.msg.progress.seconds"), cookTime, 10));
            }
            IComponent progressComponent = ThemeHelper.INSTANCE.itemProgress(
                    Arrays.asList(items[0], items[1]),
                    Arrays.asList(items[2]),
                    cookTime,
                    10,
                    legacyProcessText,
                    accessor.showDetails());
            if (progressComponent != null) {
                tooltip.child(progressComponent);
            }
        }
    }

    @Override
    public void appendServerData(NBTTagCompound data, BlockAccessor accessor) {
        if (accessor.getTileEntity() != null) {
            accessor.getTileEntity().writeToNBT(data);
        }
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.FURNACE;
    }
}
