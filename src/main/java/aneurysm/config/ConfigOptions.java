package aneurysm.config;

import aneurysm.render.RenderControls;

public class ConfigOptions {
	private String location="notset";
	private String shellBinLocation="notset";
	private String mainOSBinLocation="notset";
	private String slidesBinLocation="notset";
	private final int[] levelGrid = new int[15];
	private final int[] levelZoom = {16,16,16,16,16,16,16,16,16,16,16,16,16,16,16};
	private final boolean[] levelRot = new boolean[15];
	private boolean noCD = false;
	private final int[] levelMode = new int[15];
	private final int mapSize = 15;

	public boolean isNoCD() {
		return noCD;
	}

	public void setNoCD(boolean noCD) {
		this.noCD = noCD;
	}

	public String getMainOSBinLocation() {
		return mainOSBinLocation;
	}

	public void setMainOSBinLocation(String mainOSBinLocation) {
		this.mainOSBinLocation = mainOSBinLocation;
	}

	public String getShellBinLocation() {
		return shellBinLocation;
	}

	public void setShellBinLocation(String shellBinLocation) {
		this.shellBinLocation = shellBinLocation;
	}

    public int getMapSize() {
        return mapSize;
    }

    public void saveMapConfigs() {
        if (currentLevel < 0 || currentLevel >= levelZoom.length) {
            throw new IndexOutOfBoundsException("Invalid currentLevel: " + currentLevel);
        }
        
        levelZoom[currentLevel] = RenderControls.getZoomLevel();
        levelGrid[currentLevel] = RenderControls.getGridIntensity();
        levelRot[currentLevel] = RenderControls.isRot90();
        
        int mode = 0;
        if (RenderControls.isVertsMode()) {
            mode = 0;
        } else if (RenderControls.isLinesMode()) {
            mode = 1;
        } else if (RenderControls.isThingsMode()) {
            mode = 2;
        }
        
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

    public String getSlidesBinLocation() {
        return slidesBinLocation;
    }

    public void setSlidesBinLocation(String slidesBinLocation) {
        this.slidesBinLocation = slidesBinLocation;
    }
}
