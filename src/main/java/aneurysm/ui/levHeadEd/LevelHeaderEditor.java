package aneurysm.ui.levHeadEd;

import aneurysm.io.AudioManager;
import aneurysm.structures.ActorDefinition;
import aneurysm.structures.LevelHeader;
import aneurysm.ui.DataLists;
import aneurysm.ui.NumericTextBox;
import aneurysm.ui.Window;
import aneurysm.ui.actorEd.ui.SpriteSheetCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class LevelHeaderEditor extends JPanel implements FocusListener, ActionListener, ItemListener {

    private static final long serialVersionUID = -8635012649987300945L;
    private final Window host;
    private boolean changesMade;
    private int currentHeader = 0;
    private JLabel numLinesLbl, lineOffsLbl, numThingsLbl, thingOffsLbl, numLightsLbl, numDoorsLbl, paletteLbl,
            nodeTimerLbl, nodeLbl, minimapStartOffsetXLbl, minimapStartOffsetYLbl, mapOffsetXLbl, minimapWidthLbl,
            minimapHeightLbl, minimapOffsLbl, mapGridOffsLbl, spriteOffsLbl, selectedHeaderLbl;
    private NumericTextBox numLinesTxt, lineOffsTxt, numThingsTxt, thingOffsTxt, numLightsTxt, numDoorsTxt, nodeTimerTxt,
            minimapStartOffsetXTxt, minimapStartOffsetYTxt, mapOffsetXTxt, minimapWidthTxt, minimapHeightTxt,
            minimapOffsTxt, mapGridOffsTxt, spriteOffsTxt;
    JComboBox<Integer> selectedPalette, selectedNode, selectedLevelHeader;
    private JButton saveButton;
    private LevelHeader currentLevelHeaderDef = DataLists.getLevelHeaders().get(DataLists.getLevelHeaderList().get(currentHeader));

    private void pollToSave() {
        int result = -1;
        if (changesMade) {
            result = JOptionPane.showConfirmDialog(this, "Save Changes?", "You have unsaved changes.  Would you like to save them?", JOptionPane.YES_NO_CANCEL_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                Window.getReader().writeLevelHeader(currentHeader, currentLevelHeaderDef);
            }
        }
        changesMade = false;
        saveButton.setEnabled(false);
    }

    public LevelHeaderEditor(Window host) {
        this.setLayout(new GridLayout(6, 8));
        this.host = host;
        setupComponents();
    }

    private void setupComponents() {
        Integer[] entries = new Integer[DataLists.getActorDefinitions().size()];
        for (int i = 0; i < entries.length; i++) {
            entries[i] = i;
        }
        this.setSize(400, 240);
        numLinesLbl = new JLabel("Number of Lines");
        numLinesLbl.setLocation(16, 16);
        numLinesLbl.setSize(48, 24);
        numLinesTxt = new NumericTextBox(Integer.toHexString(currentLevelHeaderDef.getNumLines() & 0x0000ffff));
        numLinesTxt.setLocation(80, 16);
        numLinesTxt.setSize(48, 24);
        numLinesTxt.setFocusable(false);
        numLinesTxt.setEnabled(false);

        lineOffsLbl = new JLabel("Lines Offset");
        lineOffsLbl.setLocation(136, 16);
        lineOffsLbl.setSize(48, 24);
        lineOffsTxt = new NumericTextBox(Integer.toHexString(currentLevelHeaderDef.getLineoffs() & 0x0fffffff));
        lineOffsTxt.setLocation(160, 16);
        lineOffsTxt.setSize(48, 24);
        lineOffsTxt.setFocusable(false);
        lineOffsTxt.setEnabled(false);

        numThingsLbl = new JLabel("Number of Things");
        numThingsLbl.setLocation(0, 0);
        numThingsLbl.setSize(48, 24);
        numThingsTxt = new NumericTextBox(Integer.toHexString(currentLevelHeaderDef.getNumThings() & 0x0000ffff));
        numThingsTxt.setLocation(0, 0);
        numThingsTxt.setSize(48, 24);
        numThingsTxt.setFocusable(false);
        numThingsTxt.setEnabled(false);


        thingOffsLbl = new JLabel("Things Offset");
        thingOffsLbl.setLocation(0, 0);
        thingOffsLbl.setSize(48, 24);
        thingOffsTxt = new NumericTextBox(Integer.toHexString(currentLevelHeaderDef.getThingoffs() & 0x0fffffff));
        thingOffsTxt.setLocation(0, 0);
        thingOffsTxt.setSize(48, 24);
        thingOffsTxt.setFocusable(false);
        thingOffsTxt.setEnabled(false);

        numLightsLbl = new JLabel("Number of Lights");
        numLightsLbl.setLocation(0, 0);
        numLightsLbl.setSize(48, 24);
        numLightsTxt = new NumericTextBox(Integer.toHexString(currentLevelHeaderDef.getNumLights() & 0x0000ffff));
        numLightsTxt.setLocation(0, 0);
        numLightsTxt.setSize(48, 24);
        numLightsTxt.setFocusable(false);
        numLightsTxt.setEnabled(false);

        numDoorsLbl = new JLabel("Number of Doors");
        numDoorsLbl.setLocation(0, 0);
        numDoorsLbl.setSize(48, 24);
        numDoorsTxt = new NumericTextBox(Integer.toHexString(currentLevelHeaderDef.getNumDoors() & 0x0000ffff));
        numDoorsTxt.setLocation(0, 0);
        numDoorsTxt.setSize(48, 24);
        numDoorsTxt.setEnabled(false);
        numDoorsTxt.setFocusable(false);

        paletteLbl = new JLabel("Level Palette");
        paletteLbl.setLocation(0, 0);
        paletteLbl.setSize(48, 24);
        selectedPalette = new JComboBox<>(DataLists.getLevelPalettes().keySet().toArray(new Integer[0]));
        selectedPalette.setSelectedItem(currentLevelHeaderDef.getPaloffs());
        selectedPalette.addItemListener(this);
        if(DataLists.isCdOrCart()) {
            selectedPalette.setEnabled(false);
            selectedPalette.setFocusable(false);
        }

        nodeTimerLbl = new JLabel("Node Timer");
        nodeTimerLbl.setLocation(0, 0);
        nodeTimerLbl.setSize(48, 24);
        nodeTimerTxt = new NumericTextBox(Integer.toHexString(currentLevelHeaderDef.getNodeTimer() & 0x0000ffff));
        nodeTimerTxt.setLocation(0, 0);
        nodeTimerTxt.setSize(48, 24);
        nodeTimerTxt.addFocusListener(this);

        nodeLbl = new JLabel("Node Configuration");
        nodeLbl.setLocation(0, 0);
        nodeLbl.setSize(48, 24);
        selectedNode = new JComboBox<>(DataLists.getNodeDefinitions().keySet().toArray(new Integer[0]));
        selectedNode.setSelectedItem(currentLevelHeaderDef.getNodeoffs());
        selectedNode.addItemListener(this);

        minimapStartOffsetXLbl = new JLabel("Minimap X Scale");
        minimapStartOffsetXLbl.setLocation(0, 0);
        minimapStartOffsetXLbl.setSize(48, 24);
        minimapStartOffsetXTxt = new NumericTextBox(Integer.toHexString(currentLevelHeaderDef.getMinimapXScale() & 0x0000ffff));
        minimapStartOffsetXTxt.setLocation(0, 0);
        minimapStartOffsetXTxt.setSize(48, 24);
        minimapStartOffsetXTxt.setFocusable(false);
        minimapStartOffsetXTxt.setEnabled(false);

        minimapStartOffsetYLbl = new JLabel("Minimap Y Scale");
        minimapStartOffsetYLbl.setLocation(0, 0);
        minimapStartOffsetYLbl.setSize(48, 24);
        minimapStartOffsetYTxt = new NumericTextBox(Integer.toHexString(currentLevelHeaderDef.getMinimapYScale() & 0x0000ffff));
        minimapStartOffsetYTxt.setLocation(0, 0);
        minimapStartOffsetYTxt.setSize(48, 24);
        minimapStartOffsetYTxt.setFocusable(false);
        minimapStartOffsetYTxt.setEnabled(false);

        mapOffsetXLbl = new JLabel("Minimap X Offset");
        mapOffsetXLbl.setLocation(0, 0);
        mapOffsetXLbl.setSize(48, 24);
        mapOffsetXTxt = new NumericTextBox(Integer.toHexString(currentLevelHeaderDef.getMinimapXOffset() & 0x0000ffff));
        mapOffsetXTxt.setLocation(0, 0);
        mapOffsetXTxt.setSize(48, 24);
        mapOffsetXTxt.setEnabled(false);
        mapOffsetXTxt.setFocusable(false);

        minimapWidthLbl = new JLabel("Minimap Width");
        minimapWidthLbl.setLocation(0, 0);
        minimapWidthLbl.setSize(48, 24);
        minimapWidthTxt = new NumericTextBox(Integer.toHexString(currentLevelHeaderDef.getMinimapWidth() & 0x0000ffff));
        minimapWidthTxt.setLocation(0, 0);
        minimapWidthTxt.setSize(48, 24);
        minimapWidthTxt.setEnabled(false);
        minimapWidthTxt.setFocusable(false);

        minimapHeightLbl = new JLabel("Minimap Height");
        minimapHeightLbl.setLocation(0, 0);
        minimapHeightLbl.setSize(48, 24);
        minimapHeightTxt = new NumericTextBox(Integer.toHexString(currentLevelHeaderDef.getMinimapHeight() & 0x0000ffff));
        minimapHeightTxt.setLocation(0, 0);
        minimapHeightTxt.setSize(48, 24);
        minimapHeightTxt.setEnabled(false);
        minimapHeightTxt.setFocusable(false);

        minimapOffsLbl = new JLabel("Minimap Offset");
        minimapOffsLbl.setLocation(0, 0);
        minimapOffsLbl.setSize(48, 24);
        minimapOffsTxt = new NumericTextBox(Integer.toHexString(currentLevelHeaderDef.getMinimapoffs() & 0x0fffffff));
        minimapOffsTxt.setLocation(0, 0);
        minimapOffsTxt.setSize(48, 24);
        minimapOffsTxt.setEnabled(false);
        minimapOffsTxt.setFocusable(false);

        mapGridOffsLbl = new JLabel("Minimap Grid Offset");
        mapGridOffsLbl.setLocation(0, 0);
        mapGridOffsLbl.setSize(48, 24);
        mapGridOffsTxt = new NumericTextBox(Integer.toHexString(currentLevelHeaderDef.getGridoffs() & 0x0fffffff));
        mapGridOffsTxt.setLocation(0, 0);
        mapGridOffsTxt.setSize(48, 24);
        mapGridOffsTxt.setEnabled(false);
        mapGridOffsTxt.setFocusable(false);

        spriteOffsLbl = new JLabel("Sprite Table Offset");
        spriteOffsLbl.setLocation(0, 0);
        spriteOffsLbl.setSize(48, 24);
        spriteOffsTxt = new NumericTextBox(Integer.toHexString(currentLevelHeaderDef.getSpriteTableOffs() & 0x0fffffff));
        spriteOffsTxt.setLocation(0, 0);
        spriteOffsTxt.setSize(48, 24);
        spriteOffsTxt.addFocusListener(this);
        spriteOffsTxt.setEnabled(false);
        spriteOffsTxt.setFocusable(false);

        selectedHeaderLbl = new JLabel("Selected Level Header");

        selectedLevelHeader = new JComboBox<>(entries);
        selectedLevelHeader.setEditable(false);
        selectedLevelHeader.addItemListener(this);
        selectedLevelHeader.setEnabled(!DataLists.isCdOrCart());

        saveButton = new JButton("Save");
        saveButton.setEnabled(false);
        saveButton.addActionListener(this);

        this.add(numLinesLbl);
        this.add(numLinesTxt);
        this.add(lineOffsLbl);
        this.add(lineOffsTxt);
        this.add(numThingsLbl);
        this.add(numThingsTxt);
        this.add(thingOffsLbl);
        this.add(thingOffsTxt);
        this.add(numLightsLbl);
        this.add(numLightsTxt);
        this.add(numDoorsLbl);
        this.add(numDoorsTxt);
        this.add(paletteLbl);
        this.add(selectedPalette);
        this.add(nodeTimerLbl);
        this.add(nodeTimerTxt);
        this.add(nodeLbl);
        this.add(selectedNode);
        this.add(minimapStartOffsetXLbl);
        this.add(minimapStartOffsetXTxt);
        this.add(minimapStartOffsetYLbl);
        this.add(minimapStartOffsetYTxt);
        this.add(mapOffsetXLbl);
        this.add(mapOffsetXTxt);
        this.add(minimapWidthLbl);
        this.add(minimapWidthTxt);
        this.add(minimapHeightLbl);
        this.add(minimapHeightTxt);
        this.add(minimapOffsLbl);
        this.add(minimapOffsTxt);
        this.add(mapGridOffsLbl);
        this.add(mapGridOffsTxt);
        if (DataLists.isCdOrCart()) {
            this.add(spriteOffsLbl);
            this.add(spriteOffsTxt);
        }
        this.add(selectedHeaderLbl);
        this.add(selectedLevelHeader);
        this.add(saveButton);


        Thread main = new Thread(new Runner());
        main.setDaemon(true);
        main.start();
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {
        short txtShort;

        if (e.getSource() == nodeTimerTxt) {

            txtShort = Short.parseShort(nodeTimerTxt.getText(), 16);
            if (txtShort != (DataLists.getLevelHeaders().get(DataLists.getLevelHeaderList().get(currentHeader)).getNodeTimer() & 0x0000ffff)) {
                changesMade = true;
                currentLevelHeaderDef.setNodeTimer(txtShort);
            }
        }

        if (changesMade) {
            saveButton.setEnabled(true);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            Window.getReader().writeLevelHeader(DataLists.getLevelHeaderList().get(currentHeader), currentLevelHeaderDef);
            changesMade = false;
            saveButton.setEnabled(false);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        if (e.getSource() == this.selectedPalette && e.getStateChange() == ItemEvent.SELECTED) {
            currentLevelHeaderDef.setPaloffs((Integer) selectedPalette.getSelectedItem());
            changesMade = true;
        }

        if (e.getSource() == this.selectedNode && e.getStateChange() == ItemEvent.SELECTED) {
            currentLevelHeaderDef.setNodeoffs((Integer) selectedNode.getSelectedItem());
            changesMade = true;
        }

        if (e.getSource() == this.selectedLevelHeader && e.getStateChange() == ItemEvent.SELECTED) {
            if (changesMade) {
                pollToSave();
            }
            currentHeader = (int) this.selectedLevelHeader.getSelectedItem();
            currentLevelHeaderDef = DataLists.getLevelHeaders().get(DataLists.getLevelHeaderList().get(currentHeader));

            System.out.println("currentHeader \n" + DataLists.getLevelHeaders().get(DataLists.getLevelHeaderList().get(currentHeader)));

            numLinesTxt.setText(Integer.toHexString(currentLevelHeaderDef.getNumLines() & 0x0000ffff));
            lineOffsTxt.setText(Integer.toHexString(currentLevelHeaderDef.getLineoffs() & 0x0fffffff));
            numThingsTxt.setText(Integer.toHexString(currentLevelHeaderDef.getNumThings() & 0x0000ffff));
            thingOffsTxt.setText(Integer.toHexString(currentLevelHeaderDef.getThingoffs() & 0x0fffffff));
            numLightsTxt.setText(Integer.toHexString(currentLevelHeaderDef.getNumLights() & 0x0000ffff));
            numDoorsTxt.setText(Integer.toHexString(currentLevelHeaderDef.getNumDoors() & 0x0000ffff));
            selectedPalette.setSelectedItem(Integer.toHexString(currentLevelHeaderDef.getPaloffs() & 0x0fffffff));
            nodeTimerTxt.setText(Integer.toHexString(currentLevelHeaderDef.getNodeTimer() & 0x0000ffff));
            selectedNode.setSelectedItem(Integer.toHexString(currentLevelHeaderDef.getNodeoffs() & 0x00ffffff));
            minimapStartOffsetXTxt.setText(Integer.toHexString(currentLevelHeaderDef.getMinimapXScale() & 0x0000ffff));
            minimapStartOffsetYTxt.setText(Integer.toHexString(currentLevelHeaderDef.getMinimapYScale() & 0x0000ffff));
            mapOffsetXTxt.setText(Integer.toHexString(currentLevelHeaderDef.getMinimapXOffset() & 0x0000ffff));
            minimapWidthTxt.setText(Integer.toHexString(currentLevelHeaderDef.getMinimapWidth() & 0x0000ffff));
            minimapHeightTxt.setText(Integer.toHexString(currentLevelHeaderDef.getMinimapHeight() & 0x0000ffff));
            minimapOffsTxt.setText(Integer.toHexString(currentLevelHeaderDef.getMinimapoffs() & 0x0fffffff));
            mapGridOffsTxt.setText(Integer.toHexString(currentLevelHeaderDef.getGridoffs() & 0x0fffffff));
            if (DataLists.isCdOrCart())
                spriteOffsTxt.setText(Integer.toHexString(currentLevelHeaderDef.getSpriteTableOffs() & 0x0fffffff));
            if(saveButton.isEnabled())
                saveButton.setEnabled(false);
        }

        if (changesMade) {
            saveButton.setEnabled(true);
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
