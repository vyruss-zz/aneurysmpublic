package aneurysm.ui;

import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class NumericTextBox extends JTextField {

	private static final long serialVersionUID = 1L;

	@Override
	public void processKeyEvent(KeyEvent ev) {
		if (Character.isDigit(ev.getKeyChar()) || ev.getKeyChar() == 'a' || ev.getKeyChar() == 'b'
				|| ev.getKeyChar() == 'c' || ev.getKeyChar() == 'd' || ev.getKeyChar() == 'e'
				|| ev.getKeyChar() == 'f' || ev.getKeyChar() == 'A' || ev.getKeyChar() == 'B'
				|| ev.getKeyChar() == 'C' || ev.getKeyChar() == 'D' || ev.getKeyChar() == 'E'
				|| ev.getKeyChar() == 'F' || ev.getKeyChar() == KeyEvent.VK_ENTER) {
			super.processKeyEvent(ev);
		}

		ev.consume();
		return;
	}

	public Byte getByte() {
		Byte result = null;

		String text = getText();
		if (text != null && !"".equals(text)) {
			result = Byte.valueOf(text);
		}

		return result;
	}
}
