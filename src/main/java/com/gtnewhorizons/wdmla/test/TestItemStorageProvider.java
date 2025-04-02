package com.gtnewhorizons.wdmla.test;

import com.google.common.collect.Lists;
import com.gtnewhorizons.wdmla.api.Accessor;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.view.ClientViewGroup;
import com.gtnewhorizons.wdmla.api.view.IClientExtensionProvider;
import com.gtnewhorizons.wdmla.api.view.IServerExtensionProvider;
import com.gtnewhorizons.wdmla.api.view.ItemView;
import com.gtnewhorizons.wdmla.api.view.ViewGroup;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public enum TestItemStorageProvider implements IServerExtensionProvider<ItemStack>, IClientExtensionProvider<ItemStack, ItemView> {
    INSTANCE;

    @Override
    public List<ClientViewGroup<ItemView>> getClientGroups(Accessor accessor, List<ViewGroup<ItemStack>> groups) {
        return ClientViewGroup.map(groups, stack -> {
            NBTTagCompound customData = stack.getTagCompound();
            if (customData == null) {
                return null;
            }
            if (!customData.hasKey("amount")) {
                return null;
            }
            String text = String.valueOf(customData.getInteger("amount"));
            return new ItemView(stack).amountText(text);
        }, (viewGroup, clientViewGroup) -> clientViewGroup.title = "Test Group");
    }

    @Override
    public @Nullable List<ViewGroup<ItemStack>> getGroups(Accessor accessor) {
        if (accessor.getTarget() instanceof IInventory inventory) {
            List<ItemStack> list = Lists.newArrayList();
            for (int i = 0; i < inventory.getSizeInventory(); i++) {
                ItemStack stack = inventory.getStackInSlot(i);
                if (stack == null) {
                    continue;
                }
                stack = stack.copy();
                NBTTagCompound amountNBT = new NBTTagCompound();
                long totalTime = accessor.getWorld().getTotalWorldTime();
                amountNBT.setInteger("amount", (int)((totalTime % 400) / 20));
                stack.setTagCompound(amountNBT);
                list.add(stack);
            }
            return Arrays.asList(new ViewGroup<>(list));
        }
        return null;
    }

    @Override
    public ResourceLocation getUid() {
        return Identifiers.TEST_ITEM_STORAGE;
    }
}
