package mcp.mobius.wdmla.test;

import mcp.mobius.wdmla.api.BlockAccessor;
import mcp.mobius.wdmla.api.IComponentProvider;
import mcp.mobius.wdmla.api.IServerDataProvider;
import mcp.mobius.wdmla.api.ui.IComponent;
import mcp.mobius.wdmla.impl.ui.component.*;
import mcp.mobius.wdmla.impl.ui.value.setting.TextStyle;
import mcp.mobius.wdmla.impl.ui.value.sizer.Size;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Random;

public class TestComponentProvider implements IComponentProvider<BlockAccessor>, IServerDataProvider<BlockAccessor> {

    @Override
    public void appendTooltip(IComponent rootComponent, BlockAccessor accessor) {
        IComponent main = new VPanelComponent().child(new TextComponent("TEST FURNACE").style(new TextStyle().color(0xFFAA0000)))
                .child(new ItemComponent(accessor.getItemForm()))
                .child(new ProgressComponent(100, 200).size(new Size(50, 10)));

        int random = accessor.getServerData().getInteger("random");
        if(random != 0) {
            main.child(new TextComponent("Recieved Server Data: " + random));
        }

        rootComponent.child(main);
    }

    @Override
    public void appendServerData(NBTTagCompound data, BlockAccessor accessor) {
        data.setInteger("random", new Random().nextInt(11));
    }
}
