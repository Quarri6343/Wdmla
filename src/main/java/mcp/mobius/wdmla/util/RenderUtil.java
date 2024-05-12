package mcp.mobius.wdmla.util;

import mcp.mobius.waila.utils.WailaExceptionHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public enum RenderUtil {
    INSTANCE;

    private static final int VANILLA_ITEM_SCALE = 16;

    private static FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
    private static TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
    private static RenderItem renderItem = new RenderItem();

    public static void drawString(String text, int x, int y, int colour, boolean shadow) {
        if (shadow) fontRenderer.drawStringWithShadow(text, x, y, colour);
        else fontRenderer.drawString(text, x, y, colour);
    }

    public static void renderStack(int x, int y, ItemStack stack, int w, int h) {
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);

        float xScale = (float) w / VANILLA_ITEM_SCALE;
        float yScale = (float) h / VANILLA_ITEM_SCALE;
        try {
            GL11.glPushMatrix();

            GL11.glScalef(xScale, yScale, 1);

            RenderHelper.enableGUIStandardItemLighting();
            renderItem.renderItemAndEffectIntoGUI(
                    fontRenderer,
                    textureManager,
                    stack,
                    (int) (x / xScale),
                    (int) (y / yScale));
            renderItem.renderItemOverlayIntoGUI(
                    fontRenderer,
                    textureManager,
                    stack,
                    (int) (x / xScale),
                    (int) (y / yScale));
            RenderHelper.disableStandardItemLighting();
        } catch (Exception e) {
            String stackStr = stack != null ? stack.toString() : "NullStack";
            WailaExceptionHandler.handleErr(e, "renderStack | " + stackStr, null);
        } finally {
            GL11.glPopMatrix();
        }

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    }

    public static void drawThickBeveledBox(int x1, int y1, int x2, int y2, int thickness, int topleftcolor,
                                           int botrightcolor, int fillcolor) {
        if (fillcolor != -1) {
            Gui.drawRect(x1 + 1, y1 + 1, x2 - 1, y2 - 1, fillcolor);
        }

        Gui.drawRect(x1, y1, x2 - 1, y1 + thickness, topleftcolor);
        Gui.drawRect(x1, y1, x1 + thickness, y2 - 1, topleftcolor);
        Gui.drawRect(x2 - thickness, y1, x2, y2 - 1, botrightcolor);
        Gui.drawRect(x1, y2 - thickness, x2, y2, botrightcolor);
    }

    public static void drawVerticalLine(int x1, int y1, int y2, int color) {
        Gui.drawRect(x1, y1, x1 + 1, y2, color);
    }

    public static void drawHorizontalLine(int x1, int y1, int x2, int color) {
        Gui.drawRect(x1, y1, x2, y1 + 1, color);

    }
}
