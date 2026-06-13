# Better Game Setting for BTW

### v1.4.0.1
* Added defer chunk updates option
* Fixed an issue where the maximum brightness option was incorrect

---

### v1.4.0
* The required BTWCE version has been changed to version 3.1.0 or above
* Changed the slider in the video settings interface to take effect only after releasing
* Render distance adjustment
  + The actual render distance is now consistent with the render distance displayed on the button
  + The maximum render distance has been changed to 32 chunks
    - It is highly not recommended to adjust the render distance above 16 chunks
    - Consumes too much memory; 32 chunks will take up approximately 8 to 12 GB of RAM
    - Lag when generating new chunks
    - Slow world saving
* Create World screen adjustments
  + Optimized Tab button height and spacing
* Added support for setting keybinding categories for mod keybindings
  + Example
```java
// No dependency on BetterGameSetting required, but requires three translation texts: key.categories.example, key.example, and key.categories.example:key.example
public static KeyBinding keyExample = new KeyBinding("key.categories.example:key.example", Keyboard.KEY_NONE);
// Requires dependency on BetterGameSetting
KeyBindingExtra.setKeyKeyCategory(this.keyExample.keyDescription, "key.categories.example");
```
* Added a search box and type filtering to the Game Rules screen
  + Added ability to modify game rules within the world via World Options
* Significantly optimized language switching performance, enabling instant language switching
* Optimized the text display of the brightness option
* Fixed an issue where some list elements were still rendering even when out of bounds
* Optimized code

---

### v1.3.0.2 & v1.3.0.3 (by Maro)
* Fixed rendering issues when using third-person view

---

### v1.3.0.1
* Fixed some compatibility issues
* Fixed an unresponsive issue
* Optimized code

---

### v1.3.0
* Adapted for 3.0.0 Release.
* Optimized key bindings; now supports resetting mod keys.
* Optimized the position of some buttons in the key binding interface.
* Added the Create World interface from 1.19.4+.
  * Ported and heavily modified from [CreateWorldUI](https://github.com/song682/CreateWorldUI).
  * Added a Game Rules settings interface, and enabled modified game rules to take effect properly.
* Enhanced the Sound Settings interface, though adjustments for some sound effects are not yet available.
* Added a search box to the Resource Pack interface with a click-to-select highlight effect.
* Reduced the size of text fields to match buttons of the same dimensions.
* Lightened the border color of text fields when focused.
* Added a scrolling text effect for long button text.
* Added a search box to the Language interface, supporting searches by language code.
* Optimized the tooltip logic in the Video Settings interface.
* Optimized the rendering layer order for the new list interfaces added by this mod.
* Added a confirmation window for using incompatible resource packs.
* Introduced `IButton` and `ITextField` interfaces for quickly implementing features like position/size changes, text field hints, and scrolling text rendering.
* Optimized some package names, class names, and code structure.
* Fixed an issue where the "Force Unicode Font" button text would not update when switching languages.
* Bundled the Fabric-ASM mod internally.

---

### v1.2.3-fix
* Fixed the issue where the open control gui crashed

---

### v1.2.3
* Added `Transparent Background` option
* Added `Highlight Button Text` option
* Reimplemented a more modern `GuiSlot`
* Optimized the code
* Fixed the issue where elements outside the list in the resource pack interface were still displayed
* Fixed the issue where the maximum interface size was still smaller than the automatic setting
* Fixed the issue where the shadow of English & Numbers was too far off
* Fixed the issue where the disabled button area in the resource pack was incorrect

---

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