package com.gtnewhorizons.wdmla.api.ui;

import net.minecraft.util.ResourceLocation;

/**
 * Base UI Component interface
 */
public interface IComponent {

    void tick(int x, int y);

    int getWidth();

    int getHeight();

    IComponent tag(ResourceLocation tag);

    ResourceLocation getTag();
}
