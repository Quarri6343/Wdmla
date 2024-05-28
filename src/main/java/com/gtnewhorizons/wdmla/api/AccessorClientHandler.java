package com.gtnewhorizons.wdmla.api;

import com.gtnewhorizons.wdmla.api.ui.ITooltip;

public interface AccessorClientHandler<T extends Accessor> {

	boolean shouldDisplay(T accessor);

	boolean shouldRequestData(T accessor);

	void requestData(T accessor);

	ITooltip getIcon(BlockAccessor accessor);

	void gatherComponents(T accessor, ITooltip tooltip);
}