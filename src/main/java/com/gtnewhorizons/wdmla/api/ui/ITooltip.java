package com.gtnewhorizons.wdmla.api.ui;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizons.wdmla.api.ui.sizer.IPadding;
import com.gtnewhorizons.wdmla.api.ui.sizer.ISize;
import com.gtnewhorizons.wdmla.api.ui.style.IItemStyle;
import com.gtnewhorizons.wdmla.api.ui.style.IPanelStyle;
import com.gtnewhorizons.wdmla.api.ui.style.IProgressStyle;
import com.gtnewhorizons.wdmla.api.ui.style.ITextStyle;

/**
 * The interface for component which can append child components Provides quick shortcuts to add simple elements
 */
public interface ITooltip extends IComponent {

    ITooltip text(String text, ITextStyle style, IPadding padding);

    ITooltip text(String text);

    ITooltip vertical(IPanelStyle style, IPadding padding);

    ITooltip vertical(IPanelStyle style);

    ITooltip vertical();

    ITooltip horizontal(IPanelStyle style, IPadding padding);

    ITooltip horizontal(IPanelStyle style);

    ITooltip horizontal();

    ITooltip item(ItemStack stack, IItemStyle style, IPadding padding, ISize size);

    ITooltip item(ItemStack stack, IPadding padding, ISize size);

    ITooltip item(ItemStack stack);

    ITooltip progress(long current, long max, IProgressStyle style, String progressText);

    ITooltip progress(long current, long max, String progressText);

    ITooltip child(@NotNull IComponent child);

    ITooltip clear();

    ITooltip tag(ResourceLocation tag);

    boolean replaceChildWithTag(ResourceLocation tag, IComponent newChild);
}
