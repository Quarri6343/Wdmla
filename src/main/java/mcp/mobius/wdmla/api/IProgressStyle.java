package mcp.mobius.wdmla.api;

public interface IProgressStyle {

    /// The color that is used for the border of the progress bar
    IProgressStyle borderColor(int c);

    /// The color that is used for the background of the progress bar
    IProgressStyle backgroundColor(int c);

    /// The color that is used for the filled part of the progress bar
    IProgressStyle filledColor(int c);

    /// If this is different from the filledColor then the fill color will alternate
    IProgressStyle alternateFilledColor(int c);

    int getBorderColor();

    int getBackgroundColor();

    int getFilledColor();

    int getAlternateFilledColor();
}
