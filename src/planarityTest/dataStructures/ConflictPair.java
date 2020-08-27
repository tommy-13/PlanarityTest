package planarityTest.dataStructures;

public class ConflictPair {
	
	private static int ID_COUNTER = 0;
	
	private int id;

	
	EdgeInterval leftInterval;
	EdgeInterval rightInterval;
	
	
	/**
	 * Creates a new conflict pair.
	 * @param leftInterval  left interval of edges
	 * @param rightInterval right interval of edges
	 */
	public ConflictPair(EdgeInterval leftInterval, EdgeInterval rightInterval) {
		this.id = ID_COUNTER++;
		this.leftInterval  = leftInterval;
		this.rightInterval = rightInterval;
	}
	
	/**
	 * Returns an empty conflict pair, that is, the left and right interval are both empty intervals.
	 * @return empty conflict pair
	 */
	public static ConflictPair getEmptyPair() {
		return new ConflictPair(EdgeInterval.getEmpty(), EdgeInterval.getEmpty());
	}
	
	public boolean isEmpty() {
		return leftInterval.isEmpty() && rightInterval.isEmpty();
	}
	
	
	/**
	 * Returns the left interval of this conflict pair.
	 * @return left interval
	 */
	public EdgeInterval getLeftInterval() {
		return this.leftInterval;
	}
	
	/**
	 * Returns the right interval of this conflict pair.
	 * @return right interval
	 */
	public EdgeInterval getRightInterval() {
		return this.rightInterval;
	}
	
	/**
	 * Exchanges the left and right interval.
	 */
	public void swapIntervals() {
		EdgeInterval temp = leftInterval;
		leftInterval = rightInterval;
		rightInterval = temp;
	}
	
	/**
	 * Sets the right interval.
	 * @param interval
	 */
	public void setRightInterval(EdgeInterval interval) {
		this.rightInterval = interval;
	}
	
	/**
	 * Sets the right interval.
	 * @param interval
	 */
	public void setLeftInterval(EdgeInterval interval) {
		this.leftInterval = interval;
	}
	

	/**
	 * Tests if this conflict pair is equal to <code>conflictPair</code>.
	 * @param conflictPair other conflict pair.
	 * @return true if and only if the conflict pairs are equal
	 */
	public boolean isEqual(ConflictPair conflictPair) {
		if (conflictPair == null) {
			return false;
		}
		else {
			return id == conflictPair.id;
		}
	}
	
	/**
	 * Returns a string representation of this conflict pair.
	 * @return string representation
	 */
	public String getString() {
		return "id=" + this.id + "{" + (leftInterval == null ? "null" : leftInterval.getString()) + "," + (rightInterval == null ? "null" : rightInterval.getString()) + "}";
	}
}
