package aneurysm.io;

import java.awt.Color;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import aneurysm.config.ConfigOptions;
import aneurysm.offsets.CDOffsets;
import aneurysm.pack.ThingPack;
import aneurysm.patchdata.TexturePatch0D7822;
import aneurysm.patchdata.TexturePatch0DB822;
import aneurysm.structures.MapObjectStructure;
import aneurysm.structures.WallStructure;
import aneurysm.ui.DataLists;
import aneurysm.ui.Window;

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

	public Image readCDImage(int offset, int w, int h) {
		BufferedImage im = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		try {
			RandomAccessFile rin = new RandomAccessFile(config.getLocation(), "rw");
			rin.seek(offset);
			for (int j = 0; j < w; j += 2) {
				for (int i = 0; i < h; i++) {
					byte read = rin.readByte();
					byte lower, higher;
					if (read == 0) {
						continue;
					}
					if (j == w - 1) {
						im.setRGB(j, i, (DataLists.getObjectPal()[(read & 0x000000F0) / 16].getRGB()));
					}

					else {
						if (((read & 0xF0)) != (read & 0x0F) * 0x10) {

							if ((read & 0xF0) > ((read & 0x0F) * 16)) {
								lower = (byte) ((read << 4) + (read & 0x0F));
								higher = (byte) ((read & 0xF0) + ((read & 0xF0) / 0x10));

								im.setRGB(j, i, (DataLists.getObjectPal()[(higher & 0x000000F0) / 16].getRGB()));
								im.setRGB(j + 1, i, (DataLists.getObjectPal()[(lower & 0x000000F0) / 16].getRGB()));
							} else {
								higher = (byte) ((read << 4) + (read & 0x0F));
								lower = (byte) ((read & 0xF0) + ((read & 0xF0) / 0x10));

								im.setRGB(j + 1, i, (DataLists.getObjectPal()[(higher & 0x000000F0) / 16].getRGB()));
								im.setRGB(j, i, (DataLists.getObjectPal()[(lower & 0x000000F0) / 16].getRGB()));
							}

						} else {
							im.setRGB(j, i, (DataLists.getObjectPal()[(read & 0x000000F0) / 16].getRGB()));
							im.setRGB(j + 1, i, (DataLists.getObjectPal()[(read & 0x000000F0) / 16].getRGB()));
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
					im.setRGB(i, j, DataLists.getObjectPal()[read & 0x000000ff].getRGB());
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
		ind.seek(DataLists.getThingsAddress());
		int current = 0;

		while (current < DataLists.getNumThings()) {
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

	private Image readCartWallGraphics(int location, int actual, short length, String filename) {
		BufferedImage image = new BufferedImage(length, 128, BufferedImage.TYPE_3BYTE_BGR);
		try {
			RandomAccessFile rin = new RandomAccessFile(filename, "rw");

			rin.seek(location);

			int count = 0;
			for (int i = 0; i < length; i++) {
				for (int j = 0; j < 64; j++) {
					byte read = rin.readByte();

					if (read == 0x70 || (read & 0x000000F0) == 0x80) {
						if (read == 0x70)
							image.setRGB(i, 63 - j, DataLists.getLevelPal()[0x2].getRGB());
						if ((read & 0x000000F0) == 0x80)
							image.setRGB(i, 63 - j, DataLists.getLevelPal()[0xe].getRGB());
					} else
						image.setRGB(i, 63 - j, DataLists.getLevelPal()[(read & 0x000000ff) / 0x10].getRGB());

					if (read == 0x60 || read == 0x70 || (read & 0x000000F0) == 0x80) {
						if (read == 0x60)
							image.setRGB(i, 64 + j, DataLists.getLevelPal()[0x11].getRGB());
						if (read == 0x70)
							image.setRGB(i, 64 + j, DataLists.getLevelPal()[0x1e].getRGB());
						if ((read & 0x000000F0) == 0x80)
							image.setRGB(i, 64 + j, DataLists.getLevelPal()[0x12].getRGB());
					} else
						image.setRGB(i, 64 + j, DataLists.getLevelPal()[((read & 0x000000ff) / 0x10) + 0x10].getRGB());

					count++;
					if (count == 64) {
						count = 0;
						rin.seek(rin.getFilePointer() + 64);
					}

				}
			}
			rin.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return image;
	}

	private void readByteArrayToImage(Byte[][] data, BufferedImage image) {
//		int num = Integer.parseInt(config.getLocation().replaceAll("[^0-9]", "")) - 1;
		Color[] pal = DataLists.getLevelPal();
		int yOffs = 0;
		int xOffs = 0;
		for (int i = 0; i < data[data.length - 1].length; i++) {
			for (int j = 0; j < data.length / 2; j++) {
				if (data[j][i] == 0x70 || (data[j][i] & 0x000000F0) == 0x80) {
					if (data[j][i] == 0x70) {
						image.setRGB(yOffs, 63 - j + xOffs, pal[2].getRGB());
					}
					if ((data[j][i] & 0x000000F0) == 0x80) {
						image.setRGB(yOffs, 63 - j + xOffs, pal[14].getRGB());
					}
				} else {
					image.setRGB(yOffs, 63 - j + xOffs, pal[data[j][i] / 16 & 0x0000000F].getRGB());
				}
				// bottom
				if (data[j][i] == 0x60 || data[j][i] == 0x70 || (data[j][i] & 0x000000F0) == 0x80) {
					if (data[j][i] == 0x60)
						image.setRGB(yOffs, 64 + j + xOffs, pal[1 + 16].getRGB());
					if (data[j][i] == 0x70)
						image.setRGB(yOffs, 64 + j + xOffs, pal[14 + 16].getRGB());
					if ((data[j][i] & 0x000000F0) == 0x80)
						image.setRGB(yOffs, 64 + j + xOffs, pal[2 + 16].getRGB());
				} else {
					image.setRGB(yOffs, 64 + j + xOffs, pal[(data[j][i] / 16 & 0x0000000F) + 16].getRGB());
				}
			}
			yOffs++;
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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}

	public Byte[][] readSprite(int location, int w, int h) {
		Byte[][] out = null;
		if (!DataLists.isCdOrCart())
			out = readRomSprite(location, w, h);
		else {
			out = readCDSprite(location, w, h);
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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
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

	public Byte[][] readCDSprite(int offset, int w, int h) {
		Byte[][] out = new Byte[w][h];
		try {
			RandomAccessFile rin = new RandomAccessFile(config.getLocation(), "rw");
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

		return out;
	}

	private Image readCDWallGraphics(int location, int actual, short length, String filename) {
		BufferedImage image = new BufferedImage(length, 128, BufferedImage.TYPE_INT_ARGB);

		try {
			RandomAccessFile rin = new RandomAccessFile(config.getLocation(), "rw");
			int yOffs = 0, xOffs = 0, colCount = 0;

			Byte[][] convert = new Byte[128][length];
			int tex = location;
			if ((tex & 0xFF000000) != 0) {
				tex = tex & 0x00FFFFFF;
			}

			int current = 0;
			for (int i = 0; i < length; i += 4) {
				for (int j = 0; j < 64; j++) {
					rin.seek(tex + current);
					byte read = (rin.readByte());

					rin.seek((tex + 0x40) + current);
					byte next = rin.readByte();
					byte higher;
					byte lower;
					if (((read & 0xF0)) != (read & 0x0F) * 0x10) {

						if ((read & 0xF0) > ((read & 0x0F) * 16)) {
							lower = (byte) ((read << 4) + (read & 0x0F));
							higher = (byte) ((read & 0xF0) + ((read & 0xF0) / 0x10));

							convert[j][i] = (byte) (higher & 0x000000F0);
							convert[j][i + 1] = (byte) (lower & 0x000000F0);
							convert[j + 64][i] = (byte) ((higher & 0x000000F0 / 16) + 16);
							convert[j + 64][i + 1] = (byte) ((lower & 0x000000F0 / 16) + 16);

						} else {
							higher = (byte) ((read << 4) + (read & 0x0F));
							lower = (byte) ((read & 0xF0) + ((read & 0xF0) / 0x10));

							convert[j][i + 1] = (byte) (higher & 0x000000F0);
							convert[j][i] = (byte) (lower & 0x000000F0);
							convert[j + 64][i + 1] = (byte) ((higher & 0x000000F0 / 16) + 16);
							convert[j + 64][i] = (byte) ((lower & 0x000000F0 / 16) + 16);

						}
					} else {

						convert[j][i] = (byte) (read & 0x000000F0);
						convert[j][i + 1] = (byte) (read & 0x000000F0);
						convert[j + 64][i] = (byte) (((read & 0x000000F0) / 16) + 16);
						convert[j + 64][i + 1] = (byte) (((read & 0x000000F0) / 16) + 16);

					}

					if (((next & 0xF0)) != (next & 0x0F) * 0x10) {

						if ((next & 0xF0) > ((next & 0x0F) * 16)) {
							lower = (byte) ((next << 4) + (next & 0x0F));
							higher = (byte) ((next & 0xF0) + ((next & 0xF0) / 0x10));

							convert[j][i + 2] = (byte) (higher & 0x000000F0);
							convert[j][i + 3] = (byte) (lower & 0x000000F0);
							convert[j + 64][i + 2] = (byte) ((higher & 0x000000F0 / 16) + 16);
							convert[j + 64][i + 3] = (byte) ((lower & 0x000000F0 / 16) + 16);
						} else {
							higher = (byte) ((next << 4) + (next & 0x0F));
							lower = (byte) ((next & 0xF0) + ((next & 0xF0) / 0x10));
							convert[j][i + 2] = (byte) (lower & 0x000000F0);
							convert[j][i + 3] = (byte) (higher & 0x000000F0);
							convert[j + 64][i + 2] = (byte) ((lower & 0x000000F0 / 16) + 16);
							convert[j + 64][i + 3] = (byte) ((higher & 0x000000F0 / 16) + 16);

						}
					} else {
						convert[j][i + 2] = (byte) (next & 0x000000F0);
						convert[j][i + 3] = (byte) (next & 0x000000F0);
//						System.out.println("color " + (byte) (((next & 0x000000F0) / 16)+16));
						convert[j + 64][i + 2] = (byte) (((next & 0x000000F0) / 16) + 16);
						convert[j + 64][i + 3] = (byte) (((next & 0x000000F0) / 16) + 16);

					}
					current++;
					if (current % 64 == 0) {
						colCount++;
						current += 64;
					}
				}
				yOffs += 4;
			}
			readByteArrayToImage(convert, image);
			rin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
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

	public void readNewFile(String filename) {
		if (!DataLists.getMapObjectImages().isEmpty())
			DataLists.getMapObjectImages().clear();
		try {
			RandomAccessFile rin = new RandomAccessFile(filename, "rw");
			rin.seek(0x02);
			if (rin.readInt() == 0x30 && rin.length() < 0x1FFFFF) {
				System.out.println("cd");
				DataLists.setCdOrCart(true);
				levelHeaderLocation = 0;
			} else {
				rin.seek(0xF9D0);
				System.out.println("rom");
				levelHeaderLocation = rin.readInt();
				DataLists.setCdOrCart(false);
			}
			config.setLocation(filename);
			rin.close();
			grabHeaderData(filename);
			if (DataLists.isCdOrCart()) {
				populateCDOffsets();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Image readFirstThings(int id) {
		System.out.println("id " + id);
		ArrayList<Integer> list = new ArrayList<>();
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
					im.setRGB(x++, y, DataLists.getObjectPal()[data].getRGB());
					if(x > im.getWidth()-1) {
						x=0; y++;
					}
				}
			}
			BufferedImage img = (BufferedImage) im;
			AffineTransform af = AffineTransform.getScaleInstance(-1, 1);

			af.translate(0, 0);
			af.rotate(-1, 15);
			AffineTransformOp op = new AffineTransformOp(af, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			img = op.filter(img, null);
			return img;
		}
		
		

		
		return im;
	}

	public void grabHeaderData(String filename) {
		RandomAccessFile ind;
		try {
			ind = new RandomAccessFile(filename, "rw");
			if (!DataLists.isCdOrCart()) {
				if (levelHeaderLocation == 0) {
					ind.seek(0xF9D0);
					levelHeaderLocation = ind.readInt();
				}
				ind.seek(levelHeaderLocation + (0x2C * config.getCurrentLevel()));
			} else
				levelHeaderLocation = 0;
			DataLists.setNumLines(ind.readShort());
			DataLists.setLinesAddress(ind.readInt());
			DataLists.setNumThings(ind.readShort());
			DataLists.setThingsAddress(ind.readInt());
			ind.readInt();
			DataLists.setPaletteAddress(ind.readInt());
			ind.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int levelHeaderLocation = 0;

	public List<WallStructure> readWalls(String filename) throws IOException {
		ArrayList<WallStructure> walls = new ArrayList<WallStructure>();
		RandomAccessFile ind = new RandomAccessFile(filename, "rw");

		int current = 0;

		WallStructure wall;
		ind.seek(DataLists.getLinesAddress());
		while (current < DataLists.getNumLines()) {
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
		System.out.println("reading wall images");
		if (palRefresh)
			DataLists.getWallImages().clear();
		System.out.println("size " + DataLists.getWalls().size());
		for (int i = 0; i < DataLists.getWalls().size(); i++) {
			if (!DataLists.getWallImages().containsKey(DataLists.getWalls().get(i).getTextureID())) {
				if (!DataLists.isCdOrCart())
					DataLists.getWallImages().put(DataLists.getWalls().get(i).getTextureID() & 0x00FFFFFF,
							readCartWallGraphics(DataLists.getWalls().get(i).getTextureID() & 0x00FFFFFF,
									DataLists.getWalls().get(i).getTextureID(),
									DataLists.getWalls().get(i).getTextureScale(), filename));
				else
					DataLists.getWallImages().put(DataLists.getWalls().get(i).getTextureID() & 0x00FFFFFF,
							readCDWallGraphics(DataLists.getWalls().get(i).getTextureID() & 0x00FFFFFF,
									DataLists.getWalls().get(i).getTextureID(),
									DataLists.getWalls().get(i).getTextureScale(), filename));
			}
		}
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
			rf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeLevelToFile(List<WallStructure> walls, List<MapObjectStructure> objs) {
		try {
			RandomAccessFile fi = new RandomAccessFile(new File(config.getLocation()), "rw");
			fi.seek(DataLists.getLinesAddress());
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

	public void writeConfig() {
		File conf = new File("./config");

		FileWriter out;
		try {
			out = new FileWriter(conf);
			out.write("location=" + config.getLocation() + "\n");
			out.write("lastlevel=" + config.getCurrentLevel() + "\n");
			for (int i = 0; i < config.getGetMapSize(); i++) {
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
			fi.close();
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
				config.setLevelGrid(Integer.parseInt(fins.nextLine().split("=")[1]), i);
				config.setLevelZoom(Integer.parseInt(fins.nextLine().split("=")[1]), i);
				config.setLevelRot(Boolean.parseBoolean(fins.nextLine().split("=")[1]), i);
				config.setLevelMode(Integer.parseInt(fins.nextLine().split("=")[1]), i);
			}
			try {
				byte iscc = Byte.parseByte(fins.nextLine().split("=")[1]);
				if (iscc == 0)
					DataLists.setCdOrCart(false);
				else
					DataLists.setCdOrCart(true);
			} catch (Exception e) {
				DataLists.setCdOrCart(false);
			}
			try {
				Window.getKeyControls().setGridKey(Integer.parseInt(fins.nextLine().split("=")[1]));
				Window.getKeyControls().setLinesKey(Integer.parseInt(fins.nextLine().split("=")[1]));
				Window.getKeyControls().setMouseObjectMove(Integer.parseInt(fins.nextLine().split("=")[1]));
				Window.getKeyControls().setMousePan(Integer.parseInt(fins.nextLine().split("=")[1]));
				Window.getKeyControls().setMouseSelect(Integer.parseInt(fins.nextLine().split("=")[1]));
				Window.getKeyControls().setMoveObjectKey(Integer.parseInt(fins.nextLine().split("=")[1]));
				Window.getKeyControls().setPanKey(Integer.parseInt(fins.nextLine().split("=")[1]));
				Window.getKeyControls().setRotateKey(Integer.parseInt(fins.nextLine().split("=")[1]));
				Window.getKeyControls().setSaveKey(Integer.parseInt(fins.nextLine().split("=")[1]));
				Window.getKeyControls().setSelectKey(Integer.parseInt(fins.nextLine().split("=")[1]));
				Window.getKeyControls().setSnapKey(Integer.parseInt(fins.nextLine().split("=")[1]));
				Window.getKeyControls().setThingsKey(Integer.parseInt(fins.nextLine().split("=")[1]));
				Window.getKeyControls().setVertsKey(Integer.parseInt(fins.nextLine().split("=")[1]));
				Window.getKeyControls().setZoomInKey(Integer.parseInt(fins.nextLine().split("=")[1]));
				Window.getKeyControls().setZoomOutKey(Integer.parseInt(fins.nextLine().split("=")[1]));
			} catch (Exception e) {
				System.out.println("legacy config found, loading default controls");
			}
			configLoaded = true;
			fins.close();
		} catch (FileNotFoundException e) {
			configLoaded = false;
		}

		return configLoaded;
	}

	public void readPalette() {
		try {
			RandomAccessFile rin = new RandomAccessFile(config.getLocation(), "rw");
			DataLists.getPaletteAddress();
			rin.seek(DataLists.getPaletteAddress());
			byte blue = 0;
			byte greenRed = 0;

			for (int i = 0; i < 32; i++) {
				blue = rin.readByte();
				greenRed = rin.readByte();

				int r = (greenRed & 0x0000000F) * 0x10;
				int g = (greenRed & 0x000000F0);
				int b = (blue & 0x0000000F) * 0x10;

				DataLists.getLevelPal()[i] = new Color(r, g, b);
			}
//			rin.seek(rin.getFilePointer());
			for (int i = 0; i < 16; i++) {
				blue = rin.readByte();
				greenRed = rin.readByte();

				int r = (greenRed & 0x0000000F) * 0x10;
				int g = (greenRed & 0x000000F0);
				int b = (blue & 0x0000000F) * 0x10;

				DataLists.getObjectPal()[i] = new Color(r, g, b);
			}
			rin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
