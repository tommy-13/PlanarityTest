package planarityTest.dataStructures;

import alg.dataStructure.Sortable;

public class Edge extends Sortable {

	private static int ID_COUNTER = 0;
	
	public static int getNumberOfEdge() {
		return ID_COUNTER;
	}
	
	private final int id;
	
	private Vertex source;
	private Vertex target;
	
	private int nestingDepth = -1;
	private int side         = 1;
	private int lowpoint     = -1;
	private int lowpoint2    = -1;
	private boolean isOriented = false;
	private boolean isTreeEdge = false;
	
	private int  stackBottom   = 0;
	private Edge lowPointEdge  = null;
	private Edge referenceEdge = null;
	
	
	/**
	 * Creates a new edge.
	 * @param source source of the new edge
	 * @param target target of the new edge
	 */
	public Edge(Vertex source, Vertex target) {
		this.id = ID_COUNTER;
		ID_COUNTER++;
		
		this.source = source;
		this.target = target;
		
		this.setKey(nestingDepth);
	}
	
	
	/**
	 * Returns the source of this edge.
	 * @return source
	 */
	public Vertex getSource() {
		return this.source;
	}
	
	/**
	 * Returns the target of this edge.
	 * @return target
	 */
	public Vertex getTarget() {
		return this.target;
	}
	
	/**
	 * Orient this edge in a DFS tree.
	 * @param source the source vertex of this edge
	 */
	public void orient(Vertex source) {
		isOriented = true;
		if (this.source.equals(source)) {
			return;
		}
		else if (this.target.equals(source)) {
			this.target = this.source;
			this.source = source;
		}
		else {
			isOriented = false;
		}
	}
	
	/**
	 * Returns if this edge is oriented.
	 * @return true if and only if this edge is oriented
	 */
	public boolean isOriented() {
		return this.isOriented;
	}
	
	/**
	 * Returns the id of this edge.
	 * @return id
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Returns the other vertex of this edge.
	 * @param v a vertex of this edge
	 * @return the other vertex of this edge
	 */
	public Vertex getOther(Vertex v) {
		return (source.equals(v)) ? target : source;
	}
	
	/**
	 * Returns the nesting depth of this edge (calculated together with the DFS tree).
	 * @return nesting depth
	 */
	public int getNestingDepth() {
		return this.nestingDepth;
	}
	
	/**
	 * Sets the nesting depth of this edge.
	 * @param value nesting depth
	 */
	public void setNestingDepth(int value) {
		this.nestingDepth = value;
		this.setKey(value);
	}
	
	/**
	 * Returns the side of this edge in the DFS tree (needed for an embedding).
	 * @return side
	 */
	public int getSide() {
		return this.side;
	}
	
	/**
	 * Sets the side of this edge in the DFS tree (needed for an embedding). 
	 * @param side side
	 */
	public void setSide(int side) {
		this.side = side;
	}
	
	/**
	 * Returns the lowpoint of this edge (lowest return point in DFS tree).
	 * @return lowpoint
	 */
	public int getLowpoint() {
		return this.lowpoint;
	}
	
	/**
	 * Sets the lowpoint of this edge (lowest return point in DFS tree).
	 * @param value lowpoint
	 */
	public void setLowpoint(int value) {
		this.lowpoint = value;
	}
	
	/**
	 * Returns the second lowest return point.
	 * @return second lowest return point 
	 */
	public int getLowpoint2() {
		return this.lowpoint2;
	}
	
	/**
	 * Sets the second lowest return point.
	 * @param value second lowest return point
	 */
	public void setLowpoint2(int value) {
		this.lowpoint2 = value;
	}
	
	/**
	 * Returns if this edge is a tree edge in a DFS tree.
	 * @return true if and only if this is a tree edge
	 */
	public boolean isTreeEdge() {
		return this.isTreeEdge;
	}
	
	/**
	 * Sets this edge as a tree/return edge.
	 * @param isTreeEdge
	 */
	public void setIsTreeEdge(boolean isTreeEdge) {
		this.isTreeEdge = isTreeEdge;
	}
	
	/**
	 * Returns the size of the stack when this edge was considered first in the testing algorithm.
	 * @return size of stack
	 */
	public int getStackBottom() {
		return this.stackBottom;
	}
	
	/**
	 * Sets the stack bottom.
	 * @param stackSize
	 */
	public void setStackBottom(int stackSize) {
		this.stackBottom = stackSize;
	}
	
	/**
	 * Returns the back edge to the low point of this edge. 
	 * @return low point edge
	 */
	public Edge getLowPointEdge() {
		return this.lowPointEdge;
	}
	
	/**
	 * References to the back edge to the low point of this edge.
	 * @param e low point edge
	 */
	public void setLowPointEdge(Edge e) {
		this.lowPointEdge = e;
	}
	
	/**
	 * Returns the reference edge for this edge (edge relative to a side; calculated in testing).
	 * @return reference edge
	 */
	public Edge getReferenceEdge() {
		return this.referenceEdge;
	}
	
	/**
	 * Sets the reference edge for this edge (edge relative to a side; calculated in testing).
	 * @param edge reference edge
	 */
	public void setReferenceEdge(Edge edge) {
		this.referenceEdge = edge;
		/*
		if (getSource().getId() == 2 || getTarget().getId() == 2) {
			System.out.println("reference " + getString() + "(" + getSide() + "): " + (getReferenceEdge() == null ? "null" : getReferenceEdge().getString() + "(" + getReferenceEdge().getSide()+ ")"));
		}*/
	}



	/**
	 * Tests if this edge is equal to another edge
	 * @param other other edge
	 * @return true if and only if both edges are equal
	 */
	public boolean isEqual(Edge other) {
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (id != other.id)
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 1999;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	
	
	/**
	 * Returns a string representation of this edge.
	 * @return string representation
	 */
	public String getString() {
		return "(" + this.source.getId() + "," + this.target.getId() + ")";
	}
	
}
