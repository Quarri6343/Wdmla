package com.gtnewhorizons.wdmla.impl.ui.sizer;

import java.awt.*;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizons.wdmla.api.ui.sizer.IArea;

public class Area extends Size implements IArea {

    private final @NotNull Point point;

    public Area(int x, int y, int width, int height) {
        super(width, height);
        point = new Point(x, y);
    }

    @Override
    public int getX() {
        return point.x;
    }

    @Override
    public int getY() {
        return point.y;
    }
}
