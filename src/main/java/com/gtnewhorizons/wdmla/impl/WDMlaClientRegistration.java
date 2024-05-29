package com.gtnewhorizons.wdmla.impl;

import java.util.*;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;

import com.google.common.collect.Maps;
import com.gtnewhorizons.wdmla.api.*;

import mcp.mobius.waila.Waila;

public class WDMlaClientRegistration implements IWDMlaClientRegistration {

    private static final WDMlaClientRegistration INSTANCE = new WDMlaClientRegistration();

    // We can't use HierarchyLookup in Java8
    private final LinkedHashMap<Class<?>, ArrayList<IComponentProvider<BlockAccessor>>> blockIconProviders;
    private final LinkedHashMap<Class<?>, ArrayList<IComponentProvider<BlockAccessor>>> blockComponentProviders;
    // TODO: use Session

    public final Map<Class<Accessor>, AccessorClientHandler<Accessor>> accessorHandlers = Maps.newIdentityHashMap();

    WDMlaClientRegistration() {
        blockIconProviders = new LinkedHashMap<>();
        blockComponentProviders = new LinkedHashMap<>();
    }

    public static WDMlaClientRegistration instance() {
        return INSTANCE;
    }

    @Override
    public void registerBlockIcon(IComponentProvider<BlockAccessor> provider, Class<?> clazz) {
        if (clazz == null || provider == null) {
            throw new RuntimeException(
                    "Trying to register a null provider or null block ! Please check the stacktrace to know what was the original registration method.");
        }

        if (!blockIconProviders.containsKey(clazz)) {
            blockIconProviders.put(clazz, new ArrayList<>());
        }

        ArrayList<IComponentProvider<BlockAccessor>> providers = blockIconProviders.get(clazz);
        if (providers.contains(provider)) {
            throw new RuntimeException("Trying to register the same provider to Wdmla twice !");
        }

        blockIconProviders.get(clazz).add(provider);
    }

    @Override
    public void registerBlockComponent(IComponentProvider<BlockAccessor> provider, Class<?> clazz) {
        if (clazz == null || provider == null) {
            throw new RuntimeException(
                    "Trying to register a null provider or null block ! Please check the stacktrace to know what was the original registration method.");
        }

        if (!blockComponentProviders.containsKey(clazz)) {
            blockComponentProviders.put(clazz, new ArrayList<>());
        }

        ArrayList<IComponentProvider<BlockAccessor>> providers = blockComponentProviders.get(clazz);
        if (providers.contains(provider)) {
            throw new RuntimeException("Trying to register the same provider to Wdmla twice !");
        }

        blockComponentProviders.get(clazz).add(provider);
    }

    public List<IComponentProvider<BlockAccessor>> getBlockProviders(Object instance) {
        List<IComponentProvider<BlockAccessor>> returnList = new ArrayList<>();

        for (Class<?> clazz : blockComponentProviders.keySet()) {
            if (clazz.isInstance(instance)) {
                returnList.addAll(blockComponentProviders.get(clazz));
            }
        }

        return returnList;
    }

    public List<IComponentProvider<BlockAccessor>> getBlockIconProviders(Object instance) {
        List<IComponentProvider<BlockAccessor>> returnList = new ArrayList<>();

        for (Class<?> clazz : blockIconProviders.keySet()) {
            if (clazz.isInstance(instance)) {
                returnList.addAll(blockIconProviders.get(clazz));
            }
        }

        return returnList;
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
}
