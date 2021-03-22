package aneurysm.structures;

public class MapObjectStructure {
	private int thingID;
	private short x, y;
	private byte offset_major, offset_minor;

	public MapObjectStructure(int thingID, short x, short y, byte offset_major, byte offset_minor) {
		super();
		this.thingID = thingID;
		this.x = x;
		this.y = y;
		this.offset_major = offset_major;
		this.offset_minor = offset_minor;
	}

	@Override
	public String toString() {
		return "MapObjectStructure [thingID=" + thingID + ", x=" + x + ", y=" + y + ", offset_major=" + offset_major
				+ ", offset_minor=" + offset_minor + "]";
	}

	public byte getOffset_major() {
		return offset_major;
	}

	public void setOffset_major(byte offset_major) {
		this.offset_major = offset_major;
	}

	public byte getOffset_minor() {
		return offset_minor;
	}

	public void setOffset_minor(byte offset_minor) {
		this.offset_minor = offset_minor;
	}

	public int getThingID() {
		return thingID;
	}

	public void setThingID(int thingID) {
		this.thingID = thingID;
	}

	public short getX() {
		return x;
	}

	public void setX(short x) {
		this.x = x;
	}

	public short getY() {
		return y;
	}

	public void setY(short y) {
		this.y = y;
	}

	public MapObjectStructure() {
		super();
	}
}
