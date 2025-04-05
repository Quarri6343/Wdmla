package com.gtnewhorizons.wdmla.plugin.vanilla;

import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IEntityComponentProvider;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.impl.ui.component.ItemComponent;

import mcp.mobius.waila.api.impl.ModuleRegistrar;
import mcp.mobius.waila.cbcore.LangUtil;
import mcp.mobius.waila.overlay.DisplayUtil;
import net.minecraft.util.StatCollector;

// test command: summon FallingSand ~ ~10 ~ {TileID:137,Time:1}
public enum FallingBlockHeaderProvider implements IEntityComponentProvider {

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor) {
        if (accessor.getEntity() instanceof EntityFallingBlock falling) {
            // we don't have proper api to deal with non-raytraced block yet
            if (ModuleRegistrar.instance().hasStackProviders(falling.func_145805_f())) {
                return;
            }

            ItemStack itemForm = new ItemStack(falling.func_145805_f(), 1, falling.field_145814_a);
            tooltip.replaceChildWithTag(Identifiers.ENTITY, new ItemComponent(itemForm).doDrawOverlay(false));
            ThemeHelper.INSTANCE.overrideEntityTooltipTitle(
                    tooltip,
                    String.format(
                            StatCollector.translateToLocal("hud.msg.wdmla.entity.falling"),
                            DisplayUtil.itemDisplayNameShort(itemForm)));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.FALLING_BLOCK_HEADER;
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.HEAD;
    }
}
