package mcp.mobius.wdmla.test;

import mcp.mobius.wdmla.api.IComponentProvider;
import mcp.mobius.wdmla.api.ui.IComponent;
import mcp.mobius.wdmla.impl.ui.component.*;
import mcp.mobius.wdmla.impl.ui.value.setting.TextStyle;
import mcp.mobius.wdmla.impl.ui.value.sizer.Size;
import mcp.mobius.wdmla.impl.value.BlockAccessor;

public class TestComponentProvider implements IComponentProvider<BlockAccessor> {
    @Override
    public void appendTooltip(IComponent rootComponent, BlockAccessor accessor) {
        rootComponent.child(
                new VPanelComponent()
                        .child(new TextComponent("TEST BLOCK").style(new TextStyle().color(0xFFAA0000)))
                        .child(new ItemComponent(accessor.getItemForm()))
                        .child(new ProgressComponent(100, 200).size(new Size(50, 30))));
    }
}
