package com.gtnewhorizons.wdmla.api.view;

import com.github.bsideup.jabel.Desugar;
import com.gtnewhorizons.wdmla.api.ui.IComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.TextComponent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@ApiStatus.Experimental
public class FluidView {

    public IComponent overlay;
    public String current;
    public String max;
    public float ratio;
    @Nullable
    public String fluidName;
    @Nullable
    public String overrideText;

    public FluidView(IComponent overlay) {
        this.overlay = overlay;
        Objects.requireNonNull(overlay);
    }

    @Nullable
    public static FluidView readDefault(Data tank) {
        if (tank.capacity <= 0) {
            return null;
        }
        FluidStack fluidObject = tank.fluid;
        //IElementHelper.get().fluid(fluidObject) snownee.jade.impl.ui.FluidStackElement
        FluidView fluidView = new FluidView(new TextComponent("PlaceHolder Fluid Object")); //TODO: attach fluid component for progress bar
        fluidView.max = tank.capacity + StatCollector.translateToLocal("hud.wdmla.msg.millibucket");
        long amount;
        if (fluidObject == null) {
            amount = 0;
            fluidView.fluidName = null;
            fluidView.overrideText = "EMPTY / " + fluidView.max; //TODO: localize
        }
        else {
            amount = fluidObject.amount;
            fluidView.fluidName = fluidObject.getLocalizedName();
        }
        fluidView.current = amount + StatCollector.translateToLocal("hud.wdmla.msg.millibucket"); //TODO: formatter
        fluidView.ratio = (float) ((double) amount / tank.capacity);
        return fluidView;
    }

    //TODO: stop using Jabel records
    @Desugar
    public record Data(FluidStack fluid, long capacity) {

        public static NBTTagCompound encode(Data data) {
            NBTTagCompound encoded = new NBTTagCompound();
            if(data.fluid != null) {
                data.fluid.writeToNBT(encoded);
            }
            encoded.setLong("capacity", data.capacity);
            return encoded;
        }

        public static Data decode(NBTTagCompound nbt) {
            return new Data(FluidStack.loadFluidStackFromNBT(nbt),
                    nbt.getLong("capacity"));
        }
    }
}
