package mcp.mobius.wdmla.impl;

import mcp.mobius.wdmla.api.Accessor;
import mcp.mobius.wdmla.api.AccessorClientHandler;
import net.minecraft.nbt.NBTTagCompound;

//TODO: full implementation
public final class ObjectDataCenter {

    public static int rateLimiter = 250;
    public static long timeLastUpdate = System.currentTimeMillis();
    public static boolean serverConnected;
    private static Accessor accessor;
    private static AccessorClientHandler<Accessor> clientHandler;
    private static NBTTagCompound serverData = new NBTTagCompound();
    private static Object lastObject;

    private ObjectDataCenter() {
    }

    public static NBTTagCompound getServerData() {
        return serverData;
    }

    public static void setServerData(NBTTagCompound tag) {
        serverData = tag;
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
}
