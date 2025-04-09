package com.gtnewhorizons.wdmla.api.provider;

import com.gtnewhorizons.wdmla.api.accessor.Accessor;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;

public interface IComponentProvider<T extends Accessor> extends IToggleableProvider {

    void appendTooltip(ITooltip tooltip, T accessor);
}
