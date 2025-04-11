package com.gtnewhorizons.wdmla.api.provider;

import com.gtnewhorizons.wdmla.api.IWDMlaClientRegistration;
import com.gtnewhorizons.wdmla.api.accessor.Accessor;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;

// spotless:off
/**
 * Gets information from accessor object and write it in tooltip.
 * Example implementation:
 * <pre>
 * {@code
 * public enum ExampleProvider implements IBlockComponentProvider {
 *
 *     INSTANCE;
 *
 *     @Override
 *     public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
 *         tooltip.text("This is an example.");
 *     }
 *
 *     @Override
 *     public ResourceLocation getUid() {
 *         return "minecraft:example;
 *     }
 * }
 * }
 * </pre>
 * It is the good practice to use enum singleton on child classes.<br>
 * Then, we can make a {@link com.gtnewhorizons.wdmla.api.IWDMlaPlugin} implementation to register actual plugins there
 * @param <T> The type of accessor this provider accepts.
 */
// spotless:on
public interface IComponentProvider<T extends Accessor> extends IToggleableProvider {

    /**
     * Write tooltip based on information provided by accessor.
     * 
     * @param tooltip  tooltip object which can append child
     * @param accessor Accessor which contains target object that matches this provider. Defined in
     *                 {@link IWDMlaClientRegistration} register method
     */
    void appendTooltip(ITooltip tooltip, T accessor);
}
