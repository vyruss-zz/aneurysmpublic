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

public class SpriteEditor extends JPanel {

	private JButton next;
	private JButton prev;
	private static final long serialVersionUID = -8635012649987300945L;
	private JLabel coords;
	private int selectedImageIndex;
	private final Window host;
	private int colorIndex = 0;
	private final JLabel[] colors = new JLabel[16];
	private Color[] pal;
	private SpriteCanvas imagePanel;
	private ArrayList<SpriteDimension> dims = new ArrayList<>();
	private int[] offsets;
	private Byte[][] currentImage;
	private boolean changesMade;
	private boolean moveDir;


	public void setCurrentImage(Byte[][] in) {
		currentImage = in;
	}

	public void setChangesMade() {
		changesMade = true;
	}

	private Byte[][] readSprite(int i, boolean forward) {
		Byte[][] out;
		if (!DataLists.isCdOrCart())
			out = Window.getReader().readSprite(CartOffsets.getSpriteOffsets()[i], dims.get(i).getW(), dims.get(i).getH());
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
			out = Window.getReader().readCDSprite(CDOffsets.getSpriteOffsets()[i], dims.get(i).getW(), dims.get(i).getH(), Window.getReader().getConfig().getLocation());
		}
		return out;
	}


	private void writeSprite() {
		if (!DataLists.isCdOrCart()) {
			Window.getReader().writeROMSprite(offsets[selectedImageIndex], currentImage);
		} else {
			Window.getReader().writeCDSprite(offsets[selectedImageIndex], currentImage);
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

	public void getIndexes() {
		if (!DataLists.isCdOrCart()) {
			dims = CartOffsets.getSpriteDimensions();
			offsets = CartOffsets.getSpriteOffsets();
		} else {
			dims = CDOffsets.getSpriteDimensions();

			offsets = CDOffsets.getSpriteOffsets();
		}
	}

	public SpriteEditor(Window host) {
		pal = DataLists.getLevelPal();
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
		for (int i = 0; i < 16; i++) {
			colors[i] = new JLabel("0" + Integer.toHexString(i));
			this.add(colors[i]);
			colors[i].setBackground(pal[i+32]);
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

		getIndexes();
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

	private void updateEditingImage(int num) {
		if (num > offsets.length - 1)
			num = 0;
		if (num < 0)
			num = offsets.length - 1;
		selectedImageIndex = num;
		imagePanel.setImage(readSprite(num, moveDir));
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
