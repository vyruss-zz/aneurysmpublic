package aneurysm.ui.actorEd.edit;

import aneurysm.io.AudioManager;
import aneurysm.structures.ActorDefinition;
import aneurysm.ui.DataLists;
import aneurysm.ui.NumericTextBox;
import aneurysm.ui.Window;
import aneurysm.ui.actorEd.ui.SpriteSheetCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class ActorEditor extends JPanel implements FocusListener, ActionListener, ItemListener {

    private static final long serialVersionUID = -8635012649987300945L;
    private final Window host;
    private boolean changesMade;
    private int currentActor = 0;
    private JLabel shotDelayLbl, accuracyLbl, projectileLbl, moveSpeedLbl, moveDelayTimeLbl, actionDelayTimeLbl, unknTimerLbl,
            projectileHeightLbl, hitPointsLbl, hitStunMoveDelayTimeLbl, hitStunFireDelayLbl, hitDelayTimeLbl, alertSoundLbl,
            painSoundLbl, deathSoundLbl, hitRadiusLbl, wallRadiusLbl, knockbackAngleLbl, knockbackDistanceLbl, playerDistanceLbl,
            idleFrameLbl, walkAnimLbl, painAnimLbl, dieAnimLbl, selectedLabel, paddingLbl;
    private NumericTextBox shotDelayTxt, accuracyTxt, moveSpeedTxt, moveDelayTimeTxt, actionDelayTimeTxt,
            unknTimerTxt, projectileHeightTxt, hitPointsTxt, hitStunMoveDelayTimeTxt, hitStunFireDelayTxt, hitDelayTimeTxt,
            hitRadiusTxt, wallRadiusTxt, knockbackAngleTxt, knockbackDistanceTxt,
            playerDistanceTxt;
    JComboBox<Integer> selectedActor, selectedProjectile, selectedAlertSound, selectedHitSound, selectedDieSound, selectedIdleFrame, selectedWalkFrame, selectedHitFrame, selectedDieFrame;
    private HashMap<Integer, Byte[][]> spriteCache;
    private JButton saveButton, previewAlertSound, previewHitSound, previewDieSound;
    private JFrame spritePreview;
    private SpriteSheetCanvas spritePreviewPanel;
    private ActorDefinition currentActorDef = DataLists.getActorDefinitions().get(currentActor);

    private void pollToSave() {
        int result = -1;
        if (changesMade) {
            result = JOptionPane.showConfirmDialog(this, "Save Changes?", "You have unsaved changes.  Would you like to save them?", JOptionPane.YES_NO_CANCEL_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                Window.getReader().writeActorDefinition(currentActor, currentActorDef);
            }
        }
        changesMade = false;
        saveButton.setEnabled(false);
    }

    public ActorEditor(Window host) {
        this.setLayout(new GridLayout(6, 8));
        this.host = host;
        setupComponents();
    }

    public ActorDefinition getCurrentActorDef() {
        return currentActorDef;
    }

    public void resizeSpritePreviewWindow(int w, int h) {
        spritePreview.setSize(w, h);
    }

    private JFrame buildSpritePreviewWindow() {
        JFrame frame = new JFrame("Sprite Preview");

        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        return frame;
    }

    private void setupComponents() {
        spriteCache = DataLists.getActorSpriteCache();
        Integer[] entries = new Integer[DataLists.getActorDefinitions().size()];
        for (int i = 0; i < entries.length; i++) {
            entries[i] = i;
        }
        this.setSize(400, 240);
        shotDelayLbl = new JLabel("Fire Delay");
        shotDelayLbl.setLocation(16, 16);
        shotDelayLbl.setSize(48, 24);
        shotDelayTxt = new NumericTextBox(Integer.toHexString(currentActorDef.getShotDelay() & 0x0000ffff));
        shotDelayTxt.setLocation(80, 16);
        shotDelayTxt.setSize(48, 24);
        shotDelayTxt.addFocusListener(this);

        accuracyLbl = new JLabel("Accuracy");
        accuracyLbl.setLocation(136, 16);
        accuracyLbl.setSize(48, 24);
        accuracyTxt = new NumericTextBox(Integer.toHexString(currentActorDef.getAccuracy() & 0x0000ffff));
        accuracyTxt.setLocation(160, 16);
        accuracyTxt.setSize(48, 24);
        accuracyTxt.addFocusListener(this);

        projectileLbl = new JLabel("Projectile");
        projectileLbl.setLocation(0, 0);
        projectileLbl.setSize(48, 24);
        selectedProjectile = new JComboBox<>(DataLists.getProjectiles().keySet().toArray(new Integer[0]));
        selectedProjectile.setSelectedItem(currentActorDef.getProjectile());
        selectedProjectile.addItemListener(this);

        moveSpeedLbl = new JLabel("Move Speed");
        moveSpeedLbl.setLocation(0, 0);
        moveSpeedLbl.setSize(48, 24);
        moveSpeedTxt = new NumericTextBox(Integer.toHexString(currentActorDef.getMoveSpeed() & 0x0000ffff));
        moveSpeedTxt.setLocation(0, 0);
        moveSpeedTxt.setSize(48, 24);
        moveSpeedTxt.addFocusListener(this);

        moveDelayTimeLbl = new JLabel("Move Delay");
        moveDelayTimeLbl.setLocation(0, 0);
        moveDelayTimeLbl.setSize(48, 24);
        moveDelayTimeTxt = new NumericTextBox(Integer.toHexString(currentActorDef.getMoveDelay() & 0x0000ffff));
        moveDelayTimeTxt.setLocation(0, 0);
        moveDelayTimeTxt.setSize(48, 24);
        moveDelayTimeTxt.addFocusListener(this);

        actionDelayTimeLbl = new JLabel("Action Delay");
        actionDelayTimeLbl.setLocation(0, 0);
        actionDelayTimeLbl.setSize(48, 24);
        actionDelayTimeTxt = new NumericTextBox(Integer.toHexString(currentActorDef.getActionDelay() & 0x0000ffff));
        actionDelayTimeTxt.setLocation(0, 0);
        actionDelayTimeTxt.setSize(48, 24);
        actionDelayTimeTxt.addFocusListener(this);

        unknTimerLbl = new JLabel("Unknown Field");
        unknTimerLbl.setLocation(0, 0);
        unknTimerLbl.setSize(48, 24);
        unknTimerTxt = new NumericTextBox(Integer.toHexString(currentActorDef.getUnknDelay() & 0x0000ffff));
        unknTimerTxt.setLocation(0, 0);
        unknTimerTxt.setSize(48, 24);
        unknTimerTxt.addFocusListener(this);

        projectileHeightLbl = new JLabel("Projectile Origin");
        projectileHeightLbl.setLocation(0, 0);
        projectileHeightLbl.setSize(48, 24);
        projectileHeightTxt = new NumericTextBox(Integer.toHexString(currentActorDef.getProjectileYOrg() & 0x0000ffff));
        projectileHeightTxt.setLocation(0, 0);
        projectileHeightTxt.setSize(48, 24);
        projectileHeightTxt.addFocusListener(this);

        hitPointsLbl = new JLabel("Hit Points");
        hitPointsLbl.setLocation(0, 0);
        hitPointsLbl.setSize(48, 24);
        hitPointsTxt = new NumericTextBox(Integer.toHexString(currentActorDef.getHitPoints() & 0x0000ffff));
        hitPointsTxt.setLocation(0, 0);
        hitPointsTxt.setSize(48, 24);
        hitPointsTxt.addFocusListener(this);

        hitStunMoveDelayTimeLbl = new JLabel("Hit Move Delay");
        hitStunMoveDelayTimeLbl.setLocation(0, 0);
        hitStunMoveDelayTimeLbl.setSize(48, 24);
        hitStunMoveDelayTimeTxt = new NumericTextBox(Integer.toHexString(currentActorDef.getHitStunMoveTime() & 0x0000ffff));
        hitStunMoveDelayTimeTxt.setLocation(0, 0);
        hitStunMoveDelayTimeTxt.setSize(48, 24);
        hitStunMoveDelayTimeTxt.addFocusListener(this);

        hitStunFireDelayLbl = new JLabel("Hit Fire Delay");
        hitStunFireDelayLbl.setLocation(0, 0);
        hitStunFireDelayLbl.setSize(48, 24);
        hitStunFireDelayTxt = new NumericTextBox(Integer.toHexString(currentActorDef.getHitStunFireDelayTime() & 0x0000ffff));
        hitStunFireDelayTxt.setLocation(0, 0);
        hitStunFireDelayTxt.setSize(48, 24);
        hitStunFireDelayTxt.addFocusListener(this);

        hitDelayTimeLbl = new JLabel("Hit Delay");
        hitDelayTimeLbl.setLocation(0, 0);
        hitDelayTimeLbl.setSize(48, 24);
        hitDelayTimeTxt = new NumericTextBox(Integer.toHexString(currentActorDef.getHitDelayTime() & 0x0000ffff));
        hitDelayTimeTxt.setLocation(0, 0);
        hitDelayTimeTxt.setSize(48, 24);
        hitDelayTimeTxt.addFocusListener(this);

        alertSoundLbl = new JLabel("Alert Sound");
        alertSoundLbl.setLocation(0, 0);
        alertSoundLbl.setSize(48, 24);
        selectedAlertSound = new JComboBox<>(DataLists.getDigitalSounds().keySet().toArray(new Integer[0]));
        selectedAlertSound.setSelectedItem(currentActorDef.getAlertSoundId());
        selectedAlertSound.addItemListener(this);
        previewAlertSound = new JButton("Play Sound");
        previewAlertSound.addActionListener(this);

        painSoundLbl = new JLabel("Pain Sound");
        painSoundLbl.setLocation(0, 0);
        painSoundLbl.setSize(48, 24);
        selectedHitSound = new JComboBox<>(DataLists.getDigitalSounds().keySet().toArray(new Integer[0]));
        selectedHitSound.setSelectedItem(currentActorDef.getHitSoundId());
        selectedHitSound.addItemListener(this);
        previewHitSound = new JButton("Play Sound");
        previewHitSound.addActionListener(this);

        deathSoundLbl = new JLabel("Death Sound");
        deathSoundLbl.setLocation(0, 0);
        deathSoundLbl.setSize(48, 24);
        selectedDieSound = new JComboBox<>(DataLists.getDigitalSounds().keySet().toArray(new Integer[0]));
        selectedDieSound.setSelectedItem(currentActorDef.getDeathSoundId());
        selectedDieSound.addItemListener(this);
        previewDieSound = new JButton("Play Sound");
        previewDieSound.addActionListener(this);

        hitRadiusLbl = new JLabel("Hit Radius");
        hitRadiusLbl.setLocation(0, 0);
        hitRadiusLbl.setSize(48, 24);
        hitRadiusTxt = new NumericTextBox(Integer.toHexString(currentActorDef.getDamageHitRadius() & 0x0000ffff));
        hitRadiusTxt.setLocation(0, 0);
        hitRadiusTxt.setSize(48, 24);
        hitRadiusTxt.addFocusListener(this);

        wallRadiusLbl = new JLabel("Wall Radius");
        wallRadiusLbl.setLocation(0, 0);
        wallRadiusLbl.setSize(48, 24);
        wallRadiusTxt = new NumericTextBox(Integer.toHexString(currentActorDef.getWallRadius() & 0x0000ffff));
        wallRadiusTxt.setLocation(0, 0);
        wallRadiusTxt.setSize(48, 24);
        wallRadiusTxt.addFocusListener(this);
        paddingLbl = new JLabel("");

        knockbackAngleLbl = new JLabel("Knockback Angle");
        knockbackAngleLbl.setLocation(0, 0);
        knockbackAngleLbl.setSize(48, 24);
        knockbackAngleTxt = new NumericTextBox(Integer.toHexString(currentActorDef.getKnockbackAngle() & 0x0000ffff));
        knockbackAngleTxt.setLocation(0, 0);
        knockbackAngleTxt.setSize(48, 24);
        knockbackAngleTxt.addFocusListener(this);

        knockbackDistanceLbl = new JLabel("Knockback Distance");
        knockbackDistanceLbl.setLocation(0, 0);
        knockbackDistanceLbl.setSize(48, 24);
        knockbackDistanceTxt = new NumericTextBox(Integer.toHexString(currentActorDef.getKnockbackDistance() & 0x0000ffff));
        knockbackDistanceTxt.setLocation(0, 0);
        knockbackDistanceTxt.setSize(48, 24);
        knockbackDistanceTxt.addFocusListener(this);

        playerDistanceLbl = new JLabel("Player Distance");
        playerDistanceLbl.setLocation(0, 0);
        playerDistanceLbl.setSize(48, 24);
        playerDistanceTxt = new NumericTextBox(Integer.toHexString(currentActorDef.getPlayerDistanceKept() & 0x0000ffff));
        playerDistanceTxt.setLocation(0, 0);
        playerDistanceTxt.setSize(48, 24);
        playerDistanceTxt.addFocusListener(this);

        idleFrameLbl = new JLabel("Idle Frame");
        idleFrameLbl.setLocation(0, 0);
        idleFrameLbl.setSize(48, 24);
        selectedIdleFrame = new JComboBox<>(DataLists.getEditorActorInfo().getActorSpriteDefs().keySet().toArray(new Integer[0]));
        selectedIdleFrame.setSelectedItem(currentActorDef.getIdleSpriteFrameOffs());
        selectedIdleFrame.addItemListener(this);

        walkAnimLbl = new JLabel("Walk Anim");
        walkAnimLbl.setLocation(0, 0);
        walkAnimLbl.setSize(48, 24);
        selectedWalkFrame = new JComboBox<>(DataLists.getEditorActorInfo().getActorSpriteDefs().keySet().toArray(new Integer[0]));
        selectedWalkFrame.setSelectedItem(currentActorDef.getWalkAnimOffs());
        selectedWalkFrame.addItemListener(this);

        painAnimLbl = new JLabel("Hit Anim");
        painAnimLbl.setLocation(0, 0);
        painAnimLbl.setSize(48, 24);
        selectedHitFrame = new JComboBox<>(DataLists.getEditorActorInfo().getActorSpriteDefs().keySet().toArray(new Integer[0]));
        selectedHitFrame.setSelectedItem(currentActorDef.getHitAnimOffs());
        selectedHitFrame.addItemListener(this);

        dieAnimLbl = new JLabel("Death Anim");
        dieAnimLbl.setLocation(0, 0);
        dieAnimLbl.setSize(48, 24);
        selectedDieFrame = new JComboBox<>(DataLists.getEditorActorInfo().getActorSpriteDefs().keySet().toArray(new Integer[0]));
        selectedDieFrame.setSelectedItem(currentActorDef.getDieAnimOffs());
        selectedDieFrame.addItemListener(this);

        selectedLabel = new JLabel("Selected Actor");

        selectedActor = new JComboBox<>(entries);
        selectedActor.setEditable(false);
        selectedActor.addItemListener(this);

        saveButton = new JButton("Save");
        saveButton.setEnabled(false);
        saveButton.addActionListener(this);

        this.add(shotDelayLbl);
        this.add(shotDelayTxt);
        this.add(accuracyLbl);
        this.add(accuracyTxt);
        this.add(projectileLbl);
        this.add(selectedProjectile);
        this.add(moveSpeedLbl);
        this.add(moveSpeedTxt);
        this.add(moveDelayTimeLbl);
        this.add(moveDelayTimeTxt);
        this.add(actionDelayTimeLbl);
        this.add(actionDelayTimeTxt);
        this.add(unknTimerLbl);
        this.add(unknTimerTxt);
        this.add(projectileHeightLbl);
        this.add(projectileHeightTxt);
        this.add(hitPointsLbl);
        this.add(hitPointsTxt);
        this.add(hitStunMoveDelayTimeLbl);
        this.add(hitStunMoveDelayTimeTxt);
        this.add(hitStunFireDelayLbl);
        this.add(hitStunFireDelayTxt);
        this.add(hitDelayTimeLbl);
        this.add(hitDelayTimeTxt);
        this.add(alertSoundLbl);
        this.add(selectedAlertSound);
        this.add(previewAlertSound);
        this.add(painSoundLbl);
        this.add(selectedHitSound);
        this.add(previewHitSound);
        this.add(deathSoundLbl);
        this.add(selectedDieSound);
        this.add(previewDieSound);
        this.add(hitRadiusLbl);
        this.add(hitRadiusTxt);
        this.add(wallRadiusLbl);
        this.add(wallRadiusTxt);
        this.add(paddingLbl);
        this.add(knockbackAngleLbl);
        this.add(knockbackAngleTxt);
        this.add(knockbackDistanceLbl);
        this.add(knockbackDistanceTxt);
        this.add(playerDistanceLbl);
        this.add(playerDistanceTxt);
        this.add(idleFrameLbl);
        this.add(selectedIdleFrame);
        this.add(walkAnimLbl);
        this.add(selectedWalkFrame);
        this.add(painAnimLbl);
        this.add(selectedHitFrame);
        this.add(dieAnimLbl);
        this.add(selectedDieFrame);
        this.add(selectedLabel);
        this.add(selectedActor);
        this.add(saveButton);

        spritePreview = buildSpritePreviewWindow();
        spritePreviewPanel = new SpriteSheetCanvas(this);
        spritePreview.add(spritePreviewPanel);

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
        if (e.getSource() == shotDelayTxt) {

            txtShort = Short.parseShort(shotDelayTxt.getText(), 16);
            if (txtShort != DataLists.getActorDefinitions().get(currentActor).getShotDelay()) {
                changesMade = true;
                currentActorDef.setShotDelay(txtShort);
            }
        }
        if (e.getSource() == accuracyTxt) {
            txtShort = Short.parseShort(accuracyTxt.getText(), 16);
            if (txtShort != DataLists.getActorDefinitions().get(currentActor).getShotDelay()) {
                changesMade = true;
                currentActorDef.setAccuracy(txtShort);
            }
        }
        if (e.getSource() == moveSpeedTxt) {
            txtShort = Short.parseShort(moveSpeedTxt.getText(), 16);
            if (txtShort != DataLists.getActorDefinitions().get(currentActor).getShotDelay()) {
                changesMade = true;
                currentActorDef.setMoveSpeed(txtShort);
            }
        }
        if (e.getSource() == moveDelayTimeTxt) {
            txtShort = Short.parseShort(moveDelayTimeTxt.getText(), 16);
            if (txtShort != DataLists.getActorDefinitions().get(currentActor).getShotDelay()) {
                changesMade = true;
                currentActorDef.setMoveDelay(txtShort);
            }
        }
        if (e.getSource() == actionDelayTimeTxt) {
            txtShort = Short.parseShort(actionDelayTimeTxt.getText(), 16);
            if (txtShort != DataLists.getActorDefinitions().get(currentActor).getShotDelay()) {
                changesMade = true;
                currentActorDef.setActionDelay(txtShort);
            }
        }
        if (e.getSource() == unknTimerTxt) {
            txtShort = Short.parseShort(unknTimerTxt.getText(), 16);
            if (txtShort != DataLists.getActorDefinitions().get(currentActor).getShotDelay()) {
                changesMade = true;
                currentActorDef.setUnknDelay(txtShort);
            }
        }
        if (e.getSource() == projectileHeightTxt) {
            txtShort = Short.parseShort(projectileHeightTxt.getText(), 16);
            if (txtShort != DataLists.getActorDefinitions().get(currentActor).getShotDelay()) {
                changesMade = true;
                currentActorDef.setProjectileYOrg(txtShort);
            }
        }
        if (e.getSource() == hitPointsTxt) {
            txtShort = Short.parseShort(hitPointsTxt.getText(), 16);
            if (txtShort != DataLists.getActorDefinitions().get(currentActor).getShotDelay()) {
                changesMade = true;
                currentActorDef.setHitPoints(txtShort);
            }
        }
        if (e.getSource() == hitStunMoveDelayTimeTxt) {
            txtShort = Short.parseShort(hitStunMoveDelayTimeTxt.getText(), 16);
            if (txtShort != DataLists.getActorDefinitions().get(currentActor).getShotDelay()) {
                changesMade = true;
                currentActorDef.setHitStunMoveTime(txtShort);
            }
        }
        if (e.getSource() == hitStunFireDelayTxt) {
            txtShort = Short.parseShort(hitStunFireDelayTxt.getText(), 16);
            if (txtShort != DataLists.getActorDefinitions().get(currentActor).getShotDelay()) {
                changesMade = true;
                currentActorDef.setHitStunFireDelayTime(txtShort);
            }
        }
        if (e.getSource() == hitDelayTimeTxt) {
            txtShort = Short.parseShort(hitDelayTimeTxt.getText(), 16);
            if (txtShort != DataLists.getActorDefinitions().get(currentActor).getShotDelay()) {
                changesMade = true;
                currentActorDef.setHitDelayTime(txtShort);
            }
        }
        if (e.getSource() == hitRadiusTxt) {
            txtShort = Short.parseShort(hitRadiusTxt.getText(), 16);
            if (txtShort != DataLists.getActorDefinitions().get(currentActor).getShotDelay()) {
                changesMade = true;
                currentActorDef.setDamageHitRadius(txtShort);
            }
        }
        if (e.getSource() == wallRadiusTxt) {
            txtShort = Short.parseShort(wallRadiusTxt.getText(), 16);
            if (txtShort != DataLists.getActorDefinitions().get(currentActor).getShotDelay()) {
                changesMade = true;
                currentActorDef.setWallRadius(txtShort);
            }
        }
        if (e.getSource() == knockbackAngleTxt) {
            txtShort = Short.parseShort(knockbackAngleTxt.getText(), 16);
            if (txtShort != DataLists.getActorDefinitions().get(currentActor).getShotDelay()) {
                changesMade = true;
                currentActorDef.setKnockbackAngle(txtShort);
            }
        }
        if (e.getSource() == knockbackDistanceTxt) {
            txtShort = Short.parseShort(knockbackDistanceTxt.getText(), 16);
            if (txtShort != DataLists.getActorDefinitions().get(currentActor).getShotDelay()) {
                changesMade = true;
                currentActorDef.setKnockbackDistance(txtShort);
            }
        }
        if (e.getSource() == playerDistanceTxt) {
            txtShort = Short.parseShort(playerDistanceTxt.getText(), 16);
            if (txtShort != DataLists.getActorDefinitions().get(currentActor).getShotDelay()) {
                changesMade = true;
                currentActorDef.setPlayerDistanceKept(txtShort);
            }
        }
        if (changesMade) {
            saveButton.setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            Window.getReader().writeActorDefinition(currentActor, currentActorDef);
            changesMade = false;
            saveButton.setEnabled(false);
        }
        if (e.getSource() == previewAlertSound) {
            AudioManager.playSound(DataLists.getDigitalSounds().get(currentActorDef.getAlertSoundId()));
        }
        if (e.getSource() == previewHitSound) {

            AudioManager.playSound(DataLists.getDigitalSounds().get(currentActorDef.getHitSoundId()));
        }
        if (e.getSource() == previewDieSound) {
            AudioManager.playSound(DataLists.getDigitalSounds().get(currentActorDef.getDeathSoundId()));
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        if(e.getSource() == this.selectedProjectile && e.getStateChange() == ItemEvent.SELECTED) {
            currentActorDef.setProjectile((Integer) selectedProjectile.getSelectedItem());
            spritePreviewPanel.updateSpritePreviewPanel();
            changesMade = true;
        }

        if (e.getSource() == this.selectedAlertSound && e.getStateChange() == ItemEvent.SELECTED) {
            currentActorDef.setAlertSoundId((Integer) selectedAlertSound.getSelectedItem());
            changesMade = true;
        }

        if (e.getSource() == this.selectedHitSound && e.getStateChange() == ItemEvent.SELECTED) {
            currentActorDef.setHitSoundId((Integer) selectedHitSound.getSelectedItem());
            changesMade = true;
        }

        if (e.getSource() == this.selectedDieSound && e.getStateChange() == ItemEvent.SELECTED) {
            currentActorDef.setDeathSoundId((Integer) selectedDieSound.getSelectedItem());
            changesMade = true;
        }

        if (e.getSource() == this.selectedIdleFrame && e.getStateChange() == ItemEvent.SELECTED) {
            currentActorDef.setIdleSpriteFrameOffs((Integer) selectedIdleFrame.getSelectedItem());
            spritePreviewPanel.updateSpritePreviewPanel();
            changesMade = true;
        }

        if (e.getSource() == this.selectedWalkFrame && e.getStateChange() == ItemEvent.SELECTED) {

            currentActorDef.setWalkAnimOffs((Integer) selectedWalkFrame.getSelectedItem());
            spritePreviewPanel.updateSpritePreviewPanel();
            changesMade = true;
        }

        if (e.getSource() == this.selectedHitFrame && e.getStateChange() == ItemEvent.SELECTED) {

            currentActorDef.setHitAnimOffs((Integer) selectedWalkFrame.getSelectedItem());
            spritePreviewPanel.updateSpritePreviewPanel();
            changesMade = true;
        }

        if (e.getSource() == this.selectedDieFrame && e.getStateChange() == ItemEvent.SELECTED) {
            currentActorDef.setDieAnimOffs((Integer) selectedDieFrame.getSelectedItem());
            spritePreviewPanel.updateSpritePreviewPanel();
            changesMade = true;
        }
        if (e.getSource() == this.selectedActor && e.getStateChange() == ItemEvent.SELECTED) {
            if (changesMade) {
                pollToSave();
            }
            currentActor = this.selectedActor.getSelectedIndex();
            currentActorDef = DataLists.getActorDefinitions().get(currentActor);

            System.out.printf("%s %08x %s", "actor offset", DataLists.getActorDefOffset(), currentActorDef);

            shotDelayTxt.setText(Integer.toHexString(currentActorDef.getShotDelay() & 0x0000ffff));
            accuracyTxt.setText(Integer.toHexString(currentActorDef.getAccuracy() & 0x0000ffff));
            selectedProjectile.setSelectedItem(Integer.toHexString(currentActorDef.getProjectile() & 0x00ffffff));
            moveSpeedTxt.setText(Integer.toHexString(currentActorDef.getMoveSpeed() & 0x0000ffff));
            moveDelayTimeTxt.setText(Integer.toHexString(currentActorDef.getMoveDelay() & 0x0000ffff));
            actionDelayTimeTxt.setText(Integer.toHexString(currentActorDef.getActionDelay() & 0x0000ffff));
            unknTimerTxt.setText(Integer.toHexString(currentActorDef.getUnknDelay() & 0x0000ffff));
            projectileHeightTxt.setText(Integer.toHexString(currentActorDef.getProjectileYOrg() & 0x0000ffff));
            hitPointsTxt.setText(Integer.toHexString(currentActorDef.getHitPoints() & 0x0000ffff));
            hitStunMoveDelayTimeTxt.setText(Integer.toHexString(currentActorDef.getHitStunMoveTime() & 0x0000ffff));
            hitStunFireDelayTxt.setText(Integer.toHexString(currentActorDef.getHitStunFireDelayTime() & 0x0000ffff));
            hitDelayTimeTxt.setText(Integer.toHexString(currentActorDef.getHitDelayTime() & 0x0000ffff));
            selectedAlertSound.setSelectedItem(Integer.toHexString(currentActorDef.getAlertSoundId() & 0x00ffffff));
            selectedHitSound.setSelectedItem(Integer.toHexString(currentActorDef.getHitSoundId() & 0x00ffffff));
            selectedDieSound.setSelectedItem(Integer.toHexString(currentActorDef.getDeathSoundId() & 0x00ffffff));
            hitRadiusTxt.setText(Integer.toHexString(currentActorDef.getDamageHitRadius() & 0x0000ffff));
            wallRadiusTxt.setText(Integer.toHexString(currentActorDef.getWallRadius() & 0x0000ffff));
            knockbackAngleTxt.setText(Integer.toHexString(currentActorDef.getKnockbackAngle() & 0x0000ffff));
            knockbackDistanceTxt.setText(Integer.toHexString(currentActorDef.getKnockbackDistance() & 0x0000ffff));
            playerDistanceTxt.setText(Integer.toHexString(currentActorDef.getPlayerDistanceKept() & 0x0000ffff));
            selectedIdleFrame.setSelectedItem(Integer.toHexString(currentActorDef.getIdleSpriteFrameOffs() & 0x00ffffff));
            selectedWalkFrame.setSelectedItem(Integer.toHexString(currentActorDef.getWalkAnimOffs() & 0x00ffffff));
            selectedHitFrame.setSelectedItem(Integer.toHexString(currentActorDef.getHitAnimOffs() & 0x00ffffff));
            selectedDieFrame.setSelectedItem(Integer.toHexString(currentActorDef.getDieAnimOffs() & 0x00ffffff));

            spritePreviewPanel.updateSpritePreviewPanel();
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
