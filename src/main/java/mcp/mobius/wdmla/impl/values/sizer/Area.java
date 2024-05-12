package mcp.mobius.wdmla.impl.values.sizer;

import java.awt.*;

import org.jetbrains.annotations.NotNull;

import mcp.mobius.wdmla.api.IArea;

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
