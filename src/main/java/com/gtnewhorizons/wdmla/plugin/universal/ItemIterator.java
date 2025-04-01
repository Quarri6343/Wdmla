package com.gtnewhorizons.wdmla.plugin.universal;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.gtnewhorizons.wdmla.api.Accessor;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public abstract class ItemIterator<T> {
	public static final AtomicLong version = new AtomicLong();
	protected final Function<Accessor, @Nullable T> containerFinder;
	protected final int fromIndex;
	protected boolean finished;
	protected int currentIndex;

	protected ItemIterator(Function<Accessor, @Nullable T> containerFinder, int fromIndex) {
		this.containerFinder = containerFinder;
		this.currentIndex = this.fromIndex = fromIndex;
	}

	public @Nullable T find(Accessor accessor) {
		return containerFinder.apply(accessor);
	}

	public final boolean isFinished() {
		return finished;
	}

	public long getVersion(T container) {
		return version.getAndIncrement();
	}

	public abstract Stream<ItemStack> populate(T container, int amount);

	public void reset() {
		currentIndex = fromIndex;
		finished = false;
	}

	public void afterPopulate(int count) {
		currentIndex += count;
		if (count == 0 || currentIndex >= 10000) {
			finished = true;
		}
	}

	public float getCollectingProgress() {
		return Float.NaN;
	}

	public static abstract class SlottedItemIterator<T> extends ItemIterator<T> {
		protected float progress;

		public SlottedItemIterator(Function<Accessor, @Nullable T> containerFinder, int fromIndex) {
			super(containerFinder, fromIndex);
		}

		protected abstract int getSlotCount(T container);

		protected abstract ItemStack getItemInSlot(T container, int slot);

		@Override
		public Stream<ItemStack> populate(T container, int amount) {
			int slotCount = getSlotCount(container);
			int toIndex = (long) currentIndex + amount > Integer.MAX_VALUE ? Integer.MAX_VALUE : currentIndex + amount;
			if (toIndex >= slotCount) {
				toIndex = slotCount;
				finished = true;
				progress = 1;
			} else {
				progress = (float) (currentIndex - fromIndex) / (slotCount - fromIndex);
			}
			return IntStream.range(currentIndex, toIndex).mapToObj(slot -> getItemInSlot(container, slot));
		}

		@Override
		public float getCollectingProgress() {
			return progress;
		}
	}

	public static class IInventoryItemIterator extends SlottedItemIterator<IInventory> {
		public IInventoryItemIterator(int fromIndex) {
			this(IInventoryItemIterator::findIInventory, fromIndex);
		}

		public IInventoryItemIterator(Function<Accessor, @Nullable IInventory> containerFinder, int fromIndex) {
			super(containerFinder, fromIndex);
		}

		@Override
		protected int getSlotCount(IInventory container) {
			return container.getSizeInventory();
		}

		@Override
		protected ItemStack getItemInSlot(IInventory container, int slot) {
			return container.getStackInSlot(slot);
		}

		public static IInventory findIInventory(Accessor accessor) {
			Object target = accessor.getTarget();
			if (target instanceof IInventory container) {
				return container;
			}
			return null;
		}
	}

	public static abstract class SlotlessItemIterator<T> extends ItemIterator<T> {

		protected SlotlessItemIterator(Function<Accessor, @Nullable T> containerFinder, int fromIndex) {
			super(containerFinder, fromIndex);
		}

		@Override
		public Stream<ItemStack> populate(T container, int amount) {
			return populateRaw(container).skip(currentIndex).limit(amount);
		}

		protected abstract Stream<ItemStack> populateRaw(T container);
	}
}