package com.gtnewhorizons.wdmla.api.provider;

import com.gtnewhorizons.wdmla.api.accessor.Accessor;
import net.minecraft.nbt.NBTTagCompound;

public interface IServerDataProvider<T extends Accessor> extends IWDMlaProvider {

    void appendServerData(NBTTagCompound data, T accessor);

    default boolean shouldRequestData(T accessor) {
        return true;
    }
}
