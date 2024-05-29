package com.gtnewhorizons.wdmla.api;

public interface IWDMlaPlugin {

    default void register(IWDMlaCommonRegistration registration) {

    }

    default void registerClient(IWDMlaClientRegistration registration) {

    }
}
