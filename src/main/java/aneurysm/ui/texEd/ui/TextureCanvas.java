package aneurysm.ui.texEd.ui;

import aneurysm.ui.DataLists;
import aneurysm.ui.texEd.edit.TextureEditor;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;


public class TextureCanvas extends JPanel implements MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    private final TextureEditor host;
    private Byte[][] currentTexture;
    private final byte imgScale = 4;
    private int selectedColorIndex;
    private Color[] pal;
    private int xPos = 0, yPos = 0;
    private Point mp;
    private final JLabel c;


    public void setPal(Color[] pal) {
        this.pal = pal;
    }

    public void setImage(Byte[][] im) {
        currentTexture = im;
        resize();
    }

    public void sendImageToHost() {
        host.setCurrentImage(currentTexture);
    }

    private BufferedImage renderImage() {
        BufferedImage im = new BufferedImage(currentTexture.length * imgScale, (currentTexture[0].length * 2) * imgScale,
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = im.getGraphics();
        for (int i = 0; i < currentTexture.length; i++) {
            for (int j = 0; j < currentTexture[0].length; j++) {
                if(!DataLists.isCdOrCart()) {
                    g.setColor(pal[((currentTexture[i][j] >> 4) & 0x0000000F) + 16]);
                    g.fillRect(i * imgScale, (64 + j) * imgScale, imgScale, imgScale);

                    g.setColor(pal[((currentTexture[i][j] >> 4) & 0x0000000F)]);
                    g.fillRect(i * imgScale, (63 - j) * imgScale, imgScale, imgScale);
                } else {
                    g.setColor(pal[(currentTexture[i][j]) + 16]);
                    g.fillRect(i * imgScale, (64 + j) * imgScale, imgScale, imgScale);

                    g.setColor(pal[currentTexture[i][j]]);
                    g.fillRect(i * imgScale, (63 - j) * imgScale, imgScale, imgScale);
                }
            }
        }

        drawGrid(im);

        return im;
    }

    private void drawGrid(BufferedImage im) {
        Graphics g = im.getGraphics();
        Color grid = new Color(255, 255, 255, 64);

        for (int i = 0; i < im.getWidth() / imgScale; i++) {

            g.setColor(grid);
            g.drawLine(i * imgScale, 0, i * imgScale, im.getHeight());
        }
        for (int i = 0; i < im.getHeight() / imgScale; i++) {
            g.setColor(grid);
            g.drawLine(0, i * imgScale, im.getWidth(), i * imgScale);
        }
    }

    public void resize() {
        this.setSize(currentTexture.length * imgScale, (currentTexture[0].length * 2) * imgScale);
        this.setLocation(host.getWidth() / 2 - this.getWidth() / 2, host.getHeight() / 2 - this.getHeight() / 2);
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

            currentTexture[xPos][yPos] = (byte) selectedColorIndex;
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
        System.out.println("selected color: " + col);
        selectedColorIndex = col;
    }

    public TextureCanvas(TextureEditor host, JLabel c) {

        this.c = c;
        this.host = host;
        this.setSize(0, 0);
        this.setLocation(host.getWidth() / 2 - this.getWidth() / 2, host.getHeight() / 2 - this.getHeight() / 2);
        addMouseListener(this);
        addMouseMotionListener(this);
        System.out.println(this.getLocation());
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        currentTexture[xPos][yPos] = (byte) selectedColorIndex;
        mp = e.getPoint();

        if (mp.getX() > 0)
            xPos = (int) (mp.getX() / 4);
        if (mp.getY() > 0 && mp.getY() < this.getHeight() / 2)
            yPos = 63 - (int) (mp.getY() / 4);
        if (mp.getY() >= this.getHeight() / 2 && mp.getY() < this.getHeight())
            yPos = (int) (mp.getY() / 4) - 64;
        if (xPos > currentTexture.length - 1)
            xPos = currentTexture.length - 1;
        if (yPos > currentTexture[0].length - 1)
            yPos = currentTexture[0].length - 1;
        c.setText(xPos + "," + yPos);
        repaint();
        sendImageToHost();
        host.setChangesMade();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mp = e.getPoint();

        if (mp.getX() > 0)
            xPos = (int) (mp.getX() / 4);
        if (mp.getY() > 0 && mp.getY() < this.getHeight() / 2)
            yPos = 63 - (int) (mp.getY() / 4);
        if (mp.getY() >= this.getHeight() / 2 && mp.getY() < this.getHeight())
            yPos = (int) (mp.getY() / 4) - 64;

        if (xPos > currentTexture.length - 1)
            xPos = currentTexture.length - 1;
        if (yPos > currentTexture[0].length - 1)
            yPos = currentTexture[0].length - 1;
        c.setText(xPos + "," + yPos);

    }
}
