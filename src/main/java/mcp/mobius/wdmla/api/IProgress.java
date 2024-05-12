package mcp.mobius.wdmla.api;

/**
 * Represents something is going on with two positive values
 */
public interface IProgress {

    long getCurrent();

    long getMax();
}
