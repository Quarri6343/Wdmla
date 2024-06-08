package com.gtnewhorizons.wdmla.impl.lookup;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import net.minecraft.util.ResourceLocation;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.gtnewhorizons.wdmla.api.IWDMlaProvider;
import com.gtnewhorizons.wdmla.impl.PriorityStore;
import com.gtnewhorizons.wdmla.impl.WDMlaCommonRegistration;

import mcp.mobius.waila.utils.WailaExceptionHandler;

public class HierarchyLookup<T extends IWDMlaProvider> implements IHierarchyLookup<T> {

    private final Class<?> baseClass;
    private final Cache<Class<?>, List<T>> resultCache = CacheBuilder.newBuilder().build();
    private final boolean singleton;
    private ListMultimap<Class<?>, T> objects = ArrayListMultimap.create();

    public HierarchyLookup(Class<?> baseClass) {
        this(baseClass, false);
    }

    public HierarchyLookup(Class<?> baseClass, boolean singleton) {
        this.baseClass = baseClass;
        this.singleton = singleton;
    }

    @Override
    public void register(Class<?> clazz, T provider) {
        Preconditions.checkArgument(isClassAcceptable(clazz), "Class %s is not acceptable", clazz);
        Objects.requireNonNull(provider.getUid());
        WDMlaCommonRegistration.instance().priorities.put(provider);
        objects.put(clazz, provider);
    }

    @Override
    public boolean isClassAcceptable(Class<?> clazz) {
        return baseClass.isAssignableFrom(clazz);
    }

    @Override
    public List<T> get(Class<?> clazz) {
        try {
            return resultCache.get(clazz, () -> {
                List<T> list = Lists.newArrayList();
                getInternal(clazz, list);
                list = list.stream()
                        .sorted(Comparator.comparingInt(WDMlaCommonRegistration.instance().priorities::byValue))
                        .collect(collectingAndThen(toList(), ImmutableList::copyOf));
                if (singleton && !list.isEmpty()) {
                    return ImmutableList.of(list.get(0));
                }
                return list;
            });
        } catch (ExecutionException e) {
            WailaExceptionHandler.handleErr(e, this.getClass().getName(), null);
        }
        return ImmutableList.of();
    }

    private void getInternal(Class<?> clazz, List<T> list) {
        if (clazz != baseClass && clazz != Object.class) {
            getInternal(clazz.getSuperclass(), list);
        }
        list.addAll(objects.get(clazz));
    }

    @Override
    public boolean isEmpty() {
        return objects.isEmpty();
    }

    @Override
    public Stream<Map.Entry<Class<?>, Collection<T>>> entries() {
        return objects.asMap().entrySet().stream();
    }

    @Override
    public void invalidate() {
        resultCache.invalidateAll();
    }

    @Override
    public void loadComplete(PriorityStore<ResourceLocation, IWDMlaProvider> priorityStore) {
        objects.asMap().forEach((clazz, list) -> {
            if (list.size() < 2) {
                return;
            }
            Set<ResourceLocation> set = Sets.newHashSetWithExpectedSize(list.size());
            for (T provider : list) {
                if (set.contains(provider.getUid())) {
                    throw new IllegalStateException(
                            String.format(
                                    "Duplicate UID: %s for %s",
                                    provider.getUid(),
                                    list.stream().filter(p -> p.getUid().equals(provider.getUid()))
                                            .map(p -> p.getClass().getName())
                                            .collect(collectingAndThen(toList(), ImmutableList::copyOf))));
                }
                set.add(provider.getUid());
            }
        });

        objects = ImmutableListMultimap.<Class<?>, T>builder()
                .orderValuesBy(Comparator.comparingInt(priorityStore::byValue)).putAll(objects).build();
    }

}
