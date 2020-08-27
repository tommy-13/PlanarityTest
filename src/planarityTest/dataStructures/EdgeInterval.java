package planarityTest.dataStructures;

public class EdgeInterval {
	
	private static int ID_COUNTER = 0;
	
	private int id;

	Edge lowReturnEdge;
	Edge highReturnEdge;
	
	
	/**
	 * Creates a new edge interval.
	 * @param lowReturnEdge  back edge with lowest return point in this interval
	 * @param highReturnEdge back edge with highest return point in this interval
	 */
	public EdgeInterval(Edge lowReturnEdge, Edge highReturnEdge) {
		this.id = ID_COUNTER++;
		this.lowReturnEdge = lowReturnEdge;
		this.highReturnEdge = highReturnEdge;
	}
	
	/**
	 * Returns the back edge with the lowest return point. 
	 * @return lowest back edge
	 */
	public Edge getLowEdge() {
		return this.lowReturnEdge;
	}
	
	/**
	 * Returns the back edge with the highest return point.
	 * @return highest back edge
	 */
	public Edge getHighEdge() {
		return this.highReturnEdge;
	}
	
	/**
	 * Sets the back edge with the lowest return point.
	 * @param edge
	 */
	public void setLowEdge(Edge edge) {
		this.lowReturnEdge = edge;
	}
	
	/**
	 * Sets the back edge with the highest return point.
	 * @param edge
	 */
	public void setHighEdge(Edge edge) {
		this.highReturnEdge = edge;
	}
	
	
	/**
	 * Compares this interval to <code>interval</code>.
	 * @param interval other interval
	 * @return true if and only if both intervals are equal
	 */
	public boolean isEqual(EdgeInterval interval) {
		if (interval == null) {
			return false;
		}
		else {
			return id == interval.id;
		}
	}
	
	/**
	 * Returns an empty edge interval, that is the low and high return edges are both <code>null</code>.
	 * @return empty edge interval
	 */
	public static EdgeInterval getEmpty() {
		return new EdgeInterval(null, null);
	}
	public boolean isEmpty() {
		return lowReturnEdge == null && highReturnEdge == null;
	}
	
	/**
	 * Returns a string representation of this interval.
	 * @return string representation
	 */
	public String getString() {
		return "[" + (lowReturnEdge == null ? "null" : lowReturnEdge.getString()) + ";" + (highReturnEdge == null ? "null" : highReturnEdge.getString()) + "]";
	}
	
}
