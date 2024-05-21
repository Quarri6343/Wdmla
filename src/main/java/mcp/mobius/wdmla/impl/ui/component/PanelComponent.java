package mcp.mobius.wdmla.impl.ui.component;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import mcp.mobius.wdmla.api.ui.IComponent;
import mcp.mobius.wdmla.api.ui.IDrawable;
import mcp.mobius.wdmla.api.ui.sizer.IPadding;
import mcp.mobius.wdmla.api.ui.sizer.ISize;
import mcp.mobius.wdmla.api.ui.style.IPanelStyle;
import mcp.mobius.wdmla.impl.ui.drawable.BorderDrawable;
import mcp.mobius.wdmla.impl.ui.value.setting.PanelStyle;
import mcp.mobius.wdmla.impl.ui.value.sizer.Padding;
import mcp.mobius.wdmla.impl.ui.value.sizer.Size;

public class PanelComponent extends Component {

    public static final int DEFAULT_W = 100;
    public static final int DEFAULT_H = 12;

    protected final @NotNull IPanelStyle style;

    protected PanelComponent() {
        super(new ArrayList<>(), new Padding(), new Size(DEFAULT_W, DEFAULT_H), new BorderDrawable());
        this.style = new PanelStyle();
    }

    protected PanelComponent(List<IComponent> children, IPadding padding, ISize size, IDrawable foreground,
            @NotNull IPanelStyle style) {
        super(children, padding, size, foreground);
        this.style = style;
    }

    public PanelComponent style(IPanelStyle style) {
        return new PanelComponent(children, padding, size, ((BorderDrawable) this.foreground).style(style), style);
    }

    @Override
    public PanelComponent padding(@NotNull IPadding padding) {
        return new PanelComponent(children, padding, size, foreground, style);
    }

    @Override
    public PanelComponent size(@NotNull ISize size) {
        return new PanelComponent(children, padding, size, foreground, style);
    }
}
