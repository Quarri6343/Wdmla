package mcp.mobius.wdmla.impl.value;

import java.util.ArrayList;
import java.util.Optional;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import org.jetbrains.annotations.NotNull;

import mcp.mobius.wdmla.util.OptionalUtil;

/**
 * capable of converting BlockPos to corresponding ItemStack. ItemStack can be air.
 */
public class UnIdentifiedBlockPos {

    private final int x;
    private final int y;
    private final int z;
    private final MovingObjectPosition mop;
    private final Minecraft mc = Minecraft.getMinecraft();

    public UnIdentifiedBlockPos(MovingObjectPosition mop) {
        this.x = mop.blockX;
        this.y = mop.blockY;
        this.z = mop.blockZ;
        this.mop = mop;
    }

    public @NotNull BlockAccessor identify() {
        ArrayList<ItemStack> items = this.getIdentifiedStacks();

        items.sort((stack0, stack1) -> stack1.getItemDamage() - stack0.getItemDamage());

        World world = mc.theWorld;
        EntityPlayer player = mc.thePlayer;
        return new BlockAccessor(world.getBlock(x, y, z), world.getTileEntity(x, y, z), items.get(0), world, player, mop);
    }

    private @NotNull ArrayList<ItemStack> getIdentifiedStacks() {
        ArrayList<ItemStack> items = new ArrayList<>();
        World world = mc.theWorld;

        Block unidentifiedBlock = world.getBlock(x, y, z);
        Optional<TileEntity> tileEntity = Optional.ofNullable(world.getTileEntity(x, y, z));

        // TODO: simple provider for block and TE

        if (OptionalUtil.isEmpty(tileEntity)) {
            try {
                ItemStack block = new ItemStack(unidentifiedBlock, 1, world.getBlockMetadata(x, y, z));

                if (Optional.ofNullable(block.getItem()).isPresent()) items.add(block);

            } catch (Exception ignored) {}
        }

        if (!items.isEmpty()) return items;

        try {
            ItemStack pick = unidentifiedBlock.getPickBlock(null, world, x, y, z, null);
            if (pick != null) items.add(pick);
        } catch (Exception ignored) {}

        if (!items.isEmpty()) return items;

        if (unidentifiedBlock instanceof IShearable shearable) {
            if (shearable.isShearable(new ItemStack(Items.shears), world, x, y, z)) {
                items.addAll(shearable.onSheared(new ItemStack(Items.shears), world, x, y, z, 0));
            }
        }

        if (items.isEmpty()) items.add(0, new ItemStack(unidentifiedBlock, 1, world.getBlockMetadata(x, y, z)));

        return items;
    }
}
