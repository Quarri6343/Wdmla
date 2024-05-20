package mcp.mobius.wdmla;

import mcp.mobius.wdmla.api.IComponentProvider;
import mcp.mobius.wdmla.api.IWdmlaClientRegistration;
import mcp.mobius.wdmla.impl.value.BlockAccessor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class WdmlaClientRegistration implements IWdmlaClientRegistration {

    private static final WdmlaClientRegistration INSTANCE = new WdmlaClientRegistration();

    private final LinkedHashMap<Class<?>, ArrayList<IComponentProvider<BlockAccessor>>> dataProviders = new LinkedHashMap<>();

    public static WdmlaClientRegistration instance() {
        return INSTANCE;
    }

    public void registerBlockComponent(IComponentProvider<BlockAccessor> provider, Class<?> clazz) {
        if (clazz == null || provider == null) {
            throw new RuntimeException(
                    "Trying to register a null provider or null block ! Please check the stacktrace to know what was the original registration method.");
        }

        if (!dataProviders.containsKey(clazz)) {
            dataProviders.put(clazz, new ArrayList<>());
        }

        ArrayList<IComponentProvider<BlockAccessor>> providers = dataProviders.get(clazz);
        if (providers.contains(provider)){
            throw new RuntimeException(
                    "Trying to register the same provider to Wdmla twice !"
            );
        }

        dataProviders.get(clazz).add(provider);
    }

    public boolean hasProviders(Object obj) {
        for (Class<?> clazz : dataProviders.keySet()) {
            if (clazz.isInstance(obj)) {
                return true;
            }
        }
        return false;
    }

    public List<IComponentProvider<BlockAccessor>> getProviders(Object instance) {
        List<IComponentProvider<BlockAccessor>> returnList = new ArrayList<>();

        for (Class<?> clazz : dataProviders.keySet()) {
            if (clazz.isInstance(instance)) {
                returnList.addAll(dataProviders.get(clazz));
            }
        }

        return returnList;
    }
}
