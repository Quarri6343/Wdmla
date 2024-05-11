package mcp.mobius.wdmla.impl;

import mcp.mobius.wdmla.api.IPanelStyle;

// required:borderdrawable
public class AbstractPanel extends Widget {

    protected IPanelStyle style;

    public AbstractPanel() {
        super(new BorderDrawable());
        style(new PanelStyle());
        size(new Size().w(100).h(12));
    }

    public AbstractPanel style(IPanelStyle style) {
        this.style = style;
        return this;
    }
}
