package mcp.mobius.wdmla;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.overlay.RayTracing;
import mcp.mobius.wdmla.api.Accessor;
import mcp.mobius.wdmla.api.IWdmlaPlugin;
import mcp.mobius.wdmla.impl.ObjectDataCenter;
import mcp.mobius.wdmla.impl.WdmlaClientRegistration;
import mcp.mobius.wdmla.impl.WdmlaCommonRegistration;
import mcp.mobius.wdmla.impl.ui.component.RootComponent;
import mcp.mobius.wdmla.test.TestPlugin;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

//TODO:move mod annotation
//TODO:Edit Readme for TOP and Jade credits
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

        Accessor accessor = null;

        if(target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            Block block = world.getBlock(target.blockX, target.blockY, target.blockZ);
            TileEntity tileEntity = world.getTileEntity(target.blockX, target.blockY, target.blockZ);
            int metadata = world.getBlockMetadata(target.blockX, target.blockY, target.blockZ);
            ItemStack itemForm = getIdentifierStack(world, target);
            accessor = WdmlaClientRegistration.instance().blockAccessor().block(block).tileEntity(tileEntity)
                    .meta(metadata).hit(target).itemForm(itemForm).requireVerification().build();
        }

        ObjectDataCenter.set(accessor);
        if (accessor == null || accessor.getHitResult() == null) {
            mainHUD = null;
            return;
        }

        mainHUD = handle(accessor);
    }

    public RootComponent handle(Accessor accessor) {
        RootComponent root = new RootComponent();

        var handler = WdmlaClientRegistration.instance().getAccessorHandler(accessor.getAccessorType());
        if (!handler.shouldDisplay(accessor)) {
            return null;
        }

        if (accessor.isServerConnected()) {
            if (accessor.getServerData() != null && !accessor.verifyData(accessor.getServerData())) {
                accessor.getServerData().func_150296_c().clear();
            }
            boolean request = handler.shouldRequestData(accessor);
            if (ObjectDataCenter.isTimeElapsed(ObjectDataCenter.rateLimiter)) {
                ObjectDataCenter.resetTimer();
                if (request) {
                    handler.requestData(accessor);
                }
            }
            if (request && ObjectDataCenter.getServerData() == null) {
                return null;
            }
        } else {
            return null;
        }

        handler.gatherComponents(accessor, root);

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
