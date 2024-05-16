package mcp.mobius.wdmla.api.ui.style;

import mcp.mobius.wdmla.api.ui.WidgetAlignment;

public interface IPanelStyle {

    int NO_BORDER = 0x00000000;
    int DEFAULT_SPACE = 2;

    int getBorderColor();

    WidgetAlignment getAlignment();

    int getSpacing();
}
