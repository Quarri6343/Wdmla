package mcp.mobius.wdmla.impl.widget;

import mcp.mobius.wdmla.api.IPanelStyle;
import mcp.mobius.wdmla.impl.drawable.BorderDrawable;
import mcp.mobius.wdmla.impl.PanelStyle;
import mcp.mobius.wdmla.impl.Size;
import mcp.mobius.wdmla.impl.Widget;

public abstract class PanelWidget extends Widget {

    protected IPanelStyle style;

    public PanelWidget() {
        super(new BorderDrawable(), new Size(100, 12));
        style(new PanelStyle());
    }

    public PanelWidget style(IPanelStyle style) {
        this.style = style;
        ((BorderDrawable)foreGround).style(style);
        return this;
    }
}
