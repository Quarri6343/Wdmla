package mcp.mobius.wdmla;

import com.google.common.eventbus.Subscribe;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.wdmla.api.IBlockAccessor;
import mcp.mobius.wdmla.api.IComponentProvider;
import mcp.mobius.wdmla.api.IWdmlaPlugin;
import mcp.mobius.wdmla.impl.ui.component.RootComponent;
import mcp.mobius.wdmla.impl.value.BlockAccessor;
import mcp.mobius.wdmla.impl.value.ObjectPlayerIsLookingAt;
import mcp.mobius.wdmla.impl.value.UnIdentifiedBlockPos;
import mcp.mobius.wdmla.test.TestPlugin;
import mcp.mobius.wdmla.util.OptionalUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Wdmla {

    //@instance
    public static Wdmla instance = new Wdmla();

    private static @Nullable RootComponent mainHUD = null;
    private static @Nullable IBlockAccessor lastLookingBlock = null;

    public void loadComplete(FMLLoadCompleteEvent event) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            IWdmlaPlugin testPlugin = new TestPlugin();
            testPlugin.registerClient(WdmlaClientRegistration.instance());
        }
        //TODO: IMC
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void tickRender(TickEvent.RenderTickEvent event) {
        if(mainHUD != null) {
            mainHUD.renderHUD();
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void tickClient(TickEvent.ClientTickEvent event) {
        if(!canShowHUD()) {
            mainHUD = null;
            lastLookingBlock = null;
            return;
        }

        Optional<UnIdentifiedBlockPos> lookingBlockPos = new ObjectPlayerIsLookingAt().getBlockPos();
        OptionalUtil.ifPresentOrElse(lookingBlockPos, block -> {
            BlockAccessor target = block.identify();
            if(target.isSameBlock(lastLookingBlock)) { //TODO: method inside Accessor to compare position
                return;
            }

            mainHUD = null;
            List<IComponentProvider<BlockAccessor>> providers = WdmlaClientRegistration.instance().getProviders(target.getBlock());
            if(!providers.isEmpty()) {
                RootComponent rootComponent = new RootComponent();
                for (IComponentProvider<BlockAccessor> provider : providers) {
                    provider.appendTooltip(rootComponent, target); //TODO: use AccessorHandlers
                }

                mainHUD = rootComponent;
                lastLookingBlock = target;
            }
        }, () -> mainHUD = null);
    }

    private static boolean canShowHUD() {
        Minecraft mc = Minecraft.getMinecraft();
        Optional<World> world = Optional.ofNullable(mc.theWorld);
        Optional<EntityPlayer> player = Optional.ofNullable(mc.thePlayer);
        Optional<GuiScreen> currentScreen = Optional.ofNullable(mc.currentScreen);

        return  OptionalUtil.isEmpty(currentScreen)
                && world.isPresent()
                && player.isPresent()
                && Minecraft.isGuiEnabled()
                && !mc.gameSettings.keyBindPlayerList.getIsKeyPressed()
                && ConfigHandler.instance().showTooltip();
    }
}
