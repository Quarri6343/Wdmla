package com.gtnewhorizons.wdmla;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.gtnewhorizons.wdmla.command.PrintUnsupportedTEsCommand;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;

import org.jetbrains.annotations.Nullable;

import com.gtnewhorizons.wdmla.api.Accessor;
import com.gtnewhorizons.wdmla.api.Mods;
import com.gtnewhorizons.wdmla.api.view.ClientViewGroup;
import com.gtnewhorizons.wdmla.api.view.IClientExtensionProvider;
import com.gtnewhorizons.wdmla.api.view.ViewGroup;
import com.gtnewhorizons.wdmla.command.GenerateDumpCommand;
import com.gtnewhorizons.wdmla.config.WDMlaConfig;
import com.gtnewhorizons.wdmla.overlay.WDMlaTickHandler;
import com.gtnewhorizons.wdmla.plugin.harvestability.proxy.ProxyGregTech;
import com.gtnewhorizons.wdmla.plugin.harvestability.proxy.ProxyIguanaTweaks;
import com.gtnewhorizons.wdmla.plugin.harvestability.proxy.ProxyTinkersConstruct;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.utils.WailaExceptionHandler;

public class ClientProxy extends CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        File wdmlaConfigFolder = new File(event.getModConfigurationDirectory().getPath(), "WDMla");
        File wdmlaConfig = new File(wdmlaConfigFolder, "plugins_autogen.cfg");
        new WDMlaConfig(wdmlaConfig);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        WDMlaTickHandler tickHandler = new WDMlaTickHandler();
        FMLCommonHandler.instance().bus().register(tickHandler);
        MinecraftForge.EVENT_BUS.register(tickHandler);
        FMLCommonHandler.instance().bus().register(this);
        ClientCommandHandler.instance.registerCommand(new GenerateDumpCommand());
        if(WDMla.isDevEnv()) {
            ClientCommandHandler.instance.registerCommand(new PrintUnsupportedTEsCommand());
        }
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        if (Mods.TCONSTUCT.isLoaded()) {
            ProxyTinkersConstruct.init();
        }
        if (Mods.IGUANATWEAKS.isLoaded()) {
            ProxyIguanaTweaks.init();
        }
        if (Mods.GREGTECH.isLoaded()) {
            ProxyGregTech.init();
        }
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(WDMla.MODID)) {
            WDMlaConfig.instance().save();
            WDMlaConfig.instance().reloadConfig();
            loadComplete(); // sort priorities

            ConfigHandler.instance().reloadDefaultConfig();
        }
    }

    @Nullable
    public static <IN, OUT> List<ClientViewGroup<OUT>> mapToClientGroups(Accessor accessor, ResourceLocation key,
            Function<NBTTagCompound, Map.Entry<ResourceLocation, List<ViewGroup<IN>>>> decoder,
            Function<ResourceLocation, IClientExtensionProvider<IN, OUT>> mapper) {
        NBTBase tagBase = accessor.getServerData().getTag(key.toString());
        if (!(tagBase instanceof NBTTagCompound tag)) {
            return null;
        }
        Map.Entry<ResourceLocation, List<ViewGroup<IN>>> entry = decoder.apply(tag);
        if (entry == null) {
            return null;
        }
        IClientExtensionProvider<IN, OUT> provider = mapper.apply(entry.getKey());
        if (provider == null) {
            return null;
        }
        try {
            return provider.getClientGroups(accessor, entry.getValue());
        } catch (Exception e) {
            WailaExceptionHandler.handleErr(e, provider.getClass().getName(), null);
            return null;
        }
    }
}
