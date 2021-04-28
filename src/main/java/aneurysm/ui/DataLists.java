package aneurysm.ui;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aneurysm.io.FileReader;
import aneurysm.offsets.CDOffsets;
import aneurysm.structures.Line;
import aneurysm.structures.MapObjectStructure;
import aneurysm.structures.Vertex;
import aneurysm.structures.WallStructure;

public class DataLists {

	private static SpriteSet[] spriteList = new SpriteSet[36];
	private static int paletteAddress;
	private static int numLines;
	private static int numThings;
	private static int linesAddress;
	private static int thingsAddress;
	private static Image sprites[];
	private static Map<Integer, Image> wallImages = new HashMap<>();
	private static Map<Integer, Image> mapObjectImages = new HashMap<>();
	private static Color[] objectPal = new Color[16];
	private static Color[] levelPal = new Color[32];
	private static boolean cdOrCart = false; // false Cart, true Cd

	public static int getNumLines() {
		return numLines;
	}

	public static void setNumLines(int numLines) {
		DataLists.numLines = numLines;
	}

	public static int getNumThings() {
		return numThings;
	}

	public static void setNumThings(int numThings) {
		DataLists.numThings = numThings;
	}

	public static int getLinesAddress() {
		return linesAddress;
	}

	public static void setLinesAddress(int linesAddress) {
		DataLists.linesAddress = linesAddress;
	}

	public static int getThingsAddress() {
		return thingsAddress;
	}

	public static void setThingsAddress(int thingsAddress) {
		DataLists.thingsAddress = thingsAddress;
	}

	public static void setLevelPal(Color[] levelPal) {
		DataLists.levelPal = levelPal;
	}

	public static int getPaletteAddress() {
		return paletteAddress;
	}

	public static void setPaletteAddress(int paletteAddress) {
		DataLists.paletteAddress = paletteAddress;
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

	public static Color[] getObjectPal() {
		return objectPal;
	}

	public static Color[] getLevelPal() {
		return levelPal;
	}

	public static Image getObjectImage(int id) {
		Image image = null;
		if (!cdOrCart) {
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
		} else {
			switch (id) {
			case 0:
				image = getSprites()[0];
				break;
			case 6:
				image = getSprites()[1];
				break;
			case 0x0c: {
				image = getSprites()[2];
				break;
			}
			case 0x12: {
				image = getSprites()[3];
				break;
			}
			case 0x24: {
				image = getSprites()[4];
				break;
			}
			case 0x2a: {
				image = getSprites()[5];
				break;
			}
			case 0x30: {
				image = getSprites()[6];
				break;
			}
			case 0x36: {
				image = getSprites()[7];
				break;
			}
			case 0x3c: {
				image = getSprites()[8];
				break;
			}
			case 0x48: {
				image = getSprites()[10];
				break;
			}
			case 0x4e: {
				image = getSprites()[11];
				break;
			}
			case 0x54: {
				image = getSprites()[12];
				break;
			}
			case 0x5a: {
				image = getSprites()[13];
				break;
			}
			case 0x60: {
				image = getSprites()[14];
				break;
			}
			case 0x66: {
				image = getSprites()[15];
				break;
			}
			case 0x6c: {
				image = getSprites()[16];
				break;
			}
			case 0x72: {
				image = getSprites()[17];
				break;
			}
			case 0x78: {
				image = getSprites()[18];
				break;
			}
			case 0x7e: {
				image = getSprites()[19];
				break;
			}
			case 0x84: {
				image = getSprites()[23];
				break;
			}
			case 0x8a: {
				image = getSprites()[20];
				break;
			}
			case 0x90: {
				image = getSprites()[22];
				break;
			}
			case 0x96: {
				image = getSprites()[21];
				break;
			}
			case 0x9c: {
				image = getSprites()[24];
				break;
			}
			case 0xa2: {
				image = getSprites()[25];
				break;
			}
			case 0xa8: {
				image = getSprites()[26];
				break;
			}
			case 0xae: {
				image = getSprites()[27];
				break;
			}
			case 0xb4: {
				image = getSprites()[29];
				break;
			}
			case 0xba: {
				image = getSprites()[29];
				break;
			}
			case 0xc0: {
				image = getSprites()[30];
				break;
			}
			case 0xc6: {
				image = getSprites()[28];
				break;
			}
			case 0xcc: {
				image = getSprites()[31];
				break;
			}
			case 0xd2: {
				image = getSprites()[32];
				break;
			}
			case 0xd8: {
				image = getSprites()[33];
				break;
			}
			case 0xe4: {
				image = getSprites()[34];
				break;
			}
			case 0xf0: {
				image = getSprites()[35];
				break;
			}
			}
		}
		return image;
	}

	public static void setupColors() {
		readPalette();
		readSprites();
	}

	private static void readPalette() {
		Window.getReader().readPalette();
	}

	private static void readSprites() {
		sprites = new Image[spriteList.length];
		if(!mapObjectImages.isEmpty()) mapObjectImages.clear();
		if (!isCdOrCart()) {
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
			spriteList[0] = new SpriteSet(0x00, -1,
					0x28, 0x58);
			spriteList[1] = new SpriteSet(0x06, -2,
							CDOffsets.getSpriteDimensions().get(0).getW(), CDOffsets.getSpriteDimensions().get(0).getH());
					
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

//		if(isCdOrCart()) {
//			sprites[0] = FileReader.readFirstThings(0);
//			sprites[1] = FileReader.readFirstThings(1);
//		}
//		int start = isCdOrCart()?2:0;
//		System.out.println("start: " + start);
		for (int i = 0; i < spriteList.length; i++) {

			if (!isCdOrCart())
				sprites[i] = Window.getReader().readImage(spriteList[i].getOffset(), spriteList[i].getWidth(),
						spriteList[i].getHeight());
			else {
				switch(i) {
				case 0:
					sprites[i] = FileReader.readFirstThings(0);
					break;
				case 1:
					sprites[i] = FileReader.readFirstThings(1);
					break;
					default:
						sprites[i] = Window.getReader().readCDImage(spriteList[i].getOffset(), spriteList[i].getWidth(),
								spriteList[i].getHeight());
						System.out.println("i " + i);
						break;
				}
			}

			
			if (spriteList[i].getOffset() == -1) {
				mapObjectImages.put(i, FileReader.readFirstThings(0));
				System.out.println("found -1");
				continue;
			}
			if (spriteList[i].getOffset() == -2) {
				mapObjectImages.put(i, FileReader.readFirstThings(1));
				System.out.println("found -2");
				continue;
			}
			if (spriteList[i].getOffset() != 0) {
				mapObjectImages.put(i, sprites[i]);
			}

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
