package mcp.mobius.wdmla.test;

import mcp.mobius.waila.overlay.DisplayUtil;
import mcp.mobius.waila.utils.ModIdentification;
import mcp.mobius.wdmla.api.BlockAccessor;
import mcp.mobius.wdmla.api.IComponentProvider;
import mcp.mobius.wdmla.api.ui.ITooltip;
import net.minecraft.item.ItemStack;

import static mcp.mobius.waila.api.SpecialChars.BLUE;
import static mcp.mobius.waila.api.SpecialChars.ITALIC;

public class DefaultComponentProvider implements IComponentProvider<BlockAccessor> {
    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        ITooltip row = tooltip.horizontal();
        ItemStack itemStack = accessor.getItemForm();
        row.item(itemStack);

        ITooltip row_vertical = row.vertical();
        row_vertical.text(DisplayUtil.itemDisplayNameShort(itemStack));
        String modName = ModIdentification.nameFromStack(itemStack);
        if (modName != null && !modName.isEmpty()) {
            row_vertical.text(BLUE + ITALIC + modName);
        }
    }
}
