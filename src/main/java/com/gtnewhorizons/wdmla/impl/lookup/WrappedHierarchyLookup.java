package com.gtnewhorizons.wdmla.impl.lookup;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Stream;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.gtnewhorizons.wdmla.api.accessor.Accessor;
import com.gtnewhorizons.wdmla.api.accessor.BlockAccessor;
import com.gtnewhorizons.wdmla.api.provider.IWDMlaProvider;
import com.gtnewhorizons.wdmla.impl.PriorityStore;
import com.gtnewhorizons.wdmla.impl.WDMlaCommonRegistration;

public class WrappedHierarchyLookup<T extends IWDMlaProvider> extends HierarchyLookup<T> {

    public final List<Pair<IHierarchyLookup<T>, Function<Accessor, @Nullable Object>>> overrides = Lists.newArrayList();
    private boolean empty = true;

    public WrappedHierarchyLookup() {
        super(Object.class);
    }

    public static <T extends IWDMlaProvider> WrappedHierarchyLookup<T> forAccessor() {
        WrappedHierarchyLookup<T> lookup = new WrappedHierarchyLookup<>();
        lookup.overrides.add(Pair.of(new HierarchyLookup<>(Block.class), accessor -> {
            if (accessor instanceof BlockAccessor blockAccessor) {
                return blockAccessor.getBlock();
            }
            return null;
        }));
        return lookup;
    }

    public List<T> wrappedGet(Accessor accessor) {
        Set<T> set = Sets.newLinkedHashSet();
        for (var override : overrides) {
            Object o = override.getRight().apply(accessor);
            if (o != null) {
                set.addAll(override.getLeft().get(o));
            }
        }
        set.addAll(get(accessor.getTarget()));
        return set.stream().sorted(Comparator.comparingInt(WDMlaCommonRegistration.instance().priorities::byValue))
                .collect(collectingAndThen(toList(), ImmutableList::copyOf));
    }

    public boolean hitsAny(Accessor accessor, BiPredicate<T, Accessor> predicate) {
        for (T provider : wrappedGet(accessor)) {
            if (predicate.test(provider, accessor)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void register(Class<?> clazz, T provider) {
        for (var override : overrides) {
            if (override.getLeft().isClassAcceptable(clazz)) {
                override.getLeft().register(clazz, provider);
                empty = false;
                return;
            }
        }
        super.register(clazz, provider);
        empty = false;
    }

    @Override
    public boolean isClassAcceptable(Class<?> clazz) {
        for (var override : overrides) {
            if (override.getLeft().isClassAcceptable(clazz)) {
                return true;
            }
        }
        return super.isClassAcceptable(clazz);
    }

    @Override
    public void invalidate() {
        for (var override : overrides) {
            override.getLeft().invalidate();
        }
        super.invalidate();
    }

    @Override
    public void loadComplete(PriorityStore<ResourceLocation, IWDMlaProvider> priorityStore) {
        for (var override : overrides) {
            override.getLeft().loadComplete(priorityStore);
        }
        super.loadComplete(priorityStore);
    }

    @Override
    public boolean isEmpty() {
        return empty;
    }

    @Override
    public Stream<Map.Entry<Class<?>, Collection<T>>> entries() {
        Stream<Map.Entry<Class<?>, Collection<T>>> stream = super.entries();
        for (var override : overrides) {
            stream = Stream.concat(stream, override.getLeft().entries());
        }
        return stream;
    }
}
