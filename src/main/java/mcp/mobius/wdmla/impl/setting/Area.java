package mcp.mobius.wdmla.impl.setting;

import mcp.mobius.wdmla.api.IArea;

import java.awt.*;

public class Area extends Size implements IArea {

    private final Point point;

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
