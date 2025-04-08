package com.gtnewhorizons.wdmla.plugin.debug;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IEntityComponentProvider;
import com.gtnewhorizons.wdmla.api.IToggleableProvider;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.plugin.PluginsConfig;
import cpw.mods.fml.common.registry.GameData;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.utils.Constants;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.config.Configuration;

import static mcp.mobius.waila.api.SpecialChars.ITALIC;

public class RegistryDataProvider implements IToggleableProvider {

    public static ForBlock getBlock() {
        return ForBlock.INSTANCE;
    }

    public static ForEntity getEntity() {
        return ForEntity.INSTANCE;
    }

    public static class ForBlock extends RegistryDataProvider implements IBlockComponentProvider {
        private static final ForBlock INSTANCE = new ForBlock();

        @Override
        public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
            if (!ConfigHandler.instance()
                    .getConfig(Configuration.CATEGORY_GENERAL, Constants.CFG_WAILA_METADATA, true)) {
                return;
            }
            DisplayMode displayMode = PluginsConfig.debug.registryData.displayMode;
            int blockID = Block.getIdFromBlock(accessor.getBlock());
            int meta = accessor.getMetadata();
            String registryName = GameData.getBlockRegistry().getNameForObject(accessor.getBlock());
            if (displayMode == DisplayMode.SHORT || (displayMode == DisplayMode.DETAILS && !accessor.showDetails())) {
                tooltip.text(
                        String.format(
                                ITALIC + StatCollector.translateToLocal("hud.msg.wdmla.block.registry.data.short"),
                                blockID,
                                meta,
                                registryName));
            }
            else {
                tooltip.child(ThemeHelper.INSTANCE.value(StatCollector.translateToLocal("hud.msg.wdmla.block.id"), String.valueOf(blockID)));
                tooltip.child(ThemeHelper.INSTANCE.value(StatCollector.translateToLocal("hud.msg.wdmla.metadata"), String.valueOf(meta)));
                tooltip.child(ThemeHelper.INSTANCE.value(StatCollector.translateToLocal("hud.msg.wdmla.registry.name"), registryName));
            }
        }
    }

    public static class ForEntity extends RegistryDataProvider implements IEntityComponentProvider {
        private static final ForEntity INSTANCE = new ForEntity();

        @Override
        public void appendTooltip(ITooltip tooltip, EntityAccessor accessor) {
            if(!PluginsConfig.debug.registryData.entityRegistryData) {
                return;
            }

            DisplayMode displayMode = PluginsConfig.debug.registryData.displayMode;
            String registryName = EntityList.classToStringMapping.get(accessor.getEntity().getClass());
            int registryID = (int) EntityList.stringToIDMapping.get(registryName);
            int entityID = accessor.getEntity().getEntityId();
            if(displayMode == DisplayMode.SHORT || (displayMode == DisplayMode.DETAILS && !accessor.showDetails())) {
                tooltip.text(
                        String.format(
                                ITALIC + StatCollector.translateToLocal("hud.msg.wdmla.entity.registry.data.short"),
                                registryID,
                                entityID,
                                registryName));
            }
            else {
                tooltip.child(ThemeHelper.INSTANCE.value(StatCollector.translateToLocal("hud.msg.wdmla.registry.id"), String.valueOf(registryID)));
                tooltip.child(ThemeHelper.INSTANCE.value(StatCollector.translateToLocal("hud.msg.wdmla.entity.id"), String.valueOf(entityID)));
                tooltip.child(ThemeHelper.INSTANCE.value(StatCollector.translateToLocal("hud.msg.wdmla.registry.name"), registryName));
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return Identifiers.REGISTRY_DATA;
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.DEFAULT_INFO + 1;
    }

    @Override
    public boolean canToggleInGui() {
        return false;
    }

    public enum DisplayMode {
        SHORT,
        FULL,
        DETAILS
    }
}
