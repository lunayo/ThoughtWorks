public class EdgePoint {
	int distance;
	char destination;
	
	public EdgePoint() {
		
	}
	
	public EdgePoint(int distance, char destination) {
		this.distance = distance;
		this.destination = destination;
	}
	
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public char getDestination() {
		return destination;
	}
	public void setDestination(char destination) {
		this.destination = destination;
	}
	
}
