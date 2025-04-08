package com.gtnewhorizons.wdmla.impl.ui.drawable;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;

import com.gtnewhorizons.wdmla.api.ui.IDrawable;
import com.gtnewhorizons.wdmla.api.ui.sizer.IArea;
import com.gtnewhorizons.wdmla.overlay.VanillaUIIcons;

public class ArmorDrawable implements IDrawable {

    private final int maxArmorsPerLine; // max armors fit in line
    private final float armor; // number of armors
    private final float maxArmor; // number of max armors

    public ArmorDrawable(int maxArmorsPerLine, float armor, float maxArmor) {
        this.maxArmorsPerLine = maxArmorsPerLine;
        this.armor = armor;
        this.maxArmor = maxArmor;
    }

    @Override
    public void draw(IArea area) {
        int nArmor = MathHelper.ceiling_float_int(maxArmor);
        int nArmorsPerLine = (int) (Math.min(maxArmorsPerLine, Math.ceil(maxArmor)));

        int offsetX = area.getX();
        int offsetY = area.getY();

        for (int iArmor = 1; iArmor <= nArmor; iArmor++) {

            if (iArmor <= MathHelper.floor_float(armor)) {
                GuiDraw.renderVanillaIcon(offsetX, offsetY, 8, 8, VanillaUIIcons.ARMOR_BG, Gui.icons);
                GuiDraw.renderVanillaIcon(offsetX, offsetY, 8, 8, VanillaUIIcons.ARMOR, Gui.icons);
                offsetX += 8;
            }

            if ((iArmor > armor) && (iArmor <= armor + 0.5f)) {
                GuiDraw.renderVanillaIcon(offsetX, offsetY, 8, 8, VanillaUIIcons.ARMOR_BG, Gui.icons);
                GuiDraw.renderVanillaIcon(offsetX, offsetY, 8, 8, VanillaUIIcons.HARMOR, Gui.icons);
                offsetX += 8;
            }

            if (iArmor > armor + 0.5f) {
                GuiDraw.renderVanillaIcon(offsetX, offsetY, 8, 8, VanillaUIIcons.EARMOR, Gui.icons);
                offsetX += 8;
            }

            if (iArmor % nArmorsPerLine == 0) {
                offsetY += 10;
                offsetX = area.getX();
            }
        }
    }
}
