package planarityTest;

import planarityTest.dataStructures.Graph;
import planarityTest.dataStructures.Vertex;

public class Test {


	public static void main(String[] args) {
		
		System.out.print("Create graph ... ");

		/* Deactivate drawing for this. */
		/* Triangulation of an embedding (necessary for drawing the graph) */
		/* is only working if an edge appears only once on the boundary of a face. */
		//Graph graph = createStar(4);
		//Graph graph = createLattice(50, 50);
		
		
		/* It should be possible to obtain a drawing (as long as the graph is planar). */
		//Graph graph = createCompleteGraph(4);
		//Graph graph = createCompleteBipartiteGraph(2,10);
		//Graph graph = createComponents(3,4);
		//Graph graph = createK33Subdivision1();
		//Graph graph = createK33Subdivision2();
		//Graph graph = createWheel(1000);
		
		Graph graph = create3Tree(10);
		//graph.createEdge(graph.getVertex(0), graph.getVertex(12));
		
		
		System.out.println("Done.");
		
		System.out.println("Starting planarity test ... ");
		
		LRPlanarityTest planarityTest = new LRPlanarityTest(graph);
		planarityTest.testPlanar();

		
		//graph.print();
		
		
		
		System.out.println(planarityTest.isPlanar() ? "The graph is planar." : "The graph is NOT planar.");
		
		/*
		if (planarityTest.isPlanar()) {
			List<Embedding> embs = planarityTest.calculateEmbedding();
			
			int counter = 0;
			for (Embedding emb : embs) {
				new EmbeddingDrawer(emb, "Embedding Component " + (++counter));
			}
		}
		else {
			System.out.println("Graph is not planar! I will not draw it.");
		}*/
		
	}
	
	
	

	
	public static Graph createStar(int n) {
		Graph graph = new Graph();
		
		Vertex[] vertices = createVertices(graph, n);
		for (int i=1; i<n; i++) {
			graph.createEdge(vertices[0], vertices[i]);
		}
		
		return graph;
	}
	
	
	public static Graph createLattice(int width, int height) {
		Graph graph = new Graph();
		
		Vertex[][] vertices = createVertices(graph, width, height);
		
		for (int i=0; i<width; i++) {
			for (int j=0; j<height; j++) {
				if (j < height-1) {
					graph.createEdge(vertices[i][j], vertices[i][j+1]);
				}
				if (i < width-1) {
					graph.createEdge(vertices[i][j], vertices[i+1][j]);
				}
			}
		}
		
		return graph;
	}
	
	public static Graph createWheel(int n) {
		Graph graph = new Graph();
		
		Vertex[] vertices = createVertices(graph, n);
		for (int i=1; i<n; i++) {
			graph.createEdge(vertices[0], vertices[i]);
			int j = (i==n-1) ? 1 : i+1;
			graph.createEdge(vertices[i], vertices[j]);
		}
		
		return graph;
	}
	
	public static Graph create3Tree(int depth) {

		Graph graph = new Graph();
		
		Vertex v1 = graph.createVertex();
		Vertex v2 = graph.createVertex();
		Vertex v3 = graph.createVertex();
		
		graph.createEdge(v1, v2);
		graph.createEdge(v1, v3);
		graph.createEdge(v2, v3);
		addTriangle(graph, v1, v2, v3, depth);
		
		return graph;
	}
	
	public static void addTriangle(Graph graph, Vertex v1, Vertex v2, Vertex v3, int depth) {
		Vertex v = graph.createVertex();
		graph.createEdge(v, v1);
		graph.createEdge(v, v2);
		graph.createEdge(v, v3);

		depth--;
		if (depth == 0) {
			return;
		}
		
		addTriangle(graph, v1, v2, v, depth);
		addTriangle(graph, v1, v3, v, depth);
		addTriangle(graph, v2, v3, v, depth);
	}
	
	
	
	public static Graph createK33Subdivision1() {
		Graph graph = new Graph();
		
		Vertex[] vertices = createVertices(graph, 8);
		graph.createEdge(vertices[0], vertices[3]);
		graph.createEdge(vertices[0], vertices[4]);
		graph.createEdge(vertices[0], vertices[5]);
		graph.createEdge(vertices[1], vertices[3]);
		graph.createEdge(vertices[1], vertices[4]);
		graph.createEdge(vertices[1], vertices[5]);
		graph.createEdge(vertices[2], vertices[4]);
		graph.createEdge(vertices[2], vertices[5]);
		graph.createEdge(vertices[2], vertices[7]);
		graph.createEdge(vertices[3], vertices[6]);
		graph.createEdge(vertices[6], vertices[7]);
		
		return graph;
	}
	
	public static Graph createK33Subdivision2() {
		Graph graph = new Graph();
		
		Vertex[] vertices = createVertices(graph, 17);
		graph.createEdge(vertices[0], vertices[3]);
		graph.createEdge(vertices[0], vertices[4]);
		graph.createEdge(vertices[0], vertices[11]);
		graph.createEdge(vertices[1], vertices[3]);
		graph.createEdge(vertices[1], vertices[4]);
		graph.createEdge(vertices[1], vertices[6]);
		graph.createEdge(vertices[2], vertices[4]);
		graph.createEdge(vertices[2], vertices[5]);
		graph.createEdge(vertices[2], vertices[16]);
		graph.createEdge(vertices[3], vertices[15]);
		graph.createEdge(vertices[5], vertices[7]);
		graph.createEdge(vertices[5], vertices[12]);
		graph.createEdge(vertices[6], vertices[7]);
		graph.createEdge(vertices[6], vertices[8]);
		graph.createEdge(vertices[6], vertices[9]);
		graph.createEdge(vertices[6], vertices[10]);
		graph.createEdge(vertices[7], vertices[8]);
		graph.createEdge(vertices[7], vertices[9]);
		graph.createEdge(vertices[7], vertices[10]);
		graph.createEdge(vertices[8], vertices[9]);
		graph.createEdge(vertices[9], vertices[10]);
		graph.createEdge(vertices[11], vertices[12]);
		graph.createEdge(vertices[11], vertices[13]);
		graph.createEdge(vertices[11], vertices[14]);
		graph.createEdge(vertices[12], vertices[13]);
		graph.createEdge(vertices[12], vertices[14]);
		graph.createEdge(vertices[13], vertices[14]);
		graph.createEdge(vertices[15], vertices[16]);
		
		return graph;
	}
	
	public static Graph createComponents(int n1, int n2) {
		Graph graph = new Graph();

		Vertex[] vertices = createVertices(graph, n1+n2);
		
		for (int i=0; i<n1-1; i++) {
			for (int j=i+1; j<n1; j++) {				
				graph.createEdge(vertices[i], vertices[j]);
			}
		}

		for (int i=n1; i<n1+n2-1; i++) {
			for (int j=i+1; j<n1+n2; j++) {				
				graph.createEdge(vertices[i], vertices[j]);
			}
		}
		
		return graph;
	}
	
	
	public static Graph createCompleteGraph(int n) {
		Graph graph = new Graph();
		Vertex[] vertices = createVertices(graph, n);
		
		for (int i=0; i<n-1; i++) {
			for (int j=i+1; j<n; j++) {				
				graph.createEdge(vertices[i], vertices[j]);
			}
		}
		
		
		return graph;
	}

	
	public static Graph createCompleteBipartiteGraph(int a, int b) {
		Graph graph = new Graph();
		Vertex[] vertices = createVertices(graph, a+b);
		
		for (int i=0; i<a; i++) {
			for (int j=a; j<a+b; j++) {
				graph.createEdge(vertices[i], vertices[j]);
			}
		}
		
		
		return graph;
	}
	
	
	public static Vertex[] createVertices(Graph graph, int number) {
		Vertex[] vertices = new Vertex[number];
		for (int i=0; i<number; i++) {
			vertices[i] = graph.createVertex(); 
		}
		return vertices;
	}
	
	public static Vertex[][] createVertices(Graph graph, int x, int y) {
		Vertex[][] vertices = new Vertex[x][y];
		
		for (int i=0; i<x; i++) {
			for (int j=0; j<y; j++) {
				vertices[i][j] = graph.createVertex();
			}
		}
		
		return vertices;
	}
}
