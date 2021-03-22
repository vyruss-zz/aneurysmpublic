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

import aneurysm.io.FileReader;
import aneurysm.ui.DataLists;
import aneurysm.ui.Window;

public class AneurysmLauncher {

	private static Window w;

	public static void main(String[] args) throws IOException{
		FileReader reader = new FileReader();
		if(!reader.readConfig()) {
			JFileChooser fc = new JFileChooser();
			fc.showOpenDialog(fc);
			FileReader.getConfig().setLocation(fc.getSelectedFile().toString());
			if(!fc.getSelectedFile().toString().contains(".lev") && !reader.checkForFixedTextures()) {
				
				int result = JOptionPane.showConfirmDialog(null, ("The ROM file loaded has the corrupted textures present.  Would you like to patch them?"),
				"Fix Corrupted Textures?", JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.YES_OPTION) {
					reader.fixCorruptedROMTextures();
				}
			}
		}

		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // TODO code application logic here

                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException classNotFoundException) {
                } catch (InstantiationException instantiationException) {
                } catch (IllegalAccessException illegalAccessException) {
                } catch (UnsupportedLookAndFeelException unsupportedLookAndFeelException) {
                }
                
                JFrame frame = new JFrame("Aneurysm");
                frame.setSize(800+144, 800);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.setUndecorated(false);
                frame.addWindowListener(new Listener());
                frame.setIgnoreRepaint(true);
                frame.setMinimumSize(new Dimension(525,625));
                frame.requestFocus();
                w=new Window(frame.getWidth(),frame.getHeight());
                frame.add(w);
                frame.setVisible(true);
            }
        	class Listener implements WindowListener{

        		@Override
        		public void windowActivated(WindowEvent e) {
        			// TODO Auto-generated method stub
        			
        		}

        		@Override
        		public void windowClosed(WindowEvent e) {
        			System.exit(0);
        		}

        		@Override
        		public void windowClosing(WindowEvent e) {
        			FileReader.getConfig().saveMapConfigs();
        			reader.writeConfig();
        			if(DataLists.isChangesMade()) {
        				FileReader.fixChecksum();
        			}
        		}

        		@Override
        		public void windowDeactivated(WindowEvent e) {
        			// TODO Auto-generated method stub
        			
        		}

        		@Override
        		public void windowDeiconified(WindowEvent e) {
        			// TODO Auto-generated method stub
        			
        		}

        		@Override
        		public void windowIconified(WindowEvent e) {
        			// TODO Auto-generated method stub
        			
        		}

        		@Override
        		public void windowOpened(WindowEvent e) {
        			// TODO Auto-generated method stub
        			
        		}
        		
        	}
        });
	}
}
