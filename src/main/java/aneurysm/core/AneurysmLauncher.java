package aneurysm.core;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.ImageIcon;
import java.io.File;

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

    private static final int DEFAULT_FRAME_WIDTH = 800;
    private static final int DEFAULT_FRAME_HEIGHT = 800;
    private static final int EXTRA_PADDING = 144;

    private static void prepData() {
        JFileChooser fc = createFileFilter();
        int result = fc.showOpenDialog(null);

        if (result != JFileChooser.CANCEL_OPTION) {
            String f = fc.getSelectedFile().toString();

            // Validate file extension
            if (!isValidFile(f)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please select a valid File.",
                        "Invalid File",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Window.getReader().getConfig().setLocation(f);

            // Offer texture fix for non-LEV files
            if (!f.toLowerCase().contains(".lev")) {
                pollForFixTextures();
            }

            Window.getReader().readNewFile(f);
        } else {
            System.exit(0);
        }
    }

    private static JFileChooser createFileFilter() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Load file...");
        fc.addChoosableFileFilter(new FileNameExtensionFilter("BIN File", "bin"));
        fc.addChoosableFileFilter(new FileNameExtensionFilter("GEN File", "gen"));
        fc.addChoosableFileFilter(new FileNameExtensionFilter("MD File", "md"));
        fc.addChoosableFileFilter(new FileNameExtensionFilter("LEV File", "lev"));
        fc.addChoosableFileFilter(
                new FileNameExtensionFilter("All supported filetypes", "bin", "gen", "lev", "md")
        );
        return fc;
    }

    private static boolean isValidFile(String path) {
        String lower = path.toLowerCase();
        return lower.contains(".lev") || lower.contains(".bin") || lower.contains(".gen") || lower.contains(".md");
    }

    private static void pollForFixTextures() {
        int result = JOptionPane.showConfirmDialog(
                null,
                "The ROM file loaded has the corrupted textures present.  Would you like to patch them?",
                "Fix Corrupted Textures?",
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {
            Window.getReader().fixCorruptedROMTextures();
        }
    }

    public static void main(String[] args) {
        if (!Window.getReader().readConfig()) {
            prepData();
        } else {
            if (DataLists.isCdOrCart()) Window.getReader().populateCDOffsets();

            Window.getReader().readNewFile(Window.getReader().getConfig().getLocation());
        }

        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     UnsupportedLookAndFeelException e) {
                throw new RuntimeException(e);
            }

            JFrame frame = createMainFrame();

            Window w = new Window(frame.getWidth(), frame.getHeight());
            frame.add(w);
            frame.setVisible(true);
        });
    }


    private static JFrame createMainFrame() {
        JFrame frame = new JFrame("Aneurysm");
        frame.setSize(DEFAULT_FRAME_WIDTH + EXTRA_PADDING, DEFAULT_FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setUndecorated(false);
        frame.addWindowListener(new Listener());
        frame.setIgnoreRepaint(true);
        frame.setMinimumSize(new Dimension(525, 625));

        // Load custom icon image for this frame
        try {
            File iconFile = new File("resources/icon.png");
            if (iconFile.exists()) {
                ImageIcon icon = new ImageIcon(iconFile.getAbsolutePath());
                frame.setIconImage(icon.getImage());
            }
        } catch (Exception e) {
            System.err.println("Could not load custom icon: " + e.getMessage());
        }

        frame.requestFocus();

        return frame;
    }

    private static class Listener implements WindowListener {

        @Override
        public void windowActivated(WindowEvent e) {
            // No action needed
        }

        @Override
        public void windowClosed(WindowEvent e) {
            System.exit(0);
        }

        @Override
        public void windowClosing(WindowEvent e) {
            Window.getReader().getConfig().saveMapConfigs();

            if (DataLists.isChangesMade()) {
                Window.getReader().fixChecksum();
            }
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
            // No action needed
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
            // No action needed
        }

        @Override
        public void windowIconified(WindowEvent e) {
            // No action needed
        }

        @Override
        public void windowOpened(WindowEvent e) {
            // No action needed
        }

    }
}