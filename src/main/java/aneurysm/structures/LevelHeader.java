package aneurysm.structures;

public class LevelHeader {
    private short numLines;
    private int lineoffs;
    private short numThings;
    private int thingoffs;
    private short numLights;
    private short numDoors;
    private int paloffs;
    private short nodeTimer;
    private int nodeoffs;
    private short minimapXScale;
    private short minimapYScale;
    private short minimapXOffset;
    private short minimapWidth;
    private short minimapHeight;
    private int minimapoffs;
    private int gridoffs;
    private int spriteTableOffs;

    public LevelHeader() {
        this.numLines = 0;
        this.lineoffs = 0;
        this.numThings = 0;
        this.thingoffs = 0;
        this.numLights = 0;
        this.numDoors = 0;
        this.paloffs = 0;
        this.nodeTimer = 0;
        this.nodeoffs = 0;
        this.minimapXScale = 0;
        this.minimapYScale = 0;
        this.minimapXOffset = 0;
        this.minimapWidth = 0;
        this.minimapHeight = 0;
        this.minimapoffs = 0;
        this.gridoffs = 0;
        this.spriteTableOffs = 0;
    }

    public LevelHeader(short numLines, int lineoffs, short numThings, int thingoffs,
                          short numLights, short numDoors, int paloffs, short nodeTimer,
                          int nodeoffs, short minimapXScale, short minimapYScale,
                          short minimapXOffset, short minimapWidth, short minimapHeight,
                          int minimapoffs, int gridoffs, int spriteoffs) {
        this.numLines = numLines;
        this.lineoffs = lineoffs;
        this.numThings = numThings;
        this.thingoffs = thingoffs;
        this.numLights = numLights;
        this.numDoors = numDoors;
        this.paloffs = paloffs;
        this.nodeTimer = nodeTimer;
        this.nodeoffs = nodeoffs;
        this.minimapXScale = minimapXScale;
        this.minimapYScale = minimapYScale;
        this.minimapXOffset = minimapXOffset;
        this.minimapWidth = minimapWidth;
        this.minimapHeight = minimapHeight;
        this.minimapoffs = minimapoffs;
        this.gridoffs = gridoffs;
        this.spriteTableOffs = spriteoffs;
    }

    public short getNumLines() {
        return numLines;
    }

    public int getLineoffs() {
        return lineoffs;
    }

    public short getNumThings() {
        return numThings;
    }

    public int getThingoffs() {
        return thingoffs;
    }

    public short getNumLights() {
        return numLights;
    }

    public short getNumDoors() {
        return numDoors;
    }

    public int getPaloffs() {
        return paloffs;
    }

    public short getNodeTimer() {
        return nodeTimer;
    }

    public int getNodeoffs() {
        return nodeoffs;
    }

    public short getMinimapXScale() {
        return minimapXScale;
    }

    public short getMinimapYScale() {
        return minimapYScale;
    }

    public short getMinimapXOffset() {
        return minimapXOffset;
    }

    public short getMinimapWidth() {
        return minimapWidth;
    }

    public short getMinimapHeight() {
        return minimapHeight;
    }

    public int getMinimapoffs() {
        return minimapoffs;
    }

    public int getGridoffs() {
        return gridoffs;
    }

    public void setNumLines(short numLines) {
        this.numLines = numLines;
    }

    public void setLineoffs(int lineoffs) {
        this.lineoffs = lineoffs;
    }

    public void setNumThings(short numThings) {
        this.numThings = numThings;
    }

    public void setThingoffs(int thingoffs) {
        this.thingoffs = thingoffs;
    }

    public void setNumLights(short numLights) {
        this.numLights = numLights;
    }

    public void setNumDoors(short numDoors) {
        this.numDoors = numDoors;
    }

    public void setPaloffs(int paloffs) {
        this.paloffs = paloffs;
    }

    public void setNodeTimer(short nodeTimer) {
        this.nodeTimer = nodeTimer;
    }

    public void setNodeoffs(int nodeoffs) {
        this.nodeoffs = nodeoffs;
    }

    public void setMinimapXScale(short minimapXScale) {
        this.minimapXScale = minimapXScale;
    }

    public void setMinimapYScale(short minimapYScale) {
        this.minimapYScale = minimapYScale;
    }

    public void setMinimapXOffset(short minimapXOffset) {
        this.minimapXOffset = minimapXOffset;
    }

    public void setMinimapWidth(short minimapWidth) {
        this.minimapWidth = minimapWidth;
    }

    public void setMinimapHeight(short minimapHeight) {
        this.minimapHeight = minimapHeight;
    }

    public void setMinimapoffs(int minimapoffs) {
        this.minimapoffs = minimapoffs;
    }

    public void setGridoffs(int gridoffs) {
        this.gridoffs = gridoffs;
    }


    public int getSpriteTableOffs() {
        return spriteTableOffs;
    }

    public void setSpriteTableOffs(int spriteTableOffs) {
        this.spriteTableOffs = spriteTableOffs;
    }

    @Override
    public String toString() {
        return String.format("paloffs=%08x, nodeoffs=%08x%n", paloffs, nodeoffs);

    }

}
