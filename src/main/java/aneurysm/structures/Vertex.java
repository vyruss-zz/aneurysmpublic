package aneurysm.structures;

import java.awt.Component;

public class Vertex {

	private short x, y;

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
	
	public Vertex(short x, short y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Vertex() {
		super();
	}

	@Override
	public String toString() {
		return "Vertex [x=" + x + ", y=" + y + "]";
	}
}
