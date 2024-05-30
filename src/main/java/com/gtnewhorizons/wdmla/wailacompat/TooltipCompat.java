package com.gtnewhorizons.wdmla.wailacompat;

import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.VPanelComponent;
import mcp.mobius.waila.overlay.DisplayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

import static mcp.mobius.waila.api.SpecialChars.*;

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
                                lineComponent.text("health: " + renderMatcher.group("args"));
                                break;//TODO: implement health component
                            case "waila.stack":
                                lineComponent.text("item: " + renderMatcher.group("args"));
                                break;//TODO: implement TTRenderStack -> itemstack component migration
                            case "waila.progress":
                                lineComponent.text("progress: " + renderMatcher.group("args"));
                                break;//TODO: implement TTRenderProgressBar -> progress component migration
                            default:
                                break;
                                //renderMatcher.group("args").split(",")
                        }
                        //TODO: implement iconrenderer
//                    } else if (iconMatcher.find()) {
//                        renderable = new Tooltip.Renderable(
//                                new TTRenderIcon(iconMatcher.group("type")),
//                                new Point(offsetX, offsetY));
//                        this.elements2nd.add(renderable);
                    } else {
                        lineComponent.text(DisplayUtil.stripWailaSymbols(cs));
                    }
                }
            }
        }
        return verticalLayout;
    }
}
