package aneurysm.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;

import aneurysm.render.RenderControls;

public class ImageStringRenderer extends DefaultListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

		Integer theValue = Integer.parseInt((String)value, 16);
		this.setText(Integer.toHexString(theValue));
		if(RenderControls.isThingsMode())
			this.setIcon(new ImageIcon(DataLists.getObjectImage(theValue)));
		if(RenderControls.isLinesMode())
			this.setIcon(new ImageIcon(DataLists.getWallImages().get(theValue)));
		
		
		if(isSelected) {
			this.setBackground(Color.BLUE);
			this.setForeground(Color.RED);
		} else {
			this.setBackground(Color.BLACK);
			this.setForeground(Color.RED);
		}
		return this;
	}
}