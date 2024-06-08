package com.gtnewhorizons.wdmla.impl;

import java.util.List;
import java.util.Objects;

import net.minecraft.entity.Entity;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.IWDMlaProvider;
import com.gtnewhorizons.wdmla.impl.lookup.IHierarchyLookup;

import akka.japi.Pair;

public class CommonRegistrationSession {

    private final WDMlaCommonRegistration registration;
    private boolean active;
    private final List<Pair<IServerDataProvider<BlockAccessor>, Class<?>>> blockDataProviders = Lists.newArrayList();
    private final List<Pair<IServerDataProvider<EntityAccessor>, Class<? extends Entity>>> entityDataProviders = Lists
            .newArrayList();

    public CommonRegistrationSession(WDMlaCommonRegistration registration) {
        this.registration = registration;
    }

    private static <T extends IWDMlaProvider, C> void register(T provider, List<Pair<T, Class<? extends C>>> list,
            IHierarchyLookup<T> lookup, Class<? extends C> clazz) {
        Preconditions.checkArgument(lookup.isClassAcceptable(clazz), "Class %s is not acceptable", clazz);
        Objects.requireNonNull(provider.getUid());
        list.add(new Pair<>(provider, clazz));
    }

    public void registerBlockDataProvider(IServerDataProvider<BlockAccessor> dataProvider,
            Class<?> blockOrBlobkEntityClass) {
        register(dataProvider, blockDataProviders, registration.blockDataProviders, blockOrBlobkEntityClass);
    }

    public void registerEntityDataProvider(IServerDataProvider<EntityAccessor> dataProvider,
            Class<? extends Entity> entityClass) {
        register(dataProvider, entityDataProviders, registration.entityDataProviders, entityClass);
    }

    public void reset() {
        blockDataProviders.clear();
        entityDataProviders.clear();
        active = true;
    }

    public void end() {
        Preconditions.checkState(active, "Session is not active");
        active = false;
        blockDataProviders.forEach(pair -> registration.registerBlockDataProvider(pair.first(), pair.second()));
        entityDataProviders.forEach(pair -> registration.registerEntityDataProvider(pair.first(), pair.second()));
    }

    public boolean isActive() {
        return active;
    }
}
