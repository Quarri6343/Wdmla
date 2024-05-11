package mcp.mobius.wdmla.api;

import mcp.mobius.waila.api.elements.ElementAlignment;

public interface ITextStyle {

    ITextStyle alignment(ElementAlignment align);

    ITextStyle color(int color);

    ITextStyle shadow(boolean shadow);

    ElementAlignment getAlignment();

    int getColor();

    boolean getShadow();
}
