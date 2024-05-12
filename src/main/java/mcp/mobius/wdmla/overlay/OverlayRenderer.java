package mcp.mobius.wdmla.overlay;

import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.overlay.OverlayConfig;
import mcp.mobius.wdmla.impl.widget.RootWidget;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class OverlayRenderer {

    protected static boolean hasBlending;
    protected static boolean hasLight;
    protected static boolean hasDepthTest;
    protected static boolean hasLight1;
    protected static int boundTexIndex;

    private static boolean canShowOverlay() {
        Minecraft mc = Minecraft.getMinecraft();
        return mc.currentScreen == null && mc.theWorld != null
                && Minecraft.isGuiEnabled()
                && !mc.gameSettings.keyBindPlayerList.getIsKeyPressed()
                && ConfigHandler.instance().showTooltip();
    }

    public static void renderOverlay(RootWidget root) {
        if(!canShowOverlay()) {
            return;
        }

        GL11.glPushMatrix();
        saveGLState();

        GL11.glScalef(OverlayConfig.scale, OverlayConfig.scale, 1.0f);

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        root.preTick();

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        root.tick();

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        loadGLState();
        GL11.glPopMatrix();
    }

    public static void saveGLState() {
        hasBlending = GL11.glGetBoolean(GL11.GL_BLEND);
        hasLight = GL11.glGetBoolean(GL11.GL_LIGHTING);
        hasDepthTest = GL11.glGetBoolean(GL11.GL_DEPTH_TEST);
        boundTexIndex = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
        GL11.glPushAttrib(GL11.GL_CURRENT_BIT);
    }

    public static void loadGLState() {
        if (hasBlending) GL11.glEnable(GL11.GL_BLEND);
        else GL11.glDisable(GL11.GL_BLEND);
        if (hasLight1) GL11.glEnable(GL11.GL_LIGHT1);
        else GL11.glDisable(GL11.GL_LIGHT1);
        if (hasDepthTest) GL11.glEnable(GL11.GL_DEPTH_TEST);
        else GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, boundTexIndex);
        GL11.glPopAttrib();
    }
}
