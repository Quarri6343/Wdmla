package mcp.mobius.wdmla;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import mcp.mobius.waila.Waila;
import mcp.mobius.waila.api.IWailaBlock;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.impl.DataAccessorCommon;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import mcp.mobius.waila.cbcore.Layout;
import mcp.mobius.waila.gui.interfaces.IWidget;
import mcp.mobius.waila.network.Message0x01TERequest;
import mcp.mobius.waila.network.WailaPacketHandler;
import mcp.mobius.waila.utils.Constants;
import mcp.mobius.waila.utils.WailaExceptionHandler;
import mcp.mobius.wdmla.impl.BlockAccessorClientHandler;
import mcp.mobius.wdmla.impl.WdmlaClientRegistration;
import mcp.mobius.wdmla.impl.WdmlaCommonRegistration;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import net.minecraftforge.common.config.Configuration;
import org.jetbrains.annotations.Nullable;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.wdmla.api.IBlockAccessor;
import mcp.mobius.wdmla.api.IWdmlaPlugin;
import mcp.mobius.wdmla.impl.ui.component.RootComponent;
import mcp.mobius.wdmla.impl.value.BlockAccessor;
import mcp.mobius.wdmla.impl.value.ObjectPlayerIsLookingAt;
import mcp.mobius.wdmla.impl.value.UnIdentifiedBlockPos;
import mcp.mobius.wdmla.test.TestPlugin;
import mcp.mobius.wdmla.util.OptionalUtil;

public class Wdmla {

    // @instance
    public static Wdmla instance = new Wdmla();

    private static @Nullable RootComponent mainHUD = null;

    public void loadComplete(FMLLoadCompleteEvent event) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            IWdmlaPlugin testPlugin = new TestPlugin();
            testPlugin.register(WdmlaCommonRegistration.instance());
            testPlugin.registerClient(WdmlaClientRegistration.instance());
        }
        // TODO: IMC
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void tickRender(TickEvent.RenderTickEvent event) {
        if (mainHUD != null) {
            mainHUD.renderHUD();
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void tickClient(TickEvent.ClientTickEvent event) {
        if (!canShowHUD()) {
            mainHUD = null;
            return;
        }

        Optional<UnIdentifiedBlockPos> lookingBlockPos = new ObjectPlayerIsLookingAt().getBlockPos();
        OptionalUtil.ifPresentOrElse(lookingBlockPos, block -> {
            BlockAccessor target = block.identify();
            //TODO: if a player looking a same block, don't request new Info until TE request occurs
            mainHUD = BlockAccessorClientHandler.INSTANCE.handle(target);
        }, () -> mainHUD = null);
    }

    //put appendtooltip method here

    private static boolean canShowHUD() {
        Minecraft mc = Minecraft.getMinecraft();
        Optional<World> world = Optional.ofNullable(mc.theWorld);
        Optional<EntityPlayer> player = Optional.ofNullable(mc.thePlayer);
        Optional<GuiScreen> currentScreen = Optional.ofNullable(mc.currentScreen);

        return OptionalUtil.isEmpty(currentScreen) && world.isPresent()
                && player.isPresent()
                && Minecraft.isGuiEnabled()
                && !mc.gameSettings.keyBindPlayerList.getIsKeyPressed()
                && ConfigHandler.instance().showTooltip();
    }
}
