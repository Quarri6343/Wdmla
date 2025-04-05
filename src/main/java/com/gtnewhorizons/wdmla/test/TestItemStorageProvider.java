package com.gtnewhorizons.wdmla.test;

import java.util.Arrays;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.Lists;
import com.gtnewhorizons.wdmla.api.Accessor;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.view.ClientViewGroup;
import com.gtnewhorizons.wdmla.api.view.IClientExtensionProvider;
import com.gtnewhorizons.wdmla.api.view.IServerExtensionProvider;
import com.gtnewhorizons.wdmla.api.view.ItemView;
import com.gtnewhorizons.wdmla.api.view.ViewGroup;
import com.gtnewhorizons.wdmla.impl.ui.component.TextComponent;

public enum TestItemStorageProvider
        implements IServerExtensionProvider<ItemStack>, IClientExtensionProvider<ItemStack, ItemView> {

    INSTANCE;

    @Override
    public List<ClientViewGroup<ItemView>> getClientGroups(Accessor accessor, List<ViewGroup<ItemStack>> groups) {
        return ClientViewGroup.map(groups, stack -> {
            NBTTagCompound customData = stack.getTagCompound();
            if (customData == null) {
                return null;
            }
            if (customData.hasKey("amount")) {
                String text = String.valueOf(customData.getInteger("amount"));
                return new ItemView(stack).amountText(text);
            } else if (customData.hasKey("description")) {
                String desc = customData.getString("description");
                return new ItemView(stack).description(new TextComponent(desc));
            } else {
                return null;
            }

        }, (viewGroup, clientViewGroup) -> {
            if (viewGroup.id != null) {
                clientViewGroup.title = viewGroup.id;
            }
        });
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
                amountNBT.setInteger("amount", (int) ((totalTime % 400) / 20));
                stack.setTagCompound(amountNBT);
                list.add(stack);
            }
            List<ItemStack> list2 = Lists.newArrayList();
            for (int i = 0; i < 4; i++) {
                ItemStack stack = new ItemStack(Items.apple, 3);
                NBTTagCompound descNBT = new NBTTagCompound();
                descNBT.setString("description", String.format("This item is %d times important", i));
                stack.setTagCompound(descNBT);
                list2.add(stack);
            }
            return Arrays.asList(new ViewGroup<>(list, "content", null), new ViewGroup<>(list2, "info", null));
        }
        return null;
    }

    @Override
    public ResourceLocation getUid() {
        return Identifiers.TEST_ITEM_STORAGE;
    }
}
