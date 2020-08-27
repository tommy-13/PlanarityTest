package planarityTest;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import drawing.DrawingPositioner;
import embedding.Embedding;
import planarityTest.dataStructures.ConflictPair;
import planarityTest.dataStructures.Edge;
import planarityTest.dataStructures.EdgeInterval;
import planarityTest.dataStructures.Graph;
import planarityTest.dataStructures.IncidentEdgeList;
import planarityTest.dataStructures.Vertex;

public class LRPlanarityTest {
	
	private Graph graph;
	private boolean isPlanar = true;
	private boolean wasTestRun = false;
	
	private List<Vertex> roots = new LinkedList<Vertex>();
	
	
	
	/**
	 * Creates a new left-right planarity test.
	 * The graph should be simple, that is, it should neither contain self-loops
	 * nor more than one edge between each pair of vertices. 
	 * @param graph
	 */
	public LRPlanarityTest(Graph graph) {
		this.graph = graph;
	}
	
	/**
	 * Returns the number of components of this graph.
	 * @return
	 */
	public int getNumberOfComponents() {
		if (!wasTestRun) {
			System.err.println("Warning! Run planarity test first. Until then this number is not valid.");
		}
		return roots.size();
	}
	
	/**
	 * Returns if this graph is planar.
	 * @return true if and only if this graph is planar
	 */
	public boolean isPlanar() {
		if (!wasTestRun) {
			System.err.println("Warning! Run planarity test first. Until then this value is not valid.");
		}
		return isPlanar;
	}
	
	
	
	/**
	 * Tests the graph for planarity.
	 */
	public void testPlanar() {

		if (wasTestRun) {
			System.err.println("Test has already been executed!");
			return;
		}
		else {
			wasTestRun = true;
		}
		
		if (graph.getNumberOfVertices() <= 2) {
			// a graph with at most 2 vertices is planar
			isPlanar = true;
			return;
		}
		
		if (graph.getNumberOfVertices() > 2 && graph.getNumberOfEdges() > 3*graph.getNumberOfVertices()-6) {
			// a planar graph can have at most (3 * #vertices - 6) edges
			isPlanar = false;
			return;
		}
		
		
		// orientation
		for (Vertex s : graph.getVertices()) {
			if (s.getHeight() == Vertex.UNDEFINED_HEIGHT) {
				s.setHeight(0);
				roots.add(s);
				doDFS1(s);
			}
		}


		/*
		System.out.print("\nRoots: ");
		for (Vertex s : roots) {
			System.out.print(s.getId() + " ");
		}
		System.out.println();
		print();
		*/
		
		// testing
		graph.sortIncidencyLists();
		
		//System.out.println("First sorting done");
		//print();
		
		for (Vertex s : roots) {
			boolean result = doDFS2(s);
			if (!result) {
				isPlanar = false;
				return;
			}
		}
	}
	
	
	
	
	/**
	 * Calculates an embedding for every component of the graph.
	 * This operation is only possible if the the graph is planar.
	 * 
	 * @return array
	 */
	public List<Embedding> calculateEmbedding() {

		if (!wasTestRun) {
			System.err.println("Warning! Run planarity test first. Execution aborted.");
			return null;
		}

		if (!isPlanar) {
			System.err.println("Warning! I cannot calculate an embedding for a non-planar graph. Execution aborted.");
			return null;
		}
		
		//graph.print();
				
		graph.updateNestingDepthForEmbedding();
		graph.sortIncidencyLists();
		//graph.print();
		for (Vertex s : roots) {
			doDFS3(s);
		}
		
		//graph.print();
		
		if (getNumberOfComponents() == 0) {
			System.err.println("There is nothing to draw.");
			return null;
		}
		
		List<Embedding> embeddings = new LinkedList<Embedding>();
		for (int i=0; i<roots.size(); i++) {
			LREmbedder embedder = new LREmbedder(graph);
			embeddings.add(embedder.getPlanarEmbedding(roots.get(i)));
		}
		
		
		// **************************************************** //
		/*
		for (Embedding emb : embeddings) {
			for (EmbeddingEdge e : emb.getEdges().values()) {
				if (e.getPrevious() == null) {
					System.out.println("Previous edge is null: " + e.toString());
				}
				if (e.getNext() == null) {
					System.out.println("Next edge is null: " + e.toString());
				}
			}
			for (EmbeddingFace f : emb.getFaces().values()) {
				if (f.getIncidentEdge() == null) {
					System.out.println("Face has no edge");
				}
			}
		}
		*/
		
		
		
		for (Embedding emb : embeddings) {
			DrawingPositioner.calcPos(emb);
		}
		
		return embeddings;
	}

	
	
	/**
	 * Orientation of the graph.
	 * @param v the root of the current subtree
	 */
	private void doDFS1(Vertex v) {
		Edge parentEdge = v.getParentEdge();
		
		for (Edge edge : graph.getAdjacencyList(v)) {
			if (edge.isOriented()) {
				continue;
			}
			
			edge.orient(v);
			edge.setLowpoint(v.getHeight());
			edge.setLowpoint2(v.getHeight());
			
			Vertex w = edge.getTarget();
			if (w.getHeight() == Vertex.UNDEFINED_HEIGHT) {
				// we have a tree edge
				edge.setIsTreeEdge(true);
				w.setParentEdge(edge);
				w.setHeight(v.getHeight() + 1);
				doDFS1(w);
			}
			else {
				// we have a back edge
				edge.setIsTreeEdge(false);
				edge.setLowpoint(w.getHeight());
			}
			
			
			// determine nesting depth
			edge.setNestingDepth(2 * edge.getLowpoint());
			if (edge.getLowpoint2() < v.getHeight()) {
				// chordal
				edge.setNestingDepth(edge.getNestingDepth() + 1);
			}
			
			// update lowpoints of parent edge
			if (parentEdge != null) {
				if (edge.getLowpoint() < parentEdge.getLowpoint()) {
					parentEdge.setLowpoint2(Math.min(parentEdge.getLowpoint(), edge.getLowpoint2()));
					parentEdge.setLowpoint(edge.getLowpoint());
				}
				else if (edge.getLowpoint() > parentEdge.getLowpoint()) {
					parentEdge.setLowpoint2(Math.min(parentEdge.getLowpoint2(), edge.getLowpoint()));
				}
				else {
					// lowpoints of edge and parent edge are the same
					parentEdge.setLowpoint2(Math.min(parentEdge.getLowpoint2(), edge.getLowpoint2()));
				}
			}
			
		}
	}
	
	
	


	private Stack<ConflictPair> stack = new Stack<ConflictPair>();
	/**
	 * Testing for planarity.
	 * @param v Root of the current subtree
	 * @return true if and only if the current subtree has a planar embedding
	 */
	private boolean doDFS2(Vertex v) {
		Edge parentEdge = v.getParentEdge();

		IncidentEdgeList adjList = graph.getAdjacencyList(v);
		Edge firstOutgoingEdge = null;
		
		for (Edge edge : adjList) {
			// edges should be ordered by nesting depth
				
			if (!edge.getSource().equals(v)) {
				// we only want to consider outgoing edges
				continue;
			}
			
			if (firstOutgoingEdge == null) {
				// remember first outgoing edge
				firstOutgoingEdge = edge;
			}
			
			
			edge.setStackBottom(stack.size());
			
			if (edge.isTreeEdge()) {
				boolean result = doDFS2(edge.getTarget());
				if (!result) {
					return false;
				}
			}
			else {
				// back edge
				edge.setLowPointEdge(edge);
				stack.push(new ConflictPair(EdgeInterval.getEmpty(), new EdgeInterval(edge, edge))); // put back edge first to the right
			}
			
			// integrate new return edges
			if (edge.getLowpoint() < v.getHeight()) {
				// edge has return edge, that is, there must be a parent edge
				if (edge.isEqual(firstOutgoingEdge)) {
					parentEdge.setLowPointEdge(firstOutgoingEdge.getLowPointEdge());
				}
				else {
					// add constraints of edge
					boolean result = addConstraints(edge, parentEdge);
					if (!result) {
						return false;
					}
				}
			}	
		}
		
		
		// remove back edges returning to parent
		if (parentEdge != null) {
			// v is not the root
			Vertex u = parentEdge.getSource();
			
			// trim back edges ending at parent u
		    trimBack(u);
			
			// side of parentEdge is side of a highest return edge
			if (parentEdge.getLowpoint() < u.getHeight()) {// && !stack.isEmpty()) {
				// parentEdge has return edge
				//System.out.println("parentEdge: " + edgeToString(parentEdge) + " - lp: " + parentEdge.getLowpoint() + " - h: " + u.getHeight());
				ConflictPair cp = stack.peek();
				Edge edgeHighLeft = cp.getLeftInterval().getHighEdge();
				Edge edgeHighRight = cp.getRightInterval().getHighEdge();
				
				if (edgeHighLeft != null && (edgeHighRight == null || edgeHighLeft.getLowpoint() > edgeHighRight.getLowpoint())) {
					parentEdge.setReferenceEdge(edgeHighLeft);
				}
				else {
					parentEdge.setReferenceEdge(edgeHighRight);
				}
			}
		}
		
		
		return true;
	}
	
	
	/**
	 * Add constraints for <code>edge</code>.
	 * @param edge       the current edge
	 * @param parentEdge the parent edge of the current edge
	 * @return           true if no contradiction to planarity was found
	 */
	private boolean addConstraints(Edge edge, Edge parentEdge) {
		ConflictPair p = ConflictPair.getEmptyPair();
		
		/*
		String str = "OUT LOOP|edge: " + edge.getString() + " - stack bottom: " + edge.getStackBottom();
		str += " --- stack top: " + (stack.isEmpty() ? "empty" : stack.peek().getString());
		System.out.println(str);
		*/
		
		while (stack.size() > edge.getStackBottom()) { // stack.peek() != edge.getStackBottom()) {
			ConflictPair q = stack.pop();
			
			if (!q.getLeftInterval().isEmpty()) { // != null) {
				q.swapIntervals();
			}
			if (!q.getLeftInterval().isEmpty()) { // != null) {
				// none of the intervals in the conflict pair are empty -> conflict to planarity
				return false;
			}
			else {
				// left interval is empty and right should not be empty
				if (q.getRightInterval().getLowEdge().getLowpoint() > parentEdge.getLowpoint()) {
					// merge intervals
					if (p.getRightInterval().isEmpty()) { // == null) {
						// topmost interval
						p.setRightInterval(q.getRightInterval());
					}
					else {
						p.getRightInterval().getLowEdge().setReferenceEdge(q.getRightInterval().getHighEdge());
					}
					p.getRightInterval().setLowEdge(q.getRightInterval().getLowEdge());
				}
				else {
					// align
					q.getRightInterval().getLowEdge().setReferenceEdge(parentEdge.getLowPointEdge());
				}
			}
			

			/*
			str = "IN LOOP|edge: " + edge.getString() + " - stack bottom: " + edge.getStackBottom();
			str += " --- stack top: " + (stack.isEmpty() ? "empty" : stack.peek().getString());
			str += " --- p: " + p.getString();
			System.out.println(str);
			*/
		}
		
		
		// merge conflicting return edges of already considered outgoing edges of current vertex
		// into left interval of conflict pair
		while (!stack.isEmpty() && (conflicting(stack.peek().getLeftInterval(), edge) || conflicting(stack.peek().getRightInterval(), edge))) {
			ConflictPair q = stack.pop();
			
			if (conflicting(q.getRightInterval(), edge)) {
				q.swapIntervals();
			}
			if (conflicting(q.getRightInterval(), edge)) {
				return false;
			}
			else {
				// merge interval below lowpoint of edge into right side of p
				if (p.getRightInterval().getLowEdge() != null) {
					p.getRightInterval().getLowEdge().setReferenceEdge(q.getRightInterval().getHighEdge());
				}
				if (q.getRightInterval().getLowEdge() != null) {
					p.getRightInterval().setLowEdge(q.getRightInterval().getLowEdge());
				}
			}
			
			if (p.getLeftInterval().isEmpty()) {
				// topmost interval
				p.getLeftInterval().setHighEdge(q.getLeftInterval().getHighEdge());
			}
			else { // p left interval is not empty
				p.getLeftInterval().getLowEdge().setReferenceEdge(q.getLeftInterval().getHighEdge());
			}
			p.getLeftInterval().setLowEdge(q.getLeftInterval().getLowEdge());
			
			/*
			str = "IN LOOP2|edge: " + edge.getString() + " - stack bottom: " + edge.getStackBottom();
			str += " --- stack top: " + (stack.isEmpty() ? "empty" : stack.peek().getString());
			str += " --- p: " + p.getString();
			System.out.println(str);
			*/
		}
		
		if (!p.isEmpty()) {
			stack.push(p);
		}
		
		return true;
	}
	
	/**
	 * Decides if an edge interval is in conflict with an edge.
	 * @param interval edge interval
	 * @param e        edge
	 * @return         true if and only the interval is in conflict with the edge
	 */
	private boolean conflicting(EdgeInterval interval, Edge e) {
		return !interval.isEmpty() && interval.getHighEdge().getLowpoint() > e.getLowpoint();
	}
	
	
	/**
	 * Remove return edges that end above vertex u.
	 * @param u current vertex
	 */
	private void trimBack(Vertex u) {
		// drop entire conflict pairs
		while (!stack.isEmpty() && lowest(stack.peek()) == u.getHeight()) {
			ConflictPair p = stack.pop();
			if (p.getLeftInterval().getLowEdge() != null) {
				p.getLeftInterval().getLowEdge().setSide(-1);
			}
		}
		
		if (!stack.isEmpty()) {
			// one more conflict pair to consider
			ConflictPair p = stack.pop();
			
			// trim left interval
			while (p.getLeftInterval().getHighEdge() != null && p.getLeftInterval().getHighEdge().getTarget().equals(u)) {
				p.getLeftInterval().setHighEdge(p.getLeftInterval().getHighEdge().getReferenceEdge());
			}
			if (p.getLeftInterval().getHighEdge() == null && p.getLeftInterval().getLowEdge() != null) {
				// just emptied
				p.getLeftInterval().getLowEdge().setReferenceEdge(p.getRightInterval().getLowEdge());
				p.getLeftInterval().getLowEdge().setSide(-1);
				p.getLeftInterval().setLowEdge(null);
			}
			
			// trim right interval
			while (p.getRightInterval().getHighEdge() != null && p.getRightInterval().getHighEdge().getTarget().equals(u)) {
				p.getRightInterval().setHighEdge(p.getRightInterval().getHighEdge().getReferenceEdge());
			}
			if (p.getRightInterval().getHighEdge() == null && p.getRightInterval().getLowEdge() != null) {
				// just emptied
				p.getRightInterval().getLowEdge().setReferenceEdge(p.getLeftInterval().getLowEdge());
				p.getRightInterval().getLowEdge().setSide(-1);
				p.getRightInterval().setLowEdge(null);
			}
			
			stack.push(p);
		}
	}
	
	/**
	 * Returns the lowest return point in this conflict pair.
	 * @param cp conflict pair
	 * @return   the lowest return point
	 */
	private int lowest(ConflictPair cp) {
		if (cp.getLeftInterval().isEmpty()) {
			return cp.getRightInterval().getLowEdge().getLowpoint();
		}
		if (cp.getRightInterval().isEmpty()) {
			return cp.getLeftInterval().getLowEdge().getLowpoint();
		}
		return Math.min(cp.getLeftInterval().getLowEdge().getLowpoint(), cp.getRightInterval().getLowEdge().getLowpoint());
	}
	
	
	
	
	/**
	 * Embedding of the graph.
	 * @param v the root of the current subtree
	 */
	private void doDFS3(Vertex v) {

		IncidentEdgeList adjList = graph.getAdjacencyList(v);
		IncidentEdgeList adjListCopy = adjList.copy();
		
		for (Edge edge : adjListCopy) {
			// edges should be ordered by nesting depth
				
			if (!edge.getSource().equals(v)) {
				// we only want to consider outgoing edges
				continue;
			}
		
			Vertex w = edge.getTarget();
			
			if (edge.isTreeEdge()) {
				// make edge first edge in adjacency list of w
				graph.getAdjacencyList(w).moveFront(edge);
				
				v.setLeftRef(edge);
				v.setRightRef(edge);
				doDFS3(w);
			}
			else {
				// back edge
				if (edge.getSide() == 1) {
					//System.out.println("vertex: " + w.getId() + " - edge: " + edge.getString() + " - rightref: " + w.getRightRef().getString());
					// place edge directly after rightRef of w in adjacency list of w
					graph.getAdjacencyList(w).moveAfter(edge, w.getRightRef());
				}
				else {
					//System.out.println("vertex: " + w.getId() + " - edge: " + edge.getString() + " - leftref: " + w.getLeftRef().getString());
					// place edge directly before leftRef of w in adjacency list of w
					graph.getAdjacencyList(w).moveBefore(edge, w.getLeftRef());
					w.setLeftRef(edge);
				}
			}
		}
	}
}
