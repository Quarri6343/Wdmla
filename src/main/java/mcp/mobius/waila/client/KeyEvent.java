package mcp.mobius.waila.client;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.config.Configuration;

import org.lwjgl.input.Keyboard;

import com.gtnewhorizon.gtnhlib.config.ConfigException;
import com.gtnewhorizons.wdmla.api.Mods;
import com.gtnewhorizons.wdmla.gui.ModsMenuScreenConfig;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.handlers.nei.NEIHandler;
import mcp.mobius.waila.utils.Constants;
import mcp.mobius.waila.utils.WailaExceptionHandler;

/**
 * Handles {@link KeyBinding} and its events.<br>
 * Cannot reimplement this on WDMla since Waila makes this public to its addons.
 */
public class KeyEvent {

    public static KeyBinding key_cfg;
    public static KeyBinding key_show;
    public static KeyBinding key_liquid;
    public static KeyBinding key_recipe;
    public static KeyBinding key_usage;
    public static KeyBinding key_details;

    public KeyEvent() {
        ClientRegistry
                .registerKeyBinding(key_cfg = new KeyBinding(Constants.BIND_WAILA_CFG, Keyboard.KEY_NUMPAD0, "Waila"));
        ClientRegistry.registerKeyBinding(
                key_show = new KeyBinding(Constants.BIND_WAILA_SHOW, Keyboard.KEY_NUMPAD1, "Waila"));
        ClientRegistry.registerKeyBinding(
                key_liquid = new KeyBinding(Constants.BIND_WAILA_LIQUID, Keyboard.KEY_NUMPAD2, "Waila"));
        ClientRegistry.registerKeyBinding(
                key_recipe = new KeyBinding(Constants.BIND_WAILA_RECIPE, Keyboard.KEY_NUMPAD3, "Waila"));
        ClientRegistry.registerKeyBinding(
                key_usage = new KeyBinding(Constants.BIND_WAILA_USAGE, Keyboard.KEY_NUMPAD4, "Waila"));
        ClientRegistry.registerKeyBinding(
                key_details = new KeyBinding(Constants.BIND_WAILA_DETAILS, Keyboard.KEY_LSHIFT, "WDMla"));
    }

    @SubscribeEvent
    public void onKeyEvent(KeyInputEvent event) {
        boolean showKey = key_show.isPressed();
        if (key_cfg.isPressed()) {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.currentScreen == null) {
                try {
                    mc.displayGuiScreen(new ModsMenuScreenConfig(null));
                } catch (ConfigException e) {
                    WailaExceptionHandler.handleErr(e, KeyEvent.class.getName(), new ArrayList<>());
                }
            }
            return;
        }
        if (showKey && ConfigHandler.instance()
                .getConfig(Configuration.CATEGORY_GENERAL, Constants.CFG_WAILA_MODE, false)) {
            boolean status = ConfigHandler.instance()
                    .getConfig(Configuration.CATEGORY_GENERAL, Constants.CFG_WAILA_SHOW, true);
            ConfigHandler.instance().setConfig(Configuration.CATEGORY_GENERAL, Constants.CFG_WAILA_SHOW, !status);
        } else if (showKey && !ConfigHandler.instance()
                .getConfig(Configuration.CATEGORY_GENERAL, Constants.CFG_WAILA_MODE, false)) {
                    ConfigHandler.instance().setConfig(Configuration.CATEGORY_GENERAL, Constants.CFG_WAILA_SHOW, true);
                } else
            if (key_liquid.isPressed()) {
                boolean status = ConfigHandler.instance()
                        .getConfig(Configuration.CATEGORY_GENERAL, Constants.CFG_WAILA_LIQUID, true);
                ConfigHandler.instance().setConfig(Configuration.CATEGORY_GENERAL, Constants.CFG_WAILA_LIQUID, !status);
            } else if (key_recipe.isPressed()) {
                if (Mods.NOTENOUGHITEMS.isLoaded()) {
                    NEIHandler.openRecipeGUI(true);
                }
            } else if (key_usage.isPressed()) {
                if (Mods.NOTENOUGHITEMS.isLoaded()) {
                    NEIHandler.openRecipeGUI(false);
                }
            }
    }
}
