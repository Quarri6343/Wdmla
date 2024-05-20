package mcp.mobius.wdmla.api.ui;

import mcp.mobius.wdmla.impl.ui.component.Component;
import org.jetbrains.annotations.NotNull;

public interface IComponent {

    void tick(int x, int y);

    int getWidth();

    int getHeight();

    IComponent child(@NotNull IComponent child);
}
