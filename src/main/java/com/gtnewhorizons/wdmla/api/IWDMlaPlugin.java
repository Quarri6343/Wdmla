package com.gtnewhorizons.wdmla.api;

/**
 * The entry point of WDMla registration system.<br>
 * When it is defined with {@link WDMlaPlugin} annotation, its method will automatically be called by WDMla at appropriate timing.<br>
 * Since no manual class call is required, it is recommended to use along with {@code @SuppressWarnings("unused")}.<br>
 * This class will be called from both client and server side.<br>
 * The main thing this class must do is registering providers with their supported target object.<br>
 * Example code:
 * <pre>
 * {@code
 * @WDMlaPlugin
 * public class ExamplePlugin implements IWDMlaPlugin {
 *
 *     @Override
 *     public void register(IWDMlaCommonRegistration registration) {
 *         registration.registerEntityDataProvider(ExampleEntityProvider.INSTANCE, EntityPig.class);
 *         registration.registerItemStorage(ExampleItemStorageProvider.INSTANCE, BlockChest.class);
 *     }
 *
 *     @Override
 *     public void registerClient(IWDMlaClientRegistration registration) {
 *         registration.registerBlockComponent(ExampleBlockProvider.INSTANCE, BlockDirt.class);
 *         registration.registerEntityComponent(ExampleEntityProvider.INSTANCE, EntityPig.class);
 *         registration.registerItemStorageClient(TestItemStorageProvider.INSTANCE);
 *     }
 * }
 * }
 * </pre>
 */
public interface IWDMlaPlugin {

    /**
     * Server side registration method.<br>
     * Mostly used to register providers.<br>
     * Note the order of registration is not limited since all entries will automatically be sorted with priorities (if exists)
     * @param registration WDMla registry
     */
    default void register(IWDMlaCommonRegistration registration) {

    }

    /**
     * Client side registration method.<br>
     * Mostly used to register providers.
     * Note the order of registration is not limited since all entries will automatically be sorted with priorities (if exists)
     * @param registration WDMla registry
     */
    default void registerClient(IWDMlaClientRegistration registration) {

    }
}
