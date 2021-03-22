package aneurysm.ui;

public class SpriteSet {

	private Integer objectNumber;
	private String stringName;
	private int offset, width, height;

	public int getOffset() {
		return offset;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getObjectNumber() {
		return objectNumber;
	}

	public void setObjectNumber(int objectNumber) {
		this.objectNumber = objectNumber;
	}

	public String getStringName() {
		return stringName;
	}

	public void setStringName(String stringName) {
		this.stringName = stringName;
	}

	public SpriteSet(int objectNumber, int offset, int width, int height) {
		this.width = width;
		this.height = height;
		this.offset = offset;
		this.objectNumber = objectNumber;
		this.stringName = "00" + Integer.toHexString(objectNumber).toUpperCase() + ".png";
	}

	@Override
	public String toString() {
		return "File Path: " + stringName;
	}
}
