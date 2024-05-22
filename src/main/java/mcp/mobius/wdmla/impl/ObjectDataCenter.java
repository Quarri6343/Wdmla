package mcp.mobius.wdmla.impl;

public class ObjectDataCenter {

    public static int rateLimiter = 250;
    public static long timeLastUpdate = System.currentTimeMillis();

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
