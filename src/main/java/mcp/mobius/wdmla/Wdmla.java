package mcp.mobius.wdmla;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.wdmla.overlay.RayTracing;
import mcp.mobius.wdmla.api.Accessor;
import mcp.mobius.wdmla.api.IWdmlaPlugin;
import mcp.mobius.wdmla.impl.ObjectDataCenter;
import mcp.mobius.wdmla.impl.WdmlaClientRegistration;
import mcp.mobius.wdmla.impl.WdmlaCommonRegistration;
import mcp.mobius.wdmla.impl.ui.component.RootComponent;
import mcp.mobius.wdmla.test.TestPlugin;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

//TODO:move mod annotation
//TODO:Edit Readme for TOP and Jade credits
public class Wdmla {

    // @instance
    public static Wdmla instance = new Wdmla();

    public void loadComplete(FMLLoadCompleteEvent event) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            IWdmlaPlugin testPlugin = new TestPlugin();
            testPlugin.register(WdmlaCommonRegistration.instance());
            testPlugin.registerClient(WdmlaClientRegistration.instance());
        }
        // TODO: IMC
    }
}
