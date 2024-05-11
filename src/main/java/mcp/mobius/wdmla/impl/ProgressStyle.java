package mcp.mobius.wdmla.impl;

import mcp.mobius.wdmla.api.IProgressStyle;

public class ProgressStyle implements IProgressStyle {

    private int borderColor = 0xFFFFFFFF;
    private int backgroundColor = 0xFFFFFFFF;
    private int filledColor = -5592406;
    private int alternateFilledColor = -5592406;

    @Override
    public IProgressStyle borderColor(int c) {
        borderColor = c;
        return this;
    }

    @Override
    public IProgressStyle backgroundColor(int c) {
        backgroundColor = c;
        return this;
    }

    @Override
    public IProgressStyle filledColor(int c) {
        filledColor = c;
        return this;
    }

    @Override
    public IProgressStyle alternateFilledColor(int c) {
        alternateFilledColor = c;
        return this;
    }

    @Override
    public int getBorderColor() {
        return borderColor;
    }

    @Override
    public int getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public int getFilledColor() {
        return filledColor;
    }

    @Override
    public int getAlternateFilledColor() {
        return alternateFilledColor;
    }
}
