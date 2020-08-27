package planarityTest;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import embedding.Embedding;
import embedding.EmbeddingEdge;
import embedding.EmbeddingFace;
import embedding.EmbeddingVertex;
import planarityTest.dataStructures.Edge;
import planarityTest.dataStructures.Graph;
import planarityTest.dataStructures.IncidentEdgeList;
import planarityTest.dataStructures.Vertex;

public class LREmbedder {

	private Graph     graph;
	private Embedding emb;
	

	private EmbeddingFace outerFace;
	
	private List<Vertex> vertices = new LinkedList<Vertex>();
	private List<EmbeddingEdge> backEdges = new LinkedList<EmbeddingEdge>();
	private Map<Vertex,EmbeddingVertex> vMap = new HashMap<Vertex,EmbeddingVertex>();
	
	private Map<Vertex, Map<Edge, EmbeddingEdge>> eOutMap = new HashMap<Vertex, Map<Edge, EmbeddingEdge>>();
	private Map<Vertex, Map<Edge, EmbeddingEdge>> eInMap = new HashMap<Vertex, Map<Edge, EmbeddingEdge>>();

	
	
	public LREmbedder(Graph graph) {
		this.graph = graph;
	}

	
	
	public Embedding getPlanarEmbedding(Vertex root) {
		
		emb = new Embedding();
		outerFace = emb.createFace();
		
		// determine vertices of this component
		vertices.add(root);
		dfsVertices(root);
		
		// create an embedding vertex for every vertex of the component
		for (Vertex v : vertices) {
			EmbeddingVertex ev = emb.createVertex(v.getLabel());
			vMap.put(v, ev);
		}

		// init hash maps
		for (Vertex v : vertices) {
			eOutMap.put(v, new HashMap<Edge, EmbeddingEdge>());
			eInMap.put(v, new HashMap<Edge, EmbeddingEdge>());
		}
		
		
		// create edges
		for (Vertex v : vertices) {
			EmbeddingVertex ev = vMap.get(v);
			
			for (Edge e : graph.getAdjacencyList(v)) {
				Vertex w = e.getOther(v);
				
				boolean isOutEdge = (e.getSource().getId() == v.getId());
				EmbeddingEdge ee = emb.createEdge(vMap.get(v), vMap.get(w));
				ee.setFace(outerFace);
				eOutMap.get(v).put(e, ee);
				eInMap.get(w).put(e, ee);
				ev.setOutEdge(ee);
				
				if (isOutEdge && !e.isTreeEdge()) {
					// the edge is a return edge
					// create a face for the return edge
					EmbeddingFace ef = emb.createFace();
					ee.setFace(ef);
					ef.setIncidentEdge(ee);
					backEdges.add(ee);
				}
			}
		}
		
		
		// connect twin edges
		for (Vertex v : vertices) {	
			for (Edge e : graph.getAdjacencyList(v)) {
				EmbeddingEdge ee = eOutMap.get(v).get(e);
				EmbeddingEdge eeTwin = eInMap.get(v).get(e);
				ee.setTwin(eeTwin);
				eeTwin.setTwin(ee);
			}
		}
		
		
		// order edges
		for (Vertex v : vertices) {
			
			//System.out.println("\n vertex: " + v.getId());
			
			EmbeddingEdge eFirstOutEdge = null;
			EmbeddingEdge ePrevInEdge = null;
			
			for (Edge e : graph.getAdjacencyList(v)) {
				
				//System.out.print(e.getString() + " ");
				
				if (eFirstOutEdge == null) {
					eFirstOutEdge = eOutMap.get(v).get(e);
				}
				
				if (ePrevInEdge != null) {
					EmbeddingEdge eOutEdge = eOutMap.get(v).get(e);
					ePrevInEdge.setNext(eOutEdge);
					eOutEdge.setPrevious(ePrevInEdge);
				}
			
				ePrevInEdge = eInMap.get(v).get(e);
			}
			
			if (eFirstOutEdge != null) {
				
				//System.out.print("if case: (" + eFirstOutEdge.getSource().getName() + "," + eFirstOutEdge.getTarget().getName() + ")");
				//System.out.print(" prev in: (" + ePrevInEdge.getSource().getName() + "," + ePrevInEdge.getTarget().getName() + ")");
				
				ePrevInEdge.setNext(eFirstOutEdge);
				eFirstOutEdge.setPrevious(ePrevInEdge);
			}
			else {
				System.err.println("Something went wrong.");
			}
		}
		
		//System.out.println("back edges: " + backEdges.size());
		//int counter = 0;
		
		// propagate faces
		for (EmbeddingEdge ee : backEdges) {
			EmbeddingFace face = ee.getFace();
			int startId = ee.getId();
			
			/*
			if (ee.getNext() == null) {
				System.out.println("\n(" + ee.getSource().getName() + "," + ee.getTarget().getName() + ")");
			}
			*/
			
			//counter++;
			//String str = "counter: " + counter + " " + ee.toString() + " - ";
			
			while (ee.getNext().getId() != startId) {
				ee = ee.getNext();
				ee.setFace(face);
				
				//str += ee.toString() + "|f:" + face.getId() + " ";
			}
			
			//System.out.println(str);
		}
		
		// set outer face
		for (EmbeddingEdge ee : backEdges) {
			EmbeddingEdge eeTwin = ee.getTwin();
			if (eeTwin.getFace().getId() == outerFace.getId()) {
				outerFace.setIncidentEdge(eeTwin);
			}
		}
		
		if (backEdges.size() == 0) {
			
			// get one edge
			for (Vertex v : vertices) {
				EmbeddingVertex ev = vMap.get(v);
				EmbeddingEdge ee = ev.getOutEdge();
				if (ee != null) {
					outerFace.setIncidentEdge(ee);
				}
			}
		}
		
		return emb;
	}
	
	
	private void dfsVertices(Vertex v) {
		IncidentEdgeList adjList = graph.getAdjacencyList(v);
		for (Edge edge : adjList) {
			if (edge.isTreeEdge() && edge.getSource().equals(v)) {
				Vertex w = edge.getTarget();
				vertices.add(w);
				dfsVertices(w);
			}
		}
	}
	
}
