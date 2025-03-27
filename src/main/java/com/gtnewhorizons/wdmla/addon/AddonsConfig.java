package com.gtnewhorizons.wdmla.addon;

import com.gtnewhorizon.gtnhlib.config.Config;
import com.gtnewhorizons.wdmla.WDMla;

@Config(modid = WDMla.MODID, category = "addons", configSubDirectory = "WDMla", filename = "addons")
@Config.LangKey("option.addon.category")
public class AddonsConfig {

    public static Core core = new Core();

    @Config.LangKey("option.core.category")
    public static class Core {

        public DefaultBlock defaultBlock = new DefaultBlock();
        public DefaultEntity defaultEntity = new DefaultEntity();

        public static class DefaultBlock {

            @Config.LangKey("option.core.show.icon")
            @Config.DefaultBoolean(true)
            public boolean showIcon;

            @Config.LangKey("option.core.show.blockname")
            @Config.DefaultBoolean(true)
            public boolean showBlockName;

            @Config.LangKey("option.core.show.modname")
            @Config.DefaultBoolean(true)
            public boolean showModName;
        }

        public static class DefaultEntity {

            @Config.LangKey("option.core.show.entityname")
            @Config.DefaultBoolean(true)
            public boolean showEntityName;

            @Config.LangKey("option.core.show.modname")
            @Config.DefaultBoolean(true)
            public boolean showModName;

            @Config.LangKey("option.core.maxentityhealth")
            @Config.DefaultInt(40)
            @Config.Comment("If the maximum health of an entity is above this value, texts will be shown instead of heart icons")
            public int maxEntityHealth;
        }
    }
}
