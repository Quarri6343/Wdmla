package com.gtnewhorizons.wdmla.test;

import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IEntityComponentProvider;
import com.gtnewhorizons.wdmla.api.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.TextComponent;

public enum TestEntityProvider implements IEntityComponentProvider, IServerDataProvider<EntityAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor) {
        int random = accessor.getServerData().getInteger("random");
        tooltip.child(new TextComponent("Recieved Server Entity Data: " + random));
    }

    @Override
    public void appendServerData(NBTTagCompound data, EntityAccessor accessor) {
        data.setInteger("random", new Random().nextInt(11));
    }

    @Override
    public ResourceLocation getUid() {
        return Identifiers.TEST_ENTITY;
    }
}
