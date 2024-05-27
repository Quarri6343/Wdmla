package mcp.mobius.wdmla.api.ui;

import org.jetbrains.annotations.NotNull;

/**
 * Base UI Component interface
 */
public interface IComponent {

    void tick(int x, int y);

    int getWidth();

    int getHeight();
}
