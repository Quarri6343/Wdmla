package com.gtnewhorizons.wdmla.addon.vanilla;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.ColorCodes;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.IComponent;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.HPanelComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.ItemComponent;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Padding;
import com.gtnewhorizons.wdmla.impl.ui.style.TextStyle;
import mcp.mobius.waila.cbcore.LangUtil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

//crops are wheat, carrot and potato like blocks which inherits BlockCrops
public class CropHeaderProvider implements IBlockComponentProvider {

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if(accessor.getBlock().equals(Blocks.wheat)) {
            IComponent replacedName = new HPanelComponent()
                    .text(LangUtil.translateG("hud.item.wheatcrop"), new TextStyle().color(ColorCodes.INFO), new Padding())
                    .tag(Identifiers.ITEM_NAME);
            tooltip.replaceChildWithTag(Identifiers.ITEM_NAME, replacedName);
        }
        else if(accessor.getBlock().equals(Blocks.carrots)) {
            ItemStack carrot = new ItemStack(Items.carrot);
            tooltip.replaceChildWithTag(Identifiers.ITEM_ICON, new ItemComponent(carrot).tag(Identifiers.ITEM_ICON));
        }
        else if(accessor.getBlock().equals(Blocks.potatoes)) {
            ItemStack potato = new ItemStack(Items.potato);
            tooltip.replaceChildWithTag(Identifiers.ITEM_ICON, new ItemComponent(potato).tag(Identifiers.ITEM_ICON));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.CROP_HEADER;
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.CORE_OVERRIDE;
    }
}
