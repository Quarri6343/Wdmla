package com.gtnewhorizons.wdmla.impl;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;
import com.gtnewhorizons.wdmla.impl.lookup.HierarchyLookup;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;

import com.google.common.collect.Maps;
import com.gtnewhorizons.wdmla.api.*;

import mcp.mobius.waila.Waila;

public class WDMlaClientRegistration implements IWDMlaClientRegistration {

    private static final WDMlaClientRegistration INSTANCE = new WDMlaClientRegistration();

    public final HierarchyLookup<IComponentProvider<BlockAccessor>> blockIconProviders;
    public final HierarchyLookup<IComponentProvider<BlockAccessor>> blockComponentProviders;

    public final Map<Class<Accessor>, AccessorClientHandler<Accessor>> accessorHandlers = Maps.newIdentityHashMap();
    private ClientRegistrationSession session;

    WDMlaClientRegistration() {
        blockIconProviders = new HierarchyLookup<>(Block.class);
        blockComponentProviders = new HierarchyLookup<>(Block.class);
    }

    public static WDMlaClientRegistration instance() {
        return INSTANCE;
    }

    @Override
    public void registerBlockIcon(IComponentProvider<BlockAccessor> provider, Class<? extends Block> blockClass) {
        if (isSessionActive()) {
            session.registerBlockIcon(provider, blockClass);
        } else {
            blockIconProviders.register(blockClass, provider);
//            tryAddConfig(provider);
        }
    }

    @Override
    public void registerBlockComponent(IComponentProvider<BlockAccessor> provider, Class<? extends Block> blockClass) {
        if (isSessionActive()) {
            session.registerBlockComponent(provider, blockClass);
        } else {
            blockComponentProviders.register(blockClass, provider);
//            tryAddConfig(provider);
        }
    }

    public List<IComponentProvider<BlockAccessor>> getBlockProviders(
            Block block,
            Predicate<IComponentProvider<? extends Accessor>> filter) {
        return blockComponentProviders.get(block).stream().filter(filter).collect(Collectors.toList());
    }

    public List<IComponentProvider<BlockAccessor>> getBlockIconProviders(
            Block block,
            Predicate<IComponentProvider<? extends Accessor>> filter) {
        return blockIconProviders.get(block).stream().filter(filter).collect(Collectors.toList());
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

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Accessor> void registerAccessorHandler(Class<T> clazz, AccessorClientHandler<T> handler) {
        accessorHandlers.put((Class<Accessor>) clazz, (AccessorClientHandler<Accessor>) handler);
    }

    @Override
    public AccessorClientHandler<Accessor> getAccessorHandler(Class<? extends Accessor> clazz) {
        return Objects.requireNonNull(accessorHandlers.get(clazz), () -> "No accessor handler for " + clazz);
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
}
