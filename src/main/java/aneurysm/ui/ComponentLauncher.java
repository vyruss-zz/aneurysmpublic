package aneurysm.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import aneurysm.patch.enemyimporter.EnemyImporter;
import aneurysm.render.RenderControls;
import aneurysm.ui.actorEd.edit.ActorEditor;
import aneurysm.ui.controlEd.ControlEditor;
import aneurysm.ui.levHeadEd.LevelHeaderEditor;
import aneurysm.ui.mapviewer.MapViewer;
import aneurysm.ui.nodeEd.NodeEditor;
import aneurysm.ui.palEd.PaletteEditor;
import aneurysm.ui.projectileEd.ProjectileEditor;
import aneurysm.ui.sprEd.edit.SpriteEditor;
import aneurysm.ui.texEd.edit.TextureEditor;
import aneurysm.ui.tileMapEd.tileMapEditor;

public class ComponentLauncher implements ActionListener, ItemListener {

    private JMenuItem patchInCDEnemy;
    private JMenuItem extractData;
    private JMenuItem editActors;
    private JMenuItem editProjectiles;
    private JMenuItem editNodeDefs;
    private JMenuItem editLevelHeader;
    private JLabel zoomLabel;
    private JLabel mousePos;
    private JLabel gridSize;
    private JLabel modeLabel;
    private JLabel snapOn;
    private JButton editTextures;
    private JButton editSprites;
    private JButton editPalette;
    private JButton viewMiniMap;
    private JButton editTileMap;
    private JMenuItem config;
    private JComboBox<String> levelcb;
    private JCheckBox rotateChb;
    private JTextArea jt;
    private final Window host;
    private SelectPanel selectPanel;

    public void setSnapOn(String snapOn) {
        this.snapOn.setText("Grid Snap: " + snapOn);
    }

    public SelectPanel getSelectPanel() {
        return selectPanel;
    }

    public void setLevelSelection(boolean b) {
        levelcb.setEnabled(b);
    }

    public void setComboNumber(int num) {
        levelcb.setSelectedIndex(num);
    }

    public JCheckBox getRotateChb() {
        return rotateChb;
    }

    public void setModeLabel(String mode) {
        modeLabel.setText("Mode: " + mode);
    }

    public void setGridLabel(Integer level) {
        gridSize.setText("Grid: 1/" + level);
    }

    public void setZoomLabel(Integer level) {
        zoomLabel.setText("Zoom: " + (100 / level) + "%");
    }

    public void setMouseXLabel(Integer x, Integer y) {
        mousePos.setText("Position: (" + x + ", " + y + ")");
    }

    public void setTextAreaContents(String contents) {
        jt.setText(contents);
    }

    public ComponentLauncher(Window frame) {
        addComponents(frame);
        host = frame;
    }

    private void showOpenDialog(Window frame) {
        JFileChooser fc = new JFileChooser(Window.getReader().getConfig().getLocation());
        fc.setDialogTitle("Load File...");
        fc.addChoosableFileFilter(new FileNameExtensionFilter("BIN File", "bin"));
        fc.addChoosableFileFilter(new FileNameExtensionFilter("GEN File", "gen"));
        fc.addChoosableFileFilter(new FileNameExtensionFilter("MD File", "md"));
        fc.addChoosableFileFilter(new FileNameExtensionFilter("LEV File", "lev"));
        fc.addChoosableFileFilter(new FileNameExtensionFilter("All supported filetypes", "bin", "gen", "lev", "md"));
        int result = fc.showOpenDialog(null);
        if (result != JFileChooser.CANCEL_OPTION) {
            String f = fc.getSelectedFile().toString();
            if (!f.toLowerCase().contains(".lev") && !f.toLowerCase().contains(".bin") && !f.toLowerCase().contains(".gen") && !f.toLowerCase().contains(".md")) {
                JOptionPane.showMessageDialog(null, "Please select a valid File.", "Invalid File",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                host.getControls().clearSelection();
                Window.getReader().getConfig().setLocation(f);
                if (!fc.getSelectedFile().toString().toLowerCase().contains(".lev")) {
                    int resultCh = JOptionPane.showConfirmDialog(null,
                            ("The ROM file loaded has the corrupted textures present.  Would you like to patch them?"),
                            "Fix Corrupted Textures?", JOptionPane.YES_NO_OPTION);
                    if (resultCh == JOptionPane.YES_OPTION) {
                        Window.getReader().fixCorruptedROMTextures();
                    }

                }

                Window.getReader().readNewFile(f);

            }
            checkIfNeedCDEnemyPatched();
        } else {
            return;
        }

        levelcb.setEnabled(!DataLists.isCdOrCart());
        extractData.setVisible(!DataLists.isCdOrCart());
        try {
            host.getControls().loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupSelectPanel(Window frame) {
        selectPanel = new SelectPanel(frame);
        frame.add(selectPanel, BorderLayout.EAST);

    }

    private void addComponents(Window frame) {
        editPalette = new JButton("Edit Palette");
        editPalette.addActionListener(this);
        editSprites = new JButton("Edit Sprites");
        editSprites.addActionListener(this);
        viewMiniMap = new JButton("View MiniMap");
        viewMiniMap.addActionListener(this);
        editTileMap = new JButton("Edit Tilemaps");
        editTileMap.addActionListener(this);
        editTextures = new JButton("Edit Textures");
        editTextures.addActionListener(this);
        config = new JMenuItem("Configuration");
        config.addActionListener(this);
        patchInCDEnemy = new JMenuItem("Patch In CD Enemy");
        patchInCDEnemy.addActionListener(this);
        extractData = new JMenuItem("Extract Data");
        extractData.addActionListener(this);
        editActors = new JMenuItem("Edit Actors");
        editActors.addActionListener(this);
        editProjectiles = new JMenuItem("Edit Projectiles");
        editProjectiles.addActionListener(this);
        editNodeDefs = new JMenuItem("Edit Node Definitions");
        editNodeDefs.addActionListener(this);
        editLevelHeader = new JMenuItem("Edit Level Header");
        editLevelHeader.addActionListener(this);
        jt = new JTextArea("Nothing highlighted.");
        JMenuItem openOption = new JMenuItem("Open...");
        jt.setSize(frame.getWidth(), 32);
        jt.setFont(new Font("Courier", Font.PLAIN, 10));
        jt.setEditable(false);
        rotateChb = new JCheckBox("Rotate 90");
        rotateChb.setFocusable(false);
        JMenuBar toolBar = new JMenuBar();
        JMenuBar infoBar = new JMenuBar();
        modeLabel = new JLabel("Mode: Vertices");
        snapOn = new JLabel("Grid Snap: Off");
        JMenuItem closeOption = new JMenuItem("Close");
        zoomLabel = new JLabel("Zoom			");
        mousePos = new JLabel("Position: (0,0)");
        gridSize = new JLabel("Grid: 		1/8");
        String[] list = {"LEVEL 1", "LEVEL 2", "LEVEL 3", "LEVEL 4", "LEVEL 5", "LEVEL 6", "LEVEL 7", "LEVEL 8",
                "LEVEL 9", "LEVEL 10", "LEVEL 11", "LEVEL 12", "ARENA 1", "ARENA 2", "ARENA 3"};
        levelcb = new JComboBox<String>(list);
        levelcb.setEditable(false);
        openOption.addActionListener(this);
        JMenu fileMenu = new JMenu("File");
        toolBar.add(fileMenu);
        fileMenu.add(openOption);
        fileMenu.add(config);
        fileMenu.add(patchInCDEnemy);
        fileMenu.add(extractData);

        if(DataLists.isCdOrCart())
            extractData.setVisible(false);

        fileMenu.add(closeOption);
        JMenu structMenu = new JMenu("Structures");
        toolBar.add(structMenu);
        structMenu.add(editActors);
        structMenu.add(editProjectiles);
        structMenu.add(editNodeDefs);
        structMenu.add(editLevelHeader);
        checkIfNeedCDEnemyPatched();
        closeOption.addActionListener(this);
        rotateChb.addActionListener(this);
        toolBar.add(editTextures);
        toolBar.add(editSprites);
        toolBar.add(editPalette);
        toolBar.add(viewMiniMap);
        toolBar.add(editTileMap);
        toolBar.add(levelcb);
        toolBar.add(snapOn);
        toolBar.add(rotateChb);
        infoBar.add(modeLabel);
        infoBar.add(zoomLabel);
        infoBar.add(gridSize);
        infoBar.add(mousePos);

        setupSelectPanel(frame);

        frame.add(selectPanel.getInstance(), BorderLayout.EAST);
        frame.add(toolBar, BorderLayout.NORTH);
        frame.add(infoBar, BorderLayout.SOUTH);
        levelcb.addItemListener(this);
    }

    public void checkIfNeedCDEnemyPatched() {

        patchInCDEnemy.setVisible(!DataLists.isEnemyPatchedIn() && !DataLists.isCdOrCart());
        patchInCDEnemy.setEnabled(!DataLists.isEnemyPatchedIn() && !DataLists.isCdOrCart());

    }

    private void disableButtons() {
        editPalette.setEnabled(false);
        editSprites.setEnabled(false);
        editTileMap.setEnabled(false);
        viewMiniMap.setEnabled(false);
        editTextures.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        String cmd = arg0.getActionCommand();
        if (cmd.equals("Close")) {
            Window.getReader().writeConfig();
            System.exit(0);
        }
        if (cmd.equals("Open...")) {
            showOpenDialog(host);
        }
        if (cmd.equals("Rotate 90")) {
            RenderControls.setRot90(!RenderControls.isRot90());
            rotateChb.transferFocusBackward();
        }
        if (cmd.equals("Edit Sprites")) {
            disableButtons();
            createNewSprEdWindow();
        }
        if (cmd.equals("Edit Palette")) {
            disableButtons();
            createNewPalEdWindow();
        }
        if (cmd.equals("View MiniMap")) {
            disableButtons();
            createNewMiniMapWindow();
        }
        if (cmd.equals("Edit Tilemaps")) {
            disableButtons();
            createNewTileMapEditorWindow();
        }
        if(cmd.equals("Edit Textures")) {
            disableButtons();
            createNewTextureEditorWindow();
        }
        if (cmd.equals("Configuration")) {
            config.setEnabled(false);
            createNewConfigWindow();
        }

        if (cmd.equals("Patch In CD Enemy")) {
            patchInCDEnemy.setEnabled(false);
            createNewCDEnemyPatchWindow();
        }

        if (cmd.equals("Extract Data")) {
            showFolderDialog(host);
        }
        if(cmd.equals("Edit Actors")) {
            editActors.setEnabled(false);
            createActorEditorWindow();
        }
        if (cmd.equals("Edit Node Definitions")) {
            editNodeDefs.setEnabled(false);
            createNodeEditorWindow();
        }
        if (cmd.equals("Edit Level Header")) {
            editLevelHeader.setEnabled(false);
            createLevelHeaderEditorWindow();
        }
        if (cmd.equals("Edit Projectiles")) {
            editProjectiles.setEnabled(false);
            createProjectileEditorWindow();
        }
    }

    private void createActorEditorWindow() {
        JFrame frame = new JFrame("Actor Editor");
        frame.setSize(1000, 240);
        frame.setResizable(false);
        frame.addWindowListener(new Listener());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new ActorEditor(host));
        frame.setVisible(true);
    }

    private void createProjectileEditorWindow() {
        JFrame frame = new JFrame("Projectile Editor");
        frame.setSize(700, 300);
        frame.setResizable(false);
        frame.addWindowListener(new Listener());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new ProjectileEditor(host));
        frame.setVisible(true);
    }

    private void createNodeEditorWindow() {
        JFrame frame = new JFrame("Plasma Node Editor");
        frame.setSize(1100, 120);
        frame.setResizable(false);
        frame.addWindowListener(new Listener());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new NodeEditor(host));
        frame.setVisible(true);
    }

    private void createLevelHeaderEditorWindow() {
        JFrame frame = new JFrame("Level Header Editor");
        frame.setSize(700, 800);
        frame.setResizable(false);
        frame.addWindowListener(new Listener());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new LevelHeaderEditor(host));
        frame.setVisible(true);
    }

    private void createNewTextureEditorWindow() {
        JFrame frame = new JFrame("Texture Editor");
        frame.setSize(700, 800);
        frame.setResizable(false);
        frame.addWindowListener(new Listener());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new TextureEditor(host));
        frame.setVisible(true);
    }
    private void createNewTileMapEditorWindow() {
        JFrame frame = new JFrame("Tile Map Editor");
        frame.setSize(1200, 800);
        frame.setResizable(false);
        frame.addWindowListener(new Listener());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new tileMapEditor(host));
        frame.setVisible(true);
    }

    private void createNewCDEnemyPatchWindow() {
        EnemyImporter im = new EnemyImporter();
        boolean vis = im.kickoff();
        patchInCDEnemy.setVisible(!vis);
        patchInCDEnemy.setEnabled(!vis);
        DataLists.setEnemyPatchedIn(vis);
        if (vis) {
            try {
                DataLists.setupColors();
                host.getControls().loadData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createNewConfigWindow() {
        JFrame frame = new JFrame("Control Configuration");
        frame.setSize(470, 415);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new ControlEditor());
        frame.addWindowListener(new Listener());
        frame.setVisible(true);
    }

    private void createNewPalEdWindow() {
        JFrame frame = new JFrame("Palette Editor");
        frame.setSize(500, 300);
        frame.setResizable(false);
        frame.addWindowListener(new Listener());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new PaletteEditor(DataLists.getCurrentLevelInfo().getPalOffs()));
        frame.setVisible(true);
    }

    private void createNewSprEdWindow() {
        JFrame frame = new JFrame("Sprite Editor");
        frame.setSize(700, 700);
        frame.setResizable(false);
        frame.addWindowListener(new Listener());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new SpriteEditor(host));
        frame.setVisible(true);
    }

    private void createNewMiniMapWindow() {
        JFrame frame = new JFrame("Mini Map: " + levelcb.getSelectedItem().toString());
        frame.setSize(700, 700);
        frame.setResizable(false);
        frame.addWindowListener(new Listener());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new MapViewer(host));
        frame.setVisible(true);
    }

    private void showFolderDialog(Window frame) {
        JFileChooser fc = new JFileChooser("Select target folder...");
        // Set to select directories only
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = fc.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            String targetPath = fc.getSelectedFile().toString();

            // Call the extraction function with the selected folder
            performExtraction(targetPath);
        } else {
            return;
        }
    }

    private void performExtraction(String targetFolder) {
        // TODO: Implement your extraction logic here
        // This method should extract data to the specified folder

        JOptionPane.showMessageDialog(null,
                "Extracting data to:\n" + targetFolder,
                "Extraction Started",
                JOptionPane.INFORMATION_MESSAGE);

        if(!DataLists.isCdOrCart()) {
            Window.getReader().extractROMData(targetFolder);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent arg0) {
        if (arg0.getSource() == levelcb &&  arg0.getStateChange() == ItemEvent.SELECTED) {
            String[] chunks = levelcb.getSelectedItem().toString().split(" ");

            int level = 0;
            switch (chunks[0]) {
                case "LEVEL": {
                    level = Integer.parseInt(chunks[1]) - 1;
                    break;
                }
                case "ARENA": {
                    level = Integer.parseInt(chunks[1]) + 11;
                }
            }
            Window.getReader().getConfig().saveMapConfigs();
            this.rotateChb.setSelected(Window.getReader().getConfig().getLevelRot(level));
            Window.getReader().getConfig().setCurrentLevel(level);
            host.getControls().updateMap(this);
            levelcb.transferFocusBackward();
        }
    }

    private class Listener implements WindowListener {
        @Override
        public void windowActivated(WindowEvent arg0) {
            // TODO Auto-generated method stub
        }

        @Override
        public void windowClosed(WindowEvent arg0) {
            if (!editTextures.isEnabled()) {
                editTextures.setEnabled(true);
            }
            if (!editPalette.isEnabled()) {
                editPalette.setEnabled(true);
            }
            if (!editSprites.isEnabled()) {
                editSprites.setEnabled(true);
            }
            if (!config.isEnabled()) {
                config.setEnabled(true);
            }
            if (!viewMiniMap.isEnabled()) {
                viewMiniMap.setEnabled(true);
            }
            if (!editTileMap.isEnabled() && !DataLists.isCdOrCart()) {
                editTileMap.setEnabled(true);
            }
            if(!editActors.isEnabled()) {
                editActors.setEnabled(true);
            }
            if(!editLevelHeader.isEnabled()) {
                editLevelHeader.setEnabled(true);
            }
            if(!editNodeDefs.isEnabled()) {
                editNodeDefs.setEnabled(true);
            }
            if(!editProjectiles.isEnabled()) {
                editProjectiles.setEnabled(true);
            }
        }

        @Override
        public void windowClosing(WindowEvent arg0) {
        }

        @Override
        public void windowDeactivated(WindowEvent arg0) {
            // TODO Auto-generated method stub
        }

        @Override
        public void windowDeiconified(WindowEvent arg0) {
            // TODO Auto-generated method stub
        }

        @Override
        public void windowIconified(WindowEvent arg0) {
            // TODO Auto-generated method stub
        }

        @Override
        public void windowOpened(WindowEvent arg0) {
            // TODO Auto-generated method stub
        }
    }
}
