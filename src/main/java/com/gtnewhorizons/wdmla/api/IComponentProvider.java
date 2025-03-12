package com.gtnewhorizons.wdmla.api;

import com.gtnewhorizons.wdmla.api.ui.ITooltip;

public interface IComponentProvider<T extends Accessor> extends IToggleableProvider {

    void appendTooltip(ITooltip tooltip, T accessor, IPluginConfig config);
}
