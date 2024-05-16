package mcp.mobius.wdmla;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.wdmla.api.IIdentifiedBlock;
import mcp.mobius.wdmla.impl.values.ObjectPlayerIsLookingAt;
import mcp.mobius.wdmla.impl.values.UnIdentifiedBlockPos;
import mcp.mobius.wdmla.impl.values.setting.TextStyle;
import mcp.mobius.wdmla.impl.values.sizer.Size;
import mcp.mobius.wdmla.impl.widget.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class Wdmla {

    //@instance
    public static Wdmla instance = new Wdmla();

    private static Optional<RootWidget> mainHUD = Optional.empty();

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void tickRender(TickEvent.RenderTickEvent event) {
        mainHUD.ifPresent(RootWidget::renderHUD);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void tickClient(TickEvent.ClientTickEvent event) {
        mainHUD = Optional.empty();
        if(!canShowHUD()) {
            return;
        }

        Optional<UnIdentifiedBlockPos> lookingBlockPos = new ObjectPlayerIsLookingAt().getBlockPos();
        lookingBlockPos.ifPresent(block -> {
            IIdentifiedBlock target = block.identify();
            mainHUD = Optional.of(getTestWidget(target.getItemForm()));
        });
    }

    private static boolean canShowHUD() {
        Minecraft mc = Minecraft.getMinecraft();
        Optional<World> world = Optional.ofNullable(mc.theWorld);
        Optional<EntityPlayer> player = Optional.ofNullable(mc.thePlayer);
        Optional<GuiScreen> currentScreen = Optional.ofNullable(mc.currentScreen);

        return  currentScreen.isPresent()
                && world.isPresent()
                && player.isPresent()
                && Minecraft.isGuiEnabled()
                && !mc.gameSettings.keyBindPlayerList.getIsKeyPressed()
                && ConfigHandler.instance().showTooltip();
    }

    private @NotNull RootWidget getTestWidget(ItemStack targetStack) {
        return new RootWidget().child(
                new VPanelWidget()
                        .child(new TextWidget("TEST BLOCK").style(new TextStyle().color(0xFFAA0000)))
                        .child(new ItemWidget(targetStack))
                        .child(new ProgressWidget(100, 200).size(new Size(50, 30))));
    }
}
