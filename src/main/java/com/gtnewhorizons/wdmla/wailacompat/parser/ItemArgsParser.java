package com.gtnewhorizons.wdmla.wailacompat.parser;

import com.gtnewhorizons.wdmla.api.ITTRenderParser;
import com.gtnewhorizons.wdmla.impl.ui.component.Component;
import com.gtnewhorizons.wdmla.impl.ui.component.ItemComponent;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemArgsParser implements ITTRenderParser {
    @Override
    public Component parse(String[] args) {
        int type = Integer.parseInt(args[0]); // 0 for block, 1 for item
        String name = args[1]; // Fully qualified name
        int amount = Integer.parseInt(args[2]);
        int meta = Integer.parseInt(args[3]);

        ItemStack stack = null;
        if (type == 0) stack = new ItemStack((Block) Block.blockRegistry.getObject(name), amount, meta);
        if (type == 1) stack = new ItemStack((Item) Item.itemRegistry.getObject(name), amount, meta);

        return new ItemComponent(stack);
    }
}
