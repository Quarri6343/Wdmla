package com.gtnewhorizons.wdmla.plugin.vanilla;

import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.Nullable;

import com.gtnewhorizons.wdmla.api.accessor.Accessor;
import com.gtnewhorizons.wdmla.api.provider.IClientExtensionProvider;
import com.gtnewhorizons.wdmla.api.provider.IServerExtensionProvider;
import com.gtnewhorizons.wdmla.api.view.ClientViewGroup;
import com.gtnewhorizons.wdmla.api.view.ItemView;
import com.gtnewhorizons.wdmla.api.view.ViewGroup;

public enum ItemFrameProvider
        implements IServerExtensionProvider<ItemStack>, IClientExtensionProvider<ItemStack, ItemView> {

    INSTANCE;

    @Override
    @Nullable
    public List<ViewGroup<ItemStack>> getGroups(Accessor accessor) {
        if (accessor.getTarget() instanceof EntityItemFrame itemFrame && itemFrame.getDisplayedItem() != null) {
            return Arrays.asList(new ViewGroup<>(Arrays.asList(itemFrame.getDisplayedItem())));
        }

        return null;
    }

    @Override
    public List<ClientViewGroup<ItemView>> getClientGroups(Accessor accessor, List<ViewGroup<ItemStack>> groups) {
        return ClientViewGroup.map(groups, ItemView::new, null);
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.ITEM_FRAME;
    }
}
