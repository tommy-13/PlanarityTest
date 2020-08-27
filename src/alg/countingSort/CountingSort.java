package alg.countingSort;

import alg.dataStructure.Sortable;

public class CountingSort implements SortableSortingAlgorithm {

	@Override
	public String getName() {
		return "CountingSort";
	}
	
	
	/**
	 * Sorts an array.
	 * @param array
	 * @param min minimal element in this array
	 * @param max maximal element in this array
	 * @return a sorted array
	 */
	public Sortable[] sort(final Sortable[] array, int min, int max) throws OutOfMemoryError {
		if(array == null) {
			return null;
		}
		int		range	= max - min + 1;
		if(range <= 0) {
			throw new IllegalArgumentException();
		}
		
		Sortable[]	result	= new Sortable[array.length];
		int[]		counter	= new int[range];
		
		try {
			// count elements
			for(int i=0; i<array.length; i++) {
				counter[array[i].getKey()-min]++;
			}

			// calculate number of elements less than or equal to min + i
			for(int i=1; i<range; i++) {
				counter[i] = counter[i] + counter[i-1];
			}

			// order elements
			for(int i=array.length-1; i>=0; i--) {
				if(counter[array[i].getKey()-min]-1 < 0) {
					System.out.println("problem");
				}
				result[counter[array[i].getKey()-min]-1] = array[i];
				counter[array[i].getKey()-min]--;
			}
		} catch(IndexOutOfBoundsException e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
		
		return result;
	}
	
	@Override
	public Sortable[] sort(Sortable[] array) throws OutOfMemoryError {
		if(array == null) {
			return null;
		}
		if(array.length == 0) {
			return new Sortable[]{};
		}
		if(array.length == 1) {
			return new Sortable[]{array[0]};
		}

		Sortable 	min 	= array[0];
		Sortable 	max 	= array[0];
		int 		start	= 1;
		if(array.length % 2 == 0) {
			// even length
			if(array[1].getKey() >= array[0].getKey()) {
				max = array[1];
			}
			else {
				min = array[1];
			}
			start	= 2;
		}
		
		for(int i=start; i<array.length; i=i+2) {
			// compare two successive array elements
			if(array[i].getKey() < array[i+1].getKey()) {
				min = array[i].getKey() < min.getKey() ? array[i] : min;
				max = array[i+1].getKey() > max.getKey()? array[i+1] : max;
			}
			else {
				min = array[i+1].getKey() < min.getKey() ? array[i+1] : min;
				max = array[i].getKey() > max.getKey() ? array[i] : max;
			}
		}
		
		/*
		for (int i=0; i<array.length; i++) {
			System.out.print(array[i].getKey() + " ");
		}
		System.out.println("\n min: " + min.getKey() + " max: " + max.getKey());
		*/
		
		return sort(array, min.getKey(), max.getKey());
	}
	
}
