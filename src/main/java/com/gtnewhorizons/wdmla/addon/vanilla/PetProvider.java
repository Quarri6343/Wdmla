package com.gtnewhorizons.wdmla.addon.vanilla;

import com.google.common.base.Objects;
import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IEntityComponentProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;
import com.gtnewhorizons.wdmla.api.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.config.WDMlaConfig;
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
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
        if (accessor.getEntity() instanceof EntityTameable pet && pet.isTamed()
                && WDMlaConfig.instance().getBoolean(VanillaIdentifiers.CONFIG_SHOW_PET_SIT)) {
            tooltip.child(ThemeHelper.INSTANCE.value(LangUtil.translateG("hud.msg.pet.sit"), String.valueOf(pet.isSitting())));
        }

        String ownerUUID = accessor.getServerData().getString("OwnerUUID");
        if (Objects.equal(ownerUUID, Strings.EMPTY) || !WDMlaConfig.instance().getBoolean(VanillaIdentifiers.CONFIG_SHOW_PET_OWNER)) {
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
