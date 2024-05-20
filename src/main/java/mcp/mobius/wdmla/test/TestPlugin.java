package mcp.mobius.wdmla.test;

import mcp.mobius.wdmla.api.IWdmlaClientRegistration;
import mcp.mobius.wdmla.api.IWdmlaPlugin;
import net.minecraft.block.Block;

public class TestPlugin implements IWdmlaPlugin {

    @Override
    public void registerClient(IWdmlaClientRegistration registration) {
        registration.registerBlockComponent(new TestComponentProvider(), Block.class);
    }
}
