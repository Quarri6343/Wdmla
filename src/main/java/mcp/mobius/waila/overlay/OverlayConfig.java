package mcp.mobius.waila.overlay;

import com.gtnewhorizons.wdmla.impl.ui.DefaultThemes;
import mcp.mobius.waila.api.BackwardCompatibility;
import net.minecraftforge.common.config.Configuration;

import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.utils.Constants;

@BackwardCompatibility
public class OverlayConfig {

    public static int posX;
    public static int posY;
    public static int alpha;
    public static int bgcolor;
    public static int gradient1;
    public static int gradient2;
    public static int fontcolor;
    public static float scale;

    public static void updateColors() {
        OverlayConfig.alpha = (int) (ConfigHandler.instance()
                .getConfig(Configuration.CATEGORY_GENERAL, Constants.CFG_WAILA_ALPHA, 0) / 100.0f
                * 256) << 24;
        OverlayConfig.bgcolor = OverlayConfig.alpha
                + ConfigHandler.instance().getConfig(Configuration.CATEGORY_GENERAL, Constants.CFG_WAILA_BGCOLOR, 0);
        OverlayConfig.gradient1 = OverlayConfig.alpha
                + ConfigHandler.instance().getConfig(Configuration.CATEGORY_GENERAL, Constants.CFG_WAILA_GRADIENT1, 0);
        OverlayConfig.gradient2 = OverlayConfig.alpha
                + ConfigHandler.instance().getConfig(Configuration.CATEGORY_GENERAL, Constants.CFG_WAILA_GRADIENT2, 0);
        OverlayConfig.fontcolor = ConfigHandler.instance().getConfig(Configuration.CATEGORY_GENERAL, Constants.CFG_WAILA_FONTCOLOR, 0);

        DefaultThemes.CUSTOM.get().bgColor = OverlayConfig.bgcolor;
        DefaultThemes.CUSTOM.get().bgGradient1 = OverlayConfig.gradient1;
        DefaultThemes.CUSTOM.get().bgGradient2 = OverlayConfig.gradient2;
        DefaultThemes.CUSTOM.get().textColors._default = OverlayConfig.fontcolor;
    }
}
