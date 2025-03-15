package com.gtnewhorizons.wdmla.addon.vanilla;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.ColorCodes;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.HPanelComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.ItemComponent;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Padding;
import com.gtnewhorizons.wdmla.impl.ui.style.TextStyle;
import mcp.mobius.waila.cbcore.LangUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RedstoneWireProvider implements IBlockComponentProvider {

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        ItemStack redstone = new ItemStack(Items.redstone);
        tooltip.replaceChildWithTag(Identifiers.ITEM_ICON, new ItemComponent(redstone).tag(Identifiers.ITEM_ICON));
        tooltip.child(
                new HPanelComponent()
                        .text(String.format("%s: ", LangUtil.translateG("hud.msg.power")))
                        .text(String.format("%s", accessor.getMetadata()), new TextStyle().color(ColorCodes.WHITE), new Padding())
        );
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.REDSTONE_WIRE;
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.CORE_OVERRIDE;
    }
}
