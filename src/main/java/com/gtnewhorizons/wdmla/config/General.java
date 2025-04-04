package com.gtnewhorizons.wdmla.config;

import com.gtnewhorizon.gtnhlib.config.Config;
import com.gtnewhorizons.wdmla.WDMla;
import com.gtnewhorizons.wdmla.api.ui.ColorPalette;
import com.gtnewhorizons.wdmla.impl.ui.DefaultThemes;

@Config(modid = WDMla.MODID, category = "wdmla_general", configSubDirectory = "WDMla", filename = "general")
@Config.LangKey("option.wdmla.general.category")
@Config.Comment("These are WDMla exclusive settings")
public class General {

    public static TextColor textColor = new TextColor();

    @Config.LangKey("option.wdmla.general.forcelegacy")
    @Config.DefaultBoolean(false)
    @Config.Comment("Disables all modern WDMla features. This will make the system ignore all settings in this category")
    public static boolean forceLegacy;

    @Config.LangKey("option.wdmla.general.ghostproduct")
    @Config.DefaultBoolean(true)
    @Config.Comment("Shows the ghost product on process")
    public static boolean ghostProduct;

    @Config.LangKey("option.wdmla.general.theme")
    @Config.DefaultEnum("CUSTOM")
    @Config.Comment("Current theme of tooltips. If other than NONE is selected, some values will be overridden")
    public static DefaultThemes currentTheme;

    @Config.LangKey("option.wdmla.general.previewincfg")
    @Config.DefaultBoolean(true)
    @Config.Comment("Shows current tooltip preview in config screen")
    public static boolean previewInCfg;

    @Config.Comment("Text color for the custom theme. \n" +
            "See general category for the default text color.")
    @Config.LangKey("option.wdmla.textcolor.category")
    public static class TextColor {

        @Config.LangKey("option.wdmla.general.textcolor.info")
        @Config.DefaultInt(ColorPalette.INFO)
        public int info;

        @Config.LangKey("option.wdmla.general.textcolor.title")
        @Config.DefaultInt(ColorPalette.TITLE)
        public int title;

        @Config.LangKey("option.wdmla.general.textcolor.success")
        @Config.DefaultInt(ColorPalette.SUCCESS)
        public int success;

        @Config.LangKey("option.wdmla.general.textcolor.warning")
        @Config.DefaultInt(ColorPalette.WARNING)
        public int warning;

        @Config.LangKey("option.wdmla.general.textcolor.danger")
        @Config.DefaultInt(ColorPalette.DANGER)
        public int danger;

        @Config.LangKey("option.wdmla.general.textcolor.failure")
        @Config.DefaultInt(ColorPalette.FAILURE)
        public int failure;

        @Config.LangKey("option.wdmla.general.textcolor.modname")
        @Config.DefaultInt(ColorPalette.MOD_NAME)
        public int modName;
    }

    //TODO:AmountStyle Config && Break Progress Config
}
