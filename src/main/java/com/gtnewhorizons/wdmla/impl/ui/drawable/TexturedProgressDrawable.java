package com.gtnewhorizons.wdmla.impl.ui.drawable;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizons.wdmla.api.ui.IDrawable;
import com.gtnewhorizons.wdmla.api.ui.IProgress;
import com.gtnewhorizons.wdmla.api.ui.sizer.IArea;

public class TexturedProgressDrawable implements IDrawable {

    public static final int TEXTURE_W = 32;
    public static final int TEXTURE_H = 16;

    private final Minecraft mc = Minecraft.getMinecraft();
    private final ResourceLocation texture = new ResourceLocation("waila", "textures/sprites.png");
    private final @NotNull IProgress progress;

    public TexturedProgressDrawable(@NotNull IProgress progress) {
        this.progress = progress;
    }

    @Override
    public void draw(IArea area) {
        int progressPixel = (int) ((progress.getCurrent() * 28) / progress.getMax());

        this.mc.getTextureManager().bindTexture(texture);

        GuiDraw.drawTexturedModalRect(area.getX() + 4, area.getY(), 4, 16, 28, 16, 28, 16);
        GuiDraw.drawTexturedModalRect(area.getX() + 4, area.getY(), 4, 0, progressPixel + 1, 16, progressPixel + 1, 16);
    }
}
