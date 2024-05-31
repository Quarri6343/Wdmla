package com.gtnewhorizons.wdmla.api;

import org.jetbrains.annotations.ApiStatus;

import com.gtnewhorizons.wdmla.impl.ui.component.Component;

@ApiStatus.Internal
public interface ITTRenderParser {

    /**
     * Parse renderer builder provided by custom regex which is part of legacy Waila api
     * 
     * @param args
     * @return
     */
    Component parse(String[] args);
}
