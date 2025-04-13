package com.gtnewhorizons.wdmla.api.view;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Experimental
public class FluidView {

    @Nullable
    public FluidStack overlay; //requires FluidStack to get icon
    public long current;
    public long max;
    @Nullable
    public String fluidName;
    @Nullable
    public String overrideText;
    /**
     * If true, the progress bar will have vertical stripes
     */
    public boolean hasScale;

    public FluidView(@Nullable FluidStack overlay) {
        this.overlay = overlay;
    }

    @Nullable
    public static FluidView readDefault(Data tank) {
        if (tank.capacity <= 0) {
            return null;
        }
        FluidStack fluidObject = tank.fluid;
        FluidView fluidView = new FluidView(fluidObject);
        long amount;
        if (fluidObject == null) {
            amount = 0;
            fluidView.fluidName = null;
        }
        else {
            amount = fluidObject.amount;
            fluidView.fluidName = fluidObject.getLocalizedName();
        }
        fluidView.current = amount;
        fluidView.max = tank.capacity;
        fluidView.hasScale = false;
        return fluidView;
    }

    public static class Data {

        public final FluidStack fluid;
        public final long capacity;

        public Data(FluidStack fluid, long capacity) {
            this.fluid = fluid;
            this.capacity = capacity;
        }

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
