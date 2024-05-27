package mcp.mobius.wdmla.test;

import mcp.mobius.wdmla.api.BlockAccessor;
import mcp.mobius.wdmla.api.IWdmlaCommonRegistration;
import mcp.mobius.wdmla.impl.BlockAccessorClientHandler;
import net.minecraft.block.Block;

import mcp.mobius.wdmla.api.IWdmlaClientRegistration;
import mcp.mobius.wdmla.api.IWdmlaPlugin;
import net.minecraft.block.BlockFurnace;

public class TestPlugin implements IWdmlaPlugin {

    @Override
    public void register(IWdmlaCommonRegistration registration) {
        registration.registerBlockDataProvider(new TestComponentProvider(), BlockFurnace.class);
    }

    @Override
    public void registerClient(IWdmlaClientRegistration registration) {
        registration.registerAccessorHandler(BlockAccessor.class, new BlockAccessorClientHandler());

        registration.registerBlockComponent(new DefaultComponentProvider(), Block.class);
        registration.registerBlockComponent(new TestComponentProvider(), BlockFurnace.class);
    }
}
