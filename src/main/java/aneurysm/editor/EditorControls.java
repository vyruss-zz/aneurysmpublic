package aneurysm.editor;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JOptionPane;

import aneurysm.io.FileReader;
import aneurysm.render.RenderControls;
import aneurysm.structures.Line;
import aneurysm.structures.MapObjectStructure;
import aneurysm.structures.Vertex;
import aneurysm.structures.WallStructure;
import aneurysm.ui.ComponentLauncher;
import aneurysm.ui.DataLists;
import aneurysm.ui.ImageStringRenderer;
import aneurysm.ui.Window;

public class EditorControls {

	private String selectedInfo;
	private String selInfor;
	private String highlightedInfo;
	private boolean itemHighlighted = false;
	private boolean movingThing = false;
	private boolean itemSelected = false;
	private boolean mapChanged = false;
	private int currentStoredXPos;
	private int currentStoredYPos;
	private MapObjectStructure highlightedMOS = null;
	private WallStructure highlightedWS = null;
	private Vertex highlightedVert = null;
	private MapObjectStructure selectedMOS = null;
	private WallStructure selectedWS = null;
	private Vertex selectedVert = null;
	private int selectedIndex = -1;
	private int highlightedIndex = -1;

	public int getCurrentStoredYPos() {
		return currentStoredYPos;
	}

	public void setCurrentStoredYPos(int currentStoredYPos) {
		this.currentStoredYPos = currentStoredYPos;
	}

	public int getCurrentStoredXPos() {
		return currentStoredXPos;
	}

	public void setCurrentStoredXPos(int currentStoredXPos) {
		this.currentStoredXPos = currentStoredXPos;
	}

	public String getHighlightedInfo() {
		return highlightedInfo;
	}

	public void setHighlightedInfo(String highlightedInfo) {
		this.highlightedInfo = highlightedInfo;
	}

	public MapObjectStructure getSelectedMOS() {
		return selectedMOS;
	}

	public void setSelectedMOS(MapObjectStructure selectedMOS) {
		this.selectedMOS = selectedMOS;
	}

	public WallStructure getSelectedWS() {
		return selectedWS;
	}

	public void setSelectedWS(WallStructure selectedWS) {
		this.selectedWS = selectedWS;
	}

	public Vertex getSelectedVert() {
		return selectedVert;
	}

	public void setSelectedVert(Vertex selectedVert) {
		this.selectedVert = selectedVert;
	}

	public int getHighlightedIndex() {
		return highlightedIndex;
	}

	public void setHighlightedIndex(int highlightedIndex) {
		this.highlightedIndex = highlightedIndex;
	}

	private Window host;

	public String getSelectedInfo() {
		return selectedInfo;
	}

	public void setSelectedInfo(String selectedInfo) {
		this.selectedInfo = selectedInfo;
	}

	public boolean isItemHighlighted() {
		return itemHighlighted;
	}

	public void setItemHighlighted(boolean itemHighlighted) {
		this.itemHighlighted = itemHighlighted;
	}

	public boolean isMovingThing() {
		return movingThing;
	}

	public void setMovingThing(boolean movingThing) {
		this.movingThing = movingThing;
	}

	public boolean isItemSelected() {
		return itemSelected;
	}

	public void setItemSelected(boolean itemSelected) {
		this.itemSelected = itemSelected;
	}

	public boolean isMapChanged() {
		return mapChanged;
	}

	public void setMapChanged(boolean mapChanged) {
		this.mapChanged = mapChanged;
	}

	public MapObjectStructure getHighlightedMOS() {
		return highlightedMOS;
	}

	public void setHighlightedMOS(MapObjectStructure highlightedMOS) {
		this.highlightedMOS = highlightedMOS;
	}

	public WallStructure getHighlightedWS() {
		return highlightedWS;
	}

	public void setHighlightedWS(WallStructure highlightedWS) {
		this.highlightedWS = highlightedWS;
	}

	public Vertex getHighlightedVert() {
		return highlightedVert;
	}

	public void setHighlightedVert(Vertex highlightedVert) {
		this.highlightedVert = highlightedVert;
	}

	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	public EditorControls getInstance() {
		return this;
	}

	public EditorControls(Window host) {
		this.host = host;
	}

	public static void prepLinesAndVerts() {
		if (DataLists.getLines().size() > 0)
			DataLists.getLines().clear();
		if (DataLists.getVertices().size() > 0)
			DataLists.getVertices().clear();
		Vertex v1;
		Vertex v2;
		Line l;
		for (int i = 0; i < DataLists.getWalls().size(); i++) {
			v1 = new Vertex(DataLists.getWalls().get(i).getX1(), DataLists.getWalls().get(i).getY1());
			v2 = new Vertex(DataLists.getWalls().get(i).getX2(), DataLists.getWalls().get(i).getY2());
			l = new Line(v1, v2);
			DataLists.getVertices().add(v1);
			DataLists.getVertices().add(v2);
			DataLists.getLines().add(l);
		}
		RenderControls.setCameraXOffset(DataLists.getVertices().get(0).getX());
		RenderControls.setCameraYOffset(DataLists.getVertices().get(0).getY());
		RenderControls.setGridStartX(DataLists.getVertices().get(0).getX());
		RenderControls.setGridStartY(DataLists.getVertices().get(0).getY());
	}

	public void setMapConfig(ComponentLauncher launcher) {
		RenderControls.setZoomLevel(FileReader.getConfig().getLevelZoom(FileReader.getConfig().getCurrentLevel()));
		RenderControls.setGridIntensity(FileReader.getConfig().getLevelGrid(FileReader.getConfig().getCurrentLevel()));
		RenderControls.setRot90(FileReader.getConfig().getLevelRot(FileReader.getConfig().getCurrentLevel()));
		switch (FileReader.getConfig().getLevelMode(FileReader.getConfig().getCurrentLevel())) {
		case 0:
			RenderControls.setVertsMode(true);
			break;
		case 1:
			setTextureList();
			RenderControls.setLinesMode(true);
			break;
		case 2:
			setThingList();
			RenderControls.setThingsMode(true);
			break;
		}
	}

	public void writeToROM(Window window) {
		int result = JOptionPane.showConfirmDialog(
				window, ("Do you wish to write your changes to the ROM located at "
						+ FileReader.getConfig().getLocation() + "?\n\nWARNING: YOU MAY CORRUPT YOUR ROM."),
				"Write to ROM", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			DataLists.setChangesMade(true);
			Window.getReader().writeLevelToFile(DataLists.getWalls(), DataLists.getObjects());
		}
	}

	public double getLineAngle() {
		double rise = Math
				.abs(Math.abs(RenderControls.getHighlightedY()) - Math.abs(RenderControls.getHighlightedHeight()));
		double run = Math
				.abs(Math.abs(RenderControls.getHighlightedX()) - Math.abs(RenderControls.getHighlightedWidth()));
		return Math.toDegrees(Math.atan(rise / run));
	}

	public double getLineLength() {
		double l = 0;
		if (RenderControls.getHighlightedX() == RenderControls.getHighlightedWidth()) {
			if (RenderControls.getHighlightedY() > RenderControls.getHighlightedHeight()) {
				l = Math.abs(RenderControls.getHighlightedY()) - Math.abs(RenderControls.getHighlightedHeight());
			} else {
				l = Math.abs(RenderControls.getHighlightedHeight() - RenderControls.getHighlightedY());
			}
		} else if (RenderControls.getHighlightedY() == RenderControls.getHighlightedHeight()) {
			if (RenderControls.getHighlightedX() > RenderControls.getHighlightedWidth()) {
				l = Math.abs(RenderControls.getHighlightedX()) - Math.abs(RenderControls.getHighlightedWidth());
			} else {
				l = Math.abs(RenderControls.getHighlightedWidth()) - Math.abs(RenderControls.getHighlightedX());
			}
		} else {
			// trig time
			double base, height;
			base = Math
					.abs(Math.abs(RenderControls.getHighlightedX()) - Math.abs(RenderControls.getHighlightedWidth()));
			height = Math
					.abs(Math.abs(RenderControls.getHighlightedY()) - Math.abs(RenderControls.getHighlightedHeight()));
			l = Math.sqrt((base * base) + (height * height));
		}
		return Math.abs(l);
	}

//	public void saveFile(Window window) {
//		JFileChooser fileChooser = new JFileChooser();
//		fileChooser.setDialogTitle("Export Level Data");
//		int option = fileChooser.showSaveDialog(window);
//		if (option == JFileChooser.APPROVE_OPTION) {
//			try {
//				Window.getReader().exportToFile(DataLists.getWalls(), DataLists.getObjects(),
//						fileChooser.getSelectedFile().toString());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}

	public void updateMap(ComponentLauncher launcher) {
		/*
		 * if(mapChanged) { confirmChangemap(); }
		 */
		if (!DataLists.getWallImages().isEmpty())
			DataLists.getWallImages().clear();
		try {
			Window.getReader().grabHeaderData(FileReader.getConfig().getLocation());
			loadData();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private void setHighlightedThing(MapObjectStructure m, int i) {
		RenderControls.setHighlightedX(m.getX());
		RenderControls.setHighlightedY(m.getY());
		RenderControls.setHighlightedWidth(16);
		RenderControls.setHighlightedHeight(16);
		RenderControls.setHighlightedInfo("" + m.getThingID());
		highlightedInfo = ("<html>Thing Number: " + i + "<br/>Thing x: " + m.getX() + "<br/>Thing y: "
				+ m.getY() + "<br/>" + DataLists.getThingAttributes().get(m.getThingID())
				+ "<br/>Offset Major: 0x" + Integer.toHexString(m.getOffset_major())
				+ "<br/>Offset Minor: " + Integer.toHexString(m.getOffset_minor()) + "</html>");
		host.getLauncher().getSelectPanel().setItemInfo(highlightedInfo);
		host.getLauncher().getSelectPanel().setCurrentImage(DataLists.getObjectImage(m.getThingID()));
		host.getLauncher().getSelectPanel().getItemBox()
				.setSelectedItem("0x0" + Integer.toHexString(m.getThingID()));
	}

	public void pollItemHighlight(int mouseX, int mouseY) {
		if (!itemSelected && selectedIndex == -1) {
			if (RenderControls.isThingsMode()) {
				MapObjectStructure m = null;
				for (int i = 0; i < DataLists.getObjects().size(); i++) {
					m = DataLists.getObjects().get(i);

					if ((m.getX() + 32 > mouseX && m.getX() - 32 < mouseX)
							&& (m.getY() - 32 < mouseY && m.getY() + 32 > mouseY)) {
						currentStoredXPos = 0;
						currentStoredYPos = 0;
						RenderControls.setItemHighlighted(true);
						
						setHighlightedThing(m, i);
						
						highlightedMOS = m;
						highlightedIndex = i;
						itemHighlighted = true;
						return;
					}
				}

				RenderControls.setItemHighlighted(false);
				RenderControls.setHighlightedX(0);
				RenderControls.setHighlightedY(0);
				RenderControls.setHighlightedWidth(0);
				RenderControls.setHighlightedHeight(0);
				highlightedIndex = -1;
				highlightedMOS = null;
				itemHighlighted = false;
				highlightedInfo = "";
			}

			if (RenderControls.isLinesMode()) {
				WallStructure w = null;
				double length = 0;
				double angle = 0;
				for (int i = 0; i < DataLists.getWalls().size(); i++) {
					w = DataLists.getWalls().get(i);
					if (((w.getX1() + 16 > mouseX && w.getX2() - 16 < mouseX) && (w.getY1() - 16 < mouseY)
							&& (w.getY2() + 16 > mouseY))
							|| ((w.getY1() + 16 > mouseY && w.getY2() - 16 < mouseY) && (w.getX1() - 16 < mouseX)
									&& (w.getX2() + 16 > mouseX))
							|| (w.getX1() + 16 < mouseX && w.getY1() + 16 < mouseY && w.getX2() - 16 > mouseX
									&& w.getY2() - 16 > mouseY)
							|| ((w.getX2() + 16 > mouseX && w.getX1() - 16 < mouseX) && (w.getY2() - 16 < mouseY)
									&& (w.getY1() + 16 > mouseY))
							|| ((w.getY2() + 16 > mouseY && w.getY1() - 16 < mouseY) && (w.getX2() - 16 < mouseX)
									&& (w.getX1() + 16 > mouseX))
							|| (w.getX2() + 16 < mouseX && w.getY2() + 16 < mouseY && w.getX1() - 16 > mouseX
									&& w.getY1() - 16 > mouseY)) {
						highlightedIndex = i;
						highlightedWS = w;
						itemHighlighted = true;
						currentStoredXPos = 0;
						currentStoredYPos = 0;

						RenderControls.setItemHighlighted(true);
						RenderControls.setHighlightedX(w.getX1());
						RenderControls.setHighlightedY(w.getY1());
						RenderControls.setHighlightedWidth(w.getX2());
						RenderControls.setHighlightedHeight(w.getY2());
						length = getLineLength();
						angle = getLineAngle();
						highlightedInfo = ("<html>Line Number: " + i + "<br/>Line Start X: " + w.getX1()
								+ "<br/>Start Y: " + w.getY1() + "<br/>End X: " + w.getX2() + "<br/>End Y: " + w.getY2()
								+ "<br/>Length: " + length + "<br/>Angle: " + angle + "<br/>Texture ID: 0x00"
								+ Integer.toHexString(w.getTextureID()) + "<br/>Texture Length: 0x"
								+ Integer.toHexString(w.getTextureScale()) + "<br/>Line Type: "
								+ (w.getDoorType() == 0 ? "Wall" : "Door") + "</html>");

						host.getLauncher().getSelectPanel().setItemInfo(highlightedInfo);
						int imageOffset = w.getTextureID();

						host.getLauncher().getSelectPanel().setImage(imageOffset);

						host.getLauncher().getSelectPanel().getItemBox()
								.setSelectedItem(Integer.toHexString(w.getTextureID()));

						host.getLauncher().getSelectPanel().setBoxValues(w.getDoorType(), w.getDoorNumber(),
								w.getDoorNSLow(), w.getDoorNSHigh(), w.getDoorWELow(), w.getDoorWEHigh(),
								w.getTextureScale(), w.getTextureID());

						return;
					}
				}

				RenderControls.setItemHighlighted(false);
				RenderControls.setHighlightedX(0);
				RenderControls.setHighlightedY(0);
				RenderControls.setHighlightedWidth(0);
				RenderControls.setHighlightedHeight(0);
				host.getLauncher().setTextAreaContents("Nothing highlighted.");
				highlightedIndex = -1;
				highlightedWS = null;
				itemHighlighted = false;
				highlightedInfo = "";
			}

			if (RenderControls.isVertsMode()) {
				Vertex v = null;
				for (int i = 0; i < DataLists.getVertices().size(); i++) {
					v = DataLists.getVertices().get(i);

					if ((v.getX() + 16 > mouseX && v.getX() - 16 < mouseX)
							&& (v.getY() - 16 < mouseY && v.getY() + 16 > mouseY)) {
						currentStoredXPos = 0;
						currentStoredYPos = 0;

						RenderControls.setItemHighlighted(true);
						RenderControls.setHighlightedX(v.getX());
						RenderControls.setHighlightedY(v.getY());
						RenderControls.setHighlightedWidth(16);
						RenderControls.setHighlightedHeight(16);
						itemHighlighted = true;
						highlightedVert = v;
						highlightedIndex = i;
						highlightedInfo = ("<html>Vertex x: " + v.getX() + "<br/>Vertex y: " + v.getY() + "</html>");
						host.getLauncher().getSelectPanel().setItemInfo(highlightedInfo);
						return;
					}
				}
				RenderControls.setItemHighlighted(false);
				RenderControls.setHighlightedX(0);
				RenderControls.setHighlightedY(0);
				RenderControls.setHighlightedWidth(0);
				RenderControls.setHighlightedHeight(0);
				itemHighlighted = false;
				highlightedIndex = -1;
				highlightedVert = null;
				highlightedInfo = "";
			}

			if (itemSelected && !itemHighlighted) {
				if (RenderControls.isLinesMode())
					host.getLauncher().getSelectPanel()
							.setCurrentImage(DataLists.getWallImages().get(selectedWS.getTextureID()));
				if (RenderControls.isThingsMode())
					host.getLauncher().getSelectPanel()
							.setCurrentImage(DataLists.getObjectImage(selectedMOS.getThingID()));
				host.getLauncher().getSelectPanel().setItemInfo(selInfor);
			} else {
				host.getLauncher().getSelectPanel()
						.setCurrentImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
				host.getLauncher().getSelectPanel().setItemInfo("");
			}
		}
	}

	public void setSelectedItem() {
		if (!itemSelected && itemHighlighted) {
			RenderControls.setItemSelected(true);
			itemSelected = true;
			if (RenderControls.isThingsMode())
				selectedMOS = DataLists.getObjects().get(highlightedIndex);
			if (RenderControls.isLinesMode())
				selectedWS = DataLists.getWalls().get(highlightedIndex);
			if (RenderControls.isVertsMode())
				selectedVert = DataLists.getVertices().get(highlightedIndex);
			int h = RenderControls.getHighlightedHeight(), w = RenderControls.getHighlightedWidth(),
					x = RenderControls.getHighlightedX(), y = RenderControls.getHighlightedY();
			RenderControls.setSelectedHeight(h);
			RenderControls.setSelectedWidth(w);
			RenderControls.setSelectedX(x);
			RenderControls.setSelectedY(y);
			selectedIndex = highlightedIndex;
			selInfor = highlightedInfo;
			selectedInfo = RenderControls.getHighlightedInfo();
			RenderControls.setSelectedInfo(selectedInfo);
			if (!RenderControls.isVertsMode())
				host.getLauncher().getSelectPanel().getItemBox().setVisible(true);
			if (RenderControls.isThingsMode())
				host.getLauncher().getSelectPanel().getItemBox()
						.setSelectedItem(Integer.toHexString(selectedMOS.getThingID()));
			if (RenderControls.isLinesMode()) {
				host.getLauncher().getSelectPanel().getItemBox()
						.setSelectedItem(Integer.toHexString(selectedWS.getTextureID()));
				host.getLauncher().getSelectPanel().showWallControls();
			}
		}

		else {
			itemHighlighted = false;
			RenderControls.setItemHighlighted(false);
			clearSelection();
			host.getLauncher().getSelectPanel().hideWallControls();
		}
	}

	public void updateSelectedItem() {
		if (RenderControls.isThingsMode()) {
			DataLists.getObjects().set(selectedIndex, selectedMOS);
			setHighlightedThing(selectedMOS, selectedIndex);
		}
		if (RenderControls.isLinesMode()) {
			DataLists.getWalls().set(selectedIndex, selectedWS);
		}
	}

	public void clearSelection() {
		RenderControls.setItemSelected(false);
		itemSelected = false;
		selectedIndex = -1;
		selectedWS = null;
		selectedVert = null;
		selectedMOS = null;
		selectedInfo = "";
		selInfor = "";
		RenderControls.setSelectedInfo(selectedInfo);
		RenderControls.setSelectedHeight(0);
		RenderControls.setSelectedWidth(0);
		RenderControls.setSelectedX(0);
		RenderControls.setSelectedY(0);
		host.getLauncher().getSelectPanel().setCurrentImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
		host.getLauncher().getSelectPanel().setItemInfo("");
		host.getLauncher().getSelectPanel().getItemBox().setVisible(false);
	}

	public void confirmChangemap() {
		int result = JOptionPane.showConfirmDialog(host,
				("You have unsaved changes.  Would you like to save first?\n\nWARNING: YOU MAY CORRUPT YOUR ROM."),
				"Write to ROM", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			DataLists.setChangesMade(true);
			Window.getReader().writeLevelToFile(DataLists.getWalls(), DataLists.getObjects());
		}
		mapChanged = false;
	}

	public void loadData() throws IOException {
		setMapConfig(host.getLauncher());
		FileReader.getConfig().saveMapConfigs();
		if (DataLists.getObjects().size() > 0)
			DataLists.getObjects().clear();
		DataLists.setObjects(Window.getReader().readMapObjects(FileReader.getConfig().getLocation()));
		if (DataLists.getWalls().size() > 0)
			DataLists.getWalls().clear();
		DataLists.setWalls(Window.getReader().readWalls(FileReader.getConfig().getLocation()));
		DataLists.setupColors();
		Window.getReader().readWallGFX(false, FileReader.getConfig().getLocation());
		if (RenderControls.isLinesMode())
			setTextureList();
		if (RenderControls.isThingsMode())
			setThingList();
		host.getLauncher().setSnapOn("Off");
		host.getLauncher().getChb1().setSelected(RenderControls.isRot90());
		host.getLauncher().setLevelSelection(!DataLists.isCdOrCart());

		prepLinesAndVerts();
	}

	public void setThingList() {

		host.getLauncher().getSelectPanel().getItemBox().setName("Thing ID");
		host.getLauncher().getSelectPanel().getItemBox().removeItemListener(host.getLauncher().getSelectPanel());
		host.getLauncher().getSelectPanel().getItemBox().removeAllItems();
		host.getLauncher().getSelectPanel().getItemBox().setRenderer(null);

		for (int i = 0; i < DataLists.getSpriteList().length; i++) {

			if (DataLists.getSpriteList()[i].getOffset() != 0)
				host.getLauncher().getSelectPanel().getItemBox()
						.addItem(Integer.toHexString(DataLists.getSpriteList()[i].getObjectNumber()));

		}
		host.getLauncher().getSelectPanel().getItemBox().setRenderer(new ImageStringRenderer());
		host.getLauncher().getSelectPanel().getItemBox().addItemListener(host.getLauncher().getSelectPanel());

	}

	public void setTextureList() {
		host.getLauncher().getSelectPanel().getItemBox().setName("Texture ID");
		host.getLauncher().getSelectPanel().getItemBox().removeItemListener(host.getLauncher().getSelectPanel());
		host.getLauncher().getSelectPanel().getItemBox().removeAllItems();
		host.getLauncher().getSelectPanel().getItemBox().setRenderer(null);
		for (Integer key : DataLists.getWallImages().keySet()) {
			host.getLauncher().getSelectPanel().getItemBox().addItem(Integer.toHexString(key));
		}
		host.getLauncher().getSelectPanel().getItemBox().setRenderer(new ImageStringRenderer());
		host.getLauncher().getSelectPanel().getItemBox().addItemListener(host.getLauncher().getSelectPanel());
	}
}
