package aneurysm.config;

import aneurysm.render.RenderControls;

public class ConfigOptions {
	private String location="";
	private int[] levelXOffs = {-4270,-4424,11272,10564,-13249,10609,11100,11404,11103,-4272,-4440,7203,-4890,-640,-2138};
	private int[] levelYOffs = {-5956,-590,-9246,-12680,12880,-7258,-5060,-8595,-4544,-8630,-3376,-7943,8274,12106, 10109};
	private int[] levelGrid = new int[15];
	private int[] levelZoom = {16,16,16,16,16,16,16,16,16,16,16,16,16,16,16};
	private boolean[] levelRot = new boolean[15];
	private int[] levelMode = new int[15];
	private int getMapSize = levelXOffs.length;
	
	public int getGetMapSize() {
		return getMapSize;
	}

	public void saveMapConfigs() {
		levelXOffs[currentLevel] = RenderControls.getCameraXOffset();
		levelYOffs[currentLevel] = RenderControls.getCameraYOffset();
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
	
	public int getLevelXOffs(int index) {
		return levelXOffs[index];
	}

	public void setLevelXOffs(int levelXOffs, int index) {
		this.levelXOffs[index] = levelXOffs;
	}

	public int getLevelYOffs(int index) {
		return levelYOffs[index];
	}

	public void setLevelYOffs(int levelYOffs, int index) {
		this.levelYOffs[index] = levelYOffs;
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
