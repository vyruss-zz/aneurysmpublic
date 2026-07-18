package aneurysm.ui.tileMapEd;

import javax.swing.*;

import aneurysm.render.BufferedImageBuilder;
import aneurysm.ui.DataLists;
import aneurysm.ui.Window;
import aneurysm.ui.tileMapEd.state.TileEditorState;
import aneurysm.ui.tileMapEd.ui.TileListCanvas;
import aneurysm.ui.tileMapEd.ui.TileMapCanvas;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;

public class tileMapEditor extends JPanel {
    private TileEditorState stateManager;
    private JLabel tileId;
    private JLabel selectedTile;
    private JLabel mappedInfo;
    private JLabel listInfo;
    private JCheckBox flipH, flipV;
    private JComboBox<String> tileMappables;
    private int currentTilemapDataIndex = 0;
    private final Window host;
    private Color[] palette = new Color[16];
    private TileMapCanvas tileMapPanel;
    private TileListCanvas tileListPanel;
    private short[] tileMap;
    private byte[] gfxbuff;
    private BufferedImage[] tiles;
    private boolean changesMade;

    public BufferedImage[] getTiles() {
        return tiles;
    }

    public short[] getTilemap() {
        return tileMap;
    }

    public tileMapEditor(Window host) {
        this.setLayout(null);
        this.host = host;
        this.stateManager = new TileEditorState(this);
        loadAssets(currentTilemapDataIndex);
        setupComponents();
    }

    public void setChangesMade() {
        this.changesMade = true;
    }

    public TileEditorState getTileStateManager() {
        return stateManager;
    }

    private void buildTileArray(BufferedImage[] tiles, byte[] gfxbuff) {
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = BufferedImageBuilder.build4BPPTile(gfxbuff, 8, 8, palette, 32 * i, (32 + (i * 32)));
        }
    }

    private void loadAssets(int currentIndex) {
        if (!DataLists.isCdOrCart()) {
            if (DataLists.getTileMappables().get(currentIndex).getType() == 'h') {
                this.tileMap = Window.getReader().getTileMap(DataLists.getTileMappables().get(currentIndex).getTilemapoffs(), DataLists.getTileMappables().get(currentIndex).getTilemaplen(), Window.getReader().getConfig().getLocation());
                this.palette = Window.getReader().readPalette(DataLists.getTileMappables().get(currentIndex).getPaloffs() + 96, 16, Window.getReader().getConfig().getLocation());
                this.gfxbuff = Window.getReader().readByteArray(DataLists.getTileMappables().get(currentIndex).getGfxoffs(), DataLists.getTileMappables().get(currentIndex).getGfxlen(), Window.getReader().getConfig().getLocation());
            }
            if (DataLists.getTileMappables().get(currentIndex).getType() == 'i') {
                this.tileMap = Window.getReader().getTileMap(DataLists.getTileMappables().get(currentIndex).getTilemapoffs(), DataLists.getTileMappables().get(currentIndex).getTilemaplen(), Window.getReader().getConfig().getLocation());
                this.palette = Window.getReader().readPalette(DataLists.getTileMappables().get(currentIndex).getPaloffs(), 16, Window.getReader().getConfig().getLocation());
                this.gfxbuff = Window.getReader().decompressRNCData(DataLists.getTileMappables().get(currentIndex).getGfxoffs(), Window.getReader().getConfig().getLocation());
            }
        } else {
            if (DataLists.getTileMappables().get(currentIndex).getType() == 'h') {
                this.tileMap = Window.getReader().getTileMap(DataLists.getTileMappables().get(currentIndex).getTilemapoffs(), DataLists.getTileMappables().get(currentIndex).getTilemaplen(), Window.getReader().getConfig().getSlidesBinLocation());
                this.palette = Window.getReader().readPalette(DataLists.getTileMappables().get(currentIndex).getPaloffs() + 96, 16, Window.getReader().getConfig().getSlidesBinLocation());
                this.gfxbuff = Window.getReader().readByteArray(DataLists.getTileMappables().get(currentIndex).getGfxoffs(), DataLists.getTileMappables().get(currentIndex).getGfxlen(), Window.getReader().getConfig().getSlidesBinLocation());
            }
            if (DataLists.getTileMappables().get(currentIndex).getType() == 'i') {
                this.tileMap = Window.getReader().getTileMap(DataLists.getTileMappables().get(currentIndex).getTilemapoffs(), DataLists.getTileMappables().get(currentIndex).getTilemaplen(), Window.getReader().getConfig().getSlidesBinLocation());
                this.palette = Window.getReader().readPalette(DataLists.getTileMappables().get(currentIndex).getPaloffs(), 16, Window.getReader().getConfig().getSlidesBinLocation());
                this.gfxbuff = Window.getReader().decompressRNCData(DataLists.getTileMappables().get(currentIndex).getGfxoffs(), Window.getReader().getConfig().getSlidesBinLocation());
            }
        }
        this.tiles = new BufferedImage[this.gfxbuff.length / 32];
        buildTileArray(this.tiles, this.gfxbuff);
    }


    public void updateTileListHighlight(int tileIndex) {
        tileListPanel.setHover(tileIndex);
    }

    public void updateTileInfoComponents(int selected) {
        flipV.setSelected((this.stateManager.getFlipState() & 0x1000) != 0);
        flipH.setSelected((this.stateManager.getFlipState() & 0x0800) != 0);
    }

    private void setupComponents() {
        String[] names = new String[DataLists.getTileMappables().size()];
        for (int i = 0; i < DataLists.getTileMappables().size(); i++) {
            names[i] = DataLists.getTileMappables().get(i).getName();
        }
        tileId = new JLabel("Current Tile: ");
        tileId.setSize(240, 48);
        tileId.setLocation(16, 500);
        selectedTile = new JLabel("Selected Tile: ");
        selectedTile.setSize(240, 48);
        selectedTile.setLocation(608, 500);
        mappedInfo = new JLabel("Left click to select tile. Right click to place tile. Middle mouse to set flip modes.");
        mappedInfo.setSize(384, 48);
        mappedInfo.setLocation(16, 480);
        listInfo = new JLabel("Left click to select tile.");
        listInfo.setSize(128, 48);
        listInfo.setLocation(608, 480);
        flipH = new JCheckBox("Flip Horizontally?", false);
        flipH.setEnabled(false);
        flipH.setLocation(16, 548);
        flipH.setSize(128, 48);
        flipV = new JCheckBox("Flip Vertically?", false);
        flipV.setEnabled(false);
        flipV.setLocation(156, 548);
        flipV.setSize(128, 48);
        tileListPanel = new TileListCanvas(this);
        tileListPanel.setLocation(608, 48);
        tileMapPanel = new TileMapCanvas(this);
        tileMapPanel.setLocation(16, 48);
        tileMappables = new JComboBox<>(names);
        tileMappables.setSize(256, 24);
        tileMappables.setEditable(false);
        tileMappables.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (changesMade) {
                    pollToSave();
                }
                currentTilemapDataIndex = tileMappables.getSelectedIndex();
                loadAssets(currentTilemapDataIndex);
                stateManager.setSelectedTileListIndex(0);
            }
        });
        tileMappables.setLocation(400, 8);

        Thread main = new Thread(new Runner());
        main.setDaemon(true);
        main.start();


        this.add(mappedInfo);
        this.add(listInfo);
        this.add(tileId);
        this.add(selectedTile);
        this.add(tileMapPanel);
        this.add(tileListPanel);
        this.add(tileMappables);
        this.add(flipH);
        this.add(flipV);
    }

    private void pollToSave() {
        int result = -1;
        if (changesMade) {
            result = JOptionPane.showConfirmDialog(this, "Save Changes?",
                    "You have unsaved changes.  Would you like to save them?", JOptionPane.YES_NO_CANCEL_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                changesMade = false;
                Window.getReader().writeTileMap(DataLists.getTileMappables().get(currentTilemapDataIndex).getTilemapoffs(), DataLists.getTileMappables().get(currentTilemapDataIndex).getTilemaplen(), tileMap);
            }
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
