package aneurysm.ui;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PalOutWindow extends JPanel {

	private JTextArea jt;

	public PalOutWindow(String out) {
		jt = new JTextArea();
		jt.setSize(600,200);
		jt.setText(out);
		jt.setEditable(false);
		jt.setLineWrap(true);
		this.add(jt);
	}
}
