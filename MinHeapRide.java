/**
 * @author Anirban
 * 
 * Model class for min heap
 *
 */
public class MinHeapRide implements Comparable<MinHeapRide> {
	public int rideNumber;
    public int rideCost;
    public int tripDuration;    
    public RedBlackTreeRide rbtRide;
    public int positionInHeap;

    public MinHeapRide(int rideNumber, int rideCost, int tripDuration)
    {
        this.rideNumber = rideNumber;
        this.rideCost = rideCost;
        this.tripDuration = tripDuration;
    }

    @Override
    public int compareTo(MinHeapRide ride) {
    	if (rideCost < ride.rideCost)
        {
        	return -1;
        }
        else if(rideCost > ride.rideCost)
        {
        	return 1;
        }
        else
        {
        	if (tripDuration < ride.tripDuration)
            {
            	return -1;
            }
            else if(tripDuration > ride.tripDuration)
            {
            	return 1;
            }
            else
            {
            	return 0;
            }
        }
    }

    public void print()
    {
        System.out.print("(" + rideNumber + "," + rideCost + "," + tripDuration + ")");
    }
    
    @Override
    public String toString()
    {
    	return "(" + rideNumber + "," + rideCost + "," + tripDuration + ")";
    }
}
