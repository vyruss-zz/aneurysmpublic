package aneurysm.ui;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class IconStringComboBox extends JComboBox {
	private static final long serialVersionUID = 1L;
	private DefaultComboBoxModel model;
	public IconStringComboBox() {
		model = new DefaultComboBoxModel();
		setModel(model);
		setRenderer(new ImageStringRenderer());
	}
	
	public void addItems(String[] items) {
		for(String item: items)
		model.addElement(item);
	}
}