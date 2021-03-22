package aneurysm.ui;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import aneurysm.render.RenderControls;

public class SelectPanel extends JPanel implements ActionListener, ItemListener, KeyListener, FocusListener {

	private static final long serialVersionUID = 1L;
	private JLabel currentImage;

	private JComboBox<String> itemBox;
	private JComboBox<String> lineType;
	private JCheckBox flipTexture;
	private NumericTextBox lineTag;
	private NumericTextBox NSLow;
	private NumericTextBox NSHigh;
	private NumericTextBox WELow;
	private NumericTextBox WEHigh;
	private NumericTextBox texLength;
	private JLabel lineTagLabel, NSLowLabel, NSHighLabel, WELowLabel, WEHighLabel, texLengthLabel;
	private JLabel itemInfo;
	private Window host;

	private String[] doorTypes = { "Wall", "Door", "Door Open and Close Others", "Door Yellow Keyed", "Door Red Keyed",
			"Door White Keyed" };

	public void refreshItemBox() {
		itemBox.removeItemListener(this);
		itemBox = new JComboBox<String>();
		itemBox.addActionListener(this);
	}

	public void setBoxValues(byte type, byte tag, byte nlow, byte nhigh, byte wlow, byte whigh, short length, int id) {
		if (type < 0 || type > 5)
			lineType.setSelectedItem(0);
		else
			lineType.setSelectedItem(doorTypes[type]);

		if (RenderControls.isRot90()) {
			NSLow.setText(Integer.toHexString(wlow & 0x000000FF));
			NSHigh.setText(Integer.toHexString(whigh & 0x000000FF));
			WELow.setText(Integer.toHexString(nlow & 0x000000FF));
			WEHigh.setText(Integer.toHexString(nhigh & 0x000000FF));
		} else {
			NSLow.setText(Integer.toHexString(nlow & 0x000000FF));
			NSHigh.setText(Integer.toHexString(nhigh & 0x000000FF));
			WELow.setText(Integer.toHexString(wlow & 0x000000FF));
			WEHigh.setText(Integer.toHexString(whigh & 0x000000FF));
		}
		lineTag.setText(Integer.toHexString(tag & 0x000000FF));

		texLength.setText(Integer.toHexString(length & 0x000000FF));
		if ((id & 0xFF000000) != 0)
			flipTexture.setSelected(true);
		else
			flipTexture.setSelected(false);
	}

	public JComboBox getItemBox() {
		return itemBox;
	}

	public void setItemBox(JComboBox<String> itemBox) {
		this.itemBox = itemBox;
	}

	public String getItemInfo() {
		return itemInfo.getText();
	}

	public void setItemInfo(String itemInfo) {
		this.itemInfo.setText(itemInfo);
	}

	public void setCurrentImage(Image currentImage) {
		this.currentImage.setIcon(new ImageIcon(currentImage));
	}

	private void addLineTypes() {
		for (String s : doorTypes)
			lineType.addItem(s);
	}

	public JComboBox<String> getLineType() {
		return lineType;
	}

	public void hideWallControls() {
		texLength.setVisible(false);
		texLengthLabel.setVisible(false);
		flipTexture.setVisible(false);
		lineTag.setVisible(false);
		lineTagLabel.setVisible(false);
		NSHigh.setVisible(false);
		NSHighLabel.setVisible(false);
		NSLow.setVisible(false);
		NSLowLabel.setVisible(false);
		WELowLabel.setVisible(false);
		WELow.setVisible(false);
		WEHighLabel.setVisible(false);
		WEHigh.setVisible(false);
		lineType.setVisible(false);
	}

	public void showWallControls() {
		texLength.setVisible(true);
		texLengthLabel.setVisible(true);
		flipTexture.setVisible(true);
		lineTag.setVisible(true);
		lineTagLabel.setVisible(true);
		NSHigh.setVisible(true);
		NSHighLabel.setVisible(true);
		NSLow.setVisible(true);
		NSLowLabel.setVisible(true);
		WELowLabel.setVisible(true);
		WELow.setVisible(true);
		WEHighLabel.setVisible(true);
		WEHigh.setVisible(true);
		lineType.setVisible(true);
	}

	public SelectPanel(Window frame) {
		this.setLayout(null);
		host = frame;
		currentImage = new JLabel();
		currentImage.setSize(128, 128);
		currentImage.setPreferredSize(new Dimension(128, 128));
		currentImage.setLocation(2, 2);

		itemBox = new JComboBox<>();
		itemBox.setLocation(2, 132);
		itemBox.setPreferredSize(new Dimension(48, 24));
		itemBox.setSize(136, 24);
		itemBox.setPreferredSize(new Dimension(128, 24));
		itemBox.addActionListener(this);
		itemBox.setVisible(false);
		itemBox.setRenderer(new ImageStringRenderer());
		itemBox.setMaximumRowCount(4);
		itemInfo = new JLabel();
		itemInfo.setLocation(2, 150);
		itemInfo.setSize(140, 250);
		itemInfo.setPreferredSize(new Dimension(140, 500));

		lineTag = new NumericTextBox();
		NSLow = new NumericTextBox();
		NSHigh = new NumericTextBox();
		WELow = new NumericTextBox();
		WEHigh = new NumericTextBox();
		lineType = new JComboBox<>();
		lineTagLabel = new JLabel("Line Tag # ");
		lineTagLabel.setLocation(2, 384);
		lineTagLabel.setSize(60, 12);
		lineTag.setSize(30, 18);
		lineTag.setLocation(94, 382);
		NSLowLabel = new JLabel("North/South Low ");
		NSLowLabel.setLocation(2, 412);
		NSLowLabel.setSize(90, 12);
		NSLow.setLocation(94, 410);
		NSLow.setSize(30, 18);
		NSHighLabel = new JLabel("North/South High ");
		NSHighLabel.setLocation(2, 436);
		NSHighLabel.setSize(90, 12);
		NSHigh.setLocation(94, 434);
		NSHigh.setSize(30, 18);
		WELowLabel = new JLabel("West/East Low ");
		WELowLabel.setLocation(2, 456);
		WELowLabel.setSize(80, 12);
		WELow.setLocation(94, 454);
		WELow.setSize(30, 18);
		WEHighLabel = new JLabel("West/East High ");
		WEHighLabel.setLocation(2, 482);
		WEHighLabel.setSize(80, 12);
		WEHigh.setLocation(94, 478);
		WEHigh.setSize(30, 18);

		NSLow.addFocusListener(this);
		NSHigh.addFocusListener(this);
		WELow.addFocusListener(this);
		WEHigh.addFocusListener(this);
		lineTag.addFocusListener(this);

		flipTexture = new JCheckBox("Flip Texture");

		flipTexture.addItemListener(this);
		flipTexture.setLocation(2, 500);
		flipTexture.setSize(128, 24);
		texLengthLabel = new JLabel("Texture Length");
		texLengthLabel.setSize(90, 12);
		texLengthLabel.setLocation(2, 528);
		texLength = new NumericTextBox();
		texLength.setSize(30, 18);
		texLength.setLocation(94, 524);
		lineType.setLocation(2, 350);
		lineType.setSize(128, 24);

		addLineTypes();
		lineType.addItemListener(this);

		WEHigh.addFocusListener(this);
		WELow.addFocusListener(this);
		NSHigh.addFocusListener(this);
		NSLow.addFocusListener(this);
		lineTag.addFocusListener(this);
		texLength.addFocusListener(this);
		WEHigh.addKeyListener(this);
		WELow.addKeyListener(this);
		NSHigh.addKeyListener(this);
		NSLow.addKeyListener(this);
		lineTag.addKeyListener(this);
		texLength.addKeyListener(this);

		this.add(currentImage);
		this.add(itemBox);
		this.add(itemInfo);
		this.add(lineType);
		this.add(lineTagLabel);
		this.add(lineTag);
		this.add(NSLowLabel);
		this.add(NSLow);
		this.add(NSHighLabel);
		this.add(NSHigh);
		this.add(WELowLabel);
		this.add(WELow);
		this.add(WEHighLabel);
		this.add(WEHigh);
		this.add(flipTexture);
		this.add(texLength);
		this.add(texLengthLabel);

		this.setLocation(frame.getWidth() - 140, 32);
		this.setSize(140, frame.getHeight());

		this.setPreferredSize(new Dimension(140, frame.getHeight()));
		hideWallControls();
	}

	public JPanel getInstance() {
		return this;
	}
	
	public void setImage(int imageId) {
		if ((imageId & 0xFF000000) != 0) {
			imageId = imageId & 0x00FFFFFF;
			BufferedImage img = (BufferedImage) DataLists.getWallImages().get(imageId);
			AffineTransform af = AffineTransform.getScaleInstance(-1, 1);
			af.translate(-img.getWidth(null), 0);
			AffineTransformOp op = new AffineTransformOp(af, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			img = op.filter(img, null);
			setCurrentImage(img);
		}
		else 
			setCurrentImage(DataLists.getWallImages().get(imageId));
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		if (host.getControls().getSelectedWS() != null || host.getControls().getSelectedMOS() != null) {
			if (arg0.getSource() == itemBox) {
				String selected = itemBox.getSelectedItem().toString();
				if (RenderControls.isThingsMode()) {
					System.out.println("selected: " + selected);
					host.getControls().getSelectedMOS().setThingID(Integer.parseInt("0" + selected, 16));
				}
				if (RenderControls.isLinesMode()) {
					host.getControls().getSelectedWS().setTextureID(Integer.parseInt("0" + selected, 16));
				}
				host.getControls().updateSelectedItem();
			}
			if (arg0.getSource() == lineType) {
				if (RenderControls.isLinesMode()) {
					host.getControls().getSelectedWS().setDoorType((byte) (lineType.getSelectedIndex()));
					host.getControls().updateSelectedItem();
				}
			}

			if (arg0.getSource() == flipTexture) {
				if (RenderControls.isLinesMode()) {
					if ((host.getControls().getSelectedWS().getTextureID() & 0xFF000000) != 0) {
						host.getControls().getSelectedWS()
								.setTextureID(host.getControls().getSelectedWS().getTextureID() & 0x00FFFFFF);
					} else {
						host.getControls().getSelectedWS()
								.setTextureID(host.getControls().getSelectedWS().getTextureID() + 0x80000000);

					}
					setImage(host.getControls().getSelectedWS().getTextureID());
					host.getControls().updateSelectedItem();
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_ENTER: {
			short val = 0;
			if (Short.parseShort(((NumericTextBox) arg0.getSource()).getText(), 16) > 0xff) {
				val = 0xff;
				((NumericTextBox) arg0.getSource()).setText("ff");
			} else if (Short.parseShort(((NumericTextBox) arg0.getSource()).getText(), 16) < 0) {
				val = 0;
				((NumericTextBox) arg0.getSource()).setText("0");
			} else
				val = (byte) Short.parseShort(((NumericTextBox) arg0.getSource()).getText(), 16);
			if (arg0.getSource() == WEHigh) {
				if (RenderControls.isRot90())
					host.getControls().getSelectedWS().setDoorNSHigh((byte) val);
				else
					host.getControls().getSelectedWS().setDoorNSLow((byte) val);
			}
			if (arg0.getSource() == WELow) {
				if (RenderControls.isRot90())
					host.getControls().getSelectedWS().setDoorNSLow((byte) val);
				else
					host.getControls().getSelectedWS().setDoorWELow((byte) val);
			}
			if (arg0.getSource() == NSHigh) {
				if (RenderControls.isRot90())
					host.getControls().getSelectedWS().setDoorNSLow((byte) val);
				else
					host.getControls().getSelectedWS().setDoorNSHigh((byte) val);
			}
			if (arg0.getSource() == NSLow) {
				if (RenderControls.isRot90())
					host.getControls().getSelectedWS().setDoorWELow((byte) val);
				else
					host.getControls().getSelectedWS().setDoorNSLow((byte) val);
			}
			if (arg0.getSource() == texLength) {
				host.getControls().getSelectedWS().setTextureScale((short) val);
			}
			if (arg0.getSource() == lineTag) {
				host.getControls().getSelectedWS().setDoorNumber((byte) val);
			}
			host.getControls().updateSelectedItem();
			itemChanged = false;
			break;
		}
		default:
			itemChanged = true;
		}
	}

	private boolean itemChanged = false;

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusLost(FocusEvent arg0) {
		if (itemChanged) {
			short val = 0;
			if (Short.parseShort(((NumericTextBox) arg0.getSource()).getText(), 16) > 0xff) {
				val = 0xff;
				((NumericTextBox) arg0.getSource()).setText("ff");
			} else if (Short.parseShort(((NumericTextBox) arg0.getSource()).getText(), 16) < 0) {
				val = 0;
				((NumericTextBox) arg0.getSource()).setText("0");
			} else
				val = (byte) Short.parseShort(((NumericTextBox) arg0.getSource()).getText(), 16);
			if (arg0.getSource() == WEHigh) {
				if (RenderControls.isRot90())
					host.getControls().getSelectedWS().setDoorNSHigh((byte) val);
				else
					host.getControls().getSelectedWS().setDoorNSLow((byte) val);
			}
			if (arg0.getSource() == WELow) {
				if (RenderControls.isRot90())
					host.getControls().getSelectedWS().setDoorNSLow((byte) val);
				else
					host.getControls().getSelectedWS().setDoorWELow((byte) val);
			}
			if (arg0.getSource() == NSHigh) {
				if (RenderControls.isRot90())
					host.getControls().getSelectedWS().setDoorNSLow((byte) val);
				else
					host.getControls().getSelectedWS().setDoorNSHigh((byte) val);
			}
			if (arg0.getSource() == NSLow) {
				if (RenderControls.isRot90())
					host.getControls().getSelectedWS().setDoorWELow((byte) val);
				else
					host.getControls().getSelectedWS().setDoorNSLow((byte) val);
			}
			if (arg0.getSource() == texLength) {
				host.getControls().getSelectedWS().setTextureScale((byte) val);
			}
			if (arg0.getSource() == lineTag) {
				host.getControls().getSelectedWS().setDoorNumber((byte) val);
			}

			itemChanged = false;
			host.getControls().updateSelectedItem();
		}
	}
}
