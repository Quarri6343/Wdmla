package com.gtnewhorizons.wdmla.impl;

import com.gtnewhorizons.wdmla.api.Accessor;
import com.gtnewhorizons.wdmla.api.AccessorClientHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import org.jetbrains.annotations.Nullable;

//TODO: full implementation
public final class ObjectDataCenter {

    public static int rateLimiter = 250;
    public static long timeLastUpdate = System.currentTimeMillis();
    private static Accessor accessor;
    private static AccessorClientHandler<Accessor> clientHandler;
    private static NBTTagCompound serverData = new NBTTagCompound();
    private static MovingObjectPosition lastObject;

    private ObjectDataCenter() {
    }

    public static void set(@Nullable Accessor accessor) {
        ObjectDataCenter.accessor = accessor;
        if (accessor == null) {
            lastObject = null;
            clientHandler = null;
            return;
        }

        clientHandler = WdmlaClientRegistration.instance().getAccessorHandler(accessor.getAccessorType());
        MovingObjectPosition object = accessor.getHitResult();

        if (!equals(object, lastObject)) {
            lastObject = object;
            serverData = null;
            requestServerData();
        }
    }

    @Nullable
    public static Accessor get() {
        return accessor;
    }

    public static NBTTagCompound getServerData() {
        if (accessor == null || clientHandler == null || serverData == null) {
            return new NBTTagCompound();
        }
        if (accessor.verifyData(serverData)) {
            return serverData;
        }
        requestServerData();
        return new NBTTagCompound();
    }

    public static void setServerData(NBTTagCompound tag) {
        serverData = tag;

        if (accessor.verifyData(serverData)) {
            accessor.getServerData().func_150296_c().clear();
            for (String s : tag.func_150296_c()) {
                NBTBase nbt = tag.getTag(s);
                accessor.getServerData().setTag(s, nbt);
            }
        }
    }

    public static void requestServerData() {
        timeLastUpdate = System.currentTimeMillis() - rateLimiter;
    }

    public static boolean isTimeElapsed(long time) {
        return System.currentTimeMillis() - timeLastUpdate >= time;
    }

    public static void resetTimer() {
        timeLastUpdate = System.currentTimeMillis();
    }

    private static boolean equals(MovingObjectPosition mop1, MovingObjectPosition mop2) {
        if(mop1 != null && mop1.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
            && mop2 != null && mop2.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            return mop1.blockX == mop2.blockX && mop1.blockY == mop2.blockY && mop1.blockZ == mop2.blockZ;
        }

        return false;
    }
}
