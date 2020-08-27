package drawing;

import java.util.HashMap;
import java.util.Map.Entry;

import embedding.Constant;
import embedding.Coordinate;
import embedding.Embedding;
import embedding.EmbeddingEdge;
import embedding.EmbeddingVertex;
import embedding.EnumColor;

/**
 * Class to position the vertices of all drawings in a certain folder.
 * @author tommy
 *
 */
public class DrawingPositioner {
	
	/**
	 * Calculates the positions for the specified embedding.
	 * Also calculates additional information for showing the embedding on the screen.
	 * @param embedding		embedding
	 * @param newVertexName	name of the lastly inserted vertex
	 * @return				the drawing
	 */
	public static void calcPos(Embedding embedding) {
		
		Embedding triangulation       = Triangulator.triangulate(embedding, -1);
		CanonicalOrder canonicalOrder = new CanonicalOrder(triangulation);
		canonicalOrder.calculate();
		
		HashMap<Integer, Coordinate> coordinates = TriangulationPositioner.calculatePositions(
				triangulation,
				canonicalOrder.getVertexOrder(),
				canonicalOrder.getChildren());
		
		
		// color the nodes
		for (EmbeddingVertex v : embedding.getVertices().values()) {
			if (v.isCrossing()) {
				v.setColor(EnumColor.CROSSING);
			}
			else if (v.getName().startsWith(Embedding.SET2_LETTER)) {
				v.setColor(EnumColor.SET2);
			}
			else {
				v.setColor(EnumColor.SET1);
			}
		}
		
		
		// save the positions of the nodes
		for (Entry<Integer, Coordinate> entry : coordinates.entrySet()) {
			int id = entry.getKey();
			Coordinate coord = entry.getValue();

			if (embedding.hasVertex(id)) {
				EmbeddingVertex v = embedding.getVertex(id);
				v.setX(coord.getX());
				v.setY(coord.getY());
			}
		}
		
		calcStrechedPos(embedding);
		
		// calculate edges to draw
		for (EmbeddingEdge e : embedding.getEdges().values()) {			   
			if (!e.isToDraw() && !e.getTwin().isToDraw()) {	
				if (!embedding.isWholeEdge(e)) {
					while (e.getEdgeAfterCrossing() != null) {
						e = e.getEdgeAfterCrossing();
					}
					e.setToDraw(true);
					while (e.getEdgeBeforeCrossing() != null) {
						e = e.getEdgeBeforeCrossing();
						e.setToDraw(true);
					}
				}
				else {
					e.setToDraw(true);
				}
			}
		}
		
		// calculate edge labels
		for (Entry<Integer, EmbeddingEdge> entry : embedding.getEdges().entrySet()) {	
			int  eId = entry.getKey();
			EmbeddingEdge e   = entry.getValue();
			
			if (!e.isToDraw()) {
				continue;
			}

			int realSourceId = embedding.getRealSource(eId);
			int realTargetId = embedding.getRealTarget(eId);
			EmbeddingVertex v = embedding.getVertex(realSourceId);
			if (v.getName().startsWith(Embedding.SET1_LETTER)) {
				v = embedding.getVertex(realTargetId);
			}
			e.setName(v.getName());
			e.setMappedName(v.getMappedName());
		}
	}
	
	
	
		
	/**
	 * Calculates the stretched Coordinates for the specified embedding.
	 * @param emb	an embedding
	 */
	public static void calcStrechedPos(Embedding emb) {
		// save the positions of the nodes
		int maxX = Integer.MIN_VALUE;
		int minX = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		
		for (EmbeddingVertex v : emb.getVertices().values()) {
			maxX = Math.max(maxX, v.getX());
			maxY = Math.max(maxY, v.getY());
			minX = Math.min(minX, v.getX());
			minY = Math.min(minY, v.getY());
		}

		float stretchFactorX = ((float) Constant.CANVAS_WIDTH - 2 * Constant.NODE_SIZE) / ((float) maxX - minX);
		float stretchFactorY = ((float) Constant.CANVAS_HEIGHT - 2* Constant.NODE_SIZE) / ((float) maxY - minY);

		for (EmbeddingVertex v : emb.getVertices().values()) {
			int strechedX = (int) (Constant.NODE_SIZE + (v.getX() - minX) * stretchFactorX);
			int strechedY = (int) (Constant.NODE_SIZE + (v.getY() - minY) * stretchFactorY);
			v.setStrechedX(strechedX);
			v.setStrechedY(strechedY);
		}
	}
	
}
