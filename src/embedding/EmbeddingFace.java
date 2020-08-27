package embedding;

/**
 * Class to implement a face.
 * @author tommy
 *
 */
public class EmbeddingFace {

	private int id;				// id of the face
	private EmbeddingEdge incidentEdge;	// an edge (part) on the boundary of this face
	
	/**
	 * Creates a new <code>Face</code>.
	 * @param id id of the new face
	 */
	public EmbeddingFace(int id) {
		this.id = id;
	}
	
	/**
	 * Creates a new <code>Face</code>.
	 * @param id			id of the new face
	 * @param incidentEdge	edge (part) on the boundary of this face
	 */
	public EmbeddingFace(int id, EmbeddingEdge incidentEdge) {
		this(id);
		this.incidentEdge = incidentEdge;
	}
	
	/**
	 * Sets the id of this face.
	 * @param id new id of the face
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Returns the id of this face.
	 * @return id of the face
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Sets the incident edge of this face, that is an edge on the boundary of this face.
	 * @param incidentEdge incident edge
	 */
	public void setIncidentEdge(EmbeddingEdge incidentEdge) {
		this.incidentEdge = incidentEdge;
	}
	/**
	 * Returns an incident edge of this face, that is an edge on the boundary of this face. 
	 * @return incident edge
	 */
	public EmbeddingEdge getIncidentEdge() {
		return this.incidentEdge;
	}
}
