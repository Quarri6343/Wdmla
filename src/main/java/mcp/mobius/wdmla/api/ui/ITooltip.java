package mcp.mobius.wdmla.api.ui;
import mcp.mobius.wdmla.api.ui.sizer.IPadding;
import mcp.mobius.wdmla.api.ui.sizer.ISize;
import mcp.mobius.wdmla.api.ui.style.IPanelStyle;
import mcp.mobius.wdmla.api.ui.style.IProgressStyle;
import mcp.mobius.wdmla.api.ui.style.ITextStyle;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * The interface for component which can append child components
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

    ITooltip item(ItemStack stack, IPadding padding, ISize size);

    ITooltip item(ItemStack stack, IPadding padding);

    ITooltip item(ItemStack stack);

    ITooltip progress(long current, long max, IProgressStyle style, IPadding padding, ISize size);

    ITooltip progress(long current, long max, IProgressStyle style);

    ITooltip progress(long current, long max);

    ITooltip child(@NotNull IComponent child);
}
