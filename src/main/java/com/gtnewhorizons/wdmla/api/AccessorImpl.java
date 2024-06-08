package com.gtnewhorizons.wdmla.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public abstract class AccessorImpl implements Accessor {

    private final World world;
    private final EntityPlayer player;
    private final MovingObjectPosition hit;
    private final boolean serverConnected;
    private final boolean showDetails;
    protected boolean verify;
    private final NBTTagCompound serverData;

    public AccessorImpl(World world, EntityPlayer player, MovingObjectPosition hit, boolean serverConnected,
            boolean showDetails, NBTTagCompound serverData) {
        this.world = world;
        this.player = player;
        this.hit = hit;
        this.showDetails = showDetails;
        this.serverConnected = serverConnected;
        this.serverData = serverData == null ? new NBTTagCompound() : (NBTTagCompound) serverData.copy();
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public EntityPlayer getPlayer() {
        return player;
    }

    @Override
    public NBTTagCompound getServerData() {
        return serverData;
    }

    @Override
    public MovingObjectPosition getHitResult() {
        return hit;
    }

    @Override
    public boolean isServerConnected() {
        return serverConnected;
    }

    @Override
    public boolean showDetails() {
        return showDetails;
    }

    public void requireVerification() {
        verify = true;
    }
}
