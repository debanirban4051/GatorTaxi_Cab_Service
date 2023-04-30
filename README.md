# GatorTaxi_Cab_Service
A ride sharing software service
 Code Structure
o Class MinHeapRide
The MinHeapRide class is a model class that represents a ride in a min heap data structure. It implements the Comparable interface to define the comparison logic for sorting rides in the min heap.
Data Members-
1. rideNumber (int): Represents the ride number of the ride.
2. rideCost (int): Represents the cost of the ride.
3. tripDuration (int): Represents the duration of the ride.
4. rbtRide (RedBlackTreeRide): An object of the RedBlackTreeRide class, which represents the minimum heap for rides.
5. positionInHeap (int): Represents the index of ride in the heap.
Constructor-
public MinHeapRide(int rideNumber, int rideCost, int tripDuration): Constructor to initialize the MinHeapRide object with the given ride number, ride cost, and trip duration.
Member Functions-
1. @override public int compareTo(MinHeapRide ride): This method compares two MinHeapRide objects based on their ride cost and trip duration and returns -1 if the current ride is smaller, 1 if the current ride is larger, and 0 if they are equal.
2. public void print(): This method prints the ride details in the format of (rideNumber, rideCost, tripDuration).
3. @override public String toString(): This method returns a string representation of the ride details in the format of (rideNumber, rideCost, tripDuration).
o Class MinHeap
This class is an implementation of a min heap that provides methods for inserting rides into the heap, deleting the minimum ride from the heap, deleting an arbitrary ride from the heap, and heapifying the heap to maintain its properties.
Member functions-
1. public MinHeap(int): Constructor for creating a MinHeap object with the specified maximum size.
2. private int positionOfParentForNodeAtIndex(int index): Returns the position of the parent of a ride at a specific index.
3. private int positionOfLeftChildForNodeAtIndex(int index): Returns the position of the left child (ride on the left) of a ride with a specific index
