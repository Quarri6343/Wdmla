package com.gtnewhorizons.wdmla.impl.ui.drawable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.gtnewhorizons.wdmla.api.ui.ColorPalette;
import com.gtnewhorizons.wdmla.api.ui.sizer.IArea;
import com.gtnewhorizons.wdmla.config.General;
import com.gtnewhorizons.wdmla.overlay.VanillaIconUI;

import mcp.mobius.waila.api.SpecialChars;
import mcp.mobius.waila.utils.WailaExceptionHandler;

public final class GuiDraw {

    private GuiDraw() {
        throw new AssertionError();
    }

    private static final int VANILLA_ITEM_SCALE = 16;

    private static final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
    private static final TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
    private static final RenderItem renderItem = new RenderItem();

    public static void drawString(String text, int x, int y, int colour, boolean shadow, float scale) {
        if (scale != 1) {
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glPushMatrix();
            GL11.glScalef(scale, scale, 1);
        }
        if (shadow) {
            fontRenderer.drawStringWithShadow(text, (int) (x / scale), (int) (y / scale), colour);
        } else {
            fontRenderer.drawString(text, x, y, colour);
        }

        if (scale != 1) {
            GL11.glPopMatrix();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        }
    }

    public static void renderStack(IArea area, ItemStack stack, boolean drawOverlay, String stackSizeOverride) {
        if (stack.getItem() == null) {
            drawString("Err", area.getX(), area.getY(), ColorPalette.INFO, true, 1);
            return;
        }

        int x = area.getX();
        int y = area.getY();
        int w = area.getW();
        int h = area.getH();

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);

        float xScale = (float) w / VANILLA_ITEM_SCALE;
        float yScale = (float) h / VANILLA_ITEM_SCALE;
        try {
            GL11.glPushMatrix();

            GL11.glScalef(xScale, yScale, 1);

            net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
            renderItem.renderItemAndEffectIntoGUI(
                    fontRenderer,
                    textureManager,
                    stack,
                    (int) (x / xScale),
                    (int) (y / yScale));
            if (drawOverlay) {
                if (stack.stackSize > 0) {
                    renderItem.renderItemOverlayIntoGUI(
                            fontRenderer,
                            textureManager,
                            stack,
                            (int) (x / xScale),
                            (int) (y / yScale),
                            stackSizeOverride);
                } else if (General.ghostProduct && !General.forceLegacy) {
                    renderItem.renderItemOverlayIntoGUI(
                            fontRenderer,
                            textureManager,
                            stack,
                            (int) (x / xScale),
                            (int) (y / yScale),
                            SpecialChars.YELLOW + stackSizeOverride);
                }
            }
            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
        } catch (Exception e) {
            String stackStr = stack.toString();
            WailaExceptionHandler.handleErr(e, "renderStack | " + stackStr, null);
        } finally {
            GL11.glPopMatrix();
        }

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    }

    public static void drawGradientRect(IArea area, int grad1, int grad2) {
        int x = area.getX();
        int y = area.getY();
        int w = area.getW();
        int h = area.getH();

        float zLevel = 0.0f;

        float f = (float) (grad1 >> 24 & 255) / 255.0F;
        float f1 = (float) (grad1 >> 16 & 255) / 255.0F;
        float f2 = (float) (grad1 >> 8 & 255) / 255.0F;
        float f3 = (float) (grad1 & 255) / 255.0F;
        float f4 = (float) (grad2 >> 24 & 255) / 255.0F;
        float f5 = (float) (grad2 >> 16 & 255) / 255.0F;
        float f6 = (float) (grad2 >> 8 & 255) / 255.0F;
        float f7 = (float) (grad2 & 255) / 255.0F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(f1, f2, f3, f);
        tessellator.addVertex(x + w, y, zLevel);
        tessellator.addVertex(x, y, zLevel);
        tessellator.setColorRGBA_F(f5, f6, f7, f4);
        tessellator.addVertex(x, y + h, zLevel);
        tessellator.addVertex(x + w, y + h, zLevel);
        tessellator.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public static void drawThickBeveledBox(IArea area, int thickness, int topleftcolor, int botrightcolor,
            int fillcolor) {
        int x1 = area.getX();
        int y1 = area.getY();
        int x2 = area.getEX();
        int y2 = area.getEY();

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

    public static void drawTexturedModelRect(int x, int y, int u, int v, int w, int h, int tw, int th) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        float zLevel = 0.0F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_F(1, 1, 1);
        tessellator.addVertexWithUV(x, y + h, zLevel, (u) * f, (v + th) * f1);
        tessellator.addVertexWithUV(x + w, y + h, zLevel, (u + tw) * f, (v + th) * f1);
        tessellator.addVertexWithUV(x + w, y, zLevel, (u + tw) * f, (v) * f1);
        tessellator.addVertexWithUV(x, y, zLevel, (u) * f, (v) * f1);
        tessellator.draw();
    }

    public static void renderVanillaIcon(int x, int y, int w, int h, VanillaIconUI icon) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(VanillaIconUI.PATH);

        if (icon == null) return;

        if (icon.bu != -1) {
            drawTexturedModelRect(x, y, icon.bu, icon.bv, w, h, icon.bsu, icon.bsv);
        }
        drawTexturedModelRect(x, y, icon.u, icon.v, w, h, icon.su, icon.sv);
    }
}
