package mcp.mobius.wdmla.api;

import mcp.mobius.wdmla.api.ui.IComponent;
import mcp.mobius.wdmla.api.ui.ITooltip;

import java.util.function.Function;

public interface AccessorClientHandler<T extends Accessor> {

	boolean shouldDisplay(T accessor);

	boolean shouldRequestData(T accessor);

	void requestData(T accessor);

	ITooltip getIcon(BlockAccessor accessor);

	void gatherComponents(T accessor, ITooltip tooltip);
}