package aneurysm.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import aneurysm.editor.EditorControls;
import aneurysm.io.FileReader;
import aneurysm.render.Render;
import aneurysm.render.RenderControls;

public class Window extends JPanel implements KeyListener, MouseListener, MouseWheelListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	private Render render;
	private static int width;
	private static int height;
	private ComponentLauncher launcher;
	private EditorControls controls;
	private static FileReader reader = new FileReader();

	public Window(int width, int height) {
		this.setLayout(new BorderLayout());
		launcher = new ComponentLauncher(this);
		Window.width = width;
		Window.height = height;

		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				Window.width = e.getComponent().getWidth();
				Window.height = e.getComponent().getHeight();
			}
		});

		Thread main = new Thread(new Runner());
		main.setDaemon(true);
		main.start();

		controls = new EditorControls(this);

		this.setSize(640, 480);
		this.setFocusable(true);

		addKeyListener(this);
		addMouseListener(this);
		addMouseWheelListener(this);
		addMouseMotionListener(this);
		controls.updateMap(launcher);
		launcher.setComboNumber(FileReader.getConfig().getCurrentLevel());
		render = new Render(this);
	}

	public EditorControls getControls() {
		return controls;
	}

	public ComponentLauncher getLauncher() {
		return launcher;
	}

	public static FileReader getReader() {
		return reader;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, width, height);

		render.drawGrid(g2d, width, height);
		g.setColor(Color.WHITE);

		render.drawLines(g);
		if (RenderControls.isVertsMode() || RenderControls.isLinesMode()) {
			render.drawVertices(g);
		}
		render.drawThings(g2d, this);
		if (RenderControls.isItemHighlighted()) {
			render.drawHighlighted(g, this);
		}

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		int k = arg0.getKeyCode();
		switch (k) {
		case KeyEvent.VK_F: {
			if (RenderControls.isGridSnap())
				launcher.setSnapOn("Off");
			else
				launcher.setSnapOn("On");
			RenderControls.setGridSnap(!RenderControls.isGridSnap());
		}
		case KeyEvent.VK_W: {
			if ((arg0.getModifiers() & KeyEvent.CTRL_MASK) != 0)
				controls.saveFile(this);
			break;
		}
		case KeyEvent.VK_S: {
			if ((arg0.getModifiers() & KeyEvent.CTRL_MASK) != 0)
				controls.writeToROM(this);
			break;
		}
		case KeyEvent.VK_R: {
			RenderControls.setRot90(!RenderControls.isRot90());
			launcher.getChb1().setSelected(RenderControls.isRot90());
			break;
		}
		case KeyEvent.VK_G: {
			RenderControls.cycleGrid();
			launcher.setGridLabel(RenderControls.getGridIntensity());
			break;
		}
		case KeyEvent.VK_MINUS: {
			RenderControls.zoom(true, launcher);
			break;
		}
		case KeyEvent.VK_EQUALS: {
			RenderControls.zoom(false, launcher);
			break;
		}
		case KeyEvent.VK_T: {
			controls.setThingList();
			controls.clearSelection();
			launcher.getSelectPanel().setCurrentImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB));
			launcher.getSelectPanel().getItemBox().setEnabled(true);
			controls.setSelectedInfo("");
			launcher.setModeLabel("Things");
			RenderControls.setThingsMode(true);
			break;
		}
		case KeyEvent.VK_L: {
			controls.setTextureList();
			controls.clearSelection();
			launcher.getSelectPanel().setCurrentImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB));
			launcher.getSelectPanel().getItemBox().setEnabled(true);
			controls.setSelectedInfo("");
			launcher.setModeLabel("Lines");
			RenderControls.setLinesMode(true);
			break;
		}
		case KeyEvent.VK_M: {
			keyboardMoving = true;
			queryItemMotion(true);
			break;
		}
		case KeyEvent.VK_SPACE: {
			controls.setSelectedItem();
			break;
		}
		case KeyEvent.VK_Z: {
			keyboardMoving = true;
			System.out.println("keypand");
			RenderControls.setPanMode(true);
			break;
		}
		case KeyEvent.VK_V: {
			controls.clearSelection();
			launcher.getSelectPanel().setCurrentImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB));
			launcher.getSelectPanel().getItemBox().setEnabled(false);
			controls.setSelectedInfo("");
			launcher.setModeLabel("Vertices");
			RenderControls.setVertsMode(true);
			break;
		}
		case KeyEvent.VK_UP: {
			RenderControls.setCameraYOffset(RenderControls.getCameraYOffset() + 4);
			break;
		}
		case KeyEvent.VK_DOWN: {
			RenderControls.setCameraYOffset(RenderControls.getCameraYOffset() - 4);
			break;
		}
		case KeyEvent.VK_LEFT: {
			RenderControls.setCameraXOffset(RenderControls.getCameraXOffset() + 4);
			break;
		}
		case KeyEvent.VK_RIGHT: {
			RenderControls.setCameraXOffset(RenderControls.getCameraXOffset() - 4);
			break;
		}
		}
	}

	private void queryItemMotion(boolean movement) {
		if (controls.isItemHighlighted() && RenderControls.isItemHighlighted()
				&& ((RenderControls.isLinesMode() && controls.getHighlightedWS() != null)
						|| (RenderControls.isThingsMode() && controls.getHighlightedMOS() != null)
						|| (RenderControls.isVertsMode() && controls.getHighlightedVert() != null))
				&& controls.getHighlightedIndex() != -1) {
			controls.setMovingThing(movement);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_M: {
			queryItemMotion(false);
			keyboardMoving = false;
			break;
		}
		case KeyEvent.VK_Z: {
			keyboardMoving = false;
			RenderControls.setPanMode(false);
			break;
		}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	private class Runner implements Runnable {
		@Override
		public void run() {

			while (true) {
				try {
					Thread.sleep(35);

				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
				repaint();
			}

		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	private Point mousePoint;

	@Override
	public void mousePressed(MouseEvent arg0) {
		this.requestFocus();
		mousePoint = arg0.getPoint();
		if (arg0.getButton() == MouseEvent.BUTTON1) {
			System.out.println("clicky");
			controls.setSelectedItem();
		}
		if (arg0.getButton() == MouseEvent.BUTTON3) {
			RenderControls.setPanMode(true);

		}
		if (arg0.getButton() == MouseEvent.BUTTON2) {
			queryItemMotion(true);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		switch (arg0.getButton()) {
		case MouseEvent.BUTTON3:

			RenderControls.setPanMode(false);
			break;
		case MouseEvent.BUTTON2:
			queryItemMotion(false);
		}
	}

	private void moveAnItem(MouseEvent arg0, int dx, int dy) {
		controls.setMapChanged(true);

		if (RenderControls.isGridSnap() && RenderControls.getGridIntensity() > 1) {
			if (arg0.getX() - oldX > 0)
				controls.setCurrentStoredXPos(controls.getCurrentStoredXPos()
						+ Math.abs(mousePoint.x - arg0.getX()) * RenderControls.getZoomLevel());
			else
				controls.setCurrentStoredXPos(controls.getCurrentStoredXPos()
						- Math.abs(mousePoint.x - arg0.getX()) * RenderControls.getZoomLevel());

			if (arg0.getY() - oldY > 0)
				controls.setCurrentStoredYPos(controls.getCurrentStoredYPos()
						+ Math.abs(mousePoint.y - arg0.getY()) * RenderControls.getZoomLevel());
			else
				controls.setCurrentStoredYPos(controls.getCurrentStoredYPos()
						- Math.abs(mousePoint.y - arg0.getY()) * RenderControls.getZoomLevel());

			if (Math.abs(controls.getCurrentStoredXPos()) > RenderControls.getGridStep()) {
				if (controls.getCurrentStoredXPos() < 0)
					controls.setCurrentStoredXPos(-RenderControls.getGridStep());
				else
					controls.setCurrentStoredXPos(RenderControls.getGridStep());
				exceedXThreshold = true;
			}
			if (Math.abs(controls.getCurrentStoredYPos()) > RenderControls.getGridStep()) {
				if (controls.getCurrentStoredYPos() < 0)
					controls.setCurrentStoredYPos(-RenderControls.getGridStep());
				else
					controls.setCurrentStoredYPos(RenderControls.getGridStep());
				exceedYThreshold = true;
			}

			if (RenderControls.isRot90()) {
				if (exceedXThreshold) {
					moveSelectedYSnap(0, controls.getCurrentStoredXPos());
					controls.setCurrentStoredXPos(0);
				}
				if (exceedYThreshold) {
					moveSelectedXSnap(-controls.getCurrentStoredYPos(), 0);
					controls.setCurrentStoredYPos(0);
				}
			} else {
				if (exceedXThreshold) {
					moveSelectedXSnap((controls.getCurrentStoredXPos()), 0);
					controls.setCurrentStoredXPos(0);
				}
				if (exceedYThreshold) {
					moveSelectedYSnap(0, (controls.getCurrentStoredYPos()));
					controls.setCurrentStoredYPos(0);
				}
			}
		} else {
			moveSelected(dx, dy);
		}

		oldX = arg0.getX();
		oldY = arg0.getY();
		mousePoint = arg0.getPoint();
		exceedXThreshold = false;
		exceedYThreshold = false;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		int notches = arg0.getWheelRotation();
		if (notches < 0) {
			RenderControls.zoom(false, launcher);
		} else {
			RenderControls.zoom(true, launcher);
		}

	}

	private boolean exceedXThreshold = false;
	private boolean exceedYThreshold = false;

	private void moveSelectedXSnap(int dx, int dy) {
		if (RenderControls.isVertsMode()) {
			controls.getHighlightedVert().setX((short) (controls.getHighlightedVert().getX() + (dx)));
			if (controls.getHighlightedIndex() % 2 == 0) {
				DataLists.getWalls().get(controls.getHighlightedIndex() / 2)
						.setX1(controls.getHighlightedVert().getX());
			} else {
				DataLists.getWalls().get(controls.getHighlightedIndex() / 2)
						.setX2(controls.getHighlightedVert().getX());
			}

			DataLists.getVertices().set(controls.getHighlightedIndex(), controls.getHighlightedVert());

		}

		if (RenderControls.isThingsMode()) {
			controls.getHighlightedMOS().setX((short) (controls.getHighlightedMOS().getX() + (dx)));

			DataLists.getObjects().set(controls.getHighlightedIndex(), controls.getHighlightedMOS());
		}
		if (RenderControls.isLinesMode()) {
			controls.getHighlightedWS().setX1((short) (controls.getHighlightedWS().getX1() + (dx)));
			controls.getHighlightedWS().setX2((short) (controls.getHighlightedWS().getX2() + (dx)));
			DataLists.getVertices().get(controls.getHighlightedIndex() * 2)
					.setX((short) controls.getHighlightedWS().getX1());
			DataLists.getVertices().get((controls.getHighlightedIndex() * 2) + 1)
					.setX((short) controls.getHighlightedWS().getX2());
			DataLists.getWalls().set(controls.getHighlightedIndex(), controls.getHighlightedWS());
			RenderControls.setHighlightedWidth(controls.getHighlightedWS().getX2());
			RenderControls.setHighlightedHeight(controls.getHighlightedWS().getY2());
		}
		RenderControls.setHighlightedX(RenderControls.getHighlightedX() + (dx));
	}

	private void moveSelectedYSnap(int dx, int dy) {
		if (RenderControls.isVertsMode()) {
			controls.getHighlightedVert().setY((short) (controls.getHighlightedVert().getY() + (dy)));

			if (controls.getHighlightedIndex() % 2 == 0) {
				DataLists.getWalls().get(controls.getHighlightedIndex() / 2)
						.setY1(controls.getHighlightedVert().getY());
			} else {
				DataLists.getWalls().get(controls.getHighlightedIndex() / 2)
						.setY2(controls.getHighlightedVert().getY());
			}
			DataLists.getVertices().set(controls.getHighlightedIndex(), controls.getHighlightedVert());

		}

		if (RenderControls.isThingsMode()) {
			if (RenderControls.isGridSnap()) {
				controls.getHighlightedMOS().setY((short) (controls.getHighlightedMOS().getY() + (dy)));
			}
			DataLists.getObjects().set(controls.getHighlightedIndex(), controls.getHighlightedMOS());
		}

		if (RenderControls.isLinesMode()) {
			controls.getHighlightedWS().setY1((short) (controls.getHighlightedWS().getY1() + (dy)));
			controls.getHighlightedWS().setY2((short) (controls.getHighlightedWS().getY2() + (dy)));
			DataLists.getVertices().get(controls.getHighlightedIndex() * 2)
					.setY((short) controls.getHighlightedWS().getY1());
			DataLists.getVertices().get((controls.getHighlightedIndex() * 2) + 1)
					.setY((short) controls.getHighlightedWS().getY2());
			DataLists.getWalls().set(controls.getHighlightedIndex(), controls.getHighlightedWS());
			RenderControls.setHighlightedWidth(controls.getHighlightedWS().getX2());
			RenderControls.setHighlightedHeight(controls.getHighlightedWS().getY2());
		}
		RenderControls.setHighlightedY(RenderControls.getHighlightedY() + (dy));
	}

	private void moveSelected(int dx, int dy) {
		if (RenderControls.isVertsMode()) {
			if (RenderControls.isRot90()) {
				controls.getHighlightedVert()
						.setX((short) (controls.getHighlightedVert().getX() + (-dy * (RenderControls.getZoomLevel()))));
				controls.getHighlightedVert()
						.setY((short) (controls.getHighlightedVert().getY() + (dx * (RenderControls.getZoomLevel()))));
			} else {
				controls.getHighlightedVert()
						.setX((short) (controls.getHighlightedVert().getX() + (dx * (RenderControls.getZoomLevel()))));
				controls.getHighlightedVert()
						.setY((short) (controls.getHighlightedVert().getY() + (dy * (RenderControls.getZoomLevel()))));
			}
			if (controls.getHighlightedIndex() % 2 == 0) {
				DataLists.getWalls().get(controls.getHighlightedIndex() / 2)
						.setX1(controls.getHighlightedVert().getX());
				DataLists.getWalls().get(controls.getHighlightedIndex() / 2)
						.setY1(controls.getHighlightedVert().getY());
			} else {
				DataLists.getWalls().get(controls.getHighlightedIndex() / 2)
						.setX2(controls.getHighlightedVert().getX());
				DataLists.getWalls().get(controls.getHighlightedIndex() / 2)
						.setY2(controls.getHighlightedVert().getY());
			}

			DataLists.getVertices().set(controls.getHighlightedIndex(), controls.getHighlightedVert());

		}

		if (RenderControls.isThingsMode()) {
			if (RenderControls.isRot90()) {
				controls.getHighlightedMOS()
						.setX((short) (controls.getHighlightedMOS().getX() + (-dy * (RenderControls.getZoomLevel()))));
				controls.getHighlightedMOS()
						.setY((short) (controls.getHighlightedMOS().getY() + (dx * (RenderControls.getZoomLevel()))));
			} else {
				controls.getHighlightedMOS()
						.setX((short) (controls.getHighlightedMOS().getX() + (dx * (RenderControls.getZoomLevel()))));
				controls.getHighlightedMOS()
						.setY((short) (controls.getHighlightedMOS().getY() + (dy * (RenderControls.getZoomLevel()))));
			}

			DataLists.getObjects().set(controls.getHighlightedIndex(), controls.getHighlightedMOS());
		}
		if (RenderControls.isLinesMode()) {
			if (RenderControls.isRot90()) {
				controls.getHighlightedWS()
						.setX1((short) (controls.getHighlightedWS().getX1() + (-dy * RenderControls.getZoomLevel())));
				controls.getHighlightedWS()
						.setX2((short) (controls.getHighlightedWS().getX2() + (-dy * RenderControls.getZoomLevel())));
				controls.getHighlightedWS()
						.setY1((short) (controls.getHighlightedWS().getY1() + (dx * RenderControls.getZoomLevel())));
				controls.getHighlightedWS()
						.setY2((short) (controls.getHighlightedWS().getY2() + (dx * RenderControls.getZoomLevel())));
				DataLists.getVertices().get(controls.getHighlightedIndex() * 2)
						.setX((short) controls.getHighlightedWS().getX1());
				DataLists.getVertices().get(controls.getHighlightedIndex() * 2)
						.setY((short) controls.getHighlightedWS().getY1());
				DataLists.getVertices().get((controls.getHighlightedIndex() * 2) + 1)
						.setX((short) controls.getHighlightedWS().getX2());
				DataLists.getVertices().get((controls.getHighlightedIndex() * 2) + 1)
						.setY((short) controls.getHighlightedWS().getY2());
				DataLists.getWalls().set(controls.getHighlightedIndex(), controls.getHighlightedWS());
				RenderControls.setHighlightedWidth(controls.getHighlightedWS().getX2());
				RenderControls.setHighlightedHeight(controls.getHighlightedWS().getY2());
			} else {
				controls.getHighlightedWS()
						.setX1((short) (controls.getHighlightedWS().getX1() + (dx * RenderControls.getZoomLevel())));
				controls.getHighlightedWS()
						.setX2((short) (controls.getHighlightedWS().getX2() + (dx * RenderControls.getZoomLevel())));
				controls.getHighlightedWS()
						.setY1((short) (controls.getHighlightedWS().getY1() + (dy * RenderControls.getZoomLevel())));
				controls.getHighlightedWS()
						.setY2((short) (controls.getHighlightedWS().getY2() + (dy * RenderControls.getZoomLevel())));
				DataLists.getVertices().get(controls.getHighlightedIndex() * 2)
						.setX((short) controls.getHighlightedWS().getX1());
				DataLists.getVertices().get(controls.getHighlightedIndex() * 2)
						.setY((short) controls.getHighlightedWS().getY1());
				DataLists.getVertices().get((controls.getHighlightedIndex() * 2) + 1)
						.setX((short) controls.getHighlightedWS().getX2());
				DataLists.getVertices().get((controls.getHighlightedIndex() * 2) + 1)
						.setY((short) controls.getHighlightedWS().getY2());
				DataLists.getWalls().set(controls.getHighlightedIndex(), controls.getHighlightedWS());
				RenderControls.setHighlightedWidth(controls.getHighlightedWS().getX2());
				RenderControls.setHighlightedHeight(controls.getHighlightedWS().getY2());
			}
		}
		if (RenderControls.isRot90()) {
			RenderControls.setHighlightedX(RenderControls.getHighlightedX() + (-dy * RenderControls.getZoomLevel()));
			RenderControls.setHighlightedY(RenderControls.getHighlightedY() + (dx * RenderControls.getZoomLevel()));
		} else {
			RenderControls.setHighlightedX(RenderControls.getHighlightedX() + (dx * RenderControls.getZoomLevel()));
			RenderControls.setHighlightedY(RenderControls.getHighlightedY() + (dy * RenderControls.getZoomLevel()));
		}
	}

	int oldX = 0;
	int oldY = 0;

	private boolean keyboardMoving = false;

	@Override
	public void mouseDragged(MouseEvent arg0) {
		if (!keyboardMoving) {
			int dx = arg0.getX() - mousePoint.x;
			int dy = arg0.getY() - mousePoint.y;
			if (RenderControls.isPanMode()) {
				RenderControls
						.setCameraXOffset(RenderControls.getCameraXOffset() - dx * (RenderControls.getZoomLevel()));
				RenderControls
						.setCameraYOffset(RenderControls.getCameraYOffset() - dy * (RenderControls.getZoomLevel()));
				mousePoint = arg0.getPoint();
			}

			if (controls.isMovingThing()) {
				moveAnItem(arg0, dx, dy);
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		if(mousePoint == null) mousePoint = arg0.getPoint();
		int dx = arg0.getX() - mousePoint.x;
		int dy = arg0.getY() - mousePoint.y;
		mousePoint = arg0.getPoint();
		if (RenderControls.isPanMode()) {
			RenderControls.setCameraXOffset(RenderControls.getCameraXOffset() - dx * (RenderControls.getZoomLevel()));
			RenderControls.setCameraYOffset(RenderControls.getCameraYOffset() - dy * (RenderControls.getZoomLevel()));
			mousePoint = arg0.getPoint();
		}

		if (controls.isMovingThing()) {
			moveAnItem(arg0, dx, dy);
		}

		int mouseX = (RenderControls.getCameraXOffset() * RenderControls.getZoomLevel()) / RenderControls.getZoomLevel()
				+ arg0.getX() * RenderControls.getZoomLevel();
		int mouseY = (RenderControls.getCameraYOffset() * RenderControls.getZoomLevel()) / RenderControls.getZoomLevel()
				+ arg0.getY() * RenderControls.getZoomLevel();
		if (RenderControls.isRot90()) {
			int temp = mouseX;
			mouseX = -mouseY;
			mouseY = temp;
		}
		launcher.setMouseXLabel(mouseX, mouseY);

		controls.pollItemHighlight(mouseX, mouseY);
		oldX = arg0.getX();
		oldY = arg0.getY();
	}
}