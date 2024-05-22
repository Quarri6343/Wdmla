package mcp.mobius.wdmla.api;

public interface IWdmlaPlugin {

    default void register(IWdmlaCommonRegistration registration) {

    }

    default void registerClient(IWdmlaClientRegistration registration) {

    }
}
