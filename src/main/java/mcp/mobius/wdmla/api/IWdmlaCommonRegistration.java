package mcp.mobius.wdmla.api;

import mcp.mobius.wdmla.impl.value.BlockAccessor;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface IWdmlaCommonRegistration {

    void registerBlockDataProvider(IServerDataProvider<BlockAccessor> dataProvider, Class<?> blockOrBlockEntityClass);
}
