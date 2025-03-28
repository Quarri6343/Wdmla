package com.gtnewhorizons.wdmla.plugin.harvestability.helpers;

import java.util.List;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import com.gtnewhorizons.wdmla.plugin.harvestability.proxy.ProxyIguanaTweaks;
import com.gtnewhorizons.wdmla.plugin.harvestability.proxy.ProxyTinkersConstruct;
import com.gtnewhorizons.wdmla.api.Mods;
import com.gtnewhorizons.wdmla.api.ui.IComponent;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.HPanelComponent;

public class StringHelper {

    public static String getHarvestLevelName(int num) {
        if (Mods.IGUANATWEAKS.isLoaded()) {
            return ProxyIguanaTweaks.getHarvestLevelName(num);
        }

        if (Mods.TCONSTUCT.isLoaded()) {
            return ProxyTinkersConstruct.getTicHarvestLevelName(num);
        }

        String unlocalized = "hud.msg.harvestlevel" + (num + 1);

        if (StatCollector.canTranslate(unlocalized)) return StatCollector.translateToLocal(unlocalized);

        return String.valueOf(num);
    }

    public static IComponent concatenateStringList(List<IComponent> strings, String separator) {
        ITooltip sb = new HPanelComponent();
        String sep = "";
        for (IComponent s : strings) {
            sb.text(sep);
            sb.child(s);
            sep = separator;
        }
        return sb;
    }

    public static String stripFormatting(String str) {
        return EnumChatFormatting.getTextWithoutFormattingCodes(str);
    }
}
