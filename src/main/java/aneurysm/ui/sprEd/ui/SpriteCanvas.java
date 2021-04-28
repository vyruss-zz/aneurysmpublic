package aneurysm.ui.sprEd.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;

import aneurysm.ui.sprEd.edit.Editor;


public class SpriteCanvas extends JPanel implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	private Editor host;
	private Byte[][] currentSprite;
	private byte spriteScale = 4;
	private int selectedColorIndex;
	private Color[] pal;

	public void setPal(Color[] pal) {
		this.pal = pal;
	}

	public void setImage(Byte[][] im) {
		currentSprite = im;
		resize();
	}

	public void sendImageToHost() {
		host.setCurrentImage(currentSprite);
	}
	
	private BufferedImage renderImage() {
		BufferedImage im = new BufferedImage(currentSprite.length * spriteScale, currentSprite[0].length * spriteScale,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = im.getGraphics();
		for (int i = 0; i < im.getWidth() / spriteScale; i++) {
			for (int j = 0; j < im.getHeight() / spriteScale; j++) {
				g.setColor(pal[currentSprite[i][j] & 0x0000000F]);
				g.fillRect(i * spriteScale, j * spriteScale, spriteScale, spriteScale);
			}
		}

		drawGrid(im);

		return im;
	}

	private void drawGrid(BufferedImage im) {
		Graphics g = im.getGraphics();
		Color grid = new Color(255, 255, 255, 64);

		for (int i = 0; i < im.getWidth() / spriteScale; i++) {

			g.setColor(grid);
			g.drawLine(i * spriteScale, 0, i * spriteScale, im.getHeight());
		}
		for (int i = 0; i < im.getHeight() / spriteScale; i++) {
			g.setColor(grid);
			g.drawLine(0, i * spriteScale, im.getWidth(), i * spriteScale);
		}
	}

	public void resize() {
		this.setSize(currentSprite.length * spriteScale, currentSprite[0].length * spriteScale);
		this.setLocation(host.getWidth()/2 - this.getWidth()/2, host.getHeight()/2 - this.getHeight()/2);
		repaint();
		System.out.println(this.getLocation());
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(renderImage(), 0, 0, this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getX() >= this.getX() && e.getX() <= this.getX() + this.getWidth() && e.getY() >= this.getY()
				&& e.getY() <= this.getY() + this.getHeight()) {
			currentSprite[xPos][yPos] = (byte) selectedColorIndex;
			host.setChangesMade();
			sendImageToHost();
			repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {


	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {


	}

	public void setColor(int col) {
		selectedColorIndex = col;
	}

	private JLabel c;

	public SpriteCanvas(Editor host, JLabel c) {

		this.c = c;
		this.host = host;
		this.setSize(0, 0);
		this.setLocation(host.getWidth()/2 - this.getWidth()/2, host.getHeight()/2 - this.getHeight()/2);
		addMouseListener(this);
		addMouseMotionListener(this);
		System.out.println(this.getLocation());
	}

	@Override
	public void mouseDragged(MouseEvent e) {

			currentSprite[xPos][yPos] = (byte) selectedColorIndex;
			mp = e.getPoint();

			if (mp.getX() > 0)
				xPos = (int) (mp.getX() / 4);
			if (mp.getY() > 0)
				yPos = (int) (mp.getY() / 4);
			if (xPos > currentSprite.length - 1)
				xPos = currentSprite.length - 1;
			if (yPos > currentSprite[0].length - 1)
				yPos = currentSprite[0].length - 1;
			c.setText(xPos + "," + yPos);
			repaint();
			sendImageToHost();
			host.setChangesMade();
	}

	private int xPos = 0, yPos = 0;
	private Point mp;

	@Override
	public void mouseMoved(MouseEvent e) {
			mp = e.getPoint();

			if (mp.getX() > 0)
				xPos = (int) (mp.getX() / 4);
			if (mp.getY() > 0)
				yPos = (int) (mp.getY() / 4);
			if (xPos > currentSprite.length - 1)
				xPos = currentSprite.length - 1;
			if (yPos > currentSprite[0].length - 1)
				yPos = currentSprite[0].length - 1;
			c.setText(xPos + "," + yPos);

	}
}
