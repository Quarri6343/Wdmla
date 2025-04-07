package com.gtnewhorizons.wdmla.plugin.vanilla;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IEntityComponentProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;

import cpw.mods.fml.common.registry.VillagerRegistry;
import net.minecraft.util.StatCollector;

public enum VillagerProfessionProvider implements IEntityComponentProvider {

    INSTANCE;

    private static final String[] VANILLA_VILLAGERS = { "farmer", "librarian", "priest", "blacksmith", "butcher" };

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor) {
        if (accessor.getEntity() instanceof EntityVillager villager) {
            String name = getVillagerName(villager.getProfession());
            tooltip.child(ThemeHelper.INSTANCE.info(StatCollector.translateToLocal("hud.msg.wdmla.villager.profession." + name)));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.VILLAGER_PROFESSION;
    }

    // copied from Wawla as this is the only way to get villager name dynamically
    public static String getVillagerName(int id) {
        ResourceLocation skin = VillagerRegistry.getVillagerSkin(id, null);
        if (id >= 0 && id <= 4) {
            return VANILLA_VILLAGERS[id];
        }

        if (skin != null) {
            String path = skin.getResourcePath();
            return skin.getResourceDomain() + "." + path.substring(path.lastIndexOf("/") + 1, path.length() - 4);
        }

        return "nodata";
    }
}
