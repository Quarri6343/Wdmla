package com.gtnewhorizons.wdmla.impl.ui;

import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.ui.ColorPalette;
import com.gtnewhorizons.wdmla.api.ui.IComponent;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.api.ui.MessageType;
import com.gtnewhorizons.wdmla.config.WDMlaConfig;
import com.gtnewhorizons.wdmla.impl.ui.component.HPanelComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.ItemComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.TextComponent;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Padding;
import com.gtnewhorizons.wdmla.impl.ui.style.TextStyle;
import mcp.mobius.waila.overlay.DisplayUtil;
import mcp.mobius.waila.utils.ModIdentification;
import net.minecraft.item.ItemStack;

import static mcp.mobius.waila.api.SpecialChars.ITALIC;

/**
 * We don't implement the actual "bundles of layout configuration" for now (@see Jade).
 * This class is just meant to unify layout settings between providers
 */
public class ThemeHelper {
    public static final ThemeHelper INSTANCE = new ThemeHelper();

    public void overrideTooltipIcon(ITooltip root, ItemStack newItemStack) {
        root.replaceChildWithTag(Identifiers.ITEM_ICON, new ItemComponent(newItemStack).tag(Identifiers.ITEM_ICON));
    }

    public void overrideTooltipTitle(ITooltip root, ItemStack newItemStack) {
        overrideTooltipTitle(root, DisplayUtil.itemDisplayNameShort(newItemStack));
    }

    public void overrideTooltipTitle(ITooltip root, String newName) {
        IComponent replacedName = new HPanelComponent()
                .text(newName, new TextStyle().color(ColorPalette.TITLE), new Padding())
                .tag(Identifiers.ITEM_NAME);
        root.replaceChildWithTag(Identifiers.ITEM_NAME, replacedName);
    }

    public void overrideTooltipModName(ITooltip root, ItemStack newItemStack) {
        overrideTooltipModName(root, ModIdentification.nameFromStack(newItemStack));
    }

    public void overrideTooltipModName(ITooltip root, String newName) {
        IComponent replacedModName = new TextComponent(ITALIC + newName).style(new TextStyle().color(ColorPalette.MOD_NAME)).tag(Identifiers.MOD_NAME);
        root.replaceChildWithTag(Identifiers.MOD_NAME, replacedModName);
    }

    public void overrideTooltipHeader(ITooltip root, ItemStack newItemStack) {
        overrideTooltipIcon(root, newItemStack);
        overrideTooltipTitle(root, newItemStack);
        overrideTooltipModName(root, newItemStack);
    }

    public IComponent info(String content) {
        return color(content, MessageType.INFO);
    }

    public IComponent title(String content) {
        return color(content, MessageType.TITLE);
    }

    public IComponent success(String content) {
        return color(content, MessageType.SUCCESS);
    }

    public IComponent failure(String content) {
        return color(content, MessageType.FAILURE);
    }

    public IComponent color(String content, MessageType type) {
        if(WDMlaConfig.instance().getBoolean(Identifiers.CONFIG_FORCE_LEGACY)) {
            return new TextComponent(content).style(new TextStyle().color(ColorPalette.get(MessageType.NORMAL)));
        }
        else {
            return new TextComponent(content).style(new TextStyle().color(ColorPalette.get(type)));
        }
    }
}
