package mcp.mobius.waila.api;

import java.util.HashMap;
import java.util.Set;

/**
 * This interface was for handling configuration file.<br>
 * All legacy plugins were registering their own config to here so all of them can be edited in Waila itself.<br>
 * Note this class is still being used actively to proxy Waila.cfg content to WDMla.<br>
 * Also, disabling this class means addons will have unexpected behaviour. <br>
 * <br>
 * Read-only interface for Waila internal config storage.<br>
 * An instance of this interface is passed to most of Waila callbacks as a way to change the behavior depending on
 * client settings. Modern version : {@link com.gtnewhorizons.wdmla.config.WDMlaConfig}
 * 
 * @author ProfMobius
 *
 */
@BackwardCompatibility
public interface IWailaConfigHandler {

    /**
     * Returns a set of all the currently loaded modules in the config handler.
     */
    Set<String> getModuleNames();

    /**
     * Returns all the currently available options for a given module
     * 
     * @param modName Module name
     */
    HashMap<String, String> getConfigKeys(String modName);

    /**
     * Returns the current value of an option (true/false) with a default value if not set.
     * 
     * @param key      Option to lookup
     * @param defvalue Default values
     * @return Value of the option or defvalue if not set.
     */
    boolean getConfig(String key, boolean defvalue);

    /**
     * Returns the current value of an option (true/false) with a default value true if not set
     * 
     * @param key Option to lookup
     * @return Value of the option or true if not set.
     */
    boolean getConfig(String key);
}
