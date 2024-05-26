package mcp.mobius.wdmla.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import mcp.mobius.wdmla.api.BlockAccessor;
import mcp.mobius.wdmla.api.IComponentProvider;
import mcp.mobius.wdmla.api.IWdmlaClientRegistration;
import mcp.mobius.wdmla.impl.value.BlockAccessorImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;

public class WdmlaClientRegistration implements IWdmlaClientRegistration {

    private static final WdmlaClientRegistration INSTANCE = new WdmlaClientRegistration();

    //We can't use HierarchyLookup in Java8
    private final LinkedHashMap<Class<?>, ArrayList<IComponentProvider<BlockAccessor>>> dataProviders = new LinkedHashMap<>();
    //TODO: use Session

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
        if (providers.contains(provider)) {
            throw new RuntimeException("Trying to register the same provider to Wdmla twice !");
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

    @Override
    public boolean isServerConnected() {
        return ObjectDataCenter.serverConnected;
    }

    @Override
    public boolean isShowDetailsPressed() {
        return Minecraft.getMinecraft().thePlayer.isSneaking(); //TODO: ClientProxy.isShowDetailsPressed
    }

    @Override
    public NBTTagCompound getServerData() {
        return ObjectDataCenter.getServerData();
    }

    @Override
    public BlockAccessor.Builder blockAccessor() {
        Minecraft mc = Minecraft.getMinecraft();

        return new BlockAccessorImpl.Builder().level(mc.theWorld).player(mc.thePlayer).serverConnected(isServerConnected()).serverData(
                getServerData()).showDetails(isShowDetailsPressed());
    }
}
