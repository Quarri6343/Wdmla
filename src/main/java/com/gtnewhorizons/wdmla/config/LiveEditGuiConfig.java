package com.gtnewhorizons.wdmla.config;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import net.minecraft.client.gui.GuiScreen;

import java.util.List;

//hacky solution to force configID enabled
public class LiveEditGuiConfig  extends GuiConfig {

    public LiveEditGuiConfig (GuiScreen parentScreen, List<IConfigElement> configElements, String modID,
                             boolean allRequireWorldRestart, boolean allRequireMcRestart, String title, String titleLine2)
    {
        super(parentScreen, configElements, modID, "liveeditconfig", allRequireWorldRestart, allRequireMcRestart, title, titleLine2);
        this.entryList = new LiveEditGuiConfigEntries(this, mc);
    }

    @Override
    public void initGui(){
        if (this.entryList == null || this.needsRefresh)
        {
            this.entryList = new LiveEditGuiConfigEntries(this, mc);
            this.needsRefresh = false;
        }

        super.initGui();
    }
}
