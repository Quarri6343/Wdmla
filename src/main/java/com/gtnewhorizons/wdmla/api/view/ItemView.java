package com.gtnewhorizons.wdmla.api.view;

import com.gtnewhorizons.wdmla.api.ui.IComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.Nullable;

public class ItemView {

    public ItemStack item;

    @Nullable
    public String amountText;
    @Nullable
    public IComponent description;

    public ItemView(ItemStack item) {
        this.item = item;
    }

    public ItemView amountText(String amountText) {
        this.amountText = amountText;
        return this;
    }

    public ItemView description(IComponent description) {
        this.description = description;
        return this;
    }

    public static NBTTagCompound encode(ItemView view) {
        NBTTagCompound nbt = new NBTTagCompound();
        view.item.writeToNBT(nbt);
        nbt.setString("amount", view.amountText);
        return nbt;
    }

    public static ItemView decode(NBTTagCompound nbt) {
        ItemView view = new ItemView(ItemStack.loadItemStackFromNBT(nbt));
        view.amountText = nbt.getString("amount");
        return view;
    }
}
