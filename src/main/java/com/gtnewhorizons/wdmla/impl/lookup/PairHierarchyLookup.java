package com.gtnewhorizons.wdmla.impl.lookup;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import net.minecraft.util.ResourceLocation;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableList;
import com.gtnewhorizons.wdmla.api.IWDMlaProvider;
import com.gtnewhorizons.wdmla.impl.PriorityStore;
import com.gtnewhorizons.wdmla.impl.WDMlaCommonRegistration;

import mcp.mobius.waila.utils.WailaExceptionHandler;

public class PairHierarchyLookup<T extends IWDMlaProvider> implements IHierarchyLookup<T> {

    public final IHierarchyLookup<T> first;
    public final IHierarchyLookup<T> second;
    private final Cache<Pair<Class<?>, Class<?>>, List<T>> mergedCache = CacheBuilder.newBuilder().build();

    public PairHierarchyLookup(IHierarchyLookup<T> first, IHierarchyLookup<T> second) {
        this.first = first;
        this.second = second;
    }

    @SuppressWarnings("unchecked")
    public <ANY> List<ANY> getMerged(Object first, Object second) {
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);
        try {
            return (List<ANY>) mergedCache.get(Pair.of(first.getClass(), second.getClass()), () -> {
                List<T> firstList = this.first.get(first);
                List<T> secondList = this.second.get(second);
                if (firstList.isEmpty()) {
                    return secondList;
                } else if (secondList.isEmpty()) {
                    return firstList;
                }
                return Stream.concat(firstList.stream(), secondList.stream())
                        .sorted(Comparator.comparingInt(WDMlaCommonRegistration.instance().priorities::byValue))
                        .collect(collectingAndThen(toList(), ImmutableList::copyOf));
            });
        } catch (ExecutionException e) {
            WailaExceptionHandler.handleErr(e, this.getClass().getName(), null);
        }
        return ImmutableList.of();
    }

    @Override
    public void register(Class<?> clazz, T provider) {
        if (first.isClassAcceptable(clazz)) {
            first.register(clazz, provider);
        } else if (second.isClassAcceptable(clazz)) {
            second.register(clazz, provider);
        } else {
            throw new IllegalArgumentException("Class " + clazz + " is not acceptable");
        }
    }

    @Override
    public boolean isClassAcceptable(Class<?> clazz) {
        return first.isClassAcceptable(clazz) || second.isClassAcceptable(clazz);
    }

    @Override
    public List<T> get(Class<?> clazz) {
        List<T> result = first.get(clazz);
        if (result.isEmpty()) {
            result = second.get(clazz);
        }
        return result;
    }

    @Override
    public boolean isEmpty() {
        return first.isEmpty() && second.isEmpty();
    }

    @Override
    public Stream<Map.Entry<Class<?>, Collection<T>>> entries() {
        return Stream.concat(first.entries(), second.entries());
    }

    @Override
    public void invalidate() {
        first.invalidate();
        second.invalidate();
        mergedCache.invalidateAll();
    }

    @Override
    public void loadComplete(PriorityStore<ResourceLocation, IWDMlaProvider> priorityStore) {
        first.loadComplete(priorityStore);
        second.loadComplete(priorityStore);
    }
}
