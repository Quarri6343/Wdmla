package com.gtnewhorizons.wdmla.test;

import java.util.Random;

import com.gtnewhorizons.wdmla.api.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.api.ui.sizer.IPadding;
import com.gtnewhorizons.wdmla.impl.ui.component.TextComponent;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Padding;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Size;
import com.gtnewhorizons.wdmla.impl.ui.style.PanelStyle;
import com.gtnewhorizons.wdmla.impl.ui.style.ProgressStyle;
import com.gtnewhorizons.wdmla.impl.ui.style.TextStyle;

import mcp.mobius.waila.overlay.DisplayUtil;

public class TestBodyProvider implements IComponentProvider<BlockAccessor>, IServerDataProvider<BlockAccessor> {

    @Override
    public ResourceLocation getUid() {
        return Identifiers.TEST_BODY;
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.BODY + 10;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        int cookTime = accessor.getServerData().getShort("CookTime");
        if (cookTime != 0) {
            cookTime = Math.round(cookTime / 20.0f);

            tooltip.vertical().progress(
                    cookTime,
                    10,
                    new ProgressStyle().filledColor(0xFF4CBB17).alternateFilledColor(0xFF4CBB17)
                            .borderColor(0xFF555555),
                    "Smelting: " + cookTime + " / 10 s");
        }

        if (!accessor.showDetails()) {
            return;
        }

        int burnTime = accessor.getServerData().getInteger("BurnTime") / 20;
        if (burnTime > 0) {
            tooltip.horizontal().text("Burn").item(new ItemStack(Blocks.fire), new Padding(), new Size(8, 8))
                    .text(": " + burnTime + " " + "Seconds Remaining");
        }

        ItemStack[] items = new ItemStack[3];
        NBTTagList itemsTag = accessor.getServerData().getTagList("Items", 10);

        for (int i = 0; i < itemsTag.tagCount(); i++) {
            NBTTagCompound itemTag = itemsTag.getCompoundTagAt(i);
            byte slot = itemTag.getByte("Slot");

            if (slot >= 0 && slot < items.length) {
                items[slot] = ItemStack.loadItemStackFromNBT(itemTag);
            }
        }

        IPadding itemPadding = new Padding().vertical(2);
        ITooltip itemSection = tooltip.vertical(new PanelStyle().borderColor(0xff00ffff));
        if (items[0] != null) {
            itemSection.horizontal().text("In: ", new TextStyle(), itemPadding)
                    .item(items[0], new Padding(), new Size(10, 10)).text(
                            " " + DisplayUtil.itemDisplayNameShort(items[0]) + " x" + items[0].stackSize,
                            new TextStyle(),
                            itemPadding);
        }

        if (items[2] != null) {
            itemSection.horizontal().text("Out: ", new TextStyle(), itemPadding)
                    .item(items[2], new Padding(), new Size(10, 10)).text(
                            " " + DisplayUtil.itemDisplayNameShort(items[2]) + " x" + items[2].stackSize,
                            new TextStyle(),
                            itemPadding);
        }

        if (items[1] != null) {
            itemSection.horizontal().text("Fuel: ", new TextStyle(), itemPadding)
                    .item(items[1], new Padding(), new Size(10, 10)).text(
                            " " + DisplayUtil.itemDisplayNameShort(items[1]) + " x" + items[1].stackSize,
                            new TextStyle(),
                            itemPadding);
        }

        int random = accessor.getServerData().getInteger("random");
        tooltip.child(new TextComponent("Recieved Server Data: " + random));
    }

    @Override
    public void appendServerData(NBTTagCompound data, BlockAccessor accessor) {
        if (accessor.getTileEntity() != null) {
            accessor.getTileEntity().writeToNBT(data);
        }
        data.setInteger("random", new Random().nextInt(11));
    }
}
