package com.gtnewhorizons.wdmla.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.Nullable;

import com.google.common.base.Preconditions;
import com.gtnewhorizons.wdmla.WDMla;
import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.IWDMlaCommonRegistration;
import com.gtnewhorizons.wdmla.api.IWDMlaProvider;
import com.gtnewhorizons.wdmla.impl.lookup.HierarchyLookup;
import com.gtnewhorizons.wdmla.impl.lookup.PairHierarchyLookup;

public class WDMlaCommonRegistration implements IWDMlaCommonRegistration {

    private static final WDMlaCommonRegistration INSTANCE = new WDMlaCommonRegistration();

    public final PairHierarchyLookup<IServerDataProvider<BlockAccessor>> blockDataProviders;
    public final HierarchyLookup<IServerDataProvider<EntityAccessor>> entityDataProviders;
    public final PriorityStore<ResourceLocation, IWDMlaProvider> priorities;

    private CommonRegistrationSession session;

    public static WDMlaCommonRegistration instance() {
        return INSTANCE;
    }

    WDMlaCommonRegistration() {
        blockDataProviders = new PairHierarchyLookup<>(
                new HierarchyLookup<>(Block.class),
                new HierarchyLookup<>(TileEntity.class));
        entityDataProviders = new HierarchyLookup<>(Entity.class);
        priorities = new PriorityStore<>(IWDMlaProvider::getDefaultPriority, IWDMlaProvider::getUid);
        priorities.setSortingFunction((store, allKeys) -> {
            List<ResourceLocation> keys = allKeys.stream()
                    // .filter(PluginConfig::isPrimaryKey)
                    .sorted(Comparator.comparingInt(store::byKey)).collect(Collectors.toCollection(ArrayList::new));
            // allKeys.stream().filter(Predicate.not(PluginConfig::isPrimaryKey)).forEach($ -> {
            // int index = keys.indexOf(PluginConfig.getPrimaryKey($));
            // keys.add(index + 1, $);
            // });
            return keys;
        });
        priorities.configurable(WDMla.MODID + "/sort-order");
    }

    @Override
    public void registerBlockDataProvider(IServerDataProvider<BlockAccessor> dataProvider,
            Class<?> blockOrTileEntityClass) {
        if (isSessionActive()) {
            session.registerBlockDataProvider(dataProvider, blockOrTileEntityClass);
        } else {
            blockDataProviders.register(blockOrTileEntityClass, dataProvider);
        }
    }

    @Override
    public void registerEntityDataProvider(IServerDataProvider<EntityAccessor> dataProvider,
            Class<? extends Entity> entityClass) {
        if (isSessionActive()) {
            session.registerEntityDataProvider(dataProvider, entityClass);
        } else {
            entityDataProviders.register(entityClass, dataProvider);
        }
    }

    public List<IServerDataProvider<BlockAccessor>> getBlockNBTProviders(Block block, @Nullable TileEntity tileEntity) {
        if (tileEntity == null) {
            return blockDataProviders.first.get(block);
        }
        return blockDataProviders.getMerged(block, tileEntity);
    }

    public List<IServerDataProvider<EntityAccessor>> getEntityNBTProviders(Entity entity) {
        return entityDataProviders.get(entity);
    }

    public void loadComplete() {
        blockDataProviders.loadComplete(priorities);
        entityDataProviders.loadComplete(priorities);
        session = null;
    }

    public void startSession() {
        if (session == null) {
            session = new CommonRegistrationSession(this);
        }
        session.reset();
    }

    public void endSession() {
        Preconditions.checkState(session != null, "Session not started");
        session.end();
    }

    public boolean isSessionActive() {
        return session != null && session.isActive();
    }
}
