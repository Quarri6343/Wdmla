package com.gtnewhorizons.wdmla.api;

import com.gtnewhorizons.wdmla.api.accessor.BlockAccessor;
import com.gtnewhorizons.wdmla.api.accessor.EntityAccessor;
import com.gtnewhorizons.wdmla.api.provider.IServerDataProvider;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.ApiStatus;

import com.gtnewhorizons.wdmla.api.provider.IServerExtensionProvider;

@ApiStatus.NonExtendable
public interface IWDMlaCommonRegistration {

    void registerBlockDataProvider(IServerDataProvider<BlockAccessor> dataProvider, Class<?> blockOrTileEntityClass);

    void registerEntityDataProvider(IServerDataProvider<EntityAccessor> dataProvider,
            Class<? extends Entity> entityClass);

    <T> void registerItemStorage(IServerExtensionProvider<ItemStack> provider, Class<? extends T> clazz);
}
