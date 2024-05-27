package mcp.mobius.wdmla.api.ui.sizer;

/**
 * Padding between widgets. Negative number is allowed to intentionally overlap widgets
 */
public interface IPadding {

    IPadding top(int top);

    IPadding bottom(int bottom);

    IPadding left(int left);

    IPadding right(int right);

    IPadding vertical(int vertical);

    IPadding horizontal(int horizontal);

    int getTop();

    int getBottom();

    int getLeft();

    int getRight();
}
