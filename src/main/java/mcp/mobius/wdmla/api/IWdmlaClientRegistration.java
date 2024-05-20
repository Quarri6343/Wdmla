package mcp.mobius.wdmla.api;

import mcp.mobius.wdmla.impl.value.BlockAccessor;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface IWdmlaClientRegistration {

    void registerBlockComponent(IComponentProvider<BlockAccessor> provider, Class<?> clazz);
}
