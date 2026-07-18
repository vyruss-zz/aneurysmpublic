package aneurysm.ui.nodeEd;

import aneurysm.io.AudioManager;
import aneurysm.structures.NodeDefinition;
import aneurysm.structures.ProjectileDefinition;
import aneurysm.ui.DataLists;
import aneurysm.ui.NumericTextBox;
import aneurysm.ui.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class NodeEditor extends JPanel implements FocusListener, ItemListener, ActionListener {

    private static final long serialVersionUID = -8635012649987300945L;
    private final Window host;
    private boolean changesMade;
    private int currentNode;
    private JLabel startOrbitLbl, refireDelayLbl, deathSpeedIncreaseLbl, maxOrbitSpeedLbl, proximityCloseLbl, proximityFarLbl,
            travelSpeedLbl, numSentriesLbl, field0Lbl, field1Lbl, field2Lbl, field3Lbl, damageNodeRefireIncreaseLbl, selectedLbl;
    private NumericTextBox startOrbitTxt, refireDelayTxt, deathSpeedIncreaseTxt, maxOrbitSpeedTxt, proximityCloseTxt, proximityFarTxt,
            travelSpeedTxt, numSentriesTxt, field0Txt, field1Txt, field2Txt, field3Txt, damageNodeRefireIncreaseTxt;
    JComboBox<Integer> selectedNodeDef;
    private JButton saveButton;
    private NodeDefinition currentNodeDef;

    private void pollToSave() {
        int result = -1;
        if (changesMade) {
            result = JOptionPane.showConfirmDialog(this, "Save Changes?", "You have unsaved changes.  Would you like to save them?", JOptionPane.YES_NO_CANCEL_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                Window.getReader().writeNodeDefinition(currentNode, currentNodeDef);
            }
        }
        changesMade = false;
        saveButton.setEnabled(false);
    }

    public NodeEditor(Window host) {
        this.setLayout(new GridLayout(4, 6));
        this.host = host;
        setupComponents();
    }


    private void setupComponents() {
        selectedNodeDef = new JComboBox<>(DataLists.getNodeDefinitions().keySet().toArray(new Integer[0]));
        selectedNodeDef.setSize(64, 24);
        selectedNodeDef.setLocation(736, 16);
        selectedNodeDef.setEditable(false);
        selectedNodeDef.addItemListener(this);

        currentNode = (Integer) (selectedNodeDef.getSelectedItem());
        currentNodeDef = DataLists.getNodeDefinitions().get((Integer) selectedNodeDef.getSelectedItem());
        System.out.println(DataLists.getNodeDefinitions().keySet());
        System.out.println("Current Node: " + currentNode);
        System.out.println(currentNodeDef);

        this.setSize(400, 200);
        startOrbitLbl = new JLabel("Start Orbit Speed");
        startOrbitLbl.setLocation(16, 16);
        startOrbitLbl.setSize(48, 24);
        startOrbitTxt = new NumericTextBox(Integer.toHexString(currentNodeDef.getSentryStartOrbitSpeed() & 0x0000ffff));
        startOrbitTxt.setLocation(80, 16);
        startOrbitTxt.setSize(48, 24);
        startOrbitTxt.addFocusListener(this);

        refireDelayLbl = new JLabel("Sentry Refire Delay");
        refireDelayLbl.setLocation(16, 16);
        refireDelayLbl.setSize(48, 24);
        refireDelayTxt = new NumericTextBox(Integer.toHexString(currentNodeDef.getSentryRefireDelay() & 0x0000ffff));
        refireDelayTxt.setLocation(80, 16);
        refireDelayTxt.setSize(48, 24);
        refireDelayTxt.addFocusListener(this);

        deathSpeedIncreaseLbl = new JLabel("Sentry Speed Increment");
        deathSpeedIncreaseLbl.setLocation(16, 16);
        deathSpeedIncreaseLbl.setSize(48, 24);
        deathSpeedIncreaseTxt = new NumericTextBox(Integer.toHexString(currentNodeDef.getSentrySpeedIncrement() & 0x0000ffff));
        deathSpeedIncreaseTxt.setLocation(80, 16);
        deathSpeedIncreaseTxt.setSize(48, 24);
        deathSpeedIncreaseTxt.addFocusListener(this);

        maxOrbitSpeedLbl = new JLabel("Sentry Max Speed");
        maxOrbitSpeedLbl.setLocation(0, 0);
        maxOrbitSpeedLbl.setSize(48, 24);
        maxOrbitSpeedTxt = new NumericTextBox(Integer.toHexString(currentNodeDef.getSentryMaxSpeed() & 0x0000ffff));
        maxOrbitSpeedTxt.setLocation(80, 16);
        maxOrbitSpeedTxt.setSize(48, 24);
        maxOrbitSpeedTxt.addFocusListener(this);

        proximityCloseLbl = new JLabel("Sentry Proximity Far");
        proximityCloseLbl.setLocation(0, 0);
        proximityCloseLbl.setSize(48, 24);
        proximityCloseTxt = new NumericTextBox(Integer.toHexString(currentNodeDef.getSentryNodeFarDistance() & 0x0000ffff));
        proximityCloseTxt.setLocation(80, 16);
        proximityCloseTxt.setSize(48, 24);
        proximityCloseTxt.addFocusListener(this);

        proximityFarLbl = new JLabel("Sentry Proximity Close");
        proximityFarLbl.setLocation(0, 0);
        proximityFarLbl.setSize(48, 24);
        proximityFarTxt = new NumericTextBox(Integer.toHexString(currentNodeDef.getSentryNodeCloseDistance() & 0x0000ffff));
        proximityFarTxt.setLocation(0, 0);
        proximityFarTxt.setSize(48, 24);
        proximityFarTxt.addFocusListener(this);

        travelSpeedLbl = new JLabel("Sentry Speed");
        travelSpeedLbl.setLocation(0, 0);
        travelSpeedLbl.setSize(48, 24);
        travelSpeedTxt = new NumericTextBox(Integer.toHexString(currentNodeDef.getSentrySpeed() & 0x0000ffff));
        travelSpeedTxt.setLocation(0, 0);
        travelSpeedTxt.setSize(48, 24);
        travelSpeedTxt.addFocusListener(this);

        numSentriesLbl = new JLabel("Sentry Count");
        numSentriesLbl.setLocation(0, 0);
        numSentriesLbl.setSize(48, 24);
        numSentriesTxt = new NumericTextBox(Integer.toHexString(currentNodeDef.getSentryCount() & 0x0000ffff));
        numSentriesTxt.setLocation(0, 0);
        numSentriesTxt.setSize(48, 24);
        numSentriesTxt.addFocusListener(this);


        field0Lbl = new JLabel("Field 1");
        field0Lbl.setLocation(0, 0);
        field0Lbl.setSize(48, 24);
        field0Txt = new NumericTextBox(Integer.toHexString(currentNodeDef.getUnknOne() & 0x0000ffff));
        field0Txt.setLocation(0, 0);
        field0Txt.setSize(48, 24);
        field0Txt.addFocusListener(this);

        field1Lbl = new JLabel("Field 2");
        field1Lbl.setLocation(0, 0);
        field1Lbl.setSize(48, 24);
        field1Txt = new NumericTextBox(Integer.toHexString(currentNodeDef.getUnknTwo() & 0x0000ffff));
        field1Txt.setLocation(0, 0);
        field1Txt.setSize(48, 24);
        field1Txt.addFocusListener(this);

        field2Lbl = new JLabel("Field 3");
        field2Lbl.setLocation(0, 0);
        field2Lbl.setSize(48, 24);
        field2Txt = new NumericTextBox(Integer.toHexString(currentNodeDef.getUnknThree() & 0x0000ffff));
        field2Txt.setLocation(0, 0);
        field2Txt.setSize(48, 24);
        field2Txt.addFocusListener(this);

        field3Lbl = new JLabel("Field 4");
        field3Lbl.setLocation(0, 0);
        field3Lbl.setSize(48, 24);
        field3Txt = new NumericTextBox(Integer.toHexString(currentNodeDef.getUnknFour() & 0x0000ffff));
        field3Txt.setLocation(0, 0);
        field3Txt.setSize(48, 24);
        field3Txt.addFocusListener(this);

        damageNodeRefireIncreaseLbl = new JLabel("Node Damage Fire Speed Increment");
        damageNodeRefireIncreaseLbl.setLocation(0, 0);
        damageNodeRefireIncreaseLbl.setSize(48, 24);
        damageNodeRefireIncreaseTxt = new NumericTextBox(Integer.toHexString(currentNodeDef.getNodeHealthRefireAdvance() & 0x0000ffff));
        damageNodeRefireIncreaseTxt.setLocation(0, 0);
        damageNodeRefireIncreaseTxt.setSize(48, 24);
        damageNodeRefireIncreaseTxt.addFocusListener(this);

        selectedLbl = new JLabel("Selected Node Definition");

        saveButton = new JButton("Save");
        saveButton.setEnabled(false);
        saveButton.addActionListener(this);

        this.add(startOrbitLbl);
        this.add(startOrbitTxt);
        this.add(refireDelayLbl);
        this.add(refireDelayTxt);
        this.add(deathSpeedIncreaseLbl);
        this.add(deathSpeedIncreaseTxt);
        this.add(maxOrbitSpeedLbl);
        this.add(maxOrbitSpeedTxt);
        this.add(proximityCloseLbl);
        this.add(proximityCloseTxt);
        this.add(proximityFarLbl);
        this.add(proximityFarTxt);
        this.add(travelSpeedLbl);
        this.add(travelSpeedTxt);
        this.add(numSentriesLbl);
        this.add(numSentriesTxt);
        this.add(field0Lbl);
        this.add(field0Txt);
        this.add(field1Lbl);
        this.add(field1Txt);
        this.add(field2Lbl);
        this.add(field2Txt);
        this.add(field3Lbl);
        this.add(field3Txt);
        this.add(damageNodeRefireIncreaseLbl);
        this.add(damageNodeRefireIncreaseTxt);
        this.add(refireDelayLbl);
        this.add(refireDelayTxt);
        this.add(selectedLbl);
        this.add(selectedNodeDef);
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


        if (e.getSource() == startOrbitTxt) {

            txtShort = Short.parseShort(startOrbitTxt.getText(), 16);
            if (txtShort != DataLists.getNodeDefinitions().get(currentNode).getSentryStartOrbitSpeed()) {
                changesMade = true;
                currentNodeDef.setSentryStartOrbitSpeed(txtShort);
            }
        }
        if (e.getSource() == refireDelayTxt) {
            txtShort = Short.parseShort(refireDelayTxt.getText(), 16);
            if (txtShort != DataLists.getNodeDefinitions().get(currentNode).getSentryRefireDelay()) {
                changesMade = true;
                currentNodeDef.setSentryRefireDelay(txtShort);
            }
        }
        if (e.getSource() == deathSpeedIncreaseTxt) {
            txtShort = Short.parseShort(deathSpeedIncreaseTxt.getText(), 16);
            if (txtShort != DataLists.getNodeDefinitions().get(currentNode).getSentrySpeedIncrement()) {
                changesMade = true;
                currentNodeDef.setSentrySpeedIncrement(txtShort);
            }
        }

        if (e.getSource() == maxOrbitSpeedTxt) {
            txtShort = Short.parseShort(maxOrbitSpeedTxt.getText(), 16);
            if (txtShort != DataLists.getNodeDefinitions().get(currentNode).getSentryMaxSpeed()) {
                changesMade = true;
                currentNodeDef.setSentryMaxSpeed(txtShort);
            }
        }
        if (e.getSource() == proximityCloseTxt) {
            txtShort = Short.parseShort(proximityCloseTxt.getText(), 16);
            if (txtShort != DataLists.getNodeDefinitions().get(currentNode).getSentryNodeFarDistance()) {
                changesMade = true;
                currentNodeDef.setSentryNodeFarDistance(txtShort);
            }
        }
        if (e.getSource() == proximityFarTxt) {
            txtShort = Short.parseShort(proximityFarTxt.getText(), 16);
            if (txtShort != DataLists.getNodeDefinitions().get(currentNode).getSentryNodeCloseDistance()) {
                changesMade = true;
                currentNodeDef.setSentryNodeCloseDistance(txtShort);
            }
        }
        if (e.getSource() == travelSpeedTxt) {

            txtShort = Short.parseShort(travelSpeedTxt.getText(), 16);
            if (txtShort != DataLists.getNodeDefinitions().get(currentNode).getSentrySpeed()) {
                changesMade = true;
                currentNodeDef.setSentrySpeed(txtShort);
            }
        }
        if (e.getSource() == numSentriesTxt) {
            txtShort = Short.parseShort(numSentriesTxt.getText(), 16);
            if (txtShort != DataLists.getNodeDefinitions().get(currentNode).getSentryCount()) {
                changesMade = true;
                currentNodeDef.setSentryCount(txtShort);
            }
        }
        if (e.getSource() == field0Txt) {
            txtShort = Short.parseShort(field0Txt.getText(), 16);
            if (txtShort != DataLists.getNodeDefinitions().get(currentNode).getUnknOne()) {
                changesMade = true;
                currentNodeDef.setUnknOne(txtShort);
            }
        }
        if (e.getSource() == field1Txt) {
            txtShort = Short.parseShort(field1Txt.getText(), 16);
            if (txtShort != DataLists.getNodeDefinitions().get(currentNode).getUnknTwo()) {
                changesMade = true;
                currentNodeDef.setUnknTwo(txtShort);
            }
        }
        if (e.getSource() == field2Txt) {
            txtShort = Short.parseShort(field2Txt.getText(), 16);
            if (txtShort != DataLists.getNodeDefinitions().get(currentNode).getUnknThree()) {
                changesMade = true;
                currentNodeDef.setUnknThree(txtShort);
            }
        }
        if (e.getSource() == field3Txt) {
            txtShort = Short.parseShort(field3Txt.getText(), 16);
            if (txtShort != DataLists.getNodeDefinitions().get(currentNode).getUnknFour()) {
                changesMade = true;
                currentNodeDef.setUnknFour(txtShort);
            }
        }
        if (e.getSource() == damageNodeRefireIncreaseTxt) {
            txtShort = Short.parseShort(refireDelayTxt.getText(), 16);
            if (txtShort != DataLists.getNodeDefinitions().get(currentNode).getNodeHealthRefireAdvance()) {
                changesMade = true;
                currentNodeDef.setNodeHealthRefireAdvance(txtShort);
            }
        }
        if (changesMade) {
            saveButton.setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == saveButton) {
            Window.getReader().writeNodeDefinition(currentNode, currentNodeDef);
            changesMade = false;
            saveButton.setEnabled(false);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getSource() == selectedNodeDef && e.getStateChange() == ItemEvent.SELECTED) {
            if (changesMade) {
                pollToSave();
            }
            currentNode = (Integer) selectedNodeDef.getSelectedItem();
            currentNodeDef = DataLists.getNodeDefinitions().get(currentNode);

            currentNodeDef = DataLists.getNodeDefinitions().get((Integer) selectedNodeDef.getSelectedItem());
            System.out.println(DataLists.getNodeDefinitions().keySet());
            System.out.println("Current Node: " + currentNode);
            System.out.println(currentNodeDef);

            startOrbitTxt.setText(Integer.toHexString(currentNodeDef.getSentryStartOrbitSpeed() & 0x0000ffff));
            refireDelayTxt.setText(Integer.toHexString(currentNodeDef.getSentryRefireDelay() & 0x0000ffff));
            deathSpeedIncreaseTxt.setText(Integer.toHexString(currentNodeDef.getSentrySpeedIncrement() & 0x0000ffff));
            maxOrbitSpeedTxt.setText(Integer.toHexString(currentNodeDef.getSentryMaxSpeed() & 0x0000ffff));
            proximityCloseTxt.setText(Integer.toHexString(currentNodeDef.getSentryNodeFarDistance() & 0x0000ffff));
            proximityFarTxt.setText(Integer.toHexString(currentNodeDef.getSentryNodeCloseDistance() & 0x0000ffff));
            travelSpeedTxt.setText(Integer.toHexString(currentNodeDef.getSentrySpeed() & 0x0000ffff));
            numSentriesTxt.setText(Integer.toHexString(currentNodeDef.getSentryCount() & 0x0000ffff));
            field0Txt.setText(Integer.toHexString(currentNodeDef.getUnknOne() & 0x0000ffff));
            field1Txt.setText(Integer.toHexString(currentNodeDef.getUnknTwo() & 0x0000ffff));
            field2Txt.setText(Integer.toHexString(currentNodeDef.getUnknThree() & 0x0000ffff));
            field3Txt.setText(Integer.toHexString(currentNodeDef.getUnknFour() & 0x0000ffff));
            damageNodeRefireIncreaseTxt.setText(Integer.toHexString(currentNodeDef.getNodeHealthRefireAdvance() & 0x0000ffff));
            if(saveButton.isEnabled())
                saveButton.setEnabled(false);
            changesMade = false;
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

