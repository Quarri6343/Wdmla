package mcp.mobius.wdmla.impl.widget;

import mcp.mobius.wdmla.api.*;
import mcp.mobius.wdmla.impl.Padding;
import mcp.mobius.wdmla.impl.drawable.BorderDrawable;
import mcp.mobius.wdmla.impl.PanelStyle;
import mcp.mobius.wdmla.impl.Size;
import mcp.mobius.wdmla.impl.Widget;
import mcp.mobius.wdmla.impl.drawable.ItemDrawable;
import mcp.mobius.wdmla.impl.drawable.TextDrawable;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PanelWidget extends Widget {
    public static final int DEFAULT_W = 100;
    public static final int DEFAULT_H = 12;

    protected IPanelStyle style;

    protected PanelWidget() {
        super(new ArrayList<>(), new Padding(), new Size(DEFAULT_W, DEFAULT_H), new BorderDrawable());
        this.style = new PanelStyle();
        ((BorderDrawable) foreGround).style(style);
    }

    protected PanelWidget(List<IHUDWidget> children, IPadding padding, ISize size, IDrawable foreground, IPanelStyle style) {
        super(children, padding, size, foreground);
        this.style = style;
        ((BorderDrawable) foreGround).style(style);
    }

    public PanelWidget style(IPanelStyle style) {
        return new PanelWidget(children, padding, size, foreGround, style);
    }

    @Override
    public PanelWidget padding(IPadding padding) {
        return new PanelWidget(children, padding, size, foreGround, style);
    }

    @Override
    public PanelWidget size(ISize size) {
        return new PanelWidget(children, padding, size, foreGround, style);
    }
}
