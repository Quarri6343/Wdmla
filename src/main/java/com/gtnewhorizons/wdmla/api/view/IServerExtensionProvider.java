package com.gtnewhorizons.wdmla.api.view;

import com.gtnewhorizons.wdmla.api.Accessor;
import com.gtnewhorizons.wdmla.api.IWDMlaProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IServerExtensionProvider<T> extends IWDMlaProvider {

    @Nullable
    List<ViewGroup<T>> getGroups(Accessor accessor);

    default boolean shouldRequestData(Accessor accessor) {
        return true;
    }
}
