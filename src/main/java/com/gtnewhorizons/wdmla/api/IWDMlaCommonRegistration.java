package com.gtnewhorizons.wdmla.api;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.ApiStatus;

import com.gtnewhorizons.wdmla.api.view.IServerExtensionProvider;

@ApiStatus.NonExtendable
public interface IWDMlaCommonRegistration {

    void registerBlockDataProvider(IServerDataProvider<BlockAccessor> dataProvider, Class<?> blockOrTileEntityClass);

    void registerEntityDataProvider(IServerDataProvider<EntityAccessor> dataProvider,
            Class<? extends Entity> entityClass);

    <T> void registerItemStorage(IServerExtensionProvider<ItemStack> provider, Class<? extends T> clazz);
}
