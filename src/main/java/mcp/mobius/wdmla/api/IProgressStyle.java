package mcp.mobius.wdmla.api;

public interface IProgressStyle {

    int DEFAULT_BORDER = 0xFFFFFFFF;
    int DEFAULT_BACKGROUND = 0xFFFFFFFF;
    int DEFAULT_FILLED = 0xFFD7D7D7;
    int DEFAULT_FILLED_ALTERNATE = 0xFFD7D7D7;

    int getBorderColor();

    int getBackgroundColor();

    int getFilledColor();

    int getAlternateFilledColor();
}
