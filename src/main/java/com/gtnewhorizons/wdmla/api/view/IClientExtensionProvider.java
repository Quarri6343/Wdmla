package com.gtnewhorizons.wdmla.api.view;

import java.util.List;

import com.gtnewhorizons.wdmla.api.Accessor;
import com.gtnewhorizons.wdmla.api.IWDMlaProvider;

public interface IClientExtensionProvider<IN, OUT> extends IWDMlaProvider {

	List<ClientViewGroup<OUT>> getClientGroups(Accessor accessor, List<ViewGroup<IN>> groups);

}