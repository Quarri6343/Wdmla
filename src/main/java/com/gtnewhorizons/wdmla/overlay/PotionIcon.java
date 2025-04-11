package com.gtnewhorizons.wdmla.overlay;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

/**
 * Drawable potion sprite IIcon data calculated from potion effect class.
 * @see net.minecraft.client.renderer.InventoryEffectRenderer
 */
public class PotionIcon implements IIcon {

    public static final ResourceLocation PATH = new ResourceLocation("textures/gui/container/inventory.png");;

    private final int iconIndex;

    public PotionIcon(PotionEffect potionEffect) {
        this.iconIndex = Potion.potionTypes[potionEffect.getPotionID()].getStatusIconIndex();
    }

    @Override
    public float getMinU() {
        return iconIndex % 8 * 18;
    }

    @Override
    public float getMaxU() {
        return (iconIndex % 8 * 18) + 18;
    }

    @Override
    public float getInterpolatedU(double interporation) {
        return iconIndex % 8 * 18 + 18 * (float)interporation;
    }

    @Override
    public float getMinV() {
        return 198 + (int) (iconIndex / 8) * 18;
    }

    @Override
    public float getMaxV() {
        return 198 + (int) (iconIndex / 8) * 18 + 18;
    }

    @Override
    public float getInterpolatedV(double interporation) {
        return 198 + (int) (iconIndex / 8) * 18 + 18 * (float)interporation;
    }

    @Override
    public String getIconName() {
        return "potion_" + iconIndex;
    }

    @Override
    public int getIconWidth() {
        return 18;
    }

    @Override
    public int getIconHeight() {
        return 18;
    }
}