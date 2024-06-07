package com.gtnewhorizons.wdmla.api;

import cpw.mods.fml.common.Loader;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public enum Mods {
    CREATIVEBLOCKS("CreativeBlocks"),
    GREGTECH("gregtech"),
    IGUANATWEAKS("IguanaTweaksTConstruct"),
    TCONSTUCT("TConstruct"),
    NOTENOUGHITEMS("NotEnoughItems"),
    FORGEMULTIPARTS("ForgeMultipart");

    public final String modID;
    private Boolean loaded;

    Mods(String modID) {
        this.modID = modID;
    }

    public boolean isLoaded() {
        if (this.loaded == null) {
            this.loaded = Loader.isModLoaded(modID);
        }
        return this.loaded;
    }
}
