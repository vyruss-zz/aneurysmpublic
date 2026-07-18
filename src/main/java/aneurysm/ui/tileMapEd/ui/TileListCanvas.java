package aneurysm.ui.tileMapEd.ui;

import aneurysm.ui.tileMapEd.tileMapEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class TileListCanvas extends JPanel implements MouseListener, MouseMotionListener {
    private final tileMapEditor host;
    private int hoverX = -1;
    private int hoverY = -1;
    private int hoverIndex = -1;
    private int selectedIndex = -1;

    public TileListCanvas(tileMapEditor host) {

        this.host = host;
        this.setSize(512, 448);
        this.setLocation(host.getWidth() / 2 - this.getWidth() / 2, host.getHeight() / 2 - this.getHeight() / 2);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void setHover(int tileIndex) {
        // Calculate number of tiles per row based on display width
        int tilesPerRow = this.getWidth() / 16;

        // Convert linear index to grid coordinates
        hoverX = tileIndex % tilesPerRow;
        hoverY = tileIndex / tilesPerRow;
        hoverIndex = tileIndex;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int currX = 0, currY = 0;
        BufferedImage theTile;
        AffineTransform tx;
        AffineTransformOp op;
        g.setColor(new Color(200, 200, 200));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        for (int i = 0; i < host.getTiles().length; i++) {

            theTile = host.getTiles()[i];

            tx = AffineTransform.getScaleInstance(2, 2);

            op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            theTile = op.filter(theTile, null);
            g.drawImage(theTile, currX, currY, this);
            if (selectedIndex == i) {
                g.setColor(new Color(64, 255, 128, 96));
                g.fillRect(currX, currY, 16, 16);
            }
            currX += 16;

            if (currX >= this.getWidth()) {
                currX = 0;
                currY += 16;
            }

        }
        if (hoverIndex >= 0) {
            g.setColor(new Color(128, 128, 0, 100));
            g.fillRect(hoverX * 16, hoverY * 16, 16, 16);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            selectedIndex = hoverIndex;
            if (hoverIndex >= 0) {
                host.getTileStateManager().setSelectedTileListIndex(hoverIndex);
            }
//            System.out.printf("hoverIndex: %08x selectedIndex %08x%n", hoverIndex, selectedIndex);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        hoverX = -1;
        hoverY = -1;
        hoverIndex = -1;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int tileIndex = 0;
        hoverX = e.getPoint().x / 16;
        hoverY = e.getPoint().y / 16;

        tileIndex = ((e.getPoint().y / 16) * this.getWidth() / 16) + (e.getPoint().x / 16);
        if (tileIndex < host.getTiles().length) {
            hoverIndex = tileIndex;
        } else
            hoverIndex = -1;

        host.getTileStateManager().updateHighlightedTiles(hoverIndex);
    }
}
