package aneurysm.io;

import java.awt.Color;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

import aneurysm.config.ConfigOptions;
import aneurysm.offsets.CDOffsets;
import aneurysm.pack.ThingPack;
import aneurysm.patchdata.TexturePatch0D7822;
import aneurysm.patchdata.TexturePatch0DB822;
import aneurysm.structures.*;
import aneurysm.ui.DataLists;
import aneurysm.ui.Window;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileReader {

    private final ConfigOptions config = new ConfigOptions();
    private static boolean configLoaded = false;
    private int levelHeaderLocation = 0;

    public static boolean isConfigLoaded() {
        return configLoaded;
    }

    public static void setConfigLoaded(boolean configLoaded) {
        FileReader.configLoaded = configLoaded;
    }

    public ConfigOptions getConfig() {
        return this.config;
    }

    public Image readCDImage(int offset, int w, int h) {
        BufferedImage im = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        try (RandomAccessFile rin = new RandomAccessFile(config.getLocation(), "rw")) {
            rin.seek(offset);
            for (int j = 0; j < w; j += 2) {
                for (int i = 0; i < h; i++) {
                    byte read = rin.readByte();
                    byte lower, higher;
                    if (read == 0) {
                        continue;
                    }
                    if (j == w - 1) {
                        im.setRGB(j, i, (DataLists.getLevelPal()[((read & 0x000000F0) / 16) + 32].getRGB()));
                    } else {
                        if (((read & 0xF0)) != (read & 0x0F) * 0x10) {

                            if ((read & 0xF0) > ((read & 0x0F) * 16)) {
                                lower = (byte) ((read << 4) + (read & 0x0F));
                                higher = (byte) ((read & 0xF0) + ((read & 0xF0) / 0x10));

                                im.setRGB(j, i, (DataLists.getLevelPal()[((higher & 0x000000F0) / 16) + 32].getRGB()));
                                im.setRGB(j + 1, i, (DataLists.getLevelPal()[((lower & 0x000000F0) / 16) + 32].getRGB()));
                            } else {
                                higher = (byte) ((read << 4) + (read & 0x0F));
                                lower = (byte) ((read & 0xF0) + ((read & 0xF0) / 0x10));

                                im.setRGB(j + 1, i, (DataLists.getLevelPal()[((higher & 0x000000F0) / 16) + 32].getRGB()));
                                im.setRGB(j, i, (DataLists.getLevelPal()[((lower & 0x000000F0) / 16) + 32].getRGB()));
                            }

                        } else {
                            im.setRGB(j, i, (DataLists.getLevelPal()[((read & 0x000000F0) / 16) + 32].getRGB()));
                            im.setRGB(j + 1, i, (DataLists.getLevelPal()[((read & 0x000000F0) / 16) + 32].getRGB()));
                        }

                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return im;
    }

    public Image readImage(int offset, int width, int height) {
        BufferedImage im = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        try {
            RandomAccessFile rin = new RandomAccessFile(config.getLocation(), "rw");
            rin.seek(offset);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    byte read = rin.readByte();
                    if (read == 0) {
                        continue;
                    }
                    im.setRGB(i, j, DataLists.getLevelPal()[(read & 0x000000ff) + 32].getRGB());
                }
            }
            rin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return im;
    }

    public List<MapObjectStructure> readMapObjects(String filename) throws IOException {

        List<MapObjectStructure> objects = new ArrayList<MapObjectStructure>();

        MapObjectStructure obj;

        RandomAccessFile ind = new RandomAccessFile(filename, "rw");
        ind.seek(DataLists.getCurrentLevelInfo().getThingOffs());
        int current = 0;

        while (current < DataLists.getCurrentLevelInfo().getNumThings()) {
            obj = new MapObjectStructure();
            obj.setThingID(ind.readInt());
            obj.setOffset_major(ind.readByte());
            obj.setOffset_minor(ind.readByte());
            obj.setY(ind.readShort());
            obj.setX(ind.readShort());
            objects.add(obj);
            current++;
        }
        ind.close();

        return objects;
    }

    private Image readCartWallGraphics(int location, short length, String filename) {
        BufferedImage image = new BufferedImage(length, 128, BufferedImage.TYPE_3BYTE_BGR);
        try {
            RandomAccessFile rin = new RandomAccessFile(filename, "rw");

            rin.seek(location);

            for (int i = 0; i < length; i++) {
                for (int j = 0; j < 64; j++) {
                    byte read = rin.readByte();
                    image.setRGB(i, 63 - j, DataLists.getLevelPal()[(read & 0x000000ff) / 0x10].getRGB());
                    image.setRGB(i, 64 + j, DataLists.getLevelPal()[((read & 0x000000ff) / 0x10) + 0x10].getRGB());
                }
                rin.seek(rin.getFilePointer() + 64);
            }
            rin.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return image;
    }

    public short[] getTileMap(int offset, int len, String loc) {

        try (RandomAccessFile raf = new RandomAccessFile(loc, "r")) {
            raf.seek(offset);
            short[] tileMap = new short[len];
            for (int i = 0; i < len; i++) {
                tileMap[i] = raf.readShort();
            }
            raf.close();
            return tileMap;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
            return null;
        }
    }

    private void readByteArrayToImage(Byte[][] data, BufferedImage image) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                image.setRGB(i, (64 + j), DataLists.getLevelPal()[(data[i][j]) + 16].getRGB());
                image.setRGB(i, (63 - j), DataLists.getLevelPal()[data[i][j]].getRGB());
            }
        }
    }

    private Byte[][] readRomSprite(int offset, int w, int h) {
        Byte[][] out = new Byte[w][h];
        try {
            RandomAccessFile rin = new RandomAccessFile(config.getLocation(), "rw");
            rin.seek(offset);
            for (int j = 0; j < w; j++) {
                for (int i = 0; i < h; i++) {
                    out[j][i] = (rin.readByte());
                }
            }
            rin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    public Byte[][] readSprite(int location, int w, int h) {
        Byte[][] out;
        if (!DataLists.isCdOrCart())
            out = readRomSprite(location, w, h);
        else {
            out = readCDSprite(location, w, h, config.getLocation());
        }
        return out;
    }

    public void writeROMSprite(int location, Byte[][] currentImage) {
        try {
            RandomAccessFile rin = new RandomAccessFile(config.getLocation(), "rw");
            rin.seek(location);
            for (int i = 0; i < currentImage.length; i++) {
                for (int j = 0; j < currentImage[0].length; j++) {
                    rin.writeByte(currentImage[i][j]);
                }
            }
            rin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Byte[][] compressImage(Byte[][] image) {
        Byte[][] out;
        if (image.length % 2 == 1)
            out = new Byte[image.length / 2 + 1][image[0].length];
        else
            out = new Byte[image.length / 2][image[0].length];

        int currentRow = 0;
        for (int j = 0; j < image.length; j += 2) {
            for (int i = 0; i < image[0].length; i++) {
                if (j == image.length - 1)
                    out[currentRow][i] = (byte) (image[j][i] & 0x0000000F);
                else {
                    out[currentRow][i] = (byte) (((image[j][i] & 0x0000000F) * 16) + (image[j + 1][i] & 0x0000000F));
                }
            }
            currentRow++;
        }

        return out;
    }

    public void writeCDSprite(int location, Byte[][] currentImage) {
        Byte[][] toWrite = compressImage(currentImage);
        try {
            RandomAccessFile rin = new RandomAccessFile(config.getLocation(), "rw");
            rin.seek(location);

            for (int i = 0; i < toWrite.length; i++) {
                for (int j = 0; j < toWrite[0].length; j++) {
                    rin.writeByte(toWrite[i][j]);
                }
            }
            rin.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public byte[] readByteArray(int offset, int len, String loc) {
        byte[] out = new byte[len];

        try {
            RandomAccessFile rin = new RandomAccessFile(loc, "rw");
            rin.seek(offset);
            for (int i = 0; i < len; i++) {
                out[i] = rin.readByte();
            }
            rin.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return out;
    }

    public short[] readShortArray(int offset, int tilemaplen) {
        short[] out = new short[tilemaplen];

        try {
            RandomAccessFile rin = new RandomAccessFile(config.getLocation(), "rw");
            rin.seek(offset);
            for (int i = 0; i < tilemaplen; i++) {
                out[i] = (rin.readShort());
            }
            rin.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return out;
    }

    public Byte[][] readCDSprite(int offset, int w, int h, String location) {
        Byte[][] out = new Byte[w][h];
        if (offset > 0) {
            try {
                RandomAccessFile rin = new RandomAccessFile(location, "rw");
                rin.seek(offset);
                for (int j = 0; j < w; j += 2) {
                    for (int i = 0; i < h; i++) {
                        byte read = (rin.readByte());
                        byte higher;
                        byte lower;

                        if (j == w - 1)
                            out[j][i] = (byte) ((read & 0x000000F0) / 16);
                        else {
                            if (((read & 0xF0)) != (read & 0x0F) * 0x10) {

                                if ((read & 0xF0) > ((read & 0x0F) * 16)) {
                                    lower = (byte) ((read << 4) + (read & 0x0F));
                                    higher = (byte) ((read & 0xF0) + ((read & 0xF0) / 0x10));

                                    out[j][i] = (byte) ((higher & 0x000000F0) / 16);
                                    out[j + 1][i] = (byte) ((lower & 0x000000F0) / 16);
                                } else {
                                    higher = (byte) ((read << 4) + (read & 0x0F));
                                    lower = (byte) ((read & 0xF0) + ((read & 0xF0) / 0x10));

                                    out[j + 1][i] = (byte) ((higher & 0x000000F0) / 16);
                                    out[j][i] = (byte) ((lower & 0x000000F0) / 16);
                                }

                            } else {
                                out[j][i] = (byte) ((read & 0x000000F0) / 16);
                                out[j + 1][i] = (byte) ((read & 0x000000F0) / 16);
                            }

                        }
                    }
                }
                rin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            out = new Byte[][]{{0}};

        }
        return out;
    }

    private Image readCDWallGraphics(int location, int actual, short length, String filename) {
        BufferedImage image = new BufferedImage(length, 128, BufferedImage.TYPE_INT_ARGB);
        Byte[][] convert = readCDTexture(location & 0x00ffffff, length);
        readByteArrayToImage(convert, image);

        return image;
    }

    public void populateCDOffsets() {
        try {
            RandomAccessFile rin = new RandomAccessFile(config.getLocation(), "rw");
            rin.seek(0x30 - 4);

            rin.seek(rin.readInt());

            for (int i = 0; i < CDOffsets.getSpriteOffsets().length; i++) {
                CDOffsets.getSpriteOffsets()[i] = rin.readInt();
            }

            rin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildROMSpriteCache() {
        HashMap<Integer, Byte[][]> spriteCache = new HashMap<>();
        ArrayList<Integer> actorSprites;
        int offs;
        short w, h;
        try {
            RandomAccessFile rin = new RandomAccessFile(config.getLocation(), "r");
            //projectiles
            for (ProjectileDefinition proj : DataLists.getProjectiles().values()) {
                rin.seek(proj.getSpriteOffs());
                w = rin.readShort();
                h = rin.readShort();
                rin.readInt();
                offs = rin.readInt();
                spriteCache.put(proj.getSpriteOffs(), readRomSprite(offs, w, h));
            }
            DataLists.setProjectileSpriteCache(spriteCache);
            spriteCache = new HashMap<>();
            //sprite anims
            for (Integer sprdef : DataLists.getEditorActorInfo().getActorSpriteDefs().keySet()) {
                actorSprites = DataLists.getEditorActorInfo().getActorSpriteDefs().get(sprdef);
                for (Integer actorSprite : actorSprites) {
                    rin.seek(actorSprite);
                    w = rin.readShort();
                    h = rin.readShort();
                    rin.readInt();
                    offs = rin.readInt();
                    spriteCache.put(actorSprite, readRomSprite(offs, w, h));
                }
            }
            DataLists.setActorSpriteCache(spriteCache);
            rin.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void buildCDSpriteCache() {
        HashMap<Integer, Byte[][]> spriteCache = new HashMap<>();
        ArrayList<Integer> actorSprites;
        int offs;
        short w, h;
        try {
            RandomAccessFile rin = new RandomAccessFile(config.getShellBinLocation(), "r");
            RandomAccessFile lev = new RandomAccessFile(config.getLocation(), "r");
            //projectiles
            for (ProjectileDefinition proj : DataLists.getProjectiles().values()) {
                rin.seek(proj.getSpriteOffs());
                w = rin.readShort();
                h = rin.readShort();
                rin.readInt();
                offs = rin.readInt() - 0x8000;
                spriteCache.put(proj.getSpriteOffs(), readCDSprite(offs, w, h, config.getShellBinLocation()));
            }
            DataLists.setProjectileSpriteCache(spriteCache);
            spriteCache = new HashMap<>();
            //sprite anims
            for (Integer sprdef : DataLists.getEditorActorInfo().getActorSpriteDefs().keySet()) {
                actorSprites = DataLists.getEditorActorInfo().getActorSpriteDefs().get(sprdef);
                for (Integer actorSprite : actorSprites) {
                    rin.seek(actorSprite);
                    w = rin.readShort();
                    h = rin.readShort();
                    rin.readInt();
                    rin.readInt();
                    offs = rin.readInt() + DataLists.getCurrentLevelHeader().getSpriteTableOffs();
                    lev.seek(offs);
                    offs = lev.readInt();
                    spriteCache.put(actorSprite, readCDSprite(offs, w, h, config.getLocation()));
                }
            }
            DataLists.setActorSpriteCache(spriteCache);
            rin.close();
            lev.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void buildSpriteCache() {
        if (!DataLists.isCdOrCart())
            buildROMSpriteCache();
        else
            buildCDSpriteCache();
    }

    private ArrayList<Integer> getActorSpriteDef(int offset) {
        ArrayList<Integer> spriteDefs = new ArrayList<>();
        String loc;
        int t;
        int marker, disp;
        if (!DataLists.isCdOrCart()) {
            loc = config.getLocation();
            marker = 0x11b9d2;
            disp = 0;
        } else {
            loc = config.getShellBinLocation();
            marker = 0x02862a;
            disp = 0x8000;
        }
        try {

            if (offset > 0) {
                RandomAccessFile rin = new RandomAccessFile(loc, "rw");
                rin.seek(offset);
                t = rin.readInt() - disp;

                while (t > marker) {
                    spriteDefs.add(t);
                    t = rin.readInt() - disp;
                }
                rin.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return spriteDefs;
    }

    private HashMap<Integer, ProjectileDefinition> readProjectiles(int offset, int num) {
        HashMap<Integer, ProjectileDefinition> projectiles = new HashMap<>();
        ProjectileDefinition proj;
        String loc;
        int diff;
        int t;
        if (!DataLists.isCdOrCart()) {
            loc = config.getLocation();
            diff = 0;
        } else {
            loc = config.getShellBinLocation();
            diff = 0x8000;
        }
        try {

            RandomAccessFile rin = new RandomAccessFile(loc, "rw");
            rin.seek(offset);
            for (int i = 0; i < num; i++) {
                t = (int) rin.getFilePointer();
                proj = new ProjectileDefinition();
                proj.setUnknTwo(rin.readShort());
                proj.setUnknTwo(rin.readShort());
                proj.setDamage(rin.readShort());
                proj.setSpriteOffs(rin.readInt() - diff);
                if (!DataLists.isCdOrCart())
                    proj.setSoundId(rin.readShort());
                else
                    proj.setSoundId(rin.readInt() - 0x8000);
                proj.setDamage(rin.readShort());
                proj.setThingId(rin.readShort());
                proj.setFireDelay(rin.readShort());
                proj.setHoldFireDelay(rin.readShort());

                projectiles.put(t, proj);
            }

            rin.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return projectiles;
    }

    private EditorActorInfo readEditorActorInfo() {
        EditorActorInfo editorActorInfo = new EditorActorInfo();

        for (ActorDefinition actor : DataLists.getActorDefinitions()) {
            editorActorInfo.getActorSpriteDefs().put(actor.getIdleSpriteFrameOffs(), getActorSpriteDef(actor.getIdleSpriteFrameOffs()));
            editorActorInfo.getActorSpriteDefs().put(actor.getWalkAnimOffs(), getActorSpriteDef(actor.getWalkAnimOffs()));
            editorActorInfo.getActorSpriteDefs().put(actor.getHitAnimOffs(), getActorSpriteDef(actor.getHitAnimOffs()));
            editorActorInfo.getActorSpriteDefs().put(actor.getDieAnimOffs(), getActorSpriteDef(actor.getDieAnimOffs()));
        }

        return editorActorInfo;
    }

    private HashMap<Integer, DigitalSound> readCDSounds() {
        HashMap<Integer, DigitalSound> cdsounds = new HashMap<>();

        int soundmod, samprate, endoffs;
        try {
            RandomAccessFile rin = new RandomAccessFile(config.getShellBinLocation(), "r");

            for (int i = 0; i < DataLists.getActorDefinitions().size(); i++) {
                if (!cdsounds.containsKey(DataLists.getActorDefinitions().get(i).getAlertSoundId()) && DataLists.getActorDefinitions().get(i).getAlertSoundId() > 0) {
                    rin.seek(DataLists.getActorDefinitions().get(i).getAlertSoundId());
                    rin.readInt();
                    samprate = rin.readInt()/4;
                    rin.readShort();
                    endoffs = rin.readInt() - 0x8000;
                    cdsounds.put(DataLists.getActorDefinitions().get(i).getAlertSoundId(), new DigitalSound(readByteArray((int) rin.getFilePointer(), (int) (endoffs - rin.getFilePointer()), config.getShellBinLocation()), samprate*2));
                }
                if (!cdsounds.containsKey(DataLists.getActorDefinitions().get(i).getHitSoundId()) && DataLists.getActorDefinitions().get(i).getHitSoundId() > 0) {
                    rin.seek(DataLists.getActorDefinitions().get(i).getHitSoundId());
                    rin.readInt();
                    soundmod = rin.readInt();
                    samprate=5000/(((soundmod & 0x0000FFFF))*2/(soundmod & 0x0000FFFF));
                    rin.readShort();
                    endoffs = rin.readInt() - 0x8000;
                    cdsounds.put(DataLists.getActorDefinitions().get(i).getHitSoundId(), new DigitalSound(readByteArray((int) rin.getFilePointer(), (int) (endoffs - rin.getFilePointer()), config.getShellBinLocation()), samprate*2));
                }
                if (!cdsounds.containsKey(DataLists.getActorDefinitions().get(i).getDeathSoundId()) && DataLists.getActorDefinitions().get(i).getDeathSoundId() > 0) {
                    rin.seek(DataLists.getActorDefinitions().get(i).getDeathSoundId());
                    rin.readInt();
                    soundmod = rin.readInt();
                    samprate=5000/(((soundmod & 0x0000FFFF))*2/(soundmod & 0x0000FFFF));
                    rin.readShort();
                    endoffs = rin.readInt() - 0x8000;
                    cdsounds.put(DataLists.getActorDefinitions().get(i).getDeathSoundId(), new DigitalSound(readByteArray((int) rin.getFilePointer(), (int) (endoffs - rin.getFilePointer()), config.getShellBinLocation()), samprate));
                }
            }
            for (Integer proj : DataLists.getProjectiles().keySet()) {
                if (!cdsounds.containsKey(proj)) {
                    rin.seek(DataLists.getProjectiles().get(proj).getSoundId());
                    rin.readInt();
                    samprate = rin.readInt();
                    rin.readShort();
                    endoffs = rin.readInt() - 0x8000;
                    cdsounds.put(DataLists.getProjectiles().get(proj).getSoundId(), new DigitalSound(readByteArray((int) rin.getFilePointer(), (int) (endoffs - rin.getFilePointer()), config.getShellBinLocation()), samprate));
                }
            }
            rin.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cdsounds;
    }

    private HashMap<Integer, DigitalSound> readROMSounds() {
        HashMap<Integer, DigitalSound> romsounds = new HashMap<>();
        int soundBase, sampleRate;
        int nextHead;
        int offs;
        int length;
        byte[] soundData;
        try {
            RandomAccessFile rin = new RandomAccessFile(config.getLocation(), "r");

            rin.seek(0x6be);
            soundBase = rin.readInt();
            nextHead = soundBase;
            for (int i = 0; i < 0x19; i++) {
                rin.seek(nextHead);
                if (rin.readByte() == 0x4a)
                    sampleRate = 5517;
                else
                    sampleRate = 6500;
                offs = Short.reverseBytes(rin.readShort()) & 0x00ffff; // should be 3 bytes but none in rom are over 0xffff base offset
                rin.skipBytes(3);
                length = Short.reverseBytes(rin.readShort()) & 0x00ffff;
                rin.skipBytes(4);
                nextHead = (int) rin.getFilePointer();
                rin.seek(soundBase + offs);
                soundData = new byte[length];
                rin.read(soundData);
                romsounds.put(i, new DigitalSound(soundData, sampleRate));
            }
            rin.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return romsounds;
    }

    private HashMap<Integer, DigitalSound> readDigitalSounds() {
        if (!DataLists.isCdOrCart())
            return readROMSounds();
        else
            return readCDSounds();
    }

    private HashMap<Integer, NodeDefinition> readNodeDefinitions() {
        HashMap<Integer, NodeDefinition> nodeDefinitions = new HashMap<>();
        RandomAccessFile rin;
        try {
            if (!DataLists.isCdOrCart()) {
                rin = new RandomAccessFile(config.getLocation(), "rw");
                rin.seek(0x62fe);
            } else {
                rin = new RandomAccessFile(config.getShellBinLocation(), "r");
                rin.seek(0x27d32);
            }
            for (int i = 0; i < 4; i++)
                nodeDefinitions.put((int) rin.getFilePointer(), new NodeDefinition(rin.readShort(), rin.readShort(), rin.readShort(),
                        rin.readShort(), rin.readShort(), rin.readShort(), rin.readShort(), rin.readShort(), rin.readShort(),
                        rin.readShort(), rin.readShort(), rin.readShort(), rin.readShort()));

            rin.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return nodeDefinitions;
    }

    private HashMap<Integer, Color[]> readPalettes() {
        HashMap<Integer, Color[]> palettes = new HashMap<>();
        RandomAccessFile rin;
        Color[] palette;
        int pstart, r, g, b;
        byte blue, greenRed;
        try {
            rin = new RandomAccessFile(config.getLocation(), "r");

            for (int i = 0; i < DataLists.getLevelHeaderList().size(); i++) {
                pstart = DataLists.getLevelHeaders().get(DataLists.getLevelHeaderList().get(i)).getPaloffs();
                rin.seek(pstart);
                palette = new Color[64];


                for (int c = 0; c < palette.length; c++) {
                    blue = rin.readByte();
                    greenRed = rin.readByte();

                    r = (greenRed & 0x0000000F) * 0x10;
                    g = (greenRed & 0x000000F0);
                    b = (blue & 0x0000000F) * 0x10;

                    palette[c] = new Color(r, g, b);
                }
                palettes.put(pstart, palette);
            }
            rin.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return palettes;
    }

    private JFileChooser createBinFileFilter(String filename) {
        JFileChooser fc = new JFileChooser(config.getLocation());
        fc.addChoosableFileFilter(new FileNameExtensionFilter("BIN File", "bin"));
        fc.setDialogTitle("Select " + filename + " file");
        fc.showOpenDialog(null);
        return fc;
    }

    public void readNewFile(String filename) {
        String fileSelected;
        if (!DataLists.getMapObjectImages().isEmpty())
            DataLists.getMapObjectImages().clear();
        try {
            RandomAccessFile rin = new RandomAccessFile(filename, "rw");
            config.setLocation(filename);
            rin.seek(0x02);
            if (rin.readInt() == 0x30 && rin.length() < 0x1FFFFF) {
                System.out.println("cd");
                if (config.getShellBinLocation().equals("notset")) {
                    fileSelected = (createBinFileFilter("shell.bin").getSelectedFile().getAbsolutePath());
                    if (fileSelected.toLowerCase().endsWith("shell.bin"))
                        config.setShellBinLocation(fileSelected);
                }

                if (config.getMainOSBinLocation().equals("notset")) {
                    fileSelected = (createBinFileFilter("mainos.bin").getSelectedFile().getAbsolutePath());
                    if (fileSelected.toLowerCase().endsWith("mainos.bin"))
                        config.setMainOSBinLocation(fileSelected);
                }

                if(config.getSlidesBinLocation().equals("notset")) {
                    fileSelected = (createBinFileFilter("slides.bin").getSelectedFile().getAbsolutePath());
                    if (fileSelected.toLowerCase().endsWith("slides.bin"))
                        config.setSlidesBinLocation(fileSelected);
                }

                DataLists.setEnemyPatchedIn(false);
                DataLists.setCdOrCart(true);
                levelHeaderLocation = 0;
                DataLists.setLevelHeaderBase(0);
                DataLists.setNumLevels((short) 1);
                DataLists.setProjectiles(readProjectiles(0x32734, 21));
            } else {
                rin.seek(0x67D2);
                DataLists.setEnemyPatchedIn(rin.readInt() == 0x0EA7F00D);
                rin.seek(0xF9D0);
                levelHeaderLocation = rin.readInt();
                DataLists.setCdOrCart(false);
                DataLists.setLevelHeaderBase(levelHeaderLocation);
                DataLists.setNumLevels((short) 15);
                DataLists.setProjectiles(readProjectiles(0x10aba, 21));
            }
            rin.close();

            if (DataLists.isCdOrCart()) {
                populateCDOffsets();
            }

            DataLists.setLevelHeaders(readLevelHeaders());
            DataLists.setLevelHeaderList(new ArrayList<>(DataLists.getLevelHeaders().keySet()));
            DataLists.setLevelHeader(DataLists.getLevelHeaders().get(DataLists.getLevelHeaderList().get(config.getCurrentLevel())));
            DataLists.setLevelPalettes(readPalettes());
            DataLists.setLevelPal(DataLists.getLevelPalettes().get(DataLists.getCurrentLevelInfo().getPalOffs()));
            DataLists.setActorDefinitions(readActorDefinitions());
            DataLists.setEditorActorInfo(readEditorActorInfo());
            DataLists.setNodeDefinitions(readNodeDefinitions());
            buildSpriteCache();
            DataLists.setDigitalSounds(readDigitalSounds());
            DataLists.setupColors();
            DataLists.setThingAttributes();
            DataLists.setTileMappables();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void writeProjectileDefinition(int offset, ProjectileDefinition projectileDef) {
        RandomAccessFile rin;
        int diff;
        try {
            if (!DataLists.isCdOrCart()) {
                rin = new RandomAccessFile(config.getLocation(), "rw");
                diff = 0;
            } else {
                rin = new RandomAccessFile(config.getShellBinLocation(), "rw");
                diff = 0x8000;
            }
            rin.seek(offset);

            rin.writeShort(projectileDef.getUnknOne());
            rin.writeShort(projectileDef.getUnknTwo());
            rin.writeShort(projectileDef.getDamage());
            rin.writeInt(projectileDef.getSpriteOffs() + diff);
            if (!DataLists.isCdOrCart())
                rin.writeShort(projectileDef.getSoundId());
            else
                rin.writeInt(projectileDef.getSoundId() + diff);

            rin.writeShort(projectileDef.getSpeed());
            rin.writeShort(projectileDef.getThingId());
            rin.writeShort(projectileDef.getFireDelay());
            rin.writeShort(projectileDef.getHoldFireDelay());

            rin.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeActorDefinition(int actNum, ActorDefinition actorDef) {
        RandomAccessFile rin;
        int len, diff;
        try {
            if (!DataLists.isCdOrCart()) {

                rin = new RandomAccessFile(config.getLocation(), "rw");
                len = 0x3a;
                diff = 0;
            } else {
                rin = new RandomAccessFile(config.getShellBinLocation(), "rw");
                len = 0x40;
                diff = 0x8000;
            }
            rin.seek(DataLists.getActorDefOffset() + ((long) len * actNum));

            rin.writeShort(actorDef.getShotDelay());
            rin.writeShort(actorDef.getAccuracy());
            rin.writeInt(actorDef.getProjectile() + diff);
            rin.writeShort(actorDef.getMoveSpeed());
            rin.writeShort(actorDef.getMoveDelay());
            rin.writeShort(actorDef.getActionDelay());
            rin.writeShort(actorDef.getUnknDelay());
            rin.writeShort(actorDef.getProjectileYOrg());
            rin.writeShort(actorDef.getHitPoints());
            rin.writeShort(actorDef.getHitStunMoveTime());
            rin.writeShort(actorDef.getHitStunFireDelayTime());
            rin.writeShort(actorDef.getHitDelayTime());
            if (!DataLists.isCdOrCart())
                rin.writeShort(actorDef.getAlertSoundId());
            else
                rin.writeInt(actorDef.getAlertSoundId() + diff);
            if (!DataLists.isCdOrCart())
                rin.writeShort(actorDef.getHitSoundId());
            else
                rin.writeInt(actorDef.getDeathSoundId() + diff);
            if (!DataLists.isCdOrCart())
                rin.writeShort(actorDef.getDeathSoundId());
            else
                rin.writeInt(actorDef.getDeathSoundId() + diff);
            rin.writeShort(actorDef.getDamageHitRadius());
            rin.writeShort(actorDef.getWallRadius());
            rin.writeShort(actorDef.getKnockbackAngle());
            rin.writeShort(actorDef.getKnockbackDistance());
            rin.writeShort(actorDef.getPlayerDistanceKept());
            rin.writeInt(actorDef.getIdleSpriteFrameOffs() + diff);
            rin.writeInt(actorDef.getWalkAnimOffs() + diff);
            rin.writeInt(actorDef.getHitAnimOffs() + diff);
            rin.writeInt(actorDef.getDieAnimOffs() + diff);

            rin.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<ActorDefinition> readActorDefinitions() {
        ArrayList<ActorDefinition> actorDefinitions = new ArrayList<>();
        int t;
        try {
            RandomAccessFile rin;
            if (!DataLists.isCdOrCart()) {
                rin = new RandomAccessFile(config.getLocation(), "rw");
                rin.seek(0x6198);
                t = rin.readInt();
                if (t < 0x1fffff) {
                    rin.seek(t);
                    DataLists.setActorDefOffset(t);
                    for (int i = 0; i < 19; i++) {
                        actorDefinitions.add(new ActorDefinition(rin.readShort(), rin.readShort(), rin.readInt(), rin.readShort(),
                                rin.readShort(), rin.readShort(), rin.readShort(), rin.readShort(), rin.readShort(), rin.readShort(),
                                rin.readShort(), rin.readShort(), rin.readShort(), rin.readShort(), rin.readShort(), rin.readShort(),
                                rin.readShort(), rin.readShort(), rin.readShort(), rin.readShort(), rin.readInt(), rin.readInt(),
                                rin.readInt(), rin.readInt()));


                    }
                }
            } else {
                rin = new RandomAccessFile(config.getShellBinLocation(), "r");
                rin.seek(0x27d9a);
                DataLists.setActorDefOffset(0x27d9a);
                for (int i = 0; i < 19; i++) {
                    actorDefinitions.add(new ActorDefinition(rin.readShort(), rin.readShort(), rin.readInt() - 0x8000, rin.readShort(),
                            rin.readShort(), rin.readShort(), rin.readShort(), rin.readShort(), rin.readShort(), rin.readShort(),
                            rin.readShort(), rin.readShort(), rin.readInt() - 0x8000, rin.readInt() - 0x8000, rin.readInt() - 0x8000, rin.readShort(),
                            rin.readShort(), rin.readShort(), rin.readShort(), rin.readShort(), rin.readInt() - 0x8000, rin.readInt() - 0x8000,
                            rin.readInt() - 0x8000, rin.readInt() - 0x8000));
                }
            }
            rin.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return actorDefinitions;
    }

    public static Image readFirstThings(int id) {
        BufferedImage im;
        int length = 0;
        int data = 0;
        int x = 0, y = 0;
        ThingPack patch1 = new ThingPack();

        im = new BufferedImage(0x59, 0x2c, BufferedImage.TYPE_INT_RGB);
        if (id == 1 || id == 0) {
            for (int i = 0; i < patch1.getThingSix().length; i += 2) {
                length = patch1.getThingSix()[i];
                data = patch1.getThingSix()[i + 1] & 0x000000F;
                for (int j = 0; j < length; j++) {
                    im.setRGB(x++, y, DataLists.getLevelPal()[data + 32].getRGB());
                    if (x > im.getWidth() - 1) {
                        x = 0;
                        y++;
                    }
                }
            }
            BufferedImage img = im;
            AffineTransform af = AffineTransform.getScaleInstance(-1, 1);

            af.translate(0, 0);
            af.rotate(-1, 15);
            AffineTransformOp op = new AffineTransformOp(af, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            img = op.filter(img, null);
            return img;
        }


        return im;
    }

    public List<WallStructure> readWalls(String filename) throws IOException {
        ArrayList<WallStructure> walls = new ArrayList<WallStructure>();
        RandomAccessFile ind = new RandomAccessFile(filename, "rw");

        int current = 0;

        WallStructure wall;
        ind.seek(DataLists.getCurrentLevelInfo().getLineOffs());
        while (current < DataLists.getCurrentLevelInfo().getNumLines()) {
            wall = new WallStructure();
            wall.setTextureID(ind.readInt());
            wall.setTextureScale(ind.readShort());
            wall.setY1(ind.readShort());
            wall.setX1(ind.readShort());
            wall.setY2(ind.readShort());
            wall.setX2(ind.readShort());
            wall.setDoorType(ind.readByte());
            wall.setDoorNumber(ind.readByte());
            wall.setDoorNSLow(ind.readByte());
            wall.setDoorNSHigh(ind.readByte());
            wall.setDoorWELow(ind.readByte());
            wall.setDoorWEHigh(ind.readByte());

            walls.add(wall);
            current++;

        }
        ind.close();
        return walls;
    }

    public void readWallGFX(boolean palRefresh, String filename) {
        if (palRefresh)
            DataLists.getWallImages().clear();
        for (int i = 0; i < DataLists.getWalls().size(); i++) {
            if (!DataLists.getWallImages().containsKey(DataLists.getWalls().get(i).getTextureID())) {
                if (!DataLists.isCdOrCart())
                    DataLists.getWallImages().put(DataLists.getWalls().get(i).getTextureID() & 0x00FFFFFF,
                            readCartWallGraphics(DataLists.getWalls().get(i).getTextureID() & 0x00FFFFFF,
                                    DataLists.getWalls().get(i).getTextureScale(), filename));
                else
                    DataLists.getWallImages().put(DataLists.getWalls().get(i).getTextureID() & 0x00FFFFFF,
                            readCDWallGraphics(DataLists.getWalls().get(i).getTextureID() & 0x00FFFFFF,
                                    DataLists.getWalls().get(i).getTextureID(),
                                    DataLists.getWalls().get(i).getTextureScale(), filename));
            }
        }
    }

    public void fixChecksum() {

        try {
            RandomAccessFile rf = new RandomAccessFile(new File(this.config.getLocation()), "rw");
            rf.seek(0x18E);
            long actualSum = 0;
            rf.seek(0x200);
            long fileSize = rf.length();

            for (int i = 0; i < fileSize - 0x200; i += 2) {

                actualSum += rf.readShort();
            }
            rf.seek(0x18E);
            rf.writeShort((short) (65535 & actualSum));
            rf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeLevelToFile(List<WallStructure> walls, List<MapObjectStructure> objs) {
        try {
            RandomAccessFile fi = new RandomAccessFile(new File(config.getLocation()), "rw");
            fi.seek(DataLists.getCurrentLevelInfo().getLineOffs());
            for (WallStructure wall : walls) {
                fi.writeInt(wall.getTextureID());
                fi.writeShort(wall.getTextureScale());
                fi.writeShort(wall.getY1());
                fi.writeShort(wall.getX1());
                fi.writeShort(wall.getY2());
                fi.writeShort(wall.getX2());
                fi.writeByte(wall.getDoorType());
                fi.writeByte(wall.getDoorNumber());
                fi.writeByte(wall.getDoorNSLow());
                fi.writeByte(wall.getDoorNSHigh());
                fi.writeByte(wall.getDoorWELow());
                fi.writeByte(wall.getDoorWEHigh());
            }
            for (MapObjectStructure obj : objs) {
                fi.writeInt(obj.getThingID());
                fi.writeByte(obj.getOffset_major());
                fi.writeByte(obj.getOffset_minor());
                fi.writeShort(obj.getY());
                fi.writeShort(obj.getX());
            }
            fi.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeConfig() {
        File conf = new File("./config");

        FileWriter out;
        try {
            out = new FileWriter(conf);
            out.write("location=" + config.getLocation() + "\n");
            out.write("shellbinlocation=" + config.getShellBinLocation() + "\n");
            out.write("mainosbinlocation=" + config.getMainOSBinLocation() + "\n");
            out.write("slidesbinlocation=" + config.getSlidesBinLocation() + "\n");
            out.write("lastlevel=" + config.getCurrentLevel() + "\n");
            for (int i = 0; i < config.getMapSize(); i++) {
                out.write("level" + i + "Grid=" + config.getLevelGrid(i) + "\n");
                out.write("level" + i + "Zoom=" + config.getLevelZoom(i) + "\n");
                out.write("level" + i + "Rotation=" + config.getLevelRot(i) + "\n");
                out.write("level" + i + "Mode=" + config.getLevelMode(i) + "\n");
            }
            out.write("cdOrCart=" + (DataLists.isCdOrCart() ? "1" : "0") + "\n");
            out.write("gridKey=" + Window.getKeyControls().getGridKey() + "\n");
            out.write("linesKey=" + Window.getKeyControls().getLinesKey() + "\n");
            out.write("mouseObjectMoveButton=" + Window.getKeyControls().getMouseObjectMove() + "\n");
            out.write("mousePanButton=" + Window.getKeyControls().getMousePan() + "\n");
            out.write("mouseSelectButton=" + Window.getKeyControls().getMouseSelect() + "\n");
            out.write("moveObjectKey=" + Window.getKeyControls().getMoveObjectKey() + "\n");
            out.write("panKey=" + Window.getKeyControls().getPanKey() + "\n");
            out.write("rotateKey=" + Window.getKeyControls().getRotateKey() + "\n");
            out.write("saveKey=" + Window.getKeyControls().getSaveKey() + "\n");
            out.write("selectKey=" + Window.getKeyControls().getSelectKey() + "\n");
            out.write("snapKey=" + Window.getKeyControls().getSnapKey() + "\n");
            out.write("thingsKey=" + Window.getKeyControls().getThingsKey() + "\n");
            out.write("vertsKey=" + Window.getKeyControls().getVertsKey() + "\n");
            out.write("zoomInKey=" + Window.getKeyControls().getZoomInKey() + "\n");
            out.write("zoomOutKey=" + Window.getKeyControls().getZoomOutKey() + "\n");
            out.write("enemyPatchedIn=" + (DataLists.isEnemyPatchedIn() ? "1" : "0") + "\n");
            out.write("noCD=" + (config.isNoCD() ? "1" : "0") + "\n");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fixCorruptedROMTextures() {
        try {
            RandomAccessFile fo = new RandomAccessFile(new File(config.getLocation()), "rw");
            fo.seek(0x0d7822);
            // fix 0d7822 first
            TexturePatch0D7822 patch1 = new TexturePatch0D7822();
            byte length = 0;
            byte data = 0;
            for (int i = 0; i < patch1.getData().length; i += 2) {
                length = (byte) patch1.getData()[i];
                data = (byte) patch1.getData()[i + 1];
                for (int j = 0; j < length; j++) {
                    fo.writeByte(data);
                }
            }
            patch1 = null;
            TexturePatch0DB822 patch2 = new TexturePatch0DB822();
            for (int i = 0; i < patch2.getData().length; i += 2) {
                length = (byte) patch2.getData()[i];
                data = (byte) patch2.getData()[i + 1];
                for (int j = 0; j < length; j++) {
                    fo.writeByte(data);
                }
            }

            DataLists.setChangesMade(true);
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkForFixedTextures() {
        boolean fixed = false;
        long sum = 0;
        try {
            RandomAccessFile fi = new RandomAccessFile(new File(config.getLocation()), "rw");
            fi.seek(0x0d7822);
            for (int i = 0; i < (0x0df822 - 0x0d7822) / 8; i++) {
                sum += fi.readLong();
            }
            System.out.println("total sum of fixed rom: " + Long.toHexString(sum));
            if (sum == 0x19cede3184fc6e2fL)
                fixed = true;
            fi.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fixed;
    }

    public boolean readConfig() {
        File conf = new File("./config");
        String[] parts;
        try {
            Scanner fins = new Scanner(conf);

            while (fins.hasNextLine()) {
                parts = fins.nextLine().split("=");

                switch (parts[0]) {
                    case "location": {
                        config.setLocation(parts[1]);
                        break;
                    }
                    case "shellbinlocation": {
                        config.setShellBinLocation(parts[1]);
                        break;
                    }
                    case "mainosbinlocation": {
                        config.setMainOSBinLocation(parts[1]);
                        break;
                    }
                    case "slidesbinlocation": {
                        config.setSlidesBinLocation(parts[1]);
                        break;
                    }
                    case "lastlevel": {
                        config.setCurrentLevel(Integer.parseInt(parts[1]));
                        break;
                    }
                    case "cdOrCart": {
                        DataLists.setCdOrCart(Boolean.parseBoolean(parts[1]));
                        break;
                    }
                    case "gridKey": {
                        Window.getKeyControls().setGridKey(Integer.parseInt(parts[1]));
                        break;
                    }
                    case "linesKey": {
                        Window.getKeyControls().setLinesKey(Integer.parseInt(parts[1]));
                        break;
                    }
                    case "mouseObjectMoveButton": {
                        Window.getKeyControls().setMouseObjectMove(Integer.parseInt(parts[1]));
                        break;
                    }
                    case "mousePanButton": {
                        Window.getKeyControls().setMousePan(Integer.parseInt(parts[1]));
                        break;
                    }
                    case "mouseSelectButton": {
                        Window.getKeyControls().setMouseSelect(Integer.parseInt(parts[1]));
                        break;
                    }
                    case "moveObjectKey": {
                        Window.getKeyControls().setMoveObjectKey(Integer.parseInt(parts[1]));
                        break;
                    }
                    case "panKey": {
                        Window.getKeyControls().setPanKey(Integer.parseInt(parts[1]));
                        break;
                    }
                    case "rotateKey": {
                        Window.getKeyControls().setRotateKey(Integer.parseInt(parts[1]));
                        break;
                    }
                    case "saveKey": {
                        Window.getKeyControls().setSaveKey(Integer.parseInt(parts[1]));
                        break;
                    }
                    case "selectKey": {
                        Window.getKeyControls().setSelectKey(Integer.parseInt(parts[1]));
                        break;
                    }
                    case "snapKey": {
                        Window.getKeyControls().setSnapKey(Integer.parseInt(parts[1]));
                        break;
                    }
                    case "thingsKey": {
                        Window.getKeyControls().setThingsKey(Integer.parseInt(parts[1]));
                        break;
                    }
                    case "vertsKey": {
                        Window.getKeyControls().setVertsKey(Integer.parseInt(parts[1]));
                        break;
                    }
                    case "zoomInKey": {
                        Window.getKeyControls().setZoomInKey(Integer.parseInt(parts[1]));
                        break;
                    }
                    case "zoomOutKey": {
                        Window.getKeyControls().setZoomOutKey(Integer.parseInt(parts[1]));
                        break;
                    }
                    case "enemyPatchedIn": {
                        DataLists.setEnemyPatchedIn(Byte.parseByte(parts[1]) != 0);
                        break;
                    }
                    case "noCD": {
                        config.setNoCD(Byte.parseByte(parts[1]) != 0);
                        break;
                    }

                }

                if (parts[0].startsWith("level"))
                    for (int i = 0; i < config.getMapSize(); i++) {
                        config.setLevelGrid(Integer.parseInt(parts[1]), i);
                        config.setLevelZoom(Integer.parseInt(fins.nextLine().split("=")[1]), i);
                        config.setLevelRot(Boolean.parseBoolean(fins.nextLine().split("=")[1]), i);
                        config.setLevelMode(Integer.parseInt(fins.nextLine().split("=")[1]), i);
                        parts = fins.nextLine().split("=");
                    }

            }
            configLoaded = true;
            fins.close();
        } catch (FileNotFoundException e) {
            configLoaded = false;
        }

        return configLoaded;
    }

    public Color[] readPalette(int offset, int len, String loc) {
        Color[] out = new Color[len];
        try {
            RandomAccessFile rin = new RandomAccessFile(loc, "r");
            rin.seek(offset);
            byte blue = 0;
            byte greenRed = 0;

            for (int i = 0; i < len; i++) {
                blue = rin.readByte();
                greenRed = rin.readByte();

                int r = (greenRed & 0x0000000F) * 0x10;
                int g = (greenRed & 0x000000F0);
                int b = (blue & 0x0000000F) * 0x10;

                out[i] = new Color(r, g, b);
            }
            rin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out;
    }

    public void readPalette() {
        try {
            RandomAccessFile rin = new RandomAccessFile(config.getLocation(), "rw");
            rin.seek(DataLists.getCurrentLevelInfo().getPalOffs());
            byte blue = 0;
            byte greenRed = 0;

            for (int i = 0; i < 64; i++) {
                blue = rin.readByte();
                greenRed = rin.readByte();

                int r = (greenRed & 0x0000000F) * 0x10;
                int g = (greenRed & 0x000000F0);
                int b = (blue & 0x0000000F) * 0x10;

                DataLists.getLevelPal()[i] = new Color(r, g, b);
            }

            rin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<Integer, Short> getTextureOffsets(int headerOffsets, int numLevels) {
        HashMap<Integer, Short> offsets = new HashMap<>();
        int next = headerOffsets;
        short numLines = 0;
        int lineOffs = 0;
        try (RandomAccessFile rin = new RandomAccessFile(config.getLocation(), "rw")) {
            rin.seek(headerOffsets);
            for (int i = 0; i < numLevels; i++) {
                rin.seek(next);
                next += 0x2c;
                numLines = rin.readShort();
                lineOffs = rin.readInt();
                rin.seek(lineOffs);
                for (int l = 0; l < numLines; l++) {
                    offsets.put(rin.readInt() & 0x00ffffff, rin.readShort());
                    rin.skipBytes(0xe);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return offsets;
    }

    public byte[] decompressRNCData(int rncoffset, String location) {
        byte[] in;
        RNC2Header rncheader;
        try {
            RandomAccessFile rin = new RandomAccessFile(location, "rw");
            rin.seek(rncoffset);
            rncheader = new RNC2Header();
            rncheader.setHeader(rin.readInt());
            rncheader.setUnpackedlen(rin.readInt());
            rncheader.setPackedlen(rin.readInt());
            rncheader.setUncompcrc(rin.readShort());
            rncheader.setCompcrc(rin.readShort());
            rncheader.setLeeway(rin.readByte());
            rncheader.setChunks(rin.readByte());

            in = new byte[rncheader.getPackedlen()];
            for (int i = 0; i < in.length; i++) {
                in[i] = rin.readByte();
            }
            rin.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return JaRNC.decompress(in, rncheader.getUnpackedlen() + (rncheader.getLeeway() * 2));
    }

    public void writeTileMap(int offset, int len, short[] tilemap) {
        try (RandomAccessFile raf = new RandomAccessFile(DataLists.isCdOrCart() ? config.getSlidesBinLocation() : config.getLocation(), "rw")) {
            raf.seek(offset);
            for (int i = 0; i < len; i++) {
                raf.writeShort(tilemap[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeROMTexture(int offset, Byte[][] imgData) {
        try {
            RandomAccessFile rin = new RandomAccessFile(config.getLocation(), "rw");
            rin.seek(offset);
            for (int i = 0; i < imgData.length; i++) {
                for (int j = 0; j < imgData[0].length; j++) {
                    rin.writeByte(imgData[i][j]);
                }
                for (int j = 0; j < imgData[0].length; j++) {
                    rin.writeByte((imgData[i][j] >> 4) & 0x0f);
                }
            }
            rin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeCDTexture(int offset, Byte[][] imgData) {
        byte pix;
        try {
            RandomAccessFile rin = new RandomAccessFile(config.getLocation(), "rw");
            rin.seek(offset);
            for (int i = 0; i < imgData.length; i += 2) {
                for (int j = 0; j < imgData[0].length; j++) {
                    pix = (byte) ((imgData[i][j] << 4 & 0xf0) | (imgData[i + 1][j] & 0x0f));
                    rin.writeByte(pix);
                }
            }
            rin.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Byte[][] readROMTexture(int offset, int width) {
        Byte[][] outbuff;
        try {
            RandomAccessFile rin = new RandomAccessFile(config.getLocation(), "rw");
            rin.seek(offset);
            outbuff = new Byte[width][64];
            for (int i = 0; i < outbuff.length; i++) {
                for (int j = 0; j < 64; j++) {
                    outbuff[i][j] = rin.readByte();
                }
                rin.skipBytes(64);
            }
            rin.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return outbuff;
    }

    public Byte[][] readCDTexture(int offset, int width) {
        Byte[][] outbuff = new Byte[width][64];
        byte pix;
        try {
            RandomAccessFile rin = new RandomAccessFile(config.getLocation(), "rw");
            rin.seek(offset);
            for (int i = 0; i < outbuff.length; i += 2) {
                for (int j = 0; j < outbuff[0].length; j++) {
                    pix = rin.readByte();
                    outbuff[i][j] = (byte) ((pix >> 4) & 0x0f);
                    outbuff[i + 1][j] = (byte) (pix & 0x0f);
                }
            }
            rin.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return outbuff;
    }

    public void writeNodeDefinition(int currentNode, NodeDefinition currentNodeDef) {
        RandomAccessFile rin;
        try {
            if (!DataLists.isCdOrCart()) {
                rin = new RandomAccessFile(config.getLocation(), "rw");
            } else {
                rin = new RandomAccessFile(config.getShellBinLocation(), "rw");
            }
            rin.seek(currentNode);
            rin.writeShort(currentNodeDef.getSentryStartOrbitSpeed());
            rin.writeShort(currentNodeDef.getSentryRefireDelay());
            rin.writeShort(currentNodeDef.getSentrySpeedIncrement());
            rin.writeShort(currentNodeDef.getSentryMaxSpeed());
            rin.writeShort(currentNodeDef.getSentryNodeFarDistance());
            rin.writeShort(currentNodeDef.getSentryNodeCloseDistance());
            rin.writeShort(currentNodeDef.getSentrySpeed());
            rin.writeShort(currentNodeDef.getSentryCount());
            rin.writeShort(currentNodeDef.getUnknOne());
            rin.writeShort(currentNodeDef.getUnknTwo());
            rin.writeShort(currentNodeDef.getUnknThree());
            rin.writeShort(currentNodeDef.getUnknFour());
            rin.writeShort(currentNodeDef.getNodeHealthRefireAdvance());

            rin.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private HashMap<Integer, LevelHeader> readLevelHeaders() {
        HashMap<Integer, LevelHeader> levelHeaders = new HashMap<>();
        LevelHeader header;
        RandomAccessFile rin;
        int offs = 0, numlevels = 0, hstart;

        try {
            rin = new RandomAccessFile(config.getLocation(), "r");
            if (!DataLists.isCdOrCart()) {
                rin.seek(0xF9D0);
                offs = rin.readInt();
                numlevels = 15;
            } else {
                levelHeaderLocation = 0;
                numlevels = 1;
            }
            rin.seek(offs);
            for (int i = 0; i < numlevels; i++) {
                header = new LevelHeader();
                hstart = (int) rin.getFilePointer();
                header.setNumLines(rin.readShort());
                header.setLineoffs(rin.readInt());
                header.setNumThings(rin.readShort());
                header.setThingoffs(rin.readInt());
                header.setNumLights(rin.readShort());
                header.setNumDoors(rin.readShort());
                header.setPaloffs(rin.readInt());
                header.setNodeTimer(rin.readShort());
                header.setNodeoffs(rin.readInt());
                header.setMinimapXScale(rin.readShort());
                header.setMinimapYScale(rin.readShort());
                header.setMinimapXOffset(rin.readShort());
                header.setMinimapWidth(rin.readShort());
                header.setMinimapHeight(rin.readShort());
                header.setMinimapoffs(rin.readInt());
                header.setGridoffs(rin.readInt());
                if (DataLists.isCdOrCart())
                    header.setSpriteTableOffs(rin.readInt());
                levelHeaders.put(hstart, header);
            }
            rin.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return levelHeaders;
    }


    public void writeLevelHeader(int offset, LevelHeader currentLevelHeaderDef) {
        RandomAccessFile rin;
        try {
            if (!DataLists.isCdOrCart()) {
                rin = new RandomAccessFile(config.getLocation(), "rw");
            } else {
                rin = new RandomAccessFile(config.getShellBinLocation(), "r");
            }
            rin.seek(offset);
            rin.writeShort(currentLevelHeaderDef.getNumLines());
            rin.writeInt(currentLevelHeaderDef.getLineoffs());
            rin.writeShort(currentLevelHeaderDef.getNumThings());
            rin.writeInt(currentLevelHeaderDef.getThingoffs());
            rin.writeShort(currentLevelHeaderDef.getNumLights());
            rin.writeShort(currentLevelHeaderDef.getNumDoors());
            rin.writeInt(currentLevelHeaderDef.getPaloffs());
            rin.writeShort(currentLevelHeaderDef.getNodeTimer());
            rin.writeShort(currentLevelHeaderDef.getMinimapXScale());
            rin.writeShort(currentLevelHeaderDef.getMinimapYScale());
            rin.writeShort(currentLevelHeaderDef.getMinimapXOffset());
            rin.writeShort(currentLevelHeaderDef.getMinimapWidth());
            rin.writeShort(currentLevelHeaderDef.getMinimapHeight());
            rin.writeInt(currentLevelHeaderDef.getMinimapoffs());
            rin.writeInt(currentLevelHeaderDef.getGridoffs());
            if (DataLists.isCdOrCart())
                rin.writeInt(currentLevelHeaderDef.getSpriteTableOffs());

            rin.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void extractROMData(String targetFolder) {
        DataExtraction extractor;
        if(!DataLists.isCdOrCart()) {
            extractor = new DataExtraction(this, "rompack.json", targetFolder);
            extractor.extract();
        }
    }
}
