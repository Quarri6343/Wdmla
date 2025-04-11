package com.gtnewhorizons.wdmla.command;

import com.gtnewhorizon.gtnhlib.commands.GTNHClientCommand;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.impl.WDMlaClientRegistration;
import com.gtnewhorizons.wdmla.impl.WDMlaCommonRegistration;
import com.gtnewhorizons.wdmla.plugin.harvestability.HarvestabilityIdentifiers;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.command.ICommandSender;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;

/**
 * Output tile entities which have no WDMla provider associated for developers.<br>
 * This is especially useful to list up all forgotten tile entity of the mod.
 * TODO:blacklist for unwanted tile entity
 */
public class PrintUnsupportedTEsCommand extends GTNHClientCommand {

    @Override
    public String getCommandName() {
        return "wdmlaprintte";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (!(sender instanceof EntityClientPlayerMP mpPlayer)) {
            return;
        }

        sender.addChatMessage(new ChatComponentText("Following tile entities are not supported by WDMla"));

        for (Object block : GameData.getBlockRegistry()) {
            if (!(block instanceof ITileEntityProvider tileEntityProvider)) {
                continue;
            }

            long supportedNonGlobalProviderCount = WDMlaClientRegistration.instance()
                    .blockComponentProviders.get(block.getClass()).stream().filter(
                            provider -> !provider.getUid().getResourceDomain().equals(Identifiers.NAMESPACE_CORE)
                    && !provider.getUid().getResourceDomain().equals(HarvestabilityIdentifiers.NAMESPACE_HARVESTABILITY)
                    && !provider.getUid().getResourceDomain().equals(Identifiers.NAMESPACE_UNIVERSAL)
                    && !provider.getUid().getResourceDomain().equals(Identifiers.NAMESPACE_CORE)).count();
            long supportedStorageProvidersCount = WDMlaCommonRegistration.instance()
                    .itemStorageProviders.entries().filter(classCollectionEntry -> classCollectionEntry.getKey().isInstance(block)).count();
            //TODO: fluid, power
            if(supportedNonGlobalProviderCount == 0 && supportedStorageProvidersCount == 0) {
                TileEntity te = tileEntityProvider.createNewTileEntity(mpPlayer.worldObj, 0);
                if (te != null) {
                    sender.addChatMessage(new ChatComponentText(te.getClass().getCanonicalName()));
                }
            }
        }
    }
}
