package com.gtnewhorizons.wdmla.api.provider;

import java.util.List;

import com.gtnewhorizons.wdmla.api.view.ViewGroup;
import org.jetbrains.annotations.Nullable;

import com.gtnewhorizons.wdmla.api.accessor.Accessor;

public interface IServerExtensionProvider<T> extends IWDMlaProvider {

    @Nullable
    List<ViewGroup<T>> getGroups(Accessor accessor);

    default boolean shouldRequestData(Accessor accessor) {
        return true;
    }
}
