package aneurysm.config;

import aneurysm.render.RenderControls;

public class ConfigOptions {
	private String location="";
	private int[] levelGrid = new int[15];
	private int[] levelZoom = {16,16,16,16,16,16,16,16,16,16,16,16,16,16,16};
	private boolean[] levelRot = new boolean[15];
	private boolean noCD = false;
	
	public boolean isNoCD() {
		return noCD;
	}

	public void setNoCD(boolean noCD) {
		this.noCD = noCD;
	}

	private int[] levelMode = new int[15];
	private int getMapSize = 15;
	
	public int getGetMapSize() {
		return getMapSize;
	}

	public void saveMapConfigs() {
		levelZoom[currentLevel] = RenderControls.getZoomLevel();
		levelGrid[currentLevel] = RenderControls.getGridIntensity();
		levelRot[currentLevel] = RenderControls.isRot90();
		int mode=0;
		if(RenderControls.isVertsMode()) mode=0;
		if(RenderControls.isLinesMode()) mode=1;
		if(RenderControls.isThingsMode()) mode=2;
		levelMode[currentLevel] = mode;
	}
	
	public int getLevelMode(int index) {
		return levelMode[index];
	}

	public void setLevelMode(int levelMode, int index) {
		this.levelMode[index] = levelMode;
	}

	private int currentLevel;
	
	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

	public int getLevelGrid(int index) {
		return levelGrid[index];
	}

	public void setLevelGrid(int levelGrid, int index) {
		this.levelGrid[index] = levelGrid;
	}

	public int getLevelZoom(int index) {
		return levelZoom[index];
	}

	public void setLevelZoom(int levelZoom, int index) {
		this.levelZoom[index] = levelZoom;
	}

	public boolean getLevelRot(int index) {
		return levelRot[index];
	}

	public void setLevelRot(boolean levelRot, int index) {
		this.levelRot[index] = levelRot;
	}

	public String getLocation() {
		return location;
	}
	
	public void setLocation(String loc) {
		location = loc;
	}
}
