package com.gtnewhorizons.wdmla.addon.harvestability.proxy;

import static com.gtnewhorizons.wdmla.addon.harvestability.proxy.ProxyTinkersConstruct.creativePickaxes;

import java.lang.reflect.Method;

import com.gtnewhorizons.wdmla.addon.harvestability.HarvestabilityIdentifiers;
import com.gtnewhorizons.wdmla.api.IPluginConfig;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ProxyIguanaTweaks {

    private static Class<?> HarvestLevels = null;
    private static Method proxyGetHarvestLevelName;

    public static void init() {
        try {
            HarvestLevels = Class.forName("iguanaman.iguanatweakstconstruct.util.HarvestLevels");
            proxyGetHarvestLevelName = HarvestLevels.getDeclaredMethod("getHarvestLevelName", int.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getHarvestLevelName(int num) {
        String harvestLevelName = "<Unknown>";

        try {
            harvestLevelName = (String) proxyGetHarvestLevelName.invoke(null, num);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return harvestLevelName;
    }
}
