package mcp.mobius.wdmla.util;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import mcp.mobius.waila.overlay.OverlayConfig;

public class GLStateHelper {

    private enum HUDGLState {
        BEFORE_BG,
        BEFORE_FG,
        AFTER_FG
    }

    private static HUDGLState state = HUDGLState.BEFORE_BG;
    private static boolean hasBlending;
    private static boolean hasDepthTest;
    private static int boundTexIndex;

    private GLStateHelper() {
        throw new AssertionError();
    }

    public static void save() {
        hasBlending = GL11.glGetBoolean(GL11.GL_BLEND);
        hasDepthTest = GL11.glGetBoolean(GL11.GL_DEPTH_TEST);
        boundTexIndex = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
        GL11.glPushAttrib(GL11.GL_CURRENT_BIT);
    }

    public static void load() {
        if (hasBlending) GL11.glEnable(GL11.GL_BLEND);
        else GL11.glDisable(GL11.GL_BLEND);
        if (hasDepthTest) GL11.glEnable(GL11.GL_DEPTH_TEST);
        else GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, boundTexIndex);
        GL11.glPopAttrib();
    }

    public static void prepareBGDraw() {
        if (state != HUDGLState.BEFORE_BG) {
            throw new AssertionError("Illegal GL State");
        }

        GL11.glPushMatrix();
        save();

        GL11.glScalef(OverlayConfig.scale, OverlayConfig.scale, 1.0f);

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        state = HUDGLState.BEFORE_FG;
    }

    public static void prepareFGDraw() {
        if (state != HUDGLState.BEFORE_FG) {
            throw new AssertionError("Illegal GL State");
        }

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        state = HUDGLState.AFTER_FG;
    }

    public static void endDraw() {
        if (state != HUDGLState.AFTER_FG) {
            throw new AssertionError("Illegal GL State");
        }

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        load();
        GL11.glPopMatrix();

        state = HUDGLState.BEFORE_BG;
    }
}
