package mcp.mobius.wdmla.impl.ui.component;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

import mcp.mobius.wdmla.impl.ui.drawable.ItemDrawable;
import mcp.mobius.wdmla.impl.ui.value.sizer.Padding;
import mcp.mobius.wdmla.impl.ui.value.sizer.Size;

public class ItemComponent extends Component {

    public static final int DEFAULT_W = 16;
    public static final int DEFAULT_H = 16;

    public ItemComponent(ItemStack itemStack) {
        super(new ArrayList<>(), new Padding(), new Size(DEFAULT_W, DEFAULT_H), new ItemDrawable(itemStack));
    }
}
