package com.gtnewhorizons.wdmla.api.provider;

import java.util.List;

import com.gtnewhorizons.wdmla.api.accessor.Accessor;
import com.gtnewhorizons.wdmla.api.view.ClientViewGroup;
import com.gtnewhorizons.wdmla.api.view.ViewGroup;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public interface IClientExtensionProvider<IN, OUT> extends IWDMlaProvider {

    List<ClientViewGroup<OUT>> getClientGroups(Accessor accessor, List<ViewGroup<IN>> groups);

}
