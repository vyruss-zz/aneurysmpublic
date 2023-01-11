package aneurysm.core;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import aneurysm.io.FileReader;
import aneurysm.ui.DataLists;
import aneurysm.ui.Window;

public class AneurysmLauncher {

	private static Window w;

	private static void prepData() {
		JFileChooser fc = new JFileChooser("Load file...");
		fc.addChoosableFileFilter(new FileNameExtensionFilter("BIN File", "bin"));
		fc.addChoosableFileFilter(new FileNameExtensionFilter("GEN File", "gen"));
		fc.addChoosableFileFilter(new FileNameExtensionFilter("MD File", "md"));
		fc.addChoosableFileFilter(new FileNameExtensionFilter("LEV File", "lev"));
		fc.addChoosableFileFilter(new FileNameExtensionFilter("All supported filetypes", "bin", "gen", "lev", "md"));
		int result = fc.showOpenDialog(null);
		if (result != JFileChooser.CANCEL_OPTION) {
			String f = fc.getSelectedFile().toString();
			if (!f.toLowerCase().contains(".lev") && !f.toLowerCase().contains(".bin")) {
				JOptionPane.showMessageDialog(null, "Please select a valid File.", "Invalid File",
						JOptionPane.ERROR_MESSAGE);
			} else {
				FileReader.getConfig().setLocation(f);
				if (!fc.getSelectedFile().toString().toLowerCase().contains(".lev")) {
					pollForFixTextures(fc);
				}
				Window.getReader().readNewFile(f);
			}
		} else {
			System.exit(0);
		}
	}

	private static void pollForFixTextures(JFileChooser fc) {

		int result = JOptionPane.showConfirmDialog(null,
				("The ROM file loaded has the corrupted textures present.  Would you like to patch them?"),
				"Fix Corrupted Textures?", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			Window.getReader().fixCorruptedROMTextures();
		}

	}

	public static void main(String[] args) throws IOException {
		FileReader reader = new FileReader();

		if (!reader.readConfig()) {
			prepData();
		} else {
			if(DataLists.isCdOrCart()) reader.populateCDOffsets();
			DataLists.setupColors();
			
		}


		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException classNotFoundException) {
				} catch (InstantiationException instantiationException) {
				} catch (IllegalAccessException illegalAccessException) {
				} catch (UnsupportedLookAndFeelException unsupportedLookAndFeelException) {
				}
				
				try {
					JFrame frame = new JFrame("Aneurysm");
					frame.setSize(800 + 144, 800);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setLayout(new BorderLayout());
					frame.setUndecorated(false);
					frame.addWindowListener(new Listener());
					frame.setIgnoreRepaint(true);
					frame.setMinimumSize(new Dimension(525, 625));
					frame.requestFocus();
					w = new Window(frame.getWidth(), frame.getHeight());
					frame.add(w);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			class Listener implements WindowListener {

				@Override
				public void windowActivated(WindowEvent e) {
				}

				@Override
				public void windowClosed(WindowEvent e) {
					System.exit(0);
				}

				@Override
				public void windowClosing(WindowEvent e) {
					FileReader.getConfig().saveMapConfigs();
					reader.writeConfig();
					if (DataLists.isChangesMade()) {
						FileReader.fixChecksum();
					}
				}

				@Override
				public void windowDeactivated(WindowEvent e) {
				}

				@Override
				public void windowDeiconified(WindowEvent e) {
				}

				@Override
				public void windowIconified(WindowEvent e) {
				}

				@Override
				public void windowOpened(WindowEvent e) {
				}

			}
		});
	}
}
