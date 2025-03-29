package com.gtnewhorizons.wdmla.plugin.vanilla;

import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IEntityComponentProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.api.ui.sizer.IPadding;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Padding;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Size;
import mcp.mobius.waila.cbcore.LangUtil;
import mcp.mobius.waila.overlay.DisplayUtil;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.ResourceLocation;

public enum ItemFrameProvider implements IEntityComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor) {
        if(accessor.getEntity() instanceof EntityItemFrame itemFrame) {
            IPadding itemPadding = new Padding().vertical(2);
            tooltip.horizontal().text(String.format("%s: ", LangUtil.translateG("hud.msg.wdmla.item")), itemPadding)
                    .item(itemFrame.getDisplayedItem(), new Padding(), new Size(10, 10))
                    .text(" " + DisplayUtil.itemDisplayNameShort(itemFrame.getDisplayedItem()), itemPadding);
        }
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.ITEM_FRAME;
    }
}
