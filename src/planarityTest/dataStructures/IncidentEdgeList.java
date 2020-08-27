package planarityTest.dataStructures;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import alg.countingSort.CountingSort;
import alg.dataStructure.Sortable;

public class IncidentEdgeList implements Iterable<Edge> {

	private Vertex      source;
	private ListElement firstEdge = null;
	private ListElement lastEdge = null;
	private int         degree = 0;
	
	private Map<Edge, ListElement> edgeMap = new HashMap<Edge, ListElement>();
	
	
	/**
	 * Create a new list with edges incident to <code>source</code>.
	 * @param source a vertex
	 */
	public IncidentEdgeList(Vertex source) {
		this.source = source;
	}
	
	/**
	 * Returns the vertex all edges in this list are incident with.
	 * @return incident vertex
	 */
	public Vertex getSource() {
		return source;
	}
	
	
	private void clearEdges() {
		firstEdge = null;
		lastEdge = null;
		degree = 0;
		edgeMap.clear();
	}
	
	
	/**
	 * Sort this list non-decreasingly for the nesting depth.
	 * Sorting is done in linear time by counting sort.
	 */
	public void sort() {
		// sort for nesting depth
		if (degree < 2) return;
		
		Edge[] edges = new Edge[degree];
		int i = 0;
		for (Edge e : this) {
			edges[i] = e;
			i++;
			if (e == null) {System.err.println("WARNING");}
		}
		
		Sortable[] sortedEdges = new CountingSort().sort(edges);
		
		
		clearEdges();
		for (i=0 ; i < sortedEdges.length; i++) {
			appendBack((Edge) sortedEdges[i]);
		}
	}

	
	
	/**
	 * Returns if this list is empty.
	 * @return true if and only if this list is empty
	 */
	public boolean isEmpty() {
		return degree == 0;
	}
	
	/**
	 * Appends an edge to the back of this list.
	 * @param e edge
	 */
	public void appendBack(Edge e) {
		if (e == null) {
			return;
		}
		ListElement newElement = new ListElement(e);
		edgeMap.put(e, newElement);
		if (isEmpty()) {
			firstEdge = newElement;
			lastEdge  = newElement;
		}
		else {
			lastEdge.tail = newElement;
			newElement.head = lastEdge;
			lastEdge = newElement;
		}
		degree++;
	}
	
	/**
	 * Appends an edge to the front of this list.
	 * @param e edge
	 */
	public void appendFront(Edge e) {
		if (e == null) {
			return;
		}
		ListElement newElement = new ListElement(e);
		edgeMap.put(e, newElement);
		if (isEmpty()) {
			firstEdge = newElement;
			lastEdge  = newElement;
		}
		else {
			firstEdge.head = newElement;
			newElement.tail = firstEdge;
			firstEdge = newElement;
		}
		degree++;
	}
	
	public void moveFront(Edge e) {
		/*
		if (this.source.getId() == 3) {
			System.out.println("move front 1 " + e.getString());
			print();
		}*/
		delete(e);
		appendFront(e);
		/*
		if (this.source.getId() == 3) {
			System.out.println("move front 2 " + e.getString());
			print();
		}*/
	}
	public void moveBack(Edge e) {
		/*
		if (this.source.getId() == 3) {
			System.out.println("move back 1 " + e.getString());
			print();
		}
		*/
		delete(e);
		appendBack(e);
		/*
		if (this.source.getId() == 3) {
			System.out.println("move back 2 " + e.getString());
			print();
		}*/
	}
	
	public void delete(Edge edge) {		
		ListElement le = edgeMap.get(edge);
		if (le != null) {
			ListElement prev = le.head;
			ListElement next = le.tail;
			
			if (prev == null && next == null) {
				firstEdge = null;
				lastEdge = null;
			}
			else if (prev == null) {
				next.head = null;
				firstEdge = next;
			}
			else if (next == null) {
				prev.tail = null;
				lastEdge = prev;
			}
			else {
				next.head = prev;
				prev.tail = next;
			}
			edgeMap.remove(edge);
			degree--;
		}
	}
	
	
	/**
	 * Moves an edge of this list before another edge.
	 * @param edge      edge to move
	 * @param fixedEdge reference edge
	 */
	public void moveBefore(Edge edge, Edge fixedEdge) {
		/*
		if (this.source.getId() == 3) {
			System.out.println("move before 1 " + edge.getString() + " " + fixedEdge.getString());
			print();
		}
		*/
		
		if (edge.isEqual(fixedEdge)) return;
		
		ListElement leEdge      = edgeMap.get(edge);
		ListElement leFixedEdge = edgeMap.get(fixedEdge);
		
		if (leEdge == null || leFixedEdge == null) {
			return;
		}
		
		// delete edge from current pos
		ListElement beforeEdge = leEdge.head;
		ListElement afterEdge  = leEdge.tail;
		if (beforeEdge != null) {
			beforeEdge.tail = afterEdge;
		}
		else {
			// edge is first element of the list
			firstEdge = afterEdge;
		}
		if (afterEdge != null) {
			afterEdge.head = beforeEdge;
		}
		else {
			// edge is last element of list
			lastEdge = beforeEdge;
		}
		
		// insert edge to new position
		ListElement beforeFixedEdge = leFixedEdge.head;
		leFixedEdge.head = leEdge;
		leEdge.tail      = leFixedEdge;
		if (beforeFixedEdge != null) {
			beforeFixedEdge.tail = leEdge;
			leEdge.head       = beforeFixedEdge;
		}
		else {
			// fixed edge is first element of chain
			firstEdge = leEdge;
		}
		
		/*
		if (this.source.getId() == 3) {
			System.out.println("move before 2 " + edge.getString() + " " + fixedEdge.getString());
			print();
		}
		*/
	}
	

	/**
	 * Moves an edge of this list behind another edge.
	 * @param edge      edge to move
	 * @param fixedEdge reference edge
	 */
	public void moveAfter(Edge edge, Edge fixedEdge) {
		/*
		if (this.source.getId() == 3) {
			System.out.println("move after 1 " + edge.getString() + " " + fixedEdge.getString());
			print();
		}*/
		
		if (edge.isEqual(fixedEdge)) return;
		
		ListElement leEdge      = edgeMap.get(edge);
		ListElement leFixedEdge = edgeMap.get(fixedEdge);
		
		if (leEdge == null || leFixedEdge == null) {
			return;
		}
		
		// delete edge from current pos
		ListElement beforeEdge = leEdge.head;
		ListElement afterEdge  = leEdge.tail;
		if (beforeEdge != null) {
			beforeEdge.tail = afterEdge;
		}
		else {
			// edge is first element of the list
			firstEdge = afterEdge;
		}
		if (afterEdge != null) {
			afterEdge.head = beforeEdge;
		}
		else {
			// edge is last element of list
			lastEdge = beforeEdge;
		}
		
		// insert edge to new position
		ListElement afterFixedEdge = leFixedEdge.tail;
		leFixedEdge.tail = leEdge;
		leEdge.head      = leFixedEdge;
		if (afterFixedEdge != null) {
			afterFixedEdge.head = leEdge;
			leEdge.tail      = afterFixedEdge;
		}
		else {
			// fixed edge is last element of chain
			lastEdge = leEdge;
		}
		
		/*
		if (this.source.getId() == 3) {
			System.out.println("move after 2 " + edge.getString() + " " + fixedEdge.getString());
			print();
		}*/
	}

	
	
	/**
	 * Returns the first edge in this list.
	 * @return first edge
	 */
	public Edge getFirst() {
		return this.firstEdge == null ? null : this.firstEdge.edge;
	}
	/**
	 * Returns the last edge in this list.
	 * @return last edge
	 */
	public Edge getLast() {
		return this.lastEdge == null ? null : this.lastEdge.edge;
	}
	
	
	
	/**
	 * Class to save edges in a list.
	 * @author tommy
	 *
	 */
	private class ListElement {	
		private final Edge edge;
		ListElement tail;
		ListElement head;
		
		ListElement(Edge edge) {
			this.edge = edge;
			this.tail = null;
			this.head = null;
		}
		
	}






	@Override
	public Iterator<Edge> iterator() {
		Iterator<Edge> iterator = new Iterator<Edge>() {
			
			private ListElement le = firstEdge;

			@Override
			public boolean hasNext() {
				return le != null;
			}

			@Override
			public Edge next() {
				ListElement current = le;
				le = le.tail;
				return current.edge;
			}
		};

		return iterator;
	}
	
	
	
	/**
	 * Prints this list.
	 */
	public void print() {
		ListElement el = firstEdge;
		
		String str = "Vertex: " + source.getLabel() + "\n";
		while (el != null) {
			str += "[(" + el.edge.getSource().getId() + "," +  el.edge.getTarget().getId() + ")|";			
			str += (el.edge.isTreeEdge() ? "tree" : "return");
			str += "|nesting:" + el.edge.getNestingDepth();
			str += "|lp:" + el.edge.getLowpoint() + "|lp2:" +el.edge.getLowpoint2();
			str += "|side:" + el.edge.getSide();
			str += "\n";
			el = el.tail;
		}
		System.out.println(str);
	}

	public IncidentEdgeList copy() {
		IncidentEdgeList copy = new IncidentEdgeList(source);
		for (Edge e : this) {
			copy.appendBack(e);
		}
		return copy;
	}
}
