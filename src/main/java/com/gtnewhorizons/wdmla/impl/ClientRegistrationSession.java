package com.gtnewhorizons.wdmla.impl;

import java.util.List;
import java.util.Objects;

import net.minecraft.block.Block;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IComponentProvider;
import com.gtnewhorizons.wdmla.api.IToggleableProvider;
import com.gtnewhorizons.wdmla.api.IWDMlaProvider;
import com.gtnewhorizons.wdmla.impl.lookup.IHierarchyLookup;

import akka.japi.Pair;

public class ClientRegistrationSession {

    private final WDMlaClientRegistration registration;
    private boolean active;
    private final List<Pair<IComponentProvider<BlockAccessor>, Class<? extends Block>>> blockComponentProviders = Lists
            .newArrayList();

    public ClientRegistrationSession(WDMlaClientRegistration registration) {
        this.registration = registration;
    }

    private static <T extends IWDMlaProvider, C> void register(T provider, List<Pair<T, Class<? extends C>>> list,
            IHierarchyLookup<T> lookup, Class<? extends C> clazz) {
        Preconditions.checkArgument(lookup.isClassAcceptable(clazz), "Class %s is not acceptable", clazz);
        Objects.requireNonNull(provider.getUid());
        list.add(new Pair<>(provider, clazz));
    }

    public void registerBlockComponent(IComponentProvider<BlockAccessor> provider, Class<? extends Block> blockClass) {
        register(provider, blockComponentProviders, registration.blockComponentProviders, blockClass);
        tryAddConfig(provider);
    }

    private void tryAddConfig(IToggleableProvider provider) {
        // ResourceLocation key = provider.getUid();
        // if (!provider.isRequired()) {
        // configIds.add(key);
        // }
    }

    public void reset() {
        blockComponentProviders.clear();
        active = true;
    }

    public void end() {
        Preconditions.checkState(active, "Session is not active");
        active = false;
        blockComponentProviders.forEach(pair -> registration.registerBlockComponent(pair.first(), pair.second()));
    }

    public boolean isActive() {
        return active;
    }
}
