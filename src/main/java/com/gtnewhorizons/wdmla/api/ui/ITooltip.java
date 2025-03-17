package com.gtnewhorizons.wdmla.api.ui;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizons.wdmla.api.ui.sizer.IPadding;
import com.gtnewhorizons.wdmla.api.ui.sizer.ISize;

/**
 * The interface for component which can append child components.
 *     Provides quick shortcuts to add simple elements which does not require theme import.
 */
public interface ITooltip extends IComponent {

    ITooltip text(String text, IPadding padding);

    ITooltip text(String text);

    ITooltip vertical();

    ITooltip horizontal();

    ITooltip item(ItemStack stack, IPadding padding, ISize size);

    ITooltip item(ItemStack stack);

    ITooltip amount(long current, long max, String amountText);

    ITooltip child(@NotNull IComponent child);

    int childrenSize();

    ITooltip clear();

    ITooltip tag(ResourceLocation tag);

    IComponent getChildWithTag(ResourceLocation tag);

    boolean replaceChildWithTag(ResourceLocation tag, IComponent newChild);
}
