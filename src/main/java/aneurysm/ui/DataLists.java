package aneurysm.ui;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aneurysm.structures.Line;
import aneurysm.structures.MapObjectStructure;
import aneurysm.structures.Vertex;
import aneurysm.structures.WallStructure;

public class DataLists {

	private static SpriteSet[] spriteList = new SpriteSet[36];
	private static Image sprites[] = new Image[spriteList.length];
    private static Map<Integer, Image> wallImages = new HashMap<>();
    private static Map<Integer, Image> mapObjectImages = new HashMap<>();
    private static Color[] objectPal = new Color[16];
    private static Color[][] levelPal = new Color[15][16];
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
		DataLists.changesMade = changesMade;
	}

	public static Color[] getObjectPal() {
		return objectPal;
	}

	public static Color[][] getLevelPal() {
		return levelPal;
	}

	public static Image getObjectImage(int id) {
		Image image = null;
		switch (id) {
		case 0x0011b9d2: {
			image = getSprites()[0];
			break;
		}
		case 0x0011b9d4: {
			image = getSprites()[1];
			break;
		}
		case 0x0011cbc4: {
			image = getSprites()[2];
			break;
		}
		case 0x0011cbca: {
			image = getSprites()[3];
			break;
		}
		case 0x0011d5ca: {
			image = getSprites()[4];
			break;
		}
		case 0x0011d5d0: {
			image = getSprites()[5];
			break;
		}
		case 0x0011d5d6: {
			image = getSprites()[6];
			break;
		}
		case 0x0011e348: {
			image = getSprites()[7];
			break;
		}
		case 0x0011e34e: {
			image = getSprites()[8];
			break;
		}
		case 0x0011e354: {
			image = getSprites()[9];
			break;
		}
		case 0x0011ef72: {
			image = getSprites()[10];
			break;
		}
		case 0x0011f7be: {
			image = getSprites()[11];
			break;
		}
		case 0x0011feca: {
			image = getSprites()[12];
			break;
		}
		case 0x00120602: {
			image = getSprites()[13];
			break;
		}
		case 0x00120eae: {
			image = getSprites()[14];
			break;
		}
		case 0x001216a0: {
			image = getSprites()[15];
			break;
		}
		case 0x00121cc2: {
			image = getSprites()[16];
			break;
		}
		case 0x001228a4: {
			image = getSprites()[17];
			break;
		}
		case 0x00122e5a: {
			image = getSprites()[18];
			break;
		}
		case 0x001243de: {
			image = getSprites()[19];
			break;
		}
		case 0x00124df2: {
			image = getSprites()[20];
			break;
		}
		case 0x00124df8: {
			image = getSprites()[21];
			break;
		}
		case 0x00124dfe: {
			image = getSprites()[22];
			break;
		}
		case 0x00124e04: {
			image = getSprites()[23];
			break;
		}
		case 0x00133b0c: {
			image = getSprites()[24];
			break;
		}
		case 0x00146196: {
			image = getSprites()[25];
			break;
		}
		case 0x0014619c: {
			image = getSprites()[26];
			break;
		}
		case 0x00157de2: {
			image = getSprites()[27];
			break;
		}
		case 0x00165de0: {
			image = getSprites()[28];
			break;
		}
		case 0x00165de6: {
			image = getSprites()[29];
			break;
		}
		case 0x001847f4: {
			image = getSprites()[30];
			break;
		}
		case 0x00191786: {
			image = getSprites()[31];
			break;
		}
		case 0x001c98de: {
			image = getSprites()[32];
			break;
		}
		case 0x001c98e4: {
			image = getSprites()[33];
			break;
		}
		case 0x001cbc56: {
			image = getSprites()[34];
			break;
		}
		case 0x001d2bbe: {
			image = getSprites()[35];
			break;
		}
		}
		return image;
	}
	
	static {
		setupLevelPalettes();
		setupSpritePalette();
		readSprites();
	}

	private static void setupLevelPalettes() {
		// Level 1 (Shared with Level 10)
        levelPal[0][0] = new Color(0, 0, 0);
        levelPal[0][1] = new Color(136, 136, 170);
        levelPal[0][2] = new Color(68, 68, 102);
        levelPal[0][3] = new Color(102, 102, 136);
        levelPal[0][4] = new Color(0, 0, 102);
        levelPal[0][5] = new Color(170, 136, 0);
        levelPal[0][6] = new Color(0, 68, 136);
        levelPal[0][7] = new Color(68, 68, 102);
        levelPal[0][8] = new Color(0, 0, 0);
        levelPal[0][9] = new Color(0, 68, 204);
        levelPal[0][10] = new Color(0, 102, 238);
        levelPal[0][11] = new Color(238, 238, 238);
        levelPal[0][12] = new Color(0, 34, 170);
        levelPal[0][13] = new Color(34, 34, 68);
        levelPal[0][14] = new Color(0, 0, 0);
        levelPal[0][15] = new Color(170, 0, 0);

// Level 2 (Shared with Level 9 and Arena 2)
        levelPal[1][0] = new Color(0, 0, 0);
        levelPal[1][1] = new Color(102, 204, 204);
        levelPal[1][2] = new Color(34, 68, 68);
        levelPal[1][3] = new Color(68, 102, 102);
        levelPal[1][4] = new Color(0, 0, 0);
        levelPal[1][5] = new Color(102, 136, 0);
        levelPal[1][6] = new Color(0, 34, 34);
        levelPal[1][7] = new Color(34, 68, 68);
        levelPal[1][8] = new Color(0, 0, 0);
        levelPal[1][9] = new Color(68, 68, 68);
        levelPal[1][10] = new Color(102, 102, 102);
        levelPal[1][11] = new Color(238, 238, 238);
        levelPal[1][12] = new Color(34, 34, 34);
        levelPal[1][13] = new Color(34, 34, 34);
        levelPal[1][14] = new Color(0, 0, 0);
        levelPal[1][15] = new Color(136, 0, 0);

// Level 3 (Shared with Arena 3)
        levelPal[2][0] = new Color(0, 0, 0);
        levelPal[2][1] = new Color(170, 136, 136);
        levelPal[2][2] = new Color(102, 68, 68);
        levelPal[2][3] = new Color(136, 102, 102);
        levelPal[2][4] = new Color(68, 0, 0);
        levelPal[2][5] = new Color(170, 136, 0);
        levelPal[2][6] = new Color(102, 68, 0);
        levelPal[2][7] = new Color(102, 68, 68);
        levelPal[2][8] = new Color(0, 0, 0);
        levelPal[2][9] = new Color(204, 68, 0);
        levelPal[2][10] = new Color(102, 34, 0);
        levelPal[2][11] = new Color(238, 238, 238);
        levelPal[2][12] = new Color(34, 0, 0);
        levelPal[2][13] = new Color(68, 34, 34);
        levelPal[2][14] = new Color(0, 0, 0);
        levelPal[2][15] = new Color(170, 0, 0);

// Level 4 (Shared with Level 12 and Arena 1)
        levelPal[3][0] = new Color(0, 0, 0);
        levelPal[3][1] = new Color(0, 34, 170);
        levelPal[3][2] = new Color(0, 0, 68);
        levelPal[3][3] = new Color(0, 0, 102);
        levelPal[3][4] = new Color(0, 0, 34);
        levelPal[3][5] = new Color(170, 136, 0);
        levelPal[3][6] = new Color(0, 0, 102);
        levelPal[3][7] = new Color(0, 0, 68);
        levelPal[3][8] = new Color(0, 0, 0);
        levelPal[3][9] = new Color(34, 0, 68);
        levelPal[3][10] = new Color(34, 34, 68);
        levelPal[3][11] = new Color(238, 238, 238);
        levelPal[3][12] = new Color(34, 0, 34);
        levelPal[3][13] = new Color(0, 0, 34);
        levelPal[3][14] = new Color(0, 0, 0);
        levelPal[3][15] = new Color(170, 0, 0);

// Level 5
        levelPal[4][0] = new Color(0, 0, 0);
        levelPal[4][1] = new Color(136, 102, 102);
        levelPal[4][2] = new Color(68, 34, 34);
        levelPal[4][3] = new Color(102, 68, 68);
        levelPal[4][4] = new Color(34, 0, 0);
        levelPal[4][5] = new Color(170, 102, 0);
        levelPal[4][6] = new Color(34, 0, 0);
        levelPal[4][7] = new Color(68, 34, 34);
        levelPal[4][8] = new Color(0, 0, 0);
        levelPal[4][9] = new Color(102, 34, 68);
        levelPal[4][10] = new Color(136, 102, 102);
        levelPal[4][11] = new Color(238, 238, 238);
        levelPal[4][12] = new Color(68, 0, 0);
        levelPal[4][13] = new Color(34, 0, 0);
        levelPal[4][14] = new Color(0, 0, 0);
        levelPal[4][15] = new Color(136, 0, 0);

// Level 6
        levelPal[5][0] = new Color(0, 0, 0);
        levelPal[5][1] = new Color(136, 238, 136);
        levelPal[5][2] = new Color(68, 102, 68);
        levelPal[5][3] = new Color(102, 136, 102);
        levelPal[5][4] = new Color(34, 34, 34);
        levelPal[5][5] = new Color(170, 136, 0);
        levelPal[5][6] = new Color(0, 68, 136);
        levelPal[5][7] = new Color(68, 102, 68);
        levelPal[5][8] = new Color(0, 0, 0);
        levelPal[5][9] = new Color(68, 68, 68);
        levelPal[5][10] = new Color(136, 136, 136);
        levelPal[5][11] = new Color(238, 238, 238);
        levelPal[5][12] = new Color(68, 68, 68);
        levelPal[5][13] = new Color(0, 34, 0);
        levelPal[5][14] = new Color(0, 0, 0);
        levelPal[5][15] = new Color(170, 0, 0);

// Level 7
        levelPal[6][0] = new Color(0, 0, 0);
        levelPal[6][1] = new Color(68, 34, 102);
        levelPal[6][2] = new Color(34, 0, 34);
        levelPal[6][3] = new Color(68, 0, 102);
        levelPal[6][4] = new Color(34, 0, 0);
        levelPal[6][5] = new Color(170, 102, 0);
        levelPal[6][6] = new Color(34, 0, 0);
        levelPal[6][7] = new Color(34, 0, 34);
        levelPal[6][8] = new Color(0, 0, 0);
        levelPal[6][9] = new Color(102, 0, 0);
        levelPal[6][10] = new Color(136, 0, 0);
        levelPal[6][11] = new Color(238, 238, 238);
        levelPal[6][12] = new Color(68, 0, 0);
        levelPal[6][13] = new Color(0, 0, 0);
        levelPal[6][14] = new Color(0, 0, 0);
        levelPal[6][15] = new Color(136, 0, 0);

// Level 8
        levelPal[7][0] = new Color(0, 0, 0);
        levelPal[7][1] = new Color(102, 102, 170);
        levelPal[7][2] = new Color(34, 34, 102);
        levelPal[7][3] = new Color(68, 68, 136);
        levelPal[7][4] = new Color(0, 0, 34);
        levelPal[7][5] = new Color(170, 136, 0);
        levelPal[7][6] = new Color(34, 0, 204);
        levelPal[7][7] = new Color(34, 34, 102);
        levelPal[7][8] = new Color(0, 0, 0);
        levelPal[7][9] = new Color(0, 0, 102);
        levelPal[7][10] = new Color(0, 34, 136);
        levelPal[7][11] = new Color(238, 238, 238);
        levelPal[7][12] = new Color(0, 0, 68);
        levelPal[7][13] = new Color(0, 0, 34);
        levelPal[7][14] = new Color(0, 0, 0);
        levelPal[7][15] = new Color(170, 0, 0);

// Level 9 (Shared with Level 2 and Arena 2)
        levelPal[8][0] = new Color(0, 0, 0);
        levelPal[8][1] = new Color(102, 204, 204);
        levelPal[8][2] = new Color(34, 68, 68);
        levelPal[8][3] = new Color(68, 102, 102);
        levelPal[8][4] = new Color(0, 0, 0);
        levelPal[8][5] = new Color(102, 136, 0);
        levelPal[8][6] = new Color(0, 34, 34);
        levelPal[8][7] = new Color(34, 68, 68);
        levelPal[8][8] = new Color(0, 0, 0);
        levelPal[8][9] = new Color(68, 68, 68);
        levelPal[8][10] = new Color(102, 102, 102);
        levelPal[8][11] = new Color(238, 238, 238);
        levelPal[8][12] = new Color(34, 34, 34);
        levelPal[8][13] = new Color(34, 34, 34);
        levelPal[8][14] = new Color(0, 0, 0);
        levelPal[8][15] = new Color(136, 0, 0);

// Level 10 (Shared with Level 1)
        levelPal[9][0] = new Color(0, 0, 0);
        levelPal[9][1] = new Color(136, 136, 170);
        levelPal[9][2] = new Color(68, 68, 102);
        levelPal[9][3] = new Color(102, 102, 136);
        levelPal[9][4] = new Color(0, 0, 102);
        levelPal[9][5] = new Color(170, 136, 0);
        levelPal[9][6] = new Color(0, 68, 136);
        levelPal[9][7] = new Color(68, 68, 102);
        levelPal[9][8] = new Color(0, 0, 0);
        levelPal[9][9] = new Color(0, 68, 204);
        levelPal[9][10] = new Color(0, 102, 238);
        levelPal[9][11] = new Color(238, 238, 238);
        levelPal[9][12] = new Color(0, 34, 170);
        levelPal[9][13] = new Color(34, 34, 68);
        levelPal[9][14] = new Color(0, 0, 0);
        levelPal[9][15] = new Color(170, 0, 0);

// Level 11
        levelPal[10][0] = new Color(0, 0, 0);
        levelPal[10][1] = new Color(102, 102, 102);
        levelPal[10][2] = new Color(34, 34, 34);
        levelPal[10][3] = new Color(68, 68, 68);
        levelPal[10][4] = new Color(0, 0, 0);
        levelPal[10][5] = new Color(102, 136, 0);
        levelPal[10][6] = new Color(0, 0, 0);
        levelPal[10][7] = new Color(34, 34, 34);
        levelPal[10][8] = new Color(0, 0, 0);
        levelPal[10][9] = new Color(68, 68, 68);
        levelPal[10][10] = new Color(102, 102, 102);
        levelPal[10][11] = new Color(238, 238, 238);
        levelPal[10][12] = new Color(34, 34, 34);
        levelPal[10][13] = new Color(34, 34, 34);
        levelPal[10][14] = new Color(0, 0, 0);
        levelPal[10][15] = new Color(136, 0, 0);

// Level 12 (Shared with Level 4 and Arena 1)
        levelPal[11][0] = new Color(0, 0, 0);
        levelPal[11][1] = new Color(0, 34, 170);
        levelPal[11][2] = new Color(0, 0, 68);
        levelPal[11][3] = new Color(0, 0, 102);
        levelPal[11][4] = new Color(0, 0, 34);
        levelPal[11][5] = new Color(170, 136, 0);
        levelPal[11][6] = new Color(0, 0, 102);
        levelPal[11][7] = new Color(0, 0, 68);
        levelPal[11][8] = new Color(0, 0, 0);
        levelPal[11][9] = new Color(34, 0, 68);
        levelPal[11][10] = new Color(34, 34, 68);
        levelPal[11][11] = new Color(238, 238, 238);
        levelPal[11][12] = new Color(34, 0, 34);
        levelPal[11][13] = new Color(0, 0, 34);
        levelPal[11][14] = new Color(0, 0, 0);
        levelPal[11][15] = new Color(170, 0, 0);

// Arena 1 (Shared with Level 4 and Level 12)
        levelPal[12][0] = new Color(0, 0, 0);
        levelPal[12][1] = new Color(0, 34, 170);
        levelPal[12][2] = new Color(0, 0, 68);
        levelPal[12][3] = new Color(0, 0, 102);
        levelPal[12][4] = new Color(0, 0, 34);
        levelPal[12][5] = new Color(170, 136, 0);
        levelPal[12][6] = new Color(0, 0, 102);
        levelPal[12][7] = new Color(0, 0, 68);
        levelPal[12][8] = new Color(0, 0, 0);
        levelPal[12][9] = new Color(34, 0, 68);
        levelPal[12][10] = new Color(34, 34, 68);
        levelPal[12][11] = new Color(238, 238, 238);
        levelPal[12][12] = new Color(34, 0, 34);
        levelPal[12][13] = new Color(0, 0, 34);
        levelPal[12][14] = new Color(0, 0, 0);
        levelPal[12][15] = new Color(170, 0, 0);

// Arena 2 (Shared with Level 2 and Level 9)
        levelPal[13][0] = new Color(0, 0, 0);
        levelPal[13][1] = new Color(102, 204, 204);
        levelPal[13][2] = new Color(34, 68, 68);
        levelPal[13][3] = new Color(68, 102, 102);
        levelPal[13][4] = new Color(0, 0, 0);
        levelPal[13][5] = new Color(102, 136, 0);
        levelPal[13][6] = new Color(0, 34, 34);
        levelPal[13][7] = new Color(34, 68, 68);
        levelPal[13][8] = new Color(0, 0, 0);
        levelPal[13][9] = new Color(68, 68, 68);
        levelPal[13][10] = new Color(102, 102, 102);
        levelPal[13][11] = new Color(238, 238, 238);
        levelPal[13][12] = new Color(34, 34, 34);
        levelPal[13][13] = new Color(34, 34, 34);
        levelPal[13][14] = new Color(0, 0, 0);
        levelPal[13][15] = new Color(136, 0, 0);

// Arena 3 (Shared with Level 3)
        levelPal[14][0] = new Color(0, 0, 0);
        levelPal[14][1] = new Color(170, 136, 136);
        levelPal[14][2] = new Color(102, 68, 68);
        levelPal[14][3] = new Color(136, 102, 102);
        levelPal[14][4] = new Color(68, 0, 0);
        levelPal[14][5] = new Color(170, 136, 0);
        levelPal[14][6] = new Color(102, 68, 0);
        levelPal[14][7] = new Color(102, 68, 68);
        levelPal[14][8] = new Color(0, 0, 0);
        levelPal[14][9] = new Color(204, 68, 0);
        levelPal[14][10] = new Color(102, 34, 0);
        levelPal[14][11] = new Color(238, 238, 238);
        levelPal[14][12] = new Color(34, 0, 0);
        levelPal[14][13] = new Color(68, 34, 34);
        levelPal[14][14] = new Color(0, 0, 0);
        levelPal[14][15] = new Color(170, 0, 0);
}
	
	private static void setupSpritePalette() {
		objectPal[0] = new Color(34, 34, 0x00);
		objectPal[1] = new Color(68, 34, 68);
		objectPal[2] = new Color(102, 102, 102);
		objectPal[3] = new Color(170, 170, 170);
		objectPal[4] = new Color(238, 238, 238);
		objectPal[5] = new Color(0, 0, 34);
		objectPal[6] = new Color(0, 68, 0);
		objectPal[7] = new Color(0, 136, 0);
		objectPal[8] = new Color(34, 0, 34);
		objectPal[9] = new Color(102, 0, 34);
		objectPal[10] = new Color(136, 0, 0);
		objectPal[11] = new Color(204, 34, 34);
		objectPal[12] = new Color(204, 102, 34);
		objectPal[13] = new Color(238, 170, 68);
		objectPal[14] = new Color(0, 0, 34);
		objectPal[15] = new Color(0, 0, 0);
	}
	
	private static void readSprites() {
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
		
		for(int i=0; i< spriteList.length;i++) {

			
			sprites[i] = Window.getReader().readImage(spriteList[i].getOffset(), spriteList[i].getWidth(), spriteList[i].getHeight());
			System.out.println("adding to object images " + spriteList[i].getOffset());
			mapObjectImages.put(spriteList[i].getOffset(), sprites[i]);

		}
	}

	public static Image[] getSprites() {
		return sprites;
	}


	private static List<MapObjectStructure> objects = new ArrayList<>();
	private static List<WallStructure> walls = new ArrayList<>();
	private static List<Vertex> vertices = new ArrayList<>();
	private static List<Line> lines = new ArrayList<>();

	public static List<MapObjectStructure> getObjects() {
		return objects;
	}

	public static void setObjects(List<MapObjectStructure> objects) {
		DataLists.objects = objects;
	}

	public static List<WallStructure> getWalls() {
		return walls;
	}

	public static void setWalls(List<WallStructure> walls) {
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

}
