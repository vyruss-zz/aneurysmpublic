package aneurysm.ui.palEd;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import aneurysm.io.FileReader;
import aneurysm.ui.DataLists;
import aneurysm.ui.Window;

public class PaletteEditor extends JPanel {

	private JButton save, refresh;
	private int address = 0;
	private static final long serialVersionUID = -8635012649987300945L;
	private int colorIndex = 0;
	private JLabel[] colors = new JLabel[64];
	private JLabel[] selectorRed = new JLabel[8];
	private JLabel[] selectorGreen = new JLabel[8];
	private JLabel[] selectorBlue = new JLabel[8];
	private byte selectedBlue, selectedGR;
	private byte[] cols = new byte[128];
	private Color[] pal = new Color[64];
	private String fileLocation = FileReader.getConfig().getLocation();

	public PaletteEditor(int address) {
		this.setLayout(null);
		this.address = address;
		setupComponents();
	}

	private void addSaveButton() {
		save = new JButton("Save");
		save.setEnabled(false);
		save.setSize(64, 28);
		save.setLocation(410, 135);
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				writePalette();
				save.setEnabled(false);
				refresh.setEnabled(true);
			}
		});
		this.add(save);
	}

	private void addRefreshButton() {
		refresh = new JButton("Refresh GFX");
		refresh.setEnabled(false);
		refresh.setSize(96,28);
		refresh.setLocation(306,135);
		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DataLists.setupColors();
				Window.getReader().readWallGFX(true, FileReader.getConfig().getLocation());
				refresh.setEnabled(false);
			}
		});
		this.add(refresh);
	}
	
	private int redSelX, redSelY, blueSelX, blueSelY, greenSelX, greenSelY;

	private void addSelectors() {
		int startX = 24;
		int startY = colors[colors.length - 1].getY() + 32;
		for (int i = 0; i < selectorRed.length; i++) {
			int colIndex = 0x20 * i;
			int in = i;
			selectorRed[i] = new JLabel();
			selectorGreen[i] = new JLabel();
			selectorBlue[i] = new JLabel();

			selectorRed[i].setOpaque(true);
			selectorGreen[i].setOpaque(true);
			selectorBlue[i].setOpaque(true);

			selectorRed[i].setSize(30, 30);
			selectorGreen[i].setSize(30, 30);
			selectorBlue[i].setSize(30, 30);

			selectorRed[i].setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			selectorGreen[i].setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			selectorBlue[i].setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			
			selectorRed[i].setLocation(startX + (35 * i) , startY);
			selectorGreen[i].setLocation(startX + (35 * i), startY + 35);
			selectorBlue[i].setLocation(startX + (35 * i) , startY + 70);

			selectorRed[i].setBackground(new Color(0x20 * i, 0, 0));
			selectorGreen[i].setBackground(new Color(0, 0x20 * i, 0));
			selectorBlue[i].setBackground(new Color(0, 0, 0x20 * i));

			selectorRed[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					selectedGR = (byte) (selectedGR & 0x000000F0);
					System.out.println("selectedGR " + Integer.toHexString(selectedGR));
					System.out.println("(byte) (colIndex / 0x10) " + Integer.toHexString((byte) (colIndex / 0x10)));
					selectedGR += (byte) (colIndex / 0x10);
					System.out.println("selectedGR " + Integer.toHexString(selectedGR));
					redSelX = selectorRed[in].getX();
					redSelY = selectorRed[in].getY();
					cols[(2 * colorIndex) + 1] = selectedGR;
					updateSelectedColor();
					save.setEnabled(true);
				}
			});
			selectorGreen[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					System.out.println("selectedGR " + Integer.toHexString(selectedGR));
					selectedGR = (byte) (selectedGR & 0x0000000F);
					System.out.println("selectedGR " + Integer.toHexString(selectedGR));
					selectedGR += colIndex;
					System.out.println("selectedGR " + Integer.toHexString(selectedGR));
					greenSelX = selectorGreen[in].getX();
					greenSelY = selectorGreen[in].getY();
					cols[(2 * colorIndex) + 1] = selectedGR;
					updateSelectedColor();
					save.setEnabled(true);
				}
			});
			selectorBlue[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					selectedBlue = (byte) (colIndex / 0x10);
					System.out.println("selectedBlue " + selectedBlue);
					blueSelX = selectorBlue[in].getX();
					blueSelY = selectorBlue[in].getY();
					cols[2 * colorIndex] = selectedBlue;
					updateSelectedColor();
					save.setEnabled(true);
				}
			});

			this.add(selectorRed[i]);
			this.add(selectorGreen[i]);
			this.add(selectorBlue[i]);
		}
	}
	
	private void setSelectedColors(int b, int g, int r) {
		redSelX = selectorRed[r/0x20].getX();
		redSelY = selectorRed[r/0x20].getY();
		greenSelX = selectorGreen[g/0x20].getX();
		greenSelY = selectorGreen[g/0x20].getY();
		blueSelX = selectorBlue[b/0x20].getX();
		blueSelY = selectorBlue[b/0x20].getY();

	}

	private void writePalette() {
		try {
			RandomAccessFile rin = new RandomAccessFile(fileLocation, "rw");
			rin.seek(address);
			for (int i = 0; i < cols.length; i++) {
				rin.writeByte(cols[i]);
			}
			rin.close();
			Window.getReader().readPalette();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void updateSelectedColor() {
		int b = (selectedBlue & 0x0000000F) * 0x10;
		int g = selectedGR & 0x000000F0;
		int r = (selectedGR & 0x0000000F) * 0x10;
		pal[colorIndex] = new Color(r, g, b);
		colors[colorIndex].setBackground(pal[colorIndex]);
	}
	
	private void addPaletteButtons() {
		int acc = 0;
		int x = 0;
		int dist = 16;
		for (int i = 0; i < pal.length; i++) {
			colors[i] = new JLabel("  " + Integer.toHexString(i));
			this.add(colors[i]);
			colors[i].setBackground(pal[i]);
			colors[i].setOpaque(true);
			colors[i].setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			colors[i].setSize(new Dimension(24, 24));
			colors[i].setLocation(24 + (x * 28), dist);
			int count = i;
			acc++;
			x++;
			if (acc == 16) {
				acc = 0;
				dist += 30;
				x = 0;
			}
			colors[i].addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent me) {
					colorIndex = count;
					selectedBlue = cols[2*colorIndex];
					selectedGR = cols[(2*colorIndex)+1];

					setSelectedColors((selectedBlue & 0x0000000F) * 0x10,selectedGR & 0x000000F0,(selectedGR & 0x0000000F) * 0x10);
				}
			});
		}
	}

	private void readPalette() {
		try {
			RandomAccessFile rin = new RandomAccessFile(fileLocation, "rw");
			rin.seek(address);
			for (int i = 0; i < cols.length; i++) {
				cols[i] = rin.readByte();
			}
			rin.close();
			for (int i = 0; i < pal.length; i++) {
				int b = (cols[2 * i] & 0x0000000F) * 0x10;
				int g = cols[(2 * i) + 1] & 0x000000F0;
				int r = (cols[(2 * i) + 1] & 0x0000000F) * 0x10;
				pal[i] = new Color(r, g, b);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setupComponents() {
		this.setSize(700, 700);
		readPalette();
		addSaveButton();
		addRefreshButton();
		addPaletteButtons();
		addSelectors();
		selectedBlue = cols[2*colorIndex];
		selectedGR = cols[(2*colorIndex)+1];

		setSelectedColors((selectedBlue & 0x0000000F) * 0x10,selectedGR & 0x000000F0,(selectedGR & 0x0000000F) * 0x10);
		Thread main = new Thread(new Runner());
		main.setDaemon(true);
		main.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.red);
		g.drawRect(colors[colorIndex].getX() - 1, colors[colorIndex].getY() - 1, colors[colorIndex].getWidth() + 2,
				colors[colorIndex].getHeight() + 2);
		g.drawRect(colors[colorIndex].getX() - 2, colors[colorIndex].getY() - 2, colors[colorIndex].getWidth() + 3,
				colors[colorIndex].getHeight() + 3);

		g.setColor(Color.DARK_GRAY);
		g.fillRect(selectorRed[0].getX()-5, selectorRed[0].getY()-5, 285, 110);
		
		g.setColor(Color.black);
		if (redSelX != 0) {
			g.setColor(Color.green);
			g.drawRect(redSelX - 1, redSelY - 1, 33, 33);
			g.drawRect(redSelX - 2, redSelY - 2, 34, 34);
			g.setColor(new Color(247,108,0));
			g.drawRect(blueSelX - 1, blueSelY - 1, 33, 33);
			g.drawRect(blueSelX - 2, blueSelY - 2, 34, 34);
			g.setColor(Color.red);
			g.drawRect(greenSelX - 1, greenSelY - 1, 33, 33);
			g.drawRect(greenSelX - 2, greenSelY - 2, 34, 34);
		}
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
