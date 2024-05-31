package com.gtnewhorizons.wdmla.wailacompat;

import static mcp.mobius.waila.api.SpecialChars.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

import com.gtnewhorizons.wdmla.api.ITTRenderParser;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.*;
import com.gtnewhorizons.wdmla.wailacompat.parser.HealthArgsParser;
import com.gtnewhorizons.wdmla.wailacompat.parser.IconArgsParser;
import com.gtnewhorizons.wdmla.wailacompat.parser.ItemArgsParser;
import com.gtnewhorizons.wdmla.wailacompat.parser.ProgressArgsParser;

import mcp.mobius.waila.overlay.DisplayUtil;

/**
 * Replacement of old computeRenderables from Waila Tooltip class
 */
public class TooltipCompat {

    private final ITTRenderParser healthParser = new HealthArgsParser();
    private final ITTRenderParser itemParser = new ItemArgsParser();
    private final ITTRenderParser progressParser = new ProgressArgsParser();
    private final ITTRenderParser iconParser = new IconArgsParser();

    public ITooltip computeRenderables(List<String> legacyTextData) {
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
                                lineComponent.child(healthParser.parse(healthArgs));
                                break;
                            case "waila.stack":
                                String[] itemArgs = renderMatcher.group("args").split(",");
                                lineComponent.child(itemParser.parse(itemArgs));
                                break;
                            case "waila.progress":
                                String[] progressArgs = renderMatcher.group("args").split(",");
                                lineComponent.child(progressParser.parse(progressArgs));
                                break;
                            default:
                                break;
                        }
                    } else if (iconMatcher.find()) {
                        String iconArg = iconMatcher.group("type");
                        lineComponent.child(iconParser.parse(new String[] { iconArg }));
                    } else {
                        lineComponent.text(DisplayUtil.stripWailaSymbols(cs));
                    }
                }
            }
        }
        return verticalLayout;
    }
}
