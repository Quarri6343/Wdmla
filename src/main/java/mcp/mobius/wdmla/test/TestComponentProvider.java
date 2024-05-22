package mcp.mobius.wdmla.test;

import mcp.mobius.wdmla.api.IBlockAccessor;
import mcp.mobius.wdmla.api.IComponentProvider;
import mcp.mobius.wdmla.api.IServerDataProvider;
import mcp.mobius.wdmla.api.ui.IComponent;
import mcp.mobius.wdmla.impl.ui.component.*;
import mcp.mobius.wdmla.impl.ui.value.setting.TextStyle;
import mcp.mobius.wdmla.impl.ui.value.sizer.Size;
import net.minecraft.nbt.NBTTagCompound;

public class TestComponentProvider implements IComponentProvider<IBlockAccessor>, IServerDataProvider<IBlockAccessor> {

    @Override
    public void appendTooltip(IComponent rootComponent, IBlockAccessor accessor) {
        rootComponent.child(
                new VPanelComponent().child(new TextComponent("TEST FURNACE").style(new TextStyle().color(0xFFAA0000)))
                        .child(new ItemComponent(accessor.getItemForm()))
                        .child(new ProgressComponent(100, 200).size(new Size(50, 30))));
    }

    @Override
    public void appendServerData(NBTTagCompound data, IBlockAccessor accessor) {
        //TODO: write furnace NBT
    }
}
