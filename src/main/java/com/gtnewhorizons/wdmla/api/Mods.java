package com.gtnewhorizons.wdmla.api;

import cpw.mods.fml.common.versioning.ArtifactVersion;
import cpw.mods.fml.common.versioning.DefaultArtifactVersion;
import mcp.mobius.waila.Waila;
import org.jetbrains.annotations.ApiStatus;

import cpw.mods.fml.common.Loader;

import java.util.function.Predicate;

@ApiStatus.Internal
public enum Mods {

    CREATIVEBLOCKS("CreativeBlocks", null),
    GREGTECH("gregtech", version -> new DefaultArtifactVersion("5.09.48.18").compareTo(version) <= 0),
    IGUANATWEAKS("IguanaTweaksTConstruct", null),
    TCONSTUCT("TConstruct", null),
    NOTENOUGHITEMS("NotEnoughItems", version -> new DefaultArtifactVersion("2.7.29-GTNH").compareTo(version) <= 0),
    FORGEMULTIPARTS("ForgeMultipart", null);

    public final String modID;

    /**
     * WDMla works without any external content mod. <br>
     * Not loading old version of mods can solve class name issue, will help greatly at outside GTNH
     */
    public final Predicate<ArtifactVersion> versionRequirement;
    private Boolean loaded;

    Mods(String modID, Predicate<ArtifactVersion> versionRequirement) {
        this.modID = modID;
        this.versionRequirement = versionRequirement;
    }

    public boolean isLoaded() {
        if (this.loaded == null) {
            this.loaded = Loader.isModLoaded(modID);
        }
        if (!this.loaded) {
            return false;
        }

        if(versionRequirement != null) {
            if (versionRequirement.test(Loader.instance().getIndexedModList().get(modID).getProcessedVersion())) {
                return true;
            }
            else {
                Waila.log.info("Skipped loading %s Compatibility classes due to version mismatch");
                return false;
            }
        }
        else {
            return true;
        }
    }
}
