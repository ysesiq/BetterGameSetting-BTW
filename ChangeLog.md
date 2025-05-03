# Better Game Setting for BTW


### v1.2.2
* Annotations have been added for each option of video settings
* Added check whether the resource package is suitable for the current version
* Removed the 'Force turn off advanced OpenGL when using a non-Nvidia GPU'
* Optimized the code
* May fix an issue where the selected resource pack cannot be clicked in some cases (cannot be reproduced)

---

### v1.2.1
* Added input method fix mod( https://github.com/lss233/InputMethodBlocker )
* Optimized the save of game settings information in this mod, and now no longer overwrites vanilla entries
* Force turn off advanced OpenGL when using a non-Nvidia GPU
* Fixed an issue where FontFixer could not be enabled properly
* Fixed an issue where the opening parenthesis "(" was not displayed

---

### v1.2.0
* Added video settings in 1.7.2+
    * For compatibility (Shaders Mod Core), MIPMAP and various heterosexual filtering options are not added
* Incorporated FontFixer mod
* Optimized the scrollbar of the control gui
* Optimized the code
* Fixed compatibility issues with FreeLook mod
* Fixed an issue where the mod's GuiSlot did not have a dark background when loading at the same time as some mods
* Fixed an issue where when clicking on the new GuiSlot in this mod, the sound effect would not play
* Fixed an issue where non-Mojangles (ASCII) fonts were not showing text shadows

---

## 1.1.0
* Ported from BetterGameSetting (MITE)
* Due to the lack of Mixin Extra and EnumExtends, the sound settings will not be ported for the time being
* Select multiple resource packs
* Better adjustment of render distance & gui scale
* Custom FPS limit
* Lower FOV
* Better setup of keybindings  (Note: Resetting mod keybindings is not currently supported)