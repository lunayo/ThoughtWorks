import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;



public class KiwiLand {
	private int numberOfTowns = 5;
	private Map<Character, ArrayList<EdgePoint>> edges = new HashMap<>();
	private PriorityQueue<EdgePoint> priorityQueue;
	
	public KiwiLand() {
		initialiseEdges();
		System.out.print(this.numberOfTrips('C', 'C', 3, 0));
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
	
	public int numberOfTrips(char current, char end, int maxStop, int currentLevel) {
		
		int numOfTrips = 0;
		
		if (currentLevel > maxStop) {
			return 0;
		}
		if (current == end && currentLevel != 0) {
			return 1;
		}
		
		// DFS the number of trips that less than maxstop
		// get the list of destination
		for (EdgePoint edgePoint : edges.get(current)) {
			char destination = edgePoint.destination;
			numOfTrips += this.numberOfTrips(destination, end, maxStop, currentLevel + 1);
		}
		
		return numOfTrips;
	}
	
	public PriorityQueue<EdgePoint> getPriorityQueue() {
		// Lazy loading
		if (priorityQueue == null) {
			priorityQueue = new PriorityQueue<EdgePoint>(10, new Comparator<EdgePoint>() {
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
		}
		
		return priorityQueue;
	}
	
	public void initialiseEdges() {
		String[] distances = {"AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"};
		
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
