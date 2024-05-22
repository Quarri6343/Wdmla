package mcp.mobius.wdmla.api;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface IWdmlaCommonRegistration {

    void registerBlockDataProvider(IServerDataProvider<IBlockAccessor> dataProvider, Class<?> blockOrBlockEntityClass);
}
