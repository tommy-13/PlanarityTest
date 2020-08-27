package planarityTest.dataStructures;

public class Vertex {
	
	public static final int UNDEFINED_HEIGHT = -1;
	
	private static int ID_COUNTER = 0;
	
	private int    id;
	private String label;
	
	private int height = UNDEFINED_HEIGHT;
	private Edge parentEdge = null;
	
	private Edge leftRef = null;
	private Edge rightRef = null;
	
	
	/**
	 * Creates a new vertex.
	 */
	public Vertex() {
		this("" + Vertex.ID_COUNTER);
	}
	
	/**
	 * Creates a new vertex.
	 * @param label the label of this vertex
	 */
	public Vertex(String label) {
		setLabel(label);
		this.id = Vertex.ID_COUNTER;
		Vertex.ID_COUNTER++;
	}
	
	/**
	 * Sets the label of this vertex.
	 * @param label new label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * Returns the label of this vertex.
	 * @return label
	 */
	public String getLabel() {
		return this.label;
	}
	
	/**
	 * Returns the id of this vertex.
	 * @return id
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Returns the height in a DFS tree of this vertice.
	 * @return DFS height
	 */
	public int getHeight() {
		return this.height;
	}
	
	/**
	 * Sets the height of this tree in a DFS tree.
	 * @param value DFS height
	 */
	public void setHeight(int value) {
		this.height = value;
	}
	
	/**
	 * Returns the parent edge of this vertex in a DFS tree.
	 * @return parent edge
	 */
	public Edge getParentEdge() {
		return this.parentEdge;
	}
	
	/**
	 * Sets the parent edge of this vertex in a DFS tree.
	 * @param e
	 */
	public void setParentEdge(Edge e) {
		this.parentEdge = e;
	}
	
	/**
	 * Sets the leftmost back edge from current DFS subtree (embedding phase).
	 * @param e leftmost back edge
	 */
	public void setLeftRef(Edge e) {
		this.leftRef = e;
	}
	
	/**
	 * Returns the leftmost back edge from current DFS subtree (embedding phase).
	 * @return leftmost back edge
	 */
	public Edge getLeftRef() {
		return this.leftRef;
	}
	
	/**
	 * Sets the rightmost back edge from current DFS subtree (embedding phase).
	 * @param e rightmost back edge
	 */
	public void setRightRef(Edge e) {
		this.rightRef = e;
	}
	
	/**
	 * Returns the rightmost back edge from current DFS subtree (embedding phase).
	 * @return rightmost back edge
	 */
	public Edge getRightRef() {
		return this.rightRef;
	}
	
	
	
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
	
}
