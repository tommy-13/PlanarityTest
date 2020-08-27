package planarityTest.dataStructures;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Graph {

	
	private int n = 0; // number of vertices
	private int m = 0; // number of edges
	
	private Map<Vertex, IncidentEdgeList> adjacencyList = new HashMap<Vertex, IncidentEdgeList>();
	
	
	/**
	 * Creates a new empty graph.
	 */
	public Graph() {}
	
	
	/**
	 * Creates a new vertex.
	 * @return the new vertex
	 */
	public Vertex createVertex() {
		Vertex v = new Vertex();
		adjacencyList.put(v, new IncidentEdgeList(v));
		n++;
		return v;
	}
	
	/**
	 * Returns the vertex with the given id (if any).
	 * @param id
	 * @return vertex with id or null
	 */
	public Vertex getVertex(int id) {		
		for (Vertex v : adjacencyList.keySet()) {
			if (v.getId() == id) {
				return v;
			}
		}
		return null;
	}
	
	/**
	 * Creates a new edge.
	 * @param source the source of the new edge
	 * @param target the target of the new edge
	 * @return the new edge
	 */
	public Edge createEdge(Vertex source, Vertex target) {
		Edge e = new Edge(source, target);
		adjacencyList.get(source).appendBack(e);
		adjacencyList.get(target).appendBack(e);
		m++;
		
		/*
		System.out.println("adjacency list when inserting");
		adjacencyList.get(source).print();
		adjacencyList.get(target).print();
		*/
		
		return e;
	}
	
	
	/**
	 * Returns the current number of vertices of the graph.
	 * @return number of vertices
	 */
	public int getNumberOfVertices() {
		return n;
	}
	/**
	 * Returns the current number of edges of the graph.
	 * @return number of edges
	 */
	public int getNumberOfEdges() {
		return m;
	}
	
	/**
	 * Returns the vertices of this graph.
	 * @return set of vertices
	 */
	public Set<Vertex> getVertices() {
		return adjacencyList.keySet();
	}
	
	/**
	 * Returns the adjacency list of this graph.
	 * @return adjacency list
	 */
	public Collection<IncidentEdgeList> getAdjacencyLists() {
		return adjacencyList.values();
	}
	
	/**
	 * Returns the edges incident to <code>v</code>. 
	 * @param v vertex
	 * @return  edges incident to v
	 */
	public IncidentEdgeList getAdjacencyList(Vertex v) {
		return adjacencyList.get(v);
	}
	
	/**
	 * Sort all incident edge lists according to the nesting depth.
	 */
	public void sortIncidencyLists() {
		for (IncidentEdgeList al : adjacencyList.values()) {
			al.sort();
		}
	}
	
	/**
	 * Update the nesting depth of all edges to calculate a planar embedding.
	 */
	public void updateNestingDepthForEmbedding() {
		for (IncidentEdgeList al : adjacencyList.values()) {
			for (Edge e : al) {
				if (e.getSource().equals(al.getSource())) {
				e.setNestingDepth(e.getNestingDepth() * getSign(e));
				//System.out.println("nesting " + e.getString() + ": " + e.getNestingDepth());
				}
			}
		}
	}
	
	/**
	 * Calculate the side of the edge <code>e</code>.
	 * @param e edge
	 * @return  side of <code>e</code>
	 */
	private int getSign(Edge e) {
		if (e.getReferenceEdge() != null) {
			//System.out.println("reference " + e.getString() + ": " + e.getReferenceEdge().getString());
			e.setSide(e.getSide() * getSign(e.getReferenceEdge()));
			e.setReferenceEdge(null);
		}
		//System.out.println("side calculated " + e.getString() + ": "  + e.getSide());
		return e.getSide();
	}
	
	
	
	/**
	 * Print the graph.
	 */
	public void print() {
		System.out.println("\n***** GRAPH *****");
		for (IncidentEdgeList al : adjacencyList.values()) {
			al.print();
		}
		System.out.println("\n\n");
	}
	
}
