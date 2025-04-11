package mcp.mobius.waila.api.impl;

import java.util.HashMap;

import mcp.mobius.waila.api.BackwardCompatibility;

/**
 * All legacy Waila addons should register their boolean configs with this for storing them in Waila config file.
 * 
 * @deprecated For static config entry, use {@link com.gtnewhorizon.gtnhlib.config.Config}.
 */
@BackwardCompatibility
@Deprecated
public class ConfigModule {

    String modName;
    HashMap<String, String> options;

    public ConfigModule(String _modName) {
        this.modName = _modName;
        this.options = new HashMap<>();
    }

    public ConfigModule(String _modName, HashMap<String, String> _options) {
        this.modName = _modName;
        this.options = _options;
    }

    public void addOption(String key, String name) {
        this.options.put(key, name);
    }

}
