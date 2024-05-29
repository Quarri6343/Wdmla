package com.gtnewhorizons.wdmla.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

import org.jetbrains.annotations.Nullable;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.IWDMlaCommonRegistration;

public class WDMlaCommonRegistration implements IWDMlaCommonRegistration {

    private static final WDMlaCommonRegistration INSTANCE = new WDMlaCommonRegistration();

    // We can't use HierarchyLookup in Java8
    private final LinkedHashMap<Class<?>, ArrayList<IServerDataProvider<BlockAccessor>>> dataProviders = new LinkedHashMap<>();
    // TODO: use Session

    public static WDMlaCommonRegistration instance() {
        return INSTANCE;
    }

    @Override
    public void registerBlockDataProvider(IServerDataProvider<BlockAccessor> provider,
            Class<?> blockOrTileEntityClass) {
        if (blockOrTileEntityClass == null || provider == null) {
            throw new RuntimeException(
                    "Trying to register a null provider or null block ! Please check the stacktrace to know what was the original registration method.");
        }

        if (!dataProviders.containsKey(blockOrTileEntityClass)) {
            dataProviders.put(blockOrTileEntityClass, new ArrayList<>());
        }

        ArrayList<IServerDataProvider<BlockAccessor>> providers = dataProviders.get(blockOrTileEntityClass);
        if (providers.contains(provider)) {
            throw new RuntimeException("Trying to register the same provider to Wdmla twice !");
        }

        dataProviders.get(blockOrTileEntityClass).add(provider);
    }

    public boolean hasProviders(Object obj) {
        for (Class<?> clazz : dataProviders.keySet()) {
            if (clazz.isInstance(obj)) {
                return true;
            }
        }
        return false;
    }

    public List<IServerDataProvider<BlockAccessor>> getBlockNBTProviders(Block block, @Nullable TileEntity tileEntity) {
        List<IServerDataProvider<BlockAccessor>> returnList = new ArrayList<>();

        for (Class<?> clazz : dataProviders.keySet()) {
            if (clazz.isInstance(block)) {
                returnList.addAll(dataProviders.get(clazz));
            } else if (clazz.isInstance(tileEntity)) {
                returnList.addAll(dataProviders.get(clazz));
            }
        }

        return returnList;
    }

}
