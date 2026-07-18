package aneurysm.ui.projectileEd;


import aneurysm.io.AudioManager;
import aneurysm.structures.ProjectileDefinition;
import aneurysm.ui.DataLists;
import aneurysm.ui.NumericTextBox;
import aneurysm.ui.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class ProjectileEditor extends JPanel implements FocusListener, ItemListener, ActionListener {

    private static final long serialVersionUID = -8635012649987300945L;
    private final Window host;
    private boolean changesMade;
    private int currentProjectile;
    private JLabel field0Lbl, field1Lbl, damageLbl, spriteLbl, soundLbl, speedLbl, behaviorLbl, fireDelayLbl, refireDelayLbl, selectedLbl, spritePreviewLbl;
    private NumericTextBox field0Txt, field1Txt, damageTxt, speedTxt, behaviorTxt, fireDelayTxt, refireDelayTxt;
    JComboBox<Integer> selectedProjectile, selectedSprite, selectedSound;
    private HashMap<Integer, Byte[][]> spriteCache;
    private JButton saveButton, previewSound;
    private ProjectileDefinition selectedProjectileDef;

    private void pollToSave() {
        int result = -1;
        if (changesMade) {
            result = JOptionPane.showConfirmDialog(this, "Save Changes?", "You have unsaved changes.  Would you like to save them?", JOptionPane.YES_NO_CANCEL_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                Window.getReader().writeProjectileDefinition(currentProjectile, selectedProjectileDef);
            }
        }
        changesMade = false;
        saveButton.setEnabled(false);
    }

    public ProjectileEditor(Window host) {
        this.setLayout(new GridLayout(4, 6));
        this.host = host;
        setupComponents();
    }

    private BufferedImage buildSpritePreview(Byte[][] in) {
        BufferedImage out = new BufferedImage(2 * in.length, 2 * in[0].length, BufferedImage.TYPE_INT_RGB);
        Graphics g = out.createGraphics();
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[0].length; j++) {
                g.setColor(DataLists.getLevelPal()[in[i][j] + 32]);
                g.fillRect(2 * i, 2 * j, 2, 2);
            }
        }
        return out;
    }

    private void setupComponents() {
        spriteCache = DataLists.getProjectileSpriteCache();
        selectedProjectile = new JComboBox<>(DataLists.getProjectiles().keySet().toArray(new Integer[0]));
        selectedProjectile.setSize(64, 24);
        selectedProjectile.setLocation(736, 16);
        selectedProjectile.setEditable(false);
        selectedProjectile.addItemListener(this);

        currentProjectile = (Integer) (selectedProjectile.getSelectedItem());
        selectedProjectileDef = DataLists.getProjectiles().get((Integer) selectedProjectile.getSelectedItem());

        this.setSize(400, 200);
        field0Lbl = new JLabel("Field 0");
        field0Lbl.setLocation(16, 16);
        field0Lbl.setSize(48, 24);
        field0Txt = new NumericTextBox(Integer.toHexString(selectedProjectileDef.getUnknOne() & 0x0000ffff));
        field0Txt.setLocation(80, 16);
        field0Txt.setSize(48, 24);
        field0Txt.addFocusListener(this);

        field1Lbl = new JLabel("Field 1");
        field1Lbl.setLocation(16, 16);
        field1Lbl.setSize(48, 24);
        field1Txt = new NumericTextBox(Integer.toHexString(selectedProjectileDef.getUnknTwo() & 0x0000ffff));
        field1Txt.setLocation(80, 16);
        field1Txt.setSize(48, 24);
        field1Txt.addFocusListener(this);

        damageLbl = new JLabel("Damage");
        damageLbl.setLocation(16, 16);
        damageLbl.setSize(48, 24);
        damageTxt = new NumericTextBox(Integer.toHexString(selectedProjectileDef.getDamage() & 0x0000ffff));
        damageTxt.setLocation(80, 16);
        damageTxt.setSize(48, 24);
        damageTxt.addFocusListener(this);

        spriteLbl = new JLabel("Sprite");
        spriteLbl.setLocation(0, 0);
        spriteLbl.setSize(48, 24);
        spritePreviewLbl = new JLabel();
        spritePreviewLbl.setHorizontalAlignment(JLabel.CENTER);

        System.out.println("projectile sprite offset: " + selectedProjectileDef.getSpriteOffs() + "\n projectile sprite cache: " + DataLists.getProjectileSpriteCache());

        spritePreviewLbl.setIcon(new ImageIcon(buildSpritePreview(DataLists.getProjectileSpriteCache().get(selectedProjectileDef.getSpriteOffs()))));
        selectedSprite = new JComboBox<>(spriteCache.keySet().toArray(new Integer[0]));
        selectedSprite.addItemListener(this);

        soundLbl = new JLabel("Sound");
        soundLbl.setLocation(0, 0);
        soundLbl.setSize(48, 24);
        previewSound = new JButton("Preview Sound");
        previewSound.addActionListener(this);

        selectedSound = new JComboBox<>(DataLists.getDigitalSounds().keySet().toArray(new Integer[0]));
        selectedSound.setSelectedItem(selectedProjectileDef.getSoundId());
        selectedSound.addItemListener(this);

        speedLbl = new JLabel("Speed");
        speedLbl.setLocation(0, 0);
        speedLbl.setSize(48, 24);
        speedTxt = new NumericTextBox(Integer.toHexString(selectedProjectileDef.getSpeed() & 0x0000ffff));
        speedTxt.setLocation(0, 0);
        speedTxt.setSize(48, 24);
        speedTxt.addFocusListener(this);

        behaviorLbl = new JLabel("Behavior");
        behaviorLbl.setLocation(0, 0);
        behaviorLbl.setSize(48, 24);
        behaviorTxt = new NumericTextBox(Integer.toHexString(selectedProjectileDef.getThingId() & 0x0000ffff));
        behaviorTxt.setLocation(0, 0);
        behaviorTxt.setSize(48, 24);
        behaviorTxt.addFocusListener(this);

        fireDelayLbl = new JLabel("Fire Delay");
        fireDelayLbl.setLocation(0, 0);
        fireDelayLbl.setSize(48, 24);
        fireDelayTxt = new NumericTextBox(Integer.toHexString(selectedProjectileDef.getFireDelay() & 0x0000ffff));
        fireDelayTxt.setLocation(0, 0);
        fireDelayTxt.setSize(48, 24);
        fireDelayTxt.addFocusListener(this);

        refireDelayLbl = new JLabel("Refire Delay");
        refireDelayLbl.setLocation(0, 0);
        refireDelayLbl.setSize(48, 24);
        refireDelayTxt = new NumericTextBox(Integer.toHexString(selectedProjectileDef.getHoldFireDelay() & 0x0000ffff));
        refireDelayTxt.setLocation(0, 0);
        refireDelayTxt.setSize(48, 24);
        refireDelayTxt.addFocusListener(this);

        selectedLbl = new JLabel("Selected Projectile");

        saveButton = new JButton("Save");
        saveButton.setEnabled(false);
        saveButton.addActionListener(this);

        this.add(field0Lbl);
        this.add(field0Txt);
        this.add(field1Lbl);
        this.add(field1Txt);
        this.add(damageLbl);
        this.add(damageTxt);
        this.add(spriteLbl);
        this.add(selectedSprite);
        this.add(spritePreviewLbl);
        this.add(soundLbl);
        this.add(selectedSound);
        this.add(previewSound);
        this.add(speedLbl);
        this.add(speedTxt);
        this.add(behaviorLbl);
        this.add(behaviorTxt);
        this.add(fireDelayLbl);
        this.add(fireDelayTxt);
        this.add(refireDelayLbl);
        this.add(refireDelayTxt);
        this.add(selectedLbl);
        this.add(selectedProjectile);
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

        if (e.getSource() == field0Txt) {

            txtShort = Short.parseShort(field0Txt.getText(), 16);
            if (txtShort != DataLists.getProjectiles().get(currentProjectile).getUnknOne()) {
                changesMade = true;
                selectedProjectileDef.setUnknOne(txtShort);
            }
        }
        if (e.getSource() == field1Txt) {
            txtShort = Short.parseShort(field1Txt.getText(), 16);
            if (txtShort != DataLists.getProjectiles().get(currentProjectile).getUnknTwo()) {
                changesMade = true;
                selectedProjectileDef.setUnknTwo(txtShort);
            }
        }
        if (e.getSource() == damageTxt) {
            txtShort = Short.parseShort(damageTxt.getText(), 16);
            if (txtShort != DataLists.getProjectiles().get(currentProjectile).getDamage()) {
                changesMade = true;
                selectedProjectileDef.setDamage(txtShort);
            }
        }
        if (e.getSource() == speedTxt) {
            txtShort = Short.parseShort(speedTxt.getText(), 16);
            if (txtShort != DataLists.getProjectiles().get(currentProjectile).getSpeed()) {
                changesMade = true;
                selectedProjectileDef.setSpeed(txtShort);
            }
        }
        if (e.getSource() == behaviorTxt) {
            txtShort = Short.parseShort(behaviorTxt.getText(), 16);
            if (txtShort != DataLists.getProjectiles().get(currentProjectile).getThingId()) {
                changesMade = true;
                selectedProjectileDef.setThingId(txtShort);
            }
        }
        if (e.getSource() == fireDelayTxt) {
            txtShort = Short.parseShort(fireDelayTxt.getText(), 16);
            if (txtShort != DataLists.getProjectiles().get(currentProjectile).getFireDelay()) {
                changesMade = true;
                selectedProjectileDef.setFireDelay(txtShort);
            }
        }
        if (e.getSource() == refireDelayTxt) {
            txtShort = Short.parseShort(refireDelayTxt.getText(), 16);
            if (txtShort != DataLists.getProjectiles().get(currentProjectile).getHoldFireDelay()) {
                changesMade = true;
                selectedProjectileDef.setHoldFireDelay(txtShort);
            }
        }

        if (changesMade) {
            saveButton.setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == previewSound) {
            AudioManager.playSound(DataLists.getDigitalSounds().get(selectedProjectileDef.getSoundId()));
        }

        if(e.getSource() == saveButton) {
            Window.getReader().writeProjectileDefinition(currentProjectile, selectedProjectileDef);
            changesMade = false;
            saveButton.setEnabled(false);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        if (e.getSource() == selectedSound && e.getStateChange() == ItemEvent.SELECTED) {
            selectedProjectileDef.setSoundId((Integer) selectedSound.getSelectedItem());
            changesMade = true;
            saveButton.setEnabled(true);
        }

        if (e.getSource() == selectedSprite && e.getStateChange() == ItemEvent.SELECTED) {
            if ((int) selectedSprite.getSelectedItem() != selectedProjectileDef.getSpriteOffs()) {
                selectedProjectileDef.setSpriteOffs((Integer) selectedSprite.getSelectedItem());
                spritePreviewLbl.setIcon(new ImageIcon(buildSpritePreview(DataLists.getProjectileSpriteCache().get(selectedProjectileDef.getSpriteOffs()))));
                changesMade = true;
                saveButton.setEnabled(true);
            }
        }

        if (e.getSource() == selectedProjectile && e.getStateChange() == ItemEvent.SELECTED) {
            if (changesMade) {
                pollToSave();
            }
            currentProjectile = (Integer) selectedProjectile.getSelectedItem();
            selectedProjectileDef = DataLists.getProjectiles().get(currentProjectile);

            System.out.println(selectedProjectileDef);

            field0Txt.setText(Integer.toHexString(selectedProjectileDef.getUnknOne() & 0x0000ffff));
            field1Txt.setText(Integer.toHexString(selectedProjectileDef.getUnknTwo() & 0x0000ffff));
            damageTxt.setText(Integer.toHexString(selectedProjectileDef.getDamage() & 0x0000ffff));
            selectedSprite.setSelectedItem(selectedProjectileDef.getSpriteOffs());
            spritePreviewLbl.setIcon(new ImageIcon(buildSpritePreview(DataLists.getProjectileSpriteCache().get(selectedProjectileDef.getSpriteOffs()))));
            selectedSound.setSelectedItem(selectedProjectileDef.getSoundId());
            speedTxt.setText(Integer.toHexString(selectedProjectileDef.getSpeed() & 0x0000ffff));
            behaviorTxt.setText(Integer.toHexString(selectedProjectileDef.getThingId() & 0x0000ffff));
            fireDelayTxt.setText(Integer.toHexString(selectedProjectileDef.getFireDelay() & 0x0000ffff));
            refireDelayTxt.setText(Integer.toHexString(selectedProjectileDef.getHoldFireDelay() & 0x0000ffff));

            if (saveButton.isEnabled())
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

