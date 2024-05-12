package mcp.mobius.wdmla.impl.values.setting;

import mcp.mobius.wdmla.api.IProgressStyle;

public class ProgressStyle implements IProgressStyle {

    private final int borderColor;
    private final int backgroundColor;
    private final int filledColor;
    private final int alternateFilledColor;

    public ProgressStyle() {
        this.borderColor = DEFAULT_BORDER;
        this.backgroundColor = DEFAULT_BACKGROUND;
        this.filledColor = DEFAULT_FILLED;
        this.alternateFilledColor = DEFAULT_FILLED_ALTERNATE;
    }

    public ProgressStyle(int borderColor, int backgroundColor, int filledColor, int alternateFilledColor) {
        this.borderColor = borderColor;
        this.backgroundColor = backgroundColor;
        this.filledColor = filledColor;
        this.alternateFilledColor = alternateFilledColor;
    }

    public ProgressStyle borderColor(int borderColor) {
        return new ProgressStyle(borderColor, backgroundColor, filledColor, alternateFilledColor);
    }

    public ProgressStyle backgroundColor(int backgroundColor) {
        return new ProgressStyle(borderColor, backgroundColor, filledColor, alternateFilledColor);
    }

    public ProgressStyle filledColor(int filledColor) {
        return new ProgressStyle(borderColor, backgroundColor, filledColor, alternateFilledColor);
    }

    public ProgressStyle alternateFilledColor(int alternateFilledColor) {
        return new ProgressStyle(borderColor, backgroundColor, filledColor, alternateFilledColor);
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
