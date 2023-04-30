import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author Anirban
 * 
 *         GatorTaxi is an emerging ride-hailing service. We receive several
 *         ride requests each day and have created a software to keep track of
 *         pending ride requests.
 *
 */
public class gatorTaxi {

	static RedBlackTree rbTree = new RedBlackTree();
	static MinHeap heap = new MinHeap(2000);

	/**
	 * 
	 * This method is used to insert a ride into the red black tree and min heap and
	 * also create a pointer link between them
	 * 
	 * @param rideNumber   Depicts the ride number of integer type
	 * @param rideCost     Depicts the cost of the ride of Integer type
	 * @param tripDuration Depicts the duration of a trip of integer type
	 * @return String This method returns the String "Duplicate RideNumber" if ride
	 *         number already exists. Else insert happens and empty string is
	 *         returned.
	 * 
	 */
	public static String insert(int rideNumber, int rideCost, int tripDuration) {
		RedBlackTreeRide rbtRide = new RedBlackTreeRide(rideNumber, rideCost, tripDuration);
		MinHeapRide minHeapRide = new MinHeapRide(rideNumber, rideCost, tripDuration);

		rbtRide.minHeapRide = minHeapRide;
		minHeapRide.rbtRide = rbtRide;

		int result = rbTree.insertIntoRbt(rbtRide);

		if (result == -1) {
			return "Duplicate RideNumber";
		}

		heap.insertIntoMinHeap(minHeapRide);

		return "";
	}

	/**
	 * 
	 * This method returns the ride for the rideNumber provided in the argument and
	 * eventually prints it out
	 * 
	 * @param rideNumber Unique Ride number of integer type
	 * @return String Returns a String containing the ride detail in the following
	 *         format (rideNumber,rideCost,rideDuration)
	 */
	public static String print(int rideNumber) {
		return rbTree.searchRideNumberInTree(rideNumber).print() + "\n";
	}

	/**
	 * 
	 * This method returns all the rides between rideNumber1 and rideNumber2 and
	 * eventually prints them out
	 * 
	 * @param rideNumber1 the rideNumber from which we want the rides
	 * @param rideNumber2 the rideNumber till which we want rides
	 * @return String List of all the rides between rideNumber1 and rideNumber2
	 */
	public static String print(int rideNumber1, int rideNumber2) {
		List<String> result = rbTree.printBetweenRide1AndRide2(rideNumber1, rideNumber2);
		if (result.isEmpty()) {
			return "(0,0,0)\n";
		}
		return String.join(",", result) + "\n";
	}

	/**
	 * This method removes the ride with lowest cost from the minheap and
	 * subsequently from the red black tree
	 * 
	 * @return message if there are no ride requests else removes and prints nothing
	 */
	public static String getNextRide() {
		MinHeapRide rideWithLowestCost = heap.deleteMinFromMinheap();
		if (rideWithLowestCost == null) {
			return "No active ride requests\n";
		}
		rbTree.removeMinWithPointerRBT(rideWithLowestCost.rbtRide);
		return rideWithLowestCost.toString() + "\n";
	}

	/**
	 * This method cancels(removes) the ride with ride number in argument from the
	 * red black tree and subsequently from the minheap.
	 * 
	 * @param rideNumber ride number of ride to be cancelled
	 */
	public static void cancelRide(int rideNumber) {
		RedBlackTreeRide cancelledRide = rbTree.removeFromRBT(rideNumber);
		if (cancelledRide != null) {
			heap.deleteArbitraryRideFromMinheap(cancelledRide.minHeapRide);
		}
	}

	/**
	 * This method updates the trip details of ride whose ride number is in
	 * arguments. The ride cost changes according to the new trip_duration
	 * 
	 * @param rideNumber       ride number of ride to be updated
	 * @param new_tripDuration new trip duration
	 */
	public static void updateTrip(int rideNumber, int new_tripDuration) {
		RedBlackTreeRide rideSearch = rbTree.searchRideNumberInTree(rideNumber);

		if (new_tripDuration <= rideSearch.tripDuration) {
			MinHeapRide minFindRide = rideSearch.minHeapRide;
			rideSearch.tripDuration = new_tripDuration;
			minFindRide.tripDuration = new_tripDuration;
		} else if (new_tripDuration > rideSearch.tripDuration && new_tripDuration <= (2 * rideSearch.tripDuration)) {
			cancelRide(rideNumber);
			insert(rideNumber, rideSearch.rideCost + 10, new_tripDuration);
		} else {
			cancelRide(rideNumber);
		}
	}

	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("No arguments");
			return;
		}

		File outputFile = new File("output.txt");

		try (FileReader readFile = new FileReader(args[0]);
				FileWriter writeFile = new FileWriter("output.txt");
				BufferedReader bufferedReader = new BufferedReader(readFile);
				BufferedWriter writer = new BufferedWriter(writeFile)) {
			String eachLine;
			while ((eachLine = bufferedReader.readLine()) != null) {
				String[] splits = eachLine.split("\\(");
				String functionName = splits[0];
				String[] argumentSpilts = splits[1].split("\\)");
				String[] functionArguments = new String[0];
				if (argumentSpilts.length > 0 && !argumentSpilts[0].isEmpty()) {
					functionArguments = argumentSpilts[0].split(",");
				}

				String result = "";

				switch (functionName) {
				case "Insert":
					result = insert(Integer.parseInt(functionArguments[0]), Integer.parseInt(functionArguments[1]),
							Integer.parseInt(functionArguments[2]));
					break;
				case "Print":
					//If there is 1 argument print the ride details with that ride number
					//Else if there are two arguments print all the rides between those ride numbers
					if (functionArguments.length == 1) {
						result = print(Integer.parseInt(functionArguments[0]));
					} else {
						result = print(Integer.parseInt(functionArguments[0]), Integer.parseInt(functionArguments[1]));
					}
					break;
				case "GetNextRide":
					result = getNextRide();
					break;
				case "CancelRide":
					cancelRide(Integer.parseInt(functionArguments[0]));
					break;
				case "UpdateTrip":
					updateTrip(Integer.parseInt(functionArguments[0]), Integer.parseInt(functionArguments[1]));
					break;
				default:
					System.out.println("Invalid operation name");
					break;
				}

				writer.write(result);

				if (result.contains("Duplicate RideNumber")) {
					break;
				}
			}

			System.out.println("Result printed in file:  " + outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}