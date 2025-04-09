package mcp.mobius.waila.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import mcp.mobius.waila.Waila;
import mcp.mobius.waila.api.BackwardCompatibility;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Catches error and output information logs
 * TODO:Too little information, Reimplementation is mandatory. See snownee.jade.util.WailaExceptionHandler
 */
@BackwardCompatibility
public class WailaExceptionHandler {

    public WailaExceptionHandler() {}

    private static ArrayList<String> errs = new ArrayList<String>();

    public static List<String> handleErr(@NotNull Throwable e,@NotNull String className,@Nullable List<String> currenttip) {
        if (!errs.contains(className)) {
            errs.add(className);

            for (StackTraceElement elem : e.getStackTrace()) {
                Waila.log.log(
                        Level.WARN,
                        String.format("%s.%s:%s", elem.getClassName(), elem.getMethodName(), elem.getLineNumber()));
                if (elem.getClassName().contains("waila")) break;
            }

            Waila.log.log(Level.WARN, String.format("Catched unhandled exception : [%s] %s", className, e));
        }
        if (currenttip != null) currenttip.add("<ERROR>");

        return currenttip;
    }

}
