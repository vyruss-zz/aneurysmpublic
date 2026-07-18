package aneurysm.ui.tileMapEd.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import aneurysm.ui.tileMapEd.tileMapEditor;


public class TileMapCanvas extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
    //
//    private static final long serialVersionUID = 1L;
    private final tileMapEditor host;
    private boolean needsUpdate = true;
    private int hoverX = -1, hoverY = -1;
    private int hoverIndex = -1;

    public TileMapCanvas(tileMapEditor host) {

        this.host = host;
        this.setSize(512, 448);
        this.setLocation(host.getWidth() / 2 - this.getWidth() / 2, host.getHeight() / 2 - this.getHeight() / 2);
        addMouseListener(this);
        addMouseMotionListener(this);
        System.out.println(this.getLocation());
    }


    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(renderImage(), 0, 0, this);
    }

    private BufferedImage renderImage() {
        BufferedImage im = new BufferedImage(this.getWidth(), this.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        BufferedImage theTile;
        AffineTransform tx;
        AffineTransformOp op;
        Graphics g = im.getGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        int currX = 0, currY = 0, xScale = 0, yScale = 0;
        short currTile = 0;
        for (int i = 0; i < host.getTilemap().length; i++) {

            xScale = 2;
            yScale = 2;
            currTile = host.getTilemap()[i];
            theTile = host.getTiles()[currTile & 0x7ff];

            if ((currTile & 0x1000) != 0) {
                yScale *= -1;
            }
            if ((currTile & 0x0800) != 0) {
                xScale *= -1;
            }
            tx = AffineTransform.getScaleInstance(xScale, yScale);


            if ((currTile & 0x1000) != 0) {
                // Flip Y by translating vertically
                tx.translate(0, -theTile.getHeight());
            }
            if ((currTile & 0x0800) != 0) {
                // Flip X by translating horizontally
                tx.translate(-theTile.getWidth(), 0);
            }

            op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            theTile = op.filter(theTile, null);
            g.drawImage(theTile, currX, currY, this);
            if (host.getTileStateManager().getHighlightedTileIndex() == (host.getTilemap()[i] & 0x7ff)) {
                g.setColor(new Color(0, 0, 128, 96));
                g.fillRect(currX, currY, 16, 16);
            }

            currX += 16;
            if (currX >= this.getWidth()) {
                currX = 0;
                currY += 16;
            }

        }
        drawGrid(im);
        if (hoverX >= 0 && hoverY >= 0) {
            g.setColor(new Color(128, 128, 0, 100));
            g.fillRect(hoverX * 16, hoverY * 16, 16, 16);
        }

        needsUpdate = false;

        return im;
    }

    private void drawGrid(BufferedImage im) {
        Graphics g = im.getGraphics();
        Color grid = new Color(255, 255, 255, 64);

        for (int i = 0; i < this.getWidth(); i += 16) {

            g.setColor(grid);
            g.drawLine(i, 0, i, im.getHeight());
        }
        for (int i = 0; i < this.getHeight(); i += 16) {
            g.setColor(grid);
            g.drawLine(0, i, this.getWidth(), i);
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        hoverX = -1;
        hoverY = -1;
        hoverIndex = -1;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int tileIndex = 0;
        hoverX = e.getPoint().x / 16;
        hoverY = e.getPoint().y / 16;
        tileIndex = host.getTilemap()[(((e.getPoint().y / 16) * this.getWidth() / 16) + (e.getPoint().x / 16))];
        hoverIndex = ((e.getPoint().y / 16) * this.getWidth() / 16) + (e.getPoint().x / 16);
        host.getTileStateManager().setFlipState(tileIndex & 0x1800);
        host.updateTileListHighlight(tileIndex & 0x7ff);
        host.updateTileInfoComponents(tileIndex);
//        System.out.printf("flipState: %08x tileIndex: %08x\n",host.getTileStateManager().getFlipState(), tileIndex);

    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.requestFocus();
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (hoverIndex >= 0) {
                host.getTileStateManager().setSelectedTileListIndex(host.getTilemap()[hoverIndex] & 0x7ff);
                host.getTileStateManager().setFlipState(host.getTilemap()[hoverIndex & 0x1800]);
//                System.out.println("tileMapIndex " + host.getTileStateManager().getSelectedTileMapIndex());
            }
        }
        if (e.getButton() == MouseEvent.BUTTON2) {
//            System.out.println("mouse2");
            if (hoverIndex >= 0 && host.getTileStateManager().getSelectedTileMapIndex() < host.getTilemap().length) {
                host.getTileStateManager().incrementFlipState();
                host.getTileStateManager().setSelectedTileMapIndex(host.getTilemap()[hoverIndex] & 0x7ff);
                host.getTilemap()[hoverIndex] = (short) (host.getTileStateManager().getSelectedTileMapIndex() | host.getTileStateManager().getFlipState());
            }
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
//            System.out.printf("mouse3 tileListIndex %08x", host.getTileStateManager().getSelectedTileListIndex());
            if (hoverIndex >= 0 && host.getTileStateManager().getSelectedTileMapIndex() < host.getTilemap().length) {
                host.getTilemap()[hoverIndex] = (short) (host.getTileStateManager().getSelectedTileListIndex() | host.getTileStateManager().getFlipState());
                host.setChangesMade();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {


    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }


    @Override
    public void mouseEntered(MouseEvent e) {


    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {


    }
}
