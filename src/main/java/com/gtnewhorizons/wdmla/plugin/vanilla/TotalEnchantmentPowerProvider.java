package com.gtnewhorizons.wdmla.plugin.vanilla;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.util.FormatUtil;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public enum TotalEnchantmentPowerProvider implements IBlockComponentProvider {
    INSTANCE;

    /**
     * @see net.minecraft.inventory.ContainerEnchantment#onCraftMatrixChanged(IInventory)
     */
    public float calculateEnchantPower(BlockAccessor accessor) {
        World worldPointer = accessor.getWorld();
        int posX = accessor.getHitResult().blockX;
        int posY = accessor.getHitResult().blockY;
        int posZ = accessor.getHitResult().blockZ;
        int j;
        float power = 0;

        for (j = -1; j <= 1; ++j)
        {
            for (int k = -1; k <= 1; ++k)
            {
                if ((j != 0 || k != 0) && worldPointer.isAirBlock(posX + k, posY, posZ + j) && worldPointer.isAirBlock(posX + k, posY + 1, posZ + j))
                {
                    power += ForgeHooks.getEnchantPower(worldPointer, posX + k * 2, posY,     posZ + j * 2);
                    power += ForgeHooks.getEnchantPower(worldPointer, posX + k * 2, posY + 1, posZ + j * 2);

                    if (k != 0 && j != 0)
                    {
                        power += ForgeHooks.getEnchantPower(worldPointer, posX + k * 2, posY,     posZ + j    );
                        power += ForgeHooks.getEnchantPower(worldPointer, posX + k * 2, posY + 1, posZ + j    );
                        power += ForgeHooks.getEnchantPower(worldPointer, posX + k,     posY,     posZ + j * 2);
                        power += ForgeHooks.getEnchantPower(worldPointer, posX + k,     posY + 1, posZ + j * 2);
                    }
                }
            }
        }

        return power;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        float power = calculateEnchantPower(accessor);
        tooltip.child(ThemeHelper.INSTANCE.value(
                StatCollector.translateToLocal("hud.msg.wdmla.total.enchantment.power"), FormatUtil.STANDARD.format(power)));
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.TOTAL_ENCHANTMENT_POWER;
    }
}
