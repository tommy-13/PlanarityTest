package alg.countingSort;

import alg.dataStructure.Sortable;

public interface SortableSortingAlgorithm {

	public String getName();
	
	/**
	 * Sorts an array in ascending order according to the keys of
	 * the sortables.
	 * @param array
	 * @return in ascending order sorted array
	 */
	public Sortable[] sort(Sortable[] array);
	
}
