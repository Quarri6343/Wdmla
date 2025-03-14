package com.gtnewhorizons.wdmla.addon.harvestability.helpers;

import java.util.List;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import com.gtnewhorizons.wdmla.addon.harvestability.proxy.ProxyIguanaTweaks;
import com.gtnewhorizons.wdmla.addon.harvestability.proxy.ProxyTinkersConstruct;
import com.gtnewhorizons.wdmla.api.Mods;

public class StringHelper {

    public static String getHarvestLevelName(int num) {
        if (Mods.IGUANATWEAKS.isLoaded()) {
            return ProxyIguanaTweaks.getHarvestLevelName(num);
        }

        if (Mods.TCONSTUCT.isLoaded()) {
            return ProxyTinkersConstruct.getTicHarvestLevelName(num);
        }

        String unlocalized = "wailaharvestability.harvestlevel" + (num + 1);

        if (StatCollector.canTranslate(unlocalized)) return StatCollector.translateToLocal(unlocalized);

        return String.valueOf(num);
    }

    public static String concatenateStringList(List<String> strings, String separator) {
        StringBuilder sb = new StringBuilder();
        String sep = "";
        for (String s : strings) {
            sb.append(sep).append(s);
            sep = separator;
        }
        return sb.toString();
    }

    public static String stripFormatting(String str) {
        return EnumChatFormatting.getTextWithoutFormattingCodes(str);
    }
}
