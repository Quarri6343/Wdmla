package com.gtnewhorizons.wdmla.overlay;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;

import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;

import com.gtnewhorizons.wdmla.api.Accessor;
import com.gtnewhorizons.wdmla.impl.ObjectDataCenter;
import com.gtnewhorizons.wdmla.impl.WDMlaClientRegistration;
import com.gtnewhorizons.wdmla.impl.ui.component.RootComponent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.client.KeyEvent;
import mcp.mobius.waila.utils.Constants;

public class WDMlaTickHandler {

    private static @Nullable RootComponent mainHUD = null;

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
        if (!Keyboard.isKeyDown(KeyEvent.key_show.getKeyCode())
                && !ConfigHandler.instance().getConfig(Configuration.CATEGORY_GENERAL, Constants.CFG_WAILA_MODE, false)
                && ConfigHandler.instance()
                        .getConfig(Configuration.CATEGORY_GENERAL, Constants.CFG_WAILA_SHOW, false)) {
            ConfigHandler.instance().setConfig(Configuration.CATEGORY_GENERAL, Constants.CFG_WAILA_SHOW, false);
        }

        Minecraft mc = Minecraft.getMinecraft();
        World world = mc.theWorld;
        EntityPlayer player = mc.thePlayer;
        GuiScreen currentScreen = mc.currentScreen;

        if (world == null || player == null
                || !Minecraft.isGuiEnabled()
                || currentScreen != null
                || mc.gameSettings.keyBindPlayerList.getIsKeyPressed()
                || !ConfigHandler.instance().showTooltip()) {
            mainHUD = null;
            return;
        }

        RayTracing.instance().fire();
        MovingObjectPosition target = RayTracing.instance().getTarget();

        if (target == null) {
            mainHUD = null;
            return;
        }

        Accessor accessor = null;

        if (target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            Block block = world.getBlock(target.blockX, target.blockY, target.blockZ);
            TileEntity tileEntity = world.getTileEntity(target.blockX, target.blockY, target.blockZ);
            int metadata = world.getBlockMetadata(target.blockX, target.blockY, target.blockZ);
            ItemStack itemForm = RayTracing.instance().getIdentifierStack();
            accessor = WDMlaClientRegistration.instance().blockAccessor().block(block).tileEntity(tileEntity)
                    .meta(metadata).hit(target).itemForm(itemForm).requireVerification().build();
        } else if (target.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
            /* off */
            accessor = WDMlaClientRegistration.instance().entityAccessor().hit(target).entity(target.entityHit)
                    .requireVerification().build();
            /* on */
        }

        ObjectDataCenter.set(accessor);
        if (accessor == null || accessor.getHitResult() == null) {
            mainHUD = null;
            return;
        }

        mainHUD = handle(accessor);
    }

    public RootComponent handle(Accessor accessor) {
        RootComponent root = new RootComponent();

        var handler = WDMlaClientRegistration.instance().getAccessorHandler(accessor.getAccessorType());
        if (!handler.shouldDisplay(accessor)) {
            return null;
        }

        if (accessor.isServerConnected()) {
            if (accessor.getServerData() != null && !accessor.verifyData(accessor.getServerData())) {
                accessor.getServerData().func_150296_c().clear();
            }
            boolean request = handler.shouldRequestData(accessor);
            if (ObjectDataCenter.isTimeElapsed(ObjectDataCenter.rateLimiter)) {
                ObjectDataCenter.resetTimer();
                if (request) {
                    handler.requestData(accessor);
                }
            }
            if (request && ObjectDataCenter.getServerData() == null) {
                return null;
            }
        } else {
            return null;
        }

        handler.gatherComponents(accessor, $ -> root);

        return root;
    }
}
