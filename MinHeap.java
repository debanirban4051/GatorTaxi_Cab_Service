
/**
 * 
 * @author Anirban
 * 
 *         Implementation of minheap with all the operations
 *
 */
public class MinHeap {

	private MinHeapRide[] minHeap;
	private int currentSize;
	private int heapMaxSize;

	private static final int MINPOSITION = 1;

	/**
	 * Constructor
	 * 
	 * @param maximumSize
	 */
	public MinHeap(int maximumSize) {

		// This keyword refers to current object itself
		this.heapMaxSize = maximumSize;
		this.currentSize = 0;

		minHeap = new MinHeapRide[this.heapMaxSize + 1];
		minHeap[0] = new MinHeapRide(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
		minHeap[0].positionInHeap = 0;
	}

	/**
	 * This method returns the position of the parent of a specific index
	 * 
	 * @param index position of ride for which we need parent's position
	 * @return int Position of parent of ride at index
	 */
	private int positionOfParentForNodeAtIndex(int index) {
		return index / 2;
	}

	/**
	 * This method returns the position of the left child of a specific index
	 * 
	 * @param index position of ride for which we need left child's position
	 * @return int Position of left child of ride at index
	 */
	private int positionOfLeftChildForNodeAtIndex(int index) {
		return (2 * index);
	}

	/**
	 * This method returns the position of the right child of a specific index
	 * 
	 * @param index position of ride for which we need right child's position
	 * @return int Position of right child of ride at index
	 */
	private int positionOfRightChildForNodeAtIndex(int index) {
		return (2 * index) + 1;
	}

	/**
	 * This method returns if the node at index is a leaf node or not
	 * 
	 * @param index
	 * @return
	 */
	private boolean isLeaf(int index) {

		if (index > (currentSize / 2)) {
			return true;
		}
		return false;
	}

	/**
	 * This method swaps two rides in the min heap
	 * 
	 * @param firstPosition  First ride
	 * @param secondPosition Second ride
	 */
	private void positionSwapInHeap(int firstPosition, int secondPosition) {

		MinHeapRide tmp;
		tmp = minHeap[firstPosition];

		minHeap[firstPosition] = minHeap[secondPosition];
		minHeap[secondPosition] = tmp;

		minHeap[firstPosition].positionInHeap = firstPosition;
		minHeap[secondPosition].positionInHeap = secondPosition;
	}

	/**
	 * This method is used to heapify the min heap to maintain its properties
	 * 
	 * @param index Index of ride to do the heapify
	 */
	private void heapifyMinHeap(int index) {
		if (!isLeaf(index)) {
			int positionToSwap = index;
			if (positionOfRightChildForNodeAtIndex(index) <= currentSize)
				positionToSwap = (minHeap[positionOfLeftChildForNodeAtIndex(index)]
						.compareTo(minHeap[positionOfRightChildForNodeAtIndex(index)]) < 0)
								? positionOfLeftChildForNodeAtIndex(index)
								: positionOfRightChildForNodeAtIndex(index);
			else
				positionToSwap = positionOfLeftChildForNodeAtIndex(index);

			if ((minHeap[index].compareTo(minHeap[positionOfLeftChildForNodeAtIndex(index)]) > 0)
					|| (minHeap[index].compareTo(minHeap[positionOfRightChildForNodeAtIndex(index)]) > 0)) {
				positionSwapInHeap(index, positionToSwap);
				heapifyMinHeap(positionToSwap);
			}

		}
	}

	/**
	 * This method is used to insert a new ride into the min heap
	 * 
	 * @param ride New ride to be inserted
	 */
	public void insertIntoMinHeap(MinHeapRide ride) {

		if (currentSize >= heapMaxSize) {
			return;
		}

		minHeap[++currentSize] = ride;
		int current = currentSize;
		ride.positionInHeap = current;

		while (minHeap[current].compareTo(minHeap[positionOfParentForNodeAtIndex(current)]) < 0) {
			positionSwapInHeap(current, positionOfParentForNodeAtIndex(current));
			current = positionOfParentForNodeAtIndex(current);
		}
	}

	/**
	 * This method is used to remove the min from the minheap and heapify to
	 * maintain min heap properties
	 * 
	 * @return MinHeapRide This method returns the deleted ride
	 */
	public MinHeapRide deleteMinFromMinheap() {

		if (currentSize == 0) {
			return null;
		}
		MinHeapRide popped = minHeap[MINPOSITION];
		minHeap[MINPOSITION] = minHeap[currentSize--];
		minHeap[MINPOSITION].positionInHeap = MINPOSITION;
		heapifyMinHeap(MINPOSITION);

		return popped;
	}

	/**
	 * This method removes an arbitrary ride from the in heap and then heapifies.
	 * 
	 * @param minHeapRide
	 * @return
	 */
	public MinHeapRide deleteArbitraryRideFromMinheap(MinHeapRide minHeapRide) {
		int index = minHeapRide.positionInHeap;
		MinHeapRide deleted = minHeap[index];
		minHeap[index] = minHeap[currentSize--];
		minHeap[index].positionInHeap = index;
		heapifyMinHeap(index);

		return deleted;
	}
}
