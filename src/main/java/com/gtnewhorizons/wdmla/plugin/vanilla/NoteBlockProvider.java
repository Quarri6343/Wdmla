package com.gtnewhorizons.wdmla.plugin.vanilla;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.world.NoteBlockEvent;

import org.apache.commons.lang3.StringUtils;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;

import mcp.mobius.waila.api.SpecialChars;

public enum NoteBlockProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    INSTANCE;

    private static final String[] PITCH = { "F♯/G♭", "G", "G♯/A♭", "A", "A♯/B♭", "B", "C", "C♯/D♭", "D", "D♯/E♭", "E",
            "F" };
    private static final String[] OCTAVE = { SpecialChars.WHITE, SpecialChars.YELLOW, SpecialChars.GOLD };
    private static final Map<Material, NoteBlockEvent.Instrument> INSTRUMENTS = new HashMap<>() {

        {
            put(Material.rock, NoteBlockEvent.Instrument.BASSDRUM);
            put(Material.sand, NoteBlockEvent.Instrument.SNARE);
            put(Material.glass, NoteBlockEvent.Instrument.CLICKS);
            put(Material.wood, NoteBlockEvent.Instrument.BASSGUITAR);
        }
    };

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        if (accessor.getServerData().hasKey("note")) {
            int x = accessor.getHitResult().blockX;
            int y = accessor.getHitResult().blockY - 1;
            int z = accessor.getHitResult().blockZ;
            Material material = accessor.getWorld().getBlock(x, y, z).getMaterial();
            NoteBlockEvent.Instrument instrument = INSTRUMENTS.get(material);
            if (instrument == null) {
                instrument = NoteBlockEvent.Instrument.PIANO;
            }
            String instrumentKey = "hud.msg.wdmla.instrument." + StringUtils.lowerCase(instrument.toString());
            byte note = accessor.getServerData().getByte("note");
            String pitch = PITCH[note % PITCH.length];
            String octave = OCTAVE[note / PITCH.length];
            tooltip.text(String.format("%s %s", StatCollector.translateToLocal(instrumentKey), octave + pitch));
        }
    }

    @Override
    public void appendServerData(NBTTagCompound data, BlockAccessor accessor) {
        if (accessor.getTileEntity() instanceof TileEntityNote note) {
            note.writeToNBT(data);
        }
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.NOTE_BLOCK;
    }
}
