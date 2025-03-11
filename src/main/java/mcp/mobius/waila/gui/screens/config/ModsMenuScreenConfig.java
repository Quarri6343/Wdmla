package mcp.mobius.waila.gui.screens.config;

import cpw.mods.fml.client.config.GuiConfig;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.utils.Constants;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.ConfigElement;

import com.google.common.collect.Lists;
import net.minecraftforge.common.config.Configuration;

@SuppressWarnings("unused")
public class ModsMenuScreenConfig extends GuiConfig {


    public ModsMenuScreenConfig(GuiScreen parent) {
        super(
                parent,
                Lists.newArrayList(
                        new ConfigElement<>(ConfigHandler.instance().config.getCategory(Configuration.CATEGORY_GENERAL)),
                        new ConfigElement<>(ConfigHandler.instance().config.getCategory(Constants.CATEGORY_MODULES))),
                "wdmla",
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
