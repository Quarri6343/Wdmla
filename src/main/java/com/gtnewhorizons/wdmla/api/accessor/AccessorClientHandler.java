package com.gtnewhorizons.wdmla.api.accessor;

import java.util.function.Function;

import org.jetbrains.annotations.ApiStatus;

import com.gtnewhorizons.wdmla.api.provider.IWDMlaProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;

/**
 * Classes implement this handles main accessor object of specific type, passing it to provider or server.<br>
 * Only one instance of AccessorHandler per type can be registered in Registration object with
 * {@link com.gtnewhorizons.wdmla.api.IWDMlaClientRegistration#registerAccessorHandler(Class, AccessorClientHandler)}.<br>
 * Although this class can be implemented outside WDMla, Waila packet only supports Tile Entity and entity, so it can
 * not be used anywhere in current situation.
 * 
 * @param <T> Accessor type this class want to take care of
 */
@ApiStatus.Internal
public interface AccessorClientHandler<T extends Accessor> {

    /**
     * Mainly used to determine is the accessor target blacklisted (not implemented).
     * 
     * @param accessor accessor object
     * @return Should the information of accessor be displayed as tooltip in current screen or not.<br>
     */
    boolean shouldDisplay(T accessor);

    /**
     * @param accessor accessor object
     * @return Checks if it is needed to access server to fetch the data for accessor.
     */
    boolean shouldRequestData(T accessor);

    /**
     * Sends packet to request data about accessor from server
     * 
     * @param accessor accessor object
     */
    void requestData(T accessor);

    /**
     * Collects tooltip from accessor, using registered providers.
     * 
     * @param accessor        accessor object which may have corresponding provider to collect tooltip
     * @param tooltipProvider provides suitable tooltip object for specified provider
     */
    void gatherComponents(T accessor, Function<IWDMlaProvider, ITooltip> tooltipProvider);
}
