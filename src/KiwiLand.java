import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;



public class KiwiLand {
	private int numberOfTowns = 5;
	private int INF = 100000000;
	private Map<Character, ArrayList<EdgePoint>> edges = new HashMap<>();
	
	public KiwiLand() {
		initialiseEdges();
		System.out.print(this.shortestPath('A', 'C'));
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
