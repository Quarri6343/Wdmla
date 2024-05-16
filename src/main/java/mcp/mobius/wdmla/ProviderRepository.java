package mcp.mobius.wdmla;

import mcp.mobius.wdmla.api.ui.IHUDDataProvider;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ProviderRepository {

    private final LinkedHashMap<Class<?>, ArrayList<IHUDDataProvider>> dataProviders = new LinkedHashMap<>();

    private void registerProvider(IHUDDataProvider dataProvider, Class<?> clazz) {
        if (clazz == null || dataProvider == null) {
            throw new RuntimeException(
                    "Trying to register a null provider or null block ! Please check the stacktrace to know what was the original registration method.");
        }

        if (!dataProviders.containsKey(clazz)) {
            dataProviders.put(clazz, new ArrayList<>());
        }

        ArrayList<IHUDDataProvider> providers = dataProviders.get(clazz);
        if (providers.contains(dataProvider)){
            throw new RuntimeException(
                    "Trying to register the same provider to Wdmla twice !"
            );
        }

        dataProviders.get(clazz).add(dataProvider);
    }

    public boolean hasProviders(Object obj) {
        for (Class clazz : dataProviders.keySet()) {
            if (clazz.isInstance(obj)) {
                return true;
            }
        }
        return false;
    }

    public List<IHUDDataProvider> getProviders(Object obj) {
        List<IHUDDataProvider> returnList = new ArrayList<>();

        for (Class clazz : dataProviders.keySet()) {
            if (clazz.isInstance(obj)) {
                returnList.addAll(dataProviders.get(clazz));
            }
        }

        return returnList;
    }
}
