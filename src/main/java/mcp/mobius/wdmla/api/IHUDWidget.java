package mcp.mobius.wdmla.api;

/**
 * Avoids duplicated naming with ModularUI widget
 */
public interface IHUDWidget {

    IHUDWidget padding(IPadding padding);

    IHUDWidget size(ISize size);

    void tick(int x, int y);

    int getWidth();

    int getHeight();

    IHUDWidget child(IHUDWidget child);
}
