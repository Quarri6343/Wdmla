package com.gtnewhorizons.wdmla.config;

import com.gtnewhorizon.gtnhlib.config.Config;
import com.gtnewhorizons.wdmla.WDMla;
import com.gtnewhorizons.wdmla.impl.ui.DefaultThemes;

@Config(modid = WDMla.MODID, category = "wdmla_general", configSubDirectory = "WDMla", filename = "general")
@Config.LangKey("option.general.category")
@Config.Comment("These are WDMla exclusive settings")
public class General {

    @Config.LangKey("option.general.forcelegacy")
    @Config.DefaultBoolean(false)
    @Config.Comment("Disables all modern WDMla features. This will make the system ignore all settings in this category")
    public static boolean forceLegacy;

    @Config.LangKey("option.general.ghostproduct")
    @Config.DefaultBoolean(true)
    @Config.Comment("Shows the ghost product on process")
    public static boolean ghostProduct;

    @Config.LangKey("option.general.theme")
    @Config.DefaultEnum("NONE")
    @Config.Comment("Current theme of tooltips. If other than NONE is selected, some values will be overridden")
    public static DefaultThemes currentTheme;
}
