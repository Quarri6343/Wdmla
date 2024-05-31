package com.gtnewhorizons.wdmla.wailacompat;

import static mcp.mobius.waila.api.SpecialChars.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.*;
import com.gtnewhorizons.wdmla.overlay.VanillaIconUI;

import mcp.mobius.waila.overlay.DisplayUtil;

/**
 * Replacement of old computeRenderables from Waila Tooltip class
 */
public class TooltipCompat {

    public static ITooltip computeRenderables(List<String> legacyTextData) {
        ITooltip verticalLayout = new VPanelComponent();

        ArrayList<ArrayList<String>> lines = new ArrayList<>();
        for (String s : legacyTextData) {

            ArrayList<String> line = new ArrayList<>(Arrays.asList(patternTab.split(s)));
            lines.add(line);
        }

        for (int i = 0; i < lines.size(); i++) {
            ITooltip lineComponent = verticalLayout.horizontal();
            for (int c = 0; c < lines.get(i).size(); c++) { // We check all the columns for this line
                String currentLine = lines.get(i).get(c);
                Matcher lineMatcher = patternLineSplit.matcher(currentLine);

                while (lineMatcher.find()) {
                    String cs = lineMatcher.group();
                    Matcher renderMatcher = patternRender.matcher(cs); // We keep a matcher here to be able to check if
                    // we have a Renderer. Might be better to do a
                    // startWith + full matcher init after the check
                    Matcher iconMatcher = patternIcon.matcher(cs);

                    if (renderMatcher.find()) {
                        String renderName = renderMatcher.group("name");

                        switch (renderName) {
                            case "waila.health":
                                String[] healthArgs = renderMatcher.group("args").split(",");
                                lineComponent.child(parseHealthArgs(healthArgs));
                                break;
                            case "waila.stack":
                                String[] itemArgs = renderMatcher.group("args").split(",");
                                lineComponent.child(parseItemArgs(itemArgs));
                                break;
                            case "waila.progress":
                                String[] progressArgs = renderMatcher.group("args").split(",");
                                lineComponent.child(parseProgressArgs(progressArgs));
                                break;
                            default:
                                break;
                        }
                    } else if (iconMatcher.find()) {
                        String iconArg = iconMatcher.group("type");
                        lineComponent.child(parseIconArgs(iconArg));
                    } else {
                        lineComponent.text(DisplayUtil.stripWailaSymbols(cs));
                    }
                }
            }
        }
        return verticalLayout;
    }

    private static HealthComponent parseHealthArgs(String[] args) {
        float maxHeartsPerLine = Float.parseFloat(args[0]); //for some reason, old api accepts float
        float health = Float.parseFloat(args[1]);
        float maxhealth = Float.parseFloat(args[2]);

        return new HealthComponent((int) maxHeartsPerLine, health, maxhealth);
    }

    private static ItemComponent parseItemArgs(String[] args) {
        int type = Integer.parseInt(args[0]); // 0 for block, 1 for item
        String name = args[1]; // Fully qualified name
        int amount = Integer.parseInt(args[2]);
        int meta = Integer.parseInt(args[3]);

        ItemStack stack = null;
        if (type == 0) stack = new ItemStack((Block) Block.blockRegistry.getObject(name), amount, meta);
        if (type == 1) stack = new ItemStack((Item) Item.itemRegistry.getObject(name), amount, meta);

        return new ItemComponent(stack);
    }

    private static TexturedProgressComponent parseProgressArgs(String[] args) {
        int current = Integer.parseInt(args[0]);
        int max = Integer.parseInt(args[1]);
        return new TexturedProgressComponent(current, max);
    }

    private static VanillaIconComponent parseIconArgs(String arg) {
        VanillaIconUI iconUI = switch (arg) {
            case "a" -> VanillaIconUI.HEART;
            case "b" -> VanillaIconUI.HHEART;
            case "c" -> VanillaIconUI.EHEART;
            default -> VanillaIconUI.BUBBLEEXP;
        };
        // intentional hardcode to bypass bad enum implementation
        return new VanillaIconComponent(iconUI);
    }
}
