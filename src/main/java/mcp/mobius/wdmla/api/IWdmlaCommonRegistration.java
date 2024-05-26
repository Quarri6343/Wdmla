package mcp.mobius.wdmla.api;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface IWdmlaCommonRegistration {

    void registerBlockDataProvider(IServerDataProvider<BlockAccessor> dataProvider, Class<?> blockOrBlockEntityClass);
}
