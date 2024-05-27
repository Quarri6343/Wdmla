package mcp.mobius.wdmla.test;

import mcp.mobius.wdmla.api.BlockAccessor;
import mcp.mobius.wdmla.api.IComponentProvider;
import mcp.mobius.wdmla.api.IServerDataProvider;
import mcp.mobius.wdmla.api.ui.IComponent;
import mcp.mobius.wdmla.api.ui.ITooltip;
import mcp.mobius.wdmla.impl.ui.component.*;
import mcp.mobius.wdmla.impl.ui.value.setting.TextStyle;
import mcp.mobius.wdmla.impl.ui.value.sizer.Size;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Random;

public class TestComponentProvider implements IComponentProvider<BlockAccessor>, IServerDataProvider<BlockAccessor> {

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        tooltip.horizontal()
                .child(new TextComponent("TEST FURNACE").style(new TextStyle().color(0xFFAA0000)))
                .child(new ItemComponent(accessor.getItemForm()));
        tooltip.child(new ProgressComponent(100, 200).size(new Size(50, 10)));

        int random = accessor.getServerData().getInteger("random");
        if(random != 0) {
            tooltip.child(new TextComponent("Recieved Server Data: " + random));
        }
    }

    @Override
    public void appendServerData(NBTTagCompound data, BlockAccessor accessor) {
        data.setInteger("random", new Random().nextInt(11));
    }
}
