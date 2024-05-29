package com.gtnewhorizons.wdmla.api;

import net.minecraft.nbt.NBTTagCompound;

public interface IServerDataProvider<T extends Accessor> extends IWDMlaProvider {

    void appendServerData(NBTTagCompound data, T accessor);

    default boolean shouldRequestData(T accessor) {
        return true;
    }
}
