/**
 * @author Anirban
 * 
 * Model class for red black tree
 *
 */
public class RedBlackTreeRide implements Comparable<RedBlackTreeRide> {
    public int rideNumber;
    public int rideCost;
    public int tripDuration;    //minutes
    public MinHeapRide minHeapRide;
    RedBlackTreeRide parent;
    RedBlackTreeRide left;
    RedBlackTreeRide right;
    int colour;

    public RedBlackTreeRide(int rideNumber, int rideCost, int tripDuration)
    {
        this.rideNumber = rideNumber;
        this.rideCost = rideCost;
        this.tripDuration = tripDuration;
    }

    @Override
    public int compareTo(RedBlackTreeRide ride) {
        if (rideNumber < ride.rideNumber)
        {
        	return -1;
        }
        else if(rideNumber > ride.rideNumber)
        {
        	return 1;
        }
        else
        {
        	return 0;
        }
    }

    public String print()
    {
        return ("(" + rideNumber + "," + rideCost + "," + tripDuration + ")");
    }
    
    @Override
    public String toString()
    {
    	return "(" + rideNumber + "," + rideCost + "," + tripDuration + ")";
    }
}