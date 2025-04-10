package com.gtnewhorizons.wdmla.impl.ui.component;

import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizons.wdmla.api.ui.IComponent;
import com.gtnewhorizons.wdmla.api.ui.IDrawable;
import com.gtnewhorizons.wdmla.api.ui.sizer.IPadding;
import com.gtnewhorizons.wdmla.api.ui.sizer.ISize;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Area;

/**
 * This class provides some basic implementation of IComponent, so you don't have to write everything.
 */
public abstract class Component implements IComponent {

    // settings
    protected IPadding padding;
    protected ISize size;

    // render
    protected IDrawable foreground;

    protected ResourceLocation tag;

    protected Component(IPadding padding, ISize size, IDrawable foreground) {
        this.padding = padding;
        this.size = size;
        this.foreground = foreground;
    }

    public Component padding(@NotNull IPadding padding) {
        this.padding = padding;
        return this;
    }

    public Component size(@NotNull ISize size) {
        this.size = size;
        return this;
    }

    @Override
    public void tick(int x, int y) {
        foreground.draw(new Area(x + padding.getLeft(), y + padding.getTop(), size.getW(), size.getH()));
    }

    @Override
    public int getWidth() {
        return padding.getLeft() + size.getW() + padding.getRight();
    }

    @Override
    public int getHeight() {
        return padding.getTop() + size.getH() + padding.getBottom();
    }

    @Override
    public IComponent tag(ResourceLocation tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public ResourceLocation getTag() {
        return tag;
    }
}
