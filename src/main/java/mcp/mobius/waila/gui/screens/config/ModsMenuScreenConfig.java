package mcp.mobius.waila.gui.screens.config;

import com.google.common.collect.Lists;
import com.gtnewhorizons.wdmla.WDMla;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.config.WDMlaConfig;
import cpw.mods.fml.client.config.GuiConfig;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.utils.Constants;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

@SuppressWarnings("unused")
public class ModsMenuScreenConfig extends GuiConfig {

    public ModsMenuScreenConfig(GuiScreen parent) {
        super(
                parent,
                Lists.newArrayList(
                        new ConfigElement<>(WDMlaConfig.instance().getCategory(Identifiers.CONFIG_GENERAL)),
                        new ConfigElement<>(ConfigHandler.instance().config.getCategory(Configuration.CATEGORY_GENERAL)),
                        new ConfigElement<>(ConfigHandler.instance().config.getCategory(Constants.CATEGORY_MODULES))),
                WDMla.MODID,
                "config",
                false,
                false,
                "WDMla");
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
}
