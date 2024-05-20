package mcp.mobius.wdmla.api;

import mcp.mobius.wdmla.api.ui.IComponent;

public interface IComponentProvider<T extends Accessor> {

    void appendTooltip(IComponent rootComponent, T accessor);
}
