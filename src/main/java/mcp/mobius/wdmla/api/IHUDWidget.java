package mcp.mobius.wdmla.api;

/**
 * Base HUD Component
 * Avoids duplicated naming with ModularUI Widget
 */
public interface IHUDWidget {

    IHUDWidget padding(IPadding padding);

    IHUDWidget size(ISize size);

    void tick(int x, int y);

    int getWidth();

    int getHeight();

    IHUDWidget child(IHUDWidget child);
}
