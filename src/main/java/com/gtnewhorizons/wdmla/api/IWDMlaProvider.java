package com.gtnewhorizons.wdmla.api;

import net.minecraft.util.ResourceLocation;

public interface IWDMlaProvider {

    /**
     * The unique id of this provider. Providers from different registries can have the same id.
     */
    ResourceLocation getUid();

    /**
     * Affects the registration and display order showing in the tooltip. Leave this to default if you want to show
     * normal tooltip.
     * <p>
     * If you want to show your tooltip a bit to the bottom, you should return a value greater than 0, and less than
     * 5000. If it is greater than 5000, the content will not be collapsed in lite mode.
     * <p>
     * Registration of Default component such as item name occurs on -10000, and Registration of Harvestability occurs
     * on -8000. You can add your own default component between them.
     */
    default int getDefaultPriority() {
        return TooltipPosition.BODY;
    }
}
