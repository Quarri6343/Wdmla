package com.gtnewhorizons.wdmla.api.provider;

import java.util.List;

import org.jetbrains.annotations.ApiStatus;

import com.gtnewhorizons.wdmla.api.accessor.Accessor;
import com.gtnewhorizons.wdmla.api.view.ClientViewGroup;
import com.gtnewhorizons.wdmla.api.view.ViewGroup;

@ApiStatus.Experimental
public interface IClientExtensionProvider<IN, OUT> extends IWDMlaProvider {

    List<ClientViewGroup<OUT>> getClientGroups(Accessor accessor, List<ViewGroup<IN>> groups);

}
