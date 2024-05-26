package mcp.mobius.wdmla.test;

import mcp.mobius.waila.overlay.DisplayUtil;
import mcp.mobius.waila.utils.ModIdentification;
import mcp.mobius.wdmla.api.BlockAccessor;
import mcp.mobius.wdmla.api.IComponentProvider;
import mcp.mobius.wdmla.api.ui.IComponent;
import mcp.mobius.wdmla.impl.ui.component.HPanelComponent;
import mcp.mobius.wdmla.impl.ui.component.ItemComponent;
import mcp.mobius.wdmla.impl.ui.component.TextComponent;
import mcp.mobius.wdmla.impl.ui.component.VPanelComponent;
import net.minecraft.item.ItemStack;

import static mcp.mobius.waila.api.SpecialChars.BLUE;
import static mcp.mobius.waila.api.SpecialChars.ITALIC;

public class DefaultComponentProvider implements IComponentProvider<BlockAccessor> {
    @Override
    public void appendTooltip(IComponent rootComponent, BlockAccessor accessor) {
        IComponent row = new HPanelComponent();
        ItemStack itemStack = accessor.getItemForm();
        row.child(new ItemComponent(itemStack));

        IComponent row_vertical = new VPanelComponent();
        row_vertical.child(
                new TextComponent(DisplayUtil.itemDisplayNameShort(itemStack))
        );
        String modName = ModIdentification.nameFromStack(itemStack);
        if (modName != null && !modName.isEmpty()) {
            row_vertical.child(new TextComponent(BLUE + ITALIC + modName));
        }
        row.child(row_vertical);

        rootComponent.child(row);
    }
}
