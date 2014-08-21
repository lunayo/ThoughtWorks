import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;



public class KiwiLand {
	private int INF = 100000000;
	private Map<Character, ArrayList<EdgePoint>> edges = new HashMap<>();
	
	public KiwiLand(String[] distances) {
		initialiseEdges(distances);
	}
	
	public String distanceToString(int distance) {
		return (distance == -1) ? "NO SUCH ROUTE" : Integer.toString(distance);
	}
	
	public int shortestPath(char start, char end) {
		// initialise distance
		Map<Character, Integer> distances = new HashMap<>();
		distances.put(start, 0);
		// initialise shortest path
		PriorityQueue<EdgePoint> priorityQueue = new PriorityQueue<EdgePoint>(10, new Comparator<EdgePoint>() {
			public int compare(EdgePoint point1, EdgePoint point2) {
				if (point1.distance < point2.distance) {
					return -1;
				}
				if (point1.distance > point2.distance) {
					return 1;
				}
				return 0;
			}
		});
		
		// push initial 
		priorityQueue.add(new EdgePoint(0, start));
		while (!priorityQueue.isEmpty()) {
			EdgePoint top = priorityQueue.remove();
			int distance = top.distance;
			char currNode = top.destination;
			// avoid duplicate node
			if (distance == distances.get(currNode)) {
				for (EdgePoint edgePoint : edges.get(currNode)) {
					
					char destNode = edgePoint.destination;
					int destDestination = edgePoint.distance;
					// add the distance if not exists
					if (!distances.containsKey(destNode)) {
						distances.put(destNode, INF);
					} 
					
					// update the total distance if it is lower than the previous added path
					int travelDistance = (distances.get(currNode) + destDestination);
					if (travelDistance < distances.get(destNode) ||
							distances.get(destNode) == 0) {
						distances.put(destNode, travelDistance);
						// push the new node
						priorityQueue.add(new EdgePoint(travelDistance, destNode));
					}
				}
			}
		}
		
		return distances.get(end);
	}
	
	public int getDistance(char current, char destination) {
		// Return earlier
		if (current == destination) {
			return 0;
		}
		
		// Not very efficient for this structure data
		// it can be improved using hashmap
		// Iterate over the edgepoint to search for the distance
		Iterable<EdgePoint> edgePoints = edges.get(current);
		for (EdgePoint edgePoint : edgePoints) {
			if (edgePoint.destination == destination) {
				return edgePoint.distance;
			}
		}
		
		// Could not find anything
		return -1;
	}
	
	public int distanceOfRoute(char[] routes) {
		int distance = 0;
		for (int i = 0; i < routes.length - 1; i++) {
			int tempDistance = this.getDistance(routes[i], routes[i+1]);
			if (tempDistance == -1) return -1;
			distance += tempDistance;
		}
		
		return distance;
	}
	
	public int numberOfTrips(char current, char end, int maxStop, int currentLevel, Boolean isExact) {
		// assuming there is no cycle trip
		int numOfTrips = 0;
		
		if (currentLevel > maxStop) {
			return 0;
		}
		if (isExact) {
			if (current == end && currentLevel != 0 && 
					currentLevel == maxStop) {
				return 1;
			}
		} else {
			if (current == end && currentLevel != 0) {
				return 1;
			}
		}
		
		
		// DFS the number of trips that less than maxstop
		// get the list of destination
		for (EdgePoint edgePoint : edges.get(current)) {
			char destination = edgePoint.destination;
			numOfTrips += this.numberOfTrips(destination, end, maxStop, currentLevel + 1, isExact);
		}
		
		return numOfTrips;
	}
	
	public int numberOfRoutes(char current, char end, int maxDistance, int currentDistance) {
		int numOfRoutes = 0;
		
		if (currentDistance >= maxDistance) {
			return 0;
		}
		
		
		// DFS the number of routes
		for (EdgePoint edgePoint : edges.get(current)) {
			char destination = edgePoint.destination;
			int distance = edgePoint.distance;
			if (destination == end && currentDistance != 0 &&
					currentDistance + distance < maxDistance) {
				numOfRoutes++;
			}
			numOfRoutes += this.numberOfRoutes(destination, end, maxDistance, currentDistance + distance);
		}
		
		return numOfRoutes;
	}
	
	public void initialiseEdges(String[] distances) {		
		for (String distance : distances) {
			EdgePoint point = new EdgePoint();
			char start = distance.charAt(0);
			point.destination = distance.charAt(1);
			point.distance = Character.getNumericValue(distance.charAt(2));
			
			if (edges.containsKey(start)) {
				edges.get(start).add(point);
			} else {
				ArrayList<EdgePoint> tempArrayList = new ArrayList<EdgePoint>();
				tempArrayList.add(point);
				edges.put(start, tempArrayList);
			}
		}
		
	}
}
