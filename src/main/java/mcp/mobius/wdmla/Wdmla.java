package mcp.mobius.wdmla;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mcp.mobius.waila.Waila;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.api.impl.DataAccessorCommon;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import mcp.mobius.waila.network.Message0x01TERequest;
import mcp.mobius.waila.network.WailaPacketHandler;
import mcp.mobius.waila.overlay.RayTracing;
import mcp.mobius.waila.utils.WailaExceptionHandler;
import mcp.mobius.wdmla.api.BlockAccessor;
import mcp.mobius.wdmla.api.IComponentProvider;
import mcp.mobius.wdmla.api.IWdmlaPlugin;
import mcp.mobius.wdmla.impl.ObjectDataCenter;
import mcp.mobius.wdmla.impl.WdmlaClientRegistration;
import mcp.mobius.wdmla.impl.WdmlaCommonRegistration;
import mcp.mobius.wdmla.impl.ui.component.RootComponent;
import mcp.mobius.wdmla.impl.ui.component.TextComponent;
import mcp.mobius.wdmla.test.TestPlugin;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

//TODO:move mod annotation
//TODO:Edit Readme for credits
//TODO:TickHandler
public class Wdmla {

    // @instance
    public static Wdmla instance = new Wdmla();

    private static @Nullable RootComponent mainHUD = null;

    public void loadComplete(FMLLoadCompleteEvent event) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            IWdmlaPlugin testPlugin = new TestPlugin();
            testPlugin.register(WdmlaCommonRegistration.instance());
            testPlugin.registerClient(WdmlaClientRegistration.instance());
        }
        // TODO: IMC
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void tickRender(TickEvent.RenderTickEvent event) {
        if (mainHUD != null) {
            mainHUD.renderHUD();
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void tickClient(TickEvent.ClientTickEvent event) {

        Minecraft mc = Minecraft.getMinecraft();
        World world = mc.theWorld;
        EntityPlayer player = mc.thePlayer;
        GuiScreen currentScreen = mc.currentScreen;

        if(world == null || player == null || !Minecraft.isGuiEnabled()
                || currentScreen != null || mc.gameSettings.keyBindPlayerList.getIsKeyPressed() || !ConfigHandler.instance().showTooltip()) {
            mainHUD = null;
            return;
        }

        RayTracing.instance().fire();
        MovingObjectPosition target = RayTracing.instance().getTarget();

        if(target == null) {
            mainHUD = null;
            return;
        }

        if(target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            Block block = world.getBlock(target.blockX, target.blockY, target.blockZ);
            TileEntity tileEntity = world.getTileEntity(target.blockX, target.blockY, target.blockZ);
            int metadata = world.getBlockMetadata(target.blockX, target.blockY, target.blockZ);
            ItemStack itemForm = getIdentifierStack(world, target);
            BlockAccessor accessor = WdmlaClientRegistration.instance().blockAccessor().block(block).tileEntity(tileEntity)
                    .meta(metadata).hit(target).itemForm(itemForm).requireVerification().build();

            //TODO: if a player looking a same block, don't request new Info until TE request occurs
            mainHUD = handle(accessor);
        }
    }

    public @NotNull RootComponent handle(BlockAccessor accessor) {
        Block block = accessor.getBlock();
        RootComponent root = new RootComponent();

        if (!WdmlaClientRegistration.instance().hasProviders(block)
                && !WdmlaClientRegistration.instance().hasProviders(accessor.getTileEntity())) {
            return root;
        }

        if (accessor.getTileEntity() != null && Waila.instance.serverPresent
                && ObjectDataCenter.isTimeElapsed(ObjectDataCenter.rateLimiter)) {
            ObjectDataCenter.resetTimer();
            HashSet<String> keys = new HashSet<>();

            if (WdmlaCommonRegistration.instance().hasProviders(block)
                    || WdmlaCommonRegistration.instance().hasProviders(accessor.getTileEntity()))
                WailaPacketHandler.INSTANCE
                        .sendToServer(new Message0x01TERequest(accessor.getTileEntity(), keys, true));
            else if (ModuleRegistrar.instance().hasNBTProviders(block) //OLD API Support
                    || ModuleRegistrar.instance().hasNBTProviders(accessor.getTileEntity()))
                WailaPacketHandler.INSTANCE
                        .sendToServer(new Message0x01TERequest(accessor.getTileEntity(), keys, false));
        } else if (accessor.getTileEntity() != null && !Waila.instance.serverPresent
                && ObjectDataCenter.isTimeElapsed(ObjectDataCenter.rateLimiter)) {
            ObjectDataCenter.resetTimer();

            try {
                NBTTagCompound tag = accessor.getServerData();
                accessor.getTileEntity().writeToNBT(tag);
                //TODO: handleRequest inside BlockAccessorImpl
            } catch (Exception e) {
                WailaExceptionHandler.handleErr(e, this.getClass().getName(), null);
            }
        }

        /* Lookup by class (for blocks) */
        if (WdmlaClientRegistration.instance().hasProviders(block)) {
            List<IComponentProvider<BlockAccessor>> providers = WdmlaClientRegistration.instance().getProviders(block);
            for (IComponentProvider<BlockAccessor> provider : providers) {
                provider.appendTooltip(root, accessor);
            }
        }

        /* Lookup by class (for tileentities) */
        if ((WdmlaClientRegistration.instance().hasProviders(accessor.getTileEntity()))) {
            List<IComponentProvider<BlockAccessor>> providers = WdmlaClientRegistration.instance()
                    .getProviders(accessor.getTileEntity());
            for (IComponentProvider<BlockAccessor> provider : providers) {
                provider.appendTooltip(root, accessor);
            }
        }

        //Legacy Tooltip support
        //TODO: WailaStack support
        DataAccessorCommon legacyAccessor = DataAccessorCommon.instance;
        legacyAccessor.set(accessor.getWorld(), accessor.getPlayer(), accessor.getHitResult());
        Map<Integer, List<IWailaDataProvider>> legacyProviders = new HashMap<>();
        if(ModuleRegistrar.instance().hasBodyProviders(block)) {
            legacyProviders.putAll(ModuleRegistrar.instance().getBodyProviders(block));
        }
        if(ModuleRegistrar.instance().hasBodyProviders(accessor.getTileEntity())) {
            legacyProviders.putAll(ModuleRegistrar.instance().getBodyProviders(accessor.getTileEntity()));
        }
        for (List<IWailaDataProvider> providersList : legacyProviders.values()) {
            for (IWailaDataProvider dataProvider : providersList) {
                List<String> tooltips = new ArrayList<>();
                tooltips = dataProvider.getWailaBody(accessor.getItemForm(), tooltips, legacyAccessor, ConfigHandler.instance());
                for (String tooltip : tooltips) {
                    root.child(new TextComponent(tooltip));
                }
            }
        }

        return root;
    }


    public ItemStack getIdentifierStack(World world, MovingObjectPosition mop) {
        ArrayList<ItemStack> items = this.getIdentifierItems(world, mop);

        if (items.isEmpty()) return null;

        items.sort((stack0, stack1) -> stack1.getItemDamage() - stack0.getItemDamage());

        return items.get(0);
    }

    public ArrayList<ItemStack> getIdentifierItems(World world, MovingObjectPosition mop) {
        ArrayList<ItemStack> items = new ArrayList<>();

        int x = mop.blockX;
        int y = mop.blockY;
        int z = mop.blockZ;
        Block mouseoverBlock = world.getBlock(x, y, z);
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (mouseoverBlock == null) return items;

        if (tileEntity == null) {
            try {
                ItemStack block = new ItemStack(mouseoverBlock, 1, world.getBlockMetadata(x, y, z));

                if (block.getItem() != null) items.add(block);

            } catch (Exception ignored) {}
        }

        if (!items.isEmpty()) return items;

        try {
            ItemStack pick = mouseoverBlock.getPickBlock(mop, world, x, y, z);
            if (pick != null) items.add(pick);
        } catch (Exception ignored) {}

        if (!items.isEmpty()) return items;

        if (mouseoverBlock instanceof IShearable shearable) {
            if (shearable.isShearable(new ItemStack(Items.shears), world, x, y, z)) {
                items.addAll(shearable.onSheared(new ItemStack(Items.shears), world, x, y, z, 0));
            }
        }

        if (items.isEmpty()) items.add(0, new ItemStack(mouseoverBlock, 1, world.getBlockMetadata(x, y, z)));

        return items;
    }
}
