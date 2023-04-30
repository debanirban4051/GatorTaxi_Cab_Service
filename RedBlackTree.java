import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Implementation of red black tree
 * 
 * @author Anirban
 *
 */
public class RedBlackTree {
	private RedBlackTreeRide root;
	private RedBlackTreeRide rbtExtNode;

	/**
	 * Constructor
	 */
	public RedBlackTree() {
		rbtExtNode = new RedBlackTreeRide(0, 0, 0);
		rbtExtNode.colour = 0;
		rbtExtNode.left = null;
		rbtExtNode.right = null;
		root = rbtExtNode;
	}

	/**
	 * This method is used to search for a ride using ride number
	 * 
	 * @param rideNumber ride number of ride to be searched
	 * @return RedBlackTreeRide
	 * 
	 */
	public RedBlackTreeRide searchRideNumberInTree(int rideNumber) {
		return searchRideNumberInTreeHelper(this.root, rideNumber);
	}

	/**
	 * Helper method for searchRideNumberInTree
	 * 
	 * @param rbtRide
	 * @param rideNumber
	 * @return
	 */
	private RedBlackTreeRide searchRideNumberInTreeHelper(RedBlackTreeRide rbtRide, int rideNumber) {
		if (rbtRide == rbtExtNode || rideNumber == rbtRide.rideNumber) {
			return rbtRide;
		}

		if (rideNumber < rbtRide.rideNumber) {
			return searchRideNumberInTreeHelper(rbtRide.left, rideNumber);
		}
		return searchRideNumberInTreeHelper(rbtRide.right, rideNumber);
	}

	/**
	 * This method returns a list of all the rides between two specific ride numbers
	 * 
	 * @param rideNumber1 from ride number
	 * @param rideNumber2 ride number till
	 * @return List<String> list of rides
	 */
	public List<String> printBetweenRide1AndRide2(int rideNumber1, int rideNumber2) {
		List<String> inRange = new ArrayList<String>();
		printBetweenRide1AndRide2Helper(root, rideNumber1, rideNumber2, inRange);
		return inRange;
	}

	/**
	 * 
	 * Helper method for printBetweenRide1AndRide2
	 * 
	 * @param rbtRide
	 * @param rideNumber1
	 * @param rideNumber2
	 * @param result
	 */
	public void printBetweenRide1AndRide2Helper(RedBlackTreeRide rbtRide, int rideNumber1, int rideNumber2,
			List<String> result) {
		if (rbtRide == rbtExtNode) {
			return;
		}
		if (rbtRide.rideNumber > rideNumber1 && rbtRide.rideNumber < rideNumber2) {
			printBetweenRide1AndRide2Helper(rbtRide.left, rideNumber1, rideNumber2, result);
			result.add(rbtRide.toString());
			printBetweenRide1AndRide2Helper(rbtRide.right, rideNumber1, rideNumber2, result);
		} else if (rbtRide.rideNumber == rideNumber1) {
			result.add(rbtRide.toString());
			printBetweenRide1AndRide2Helper(rbtRide.right, rideNumber1, rideNumber2, result);
		} else if (rbtRide.rideNumber == rideNumber2) {
			printBetweenRide1AndRide2Helper(rbtRide.left, rideNumber1, rideNumber2, result);
			result.add(rbtRide.toString());
		} else if (rbtRide.rideNumber < rideNumber1) {
			printBetweenRide1AndRide2Helper(rbtRide.right, rideNumber1, rideNumber2, result);
		} else {
			printBetweenRide1AndRide2Helper(rbtRide.left, rideNumber1, rideNumber2, result);
		}
	}

	/**
	 * This method is executed after deletion to make sure red black tree properties
	 * are maintained
	 * 
	 * @param rbtRide
	 */
	private void repositionAfterDelete(RedBlackTreeRide rbtRide) {
		RedBlackTreeRide tempRide;
		while (rbtRide != root && rbtRide.colour == 0) {
			if (rbtRide == rbtRide.parent.left) {
				tempRide = rbtRide.parent.right;
				if (tempRide.colour == 1) {
					tempRide.colour = 0;
					rbtRide.parent.colour = 1;
					rotationLeft(rbtRide.parent);
					tempRide = rbtRide.parent.right;
				}

				if (tempRide.left.colour == 0 && tempRide.right.colour == 0) {
					tempRide.colour = 1;
					rbtRide = rbtRide.parent;
				} else {
					if (tempRide.right.colour == 0) {
						tempRide.left.colour = 0;
						tempRide.colour = 1;
						rotationRight(tempRide);
						tempRide = rbtRide.parent.right;
					}

					tempRide.colour = rbtRide.parent.colour;
					rbtRide.parent.colour = 0;
					tempRide.right.colour = 0;
					rotationLeft(rbtRide.parent);
					rbtRide = root;
				}
			} else {
				tempRide = rbtRide.parent.left;
				if (tempRide.colour == 1) {
					tempRide.colour = 0;
					rbtRide.parent.colour = 1;
					rotationRight(rbtRide.parent);
					tempRide = rbtRide.parent.left;
				}

				if (tempRide.right.colour == 0 && tempRide.right.colour == 0) {
					tempRide.colour = 1;
					rbtRide = rbtRide.parent;
				} else {
					if (tempRide.left.colour == 0) {
						tempRide.right.colour = 0;
						tempRide.colour = 1;
						rotationLeft(tempRide);
						tempRide = rbtRide.parent.left;
					}

					tempRide.colour = rbtRide.parent.colour;
					rbtRide.parent.colour = 0;
					tempRide.left.colour = 0;
					rotationRight(rbtRide.parent);
					rbtRide = root;
				}
			}
		}
		rbtRide.colour = 0;
	}

	/**
	 * This method is used to interchange the position in a red black tree to
	 * maintain property
	 * 
	 * @param rideA
	 * @param rideB
	 */
	private void redBlackTransplant(RedBlackTreeRide rideA, RedBlackTreeRide rideB) {
		if (rideA.parent == null) {
			root = rideB;
		} else if (rideA == rideA.parent.left) {
			rideA.parent.left = rideB;
		} else {
			rideA.parent.right = rideB;
		}
		rideB.parent = rideA.parent;
	}

	/**
	 * @param rbtRide
	 * @param rideNumber
	 * @return
	 */
	private RedBlackTreeRide removeMinRBTHelper(RedBlackTreeRide rbtRide, int rideNumber) {
		RedBlackTreeRide rideToBeDeleted = rbtExtNode;
		RedBlackTreeRide xRide, yRide;
		while (rbtRide != rbtExtNode) {
			if (rbtRide.rideNumber == rideNumber) {
				rideToBeDeleted = rbtRide;
			}

			if (rbtRide.rideNumber <= rideNumber) {
				rbtRide = rbtRide.right;
			} else {
				rbtRide = rbtRide.left;
			}
		}

		if (rideToBeDeleted == rbtExtNode) {
			return null;
		}

		yRide = rideToBeDeleted;
		int actualColourY = yRide.colour;
		if (rideToBeDeleted.left == rbtExtNode) {
			xRide = rideToBeDeleted.right;
			redBlackTransplant(rideToBeDeleted, rideToBeDeleted.right);
		} else if (rideToBeDeleted.right == rbtExtNode) {
			xRide = rideToBeDeleted.left;
			redBlackTransplant(rideToBeDeleted, rideToBeDeleted.left);
		} else {
			yRide = minimumRideNumber(rideToBeDeleted.right);
			actualColourY = yRide.colour;
			xRide = yRide.right;
			if (yRide.parent == rideToBeDeleted) {
				xRide.parent = yRide;
			} else {
				redBlackTransplant(yRide, yRide.right);
				yRide.right = rideToBeDeleted.right;
				yRide.right.parent = yRide;
			}

			redBlackTransplant(rideToBeDeleted, yRide);
			yRide.left = rideToBeDeleted.left;
			yRide.left.parent = yRide;
			yRide.colour = rideToBeDeleted.colour;
		}
		if (actualColourY == 0) {
			repositionAfterDelete(xRide);
		}

		return rideToBeDeleted;
	}

	/**
	 * @param rideToBeRemoved
	 */
	private void removeMinWithPointerRBTHelper(RedBlackTreeRide rideToBeRemoved) {

		RedBlackTreeRide xRide, yRide;

		yRide = rideToBeRemoved;
		int actualColourY = yRide.colour;
		if (rideToBeRemoved.left == rbtExtNode) {
			xRide = rideToBeRemoved.right;
			redBlackTransplant(rideToBeRemoved, rideToBeRemoved.right);
		} else if (rideToBeRemoved.right == rbtExtNode) {
			xRide = rideToBeRemoved.left;
			redBlackTransplant(rideToBeRemoved, rideToBeRemoved.left);
		} else {
			yRide = minimumRideNumber(rideToBeRemoved.right);
			actualColourY = yRide.colour;
			xRide = yRide.right;
			if (yRide.parent == rideToBeRemoved) {
				xRide.parent = yRide;
			} else {
				redBlackTransplant(yRide, yRide.right);
				yRide.right = rideToBeRemoved.right;
				yRide.right.parent = yRide;
			}

			redBlackTransplant(rideToBeRemoved, yRide);
			yRide.left = rideToBeRemoved.left;
			yRide.left.parent = yRide;
			yRide.colour = rideToBeRemoved.colour;
		}
		if (actualColourY == 0) {
			repositionAfterDelete(xRide);
		}
	}

	/**
	 * 
	 * This method inserts a new ride into the red black tree
	 * 
	 * @param rbtRide RBTRide object that contains number,cost and duration
	 * @return Int
	 */
	public int insertIntoRbt(RedBlackTreeRide rbtRide) {
		// Creating a node for new ride
		rbtRide.parent = null;
		rbtRide.left = rbtExtNode;
		rbtRide.right = rbtExtNode;
		rbtRide.colour = 1; // New node is colored red at first

		// For inserting we have to traverse and find the parent of new node so that we
		// can attach it
		RedBlackTreeRide parentOfNewRide = null;
		RedBlackTreeRide nodeToTraverse = this.root;

		while (nodeToTraverse != rbtExtNode) {
			parentOfNewRide = nodeToTraverse;
			if (rbtRide.rideNumber < nodeToTraverse.rideNumber) {
				nodeToTraverse = nodeToTraverse.left;
			} else {
				nodeToTraverse = nodeToTraverse.right;
			}
		}

		rbtRide.parent = parentOfNewRide;
		if (parentOfNewRide == null) {
			root = rbtRide;
		} else if (rbtRide.rideNumber < parentOfNewRide.rideNumber) {
			parentOfNewRide.left = rbtRide;
		} else if (rbtRide.rideNumber > parentOfNewRide.rideNumber) {
			parentOfNewRide.right = rbtRide;
		} else {
			return -1;
		}

		if (rbtRide.parent == null) {
			rbtRide.colour = 0;
			return 0;
		}

		if (rbtRide.parent.parent == null) {
			return 0;
		}

		repositionAfterInsert(rbtRide);

		return 0;
	}

	/**
	 * @param rbtRide
	 */
	private void repositionAfterInsert(RedBlackTreeRide rbtRide) {
		RedBlackTreeRide u;
		while (rbtRide.parent.colour == 1) {
			if (rbtRide.parent == rbtRide.parent.parent.right) {
				u = rbtRide.parent.parent.left;
				if (u.colour == 1) {
					u.colour = 0;
					rbtRide.parent.colour = 0;
					rbtRide.parent.parent.colour = 1;
					rbtRide = rbtRide.parent.parent;
				} else {
					if (rbtRide == rbtRide.parent.left) {
						rbtRide = rbtRide.parent;
						rotationRight(rbtRide);
					}
					rbtRide.parent.colour = 0;
					rbtRide.parent.parent.colour = 1;
					rotationLeft(rbtRide.parent.parent);
				}
			} else {
				u = rbtRide.parent.parent.right;

				if (u.colour == 1) {
					u.colour = 0;
					rbtRide.parent.colour = 0;
					rbtRide.parent.parent.colour = 1;
					rbtRide = rbtRide.parent.parent;
				} else {
					if (rbtRide == rbtRide.parent.right) {
						rbtRide = rbtRide.parent;
						rotationLeft(rbtRide);
					}
					rbtRide.parent.colour = 0;
					rbtRide.parent.parent.colour = 1;
					rotationRight(rbtRide.parent.parent);
				}
			}
			if (rbtRide == root) {
				break;
			}
		}
		root.colour = 0;
	}

	/**
	 * This method returns the ride with smallest ride number
	 * 
	 * @param rbtRide root rbtRide object of red black tree
	 * @return RBTRide Ride with minimum ride number
	 */
	public RedBlackTreeRide minimumRideNumber(RedBlackTreeRide rbtRide) {
		while (rbtRide.left != rbtExtNode) {
			rbtRide = rbtRide.left;
		}
		return rbtRide;
	}

	/**
	 * This method does a left rotate on the ride provided in argument
	 * 
	 * @param rbtRide RBTRide object
	 */
	public void rotationLeft(RedBlackTreeRide rbtRide) {
		RedBlackTreeRide y = rbtRide.right;
		rbtRide.right = y.left;
		if (y.left != rbtExtNode) {
			y.left.parent = rbtRide;
		}
		y.parent = rbtRide.parent;
		if (rbtRide.parent == null) {
			this.root = y;
		} else if (rbtRide == rbtRide.parent.left) {
			rbtRide.parent.left = y;
		} else {
			rbtRide.parent.right = y;
		}
		y.left = rbtRide;
		rbtRide.parent = y;
	}

	/**
	 * This method does a right rotate on the ride provided in argument
	 * 
	 * @param rbtRide
	 */
	public void rotationRight(RedBlackTreeRide rbtRide) {
		RedBlackTreeRide y = rbtRide.left;
		rbtRide.left = y.right;
		if (y.right != rbtExtNode) {
			y.right.parent = rbtRide;
		}
		y.parent = rbtRide.parent;
		if (rbtRide.parent == null) {
			this.root = y;
		} else if (rbtRide == rbtRide.parent.right) {
			rbtRide.parent.right = y;
		} else {
			rbtRide.parent.left = y;
		}
		y.right = rbtRide;
		rbtRide.parent = y;
	}

	/**
	 * This method updates the ride details based on the new trip duration
	 * 
	 * @param rideNumber
	 * @param new_tripDuration
	 */
	public void updateTrip(int rideNumber, int new_tripDuration) {
		RedBlackTreeRide findRide = searchRideNumberInTree(rideNumber);
		if (new_tripDuration <= findRide.tripDuration) {
			findRide.tripDuration = new_tripDuration;
		} else if (new_tripDuration > findRide.tripDuration && new_tripDuration <= (2 * findRide.tripDuration)) {
			removeFromRBT(rideNumber);
			insertIntoRbt(new RedBlackTreeRide(rideNumber, findRide.rideCost + 10, new_tripDuration));
		} else {
			removeFromRBT(rideNumber);
		}
	}

	/**
	 * This method removes the ride with ride number specified in argument
	 * 
	 * @param rideNumber ride number of ride to be removed
	 * @return RedBlackTreeRide returns the ride that is deleted
	 */
	public RedBlackTreeRide removeFromRBT(int rideNumber) {
		return removeMinRBTHelper(this.root, rideNumber);
	}

	/**
	 * This method removes the ride in the argument which we get after removal from
	 * minheap
	 * 
	 * @param rbtRide Ride that has to be deleted
	 */
	public void removeMinWithPointerRBT(RedBlackTreeRide rbtRide) {
		removeMinWithPointerRBTHelper(rbtRide);
	}
}