package com.gtnewhorizons.wdmla.impl.lookup;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import net.minecraft.util.ResourceLocation;

import com.google.common.collect.ImmutableList;
import com.gtnewhorizons.wdmla.api.provider.IWDMlaProvider;
import com.gtnewhorizons.wdmla.impl.PriorityStore;

/**
 * Allows fast and easy access to providers. <br>
 * All inherited lookup classes are backported from Jade.
 */
public interface IHierarchyLookup<T extends IWDMlaProvider> {

    default IHierarchyLookup<? extends T> cast() {
        return this;
    }

    void register(Class<?> clazz, T provider);

    boolean isClassAcceptable(Class<?> clazz);

    default List<T> get(Object obj) {
        if (obj == null) {
            return ImmutableList.of();
        }
        return get(obj.getClass());
    }

    List<T> get(Class<?> clazz);

    boolean isEmpty();

    Stream<Map.Entry<Class<?>, Collection<T>>> entries();

    void invalidate();

    void loadComplete(PriorityStore<ResourceLocation, IWDMlaProvider> priorityStore);
}
