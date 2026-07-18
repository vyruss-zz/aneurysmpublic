package aneurysm.structures;

public class EditorLevelInfo {
    private short numLines, numThings;
    private int lineOffs;
    private int thingOffs;
    private int palOffs;

    private int currentLevelHeaderOffs;

    public EditorLevelInfo(short numLines, int lineOffs, short numThings, int thingOffs, int palOffs, int currentLevelHeaderOffs) {
        this.numLines = numLines;
        this.lineOffs = lineOffs;
        this.numThings = numThings;
        this.thingOffs = thingOffs;
        this.palOffs = palOffs;
        this.currentLevelHeaderOffs = currentLevelHeaderOffs;
    }

    public void setNumLines(short numLines) {
        this.numLines = numLines;
    }

    public short getNumThings() {
        return numThings;
    }

    public void setNumThings(short numThings) {
        this.numThings = numThings;
    }

    public int getLineOffs() {
        return lineOffs;
    }

    public void setLineOffs(int lineOffs) {
        this.lineOffs = lineOffs;
    }

    public int getThingOffs() {
        return thingOffs;
    }

    public void setThingOffs(int thingOffs) {
        this.thingOffs = thingOffs;
    }

    public int getPalOffs() {
        return palOffs;
    }

    public void setPalOffs(int palOffs) {
        this.palOffs = palOffs;
    }

    public int getNumLines() {
        return this.numLines;
    }

    public int getCurrentLevelHeaderOffs() {
        return currentLevelHeaderOffs;
    }

    public void setCurrentLevelHeaderOffs(int currentLevelHeaderOffs) {
        this.currentLevelHeaderOffs = currentLevelHeaderOffs;
    }
}
