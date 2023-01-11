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

	private static SpriteSet[] spriteList = new SpriteSet[37];
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
	private static boolean enemyPatchedIn = false;

	public static boolean isEnemyPatchedIn() {
		return enemyPatchedIn;
	}

	public static void setEnemyPatchedIn(boolean enemyPatchedIn) {
		DataLists.enemyPatchedIn = enemyPatchedIn;
	}

	private static HashMap<Integer, String> thingAttributes = new HashMap<>();

	public static HashMap<Integer, String> getThingAttributes() {
		return thingAttributes;
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
	};

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
			if (!enemyPatchedIn) {
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
				case 0x00200000: {
					image = getSprites()[36];
					break;
				}

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
			case 0x42: {
				image = getSprites()[9];
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
		setThingAttributes();
	}

	private static void readPalette() {
		Window.getReader().readPalette();
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
					System.out.println("i " + i);
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
		} catch (ArrayIndexOutOfBoundsException e) { System.out.println("why are you like this aneurysm-chan :(");}
		
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
