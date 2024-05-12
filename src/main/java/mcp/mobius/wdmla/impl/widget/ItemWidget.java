package mcp.mobius.wdmla.impl.widget;

import mcp.mobius.wdmla.api.IDrawable;
import mcp.mobius.wdmla.api.IHUDWidget;
import mcp.mobius.wdmla.api.IPadding;
import mcp.mobius.wdmla.api.ISize;
import mcp.mobius.wdmla.impl.Padding;
import mcp.mobius.wdmla.impl.Size;
import mcp.mobius.wdmla.impl.Widget;
import mcp.mobius.wdmla.impl.drawable.ItemDrawable;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemWidget extends Widget {
    public static final int DEFAULT_W = 16;
    public static final int DEFAULT_H = 16;

    public ItemWidget(ItemStack itemStack) {
        super(new ArrayList<>(), new Padding(), new Size(DEFAULT_W, DEFAULT_H), new ItemDrawable(itemStack));
    }

    private ItemWidget(List<IHUDWidget> children, IPadding padding, ISize textSize, IDrawable foreground) {
        super(children, padding, textSize, foreground);
    }

    @Override
    public ItemWidget padding(IPadding padding) {
        return new ItemWidget(children, padding, size, foreGround);
    }

    @Override
    public ItemWidget size(ISize size) {
        return new ItemWidget(children, padding, size, foreGround);
    }
}
