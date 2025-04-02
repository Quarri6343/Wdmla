# WDMla
**What DreamMaster looks at** is what you look at<br>
Still in alpha, api is subject to change

# Main Features
- Allows rich non-text expression like item, entity, progress bar
- Backported many code features from Jade including new and fast registration system
- Backported powerful component system from The One Probe, makes layout creation more flexible
- Better config screen and options
- Rewritten internal tooltips includes WailaHarvestability, Wawla (WailaPlugins is planned)
- Achieved almost full backward compatibility by retaining legacy Waila api

# Required Dependencies
- NEI (removal is planned)
- [GTNHLib](https://github.com/GTNewHorizons/GTNHLib/releases/latest)

# Supported mods
- almost every mods that Waila supports
- almost every mods that supports Waila
- And more...?

# Permanent Incompatibilities
- Waila (bundled)
- Waila Harvestability (bundled)


# Partial Incompatibilities
- Wawla -> required to disable some configs of overlapped features
  - wawla.harvest.showTool
  - wawla.harvest.showHarvest
  - wawla.harvest.showTier
  - wawla.harvest.showProgress
  - wawla.beacon.showLevels
  - wawla.beacon.showPrimary
  - wawla.beacon.showSecondary
  - wawla.furnace.input
  - wawla.furnace.output
  - wawla.furnace.fuel
  - wawla.furnace.burntime
  - wawla.showHead
  - wawla.info.showhardness
  - wawla.info.showResistance
  - wawla.showEquipment
  - wawla.showMobArmor
  - wawla.pets.showOwner
  - wawla.pets.sitting
  - wawla.pets.age
  - wawla.pets.cooldown
  - wawla.horse.showjump
  - wawla.horse.showspeed
  - wawla.tnt.fuse
  - wawla.showProfession

# Other items to Note:
- Removed Enchant Screen

# Credits
- [Jade](https://github.com/Snownee/Jade) 
  - Backported many codes under same license
- [The One Probe](https://github.com/McJtyMods/TheOneProbe)
  - Backported HUD component codes under [MIT license](https://github.com/McJtyMods/TheOneProbe/blob/1.20/LICENCE)
