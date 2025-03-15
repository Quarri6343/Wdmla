package com.gtnewhorizons.wdmla.addon.vanilla;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.IComponent;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.HPanelComponent;
import mcp.mobius.waila.overlay.DisplayUtil;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import static mcp.mobius.waila.api.SpecialChars.WHITE;

public class SilverFishBlockProvider implements IBlockComponentProvider {

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        int metadata = accessor.getMetadata();
        ItemStack dummyStack = switch (metadata) {
            case 1 -> new ItemStack(Blocks.cobblestone);
            case 2 -> new ItemStack(Blocks.stonebrick, 1, 0);
            case 3 -> new ItemStack(Blocks.stonebrick, 1, 1);
            case 4 -> new ItemStack(Blocks.stonebrick, 1, 2);
            case 5 -> new ItemStack(Blocks.stonebrick, 1, 3);
            default -> new ItemStack(Blocks.stone);
        };
        IComponent replacedName = new HPanelComponent()
                .text(WHITE + DisplayUtil.itemDisplayNameShort(dummyStack))
                .tag(Identifiers.ITEM_NAME);
        tooltip.replaceChildWithTag(Identifiers.ITEM_NAME, replacedName);
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.SILVERFISH;
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.CORE_OVERRIDE;
    }
}
