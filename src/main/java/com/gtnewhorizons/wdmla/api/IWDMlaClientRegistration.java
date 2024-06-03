package com.gtnewhorizons.wdmla.api;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface IWDMlaClientRegistration {

    void registerBlockComponent(IComponentProvider<BlockAccessor> provider, Class<? extends Block> blockClass);

    boolean isServerConnected();

    boolean isShowDetailsPressed();

    NBTTagCompound getServerData();

    BlockAccessor.Builder blockAccessor();

    <T extends Accessor> void registerAccessorHandler(Class<T> clazz, AccessorClientHandler<T> handler);

    AccessorClientHandler<Accessor> getAccessorHandler(Class<? extends Accessor> clazz);
}
