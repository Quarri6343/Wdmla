package mcp.mobius.waila.handlers.nei;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MovingObjectPosition;

import org.lwjgl.input.Keyboard;

import com.gtnewhorizons.wdmla.overlay.RayTracing;
import com.gtnewhorizons.wdmla.wailacompat.RayTracingCompat;

import codechicken.nei.NEIClientConfig;
import codechicken.nei.guihook.GuiContainerManager;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import mcp.mobius.waila.cbcore.LangUtil;
import mcp.mobius.waila.utils.Constants;

public class NEIHandler {

    public static void register() {
        GuiContainerManager.addTooltipHandler(new TooltipHandlerWaila());

        // We mute the default keybind for displaying the tooltip
        NEIClientConfig.getSetting(Constants.BIND_NEI_SHOW).setIntValue(Keyboard.KEY_NONE);
        NEIClientConfig.getSetting(Constants.CFG_NEI_SHOW).setBooleanValue(false);
    }

    public static boolean firstInventory = true;

    public static void openRecipeGUI(boolean recipe) {
        Minecraft mc = Minecraft.getMinecraft();

        if ((RayTracing.instance().getTarget() != null)
                && (RayTracing.instance().getTarget().typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)) {
            ItemStack stack = RayTracingCompat.INSTANCE.getWailaStack(RayTracing.instance().getTarget());
            if (stack == null) {
                stack = RayTracing.instance().getTargetStack();
            }
            if (stack != null) {
                mc.displayGuiScreen(new GuiInventory(mc.thePlayer));
                if (firstInventory) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception ignored) {}
                    firstInventory = false;
                }

                if (recipe) if (!GuiCraftingRecipe.openRecipeGui("item", stack.copy())) {
                    ItemStack target = stack.copy();
                    target.setItemDamage(0);
                    if (!GuiCraftingRecipe.openRecipeGui("item", target)) {
                        mc.thePlayer.addChatMessage(
                                new ChatComponentTranslation(
                                        "\u00a7f\u00a7o" + LangUtil.translateG("chat.msg.waila.norecipe")));
                        mc.displayGuiScreen(null);
                        mc.setIngameFocus();
                    }
                }

                if (!recipe) if (!GuiUsageRecipe.openRecipeGui("item", stack.copy())) {
                    ItemStack target = stack.copy();
                    target.setItemDamage(0);
                    if (!GuiUsageRecipe.openRecipeGui("item", target)) {
                        mc.thePlayer.addChatMessage(
                                new ChatComponentTranslation(
                                        "\u00a7f\u00a7o" + LangUtil.translateG("chat.msg.waila.nousage")));
                        mc.displayGuiScreen(null);
                        mc.setIngameFocus();
                    }
                }
            }
        }
    }
}
