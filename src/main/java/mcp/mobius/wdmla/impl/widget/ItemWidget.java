package mcp.mobius.wdmla.impl.widget;

import mcp.mobius.wdmla.impl.drawable.ItemDrawable;
import mcp.mobius.wdmla.impl.Size;
import mcp.mobius.wdmla.impl.Widget;
import net.minecraft.item.ItemStack;

public class ItemWidget extends Widget {
    public static final int DEFAULT_W = 16;
    public static final int DEFAULT_H = 16;

    public ItemWidget(ItemStack itemStack) {
        super(new ItemDrawable(itemStack), new Size(DEFAULT_W, DEFAULT_H));
    }
}
