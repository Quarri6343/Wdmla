package mcp.mobius.wdmla.impl.ui.widget;

import java.util.ArrayList;
import java.util.List;

import mcp.mobius.wdmla.api.ui.sizer.IPadding;
import mcp.mobius.wdmla.api.ui.sizer.ISize;
import mcp.mobius.wdmla.api.ui.style.IPanelStyle;
import mcp.mobius.wdmla.api.ui.IDrawable;
import mcp.mobius.wdmla.api.ui.IWidget;
import org.jetbrains.annotations.NotNull;

import mcp.mobius.wdmla.impl.ui.drawable.BorderDrawable;
import mcp.mobius.wdmla.impl.ui.value.setting.PanelStyle;
import mcp.mobius.wdmla.impl.ui.value.sizer.Padding;
import mcp.mobius.wdmla.impl.ui.value.sizer.Size;

public class PanelWidget extends Widget {

    public static final int DEFAULT_W = 100;
    public static final int DEFAULT_H = 12;

    protected final @NotNull IPanelStyle style;

    protected PanelWidget() {
        super(new ArrayList<>(), new Padding(), new Size(DEFAULT_W, DEFAULT_H), new BorderDrawable());
        this.style = new PanelStyle();
    }

    protected PanelWidget(List<IWidget> children, IPadding padding, ISize size, IDrawable foreground,
                          @NotNull IPanelStyle style) {
        super(children, padding, size, foreground);
        this.style = style;
    }

    public PanelWidget style(IPanelStyle style) {
        return new PanelWidget(children, padding, size, ((BorderDrawable) this.foreground).style(style), style);
    }

    @Override
    public PanelWidget padding(@NotNull IPadding padding) {
        return new PanelWidget(children, padding, size, foreground, style);
    }

    @Override
    public PanelWidget size(@NotNull ISize size) {
        return new PanelWidget(children, padding, size, foreground, style);
    }
}
