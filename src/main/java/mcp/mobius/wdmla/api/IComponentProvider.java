package mcp.mobius.wdmla.api;

import mcp.mobius.wdmla.api.ui.ITooltip;

public interface IComponentProvider<T extends Accessor> extends IToggleableProvider {

    void appendTooltip(ITooltip tooltip, T accessor);
}
