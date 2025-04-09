package com.gtnewhorizons.wdmla.plugin.vanilla;

import java.util.UUID;

import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.UsernameCache;

import com.google.common.base.Objects;
import com.gtnewhorizons.wdmla.api.accessor.EntityAccessor;
import com.gtnewhorizons.wdmla.api.provider.IEntityComponentProvider;
import com.gtnewhorizons.wdmla.api.provider.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.config.PluginsConfig;

import joptsimple.internal.Strings;

public enum PetProvider implements IEntityComponentProvider, IServerDataProvider<EntityAccessor> {

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor) {
        if (accessor.getEntity() instanceof EntityTameable pet && pet.isTamed()
                && PluginsConfig.vanilla.pet.showPetSit) {
            tooltip.child(
                    ThemeHelper.INSTANCE.value(
                            StatCollector.translateToLocal("hud.msg.wdmla.sitting"),
                            String.valueOf(pet.isSitting())));
        }

        String ownerUUID = accessor.getServerData().getString("OwnerUUID");
        if (Objects.equal(ownerUUID, Strings.EMPTY) || !PluginsConfig.vanilla.pet.showPetOwner) {
            return;
        }

        String ownerString = UsernameCache.getLastKnownUsername(UUID.fromString(ownerUUID));
        if (Objects.equal(ownerString, Strings.EMPTY)) {
            return;
        }

        tooltip.child(ThemeHelper.INSTANCE.value(StatCollector.translateToLocal("hud.msg.wdmla.owner"), ownerString));
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.PET;
    }

    @Override
    public void appendServerData(NBTTagCompound data, EntityAccessor accessor) {
        accessor.getEntity().writeToNBT(data);
    }
}
