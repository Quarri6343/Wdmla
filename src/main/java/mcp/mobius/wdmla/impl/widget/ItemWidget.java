package mcp.mobius.wdmla.impl.widget;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import mcp.mobius.wdmla.api.IDrawable;
import mcp.mobius.wdmla.api.IWidget;
import mcp.mobius.wdmla.api.sizer.IPadding;
import mcp.mobius.wdmla.api.sizer.ISize;
import mcp.mobius.wdmla.impl.drawable.ItemDrawable;
import mcp.mobius.wdmla.impl.values.sizer.Padding;
import mcp.mobius.wdmla.impl.values.sizer.Size;

public class ItemWidget extends Widget {

    public static final int DEFAULT_W = 16;
    public static final int DEFAULT_H = 16;

    public ItemWidget(ItemStack itemStack) {
        super(new ArrayList<>(), new Padding(), new Size(DEFAULT_W, DEFAULT_H), new ItemDrawable(itemStack));
    }

    private ItemWidget(List<IWidget> children, IPadding padding, ISize textSize, IDrawable foreground) {
        super(children, padding, textSize, foreground);
    }

    @Override
    public ItemWidget padding(@NotNull IPadding padding) {
        return new ItemWidget(children, padding, size, foreground);
    }

    @Override
    public ItemWidget size(@NotNull ISize size) {
        return new ItemWidget(children, padding, size, foreground);
    }
}
