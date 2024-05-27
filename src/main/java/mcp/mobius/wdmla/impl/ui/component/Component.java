package mcp.mobius.wdmla.impl.ui.component;

import java.util.List;

import mcp.mobius.wdmla.api.ui.ITooltip;
import mcp.mobius.wdmla.api.ui.style.IPanelStyle;
import mcp.mobius.wdmla.api.ui.style.IProgressStyle;
import mcp.mobius.wdmla.api.ui.style.ITextStyle;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import mcp.mobius.wdmla.api.ui.IComponent;
import mcp.mobius.wdmla.api.ui.IDrawable;
import mcp.mobius.wdmla.api.ui.sizer.IPadding;
import mcp.mobius.wdmla.api.ui.sizer.ISize;
import mcp.mobius.wdmla.impl.ui.value.sizer.Area;

public abstract class Component implements IComponent {

    // settings
    protected IPadding padding;
    protected ISize size;

    // render
    protected IDrawable foreground;

    protected Component(IPadding padding, ISize size, IDrawable foreground) {
        this.padding = padding;
        this.size = size;
        this.foreground = foreground;
    }

    public Component padding(@NotNull IPadding padding) {
        this.padding = padding;
        return this;
    }

    public Component size(@NotNull ISize size) {
        this.size = size;
        return this;
    }

    @Override
    public void tick(int x, int y) {
        foreground.draw(new Area(x + padding.getLeft(), y + padding.getTop(), size.getW(), size.getH()));
    }

    @Override
    public int getWidth() {
        return padding.getLeft() + size.getW() + padding.getRight();
    }

    @Override
    public int getHeight() {
        return padding.getTop() + size.getH() + padding.getBottom();
    }
}
