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

import aneurysm.io.FileReader;
import aneurysm.render.RenderControls;
import aneurysm.ui.controlEd.ControlEditor;
import aneurysm.ui.palEd.PaletteEditor;
import aneurysm.ui.sprEd.edit.Editor;

public class ComponentLauncher implements ActionListener, ItemListener {

	private JMenuBar bar;
	private JMenuItem closeOption;
	private JMenuItem openOption;
	private JLabel zoomLabel;
	private JLabel mousePos;
	private JLabel gridSize;
	private JLabel modeLabel;
	private JLabel snapOn;
	private JButton editSprites;
	private JButton editPalette;
	private JMenuItem config;
	private JComboBox<String> cb1;
	private JCheckBox chb1;
	private JTextArea jt;
	private JMenu menu;
	private Window host;
	private SelectPanel selectPanel;

	public void setSnapOn(String snapOn) {
		this.snapOn.setText("Grid Snap: " + snapOn);
	}

	public SelectPanel getSelectPanel() {
		return selectPanel;
	}

	public void setLevelSelection(boolean b) {
		cb1.setEnabled(b);
	}

	public JTextArea getTextArea() {
		return this.jt;
	}

	public void setComboNumber(int num) {
		cb1.setSelectedIndex(num);
	}

	public JCheckBox getChb1() {
		return chb1;
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
		JFileChooser fc = new JFileChooser("Load file...");
		fc.addChoosableFileFilter(new FileNameExtensionFilter("BIN File", "bin"));
		fc.addChoosableFileFilter(new FileNameExtensionFilter("LEV File", "lev"));
		fc.addChoosableFileFilter(new FileNameExtensionFilter("Both supported filetypes", "bin", "lev"));
		int result = fc.showOpenDialog(null);
		if (result != JFileChooser.CANCEL_OPTION) {
			String f = fc.getSelectedFile().toString();
			if (!f.toLowerCase().contains(".lev") && !f.toLowerCase().contains(".bin")) {
				JOptionPane.showMessageDialog(null, "Please select a valid File.", "Invalid File",
						JOptionPane.ERROR_MESSAGE);
			} else {
				host.getControls().clearSelection();
				FileReader.getConfig().setLocation(f);
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

		} else {
			return;
		}

		cb1.setEnabled(!DataLists.isCdOrCart());
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
		config = new JMenuItem("Configuration");
		config.addActionListener(this);
		jt = new JTextArea("Nothing highlighted.");
		openOption = new JMenuItem("Open...");
		jt.setSize(frame.getWidth(), 32);
		jt.setFont(new Font("Courier", Font.PLAIN, 10));
		jt.setEditable(false);
		chb1 = new JCheckBox("Rotate 90");
		chb1.setFocusable(false);
		bar = new JMenuBar();
		modeLabel = new JLabel("Mode: Vertices");
		snapOn = new JLabel("Grid Snap: Off");
		closeOption = new JMenuItem("Close");
		zoomLabel = new JLabel("Zoom			");
		mousePos = new JLabel("Position: (0,0)");
		gridSize = new JLabel("Grid: 		1/8");
		String[] list = { "LEVEL 1", "LEVEL 2", "LEVEL 3", "LEVEL 4", "LEVEL 5", "LEVEL 6", "LEVEL 7", "LEVEL 8",
				"LEVEL 9", "LEVEL 10", "LEVEL 11", "LEVEL 12", "ARENA 1", "ARENA 2", "ARENA 3" };
		cb1 = new JComboBox<String>(list);
		cb1.setEditable(false);
		openOption.addActionListener(this);
		menu = new JMenu("File");
		bar.add(menu);
		menu.add(openOption);
		menu.add(config);
		menu.add(closeOption);
		closeOption.addActionListener(this);
		chb1.addActionListener(this);
		bar.add(editSprites);
		bar.add(editPalette);
		bar.add(cb1);
		bar.add(snapOn);
		bar.add(modeLabel);
		bar.add(zoomLabel);
		bar.add(gridSize);
		bar.add(mousePos);
		bar.add(chb1);

		setupSelectPanel(frame);

		frame.add(selectPanel.getInstance(), BorderLayout.EAST);
		frame.add(bar, BorderLayout.NORTH);
		cb1.addItemListener(this);
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
			chb1.transferFocusBackward();
		}
		if (cmd.equals("Edit Sprites")) {
			editPalette.setEnabled(false);
			editSprites.setEnabled(false);
			createNewSprEdWindow();
		}
		if (cmd.equals("Edit Palette")) {
			editPalette.setEnabled(false);
			editSprites.setEnabled(false);
			createNewPalEdWindow();
		}
		if (cmd.equals("Configuration")) {
			config.setEnabled(false);
			createNewConfigWindow();
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

	public JButton getPaletteEditorButton() {
		return editPalette;
	}
	
	public JButton getSpriteEditorButton() {
		return editSprites;
	}
	
	private void createNewPalEdWindow() {
		JFrame frame = new JFrame("Palette Editor");
		frame.setSize(500, 300);
		frame.setResizable(false);
		frame.addWindowListener(new Listener());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(new PaletteEditor(DataLists.getPaletteAddress()));
		frame.setVisible(true);
	}

	private void createNewSprEdWindow() {
		JFrame frame = new JFrame("Sprite Editor");
		frame.setSize(700, 700);
		frame.setResizable(false);
		frame.addWindowListener(new Listener());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(new Editor(host));
		frame.setVisible(true);
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		if (arg0.getSource() == cb1) {
			String[] chunks = cb1.getSelectedItem().toString().split(" ");

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
			FileReader.getConfig().saveMapConfigs();
			this.chb1.setSelected(FileReader.getConfig().getLevelRot(level));
			FileReader.getConfig().setCurrentLevel(level);
			host.getControls().updateMap(this);
			cb1.transferFocusBackward();
		}
	}
	
	private class Listener implements WindowListener {
		@Override
		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent arg0) {
			if(!editPalette.isEnabled()) editPalette.setEnabled(true);
			if(!editSprites.isEnabled()) editSprites.setEnabled(true);
			if(!config.isEnabled()) config.setEnabled(true);
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
