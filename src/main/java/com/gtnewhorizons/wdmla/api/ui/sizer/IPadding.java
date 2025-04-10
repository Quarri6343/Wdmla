package com.gtnewhorizons.wdmla.api.ui.sizer;

/**
 * Offsets inside widgets. <br>
 * Negative number is allowed to intentionally overlap widgets. However, if you want to modify every line of element,
 * the wise use of styles like {@link com.gtnewhorizons.wdmla.api.ui.style.IPanelStyle} is recommended.<br>
 * It is consisted with four elements like css: top, bottom, left, right.<br>
 * Google "padding" for more details.<br>
 */
public interface IPadding {

    IPadding top(int top);

    IPadding bottom(int bottom);

    IPadding left(int left);

    IPadding right(int right);

    /**
     * sets top and bottom value at the same time
     */
    IPadding vertical(int vertical);

    /**
     * sets left and right value at the same time
     */
    IPadding horizontal(int horizontal);

    int getTop();

    int getBottom();

    int getLeft();

    int getRight();
}
