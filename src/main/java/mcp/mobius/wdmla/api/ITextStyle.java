package mcp.mobius.wdmla.api;

public interface ITextStyle {

    WidgetAlignment DEFAULT_ALIGN = WidgetAlignment.TOPLEFT;
    int DEFAULT_COLOR = 0xDEDEDE;
    boolean DEFAULT_SHADOW = true;

    WidgetAlignment getAlignment();

    int getColor();

    boolean getShadow();
}
