package com.gtnewhorizons.wdmla.plugin.debug;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IEntityComponentProvider;
import com.gtnewhorizons.wdmla.api.IToggleableProvider;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.plugin.PluginsConfig;
import cpw.mods.fml.common.registry.GameData;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.utils.Constants;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

import static mcp.mobius.waila.api.SpecialChars.ITALIC;

public class RegistryNameProvider implements IToggleableProvider {

    public static ForBlock getBlock() {
        return ForBlock.INSTANCE;
    }

    public static ForEntity getEntity() {
        return ForEntity.INSTANCE;
    }

    public static class ForBlock extends RegistryNameProvider implements IBlockComponentProvider {
        private static final ForBlock INSTANCE = new ForBlock();

        @Override
        public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
            if (!ConfigHandler.instance()
                    .getConfig(Configuration.CATEGORY_GENERAL, Constants.CFG_WAILA_METADATA, true)) {
                return;
            }

            tooltip.text(
                    String.format(
                            ITALIC + "[%d:%d] | %s",
                            Block.getIdFromBlock(accessor.getBlock()),
                            accessor.getMetadata(),
                            GameData.getBlockRegistry().getNameForObject(accessor.getBlock())));
        }
    }

    public static class ForEntity extends RegistryNameProvider implements IEntityComponentProvider {
        private static final ForEntity INSTANCE = new ForEntity();

        @Override
        public void appendTooltip(ITooltip tooltip, EntityAccessor accessor) {
            if(!PluginsConfig.debug.registryName.entityRegistryName) {
                return;
            }

            String registryName = EntityList.classToStringMapping.get(accessor.getEntity().getClass());
            tooltip.text(
                    String.format(
                            ITALIC + "[%d:%d] | %s",
                            EntityList.stringToIDMapping.get(registryName),
                            accessor.getEntity().getEntityId(),
                            registryName));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return Identifiers.REGISTRY_NAME;
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.DEFAULT_INFO + 1;
    }

    @Override
    public boolean canToggleInGui() {
        return false;
    }
}
