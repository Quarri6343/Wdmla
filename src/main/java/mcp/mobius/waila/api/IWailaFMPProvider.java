package mcp.mobius.waila.api;

import java.util.List;

import net.minecraft.item.ItemStack;

/**
 * This was the special provider for Forge MultiParts mod <br>
 * Don't use.
 * Normal version: {@link IWailaDataProvider}
 * <br>
 * <br>
 * Callback class interface used to provide FMP tooltip informations to Waila.<br>
 * All methods in this interface shouldn't to be called by the implementing mod. An instance of the class is to be
 * registered to Waila via the {@link IWailaRegistrar} instance provided in the original registration callback method
 * (cf. {@link IWailaRegistrar} documentation for more information).
 *
 * @deprecated api rework
 * @author ProfMobius
 *
 */
@Deprecated
@BackwardCompatibility
public interface IWailaFMPProvider {

    /**
     * Callback used to add lines to one of the three sections of the tooltip (Head, Body, Tail).</br>
     * Will be used if the implementing class is registered via {@link IWailaRegistrar}.registerHeadProvider
     * client side.</br>
     * You are supposed to always return the modified input currenttip.</br>
     * 
     * @param itemStack  Current block scanned, in ItemStack form.
     * @param currenttip Current list of tooltip lines (might have been processed by other providers and might be
     *                   processed by other providers).
     * @param accessor   Contains most of the relevant information about the current environment.
     * @param config     Current configuration of Waila.
     * @return Modified input currenttip
     */
    List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaFMPAccessor accessor,
            IWailaConfigHandler config);

    /**
     * Callback used to add lines to one of the three sections of the tooltip (Head, Body, Tail).</br>
     * Will be used if the implementing class is registered via {@link IWailaRegistrar}.registerBodyProvider
     * client side.</br>
     * You are supposed to always return the modified input currenttip.</br>
     * 
     * @param itemStack  Current block scanned, in ItemStack form.
     * @param currenttip Current list of tooltip lines (might have been processed by other providers and might be
     *                   processed by other providers).
     * @param accessor   Contains most of the relevant information about the current environment.
     * @param config     Current configuration of Waila.
     * @return Modified input currenttip
     */
    List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaFMPAccessor accessor,
            IWailaConfigHandler config);

    /**
     * Callback used to add lines to one of the three sections of the tooltip (Head, Body, Tail).</br>
     * Will be used if the implementing class is registered via {@link IWailaRegistrar}.registerTailProvider
     * client side.</br>
     * You are supposed to always return the modified input currenttip.</br>
     * 
     * @param itemStack  Current block scanned, in ItemStack form.
     * @param currenttip Current list of tooltip lines (might have been processed by other providers and might be
     *                   processed by other providers).
     * @param accessor   Contains most of the relevant information about the current environment.
     * @param config     Current configuration of Waila.
     * @return Modified input currenttip
     */
    List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaFMPAccessor accessor,
            IWailaConfigHandler config);
}
