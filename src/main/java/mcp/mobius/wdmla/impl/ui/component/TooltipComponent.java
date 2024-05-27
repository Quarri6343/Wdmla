package mcp.mobius.wdmla.impl.ui.component;

import mcp.mobius.wdmla.api.ui.IComponent;
import mcp.mobius.wdmla.api.ui.IDrawable;
import mcp.mobius.wdmla.api.ui.ITooltip;
import mcp.mobius.wdmla.api.ui.sizer.IPadding;
import mcp.mobius.wdmla.api.ui.sizer.ISize;
import mcp.mobius.wdmla.api.ui.style.IPanelStyle;
import mcp.mobius.wdmla.api.ui.style.IProgressStyle;
import mcp.mobius.wdmla.api.ui.style.ITextStyle;
import mcp.mobius.wdmla.impl.ui.value.sizer.Area;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TooltipComponent extends Component implements ITooltip {

    protected List<IComponent> children;

    protected TooltipComponent(List<IComponent> children, IPadding padding, ISize size, IDrawable foreground) {
        super(padding, size, foreground);
        this.children = children;
    }

    @Override
    public void tick(int x, int y) {
        for (IComponent child : children) {
            child.tick(x + padding.getLeft(), y + padding.getTop());
        }
        foreground.draw(new Area(x + padding.getLeft(), y + padding.getTop(), size.getW(), size.getH()));
    }


    @Override
    public ITooltip child(@NotNull IComponent child) {
        this.children.add(child);
        return this;
    }

    @Override
    public ITooltip text(String text, ITextStyle style, IPadding padding) {
        Component c = new TextComponent(text).style(style).padding(padding);
        this.children.add(c);
        return this;
    }

    @Override
    public ITooltip text(String text) {
        Component c = new TextComponent(text);
        this.children.add(c);
        return this;
    }

    @Override
    public ITooltip vertical(IPanelStyle style, IPadding padding) {
        Component c = new VPanelComponent().style(style).padding(padding);
        this.children.add(c);
        return (ITooltip) c;
    }

    @Override
    public ITooltip vertical(IPanelStyle style) {
        Component c = new VPanelComponent().style(style);
        this.children.add(c);
        return (ITooltip) c;
    }

    @Override
    public ITooltip vertical() {
        Component c = new VPanelComponent();
        this.children.add(c);
        return (ITooltip) c;
    }

    @Override
    public ITooltip horizontal(IPanelStyle style, IPadding padding) {
        Component c = new HPanelComponent().style(style).padding(padding);
        this.children.add(c);
        return (ITooltip) c;
    }

    @Override
    public ITooltip horizontal(IPanelStyle style) {
        Component c = new HPanelComponent().style(style);
        this.children.add(c);
        return (ITooltip) c;
    }

    @Override
    public ITooltip horizontal() {
        Component c = new HPanelComponent();
        this.children.add(c);
        return (ITooltip) c;
    }

    @Override
    public ITooltip item(ItemStack stack, IPadding padding, ISize size) {
        Component c = new ItemComponent(stack).padding(padding).size(size);
        this.children.add(c);
        return this;
    }

    @Override
    public ITooltip item(ItemStack stack, IPadding padding) {
        Component c = new ItemComponent(stack).padding(padding);
        this.children.add(c);
        return this;
    }

    @Override
    public ITooltip item(ItemStack stack) {
        Component c = new ItemComponent(stack);
        this.children.add(c);
        return this;
    }

    @Override
    public ITooltip progress(long current, long max, IProgressStyle style, IPadding padding, ISize size) {
        Component c = new ProgressComponent(current, max).style(style).size(size);
        this.children.add(c);
        return this;
    }

    @Override
    public ITooltip progress(long current, long max, IProgressStyle style) {
        Component c = new ProgressComponent(current, max).style(style);
        this.children.add(c);
        return this;
    }

    @Override
    public ITooltip progress(long current, long max) {
        Component c = new ProgressComponent(current, max);
        this.children.add(c);
        return this;
    }
}
