package mcp.mobius.wdmla.api;

import org.jetbrains.annotations.ApiStatus;

import mcp.mobius.wdmla.impl.value.BlockAccessor;

@ApiStatus.NonExtendable
public interface IWdmlaClientRegistration {

    void registerBlockComponent(IComponentProvider<BlockAccessor> provider, Class<?> clazz);
}
