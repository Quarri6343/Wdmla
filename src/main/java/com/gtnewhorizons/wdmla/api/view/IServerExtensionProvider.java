package com.gtnewhorizons.wdmla.api.view;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.gtnewhorizons.wdmla.api.Accessor;
import com.gtnewhorizons.wdmla.api.IWDMlaProvider;

public interface IServerExtensionProvider<T> extends IWDMlaProvider {

    @Nullable
    List<ViewGroup<T>> getGroups(Accessor accessor);

    default boolean shouldRequestData(Accessor accessor) {
        return true;
    }
}
