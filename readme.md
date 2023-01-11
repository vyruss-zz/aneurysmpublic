
# Aneurysm
Current version: 0.6.2
Aneurysm is a (limited) level editor for the 1994 Megadrive and Mega CD FPS game, "Bloodshot" written in Java.  At present, it features:

  - The ability to load and read all levels in the ROM or as CD version .LEV.
  - The ability to move level walls around, as well as change them from walls to door, change their texture, and change open direction.
  - The ability to change placement and type of things placed in the levels
  - Zooming in and out capabilities
  - Varying levels of grid spacing
  - The ability to snap to grid *relative to grid size*
  - The ability to view maps as present in data as well as rotated 90 degrees to reflect in-game orientation (**Aneurysm also automatically accounts for this when editing levels**)
  - Any version of the ROM with corrupted textures (0x0D7822 and 0x0DB822) can be patched with this tool on first load
  - Remembers the last file being edited, the last level being edited, its orientation, mode, zoom and grid level
  - Automatic checksum correction for ROM version

### What's New version 0.6.2
 - I honestly have no idea what's new anymore - last public commit was nearly 2 years ago.  I *think* I added support to import the Sega CD extra enemy in 0.0.6, some bug fixes occurred and others.
 - This release (0.6.2) has added something that was trivial and should have been from day one - Door colors in the editor view.  The level display has become just a little bit more colorful.

### Default Controls and Operations
  - Left Mouse Button / Space: Select highlighted item
     * For simplicity, only one item can be selected at a time and will prevent highlighting of other items until the selected item is deselected.
  - Middle Mouse Button / M key: move highlighted / selected item
  - Right Mouse Button + Move Mouse / Z Key + Move Mouse / Arrow Keys : Pan map around
  - Mousewheel / + and - keys: Zoom in and out
  - G key: Cycle Grid Intensity
  - F key: Toggle grid Free / Snap mode
  - Ctrl+S: Writes any changes to your ROM file.
  - R Key: Toggle Rotate 90 Mode
  - L / T / V Keys: Set between Lines / Things / Vertices Mode

### Future Goals
 - Automatically expand ROM version to include CD version assets (new textures and levels as well as one new enemy - user MUST supply their own copy of the CD version.)
 - Ability to not just change attributes of levels, but to also insert or delete new elements (lines and things).
 - Ability to generate and store new minimaps based on the current level being edited in the tool
