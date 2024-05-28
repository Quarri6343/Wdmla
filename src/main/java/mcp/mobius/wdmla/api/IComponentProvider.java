package mcp.mobius.wdmla.api;

import mcp.mobius.wdmla.api.ui.IComponent;
import mcp.mobius.wdmla.api.ui.ITooltip;
import net.minecraft.item.ItemStack;

public interface IComponentProvider<T extends Accessor> extends IToggleableProvider {

    default ITooltip getIcon(T accessor, ITooltip currentIcon) {
        return null;
    }

    void appendTooltip(ITooltip tooltip, T accessor);
}
