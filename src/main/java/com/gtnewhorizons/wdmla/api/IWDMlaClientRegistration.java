package com.gtnewhorizons.wdmla.api;

import com.gtnewhorizons.wdmla.api.accessor.Accessor;
import com.gtnewhorizons.wdmla.api.accessor.AccessorClientHandler;
import com.gtnewhorizons.wdmla.api.accessor.BlockAccessor;
import com.gtnewhorizons.wdmla.api.accessor.EntityAccessor;
import com.gtnewhorizons.wdmla.api.provider.IComponentProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import org.jetbrains.annotations.ApiStatus;

import com.gtnewhorizons.wdmla.api.provider.IClientExtensionProvider;
import com.gtnewhorizons.wdmla.api.view.ItemView;

@ApiStatus.NonExtendable
public interface IWDMlaClientRegistration {

    void registerBlockComponent(IComponentProvider<BlockAccessor> provider, Class<? extends Block> blockClass);

    void registerEntityComponent(IComponentProvider<EntityAccessor> provider, Class<? extends Entity> entityClass);

    void registerItemStorageClient(IClientExtensionProvider<ItemStack, ItemView> provider);

    boolean isServerConnected();

    boolean isShowDetailsPressed();

    NBTTagCompound getServerData();

    BlockAccessor.Builder blockAccessor();

    EntityAccessor.Builder entityAccessor();

    <T extends Accessor> void registerAccessorHandler(Class<T> clazz, AccessorClientHandler<T> handler);

    AccessorClientHandler<Accessor> getAccessorHandler(Class<? extends Accessor> clazz);
}
