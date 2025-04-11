package mcp.mobius.waila.api;

import net.minecraft.item.ItemStack;

/**
 * This was the special accessor for Forge MultiParts mod. <br>
 * Don't use. Normal version: {@link IWailaCommonAccessor} <br>
 * <br>
 * Callback class interface used to provide FMP decorator.<br>
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
public interface IWailaFMPDecorator {

    /**
     * Callback for the decorator. It provides a standard GL stack positioned on the block.</br>
     * Will be used if the implementing class is registered via {@link IWailaRegistrar}.registerDecorator.</br>
     * 
     * @param itemStack Current block scanned, in ItemStack form.
     * @param accessor  Contains most of the relevant information about the current environment.
     * @param config    Current configuration of Waila.
     */
    void decorateBlock(ItemStack itemStack, IWailaFMPAccessor accessor, IWailaConfigHandler config);
}
