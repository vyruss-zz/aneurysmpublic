package aneurysm.patch.enemyimporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import aneurysm.io.FileReader;
import aneurysm.ui.DataLists;

public class EnemyImporter {

	private String romFileLocation = FileReader.getConfig().getLocation();
	private String cdDataLocation;

	private byte[] idHeader = new byte[] { 0x00, 0x20, 0x36, 0x46, 0x00, 0x0a };

	private short[][] frameHeaders = new short[][] {
			{ 0x00, 0x70, 0x00, 0x7c, 0x00, 0x38, 0x00, 0x6e, 0x00, 0x20, 0x00, 0x06 },
			{ 0x00, 0x70, 0x00, 0x79, 0x00, 0x38, 0x00, 0x6B, 0x00, 0x20, 0x36, 0x52 },
			{ 0x00, 0x75, 0x00, 0x8f, 0x00, 0x3a, 0x00, 0x81, 0x00, 0x20, 0x6b, 0x4e },
			{ 0x00, 0x75, 0x00, 0x8f, 0x00, 0x3a, 0x00, 0x81, 0x00, 0x20, 0xac, 0xb6 },
			{ 0x00, 0x72, 0x00, 0x85, 0x00, 0x37, 0x00, 0x77, 0x00, 0x20, 0xee, 0x1e },
			{ 0x00, 0x80, 0x00, 0x8f, 0x00, 0x39, 0x00, 0x7e, 0x00, 0x21, 0x29, 0x64 },
			{ 0x00, 0x86, 0x00, 0x75, 0x00, 0x3b, 0x00, 0x64, 0x00, 0x21, 0x70, 0xf0 },
			{ 0x00, 0x8b, 0x00, 0x4d, 0x00, 0x3d, 0x00, 0x3d, 0x00, 0x21, 0xae, 0x3a },
			{ 0x00, 0x8d, 0x00, 0x39, 0x00, 0x3d, 0x00, 0x27, 0x00, 0x21, 0xd8, 0x16 } };

	private short[] frameTable = new short[] { 0x00, 0x20, 0x36, 0x46, 0x00, 0x20, 0x36, 0x46, 0x00, 0x20, 0x6B, 0x42,
			0x00, 0x20, 0x6B, 0x42, 0xFF, 0xFF, 0xFF, 0xFF, 0x00, 0x21, 0xF7, 0x88, 0x00, 0x20, 0xAC, 0xAA, 0x00, 0x20,
			0xAC, 0xAA, 0x00, 0x20, 0xEE, 0x12, 0x00, 0x20, 0xEE, 0x12, 0xFF, 0xFF, 0xFF, 0xFF, 0x00, 0x21, 0xF7, 0x88,
			0x00, 0x21, 0x29, 0x58, 0x00, 0x21, 0x70, 0xE4, 0x00, 0x21, 0xAE, 0x2E, 0x00, 0x21, 0xD8, 0x0A, 0x00, 0x21,
			0xF7, 0x7C, 0x00, 0x00, 0x00, 0x00 };

	private short[] attribs = new short[] { 0x00, 0x64, 0x00, 0x3C, 0x00, 0x01, 0x0C, 0x4A, 0x00, 0x19, 0x00, 0x05,
			0x00, 0x96, 0x00, 0xB4, 0x00, 0x2D, 0x00, 0x64, 0x00, 0x19, 0x00, 0x1E, 0xFF, 0xCE, 0x00, 0x11, 0x00, 0x10,
			0x00, 0x19, 0x00, 0x4C, 0x00, 0xC8, 0x00, 0x0A, 0x00, 0x0C, 0x02, 0x30, 0x00, 0x21, 0xF7, 0x88, 0x00, 0x21,
			0xF7, 0x88, 0x00, 0x21, 0xF7, 0xA0, 0x00, 0x21, 0xF7, 0xB8 };

	private short[][] lengths = new short[][] { { 0x70, 0x7c }, { 0x70, 0x79 }, { 0x75, 0x8f }, { 0x75, 0x8f },
			{ 0x72, 0x85 }, { 0x80, 0x8f }, { 0x86, 0x75 }, { 0x8b, 0x4d }, { 0x8d, 0x39 } };

	private int[] offsets = new int[] { 0x038154, 0x039c74, 0x03b6ec, 0x03d7e2, 0x03f8d8, 0x041676, 0x043a36, 0x0458d6,
			0x046de4 };
	private byte[] frame;

	public boolean kickoff() {
		String[] opts = new String[] { "Yes", "No" };
		int choice = JOptionPane.showOptionDialog(null,
				"Would you like to import the cut enemy? PLEASE SAVE ANY CHANGES BEFORE DOING THIS!",
				"CD Enemy Import Tool", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opts, opts[0]);
		System.out.println("choice: " + choice);
		if (choice == 1)
			return false;
		else {
			performPatch();
			return true;
		}
	}

	private void performPatch() {
		boolean keepGoing = true;
		if (validatePatchNeeded()) {
			keepGoing = doCDValidate();
			if (!keepGoing)
				return;
			writeCheckAndAttributes();
			writeEnemyGraphicsAndHeaders();
			writeFrameTableAndSignal();
			DataLists.setEnemyPatchedIn(true);
			JOptionPane.showMessageDialog(null, "Patch complete.");
		}
	}

	private void writeCheckAndAttributes() {
		try {
			RandomAccessFile rin = new RandomAccessFile(romFileLocation, "rw");
			rin.seek(0x107ca);
			rin.writeInt(0x003FFFFF);
			rin.seek(0x65C8);
			for (int i = 0; i < attribs.length; i++) {
				rin.writeByte(attribs[i]);
			}
			rin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeFrameTableAndSignal() {
		try {
			RandomAccessFile rof = new RandomAccessFile(romFileLocation, "rw");
			rof.seek(0x67D2);
			rof.writeInt(0x0EA7F00D);
			rof.seek(rof.length());
			for (int i = 0; i < frameTable.length; i++)
				rof.writeByte(frameTable[i]);

			rof.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeEnemyGraphicsAndHeaders() {
		try {
			RandomAccessFile rof = new RandomAccessFile(romFileLocation, "rw");
			rof.seek(rof.length());
			for (int i = 0; i < idHeader.length; i++)
				rof.writeByte(idHeader[i]);

			for (int i = 0; i < lengths.length; i++) {
				rof.seek(rof.length());
				frame = decodeFrame(lengths[i][0], lengths[i][1], offsets[i], i);
				for (int x = 0; x < frame.length; x++) {
					rof.writeByte(frame[x]);
				}
				for (int j = 0; j < frameHeaders[i].length; j++)
					rof.writeByte(frameHeaders[i][j]);
			}

			rof.close();
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}

	}

	private byte[] decodeFrame(int x, int y, int offset, int index) {
		byte[][] sprOut = null;
		try {
			RandomAccessFile rin = new RandomAccessFile(cdDataLocation + "LEVEL3.LEV", "r");
			rin.seek(offset);
			sprOut = new byte[y][x];
			for (int j = 0; j < x; j += 2) {
				for (int i = 0; i < y; i++) {
					byte read = (rin.readByte());
					byte higher;
					byte lower;

					if (j == x - 1) {
						sprOut[i][j] = (byte) ((read & 0x000000F0) / 16);

					} else {
						if (((read & 0xF0)) != (read & 0x0F) * 0x10) {

							if ((read & 0xF0) > ((read & 0x0F) * 16)) {
								lower = (byte) ((read << 4) + (read & 0x0F));
								higher = (byte) ((read & 0xF0) + ((read & 0xF0) / 0x10));

								sprOut[i][j] = (byte) ((higher & 0x000000F0) / 16);
								sprOut[i][j + 1] = (byte) ((lower & 0x000000F0) / 16);

							} else {
								higher = (byte) ((read << 4) + (read & 0x0F));
								lower = (byte) ((read & 0xF0) + ((read & 0xF0) / 0x10));

								sprOut[i][j + 1] = (byte) ((higher & 0x000000F0) / 16);
								sprOut[i][j] = (byte) ((lower & 0x000000F0) / 16);
							}

						} else {

							sprOut[i][j] = (byte) ((read & 0x000000F0) / 16);
							sprOut[i][j + 1] = (byte) ((read & 0x000000F0) / 16);
						}
					}
				}
			}
			rin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return arraysToLinear(sprOut, index);
	}

	private byte[] arraysToLinear(byte[][] in, int index) {
		byte[] out = null;
		if (index == 2 || index == 3 || index == 7 || index == 8)
			out = new byte[(in.length * in[0].length) + 1];
		else
			out = new byte[(lengths[index][0] * lengths[index][1])];

		int outIndex = 0;
		for (int i = 0; i < in[0].length; i++) {
			for (int j = 0; j < in.length; j++) {
				out[outIndex++] = in[j][i];
			}
		}
		return out;
	}

	private boolean validatePatchNeeded() {
		try {
			RandomAccessFile rin = new RandomAccessFile(romFileLocation, "r");
			rin.seek(0x67D2);
			if (rin.readInt() == 0x0EA7F00D) {
				JOptionPane.showMessageDialog(null, "This ROM has already been patched.");
				rin.close();
				return false;
			} else {
				rin.close();
				return true;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false; // we should never reach this. if we do, blame rob
	}

	private boolean doCDValidate() {
		boolean found = false;
		while (!found) {
			JFileChooser f = new JFileChooser();
			f.setCurrentDirectory(new File("."));
			f.setDialogTitle("Please Locate Mega CD version Root");
			f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			f.setAcceptAllFileFilterUsed(false);
			if (f.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				System.out.println(f.getSelectedFile());
				File[] list = f.getSelectedFile().listFiles();
				if (list.length != 23) {
					found = false;
					JOptionPane.showMessageDialog(null, "Invalid location selected.");
				} else {
					int total = 0;
					for (int i = 0; i < list.length; i++) {
						total += list[i].length();
					}
					if (total == 6193035) {
						found = true;
						if (!f.getSelectedFile().toString().endsWith("\\"))
							cdDataLocation = f.getSelectedFile().toString() + "\\";
						else
							cdDataLocation = f.getSelectedFile().toString();
					} else {
						found = false;
						JOptionPane.showMessageDialog(null, "Invalid location selected.");
					}
				}

			} else {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		EnemyImporter im = new EnemyImporter();
		im.kickoff();
	}
}