package aneurysm.structures;

public class TileMappable {
    private int paloffs, gfxoffs, tilemapoffs, tilemaplen, gfxlen;
    private char type;
    private String name;

    public TileMappable() {
        paloffs = 0;
        gfxoffs = 0;
        gfxlen = 0;
        tilemapoffs = 0;
        tilemaplen = 0;
        type = ' ';
        name = "";
    }

    public TileMappable(int paloffs, int gfxoffs, int gfxlen, int tilemapoffs, int tilemaplen, char type, String name) {
        this.paloffs = paloffs;
        this.gfxoffs = gfxoffs;
        this.gfxlen = gfxlen;
        this.tilemapoffs = tilemapoffs;
        this.tilemaplen = tilemaplen;
        this.type = type;
        this.name = name;
    }

    public int getPaloffs() {
        return paloffs;
    }

    public void setPaloffs(int paloffs) {
        this.paloffs = paloffs;
    }

    public int getGfxoffs() {
        return gfxoffs;

    }

    public void setGfxoffs(int gfxoffs) {
        this.gfxoffs = gfxoffs;
    }

    public int getTilemapoffs() {
        return tilemapoffs;
    }

    public void setTilemapoffs(int tilemapoffs) {
        this.tilemapoffs = tilemapoffs;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTilemaplen(int tilemaplen) {
        this.tilemaplen = tilemaplen;
    }
    public int getTilemaplen() {
        return tilemaplen;
    }

    public void setGfxlen(int gfxlen) {
        this.gfxlen = gfxlen;
    }
    public int getGfxlen() {
        return gfxlen;
    }
    @Override
    public String toString() {
        return "TileMappable{" +
                "paloffs=0x" + Integer.toHexString(paloffs) + ", " +
                "gfxoffs=0x" + Integer.toHexString(gfxoffs) + ", " +
                "tilemapoffs=0x" + Integer.toHexString(tilemapoffs) + ", " +
                "tilemaplen=" + tilemaplen + ", " +
                "gfxlen=" + gfxlen + ", " +
                "type='" + type + '\'' + ", " +
                "name='" + name + '\'' +
                '}';
    }
}
