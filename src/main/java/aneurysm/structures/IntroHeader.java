package aneurysm.structures;

public class IntroHeader {
    // Fields
    private int rncoffs;
    private short len;
    private int tilemapoffs;
    private int paloffs;

    // Constructor
    public IntroHeader(int rncoffs, short len, int tilemapoffs, int paloffs) {
        this.rncoffs = rncoffs;
        this.len = len;
        this.tilemapoffs = tilemapoffs;
        this.paloffs = paloffs;
    }

    // Getters
    public int getRncOffs() {
        return rncoffs;
    }

    public short getLen() {
        return len;
    }

    public int getTilemapOffs() {
        return tilemapoffs;
    }

    public int getPalOffs() {
        return paloffs;
    }

    // Optional: toString method for debugging
    @Override
    public String toString() {
        return "IntroHeader{" +
                "rncoffs=" + rncoffs +
                ", len=" + len +
                ", tilemapoffs=" + tilemapoffs +
                ", paloffs=" + paloffs +
                '}';
    }

}
