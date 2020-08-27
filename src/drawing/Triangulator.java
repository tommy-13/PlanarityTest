package drawing;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import embedding.Embedding;
import embedding.EmbeddingEdge;
import embedding.EmbeddingFace;
import embedding.EmbeddingVertex;

/**
 * Class to triangulate a simple planar embedding.
 * @author tommy
 *
 */
public class Triangulator {
	
	/**
	 * Triangulates the specified embedding.
	 * @param embedding			a simple planar embedding
	 * @param triangulationId	id the triangulation will get
	 * @return					a triangulated embedding
	 */
	public static Embedding triangulate(final Embedding embedding, int triangulationId) {

		Embedding triangulation      = embedding.copy(triangulationId);
		HashMap<Integer, EmbeddingFace> faces = embedding.getFaces();
		
		
		if (embedding.getNumberFaces() == 1) {
			// get single face
			EmbeddingFace face = null;
			for (EmbeddingFace ef : faces.values()) {
				face = ef;
			}
			
			
			if (embedding.getNumberVertices() == 2) {
				EmbeddingVertex dummy = triangulation.createVertex();
				for (EmbeddingVertex ev : embedding.getVertices().values()) {
					triangulation.insertEdge(ev.getId(), dummy.getId(), face.getId(),-1);
				}
				return triangulation;
			}
			else if (embedding.getNumberVertices() == 1) {
				EmbeddingVertex dummy1 = triangulation.createVertex();
				EmbeddingVertex dummy2 = triangulation.createVertex();
				for (EmbeddingVertex ev : embedding.getVertices().values()) {
					triangulation.insertEdge(ev.getId(), dummy1.getId(), face.getId(),-1);
					triangulation.insertEdge(ev.getId(), dummy2.getId(), face.getId(),-1);
				}
				return triangulation;
			}
		}
		
		
		
		for (Entry<Integer, EmbeddingFace> entry : faces.entrySet()) {
			int  faceId = entry.getKey();
			if (triangulation.getFaceDegree(faceId) <= 3) {
				//System.out.println("small face");
				continue;
			}
			/*
			else {
				System.out.println("face size: " + triangulation.getFaceDegree(faceId));
			}*/
						
			EmbeddingVertex middleVertex = triangulation.createVertex();
			int    targetId     = middleVertex.getId();
			List<EmbeddingEdge> edges    = embedding.getIncidentEdgesToFace(faceId);
			for (EmbeddingEdge e : edges) {
				int sourceId        = e.getSource().getId();
				EmbeddingEdge triangEdge = triangulation.getEdge(e.getId());
				int insertionFaceId = triangEdge.getFace().getId();
				
				//System.out.println("source: " + triangulation.hasFaceVertex(insertionFaceId, sourceId));
				//System.out.println("target: " + triangulation.hasFaceVertex(insertionFaceId, targetId));
				
				triangulation.insertEdge(sourceId, targetId, insertionFaceId,-1);
			}
		}
		
		return triangulation;
	}

}
