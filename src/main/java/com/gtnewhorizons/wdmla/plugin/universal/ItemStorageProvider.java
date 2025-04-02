package com.gtnewhorizons.wdmla.plugin.universal;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.gtnewhorizons.wdmla.ClientProxy;
import com.gtnewhorizons.wdmla.CommonProxy;
import com.gtnewhorizons.wdmla.api.Accessor;
import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.EntityAccessor;
import com.gtnewhorizons.wdmla.api.IComponentProvider;
import com.gtnewhorizons.wdmla.api.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.Theme;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ComponentAlignment;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.api.ui.MessageType;
import com.gtnewhorizons.wdmla.api.view.ClientViewGroup;
import com.gtnewhorizons.wdmla.api.view.IClientExtensionProvider;
import com.gtnewhorizons.wdmla.api.view.IServerExtensionProvider;
import com.gtnewhorizons.wdmla.api.view.ItemView;
import com.gtnewhorizons.wdmla.api.view.ViewGroup;
import com.gtnewhorizons.wdmla.config.General;
import com.gtnewhorizons.wdmla.impl.WDMlaClientRegistration;
import com.gtnewhorizons.wdmla.impl.WDMlaCommonRegistration;
import com.gtnewhorizons.wdmla.impl.ui.component.HPanelComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.ItemComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.RectComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.TextComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.TooltipComponent;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Padding;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Size;
import com.gtnewhorizons.wdmla.impl.ui.style.PanelStyle;
import com.gtnewhorizons.wdmla.impl.ui.style.RectStyle;
import mcp.mobius.waila.cbcore.LangUtil;
import mcp.mobius.waila.overlay.DisplayUtil;
import mcp.mobius.waila.utils.WailaExceptionHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("UnstableApiUsage")
public class ItemStorageProvider<T extends Accessor> implements IComponentProvider<T>, IServerDataProvider<T> {

    public static final Cache<Object, ItemCollector<?>> targetCache = CacheBuilder.newBuilder().weakKeys().expireAfterAccess(
            60,
            TimeUnit.SECONDS).build();
    public static final Cache<Object, ItemCollector<?>> containerCache = CacheBuilder.newBuilder().weakKeys().expireAfterAccess(
            120,
            TimeUnit.SECONDS).build();

    public static ForBlock getBlock() {
        return ForBlock.INSTANCE;
    }

    public static ForEntity getEntity() {
        return ForEntity.INSTANCE;
    }

    public static NBTTagCompound encodeGroups(Map.Entry<ResourceLocation, List<ViewGroup<ItemStack>>> entry) {
        List<ViewGroup<ItemStack>> viewGroups = entry.getValue();
        NBTTagList groupsNBT = new NBTTagList();
        for (ViewGroup<ItemStack> viewGroup : viewGroups) {
            groupsNBT.appendTag(encodeGroup(viewGroup));
        }
        NBTTagCompound root = new NBTTagCompound();
        root.setTag(entry.getKey().toString(), groupsNBT);

        return root;
    }

    public static NBTTagCompound encodeGroup(ViewGroup<ItemStack> viewGroup) {
        List<NBTTagCompound> encodedItemStacks = new ArrayList<>();
        for (ItemStack item : viewGroup.views) {
            NBTTagCompound itemNBT = new NBTTagCompound();
            item.writeToNBT(itemNBT);
            itemNBT.setInteger("intCount", item.stackSize);
            encodedItemStacks.add(itemNBT);
        }
        ViewGroup<NBTTagCompound> contentEncodedGroup = new ViewGroup<>(encodedItemStacks, viewGroup);
        return ViewGroup.encode(contentEncodedGroup);
    }

    public static Map.Entry<ResourceLocation, List<ViewGroup<ItemStack>>> decodeGroups(NBTTagCompound root) {
        if (root.hasNoTags()) {
            return null;
        }

        String key = root.func_150296_c().iterator().next();
        ResourceLocation resourceLocation = new ResourceLocation(key);

        NBTTagList groupsNBT = root.getTagList(key, 10);
        List<ViewGroup<ItemStack>> viewGroups = new ArrayList<>();
        for (int i = 0; i < groupsNBT.tagCount(); i++) {
            NBTTagCompound groupNBT = groupsNBT.getCompoundTagAt(i);
            viewGroups.add(decodeGroup(groupNBT));
        }

        return new AbstractMap.SimpleEntry<>(resourceLocation, viewGroups);
    }

    public static ViewGroup<ItemStack> decodeGroup(NBTTagCompound groupNBT) {
        ViewGroup<NBTTagCompound> contentDecodedGroup = ViewGroup.decode(groupNBT);

        List<ItemStack> itemStacks = new ArrayList<>();
        for (NBTTagCompound itemNBT : contentDecodedGroup.views) {
            ItemStack item = ItemStack.loadItemStackFromNBT(itemNBT);
            if(item != null) {
                item.stackSize = itemNBT.getInteger("intCount");
                itemStacks.add(item);
            }
        }

        return new ViewGroup<>(itemStacks, contentDecodedGroup);
    }


    public static void append(ITooltip tooltip, Accessor accessor) {
        List<ClientViewGroup<ItemView>> groups = ClientProxy.mapToClientGroups(
                accessor,
                Identifiers.ITEM_STORAGE,
                ItemStorageProvider::decodeGroups,
                WDMlaClientRegistration.instance().itemStorageProviders::get);
        if (groups == null || groups.isEmpty()) {
            return;
        }

        boolean renderGroup = groups.size() > 1 || groups.get(0).shouldRenderGroup();
        ClientViewGroup.tooltip(
                tooltip, groups, renderGroup, (theTooltip, group) -> {
                    MutableBoolean showName = getShowName(group);

                    if (renderGroup) {
                        Theme theme = General.currentTheme.get();
                        if (group.title != null) {
                            ITooltip hPanel = new HPanelComponent().style(new PanelStyle().alignment(ComponentAlignment.CENTER));
                            hPanel.child(new RectComponent().style(new RectStyle().backgroundColor(theme.textColor(MessageType.NORMAL)))
                                    .size(new Size(20,1)));
                            hPanel.child(new TextComponent(group.title).scale(0.6f));
                            hPanel.child(new RectComponent().style(new RectStyle().backgroundColor(theme.textColor(MessageType.NORMAL)))
                                    .size(new Size(30,1)));
                            theTooltip.child(hPanel);
                        }
                        else {
                            tooltip.child(new RectComponent().style(new RectStyle().backgroundColor(theme.textColor(MessageType.NORMAL)))
                                    .size(new Size(50,1)));
                        }
                    }
                    if (group.views.isEmpty() && group.extraData != null) {
                        float progress = group.extraData.getFloat("Collecting");
                        if (progress >= 0 && progress < 1) {
                            String collectingText = LangUtil.translateG("hud.msg.wdmla.collectingItems");
                            if (progress != 0) {
                                collectingText += String.format(" %s%%", (int) (progress * 100));
                            }
                            theTooltip.text(collectingText);
                        }
                    }
                    int drawnCount = 0;
                    int realSize = accessor.showDetails() ? 54 : 9; //TODO:config UNIVERSAL_ITEM_STORAGE_DETAILED_AMOUNT, UNIVERSAL_ITEM_STORAGE_NORMAL_AMOUNT
                    realSize = Math.min(group.views.size(), realSize);
                    TooltipComponent elements = new HPanelComponent();
                    if(showName.isFalse()) {
                        elements.padding(new Padding(-1, -1, 0, 0));
                    }

                    for (int i = 0; i < realSize; i++) {
                        ItemView itemView = group.views.get(i);
                        ItemStack stack = itemView.item;
                        if (stack == null) {
                            continue;
                        }
                        if (i > 0 && (showName.isTrue() || drawnCount >= 9)) { //TODO:config UNIVERSAL_ITEM_STORAGE_ITEMS_PER_LINE
                            theTooltip.child(elements);
                            elements = new HPanelComponent();
                            if(showName.isFalse()) {
                                elements.padding(new Padding(-1, -1, 0, 0));
                            }
                            drawnCount = 0;
                        }

                        if (showName.isTrue()) {
                            if(itemView.description != null) {
                                int itemSize = itemView.description.getHeight();
                                elements.child(new ItemComponent(stack).doDrawOverlay(false).size(new Size(itemSize, itemSize)).padding(new Padding(-1, 0, 0, 0)));
                                elements.child(itemView.description);
                            }
                            else {
                                String strippedName = DisplayUtil.stripSymbols(DisplayUtil.itemDisplayNameShort(stack));
                                TextComponent name = new TextComponent(strippedName);
                                int itemSize = name.getHeight();
                                elements.child(new ItemComponent(stack).doDrawOverlay(false).size(new Size(itemSize, itemSize)).padding(new Padding(-1, 0, 0, 0)));
                                String s = String.valueOf(stack.stackSize); //TODO: unit format
                                elements.text(s).text("× ").child(name);
                            }
                        } else if (itemView.amountText != null) {
                            elements.child(new ItemComponent(stack).stackSizeOverride(itemView.amountText).padding(new Padding(-1, 0, 0, 0)));
                        } else {
                            elements.item(stack);
                        }
                        drawnCount += 1;
                    }

                    if (elements.childrenSize() > 0) {
                        theTooltip.child(elements);
                    }
                });
    }

    public static MutableBoolean getShowName(ClientViewGroup<ItemView> group) {
        MutableBoolean showName = new MutableBoolean(true);
        int showNameAmount = 5; //UNIVERSAL_ITEM_STORAGE_SHOW_NAME_AMOUNT
        int totalSize = 0;
        for (var view : group.views) {
            if (view.amountText != null) {
                showName.setFalse();
            }
            if (view.item != null) {
                ++totalSize;
                if (totalSize == showNameAmount) {
                    showName.setFalse();
                }
            }
        }

        return showName;
    }

    public static void putData(NBTTagCompound data, Accessor accessor) {
        Map.Entry<ResourceLocation, List<ViewGroup<ItemStack>>> entry = CommonProxy.getServerExtensionData(
                accessor,
                WDMlaCommonRegistration.instance().itemStorageProviders);
        if (entry != null) {
            List<ViewGroup<ItemStack>> groups = entry.getValue();
            for (ViewGroup<ItemStack> group : groups) {
                if (group.views.size() > ItemCollector.MAX_SIZE) {
                    group.views = group.views.subList(0, ItemCollector.MAX_SIZE);
                }
            }
            data.setTag(Identifiers.ITEM_STORAGE.toString(), encodeGroups(entry)); //transform List<ViewGroup<ItemStack>> into nbttag
        }
    }

    @Override
    public void appendTooltip(ITooltip tooltip, T accessor) {
        append(tooltip, accessor);
    }

    @Override
    public void appendServerData(NBTTagCompound data, T accessor) {
        putData(data, accessor);
    }

    @Override
    public ResourceLocation getUid() {
        return Identifiers.ITEM_STORAGE;
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.TAIL;
    }

    public static class ForBlock extends ItemStorageProvider<BlockAccessor> {
        private static final ForBlock INSTANCE = new ForBlock();
    }

    public static class ForEntity extends ItemStorageProvider<EntityAccessor> {
        private static final ForEntity INSTANCE = new ForEntity();
    }

    public enum Extension implements IServerExtensionProvider<ItemStack>, IClientExtensionProvider<ItemStack, ItemView> {
        INSTANCE;

        @Override
        public ResourceLocation getUid() {
            return Identifiers.ITEM_STORAGE_DEFAULT;
        }

        @Override
        public @Nullable List<ViewGroup<ItemStack>> getGroups(Accessor accessor) {
            Object target = accessor.getTarget();
            if (target == null) {
                return CommonProxy.createItemCollector(accessor, containerCache).update(accessor);
            }
            EntityPlayer player = accessor.getPlayer();
            if (target instanceof TileEntityEnderChest) { //TODO:split EnderChest extension
                InventoryEnderChest inventory = player.getInventoryEnderChest();
                return new ItemCollector<>(new ItemIterator.IInventoryItemIterator($ -> inventory, 0)).update(
                        accessor
                );
            }
            ItemCollector<?> itemCollector;
            try {
                itemCollector = targetCache.get(target, () -> CommonProxy.createItemCollector(accessor, containerCache));
            } catch (ExecutionException e) {
                WailaExceptionHandler.handleErr(e, getClass().getName(), null);
                return null;
            }
            return itemCollector.update(accessor);
        }

        @Override
        public boolean shouldRequestData(Accessor accessor) {
            return true;
        }

        @Override
        public int getDefaultPriority() {
            return 9999;
        }

        @Override
        public List<ClientViewGroup<ItemView>> getClientGroups(Accessor accessor, List<ViewGroup<ItemStack>> groups) {
            return ClientViewGroup.map(groups, ItemView::new, null);
        }
    }
}
