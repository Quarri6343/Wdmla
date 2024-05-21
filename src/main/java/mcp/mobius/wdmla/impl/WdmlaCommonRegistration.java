package mcp.mobius.wdmla.impl;

import mcp.mobius.wdmla.api.IComponentProvider;
import mcp.mobius.wdmla.api.IServerDataProvider;
import mcp.mobius.wdmla.api.IWdmlaCommonRegistration;
import mcp.mobius.wdmla.impl.value.BlockAccessor;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class WdmlaCommonRegistration implements IWdmlaCommonRegistration {

    private static final WdmlaClientRegistration INSTANCE = new WdmlaClientRegistration();

    //We can't use HierarchyLookup in Java8
    private final LinkedHashMap<Class<?>, ArrayList<IServerDataProvider<BlockAccessor>>> dataProviders = new LinkedHashMap<>();
    //TODO: use Session

    public static WdmlaClientRegistration instance() {
        return INSTANCE;
    }

    @Override
    public void registerBlockDataProvider(IServerDataProvider<BlockAccessor> provider, Class<?> blockOrTileEntityClass) {
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

    public List<IServerDataProvider<BlockAccessor>> getBlockNBTProviders(Block block, @Nullable TileEntity tileEntity) {
        List<IServerDataProvider<BlockAccessor>> returnList = new ArrayList<>();

        for (Class<?> clazz : dataProviders.keySet()) {
            if (clazz.isInstance(block)) {
                returnList.addAll(dataProviders.get(clazz));
            }
            else if (clazz.isInstance(tileEntity)) {
                returnList.addAll(dataProviders.get(clazz));
            }
        }

        return returnList;
    }

}
