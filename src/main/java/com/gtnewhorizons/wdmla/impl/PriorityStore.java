package com.gtnewhorizons.wdmla.impl;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class PriorityStore<K, V> {

    private final Object2IntMap<K> priorities = new Object2IntLinkedOpenHashMap<>();
    private final Function<V, K> keyGetter;
    private final ToIntFunction<V> defaultPriorityGetter;
    @Nullable
    private String configFile;
    private ImmutableList<K> sortedList = ImmutableList.of();
    private BiFunction<PriorityStore<K, V>, Collection<K>, List<K>> sortingFunction = (store, allKeys) -> allKeys.stream()
            .sorted(Comparator.comparingInt(store::byKey))
            .collect(collectingAndThen(toList(), ImmutableList::copyOf));

    public PriorityStore(ToIntFunction<V> defaultPriorityGetter, Function<V, K> keyGetter) {
        this.defaultPriorityGetter = defaultPriorityGetter;
        this.keyGetter = keyGetter;
    }

    public void setSortingFunction(BiFunction<PriorityStore<K, V>, Collection<K>, List<K>> sortingFunction) {
        this.sortingFunction = sortingFunction;
    }

    public void configurable(String configFile) {
        this.configFile = configFile;
    }

    public void put(V provider) {
        Objects.requireNonNull(provider);
        put(provider, defaultPriorityGetter.applyAsInt(provider));
    }

    public void put(V provider, int priority) {
        Objects.requireNonNull(provider);
        K uid = keyGetter.apply(provider);
        Objects.requireNonNull(uid);
        priorities.put(uid, priority);
    }

    public void putUnsafe(K key, int priority) {
        Objects.requireNonNull(key);
        priorities.put(key, priority);
    }

    public void sort(Set<K> extraKeys) {
        Set<K> allKeys = priorities.keySet();
        if (!extraKeys.isEmpty()) {
            allKeys = Sets.union(priorities.keySet(), extraKeys);
        }

//        if (!Strings.isNullOrEmpty(configFile)) {
//            JsonConfig<Map<K, OptionalInt>> config = new JsonConfig<>(
//                    configFile,
//                    Codec.unboundedMap(keyCodec, JadeCodecs.OPTIONAL_INT),
//                    null,
//                    Map::of);
//            Map<K, OptionalInt> map = config.get();
//            for (var e : map.entrySet()) {
//                if (e.getValue().isPresent()) {
//                    priorities.put(e.getKey(), e.getValue().getAsInt());
//                }
//            }
//            new Thread(() -> {
//                boolean changed = false;
//                TreeMap<K, OptionalInt> newMap = Maps.newTreeMap(Comparator.comparing(Object::toString));
//                for (K id : priorities.keySet()) {
//                    if (!map.containsKey(id)) {
//                        newMap.put(id, OptionalInt.empty());
//                        changed = true;
//                    }
//                }
//                if (changed) {
//                    newMap.putAll(map);
//                    config.write(newMap, false);
//                }
//            }).start();
//        }

        sortedList = ImmutableList.copyOf(sortingFunction.apply(this, allKeys));
    }

    public int byValue(V value) {
        return byKey(keyGetter.apply(value));
    }

    public int byKey(K id) {
        return priorities.getInt(id);
    }

    public ImmutableList<K> getSortedList() {
        return sortedList;
    }
}