package com.gtnewhorizons.wdmla.impl.ui;

import static com.gtnewhorizons.wdmla.impl.ui.component.TooltipComponent.DEFAULT_AMOUNT_TEXT_PADDING;
import static mcp.mobius.waila.api.SpecialChars.ITALIC;

import java.util.List;

import com.gtnewhorizons.wdmla.api.Theme;
import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.Nullable;

import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.ui.IComponent;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.api.ui.MessageType;
import com.gtnewhorizons.wdmla.config.WDMlaConfig;
import com.gtnewhorizons.wdmla.impl.ui.component.AmountComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.HPanelComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.ItemComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.TextComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.TexturedProgressComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.VPanelComponent;
import com.gtnewhorizons.wdmla.impl.ui.style.TextStyle;

import mcp.mobius.waila.cbcore.LangUtil;
import mcp.mobius.waila.overlay.DisplayUtil;
import mcp.mobius.waila.utils.ModIdentification;

/**
 * We haven't implemented the actual "bundles of layout configuration" yet (@see Jade). This class is just meant to
 * unify layout settings between providers
 */
public class ThemeHelper {

    public static final ThemeHelper INSTANCE = new ThemeHelper();

    public void overrideTooltipIcon(ITooltip root, ItemStack newItemStack) {
        root.replaceChildWithTag(
                Identifiers.ITEM_ICON,
                new ItemComponent(newItemStack).doDrawOverlay(false).tag(Identifiers.ITEM_ICON));
    }

    public void overrideTooltipTitle(ITooltip root, ItemStack newItemStack) {
        overrideTooltipTitle(root, DisplayUtil.itemDisplayNameShort(newItemStack));
    }

    public void overrideTooltipTitle(ITooltip root, String newName) {
        Theme theme = WDMlaConfig.instance().getEnum(Identifiers.CONFIG_CURRENT_THEME).get();
        IComponent replacedName = new HPanelComponent()
                .child(new TextComponent(newName).style(new TextStyle().color(theme.textColor(MessageType.TITLE))))
                .tag(Identifiers.ITEM_NAME);
        root.replaceChildWithTag(Identifiers.ITEM_NAME, replacedName);
    }

    public void overrideTooltipModName(ITooltip root, ItemStack newItemStack) {
        overrideTooltipModName(root, ModIdentification.nameFromStack(newItemStack));
    }

    public void overrideTooltipModName(ITooltip root, String newName) {
        Theme theme = WDMlaConfig.instance().getEnum(Identifiers.CONFIG_CURRENT_THEME).get();
        IComponent replacedModName = new TextComponent(ITALIC + newName)
                .style(new TextStyle().color(theme.textColor(MessageType.MOD_NAME))).tag(Identifiers.MOD_NAME);
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

    public IComponent warning(String content) {
        return color(content, MessageType.WARNING);
    }

    public IComponent danger(String content) {
        return color(content, MessageType.DANGER);
    }

    public IComponent failure(String content) {
        return color(content, MessageType.FAILURE);
    }

    public IComponent color(String content, MessageType type) {
        Theme theme = WDMlaConfig.instance().getEnum(Identifiers.CONFIG_CURRENT_THEME).get();
        return new TextComponent(content).style(new TextStyle().color(theme.textColor(type)));
    }

    public IComponent itemProgress(List<ItemStack> input, List<ItemStack> output, int currentProgress, int maxProgress,
            @Nullable IComponent legacyModeProgressText, boolean showDetails) {
        if (!WDMlaConfig.instance().getBoolean(Identifiers.CONFIG_FORCE_LEGACY)) {
            ITooltip hPanel = new HPanelComponent();
            for (ItemStack inputStack : input) {
                if (inputStack != null) {
                    hPanel.item(inputStack);
                }
            }
            hPanel.child(new TexturedProgressComponent(currentProgress, maxProgress));
            for (ItemStack outputStack : output) {
                if (outputStack != null) {
                    hPanel.item(outputStack);
                }
            }
            return hPanel;
        } else {
            ITooltip vPanel = new VPanelComponent();
            if (showDetails) {
                for (ItemStack inputStack : input) {
                    if (inputStack != null) {
                        vPanel.horizontal().text(String.format("%s: ", LangUtil.translateG("hud.msg.in"))).child(
                                ThemeHelper.INSTANCE.info(
                                        String.format(
                                                "%dx %s",
                                                inputStack.stackSize,
                                                DisplayUtil.itemDisplayNameShort(inputStack))));
                    }
                }
                for (ItemStack outputStack : output) {
                    if (outputStack != null) {
                        vPanel.horizontal().text(String.format("%s: ", LangUtil.translateG("hud.msg.out"))).child(
                                ThemeHelper.INSTANCE.info(
                                        String.format(
                                                "%dx %s",
                                                outputStack.stackSize,
                                                DisplayUtil.itemDisplayNameShort(outputStack))));
                    }
                }
            }
            if (legacyModeProgressText != null) {
                vPanel.child(legacyModeProgressText);
            }

            if (vPanel.childrenSize() != 0) {
                return vPanel;
            } else {
                return null;
            }
        }
    }

    public IComponent amount(long current, long max, IComponent content) {
        ITooltip amountTooltip = new AmountComponent(current, max);
        amountTooltip.child(new VPanelComponent().padding(DEFAULT_AMOUNT_TEXT_PADDING).child(content));
        return amountTooltip;
    }

    public IComponent value(String entry, String value) {
        return new HPanelComponent().text(String.format("%s: ", entry)).child(ThemeHelper.INSTANCE.info(value));
    }
}
