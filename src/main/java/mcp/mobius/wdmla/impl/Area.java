package mcp.mobius.wdmla.impl;

import mcp.mobius.wdmla.api.IArea;

public class Area implements IArea {

    private int x;
    private int y;
    private int width;
    private int height;

    public Area(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getW() {
        return width;
    }

    @Override
    public int getH() {
        return height;
    }

    @Override
    public IArea x(int x) {
        this.x = x;
        return this;
    }

    @Override
    public IArea y(int y) {
        this.y = y;
        return this;
    }

    @Override
    public IArea w(int width) {
        this.width = width;
        return this;
    }

    @Override
    public IArea h(int height) {
        this.height = height;
        return this;
    }
}
