package com.gtnewhorizons.wdmla.addon.harvestability;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;

import com.gtnewhorizons.wdmla.api.IWDMlaClientRegistration;
import com.gtnewhorizons.wdmla.api.IWDMlaPlugin;

import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.api.impl.ModuleRegistrar;

public class HarvestabilityPlugin implements IWDMlaPlugin {

    public static HashMap<String, Boolean> configOptions = new HashMap<String, Boolean>();

    static {
        configOptions.put("harvestability.forceLegacyMode", false);
        configOptions.put("harvestability.harvestlevel", true);
        configOptions.put("harvestability.harvestlevelnum", false);
        configOptions.put("harvestability.effectivetool", true);
        configOptions.put("harvestability.currentlyharvestable", true);
        configOptions.put("harvestability.harvestlevel.sneakingonly", false);
        configOptions.put("harvestability.harvestlevelnum.sneakingonly", false);
        configOptions.put("harvestability.effectivetool.sneakingonly", false);
        configOptions.put("harvestability.currentlyharvestable.sneakingonly", false);
        configOptions.put("harvestability.oresonly", false);
        configOptions.put("harvestability.minimal", false);
        configOptions.put("harvestability.unharvestableonly", false);
        configOptions.put("harvestability.toolrequiredonly", true);
        configOptions.put("harvestability.shearability", true);
        configOptions.put("harvestability.shearability.sneakingonly", false);
        configOptions.put("harvestability.silktouchability", true);
        configOptions.put("harvestability.silktouchability.sneakingonly", false);
    }

    @Override
    public void registerClient(IWDMlaClientRegistration registration) {
        for (Map.Entry<String, Boolean> entry : configOptions.entrySet()) {
            // hacky way to set default values to anything but true
            ConfigHandler.instance().getConfig(entry.getKey(), entry.getValue());
            ModuleRegistrar.instance().addConfig("Harvestability", entry.getKey(), "option." + entry.getKey());
        }
        registration.registerBlockComponent(new HarvestToolProvider(), Block.class);
        registration.registerBlockComponent(new LegacyHarvestToolProvider(), Block.class);
    }
}
