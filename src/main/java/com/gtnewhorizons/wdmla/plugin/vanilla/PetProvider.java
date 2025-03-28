package com.gtnewhorizons.wdmla.plugin.vanilla;

import com.google.common.base.Objects;
import com.gtnewhorizons.wdmla.plugin.PluginsConfig;
import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IEntityComponentProvider;
import com.gtnewhorizons.wdmla.api.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import joptsimple.internal.Strings;
import mcp.mobius.waila.cbcore.LangUtil;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.UsernameCache;

import java.util.UUID;

public enum PetProvider implements IEntityComponentProvider, IServerDataProvider<EntityAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor) {
        if (accessor.getEntity() instanceof EntityTameable pet && pet.isTamed()
                && PluginsConfig.vanilla.pet.showPetSit) {
            tooltip.child(ThemeHelper.INSTANCE.value(LangUtil.translateG("hud.msg.pet.sit"), String.valueOf(pet.isSitting())));
        }

        String ownerUUID = accessor.getServerData().getString("OwnerUUID");
        if (Objects.equal(ownerUUID, Strings.EMPTY) || !PluginsConfig.vanilla.pet.showPetOwner) {
            return;
        }

        String ownerString = UsernameCache.getLastKnownUsername(UUID.fromString(ownerUUID));
        if(Objects.equal(ownerString, Strings.EMPTY)) {
            return;
        }

        tooltip.child(ThemeHelper.INSTANCE.value(LangUtil.translateG("hud.msg.owner"), ownerString));
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
