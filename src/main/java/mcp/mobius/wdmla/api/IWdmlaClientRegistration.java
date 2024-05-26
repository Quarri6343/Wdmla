package mcp.mobius.wdmla.api;

import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface IWdmlaClientRegistration {

    void registerBlockComponent(IComponentProvider<BlockAccessor> provider, Class<?> clazz);

    boolean isServerConnected();

    boolean isShowDetailsPressed();

    NBTTagCompound getServerData();

    BlockAccessor.Builder blockAccessor();
}
