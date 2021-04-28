package aneurysm.ui.controlEd.capture;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CapturePane extends JFrame{

	private static final long serialVersionUID = 1L;
	private JLabel label;
	private MouseListener mListen;
	private KeyListener kListen;
	private JPanel host;
	public CapturePane() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(300,80);
		this.setVisible(true);
		label = new JLabel();
		label.setLocation(12, 12);
		this.add(label);
	}
	
	public CapturePane(JPanel host,MouseListener listener) {
		this();
		this.host=host;
		host.setVisible(false);
		label.setText("Please click a mouse button in this window to set it.");
		mListen = listener;
		this.addMouseListener(listener);
	}
	
	public CapturePane(JPanel host,KeyListener listener) {
		this();
		this.host=host;
		host.setVisible(false);
		label.setText("Please press a key in this window to set it.");
		kListen = listener;
		this.addKeyListener(listener);
	}
	
	public int getCode(MouseEvent evt) {
		this.dispose();
		host.setVisible(true);
		this.removeMouseListener(mListen);
		return evt.getButton();
	}
	
	public int getCode(KeyEvent evt) {
		this.dispose();
		host.setVisible(true);
		this.removeKeyListener(kListen);
		return evt.getKeyCode();
	}
}
