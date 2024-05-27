package mcp.mobius.wdmla.impl.ui.component;

import java.util.ArrayList;
import java.util.List;

import mcp.mobius.wdmla.api.ui.ITooltip;
import mcp.mobius.wdmla.api.ui.style.IProgressStyle;
import mcp.mobius.wdmla.api.ui.style.ITextStyle;
import net.minecraft.item.ItemStack;
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
