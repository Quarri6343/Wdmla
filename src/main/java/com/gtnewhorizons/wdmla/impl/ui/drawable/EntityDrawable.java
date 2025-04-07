package com.gtnewhorizons.wdmla.impl.ui.drawable;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.BossStatus;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

import com.gtnewhorizons.wdmla.api.ui.IDrawable;
import com.gtnewhorizons.wdmla.api.ui.sizer.IArea;

import mcp.mobius.waila.Waila;

public class EntityDrawable implements IDrawable {

    private final @NotNull EntityLiving entity;

    public EntityDrawable(@NotNull EntityLiving entity) {
        this.entity = entity;
    }

    @Override
    public void draw(IArea area) {
        String bossName = BossStatus.bossName;
        int bossTimeout = BossStatus.statusBarTime;
        boolean bossHasColorModifier = BossStatus.hasColorModifier;
        float renderTagRange = RendererLivingEntity.NAME_TAG_RANGE;
        float renderTagRangeSneaking = RendererLivingEntity.NAME_TAG_RANGE_SNEAK;
        // editing entity custom name directly will trigger DataWatcher
        RendererLivingEntity.NAME_TAG_RANGE = 0;
        RendererLivingEntity.NAME_TAG_RANGE_SNEAK = 0;
        try {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GuiInventory.func_147046_a(area.getX(), area.getY() + area.getH(), area.getW(), 135, 0, entity);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
        } catch (Exception e) {
            Waila.log.error("Error rendering instance of entity", e);
        }
        RendererLivingEntity.NAME_TAG_RANGE = renderTagRange;
        RendererLivingEntity.NAME_TAG_RANGE_SNEAK = renderTagRangeSneaking;
        BossStatus.bossName = bossName;
        BossStatus.statusBarTime = bossTimeout;
        BossStatus.hasColorModifier = bossHasColorModifier;
    }
}
