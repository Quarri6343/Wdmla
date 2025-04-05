package com.gtnewhorizons.wdmla.config;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import com.gtnewhorizon.gtnhlib.config.ConfigException;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;
import com.gtnewhorizons.wdmla.WDMla;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.plugin.PluginsConfig;

import cpw.mods.fml.client.config.IConfigElement;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.utils.Constants;

@SuppressWarnings({ "unused", "rawtypes" })
public class ModsMenuScreenConfig extends LiveEditGuiConfig {

    public ModsMenuScreenConfig(GuiScreen parent) throws ConfigException {
        super(parent, getMainCategories(), WDMla.MODID, false, false, "WDMla", "Waila Replacement Mod");
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        super.actionPerformed(button);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (ConfigHandler.instance().config.hasChanged()) {
            ConfigHandler.instance().config.save();
        }
    }

    private static List<IConfigElement> getMainCategories() throws ConfigException {
        List<IConfigElement> categories = ConfigurationManager
                .getConfigElementsMulti(true, General.class, PluginsConfig.class);
        categories.addAll(
                Arrays.asList(
                        new ConfigElement<>(WDMlaConfig.instance().getCategory(Identifiers.CONFIG_AUTOGEN)),
                        new ConfigElement<>(
                                ConfigHandler.instance().config.getCategory(Configuration.CATEGORY_GENERAL)),
                        new ConfigElement<>(ConfigHandler.instance().config.getCategory(Constants.CATEGORY_MODULES))));
        return categories;
    }
}
