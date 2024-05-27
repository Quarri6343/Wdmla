package mcp.mobius.wdmla.impl.ui.value.sizer;

import mcp.mobius.wdmla.api.ui.sizer.IPadding;

public class Padding implements IPadding {

    private int top;
    private int bottom;
    private int left;
    private int right;

    public Padding() {
        this.top = 0;
        this.bottom = 0;
        this.left = 0;
        this.right = 0;
    }

    public Padding(int top, int bottom, int left, int right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    public IPadding top(int top) {
        this.top = top;
        return this;
    }

    @Override
    public IPadding bottom(int bottom) {
        this.bottom = bottom;
        return this;
    }

    @Override
    public IPadding left(int left) {
        this.left = left;
        return this;
    }

    @Override
    public IPadding right(int right) {
        this.right = right;
        return this;
    }

    @Override
    public IPadding vertical(int vertical) {
        top(vertical);
        bottom(vertical);
        return this;
    }

    @Override
    public IPadding horizontal(int horizontal) {
        left(horizontal);
        right(horizontal);
        return this;
    }

    @Override
    public int getTop() {
        return top;
    }

    @Override
    public int getBottom() {
        return bottom;
    }

    @Override
    public int getLeft() {
        return left;
    }

    @Override
    public int getRight() {
        return right;
    }
}
