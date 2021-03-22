package aneurysm.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

import aneurysm.io.FileReader;
import aneurysm.render.RenderControls;

public class ComponentLauncher implements ActionListener, ItemListener {

	private JMenuBar bar;
	private JMenuItem m1;
	private JLabel zoomLabel;
	private JLabel mousePos;
	private JLabel gridSize;
	private JLabel modeLabel;
	private JLabel snapOn;
	private JComboBox<String> cb1;
	private JCheckBox chb1;
	private JTextArea jt;
	private JMenu menu;
	private Window owner;
	private SelectPanel selectPanel;
	
	public void setSnapOn(String snapOn) {
		this.snapOn.setText("Grid Snap: " + snapOn);
	}

	public SelectPanel getSelectPanel() {
		return selectPanel;
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
		owner = frame;
	}

	private void setupSelectPanel(Window frame) {
		selectPanel = new SelectPanel(frame);
		frame.add(selectPanel, BorderLayout.EAST);

	}

	private void addComponents(Window frame) {
		jt = new JTextArea("Nothing highlighted.");
		jt.setSize(frame.getWidth(), 32);
		jt.setFont(new Font("Courier", Font.PLAIN, 10));
		jt.setEditable(false);
		chb1 = new JCheckBox("Rotate 90");
		chb1.setFocusable(false);
		bar = new JMenuBar();
		modeLabel = new JLabel("Mode: Vertices");
		snapOn = new JLabel("Grid Snap: Off");
		m1 = new JMenuItem("Close");
		zoomLabel = new JLabel("Zoom			");
		mousePos = new JLabel("Position: (0,0)");
		gridSize = new JLabel("Grid: 		1/8");
		String[] list = { "LEVEL 1", "LEVEL 2", "LEVEL 3", "LEVEL 4", "LEVEL 5", "LEVEL 6", "LEVEL 7", "LEVEL 8",
				"LEVEL 9", "LEVEL 10", "LEVEL 11", "LEVEL 12", "ARENA 1", "ARENA 2", "ARENA 3" };
		cb1 = new JComboBox<String>(list);
		cb1.setEditable(false);
		cb1.addItemListener(this);
		menu = new JMenu("File");
		bar.add(menu);
		menu.add(m1);
		m1.addActionListener(this);
		chb1.addActionListener(this);
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
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String cmd = arg0.getActionCommand();
		if (cmd.equals("Close")) {
			Window.getReader().writeConfig();
			System.exit(0);
		}
		if (cmd.equals("Rotate 90")) {
			RenderControls.setRot90(!RenderControls.isRot90());
			chb1.transferFocusBackward();
		}
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
			owner.getControls().updateMap(this);
			cb1.transferFocusBackward();
		}

	}
}
