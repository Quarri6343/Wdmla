package com.gtnewhorizons.wdmla.api.provider;

import java.util.List;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import com.gtnewhorizons.wdmla.api.accessor.Accessor;
import com.gtnewhorizons.wdmla.api.view.ViewGroup;

@ApiStatus.Experimental
public interface IServerExtensionProvider<T> extends IWDMlaProvider {

    @Nullable
    List<ViewGroup<T>> getGroups(Accessor accessor);

    default boolean shouldRequestData(Accessor accessor) {
        return true;
    }
}
