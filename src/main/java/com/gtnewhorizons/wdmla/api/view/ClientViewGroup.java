package com.gtnewhorizons.wdmla.api.view;

import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.api.ui.MessageType;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.impl.ui.component.TextComponent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ClientViewGroup<T> {

    public final List<T> views;
    @Nullable
    public String title;
    public MessageType messageType = MessageType.NORMAL;
    public float boxProgress;
    @Nullable
    public NBTTagCompound extraData;

    public ClientViewGroup(List<T> views) {
        this.views = views;
    }

    public static <IN, OUT> List<ClientViewGroup<OUT>> map(
            List<ViewGroup<IN>> groups,
            Function<IN, OUT> itemFactory,
            @Nullable BiConsumer<ViewGroup<IN>, ClientViewGroup<OUT>> clientGroupDecorator) {
        return groups.stream().map($ -> {
            var group = new ClientViewGroup<>($.views.stream().map(itemFactory).filter(Objects::nonNull).collect(Collectors.toList()));
            NBTTagCompound data = $.extraData;
            if (data != null) {
                group.boxProgress = data.getFloat("Progress");
                String messageTypeString = data.getString("MessageType");
                group.messageType = !StringUtils.isNullOrEmpty(messageTypeString) ?
                        MessageType.valueOf(messageTypeString) : MessageType.NORMAL;
            }
            if (clientGroupDecorator != null) {
                clientGroupDecorator.accept($, group);
            }
            group.extraData = data;
            return group;
        }).collect(Collectors.toList());
    }

    public static <T> void tooltip(
            ITooltip tooltip,
            List<ClientViewGroup<T>> groups,
            boolean renderGroup,
            BiConsumer<ITooltip, ClientViewGroup<T>> consumer) {
        for (var group : groups) {
            consumer.accept(tooltip, group);
            if (renderGroup && group.boxProgress > 0 && group.boxProgress < 1) {
                //TODO:overlap progress bar with item group
                tooltip.child(ThemeHelper.INSTANCE.amount((long)(group.boxProgress * 100), 100,
                        new TextComponent(String.format("%d%%", (int)(group.boxProgress * 100)))));
            }
        }
    }

    public boolean shouldRenderGroup() {
        return title != null || boxProgress > 0;
    }

    public void renderHeader(ITooltip tooltip) {
//        if (title != null) {
//            tooltip.add(new HorizontalLineElement());
//            tooltip.append(IElementHelper.get().text(title).scale(0.5F));
//            tooltip.append(new HorizontalLineElement());
//        }
    }
}
