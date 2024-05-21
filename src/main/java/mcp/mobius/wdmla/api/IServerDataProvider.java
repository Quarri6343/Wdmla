package mcp.mobius.wdmla.api;

import net.minecraft.nbt.NBTTagCompound;

public interface IServerDataProvider<T extends Accessor> {

    void appendServerData(NBTTagCompound data, T accessor);

    default boolean shouldRequestData(T accessor) {
        return true;
    }
}
