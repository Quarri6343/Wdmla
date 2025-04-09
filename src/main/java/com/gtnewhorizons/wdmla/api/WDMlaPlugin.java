package com.gtnewhorizons.wdmla.api;

import com.gtnewhorizons.wdmla.api.provider.IWDMlaProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The plugin annotate this must implement IWDMlaPlugin
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WDMlaPlugin {

    /**
     * The uid of the plugin.<br>
     * don't get confused with {@link IWDMlaProvider#getUid()}. There are no direct association between them (for now)
     */
    String uid() default "";

    /**
     * Mod ID dependencies of the plugin
     */
    String[] dependencies() default {};
}
