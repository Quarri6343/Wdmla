package com.gtnewhorizons.wdmla.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.gtnewhorizons.wdmla.api.Accessor;
import com.gtnewhorizons.wdmla.api.AccessorClientHandler;
import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.EntityAccessorImpl;
import com.gtnewhorizons.wdmla.api.IComponentProvider;
import com.gtnewhorizons.wdmla.api.IConfigProvider;
import com.gtnewhorizons.wdmla.api.IWDMlaClientRegistration;
import com.gtnewhorizons.wdmla.impl.lookup.HierarchyLookup;

import mcp.mobius.waila.Waila;

public class WDMlaClientRegistration implements IWDMlaClientRegistration {

    private static final WDMlaClientRegistration INSTANCE = new WDMlaClientRegistration();

    public final HierarchyLookup<IComponentProvider<BlockAccessor>> blockComponentProviders;
    public final HierarchyLookup<IComponentProvider<EntityAccessor>> entityComponentProviders;
    private final List<IComponentProvider<?>> allProviders;

    public final Map<Class<Accessor>, AccessorClientHandler<Accessor>> accessorHandlers = Maps.newIdentityHashMap();

    public final List<IConfigProvider> configProviders = new ArrayList<>();

    private ClientRegistrationSession session;

    WDMlaClientRegistration() {
        blockComponentProviders = new HierarchyLookup<>(Block.class);
        entityComponentProviders = new HierarchyLookup<>(Entity.class);
        allProviders = new ArrayList<>();
    }

    public static WDMlaClientRegistration instance() {
        return INSTANCE;
    }

    @Override
    public void registerEntityComponent(IComponentProvider<EntityAccessor> provider,
            Class<? extends Entity> entityClass) {
        if (isSessionActive()) {
            session.registerEntityComponent(provider, entityClass);
        } else {
            entityComponentProviders.register(entityClass, provider);
            allProviders.add(provider);
        }
    }

    @Override
    public void registerBlockComponent(IComponentProvider<BlockAccessor> provider, Class<? extends Block> blockClass) {
        if (isSessionActive()) {
            session.registerBlockComponent(provider, blockClass);
        } else {
            blockComponentProviders.register(blockClass, provider);
            allProviders.add(provider);
        }
    }

    public List<IComponentProvider<BlockAccessor>> getBlockProviders(Block block,
            Predicate<IComponentProvider<? extends Accessor>> filter) {
        return blockComponentProviders.get(block).stream().filter(filter).collect(Collectors.toList());
    }

    public List<IComponentProvider<EntityAccessor>> getEntityProviders(Entity entity,
            Predicate<IComponentProvider<? extends Accessor>> filter) {
        return entityComponentProviders.get(entity).stream().filter(filter).collect(Collectors.toList());
    }

    public ImmutableList<IComponentProvider<?>> getAllProvidersWithoutInfo() {
        return ImmutableList.copyOf(allProviders);
    }

    @Override
    public boolean isServerConnected() {
        return Waila.instance.serverPresent;
    }

    @Override
    public boolean isShowDetailsPressed() {
        return Minecraft.getMinecraft().thePlayer.isSneaking(); // TODO: ClientProxy.isShowDetailsPressed
    }

    @Override
    public NBTTagCompound getServerData() {
        return ObjectDataCenter.getServerData();
    }

    @Override
    public BlockAccessor.Builder blockAccessor() {
        Minecraft mc = Minecraft.getMinecraft();

        return new BlockAccessorImpl.Builder().level(mc.theWorld).player(mc.thePlayer)
                .serverConnected(isServerConnected()).serverData(getServerData()).showDetails(isShowDetailsPressed());
    }

    public EntityAccessor.Builder entityAccessor() {
        Minecraft mc = Minecraft.getMinecraft();

        return new EntityAccessorImpl.Builder().level(mc.theWorld).player(mc.thePlayer)
                .serverConnected(isServerConnected()).serverData(getServerData()).showDetails(isShowDetailsPressed());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Accessor> void registerAccessorHandler(Class<T> clazz, AccessorClientHandler<T> handler) {
        accessorHandlers.put((Class<Accessor>) clazz, (AccessorClientHandler<Accessor>) handler);
    }

    @Override
    public AccessorClientHandler<Accessor> getAccessorHandler(Class<? extends Accessor> clazz) {
        return Objects.requireNonNull(accessorHandlers.get(clazz), () -> "No accessor handler for " + clazz);
    }

    public void loadComplete() {
        var priorities = WDMlaCommonRegistration.instance().priorities;
        blockComponentProviders.invalidate();
        blockComponentProviders.loadComplete(priorities);
        entityComponentProviders.invalidate();
        entityComponentProviders.loadComplete(priorities);
        session = null;
    }

    public void startSession() {
        if (session == null) {
            session = new ClientRegistrationSession(this);
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

    public void registerConfigComponent(IConfigProvider provider) {
        configProviders.add(provider);
    }
}
