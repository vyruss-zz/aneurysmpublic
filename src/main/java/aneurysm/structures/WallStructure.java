package aneurysm.structures;

public class WallStructure {
	private short x1, x2, y1, y2;
	private int textureID;
	private short textureScale;
	private byte doorType, doorNumber, doorNSLow, doorNSHigh, doorWELow, doorWEHigh;

	

	public WallStructure(short x1, short x2, short y1, short y2, int textureID, short textureScale, byte doorType,
			byte doorNumber, byte doorNSLow, byte doorNSHigh, byte doorWELow, byte doorWEHigh) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.textureID = textureID;
		this.textureScale = textureScale;
		this.doorType = doorType;
		this.doorNumber = doorNumber;
		this.doorNSLow = doorNSLow;
		this.doorNSHigh = doorNSHigh;
		this.doorWELow = doorWELow;
		this.doorWEHigh = doorWEHigh;
	}

	public byte getDoorType() {
		return doorType;
	}

	public void setDoorType(byte doorType) {
		this.doorType = doorType;
	}

	public byte getDoorNumber() {
		return doorNumber;
	}

	public void setDoorNumber(byte doorNumber) {
		this.doorNumber = doorNumber;
	}

	public byte getDoorNSLow() {
		return doorNSLow;
	}

	public void setDoorNSLow(byte doorNSLow) {
		this.doorNSLow = doorNSLow;
	}

	public byte getDoorNSHigh() {
		return doorNSHigh;
	}

	public void setDoorNSHigh(byte doorNSHigh) {
		this.doorNSHigh = doorNSHigh;
	}

	public byte getDoorWELow() {
		return doorWELow;
	}

	public void setDoorWELow(byte doorWELow) {
		this.doorWELow = doorWELow;
	}

	public byte getDoorWEHigh() {
		return doorWEHigh;
	}

	public void setDoorWEHigh(byte doorWEHigh) {
		this.doorWEHigh = doorWEHigh;
	}

	public short getX1() {
		return x1;
	}

	public void setX1(short x1) {
		this.x1 = x1;
	}

	public short getX2() {
		return x2;
	}

	public void setX2(short x2) {
		this.x2 = x2;
	}

	public short getY1() {
		return y1;
	}

	public void setY1(short y1) {
		this.y1 = y1;
	}

	public short getY2() {
		return y2;
	}

	public void setY2(short y2) {
		this.y2 = y2;
	}

	public int getTextureID() {
		return textureID;
	}

	public void setTextureID(int textureID) {
		this.textureID = textureID;
	}

	public short getTextureScale() {
		return textureScale;
	}

	public void setTextureScale(short textureScale) {
		this.textureScale = textureScale;
	}

	public WallStructure() {
		super();
	}
}
