package com.gtnewhorizons.wdmla.addon.vanilla;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
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
            ThemeHelper.INSTANCE.overrideTooltipTitle(tooltip, LangUtil.translateG("hud.item.wheatcrop"));
        }
        else if(accessor.getBlock().equals(Blocks.carrots)) {
            ThemeHelper.INSTANCE.overrideTooltipIcon(tooltip, new ItemStack(Items.carrot));
        }
        else if(accessor.getBlock().equals(Blocks.potatoes)) {
            ThemeHelper.INSTANCE.overrideTooltipIcon(tooltip, new ItemStack(Items.potato));
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
