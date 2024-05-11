package mcp.mobius.wdmla.impl;

import mcp.mobius.wdmla.api.IPadding;

public class Padding implements IPadding {

    int top = 0;
    int bottom = 0;
    int left = 0;
    int right = 0;

    @Override
    public IPadding topPadding(int padding) {
        top = padding;
        return this;
    }

    @Override
    public IPadding bottomPadding(int padding) {
        bottom = padding;
        return this;
    }

    @Override
    public IPadding leftPadding(int padding) {
        left = padding;
        return this;
    }

    @Override
    public IPadding rightPadding(int padding) {
        right = padding;
        return this;
    }

    @Override
    public int getLeftPadding() {
        return left;
    }

    @Override
    public int getRightPadding() {
        return right;
    }

    @Override
    public int getTopPadding() {
        return top;
    }

    @Override
    public int getBottomPadding() {
        return bottom;
    }
}
