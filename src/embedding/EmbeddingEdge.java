package embedding;

/**
 * Implementation of a directed (half)edge.
 * @author tommy
 *
 */
public class EmbeddingEdge {
	
	 private int id;
	 private EmbeddingVertex source;
	 private EmbeddingVertex target;
	 private EmbeddingFace face;     // face to the left of this edge
	 
	 private EmbeddingEdge twin;     // twin to this edge, that is the the edge in reversed direction
	 private EmbeddingEdge next;     // next edge on the boundary of the face
	 private EmbeddingEdge previous; // previous edge on the boundary of the face
	 
	 private EmbeddingEdge edgeBeforCrossing; // edge(part) before the source, if the source is a crossing
	 private EmbeddingEdge edgeAfterCrossing; // edge(part) after the target, if the target is a crossing

	 // drawing
	 private String  name;           // name of this edge
	 private String  mappedName;     // name of the edge this edge is mapped to (if any)
	 private boolean toDraw = false; // show this edge? (either this edge or its twin should be drawn)

	 /**
	  * Create a new <code>Edge</code>.
	  * @param id     id of this edge
	  * @param source source vertex of this edge
	  * @param target target vertex of this edge
	  */
	 public EmbeddingEdge(int id, EmbeddingVertex source, EmbeddingVertex target) {
		 this.id = id;
		 this.source = source;
		 this.target = target;
		 this.name   = id + "";
		 this.mappedName  = name;
	 }
	 
	 /**
	  * Sets the id of this edge. The id should be unique for every edge in a drawing.
	  * @param id new id of this edge
	  */
	 public void setId(int id) {
		 this.id = id;
	 }
	 /**
	  * Returns the id of this edge.
	  * @return id of the edge
	  */
	 public int getId() {
		return id;
	 }
	 
	 /**
	  * Sets the name of this edge.
	  * @param name the new name
	  */
	 public void setName(String name) {
		 this.name = name;
	 }
	 /**
	  * Returns the name of this edge.
	  * @return the name of this edge
	  */
	 public String getName() {
		return name;
	 }
	 
	 /**
	  * Sets the mapped name of this edge. The mapped name is the name of the edge this edge is mapped to
	  * in an isomorphic embedding - if such an embedding exists.
	  * @param mappedName name of the edge in an isomorphic embedding
	  */
	 public void setMappedName(String mappedName) {
		 this.mappedName = mappedName;
	 }
	 /**
	  * Returns the mapped name of this edge.
	  * @return mapped name
	  */
	 public String getMappedName() {
		return mappedName;
	 }
	 
	 /**
	  * Sets the source vertex of this edge.
	  * @param source new source vertex
	  */
	 public void setSource(EmbeddingVertex source) {
		 this.source = source;
	 }
	 /**
	  * Returns the source vertex of this edge.
	  * @return source vertex
	  */
	 public EmbeddingVertex getSource() {
		 return this.source;
	 }
	 
	 /**
	  * Sets the target vertex of this edge.
	  * @param target new target vertex
	  */
	 public void setTarget(EmbeddingVertex target) {
		 this.target = target;
	 }
	 /**
	  * Returns the target vertex of this edge.
	  * @return target vertex
	  */
	 public EmbeddingVertex getTarget() {
		 return this.target;
	 }
	 
	 /**
	  * Sets the twin edge of this edge. The twin edge is the reversed edge to this edge.
	  * @param twinEdge new twin edge
	  */
	 public void setTwin(EmbeddingEdge twinEdge) {
		 this.twin = twinEdge;
	 }
	 /**
	  * Returns the twin edge of this edge.
	  * @return twin edge
	  */
	 public EmbeddingEdge getTwin() {
		 return this.twin;
	 }
	 
	 /**
	  * Sets the face left of this edge.
	  * @param face new left face
	  */
	 public void setFace(EmbeddingFace face) {
		 this.face = face;
	 }
	 /**
	  * Return the left face of this edge.
	  * @return left face
	  */
	 public EmbeddingFace getFace() {
		 return this.face;
	 }
	 
	 /**
	  * Sets the next edge along the boundary of the left face of this edge.
	  * @param nextEdge new next edge
	  */
	 public void setNext(EmbeddingEdge nextEdge) {
		 this.next = nextEdge;
	 }
	 /**
	  * Returns the next edge along the boundary of the left face of this edge.
	  * @return next edge
	  */
	 public EmbeddingEdge getNext() {
		 return this.next;
	 }

	 /**
	  * Sets the previous edge along the boundary of the left face of this edge.
	  * @param nextEdge new previous edge
	  */
	 public void setPrevious(EmbeddingEdge previousEdge) {
		 this.previous = previousEdge;
	 }
	 /**
	  * Returns the previous edge along the boundary of the left face of this edge.
	  * @return previous edge
	  */
	 public EmbeddingEdge getPrevious() {
		 return this.previous;
	 }
	 
	 /**
	  * Sets the edge part that lies directly before this edge (must be used if
	  * the source of this edge is a crossing).
	  * @param edge edge before crossing
	  */
	 public void setEdgeBeforeCrossing(EmbeddingEdge edge) {
		 this.edgeBeforCrossing = edge;
	 }
	 /**
	  * Returns the edge part that comes directly before this edge, if the source
	  * of this edge is a crossing.
	  * @return edge before this edge
	  */
	 public EmbeddingEdge getEdgeBeforeCrossing() {
		 return this.edgeBeforCrossing;
	 }
	 
	 /**
	  * Sets the edge part that lies directly after this edge (must be used if
	  * the target of this edge is a crossing).
	  * @param edge edge after crossing
	  */
	 public void setEdgeAfterCrossing(EmbeddingEdge edge) {
		 this.edgeAfterCrossing = edge;
	 }
	 /**
	  * Returns the edge part that comes directly after this edge (part), if the
	  * target of this edge is a crossing.
	  * @return edge after this edge
	  */
	 public EmbeddingEdge getEdgeAfterCrossing() {
		 return this.edgeAfterCrossing;
	 }

	 /**
	  * Returns if this edge must be drawn in the drawing window.
	  * Either this edge or its twin must be drawn.
	  * @return true, if and only if this edge is drawn
	  */
	 public boolean isToDraw() {
		 return toDraw;
	 }

	 /**
	  * Sets if this edge must be drawn in the drawing window.
	  * Either this edge or its twin must be drawn.
	  * @param toDraw
	  */
	 public void setToDraw(boolean toDraw) {
		 this.toDraw = toDraw;
	 }
	 
	 @Override
	 public String toString() {
		 return "(" + this.source.getName() + "," + this.target.getName() + ")";
	 }

}
