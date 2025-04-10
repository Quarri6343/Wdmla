package com.gtnewhorizons.wdmla.api.provider;

import com.gtnewhorizons.wdmla.api.accessor.Accessor;
import net.minecraft.nbt.NBTTagCompound;

/**
 * This class is on the both side, however the main function is only called on server side to send server specific data to client.<br>
 * Must have respective {@link IComponentProvider} implemented or the tooltip will not be shown in client.<br>
 * Important note: blocks which does not contain tile entity cannot provide server data!<br>
 * Example code:
 * <pre>
 * {@code
 * public enum ExampleServerBlockProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
 *
 *     INSTANCE;
 *
 *     @Override
 *     public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
 *         int random = accessor.getServerData().getInteger("random");
 *         tooltip.child(new TextComponent("Recieved Server Data: " + random));
 *     }
 *
 *     @Override
 *     public void appendServerData(NBTTagCompound data, BlockAccessor accessor) {
 *         data.setInteger("random", new Random().nextInt(11));
 *     }
 *
 *     @Override
 *     public ResourceLocation getUid() {
 *         return Identifiers.EXAMPLE_BLOCK;
 *     }
 * }
 * }
 * </pre>
 * @param <T> The type of accessor this provider accepts.
 */
public interface IServerDataProvider<T extends Accessor> extends IWDMlaProvider {

    /**
     * called in the server side to write server data into packet.
     * @param data the packet the server will send back to client
     * @param accessor accessor object constructed on server side
     */
    void appendServerData(NBTTagCompound data, T accessor);

    /**
     * checked from the client side so it will send packet for this provider
     * @param accessor accessor object
     * @return is this provider requires packet
     */
    default boolean shouldRequestData(T accessor) {
        return true;
    }
}
