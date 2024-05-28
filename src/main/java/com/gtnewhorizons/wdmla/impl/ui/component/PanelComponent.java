package com.gtnewhorizons.wdmla.impl.ui.component;

import java.util.ArrayList;

import com.gtnewhorizons.wdmla.impl.ui.drawable.BorderDrawable;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Padding;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Size;
import com.gtnewhorizons.wdmla.impl.ui.style.PanelStyle;
import org.jetbrains.annotations.NotNull;

import com.gtnewhorizons.wdmla.api.ui.style.IPanelStyle;

public abstract class PanelComponent extends TooltipComponent {

    public static final int DEFAULT_W = 100;
    public static final int DEFAULT_H = 12;

    protected @NotNull IPanelStyle style;

    protected PanelComponent() {
        super(new ArrayList<>(), new Padding(), new Size(DEFAULT_W, DEFAULT_H), new BorderDrawable());
        this.style = new PanelStyle();
    }

    public PanelComponent style(IPanelStyle style) {
        ((BorderDrawable) this.foreground).style(style);
        this.style = style;
        return this;
    }
}
