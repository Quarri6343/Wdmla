package com.gtnewhorizons.wdmla.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.GuiConfigEntries;
import cpw.mods.fml.client.config.IConfigElement;

// hacky solution to force configID enabled
public class LiveEditGuiConfigEntries extends GuiConfigEntries {

    public LiveEditGuiConfigEntries(GuiConfig parent, Minecraft mc) {
        super(parent, mc);
        for (int i = 0; i < listEntries.size(); i++) {
            if (listEntries.get(i) instanceof CategoryEntry categoryEntry) {
                listEntries
                        .set(i, new LiveEditCategoryEntry(this.owningScreen, this, categoryEntry.getConfigElement()));
            }
        }
    }

    public static class LiveEditCategoryEntry extends GuiConfigEntries.CategoryEntry {

        public LiveEditCategoryEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList,
                IConfigElement configElement) {
            super(owningScreen, owningEntryList, configElement);
        }

        @Override
        @SuppressWarnings("unchecked")
        protected GuiScreen buildChildScreen() {
            return new LiveEditGuiConfig(
                    this.owningScreen,
                    this.configElement.getChildElements(),
                    this.owningScreen.modID,
                    owningScreen.allRequireWorldRestart || this.configElement.requiresWorldRestart(),
                    owningScreen.allRequireMcRestart || this.configElement.requiresMcRestart(),
                    this.owningScreen.title,
                    ((this.owningScreen.titleLine2 == null ? "" : this.owningScreen.titleLine2) + " > " + this.name));
        }
    }
}
