package mcp.mobius.wdmla.test;

import mcp.mobius.waila.overlay.DisplayUtil;
import mcp.mobius.waila.utils.ModIdentification;
import mcp.mobius.wdmla.api.IBlockAccessor;
import mcp.mobius.wdmla.api.IComponentProvider;
import mcp.mobius.wdmla.api.ui.IComponent;
import mcp.mobius.wdmla.impl.ui.component.HPanelComponent;
import mcp.mobius.wdmla.impl.ui.component.ItemComponent;
import mcp.mobius.wdmla.impl.ui.component.TextComponent;
import mcp.mobius.wdmla.impl.ui.component.VPanelComponent;

import static mcp.mobius.waila.api.SpecialChars.BLUE;
import static mcp.mobius.waila.api.SpecialChars.ITALIC;

public class DefaultComponentProvider implements IComponentProvider<IBlockAccessor> {
    @Override
    public void appendTooltip(IComponent rootComponent, IBlockAccessor accessor) {
        IComponent row = new HPanelComponent();
        row.child(new ItemComponent(accessor.getItemForm()));

        IComponent row_vertical = new VPanelComponent();
        row_vertical.child(
                new TextComponent(DisplayUtil.itemDisplayNameShort(accessor.getItemForm()))
        );
        String modName = ModIdentification.nameFromStack(accessor.getItemForm());
        if (modName != null && !modName.isEmpty()) {
            row_vertical.child(new TextComponent(BLUE + ITALIC + modName));
        }
        row.child(row_vertical);

        rootComponent.child(row);
    }
}
