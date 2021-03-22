
# Aneurysm

Aneurysm is a (limited) level editor for the 1994 Megadrive FPS game, "Bloodshot".  This tool is written in Java 1.8.  At present, it features:

  - The ability to load and read all levels in the ROM.
  - The ability to move level walls around, as well as change them from walls to door, change their texture, and change open direction.
  - The ability to change placement and type of things placed in the levels
  - Zooming in and out capabilities
  - Varying levels of grid spacing
  - The ability to snap to grid *relative to grid size*
  - The ability to view maps as present in ROM data as well as rotated 90 degrees to reflect in-game orientation (**Aneurysm also automatically accounts for this when editing levels**)
  - Any version of the ROM with corrupted textures (0x0D7822 and 0x0DB822) can be patched with this tool on first load
  - Remembers the last file being edited, the last level being edited, its orientation, mode, zoom and grid level
  - Automatic checksum correction

### Controls and Operations
At present, Aneurysm's controls are statically bound [input configuration will arrive in a near future release]:
  - Left Mouse Button / Space: Select highlighted item
     * For simplicity, only one item can be selected at a time and will prevent highlighting of other items until the selected item is deselected.
  - Middle Mouse Button / M key: move highlighted / selected item
  - Right Mouse Button + Move Mouse / Z Key + Move Mouse / Arrow Keys : Pan map around
  - Mousewheel / + and - keys: Zoom in and out
  - G key: Cycle Grid Intensity
  - F key: Toggle grid Free / Snap mode
  - Ctrl+S: Writes any changes to your ROM file.
  - Ctrl+W: Writes current level data as raw output to file
  - R Key: Toggle Rotate 90 Degrees Mode
  - L / T / V Keys: Set between Lines / Things / Vertices Mode

### Known Issues
 - Inability to select in lines mode certain lines that have their vertices inverted.  Solution: Swap the vertices' positions.

### Future Goals
 - Ability to modify levels of the Sega CD version (saved as .LEV files)
 - Automatically expand ROM version to include CD version assets (new textures and levels as well as one new enemy - user MUST supply their own copy of the CD version.)
 - Ability to configure keyboard and mouse inputs
 - Ability to not just change attributes of levels, but to also insert or delete new elements.
  
 
Special thanks to Dark Pulse for worming down this rabbit hole with me :)