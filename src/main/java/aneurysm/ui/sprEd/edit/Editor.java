package aneurysm.ui.sprEd.edit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import aneurysm.io.FileReader;
import aneurysm.offsets.CDOffsets;
import aneurysm.offsets.CartOffsets;
import aneurysm.ui.DataLists;
import aneurysm.ui.Window;
import aneurysm.ui.sprEd.offsets.SpriteDimension;
import aneurysm.ui.sprEd.ui.SpriteCanvas;

public class Editor extends JPanel {

	private JButton next;
	private JButton prev;
	private static final long serialVersionUID = -8635012649987300945L;
	private JLabel coords;
	private int selectedImageIndex;
	private Window host;
	private int colorIndex = 0;
	private JLabel[] colors = new JLabel[16];
	private Color[] pal = new Color[16];
	private SpriteCanvas imagePanel;
	private ArrayList<SpriteDimension> dims = new ArrayList<>();
	private int[] offsets;
	private String fileLocation = FileReader.getConfig().getLocation();
	private Byte[][] currentImage;
	private boolean changesMade;
	private boolean isCartOrCd = DataLists.isCdOrCart();

	public void setCurrentImage(Byte[][] in) {
		currentImage = in;
	}

	public void setChangesMade() {
		changesMade = true;
	}

	private Byte[][] readRomSprite(int offset, int w, int h) {
		Byte[][] out = new Byte[w][h];
		try {
			RandomAccessFile rin = new RandomAccessFile(fileLocation, "rw");
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

	private Byte[][] readCDSprite(int offset, int w, int h) {
		Byte[][] out = new Byte[w][h];
		try {
			RandomAccessFile rin = new RandomAccessFile(fileLocation, "rw");
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

	private Byte[][] readSprite(boolean cartOrCd, int i, boolean forward) {
		Byte[][] out = null;
		if (!cartOrCd)
			out = readRomSprite(CartOffsets.getSpriteOffsets()[i], dims.get(i).getW(), dims.get(i).getH());
		else {
			while (offsets[i] == 0) {
				if (forward) {
					i++;
					selectedImageIndex++;
				} else {
					i--;
					selectedImageIndex--;
				}
			}
			out = readCDSprite(CDOffsets.getSpriteOffsets()[i], dims.get(i).getW(), dims.get(i).getH());
		}
		return out;
	}

	private void writeROMSprite() {
		try {
			RandomAccessFile rin = new RandomAccessFile(fileLocation, "rw");
			rin.seek(offsets[selectedImageIndex]);
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

	private void writeSprite() {
		if (!isCartOrCd) {
			writeROMSprite();
		} else {
			writeCDSprite();
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

	private void writeCDSprite() {
		Byte[][] toWrite = compressImage(currentImage);
		try {
			RandomAccessFile rin = new RandomAccessFile(fileLocation, "rw");
			rin.seek(offsets[selectedImageIndex]);

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

	private int pollToSave() {
		int result = -1;
		if (changesMade) {
			result = JOptionPane.showConfirmDialog(this, "Save Changes?",
					"You have unsaved changes.  Would you like to save them?", JOptionPane.YES_NO_CANCEL_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				changesMade = false;
				writeSprite();
			}
		}
		return result;
	}

	public void getIndexes(boolean cartOrCd) {
		if (!cartOrCd) {
			dims = CartOffsets.getSpriteDimensions();
			offsets = CartOffsets.getSpriteOffsets();
		} else {
			dims = CDOffsets.getSpriteDimensions();

			offsets = CDOffsets.getSpriteOffsets();
		}
	}

	public Editor(Window host) {
		pal = DataLists.getObjectPal();
		this.setLayout(null);
		this.host = host;
		setupComponents();
	}

	private void setupComponents() {
		this.setSize(700, 700);
		coords = new JLabel("0,0");
		coords.setSize(48, 240);
		coords.setLocation(0, 0);
		int dist = 640;
		next = new JButton(">");
		next.setSize(48, 24);
		next.setLocation(630, 64);
		next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (pollToSave() != JOptionPane.CANCEL_OPTION)
					updateEditingImage(selectedImageIndex + 1);

				moveDir = true;
				changesMade = false;
				coords.setText(Integer.toHexString(offsets[selectedImageIndex]));
			}
		});
		prev = new JButton("<");
		prev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pollToSave() != JOptionPane.CANCEL_OPTION)
					updateEditingImage(selectedImageIndex - 1);

				moveDir = false;
				changesMade = false;
				coords.setText(Integer.toHexString(offsets[selectedImageIndex]));
			}
		});
		prev.setSize(48, 24);
		prev.setLocation(580, 64);
		this.add(prev);
		this.add(next);
		for (int i = 0; i < pal.length; i++) {
			colors[i] = new JLabel("0" + Integer.toHexString(i));
			this.add(colors[i]);
			colors[i].setBackground(pal[i]);
			colors[i].setOpaque(true);
			colors[i].setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			colors[i].setSize(new Dimension(24, 24));
			colors[i].setLocation(120 + i * 28, dist);
			int count = i;
			colors[i].addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent me) {
					colorIndex = count;
					imagePanel.setColor(colorIndex);
				}
			});
		}

		getIndexes(isCartOrCd);
		selectedImageIndex = 0;

		Thread main = new Thread(new Runner());
		main.setDaemon(true);
		main.start();
		imagePanel = new SpriteCanvas(this, coords);
		imagePanel.setPal(pal);
		this.add(coords);
		this.add(imagePanel);
		updateEditingImage(selectedImageIndex);
	}

	private boolean moveDir;

	private void updateEditingImage(int num) {
		if (num > offsets.length - 1)
			num = 0;
		if (num < 0)
			num = offsets.length - 1;
		selectedImageIndex = num;
		imagePanel.setImage(readSprite(isCartOrCd, num, moveDir));
	}

	public void setCartOrCd(boolean c) {
		this.isCartOrCd = c;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.red);
		g.drawRect(colors[colorIndex].getX() - 1, colors[colorIndex].getY() - 1, colors[colorIndex].getWidth() + 2,
				colors[colorIndex].getHeight() + 2);
		g.drawRect(colors[colorIndex].getX() - 2, colors[colorIndex].getY() - 2, colors[colorIndex].getWidth() + 3,
				colors[colorIndex].getHeight() + 3);
	}

	private class Runner implements Runnable {
		@Override
		public void run() {

			while (true) {
				try {
					Thread.sleep(35);

				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
				repaint();
			}

		}
	}
}
