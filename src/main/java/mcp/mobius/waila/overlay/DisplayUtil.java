package mcp.mobius.waila.overlay;

import static mcp.mobius.waila.api.SpecialChars.patternMinecraft;
import static mcp.mobius.waila.api.SpecialChars.patternRender;
import static mcp.mobius.waila.api.SpecialChars.patternWaila;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import mcp.mobius.waila.utils.WailaExceptionHandler;

public class DisplayUtil {

    private static FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
    private static TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
    private static RenderItem renderItem = new RenderItem();

    public static String stripSymbols(String s) {
        String result = patternRender.matcher(s).replaceAll("");
        result = patternMinecraft.matcher(result).replaceAll("");
        result = patternWaila.matcher(result).replaceAll("");
        return result;
    }

    public static String stripWailaSymbols(String s) {
        String result = patternRender.matcher(s).replaceAll("");
        result = patternWaila.matcher(result).replaceAll("");
        return result;
    }

    public static void renderStack(int x, int y, ItemStack stack) {
        enable3DRender();
        try {
            renderItem.renderItemAndEffectIntoGUI(fontRenderer, textureManager, stack, x, y);
            renderItem.renderItemOverlayIntoGUI(fontRenderer, textureManager, stack, x, y);
        } catch (Exception e) {
            String stackStr = stack != null ? stack.toString() : "NullStack";
            WailaExceptionHandler.handleErr(e, "renderStack | " + stackStr, null);
        }
        enable2DRender();
    }

    public static void enable3DRender() {
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public static void enable2DRender() {
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    }

    public static List<String> itemDisplayNameMultiline(ItemStack itemstack) {
        List<String> namelist = null;
        try {
            namelist = itemstack.getTooltip(
                    Minecraft.getMinecraft().thePlayer,
                    Minecraft.getMinecraft().gameSettings.advancedItemTooltips);
        } catch (Throwable ignored) {}

        if (namelist == null) namelist = new ArrayList<>();

        if (namelist.isEmpty()) namelist.add("Unnamed");

        if (namelist.get(0) == null || namelist.get(0).isEmpty()) namelist.set(0, "Unnamed");

        String rarityColor = itemstack.getItem() != null ? itemstack.getRarity().rarityColor.toString() : "" ;
        namelist.set(0, rarityColor + namelist.get(0));
        for (int i = 1; i < namelist.size(); i++) namelist.set(i, "\u00a77" + namelist.get(i));

        return namelist;
    }

    public static String itemDisplayNameShort(ItemStack itemstack) {
        List<String> list = itemDisplayNameMultiline(itemstack);
        return list.get(0);
    }
}
