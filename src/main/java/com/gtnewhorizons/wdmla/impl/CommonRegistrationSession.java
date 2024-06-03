package com.gtnewhorizons.wdmla.impl;


import akka.japi.Pair;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.IWDMlaProvider;
import com.gtnewhorizons.wdmla.impl.lookup.IHierarchyLookup;

import java.util.List;
import java.util.Objects;

public class CommonRegistrationSession {
    private final WDMlaCommonRegistration registration;
    private boolean active;
    private final List<Pair<IServerDataProvider<BlockAccessor>, Class<?>>> blockDataProviders = Lists.newArrayList();

    public CommonRegistrationSession(WDMlaCommonRegistration registration) {
        this.registration = registration;
    }

    private static <T extends IWDMlaProvider, C> void register(
            T provider,
            List<Pair<T, Class<? extends C>>> list,
            IHierarchyLookup<T> lookup,
            Class<? extends C> clazz) {
        Preconditions.checkArgument(
                lookup.isClassAcceptable(clazz),
                "Class %s is not acceptable",
                clazz);
        Objects.requireNonNull(provider.getUid());
        list.add(new Pair<>(provider, clazz));
    }

    public void registerBlockDataProvider(IServerDataProvider<BlockAccessor> dataProvider, Class<?> blockOrBlobkEntityClass) {
        register(dataProvider, blockDataProviders, registration.blockDataProviders, blockOrBlobkEntityClass);
    }

    public void reset() {
        blockDataProviders.clear();
        active = true;
    }

    public void end() {
        Preconditions.checkState(active, "Session is not active");
        active = false;
        blockDataProviders.forEach(pair -> registration.registerBlockDataProvider(pair.first(), pair.second()));
    }

    public boolean isActive() {
        return active;
    }
}
