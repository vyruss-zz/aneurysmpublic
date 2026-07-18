package aneurysm.structures;

public class CDLevelHeader extends RomLevelHeader {
    private int spriteTableOffs;

    public CDLevelHeader() {
        super();
        this.spriteTableOffs = 0;
    }

    public CDLevelHeader(short numLines, int lineOffs, short numThings, int thingOffs, short numLights, short numDoors, int palOffs, short nodeTimer, int nodeOffs, short mapXScale, short mapYScale, short mapXOffset, short mapWidth, short mapHeight, int mapOffs, int gridOffs, int spriteTableOffs) {
        super(numLines, lineOffs, numThings, thingOffs, numLights, numDoors, palOffs, nodeTimer, nodeOffs, mapXScale, mapYScale, mapXOffset, mapWidth, mapHeight, mapOffs, gridOffs);
        this.spriteTableOffs = spriteTableOffs;
    }

    public int getSpriteTableOffs() {
        return spriteTableOffs;
    }

    public void setSpriteTableOffs(int spriteTableOffs) {
        this.spriteTableOffs = spriteTableOffs;
    }

}
