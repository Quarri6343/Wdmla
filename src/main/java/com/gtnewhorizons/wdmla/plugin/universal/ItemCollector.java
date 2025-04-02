package com.gtnewhorizons.wdmla.plugin.universal;

import com.gtnewhorizons.wdmla.api.Accessor;
import com.gtnewhorizons.wdmla.api.view.ViewGroup;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ItemCollector<T> {
    public static final int MAX_SIZE = 54;
    public static final ItemCollector<?> EMPTY = new ItemCollector<>(null);

    private final Object2IntLinkedOpenHashMap<Item> items = new Object2IntLinkedOpenHashMap<>();
    private final ItemIterator<T> iterator;
    public long version;
    public long lastTimeFinished;
    public boolean lastTimeIsEmpty;
    public List<ViewGroup<ItemStack>> mergedResult;

    public ItemCollector(ItemIterator<T> iterator) {
        this.iterator = iterator;
    }

    public List<ViewGroup<ItemStack>> update(Accessor accessor) {
        if (iterator == null) {
            return null;
        }
        T container = iterator.find(accessor);
        if (container == null) {
            return null;
        }
        long currentVersion = iterator.getVersion(container);
        long gameTime = accessor.getWorld().getTotalWorldTime();
        if (mergedResult != null && iterator.isFinished()) {
            if (version == currentVersion) {
                return mergedResult; // content not changed
            }
            if (lastTimeFinished + 5 > gameTime) {
                return mergedResult; // avoid update too frequently
            }
            iterator.reset();
        }
        AtomicInteger count = new AtomicInteger();
        iterator.populate(container, MAX_SIZE * 2).forEach(stack -> {
            count.incrementAndGet();
            if (stack != null) {
                Item def = stack.getItem(); //ignore properties
                items.addTo(def, stack.stackSize);
            }
        });
        iterator.afterPopulate(count.get());
        if (mergedResult != null && !iterator.isFinished()) {
            updateCollectingProgress(mergedResult.get(0));
            return mergedResult;
        }
        List<ItemStack> partialResult = items.object2IntEntrySet().stream().limit(MAX_SIZE).map(entry ->
            new ItemStack(entry.getKey(), entry.getIntValue())).collect(Collectors.toList());
        List<ViewGroup<ItemStack>> groups = Arrays.asList(updateCollectingProgress(new ViewGroup<>(partialResult)));
        if (iterator.isFinished()) {
            mergedResult = groups;
            lastTimeIsEmpty = mergedResult.get(0).views.isEmpty();
            version = currentVersion;
            lastTimeFinished = gameTime;
            items.clear();
        }
        return groups;
    }

    protected ViewGroup<ItemStack> updateCollectingProgress(ViewGroup<ItemStack> group) {
        if (lastTimeIsEmpty && group.views.isEmpty()) {
            return group;
        }
        float progress = iterator.getCollectingProgress();
        NBTTagCompound data = group.getExtraData();
        if (Float.isNaN(progress) || progress >= 1) {
            data.removeTag("Collecting");
        } else {
            data.setFloat("Collecting", progress);
        }
        return group;
    }
    //backporting Jade itemcollector
    //itemdefinition -> just use Item
}
