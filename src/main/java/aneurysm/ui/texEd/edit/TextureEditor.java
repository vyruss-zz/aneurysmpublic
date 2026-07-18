package aneurysm.ui.texEd.edit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import aneurysm.io.FileReader;
import aneurysm.offsets.CDOffsets;
import aneurysm.offsets.CartOffsets;
import aneurysm.ui.DataLists;
import aneurysm.ui.Window;
import aneurysm.ui.sprEd.offsets.SpriteDimension;
import aneurysm.ui.sprEd.ui.SpriteCanvas;
import aneurysm.ui.texEd.ui.TextureCanvas;

public class TextureEditor extends JPanel {

    private JButton next;
    private JButton prev;
    private JComboBox<Integer> dropDownList;
    private static final long serialVersionUID = -8635012649987300945L;
    private JLabel coords;
    private int selectedImageIndex;
    private final Window host;
    private int colorIndex = 0;
    private final JLabel[] colors = new JLabel[32];
    private Color[] pal;
    private TextureCanvas imagePanel;
    private int currentImageOffset;
    private short currentImageWidth;
    private Byte[][] currentImage;
    private boolean changesMade;

    public void setCurrentImage(Byte[][] in) {
        currentImage = in;
    }

    private void writeTexture() {
        if (!DataLists.isCdOrCart()) {
            Window.getReader().writeROMTexture(currentImageOffset, currentImage);
        } else {
            Window.getReader().writeCDTexture(currentImageOffset, currentImage);
        }
    }

    private void pollToSave() {
        int result = -1;
        if (changesMade) {
            result = JOptionPane.showConfirmDialog(this, "Save Changes?", "You have unsaved changes.  Would you like to save them?", JOptionPane.YES_NO_CANCEL_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                writeTexture();
            }
        }
        changesMade = false;
    }

    public TextureEditor(Window host) {
        pal = DataLists.getLevelPal();
        this.setLayout(null);
        this.host = host;
        setupComponents();
    }

    private Byte[][] readTexture(int offset, int width) {
        Byte[][] out;
        if (!DataLists.isCdOrCart())
            out = Window.getReader().readROMTexture(offset, width);
        else {
            out = Window.getReader().readCDTexture(offset, width);
        }
        return out;
    }

    private void setupComponents() {
        Integer[] entries = DataLists.getTextureInfoCache().keySet().toArray(new Integer[0]);
        currentImageOffset = entries[0];
        currentImageWidth = DataLists.getTextureInfoCache().get(currentImageOffset);
        int dist = 640;
        this.setSize(700, 800);
        coords = new JLabel("0,0");
        coords.setSize(48, 240);
        coords.setLocation(0, 0);

        dropDownList = new JComboBox<>(entries);
        dropDownList.setSize(64, 24);
        dropDownList.setEditable(false);
        dropDownList.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (changesMade) {
                    pollToSave();
                }
                selectedImageIndex = dropDownList.getSelectedIndex();
                currentImageOffset = entries[dropDownList.getSelectedIndex()];
                currentImageWidth = DataLists.getTextureInfoCache().get(currentImageOffset);
                currentImage = readTexture(currentImageOffset, currentImageWidth);
                System.out.printf("%s%08x%s%04x%n", "current offset: ", currentImageOffset, ", current Width: ", currentImageWidth);
                updateEditingImage();
            }
        });

        for (int i = 0; i < 16; i++) {
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
                    if(!DataLists.isCdOrCart())
                        colorIndex <<= 4;
                    imagePanel.setColor(colorIndex);
                }
            });
        }

        for (int i = 16; i < 32; i++) {
            colors[i] = new JLabel("0" + Integer.toHexString(i));
            this.add(colors[i]);
            colors[i].setBackground(pal[i]);
            colors[i].setOpaque(true);
            colors[i].setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            colors[i].setSize(new Dimension(24, 24));
            colors[i].setLocation(120 + (i-(pal.length/2)) * 28, dist+32);
            int count = i-pal.length/2;
            colors[i].addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent me) {
                    colorIndex = count+16;
                    if(!DataLists.isCdOrCart())
                        colorIndex <<= 4;
                    imagePanel.setColor(colorIndex);
                }
            });
        }

        selectedImageIndex = 0;

        Thread main = new Thread(new Runner());
        main.setDaemon(true);
        main.start();
        imagePanel = new TextureCanvas(this, coords);
        imagePanel.setPal(pal);
        this.add(coords);
        this.add(imagePanel);
        this.add(dropDownList);
        updateEditingImage();
    }

    private void updateEditingImage() {
        imagePanel.setImage(readTexture(currentImageOffset, currentImageWidth));
    }

    public void setChangesMade() {
        this.changesMade = true;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.red);
        g.drawRect(colors[colorIndex].getX() - 1, colors[colorIndex].getY() - 1, colors[colorIndex].getWidth() + 2, colors[colorIndex].getHeight() + 2);
        g.drawRect(colors[colorIndex].getX() - 2, colors[colorIndex].getY() - 2, colors[colorIndex].getWidth() + 3, colors[colorIndex].getHeight() + 3);
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
