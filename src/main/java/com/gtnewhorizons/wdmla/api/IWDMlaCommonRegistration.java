package com.gtnewhorizons.wdmla.api;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface IWDMlaCommonRegistration {

    void registerBlockDataProvider(IServerDataProvider<BlockAccessor> dataProvider, Class<?> blockOrBlockEntityClass);
}
