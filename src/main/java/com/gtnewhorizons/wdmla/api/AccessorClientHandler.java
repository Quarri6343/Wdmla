package com.gtnewhorizons.wdmla.api;

import java.util.function.Function;

import com.gtnewhorizons.wdmla.api.ui.ITooltip;

public interface AccessorClientHandler<T extends Accessor> {

    boolean shouldDisplay(T accessor);

    boolean shouldRequestData(T accessor);

    void requestData(T accessor);

    void gatherComponents(T accessor, Function<IWDMlaProvider, ITooltip> tooltipProvider);
}
