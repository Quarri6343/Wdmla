package com.gtnewhorizons.wdmla.command;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import com.gtnewhorizon.gtnhlib.GTNHLib;
import com.gtnewhorizon.gtnhlib.commands.GTNHClientCommand;
import com.gtnewhorizons.wdmla.api.provider.IWDMlaProvider;
import com.gtnewhorizons.wdmla.impl.WDMlaClientRegistration;
import com.gtnewhorizons.wdmla.impl.WDMlaCommonRegistration;
import com.gtnewhorizons.wdmla.impl.lookup.IHierarchyLookup;

import mcp.mobius.waila.utils.WailaExceptionHandler;

public class GenerateDumpCommand extends GTNHClientCommand {

    @Override
    public String getCommandName() {
        return "wdmladump";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (!(sender instanceof EntityClientPlayerMP)) {
            return;
        }

        File file = new File(Minecraft.getMinecraft().mcDataDir, "wdmla_handlers.md");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(generateInfoDump());
            GTNHLib.proxy.addMessageToChat(
                    new ChatComponentText(
                            String.format(
                                    StatCollector.translateToLocal("chat.msg.wdmla.dump.success"),
                                    file.getCanonicalPath())));
        } catch (IOException e) {
            WailaExceptionHandler.handleErr(e, this.getClass().getName(), null);
        }
    }

    public static String generateInfoDump() {
        StringBuilder builder = new StringBuilder("# Wdmla Handler Dump");
        WDMlaClientRegistration client = WDMlaClientRegistration.instance();
        WDMlaCommonRegistration common = WDMlaCommonRegistration.instance();

        builder.append("\nGenerated at ").append(ZonedDateTime.now(ZoneOffset.UTC)).append("\n");

        builder.append("\n## Block");
        // createSection(builder, "Icon Providers", client.blockIconProviders);
        createSection(builder, "Component Providers", client.blockComponentProviders);
        createSection(builder, "Data Providers", common.blockDataProviders);

        builder.append("\n## Entity");
        // createSection(builder, "Icon Providers", client.entityIconProviders);
        createSection(builder, "Component Providers", client.entityComponentProviders);
        createSection(builder, "Data Providers", common.entityDataProviders);

        builder.append("\n## Common Extension");
        createSection(builder, "Item Storage", common.itemStorageProviders);
        // createSection(builder, "Fluid Storage", common.fluidStorageProviders);
        // createSection(builder, "Energy Storage", common.energyStorageProviders);
        // createSection(builder, "Progress", common.progressProviders);

        builder.append("\n## Client Extension");
        createSection(builder, "Item Storage", client.itemStorageProviders);
        // createSection(builder, "Fluid Storage", client.fluidStorageProviders);
        // createSection(builder, "Energy Storage", client.energyStorageProviders);
        // createSection(builder, "Progress", client.progressProviders);

        builder.append("\n## Priorities");
        for (ResourceLocation resourceLocation : common.priorities.getSortedList()) {
            builder.append("\n* ").append(resourceLocation);
        }

        return builder.toString();
    }

    private static void createSection(StringBuilder builder, String subsection,
            Map<ResourceLocation, ? extends IWDMlaProvider> map) {
        if (map.isEmpty()) {
            return;
        }
        builder.append("\n### ").append(subsection);
        map.forEach((key, value) -> {
            builder.append("\n\n#### ").append(key);
            builder.append("\n* ").append(value.getUid()).append(", ").append(value.getClass().getName());
        });
        builder.append("\n\n");
    }

    private static void createSection(StringBuilder builder, String subsection,
            IHierarchyLookup<? extends IWDMlaProvider> lookup) {
        if (lookup.isEmpty()) {
            return;
        }
        builder.append("\n### ").append(subsection);
        lookup.entries().forEach(entry -> {
            builder.append("\n\n#### ").append(entry.getKey().getName());
            entry.getValue().stream().distinct()
                    .sorted(Comparator.comparingInt(WDMlaCommonRegistration.instance().priorities::byValue))
                    .forEach($ -> {
                        builder.append("\n* ").append($.getUid()).append(", ")
                                .append(WDMlaCommonRegistration.instance().priorities.byValue($)).append(", ")
                                .append($.getClass().getName());
                    });
        });
        builder.append("\n\n");
    }
}
