package mcp.mobius.waila.addons.vanillamc;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneOre;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockWoodSlab;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.SpecialChars;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import mcp.mobius.waila.cbcore.LangUtil;

public class HUDHandlerVanilla implements IWailaDataProvider {

    static Block mobSpawner = Blocks.mob_spawner;
    static Block lever = Blocks.lever;
    static Block repeaterIdle = Blocks.unpowered_repeater;
    static Block repeaterActv = Blocks.powered_repeater;
    static Block comparatorIdl = Blocks.unpowered_comparator;
    static Block comparatorAct = Blocks.powered_comparator;
    static Block jukebox = Blocks.jukebox;
    static Block doubleplant = Blocks.double_plant;
    static Block leave = Blocks.leaves;
    static Block leave2 = Blocks.leaves2;
    static Block log = Blocks.log;
    static Block log2 = Blocks.log2;
    static Block quartz = Blocks.quartz_block;
    static Block anvil = Blocks.anvil;
    static Block sapling = Blocks.sapling;

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        Block block = accessor.getBlock();

        if (block == doubleplant && (accessor.getMetadata() & 8) != 0) {
            int x = accessor.getPosition().blockX;
            int y = accessor.getPosition().blockY - 1;
            int z = accessor.getPosition().blockZ;
            int meta = accessor.getWorld().getBlockMetadata(x, y, z);

            return new ItemStack(doubleplant, 0, meta);
        }

        // fix lit redstone ore has no item form
        if (block instanceof BlockRedstoneOre) {
            return new ItemStack(Blocks.redstone_ore);
        }

        if ((block == leave || block == leave2) && (accessor.getMetadata() > 3)) {
            return new ItemStack(block, 1, accessor.getMetadata() - 4);
        }

        if (block == log || block == log2) {
            return new ItemStack(block, 1, accessor.getMetadata() % 4);
        }

        if ((block == quartz) && (accessor.getMetadata() > 2)) {
            return new ItemStack(block, 1, 2);
        }

        if (block == anvil) {
            return new ItemStack(block, 1, block.damageDropped(accessor.getMetadata()));
        }

        if (block == sapling) {
            return new ItemStack(block, 1, block.damageDropped(accessor.getMetadata()));
        }

        if (block instanceof BlockStoneSlab || block instanceof BlockWoodSlab) {
            return new ItemStack(block, 1, block.damageDropped(accessor.getMetadata()));
        }

        return null;

    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        Block block = accessor.getBlock();

        /* Mob spawner handler */
        if (block == mobSpawner && accessor.getTileEntity() instanceof TileEntityMobSpawner
                && config.getConfig("vanilla.spawntype")) {
            String name = currenttip.get(0);
            String mobname = ((TileEntityMobSpawner) accessor.getTileEntity()).func_145881_a().getEntityNameToSpawn();
            currenttip.set(0, String.format("%s (%s)", name, mobname));
        }

        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        Block block = accessor.getBlock();

        if (config.getConfig("vanilla.leverstate")) if (block == lever) {
            String redstoneOn = (accessor.getMetadata() & 8) == 0 ? LangUtil.translateG("hud.msg.off")
                    : LangUtil.translateG("hud.msg.on");
            currenttip.add(String.format("%s : %s", LangUtil.translateG("hud.msg.state"), redstoneOn));
            return currenttip;
        }

        if (config.getConfig("vanilla.repeater")) if ((block == repeaterIdle) || (block == repeaterActv)) {
            int tick = (accessor.getMetadata() >> 2) + 1;
            if (tick == 1) currenttip.add(String.format("%s : %s tick", LangUtil.translateG("hud.msg.delay"), tick));
            else currenttip.add(String.format("%s : %s ticks", LangUtil.translateG("hud.msg.delay"), tick));
            return currenttip;
        }

        if (config.getConfig("vanilla.comparator")) if ((block == comparatorIdl) || (block == comparatorAct)) {
            String mode = ((accessor.getMetadata() >> 2) & 1) == 0 ? LangUtil.translateG("hud.msg.comparator")
                    : LangUtil.translateG("hud.msg.substractor");
            currenttip.add("Mode : " + mode);
            return currenttip;
        }

        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x,
            int y, int z) {
        if (te != null) te.writeToNBT(tag);
        return tag;
    }

    public static void register() {
        ModuleRegistrar.instance().addConfig("VanillaMC", "vanilla.spawntype");
        ModuleRegistrar.instance().addConfig("VanillaMC", "vanilla.leverstate");
        ModuleRegistrar.instance().addConfig("VanillaMC", "vanilla.repeater");
        ModuleRegistrar.instance().addConfig("VanillaMC", "vanilla.comparator");
        ModuleRegistrar.instance().addConfig("VanillaMC", "vanilla.redstone");
        ModuleRegistrar.instance().addConfigRemote("VanillaMC", "vanilla.jukebox");
        ModuleRegistrar.instance().addConfigRemote("VanillaMC", "vanilla.show_invisible_players");

        IWailaDataProvider provider = new HUDHandlerVanilla();

        ModuleRegistrar.instance().registerStackProvider(provider, doubleplant.getClass());
        ModuleRegistrar.instance().registerStackProvider(provider, BlockRedstoneOre.class);
        ModuleRegistrar.instance().registerStackProvider(provider, leave.getClass());
        ModuleRegistrar.instance().registerStackProvider(provider, leave2.getClass());
        ModuleRegistrar.instance().registerStackProvider(provider, log.getClass());
        ModuleRegistrar.instance().registerStackProvider(provider, log2.getClass());
        ModuleRegistrar.instance().registerStackProvider(provider, quartz.getClass());
        ModuleRegistrar.instance().registerStackProvider(provider, anvil.getClass());
        ModuleRegistrar.instance().registerStackProvider(provider, sapling.getClass());
        ModuleRegistrar.instance().registerStackProvider(provider, BlockStoneSlab.class);
        ModuleRegistrar.instance().registerStackProvider(provider, BlockWoodSlab.class);

        ModuleRegistrar.instance().registerHeadProvider(provider, mobSpawner.getClass());

        ModuleRegistrar.instance().registerBodyProvider(provider, lever.getClass());
        ModuleRegistrar.instance().registerBodyProvider(provider, repeaterIdle.getClass());
        ModuleRegistrar.instance().registerBodyProvider(provider, repeaterActv.getClass());
        ModuleRegistrar.instance().registerBodyProvider(provider, comparatorIdl.getClass());
        ModuleRegistrar.instance().registerBodyProvider(provider, comparatorAct.getClass());
        ModuleRegistrar.instance().registerBodyProvider(provider, jukebox.getClass());

        ModuleRegistrar.instance().registerNBTProvider(provider, mobSpawner.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, lever.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, repeaterIdle.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, repeaterActv.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, comparatorIdl.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, comparatorAct.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, jukebox.getClass());
    }

}
