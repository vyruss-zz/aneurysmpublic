package aneurysm.structures;

public class Line {

	private Vertex startV, endV;

	public Vertex getStartV() {
		return startV;
	}

	public void setStartV(Vertex startV) {
		this.startV = startV;
	}

	public Vertex getEndV() {
		return endV;
	}

	public void setEndV(Vertex endV) {
		this.endV = endV;
	}

	public Line(Vertex startV, Vertex endV) {
		super();
		this.startV = startV;
		this.endV = endV;
	}

	public Line() {
		super();
	}

	@Override
	public String toString() {
		return "Line [startV=" + startV + ", endV=" + endV + "]";
	}
}
