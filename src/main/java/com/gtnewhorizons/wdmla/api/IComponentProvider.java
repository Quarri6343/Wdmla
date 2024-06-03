package com.gtnewhorizons.wdmla.api;

import com.gtnewhorizons.wdmla.api.ui.ITooltip;

public interface IComponentProvider<T extends Accessor> extends IToggleableProvider {

    //TODO: remove
    default ITooltip getIcon(T accessor, ITooltip currentIcon) {
        return null;
    }

    void appendTooltip(ITooltip tooltip, T accessor);
}
