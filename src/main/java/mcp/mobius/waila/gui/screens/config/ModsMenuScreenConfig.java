package mcp.mobius.waila.gui.screens.config;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import com.gtnewhorizons.wdmla.WDMla;
import com.gtnewhorizons.wdmla.api.IConfigProvider;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.config.WDMlaConfig;
import com.gtnewhorizons.wdmla.impl.WDMlaClientRegistration;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.utils.Constants;

@SuppressWarnings({ "unused", "rawtypes" })
public class ModsMenuScreenConfig extends GuiConfig {

    public ModsMenuScreenConfig(GuiScreen parent) {
        super(parent, getMainCategories(), WDMla.MODID, "config", false, false, "WDMla");
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

    private static List<IConfigElement> getMainCategories() {
        List<IConfigElement> categories = new ArrayList<>();

        categories.add(new ConfigElement<>(WDMlaConfig.instance().getCategory(Identifiers.CONFIG_GENERAL)));
        categories
                .add(new ConfigElement<>(ConfigHandler.instance().config.getCategory(Configuration.CATEGORY_GENERAL)));
        for (IConfigProvider configProvider : WDMlaClientRegistration.instance().configProviders) {
            categories.add(new ConfigElement<>(WDMlaConfig.instance().getCategory(configProvider.categoryName())));
        }
        categories.add(new ConfigElement<>(ConfigHandler.instance().config.getCategory(Constants.CATEGORY_MODULES)));

        return categories;
    }
}
