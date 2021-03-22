package aneurysm.io;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import aneurysm.config.ConfigOptions;
import aneurysm.offsets.CartOffsets;
import aneurysm.patchdata.TexturePatch0D7822;
import aneurysm.patchdata.TexturePatch0DB822;
import aneurysm.structures.MapObjectStructure;
import aneurysm.structures.WallStructure;
import aneurysm.ui.DataLists;

public class FileReader {

	private static ConfigOptions config = new ConfigOptions();
	private static boolean configLoaded = false;

	public static boolean isConfigLoaded() {
		return configLoaded;
	}

	public static void setConfigLoaded(boolean configLoaded) {
		FileReader.configLoaded = configLoaded;
	}

	public static ConfigOptions getConfig() {
		return config;
	}

	public boolean isEOF(FileInputStream in) {
		try {
			return in.getChannel().position() >= in.getChannel().size() - 1;
		} catch (IOException e) {
			return true;
		}
	}

	public List<MapObjectStructure> oldReadMapObjects(String filename) throws IOException {

		List<MapObjectStructure> objects = new ArrayList<MapObjectStructure>();

		File in = new File(filename);

		MapObjectStructure obj;

		FileInputStream ins = new FileInputStream(in);
		DataInputStream ind = new DataInputStream(ins);
		while (!isEOF(ins)) {
			obj = new MapObjectStructure();
			obj.setThingID(ind.readInt());
			obj.setOffset_major(ind.readByte());
			obj.setOffset_minor(ind.readByte());
			obj.setX(ind.readShort());
			obj.setY(ind.readShort());
			objects.add(obj);
		}
		ins.close();
		ind.close();

		return objects;
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
					im.setRGB(i, j, DataLists.getObjectPal()[read & 0x000000ff].getRGB());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return im;
	}

	public List<MapObjectStructure> readMapObjects(String filename) throws IOException {

		List<MapObjectStructure> objects = new ArrayList<MapObjectStructure>();

		File in = new File(filename);

		MapObjectStructure obj;

		FileInputStream ins = new FileInputStream(in);
		DataInputStream ind = new DataInputStream(ins);

		ind.skipBytes(CartOffsets.LEVELOBJECTSTART[config.getCurrentLevel()]);

		int max = CartOffsets.LEVELOBJECTLENGTHS[config.getCurrentLevel()];
		int current = 0;

		while (current < max) {
			obj = new MapObjectStructure();
			obj.setThingID(ind.readInt());
			current += 4;
			obj.setOffset_major(ind.readByte());
			current += 1;
			obj.setOffset_minor(ind.readByte());
			current += 1;
			obj.setY(ind.readShort());
			current += 2;
			obj.setX(ind.readShort());
			current += 2;
			objects.add(obj);
		}
		ins.close();
		ind.close();

		return objects;
	}

	private Image readWallGraphics(int location, int actual, short length, String filename, boolean flipped)
			throws IOException {
		BufferedImage image = new BufferedImage(length, 128, BufferedImage.TYPE_3BYTE_BGR);

		RandomAccessFile rin = new RandomAccessFile(filename, "rw");

		/*
		 * boolean flipped = false; if ((actual & 0xFF000000) != 0x00000000) { flipped =
		 * true; actual = actual & 0x00FFFFFF; }
		 */
		rin.seek(location);

		int count = 0;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < 64; j++) {
				byte read = rin.readByte();

				if (read == 0x70 || (read & 0x000000F0) == 0x80) {
					if (read == 0x70)
						image.setRGB(i, 63 - j, DataLists.getLevelPal()[config.getCurrentLevel()][0x2].getRGB());
					if ((read & 0x000000F0) == 0x80)
						image.setRGB(i, 63 - j, DataLists.getLevelPal()[config.getCurrentLevel()][0xe].getRGB());
				} else
					image.setRGB(i, 63 - j,
							DataLists.getLevelPal()[config.getCurrentLevel()][(read & 0x000000ff) / 0x10].getRGB());

				if (read == 0x60 || read == 0x70 || (read & 0x000000F0) == 0x80) {
					if (read == 0x60)
						image.setRGB(i, 64 + j, DataLists.getLevelPal()[config.getCurrentLevel()][0x1].getRGB());
					if (read == 0x70)
						image.setRGB(i, 64 + j, DataLists.getLevelPal()[config.getCurrentLevel()][0xe].getRGB());
					if ((read & 0x000000F0) == 0x80)
						image.setRGB(i, 64 + j, DataLists.getLevelPal()[config.getCurrentLevel()][0x2].getRGB());
				} else
					image.setRGB(i, 64 + j,
							DataLists.getLevelPal()[config.getCurrentLevel()][(read & 0x000000ff) / 0x10].getRGB());

				count++;
				if (count == 64) {
					count = 0;
					rin.seek(rin.getFilePointer() + 64);
				}

			}
		}
		rin.close();
		return image;
	}

	public List<WallStructure> readWalls(String filename) throws IOException {
		ArrayList<WallStructure> walls = new ArrayList<WallStructure>();
		File in = new File(filename);
		FileInputStream ins = new FileInputStream(in);
		DataInputStream ind = new DataInputStream(ins);

		ind.skipBytes(CartOffsets.LEVELWALLSTART[config.getCurrentLevel()]);
		int max = CartOffsets.LEVELWALLENGTHS[config.getCurrentLevel()];
		int current = 0;

		WallStructure wall;

		while (current < max) {
			wall = new WallStructure();
			wall.setTextureID(ind.readInt());
			current += 4;
			wall.setTextureScale(ind.readShort());
			current += 2;
			wall.setY1(ind.readShort());
			current += 2;
			wall.setX1(ind.readShort());
			current += 2;
			wall.setY2(ind.readShort());
			current += 2;
			wall.setX2(ind.readShort());
			current += 2;
			wall.setDoorType(ind.readByte());
			current += 1;
			wall.setDoorNumber(ind.readByte());
			current += 1;
			wall.setDoorNSLow(ind.readByte());
			current++;
			wall.setDoorNSHigh(ind.readByte());
			current++;
			wall.setDoorWELow(ind.readByte());
			current++;
			wall.setDoorWEHigh(ind.readByte());
			current++;
			walls.add(wall);
			if (!DataLists.getWallImages().containsKey(wall.getTextureID())) {
				int offset = wall.getTextureID();
				if ((offset & 0xFF000000) != 0)
					DataLists.getWallImages().put(wall.getTextureID() & 0x00FFFFFF,
							readWallGraphics(wall.getTextureID() & 0x00FFFFFF, wall.getTextureID(),
									wall.getTextureScale(), filename, true));
				else
					DataLists.getWallImages().put(wall.getTextureID(),
							readWallGraphics(wall.getTextureID() & 0x00FFFFFF, wall.getTextureID(),
									wall.getTextureScale(), filename, false));
			}

		}
		ins.close();
		ind.close();

		return walls;
	}

	public List<WallStructure> oldReadWalls(String filename) throws IOException {
		ArrayList<WallStructure> walls = new ArrayList<WallStructure>();
		File in = new File(filename);
		FileInputStream ins = new FileInputStream(in);
		DataInputStream ind = new DataInputStream(ins);

		WallStructure wall;

		while (!isEOF(ins)) {
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

		}

		ins.close();
		ind.close();

		return walls;

	}

	public static void fixChecksum() {

		try {
			RandomAccessFile rf = new RandomAccessFile(new File(config.getLocation()), "rw");
			rf.seek(0x18E);
			long actualSum = 0;
			rf.seek(0x200);
			long fileSize = rf.length();

			for (int i = 0; i < fileSize - 0x200; i += 2) {

				actualSum += rf.readShort();
			}
			rf.seek(0x18E);
			rf.writeShort((short) (65535 & actualSum));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeLevelToFile(List<WallStructure> walls, List<MapObjectStructure> objs) {
		try {
			RandomAccessFile fi = new RandomAccessFile(new File(config.getLocation()), "rw");
			fi.seek(CartOffsets.LEVELWALLSTART[config.getCurrentLevel()]);
			for (int i = 0; i < walls.size(); i++) {
				fi.writeInt(walls.get(i).getTextureID());
				fi.writeShort(walls.get(i).getTextureScale());
				fi.writeShort(walls.get(i).getY1());
				fi.writeShort(walls.get(i).getX1());
				fi.writeShort(walls.get(i).getY2());
				fi.writeShort(walls.get(i).getX2());
				fi.writeByte(walls.get(i).getDoorType());
				fi.writeByte(walls.get(i).getDoorNumber());
				fi.writeByte(walls.get(i).getDoorNSLow());
				fi.writeByte(walls.get(i).getDoorNSHigh());
				fi.writeByte(walls.get(i).getDoorWELow());
				fi.writeByte(walls.get(i).getDoorWEHigh());
			}
			for (int i = 0; i < objs.size(); i++) {
				fi.writeInt(objs.get(i).getThingID());
				fi.writeByte(objs.get(i).getOffset_major());
				fi.writeByte(objs.get(i).getOffset_minor());
				fi.writeShort(objs.get(i).getY());
				fi.writeShort(objs.get(i).getX());
			}
			fi.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void exportToFile(List<WallStructure> walls, List<MapObjectStructure> objs, String filename)
			throws IOException {
		File out = new File(filename);
		FileOutputStream fos = new FileOutputStream(out);
		DataOutputStream dos = new DataOutputStream(fos);

		for (int i = 0; i < walls.size(); i++) {
			dos.writeInt(walls.get(i).getTextureID());
			dos.writeShort(walls.get(i).getTextureScale());
			dos.writeShort(walls.get(i).getY1());
			dos.writeShort(walls.get(i).getX1());
			dos.writeShort(walls.get(i).getY2());
			dos.writeShort(walls.get(i).getX2());
			dos.writeByte(walls.get(i).getDoorType());
			dos.writeByte(walls.get(i).getDoorNumber());
			dos.writeByte(walls.get(i).getDoorNSLow());
			dos.writeByte(walls.get(i).getDoorNSHigh());
			dos.writeByte(walls.get(i).getDoorWELow());
			dos.writeByte(walls.get(i).getDoorWEHigh());
		}

		for (int i = 0; i < objs.size(); i++) {
			dos.writeInt(objs.get(i).getThingID());
			dos.writeByte(objs.get(i).getOffset_major());
			dos.writeByte(objs.get(i).getOffset_minor());
			dos.writeShort(objs.get(i).getY());
			dos.writeShort(objs.get(i).getX());
		}

		dos.close();
		fos.close();
		out = null;
	}

	public void writeConfig() {
		File conf = new File("./config");

		FileWriter out;
		try {
			out = new FileWriter(conf);
			out.write("location=" + config.getLocation() + "\n");
			out.write("lastlevel=" + config.getCurrentLevel() + "\n");
			for (int i = 0; i < config.getGetMapSize(); i++) {
				out.write("level" + i + "XOffsets=" + config.getLevelXOffs(i) + "\n");
				out.write("level" + i + "YOffsets=" + config.getLevelYOffs(i) + "\n");
				out.write("level" + i + "Grid=" + config.getLevelGrid(i) + "\n");
				out.write("level" + i + "Zoom=" + config.getLevelZoom(i) + "\n");
				out.write("level" + i + "Rotation=" + config.getLevelRot(i) + "\n");
				out.write("level" + i + "Mode=" + config.getLevelMode(i) + "\n");
			}
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

		} catch (FileNotFoundException e) {
			e.printStackTrace();
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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fixed;
	}

	public boolean readConfig() {
		File conf = new File("./config");
		try {
			Scanner fins = new Scanner(conf);

			config.setLocation(fins.nextLine().split("=")[1]);
			config.setCurrentLevel(Integer.parseInt(fins.nextLine().split("=")[1]));
			for (int i = 0; i < config.getGetMapSize(); i++) {
				config.setLevelXOffs(Integer.parseInt(fins.nextLine().split("=")[1]), i);
				config.setLevelYOffs(Integer.parseInt(fins.nextLine().split("=")[1]), i);
				config.setLevelGrid(Integer.parseInt(fins.nextLine().split("=")[1]), i);
				config.setLevelZoom(Integer.parseInt(fins.nextLine().split("=")[1]), i);
				config.setLevelRot(Boolean.parseBoolean(fins.nextLine().split("=")[1]), i);
				config.setLevelMode(Integer.parseInt(fins.nextLine().split("=")[1]), i);
			}
			configLoaded = true;
			fins.close();
		} catch (FileNotFoundException e) {

			configLoaded = false;
		}

		return configLoaded;
	}
}
