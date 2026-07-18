package aneurysm.ui.mapviewer;

import aneurysm.render.BufferedImageBuilder;
import aneurysm.ui.DataLists;
import aneurysm.ui.Window;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MapViewer extends JPanel {
    private BufferedImage mapImage;

    public MapViewer(Window host) {
        this.setSize(640, 480);
        this.readMiniMap();
        repaint();
    }

    private void readMiniMap() {
        if(!DataLists.isCdOrCart())
            mapImage = BufferedImageBuilder.build1BPPImage(Window.getReader().decompressRNCData(DataLists.getCurrentLevelHeader().getMinimapoffs(), Window.getReader().getConfig().getLocation()),
                    DataLists.getCurrentLevelHeader().getMinimapWidth(), DataLists.getCurrentLevelHeader().getMinimapHeight(), DataLists.getLevelPal(), 0);
        else
            mapImage = BufferedImageBuilder.build1BPPImage(Window.getReader().readByteArray(DataLists.getCurrentLevelHeader().getMinimapoffs(),
                    (DataLists.getCurrentLevelHeader().getMinimapWidth() * DataLists.getCurrentLevelHeader().getMinimapHeight())/8, Window.getReader().getConfig().getLocation()),
                    DataLists.getCurrentLevelHeader().getMinimapWidth(), DataLists.getCurrentLevelHeader().getMinimapHeight(), DataLists.getLevelPal(), 0);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //draw mapImage to this JPanel canvas
        g.drawImage(mapImage, 16, 16, this);
    }
}
