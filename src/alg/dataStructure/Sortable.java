package alg.dataStructure;

public abstract class Sortable {

	protected int	key	= -1;
	
	public final void setKey(int key) {
		this.key = key;
	}
	public final int getKey() {
		return key;
	}
	
	
	/**
	 * Compares the key of this element with the key of <code>other</code>.
	 * If this key is bigger, a value > 0 will be returned.
	 * If this key is smaller, a value < 0 will be returned.
	 * If both keys are the same, 0 will be returned.
	 * @param other
	 * @return
	 */
	public final int compare(Sortable other) {
		if(getKey() > other.getKey()) {
			return 1;
		}
		if(getKey() < other.getKey()) {
			return -1;
		}
		return 0;
	}
	
}
