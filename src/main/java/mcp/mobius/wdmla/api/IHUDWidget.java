package mcp.mobius.wdmla.api;

/**
 * Base HUD Component Avoids duplicated naming with ModularUI "Widget"
 */
public interface IHUDWidget {

    void tick(int x, int y);

    int getWidth();

    int getHeight();
}
