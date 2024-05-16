package mcp.mobius.wdmla.impl.ui.value.sizer;

import mcp.mobius.wdmla.api.ui.sizer.IPadding;

/**
 * Padding between widgets. Negative number is allowed to intentionally overlap widgets
 */
public class Padding implements IPadding {

    private final int top;
    private final int bottom;
    private final int left;
    private final int right;

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

    @Override
    public int getLeft() {
        return left;
    }

    @Override
    public int getRight() {
        return right;
    }

    @Override
    public int getTop() {
        return top;
    }

    @Override
    public int getBottom() {
        return bottom;
    }
}
