package aneurysm.render;

import aneurysm.ui.ComponentLauncher;

public class RenderControls {

	private static boolean panMode = false;

	private static boolean gridSnap = false;
	private static int gridIntensity = 16;
	private static int zoomLevel = 1;
	private static int cameraXOffset = 0;
	private static int cameraYOffset = 0;
	private static short gridStep = 0;
	private static short gridStartX, gridStartY;
	private static boolean rot90 = false;

	private static boolean thingsMode = false;
	private static boolean linesMode = false;
	private static boolean vertsMode = true;

	private static boolean itemHighlighted = false;
	private static boolean itemSelected = false;

	private static String highlightedInfo;

	private static String selectedInfo;
	
	private static int highlightedX = 0;
	private static int highlightedY = 0;
	private static int highlightedWidth = 0;
	private static int highlightedHeight = 0;

	private static int selectedX = 0;
	private static int selectedY = 0;
	private static int selectedWidth = 0;
	private static int selectedHeight = 0;

	
	
	
	public static short getGridStep() {
		return gridStep;
	}

	public static void setGridStep(short gridStep) {
		RenderControls.gridStep = gridStep;
	}

	public static boolean isGridSnap() {
		return gridSnap;
	}

	public static void setGridSnap(boolean gridSnap) {
		RenderControls.gridSnap = gridSnap;
	}

	public static String getSelectedInfo() {
		return selectedInfo;
	}

	public static void setSelectedInfo(String selectedInfo) {
		RenderControls.selectedInfo = selectedInfo;
	}

	public static int getSelectedX() {
		return selectedX;
	}

	public static void setSelectedX(int selectedX) {
		RenderControls.selectedX = selectedX;
	}

	public static int getSelectedY() {
		return selectedY;
	}

	public static void setSelectedY(int selectedY) {
		RenderControls.selectedY = selectedY;
	}

	public static int getSelectedWidth() {
		return selectedWidth;
	}

	public static void setSelectedWidth(int selectedWidth) {
		RenderControls.selectedWidth = selectedWidth;
	}

	public static int getSelectedHeight() {
		return selectedHeight;
	}

	public static void setSelectedHeight(int selectedHeight) {
		RenderControls.selectedHeight = selectedHeight;
	}

	public static int getGridStartX() {
		if(rot90) return gridStartY;
		else return gridStartX;
	}

	public static void setGridStartX(short gridStartX) {
		RenderControls.gridStartX = gridStartX;
	}

	public static short getGridStartY() {
		if(rot90) return (short) -gridStartX;
		else return gridStartY;
	}

	public static void setGridStartY(short gridStartY) {
		RenderControls.gridStartY = gridStartY;
	}

	public static boolean isRot90() {
		return rot90;
	}

	public static void setRot90(boolean rot90) {
		RenderControls.rot90 = rot90;
	}

	public static boolean isItemSelected() {
		return itemSelected;
	}

	public static void setItemSelected(boolean itemSelected) {
		RenderControls.itemSelected = itemSelected;
	}

	public static String getHighlightedInfo() {
		return highlightedInfo;
	}

	public static void setHighlightedInfo(String hiInfo) {
		highlightedInfo = hiInfo;
	}

	public static boolean isPanMode() {
		return panMode;
	}

	public static void setPanMode(boolean mode) {
		panMode = mode;
	}

	public static int getGridIntensity() {
		return gridIntensity;
	}

	public static void setGridIntensity(int gridIntensity) {
		RenderControls.gridIntensity = gridIntensity;
	}

	public static int getZoomLevel() {
		return zoomLevel;
	}

	public static void setZoomLevel(int zoomLevel) {
		RenderControls.zoomLevel = zoomLevel;
	}

	public static int getCameraXOffset() {
		return cameraXOffset;
	}

	public static void setCameraXOffset(int cameraXOffset) {
		RenderControls.cameraXOffset = cameraXOffset;
	}

	public static int getCameraYOffset() {
		return cameraYOffset;
	}

	public static void setCameraYOffset(int cameraYOffset) {
		RenderControls.cameraYOffset = cameraYOffset;
	}

	public static boolean isThingsMode() {
		return thingsMode;
	}

	public static void setThingsMode(boolean thingsMode) {
		RenderControls.thingsMode = true;
		RenderControls.linesMode = false;
		RenderControls.vertsMode = false;
	}

	public static boolean isLinesMode() {
		return linesMode;
	}

	public static void setLinesMode(boolean linesMode) {
		RenderControls.linesMode = true;
		RenderControls.thingsMode = false;
		RenderControls.vertsMode = false;
	}

	public static boolean isVertsMode() {
		return vertsMode;
	}

	public static void setVertsMode(boolean vertsMode) {
		RenderControls.vertsMode = true;
		RenderControls.linesMode = false;
		RenderControls.thingsMode = false;
	}

	public static boolean isItemHighlighted() {
		return itemHighlighted;
	}

	public static void setItemHighlighted(boolean itemHighlighted) {
		RenderControls.itemHighlighted = itemHighlighted;
	}

	public static int getHighlightedX() {
		return highlightedX;
	}

	public static void setHighlightedX(int highlightedX) {
		RenderControls.highlightedX = highlightedX;
	}

	public static int getHighlightedY() {
		return highlightedY;
	}

	public static void setHighlightedY(int highlightedY) {
		RenderControls.highlightedY = highlightedY;
	}

	public static int getHighlightedWidth() {
		return highlightedWidth;
	}

	public static void setHighlightedWidth(int highlightedWidth) {
		RenderControls.highlightedWidth = highlightedWidth;
	}

	public static int getHighlightedHeight() {
		return highlightedHeight;
	}

	public static void setHighlightedHeight(int highlightedHeight) {
		RenderControls.highlightedHeight = highlightedHeight;
	}

	public static void cycleGrid() {
		if (gridIntensity == 0) {
			gridIntensity = 256;
			gridStep = (short) ((short) 2*gridIntensity);
			return;
		}
		if (gridIntensity == 4) {
			gridIntensity = 2;
			gridStep = (short) ((short) 2*gridIntensity);
			return;
		}

		
		gridIntensity /= 2;
		gridStep = (short) ((short) 2*gridIntensity);
	}

	public static void zoom(boolean inOrOut, ComponentLauncher launcher) {
		if (inOrOut) {
			if (zoomLevel < 32) {
				zoomLevel += 1;
				cameraXOffset += 4 * zoomLevel;
				cameraYOffset += 4 * zoomLevel;
			}
		} else {
			if (zoomLevel >= 2) {
				zoomLevel -= 1;
				cameraXOffset -= 4 * zoomLevel;
				cameraYOffset -= 4 * zoomLevel;
			}
		}
		launcher.setZoomLabel(zoomLevel);
	}

}
