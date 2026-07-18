package aneurysm.ui;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import aneurysm.io.FileReader;
import aneurysm.offsets.CDOffsets;
import aneurysm.structures.*;

public class DataLists {

    private static SpriteSet[] spriteList = new SpriteSet[37];
    private static EditorLevelInfo levelInfo;
    private static LevelHeader levelHeader;
    private static int levelHeaderBase;
    private static int currentLevelHeaderIndex;
    private static int actorDefOffset;
    private static int currentLevelHeaderOffset;
    private static short numLevels;
    private static Image[] sprites;
    private static Map<Integer, Image> wallImages = new HashMap<>();
    private static Map<Integer, Image> mapObjectImages = new HashMap<>();
    private static Map<Integer, Short> textureInfoCache = new HashMap<>();
    private static HashMap<Integer, DigitalSound> digitalSounds = new HashMap<>();
    private static final HashMap<Integer, String> thingAttributes = new HashMap<>();
    private static HashMap<Integer, ProjectileDefinition> projectiles = new HashMap<>();
    private static HashMap<Integer, Byte[][]> actorSpriteCache = new HashMap<>();
    private static HashMap<Integer, Byte[][]> projectileSpriteCache = new HashMap<>();
    private static HashMap<Integer, NodeDefinition> nodeDefinitions = new HashMap<>();
    private static HashMap<Integer, LevelHeader> levelHeaders = new HashMap<>();
    private static HashMap<Integer, Color[]> levelPalettes = new HashMap<>();
    private static ArrayList<TileMappable> tileMappables = new ArrayList<>();
    private static ArrayList<ActorDefinition> actorDefinitions = new ArrayList<>();
    private static ArrayList<Integer> levelHeaderList = new ArrayList<>();
    private static List<MapObjectStructure> objects = new ArrayList<>();
    private static List<WallStructure> walls = new ArrayList<>();
    private static List<Vertex> vertices = new ArrayList<>();
    private static List<Line> lines = new ArrayList<>();
    private static EditorActorInfo editorActorInfo = new EditorActorInfo();
    private static Color[] levelPal;
    private static boolean cdOrCart = false; // false Cart, true Cd
    private static boolean enemyPatchedIn = false;

    public static int getActorDefOffset() {
        return actorDefOffset;
    }

    public static void setActorDefOffset(int offset) {
        actorDefOffset = offset;
    }

    public static EditorLevelInfo getCurrentLevelInfo() {
        return levelInfo;
    }

    public static void setTextureInfoCache(HashMap<Integer, Short> cache) {
        if(!DataLists.textureInfoCache.isEmpty())
            DataLists.textureInfoCache.clear();
        textureInfoCache = cache;
    }

    public static Map<Integer, Short> getTextureInfoCache() {
        return textureInfoCache;
    }

    public static int getLevelHeaderBase() {
        return levelHeaderBase;
    }

    public static void setLevelHeaderBase(int levelHeaderBase) {
        DataLists.levelHeaderBase = levelHeaderBase;
    }

    public static short getNumLevels() {
        return numLevels;
    }

    public static void setNumLevels(short numLevels) {
        DataLists.numLevels = numLevels;
    }

    public static void setCurrentLevelInfo(EditorLevelInfo info) {
        levelInfo = info;
    }

    public static LevelHeader getCurrentLevelHeader() {
        return levelHeader;
    }

    public static void setLevelHeader(LevelHeader levelHeader) {
        DataLists.levelHeader = levelHeader;
        levelInfo = new EditorLevelInfo(levelHeader.getNumLines(), levelHeader.getLineoffs(), levelHeader.getNumThings(), levelHeader.getThingoffs(), levelHeader.getPaloffs(), DataLists.getCurrentLevelHeaderOffset());

    }

    public static boolean isEnemyPatchedIn() {
        return enemyPatchedIn;
    }

    public static void setEnemyPatchedIn(boolean enemyPatchedIn) {
        DataLists.enemyPatchedIn = enemyPatchedIn;
    }

    public static HashMap<Integer, String> getThingAttributes() {
        return thingAttributes;
    }

    public static ArrayList<TileMappable> getTileMappables() {
        return tileMappables;
    }

    public static void setTileMappables() {
        if (!tileMappables.isEmpty()) {
            tileMappables.clear();
        }
        if (!cdOrCart) {
            //TODO: get rid of this hardcoded crap.
            tileMappables.add(new TileMappable(0x139e6, 0x102bd2, 2624, 0x103612, 896, 'h', "1P HUD"));
            tileMappables.add(new TileMappable(0x139e6, 0x103d12, 1696, 0x1043b2, 896, 'h', "2P HUD"));
            tileMappables.add(new TileMappable(0x37504, 0x3695a, 0, 0x36e04, 896, 'i', "Acclaim Logo"));
            tileMappables.add(new TileMappable(0x3829a, 0x37532, 0, 0x37b9a, 896, 'i', "Sega(TM) Logo"));
            tileMappables.add(new TileMappable(0x39024, 0x382c8, 0, 0x38924, 896, 'i', "Sega Logo"));
            tileMappables.add(new TileMappable(0x39c6e, 0x39052, 0, 0x3956e, 896, 'i', "Domark Logo"));
            tileMappables.add(new TileMappable(0x3de34, 0x39c9c, 0, 0x3d734, 896, 'i', "Title Screen Back"));
            tileMappables.add(new TileMappable(0x3efac, 0x3de62, 0, 0x3e8ac, 896, 'i', "Bloodshot Copyright"));
            tileMappables.add(new TileMappable(0x40194, 0x3efda, 0, 0x3fa94, 896, 'i', "Battle Frenzy Copyright"));
            tileMappables.add(new TileMappable(0x43138, 0x401c2, 0, 0x42a38, 896, 'i', "Ship Screen"));
            tileMappables.add(new TileMappable(0x47eb2, 0x43166, 0, 0x477b2, 896, 'i', "Space Marines"));
            tileMappables.add(new TileMappable(0x4c40c, 0x47ee0, 0, 0x4bd0c, 896, 'i', "Brain Surgery"));
            tileMappables.add(new TileMappable(0x4faf2, 0x4c43a, 0, 0x4f3f2, 896, 'i', "Lethal Alien Robots"));
            tileMappables.add(new TileMappable(0x51d02, 0x4fb20, 0, 0x51602, 896, 'i', "Moon Base Yaz"));
            tileMappables.add(new TileMappable(0x54cd8, 0x51d30, 0, 0x545d8, 896, 'i', "Invading Fleet"));
            tileMappables.add(new TileMappable(0x58e46, 0x54d06, 0, 0x58746, 896, 'i', "Plasma Node"));
            tileMappables.add(new TileMappable(0x5b34e, 0x58e74, 0, 0x5ac4e, 896, 'i', "Space Towing"));
        } else {
            //0x96726
//            tileMappables.add(new TileMappable(0x139e6, 0x102bd2, 2624, 0x103612, 896, 'h', "1P HUD"));
//            tileMappables.add(new TileMappable(0x139e6, 0x103d12, 1696, 0x1043b2, 896, 'h', "2P HUD"));
            tileMappables.add(new TileMappable(0x700, 0x0, 0, 0x586, 896, 'i', "3b598"));
            tileMappables.add(new TileMappable(0x1a0e, 0xca6, 0, 0x130e, 896, 'i', "3b5a6"));
            tileMappables.add(new TileMappable(0x278a, 0x1a2e, 0, 0x208a, 896, 'i', "3b5b4"));
            tileMappables.add(new TileMappable(0x3354, 0x27aa, 0, 0x2c54, 896, 'i', "3b5c2"));
            tileMappables.add(new TileMappable(0x3f90, 0x3374, 0, 0x3890, 896, 'i', "3b5d0"));
            tileMappables.add(new TileMappable(0x8148, 0x3fb0, 0, 0x7a48, 896, 'i', "3b5de"));
            tileMappables.add(new TileMappable(0x92b2, 0x8168, 0, 0x8bb2, 896, 'i', "3b5ec"));
            tileMappables.add(new TileMappable(0xa48c, 0x92d2, 0, 0x9d8c, 896, 'i', "3b5fa"));
            tileMappables.add(new TileMappable(0xd422, 0xa4ac, 0, 0xcd22, 896, 'i', "3b608"));
            tileMappables.add(new TileMappable(0x1218e, 0xd442, 0, 0x11a8e, 896, 'i', "3b616"));
            tileMappables.add(new TileMappable(0x166da, 0x121ae, 0, 0x15fda, 896, 'i', "3b624"));
            tileMappables.add(new TileMappable(0x19db2, 0x166fa, 0, 0x196b2, 896, 'i', "3b632"));
            tileMappables.add(new TileMappable(0x1bfb4, 0x19dd2, 0, 0x1b8b4, 896, 'i', "3b640"));
            tileMappables.add(new TileMappable(0x1ef7c, 0x1bfd4, 0, 0x1e87c, 896, 'i', "3b64e"));
            tileMappables.add(new TileMappable(0x230dc, 0x1ef9c, 0, 0x229dc, 896, 'i', "3b65c"));
            tileMappables.add(new TileMappable(0x255d6, 0x230fc, 0, 0x24ed6, 896, 'i', "3b66a"));
        }
    }

    public static void setThingAttributes() {
        if (!thingAttributes.isEmpty())
            thingAttributes.clear();
        if (!cdOrCart) {
            if (enemyPatchedIn) {
                thingAttributes.put(0x11b9d2, "Thing ID: 11B9D2<br/>Desc: P1 Start<br/>Start Health: 128");
                thingAttributes.put(0x11b9d4, "Thing ID: 11B9D4<br/>Desc: P2 Start<br/>Start Health: 128");
                thingAttributes.put(0x11cbc4, "Thing ID: 11CBC4<br/>Desc: Small Light");
                thingAttributes.put(0x11cbca, "Thing ID: 11CBCA<br/>Desc: Big Light");
                thingAttributes.put(0x11d5ca, "Thing ID: 11D5CA<br/>Desc: Oxygen Tank");
                thingAttributes.put(0x11d5d0, "Thing ID: 11CD50<br/>Desc: Ammo Pickup");
                thingAttributes.put(0x11d5d6, "Thing ID: 11D5D6<br/>Desc: 1-Up");
                thingAttributes.put(0x11e348, "Thing ID: 11E348<br/>Desc: Yellow Key");
                thingAttributes.put(0x11e34e, "Thing ID: 11E34E<br/>Desc: Red Key");
                thingAttributes.put(0x11e354, "Thing ID: 11E354<br/>Desc: White Key");
                thingAttributes.put(0x11ef72, "Thing ID: 11EF72<br/>Desc: Ricochet Pickup");
                thingAttributes.put(0x11f7be, "Thing ID: 11F4BE<br/>Desc: Lock-On Pickup");
                thingAttributes.put(0x11feca, "Thing ID: 11FECA<br/>Desc: Cannon Pickup");
                thingAttributes.put(0x120602, "Thing ID: 120602<br/>Desc: Rapid Pickup");
                thingAttributes.put(0x120eae, "Thing ID: 120EAE<br/>Desc: Tribolt Pickup");
                thingAttributes.put(0x1216a0, "Thing ID: 1216A0<br/>Desc: Piercer Pickup");
                thingAttributes.put(0x121cc2, "Thing ID: 121CC2<br/>Desc: Grenade Pickup");
                thingAttributes.put(0x1228a4, "Thing ID: 1228A4<br/>Desc: Breaker Pickup");
                thingAttributes.put(0x122E5A, "Thing ID: 122E5A<br/>Desc: Kinghell Pickup");
                thingAttributes.put(0x1243de, "Thing ID: 1243DE<br/>Desc: Spray Pickup");
                thingAttributes.put(0x124df2,
                        "Thing ID: 124DF2<br/>Desc: Sentry<br/>Start Health: 70<br/>Weapon: Cannon");
                thingAttributes.put(0x124df8,
                        "Thing ID: 124DF8<br/>Desc: Sentry<br/>Start Health: 30<br/>Weapon: Cannon");
                thingAttributes.put(0x124dfe,
                        "Thing ID: 124DFE<br/>Desc: Sentry<br/>Start Health: 30<br/>Weapon: Base");
                thingAttributes.put(0x124e04, "Thing ID: 124E04<br/>Desc: Sentry<br/>Start Health: 1<br/>Weapon: Base");
                thingAttributes.put(0x133b0c,
                        "Thing ID: 133B0C<br/>Desc: Plasma Node<br/>Start Health: 210<br/>Weapon: Sentries");
                thingAttributes.put(0x146196, "Thing ID: 146196<br/>Desc: Beast<br/>Start Health: 55<br/>Weapon: Orb");
                thingAttributes.put(0x14619c, "Thing ID: 14619C<br/>Desc: Beast<br/>Start Health: 50<br/>Weapon: Orb");
                thingAttributes.put(0x157de2,
                        "Thing ID: 157DE2<br/>Desc: Bborg<br/>Start Health: 100<br/>Weapon: Fireball");
                thingAttributes.put(0x165de0,
                        "Thing ID: 165DE0<br/>Desc: Tatbot<br/>Start Health: 115<br/>Weapon: Cannon");
                thingAttributes.put(0x165de6,
                        "Thing ID: 165DE6<br/>Desc: Tatbot<br/>Start Health: 115<br/>Weapon: Grenade");
                thingAttributes.put(0x1847f4,
                        "Thing ID: 1847F4<br/>Desc: Stroller<br/>Start Health: 60<br/>Weapon: Cannon");
                thingAttributes.put(0x191786,
                        "Thing ID: 191786<br/>Desc: Wirehead<br/>Start Health: 140<br/>Weapon: Fireball");
                thingAttributes.put(0x1c98de, "Thing ID: 1C98DE<br/>Desc: Short Barrel");
                thingAttributes.put(0x1c98e4, "Thing ID: 1C98E4<br/>Desc: Tall Barrel");
                thingAttributes.put(0x1cbc56, "Thing ID: 1CBC56<br/>Desc: Mine");
                thingAttributes.put(0x1d2bbe,
                        "Thing ID: 1D2BBE<br/>Desc: Plasma Man<br/>Start Health: 70<br/>Weapon: Fireball");
                thingAttributes.put(0x200000,
                        "Thing ID: 200000<br/>Desc: CD Enemy<br/>Start Health: 100<br/>Weapon: Fireball (8hp)");
            } else {
                thingAttributes.put(0x11b9d2, "Thing ID: 11B9D2<br/>Desc: P1 Start<br/>Start Health: 128");
                thingAttributes.put(0x11b9d4, "Thing ID: 11B9D4<br/>Desc: P2 Start<br/>Start Health: 128");
                thingAttributes.put(0x11cbc4, "Thing ID: 11CBC4<br/>Desc: Small Light");
                thingAttributes.put(0x11cbca, "Thing ID: 11CBCA<br/>Desc: Big Light");
                thingAttributes.put(0x11d5ca, "Thing ID: 11D5CA<br/>Desc: Oxygen Tank");
                thingAttributes.put(0x11d5d0, "Thing ID: 11CD50<br/>Desc: Ammo Pickup");
                thingAttributes.put(0x11d5d6, "Thing ID: 11D5D6<br/>Desc: 1-Up");
                thingAttributes.put(0x11e348, "Thing ID: 11E348<br/>Desc: Yellow Key");
                thingAttributes.put(0x11e34e, "Thing ID: 11E34E<br/>Desc: Red Key");
                thingAttributes.put(0x11e354, "Thing ID: 11E354<br/>Desc: White Key");
                thingAttributes.put(0x11ef72, "Thing ID: 11EF72<br/>Desc: Ricochet Pickup");
                thingAttributes.put(0x11f7be, "Thing ID: 11F4BE<br/>Desc: Lock-On Pickup");
                thingAttributes.put(0x11feca, "Thing ID: 11FECA<br/>Desc: Cannon Pickup");
                thingAttributes.put(0x120602, "Thing ID: 120602<br/>Desc: Rapid Pickup");
                thingAttributes.put(0x120eae, "Thing ID: 120EAE<br/>Desc: Tribolt Pickup");
                thingAttributes.put(0x1216a0, "Thing ID: 1216A0<br/>Desc: Piercer Pickup");
                thingAttributes.put(0x121cc2, "Thing ID: 121CC2<br/>Desc: Grenade Pickup");
                thingAttributes.put(0x1228a4, "Thing ID: 1228A4<br/>Desc: Breaker Pickup");
                thingAttributes.put(0x122E5A, "Thing ID: 122E5A<br/>Desc: Kinghell Pickup");
                thingAttributes.put(0x1243de, "Thing ID: 1243DE<br/>Desc: Spray Pickup");
                thingAttributes.put(0x124df2,
                        "Thing ID: 124DF2<br/>Desc: Sentry<br/>Start Health: 70<br/>Weapon: Cannon");
                thingAttributes.put(0x124df8,
                        "Thing ID: 124DF8<br/>Desc: Sentry<br/>Start Health: 30<br/>Weapon: Cannon");
                thingAttributes.put(0x124dfe,
                        "Thing ID: 124DFE<br/>Desc: Sentry<br/>Start Health: 30<br/>Weapon: Base");
                thingAttributes.put(0x124e04, "Thing ID: 124E04<br/>Desc: Sentry<br/>Start Health: 1<br/>Weapon: Base");
                thingAttributes.put(0x133b0c,
                        "Thing ID: 133B0C<br/>Desc: Plasma Node<br/>Start Health: 210<br/>Weapon: Sentries");
                thingAttributes.put(0x146196, "Thing ID: 146196<br/>Desc: Beast<br/>Start Health: 55<br/>Weapon: Orb");
                thingAttributes.put(0x14619c, "Thing ID: 14619C<br/>Desc: Beast<br/>Start Health: 50<br/>Weapon: Orb");
                thingAttributes.put(0x157de2,
                        "Thing ID: 157DE2<br/>Desc: Bborg<br/>Start Health: 100<br/>Weapon: Fireball");
                thingAttributes.put(0x165de0,
                        "Thing ID: 165DE0<br/>Desc: Tatbot<br/>Start Health: 115<br/>Weapon: Cannon");
                thingAttributes.put(0x165de6,
                        "Thing ID: 165DE6<br/>Desc: Tatbot<br/>Start Health: 115<br/>Weapon: Grenade");
                thingAttributes.put(0x1847f4,
                        "Thing ID: 1847F4<br/>Desc: Stroller<br/>Start Health: 60<br/>Weapon: Cannon");
                thingAttributes.put(0x191786,
                        "Thing ID: 191786<br/>Desc: Wirehead<br/>Start Health: 140<br/>Weapon: Fireball");
                thingAttributes.put(0x1c98de, "Thing ID: 1C98DE<br/>Desc: Short Barrel");
                thingAttributes.put(0x1c98e4, "Thing ID: 1C98E4<br/>Desc: Tall Barrel");
                thingAttributes.put(0x1cbc56, "Thing ID: 1CBC56<br/>Desc: Mine");
                thingAttributes.put(0x1d2bbe,
                        "Thing ID: 1D2BBE<br/>Desc: Plasma Man<br/>Start Health: 70<br/>Weapon: Fireball");
            }
        } else {
            thingAttributes.put(0x00, "Thing ID: 0<br/>Desc: P1 Start<br/>Start Health: 128");
            thingAttributes.put(0x06, "Thing ID: 06<br/>Desc: P2 Start<br/>Start Health: 128");
            thingAttributes.put(0x0C, "Thing ID: 0C<br/>Desc: Small Light");
            thingAttributes.put(0x12, "Thing ID: 12<br/>Desc: Big Light");
            thingAttributes.put(0x24, "Thing ID: 24<br/>Desc: Oxygen Tank");
            thingAttributes.put(0x2a, "Thing ID: 2A<br/>Desc: Ammo Pickup");
            thingAttributes.put(0x30, "Thing ID: 30<br/>Desc: 1-Up");
            thingAttributes.put(0x36, "Thing ID: 36<br/>Desc: Yellow Key");
            thingAttributes.put(0x3C, "Thing ID: 3C<br/>Desc: Red Key");
            thingAttributes.put(0x42, "Thing ID: 42<br/>Desc: White Key");
            thingAttributes.put(0x48, "Thing ID: 48<br/>Desc: Ricochet Pickup");
            thingAttributes.put(0x4e, "Thing ID: 4E<br/>Desc: Lock-On Pickup");
            thingAttributes.put(0x54, "Thing ID: 54<br/>Desc: Cannon Pickup");
            thingAttributes.put(0x5a, "Thing ID: 5A<br/>Desc: Rapid Pickup");
            thingAttributes.put(0x60, "Thing ID: 60<br/>Desc: Tribolt Pickup");
            thingAttributes.put(0x66, "Thing ID: 66<br/>Desc: Piercer Pickup");
            thingAttributes.put(0x6c, "Thing ID: 6C<br/>Desc: Grenade Pickup");
            thingAttributes.put(0x72, "Thing ID: 72<br/>Desc: Breaker Pickup");
            thingAttributes.put(0x78, "Thing ID: 78<br/>Desc: Kinghell Pickup");
            thingAttributes.put(0x7e, "Thing ID: 7E<br/>Desc: Spray Pickup");
            thingAttributes.put(0x84, "Thing ID: 84<br/>Desc: Sentry<br/>Start Health: 70<br/>Weapon: Cannon");
            thingAttributes.put(0x8a, "Thing ID: 8A<br/>Desc: Sentry<br/>Start Health: 30<br/>Weapon: Cannon");
            thingAttributes.put(0x90, "Thing ID: 90<br/>Desc: Sentry<br/>Start Health: 30<br/>Weapon: Base");
            thingAttributes.put(0x96, "Thing ID: 96<br/>Desc: Sentry<br/>Start Health: 1<br/>Weapon: Base");
            thingAttributes.put(0x9c, "Thing ID: 9C<br/>Desc: Plasma Node<br/>Start Health: 210<br/>Weapon: Sentries");
            thingAttributes.put(0xa2, "Thing ID: A2<br/>Desc: Beast<br/>Start Health: 55<br/>Weapon: Orb");
            thingAttributes.put(0xa8, "Thing ID: A8<br/>Desc: Beast<br/>Start Health: 50<br/>Weapon: Orb");
            thingAttributes.put(0xae, "Thing ID: AE<br/>Desc: Bborg<br/>Start Health: 100<br/>Weapon: Fireball");
            thingAttributes.put(0xb4, "Thing ID: B4<br/>Desc: Tatbot<br/>Start Health: 115<br/>Weapon: Cannon");
            thingAttributes.put(0xba, "Thing ID: BA<br/>Desc: Tatbot<br/>Start Health: 115<br/>Weapon: Grenade");
            thingAttributes.put(0xc0, "Thing ID: C0<br/>Desc: Stroller<br/>Start Health: 60<br/>Weapon: Cannon");
            thingAttributes.put(0xc6, "Thing ID: C6<br/>Desc: CD Enemy<br/>Start Health: 100<br/>Weapon: Fireball");
            thingAttributes.put(0xcc, "Thing ID: CC<br/>Desc: Wirehead<br/>Start Health: 140<br/>Weapon: Fireball");
            thingAttributes.put(0xd2, "Thing ID: D2<br/>Desc: Short Barrel");
            thingAttributes.put(0xd8, "Thing ID: D8<br/>Desc: Tall Barrel");
            thingAttributes.put(0xe4, "Thing ID: E4<br/>Desc: Mine");
            thingAttributes.put(0xf0, "Thing ID: F0<br/>Desc: Plasma Man<br/>Start Health: 70<br/>Weapon: Fireball");
        }
    }

    public static boolean isCdOrCart() {
        return cdOrCart;
    }

    public static void setCdOrCart(boolean cdOrCart) {
        DataLists.cdOrCart = cdOrCart;
    }

    public static Map<Integer, Image> getWallImages() {
        return wallImages;
    }

    public static Map<Integer, Image> getMapObjectImages() {
        return mapObjectImages;
    }

    public static void setMapObjectImages(Map<Integer, Image> mapObjectImages) {
        DataLists.mapObjectImages = mapObjectImages;
    }

    public static SpriteSet[] getSpriteList() {
        return spriteList;
    }

    public static void setWallImages(Map<Integer, Image> wallImages) {
        DataLists.wallImages = wallImages;
    }

    private static boolean changesMade = false;

    public static boolean isChangesMade() {
        return changesMade;
    }

    public static void setChangesMade(boolean changesMade) {
        if (!cdOrCart)
            DataLists.changesMade = changesMade;
    }

    public static Color[] getLevelPal() {
        return levelPal;
    }

    public static Image getObjectImage(int id) {
        Image image = null;

        if (!cdOrCart) {
            int spriteIndex = getSpriteIndexForId(id);
            if (spriteIndex >= 0 && spriteIndex < getSprites().length) {
                image = getSprites()[spriteIndex];
            }
        } else {
            // CD/Cart mode uses different mapping - keep existing logic for now
            image = mapCdOrCartIdToSprite(id);
        }

        return image;
    }

    private static int getSpriteIndexForId(int id) {
        if (!enemyPatchedIn) {
            return switch (id) {
                case 0x11b9d2 -> 0;
                case 0x11b9d4 -> 1;
                case 0x11cbc4 -> 2;
                case 0x11cbca -> 3;
                case 0x11d5ca -> 4;
                case 0x11d5d0 -> 5;
                case 0x11d5d6 -> 6;
                case 0x11e348 -> 7;
                case 0x11e34e -> 8;
                case 0x11e354 -> 9;
                case 0x11ef72 -> 10;
                case 0x11f7be -> 11;
                case 0x11feca -> 12;
                case 0x120602 -> 13;
                case 0x120eae -> 14;
                case 0x1216a0 -> 15;
                case 0x121cc2 -> 16;
                case 0x1228a4 -> 17;
                case 0x122e5a -> 18;
                case 0x1243de -> 19;
                case 0x124df2 -> 20;
                case 0x124df8 -> 21;
                case 0x124dfe -> 22;
                case 0x124e04 -> 23;
                case 0x133b0c -> 24;
                case 0x146196 -> 25;
                case 0x14619c -> 26;
                case 0x157de2 -> 27;
                case 0x165de0 -> 28;
                case 0x165de6 -> 29;
                case 0x1847f4 -> 30;
                case 0x191786 -> 31;
                case 0x1c98de -> 32;
                case 0x1c98e4 -> 33;
                case 0x1cbc56 -> 34;
                case 0x1d2bbe -> 35;
                default -> -1;
            };
        } else {
            return switch (id) {
                case 0x11b9d2 -> 0;
                case 0x11b9d4 -> 1;
                case 0x11cbc4 -> 2;
                case 0x11cbca -> 3;
                case 0x11d5ca -> 4;
                case 0x11d5d0 -> 5;
                case 0x11d5d6 -> 6;
                case 0x11e348 -> 7;
                case 0x11e34e -> 8;
                case 0x11e354 -> 9;
                case 0x11ef72 -> 10;
                case 0x11f7be -> 11;
                case 0x11feca -> 12;
                case 0x120602 -> 13;
                case 0x120eae -> 14;
                case 0x1216a0 -> 15;
                case 0x121cc2 -> 16;
                case 0x1228a4 -> 17;
                case 0x122e5a -> 18;
                case 0x1243de -> 19;
                case 0x124df2 -> 20;
                case 0x124df8 -> 21;
                case 0x124dfe -> 22;
                case 0x124e04 -> 23;
                case 0x133b0c -> 24;
                case 0x146196 -> 25;
                case 0x14619c -> 26;
                case 0x157de2 -> 27;
                case 0x165de0 -> 28;
                case 0x165de6 -> 29;
                case 0x1847f4 -> 30;
                case 0x191786 -> 31;
                case 0x1c98de -> 32;
                case 0x1c98e4 -> 33;
                case 0x1cbc56 -> 34;
                case 0x1d2bbe -> 35;
                case 0x200000 -> 36;
                default -> -1;
            };
        }
    }

    private static Image mapCdOrCartIdToSprite(int id) {
        return switch (id) {
            case 0 -> getSprites()[0];
            case 6 -> getSprites()[1];
            case 0x0c -> getSprites()[2];
            case 0x12 -> getSprites()[3];
            case 0x24 -> getSprites()[4];
            case 0x2a -> getSprites()[5];
            case 0x30 -> getSprites()[6];
            case 0x36 -> getSprites()[7];
            case 0x3c -> getSprites()[8];
            case 0x42 -> getSprites()[9];
            case 0x48 -> getSprites()[10];
            case 0x4e -> getSprites()[11];
            case 0x54 -> getSprites()[12];
            case 0x5a -> getSprites()[13];
            case 0x60 -> getSprites()[14];
            case 0x66 -> getSprites()[15];
            case 0x6c -> getSprites()[16];
            case 0x72 -> getSprites()[17];
            case 0x78 -> getSprites()[18];
            case 0x7e -> getSprites()[19];
            case 0x84 -> getSprites()[23];
            case 0x8a -> getSprites()[20];
            case 0x90 -> getSprites()[22];
            case 0x96 -> getSprites()[21];
            case 0x9c -> getSprites()[24];
            case 0xa2 -> getSprites()[25];
            case 0xa8 -> getSprites()[26];
            case 0xae -> getSprites()[27];
            case 0xb4 -> getSprites()[29];
            case 0xba -> getSprites()[29];
            case 0xc0 -> getSprites()[30];
            case 0xc6 -> getSprites()[28];
            case 0xcc -> getSprites()[31];
            case 0xd2 -> getSprites()[32];
            case 0xd8 -> getSprites()[33];
            case 0xe4 -> getSprites()[34];
            case 0xf0 -> getSprites()[35];
            default -> null;
        };
    }

    public static void setLevelPal(Color[] pal) {
        DataLists.levelPal = pal;
    }


    public static void setupColors() {
        readSprites();
        setThingAttributes();
        setTileMappables();
    }

    private static void readSprites() {

        sprites = new Image[spriteList.length];
        if (!mapObjectImages.isEmpty())
            mapObjectImages.clear();
        if (!isCdOrCart()) {
            if (!enemyPatchedIn) {
                spriteList = new SpriteSet[36];
                spriteList[0] = new SpriteSet(0x0011B9D2, 0x1df302, 0x28, 0x59);
                spriteList[1] = new SpriteSet(0x0011B9D4, 0x1e104e, 0x22, 0x57);
                spriteList[2] = new SpriteSet(0x0011CBC4, 0x11cbdc, 0x1e, 0x08);
                spriteList[3] = new SpriteSet(0x0011CBCA, 0x11ccd8, 0x2b, 0x0f);
                spriteList[4] = new SpriteSet(0x0011D5CA, 0x11d5dc, 0x19, 0x2b);
                spriteList[5] = new SpriteSet(0x0011D5D0, 0x11da1c, 0x1c, 0x2c);
                spriteList[6] = new SpriteSet(0x0011D5D6, 0x11def8, 0x1c, 0x27);
                spriteList[7] = new SpriteSet(0x0011E348, 0x11e35a, 0x1e, 0x22);
                spriteList[8] = new SpriteSet(0x0011E34E, 0x11e762, 0x1e, 0x22);
                spriteList[9] = new SpriteSet(0x0011E354, 0x11eb6a, 0x1e, 0x22);
                spriteList[10] = new SpriteSet(0x0011EF72, 0x11ef84, 0x36, 0x27);
                spriteList[11] = new SpriteSet(0x0011F7BE, 0x11f7d0, 0x2f, 0x26);
                spriteList[12] = new SpriteSet(0x0011FECA, 0x11fedc, 0x3b, 0x1f);
                spriteList[13] = new SpriteSet(0x00120602, 0x120614, 0x47, 0x1f);
                spriteList[14] = new SpriteSet(0x00120EAE, 0x120ec0, 0x38, 0x24);
                spriteList[15] = new SpriteSet(0x001216A0, 0x1216b2, 0x2f, 0x21);
                spriteList[16] = new SpriteSet(0x00121CC2, 0x121cd4, 0x48, 0x2a);
                spriteList[17] = new SpriteSet(0x001228A4, 0x1228b6, 0x27, 0x25);
                spriteList[18] = new SpriteSet(0x00122E5A, 0x122e6c, 0x5a, 0x3d);
                spriteList[19] = new SpriteSet(0x001243DE, 0x1243f0, 0x3a, 0x23);
                spriteList[20] = new SpriteSet(0x00124DF2, 0x124e0a, 0x24, 0x58);
                spriteList[21] = new SpriteSet(0x00124DF8, 0x124e0a, 0x24, 0x58);
                spriteList[22] = new SpriteSet(0x00124DFE, 0x124e0a, 0x24, 0x58);
                spriteList[23] = new SpriteSet(0x00124E04, 0x124e0a, 0x24, 0x58);
                spriteList[24] = new SpriteSet(0x00133B0C, 0x133b12, 0x51, 0x80);
                spriteList[25] = new SpriteSet(0x00146196, 0x1461a2, 0x25, 0x64);
                spriteList[26] = new SpriteSet(0x0014619C, 0x1461a2, 0x25, 0x64);
                spriteList[27] = new SpriteSet(0x00157DE2, 0x157de8, 0x59, 0x5f);
                spriteList[28] = new SpriteSet(0x00165DE0, 0x176556, 0x59, 0x6b);
                spriteList[29] = new SpriteSet(0x00165DE6, 0x165dec, 0x59, 0x64);
                spriteList[30] = new SpriteSet(0x001847F4, 0x1847fa, 0x22, 0x5a);
                spriteList[31] = new SpriteSet(0x00191786, 0x19178c, 0x78, 0x97);
                spriteList[32] = new SpriteSet(0x001C98DE, 0x1c98f0, 0x31, 0x3a);
                spriteList[33] = new SpriteSet(0x001C98E4, 0x1ca416, 0x31, 0x46);
                spriteList[34] = new SpriteSet(0x001CBC56, 0x1cbc6e, 0x17, 0x1b);
                spriteList[35] = new SpriteSet(0x001D2BBE, 0x1d2bc4, 0x25, 0x57);

            } else {
                spriteList = new SpriteSet[37];
                spriteList[0] = new SpriteSet(0x0011B9D2, 0x1df302, 0x28, 0x59);
                spriteList[1] = new SpriteSet(0x0011B9D4, 0x1e104e, 0x22, 0x57);
                spriteList[2] = new SpriteSet(0x0011CBC4, 0x11cbdc, 0x1e, 0x08);
                spriteList[3] = new SpriteSet(0x0011CBCA, 0x11ccd8, 0x2b, 0x0f);
                spriteList[4] = new SpriteSet(0x0011D5CA, 0x11d5dc, 0x19, 0x2b);
                spriteList[5] = new SpriteSet(0x0011D5D0, 0x11da1c, 0x1c, 0x2c);
                spriteList[6] = new SpriteSet(0x0011D5D6, 0x11def8, 0x1c, 0x27);
                spriteList[7] = new SpriteSet(0x0011E348, 0x11e35a, 0x1e, 0x22);
                spriteList[8] = new SpriteSet(0x0011E34E, 0x11e762, 0x1e, 0x22);
                spriteList[9] = new SpriteSet(0x0011E354, 0x11eb6a, 0x1e, 0x22);
                spriteList[10] = new SpriteSet(0x0011EF72, 0x11ef84, 0x36, 0x27);
                spriteList[11] = new SpriteSet(0x0011F7BE, 0x11f7d0, 0x2f, 0x26);
                spriteList[12] = new SpriteSet(0x0011FECA, 0x11fedc, 0x3b, 0x1f);
                spriteList[13] = new SpriteSet(0x00120602, 0x120614, 0x47, 0x1f);
                spriteList[14] = new SpriteSet(0x00120EAE, 0x120ec0, 0x38, 0x24);
                spriteList[15] = new SpriteSet(0x001216A0, 0x1216b2, 0x2f, 0x21);
                spriteList[16] = new SpriteSet(0x00121CC2, 0x121cd4, 0x48, 0x2a);
                spriteList[17] = new SpriteSet(0x001228A4, 0x1228b6, 0x27, 0x25);
                spriteList[18] = new SpriteSet(0x00122E5A, 0x122e6c, 0x5a, 0x3d);
                spriteList[19] = new SpriteSet(0x001243DE, 0x1243f0, 0x3a, 0x23);
                spriteList[20] = new SpriteSet(0x00124DF2, 0x124e0a, 0x24, 0x58);
                spriteList[21] = new SpriteSet(0x00124DF8, 0x124e0a, 0x24, 0x58);
                spriteList[22] = new SpriteSet(0x00124DFE, 0x124e0a, 0x24, 0x58);
                spriteList[23] = new SpriteSet(0x00124E04, 0x124e0a, 0x24, 0x58);
                spriteList[24] = new SpriteSet(0x00133B0C, 0x133b12, 0x51, 0x80);
                spriteList[25] = new SpriteSet(0x00146196, 0x1461a2, 0x25, 0x64);
                spriteList[26] = new SpriteSet(0x0014619C, 0x1461a2, 0x25, 0x64);
                spriteList[27] = new SpriteSet(0x00157DE2, 0x157de8, 0x59, 0x5f);
                spriteList[28] = new SpriteSet(0x00165DE0, 0x176556, 0x59, 0x6b);
                spriteList[29] = new SpriteSet(0x00165DE6, 0x165dec, 0x59, 0x64);
                spriteList[30] = new SpriteSet(0x001847F4, 0x1847fa, 0x22, 0x5a);
                spriteList[31] = new SpriteSet(0x00191786, 0x19178c, 0x78, 0x97);
                spriteList[32] = new SpriteSet(0x001C98DE, 0x1c98f0, 0x31, 0x3a);
                spriteList[33] = new SpriteSet(0x001C98E4, 0x1ca416, 0x31, 0x46);
                spriteList[34] = new SpriteSet(0x001CBC56, 0x1cbc6e, 0x17, 0x1b);
                spriteList[35] = new SpriteSet(0x001D2BBE, 0x1d2bc4, 0x25, 0x57);
                spriteList[36] = new SpriteSet(0x00200000, 0x200006, 0x70, 0x7c);
            }
        } else {
            spriteList = new SpriteSet[36];
            spriteList[0] = new SpriteSet(0x00, -1, 0x28, 0x58);
            spriteList[1] = new SpriteSet(0x06, -2, CDOffsets.getSpriteDimensions().get(0).getW(),
                    CDOffsets.getSpriteDimensions().get(0).getH());

            spriteList[2] = new SpriteSet(0x0c, CDOffsets.getSpriteOffsets()[0],
                    CDOffsets.getSpriteDimensions().get(0).getW(), CDOffsets.getSpriteDimensions().get(0).getH());
            spriteList[3] = new SpriteSet(0x12, CDOffsets.getSpriteOffsets()[1],
                    CDOffsets.getSpriteDimensions().get(1).getW(), CDOffsets.getSpriteDimensions().get(1).getH());
            spriteList[4] = new SpriteSet(0x24, CDOffsets.getSpriteOffsets()[4],
                    CDOffsets.getSpriteDimensions().get(4).getW(), CDOffsets.getSpriteDimensions().get(4).getH());
            spriteList[5] = new SpriteSet(0x2a, CDOffsets.getSpriteOffsets()[5],
                    CDOffsets.getSpriteDimensions().get(5).getW(), CDOffsets.getSpriteDimensions().get(5).getH());
            spriteList[6] = new SpriteSet(0x30, CDOffsets.getSpriteOffsets()[6],
                    CDOffsets.getSpriteDimensions().get(6).getW(), CDOffsets.getSpriteDimensions().get(6).getH());
            spriteList[7] = new SpriteSet(0x36, CDOffsets.getSpriteOffsets()[7],
                    CDOffsets.getSpriteDimensions().get(7).getW(), CDOffsets.getSpriteDimensions().get(7).getH());
            spriteList[8] = new SpriteSet(0x3c, CDOffsets.getSpriteOffsets()[8],
                    CDOffsets.getSpriteDimensions().get(8).getW(), CDOffsets.getSpriteDimensions().get(8).getH());
            spriteList[9] = new SpriteSet(0x42, CDOffsets.getSpriteOffsets()[9],
                    CDOffsets.getSpriteDimensions().get(9).getW(), CDOffsets.getSpriteDimensions().get(9).getH());
            spriteList[10] = new SpriteSet(0x48, CDOffsets.getSpriteOffsets()[10],
                    CDOffsets.getSpriteDimensions().get(10).getW(), CDOffsets.getSpriteDimensions().get(10).getH());
            spriteList[11] = new SpriteSet(0x4e, CDOffsets.getSpriteOffsets()[11],
                    CDOffsets.getSpriteDimensions().get(11).getW(), CDOffsets.getSpriteDimensions().get(11).getH());
            spriteList[12] = new SpriteSet(0x54, CDOffsets.getSpriteOffsets()[12],
                    CDOffsets.getSpriteDimensions().get(12).getW(), CDOffsets.getSpriteDimensions().get(12).getH());
            spriteList[13] = new SpriteSet(0x5a, CDOffsets.getSpriteOffsets()[13],
                    CDOffsets.getSpriteDimensions().get(13).getW(), CDOffsets.getSpriteDimensions().get(13).getH());
            spriteList[14] = new SpriteSet(0x60, CDOffsets.getSpriteOffsets()[14],
                    CDOffsets.getSpriteDimensions().get(14).getW(), CDOffsets.getSpriteDimensions().get(14).getH());
            spriteList[15] = new SpriteSet(0x66, CDOffsets.getSpriteOffsets()[15],
                    CDOffsets.getSpriteDimensions().get(15).getW(), CDOffsets.getSpriteDimensions().get(15).getH());
            spriteList[16] = new SpriteSet(0x6c, CDOffsets.getSpriteOffsets()[16],
                    CDOffsets.getSpriteDimensions().get(16).getW(), CDOffsets.getSpriteDimensions().get(16).getH());
            spriteList[17] = new SpriteSet(0x72, CDOffsets.getSpriteOffsets()[17],
                    CDOffsets.getSpriteDimensions().get(17).getW(), CDOffsets.getSpriteDimensions().get(17).getH());
            spriteList[18] = new SpriteSet(0x78, CDOffsets.getSpriteOffsets()[18],
                    CDOffsets.getSpriteDimensions().get(18).getW(), CDOffsets.getSpriteDimensions().get(18).getH());
            spriteList[19] = new SpriteSet(0x7e, CDOffsets.getSpriteOffsets()[19],
                    CDOffsets.getSpriteDimensions().get(19).getW(), CDOffsets.getSpriteDimensions().get(19).getH());
            spriteList[20] = new SpriteSet(0x8a, CDOffsets.getSpriteOffsets()[23],
                    CDOffsets.getSpriteDimensions().get(23).getW(), CDOffsets.getSpriteDimensions().get(23).getH());
            spriteList[21] = new SpriteSet(0x96, CDOffsets.getSpriteOffsets()[23],
                    CDOffsets.getSpriteDimensions().get(23).getW(), CDOffsets.getSpriteDimensions().get(23).getH());
            spriteList[22] = new SpriteSet(0x90, CDOffsets.getSpriteOffsets()[23],
                    CDOffsets.getSpriteDimensions().get(23).getW(), CDOffsets.getSpriteDimensions().get(23).getH());
            spriteList[23] = new SpriteSet(0x84, CDOffsets.getSpriteOffsets()[23],
                    CDOffsets.getSpriteDimensions().get(23).getW(), CDOffsets.getSpriteDimensions().get(23).getH());
            spriteList[24] = new SpriteSet(0x9c, CDOffsets.getSpriteOffsets()[42],
                    CDOffsets.getSpriteDimensions().get(42).getW(), CDOffsets.getSpriteDimensions().get(42).getH());
            spriteList[25] = new SpriteSet(0xa2, CDOffsets.getSpriteOffsets()[56],
                    CDOffsets.getSpriteDimensions().get(56).getW(), CDOffsets.getSpriteDimensions().get(56).getH());
            spriteList[26] = new SpriteSet(0xa8, CDOffsets.getSpriteOffsets()[56],
                    CDOffsets.getSpriteDimensions().get(56).getW(), CDOffsets.getSpriteDimensions().get(56).getH());
            spriteList[27] = new SpriteSet(0xae, CDOffsets.getSpriteOffsets()[75],
                    CDOffsets.getSpriteDimensions().get(75).getW(), CDOffsets.getSpriteDimensions().get(75).getH());
            spriteList[28] = new SpriteSet(0xc6, CDOffsets.getSpriteOffsets()[114],
                    CDOffsets.getSpriteDimensions().get(114).getW(), CDOffsets.getSpriteDimensions().get(114).getH());
            spriteList[29] = new SpriteSet(0xb4, CDOffsets.getSpriteOffsets()[82],
                    CDOffsets.getSpriteDimensions().get(82).getW(), CDOffsets.getSpriteDimensions().get(82).getH());
            spriteList[30] = new SpriteSet(0xc0, CDOffsets.getSpriteOffsets()[95],
                    CDOffsets.getSpriteDimensions().get(95).getW(), CDOffsets.getSpriteDimensions().get(95).getH());
            spriteList[31] = new SpriteSet(0xcc, CDOffsets.getSpriteOffsets()[124],
                    CDOffsets.getSpriteDimensions().get(124).getW(), CDOffsets.getSpriteDimensions().get(124).getH());
            spriteList[32] = new SpriteSet(0xd2, CDOffsets.getSpriteOffsets()[20],
                    CDOffsets.getSpriteDimensions().get(20).getW(), CDOffsets.getSpriteDimensions().get(20).getH());
            spriteList[33] = new SpriteSet(0xd8, CDOffsets.getSpriteOffsets()[21],
                    CDOffsets.getSpriteDimensions().get(21).getW(), CDOffsets.getSpriteDimensions().get(21).getH());
            spriteList[34] = new SpriteSet(0xe4, CDOffsets.getSpriteOffsets()[160],
                    CDOffsets.getSpriteDimensions().get(160).getW(), CDOffsets.getSpriteDimensions().get(160).getH());
            spriteList[35] = new SpriteSet(0xf0, CDOffsets.getSpriteOffsets()[144],
                    CDOffsets.getSpriteDimensions().get(144).getW(), CDOffsets.getSpriteDimensions().get(144).getH());
        }

        try {
            for (int i = 0; i < spriteList.length; i++) {

                if (!isCdOrCart())
                    sprites[i] = Window.getReader().readImage(spriteList[i].getOffset(), spriteList[i].getWidth(),
                            spriteList[i].getHeight());
                else {
                    switch (i) {
                        case 0:
                            sprites[i] = FileReader.readFirstThings(0);
                            break;
                        case 1:
                            sprites[i] = FileReader.readFirstThings(1);
                            break;
                        default:
                            sprites[i] = Window.getReader().readCDImage(spriteList[i].getOffset(), spriteList[i].getWidth(),
                                    spriteList[i].getHeight());
                            break;
                    }
                }

                if (spriteList[i].getOffset() == -1) {
                    mapObjectImages.put(i, FileReader.readFirstThings(0));
                    continue;
                }
                if (spriteList[i].getOffset() == -2) {
                    mapObjectImages.put(i, FileReader.readFirstThings(1));
                    continue;
                }
                if (spriteList[i].getOffset() != 0) {
                    mapObjectImages.put(i, sprites[i]);
                }

            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("why are you like this aneurysm-chan :(");
        }

    }

    public static Image[] getSprites() {
        return sprites;
    }

    public static List<MapObjectStructure> getObjects() {
        return objects;
    }

    public static void setObjects(List<MapObjectStructure> objects) {
        if (!DataLists.objects.isEmpty())
            DataLists.objects.clear();
        DataLists.objects = objects;
    }

    public static List<WallStructure> getWalls() {
        return walls;
    }

    public static void setWalls(List<WallStructure> walls) {
        if (!DataLists.walls.isEmpty())
            DataLists.walls.clear();
        DataLists.walls = walls;
    }

    public static List<Vertex> getVertices() {
        return vertices;
    }

    public static void setVertices(List<Vertex> vertices) {
        DataLists.vertices = vertices;
    }

    public static List<Line> getLines() {
        return lines;
    }

    public static void setLines(List<Line> lines) {
        DataLists.lines = lines;
    }

    public static ArrayList<ActorDefinition> getActorDefinitions() {
        return actorDefinitions;
    }

    public static void setActorDefinitions(ArrayList<ActorDefinition> actorDefinitions) {
        if(!DataLists.actorDefinitions.isEmpty())
            DataLists.actorDefinitions.clear();
        DataLists.actorDefinitions = actorDefinitions;
    }

    public static EditorActorInfo getEditorActorInfo() {
        return editorActorInfo;
    }

    public static void setEditorActorInfo(EditorActorInfo editorActorInfo) {
        DataLists.editorActorInfo = editorActorInfo;
    }

    public static HashMap<Integer, ProjectileDefinition> getProjectiles() {
        return projectiles;
    }

    public static void setProjectiles(HashMap<Integer, ProjectileDefinition> projectiles) {
        if (!DataLists.projectiles.isEmpty())
            DataLists.projectiles.clear();
        DataLists.projectiles = projectiles;
    }

    public static void setProjectileSpriteCache(HashMap<Integer, Byte[][]> projectileSpriteCache) {
        if (!DataLists.projectileSpriteCache.isEmpty())
            DataLists.projectileSpriteCache.clear();
        DataLists.projectileSpriteCache = projectileSpriteCache;
    }

    public static void setActorSpriteCache(HashMap<Integer, Byte[][]> actorSpriteCache) {
        if (!DataLists.actorSpriteCache.isEmpty())
            DataLists.actorSpriteCache.clear();
        DataLists.actorSpriteCache = actorSpriteCache;
    }

    public static HashMap<Integer, Byte[][]> getActorSpriteCache() {
        return actorSpriteCache;
    }

    public static HashMap<Integer, Byte[][]> getProjectileSpriteCache() {
        return projectileSpriteCache;
    }

    public static HashMap<Integer, DigitalSound> getDigitalSounds() {
        return digitalSounds;
    }

    public static void setDigitalSounds(HashMap<Integer, DigitalSound> digitalSounds) {
        if (!DataLists.digitalSounds.isEmpty())
            DataLists.digitalSounds.clear();
        DataLists.digitalSounds = digitalSounds;
    }

    public static HashMap<Integer, NodeDefinition> getNodeDefinitions() {
        return nodeDefinitions;
    }

    public static void setNodeDefinitions(HashMap<Integer, NodeDefinition> nodeDefinitions) {
        if (!DataLists.nodeDefinitions.isEmpty())
            DataLists.nodeDefinitions.clear();
        DataLists.nodeDefinitions = nodeDefinitions;
    }

    public static HashMap<Integer, LevelHeader> getLevelHeaders() {
        return levelHeaders;
    }

    public static void setLevelHeaders(HashMap<Integer, LevelHeader> levelHeaders) {
        if(!DataLists.levelHeaders.isEmpty())
            DataLists.levelHeaders.clear();
        DataLists.levelHeaders = levelHeaders;
    }

    public static HashMap<Integer, Color[]> getLevelPalettes() {
        return levelPalettes;
    }

    public static void setLevelPalettes(HashMap<Integer, Color[]> levelPalettes) {
        if(!DataLists.levelPalettes.isEmpty())
            DataLists.levelPalettes.clear();
        DataLists.levelPalettes = levelPalettes;
    }

    public static int getCurrentLevelHeaderOffset() {
        return currentLevelHeaderOffset;
    }

    public static void setCurrentLevelHeaderOffset(int currentLevelHeaderOffset) {
        DataLists.currentLevelHeaderOffset = currentLevelHeaderOffset;
    }

    public static int getCurrentLevelHeaderIndex() {
        return currentLevelHeaderIndex;
    }

    public static void setCurrentLevelHeaderIndex(int currentLevelHeaderIndex) {
        DataLists.currentLevelHeaderIndex = currentLevelHeaderIndex;
    }

    public static ArrayList<Integer> getLevelHeaderList() {
        return levelHeaderList;
    }

    public static void setLevelHeaderList(ArrayList<Integer> levelHeaderList) {
        DataLists.levelHeaderList = levelHeaderList;
        DataLists.levelHeaderList.sort(null);
        System.out.println(levelHeaderList);
    }
}
