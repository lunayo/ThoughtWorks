import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
	
	private static String[] distances;
	private static Scanner input;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Start Kiwiland");
		readInput();
		KiwiLand kiwiLand = new KiwiLand(distances);
		System.out.println("Output #1: " + kiwiLand.distanceToString(kiwiLand.distanceOfRoute(new char[]{'A', 'B', 'C'})));
		System.out.println("Output #2: " + kiwiLand.distanceToString(kiwiLand.distanceOfRoute(new char[]{'A', 'D'})));
		System.out.println("Output #3: " + kiwiLand.distanceToString(kiwiLand.distanceOfRoute(new char[]{'A', 'D', 'C'})));
		System.out.println("Output #4: " + kiwiLand.distanceToString(kiwiLand.distanceOfRoute(new char[]{'A', 'E', 'B', 'C', 'D'})));
		System.out.println("Output #5: " + kiwiLand.distanceToString(kiwiLand.distanceOfRoute(new char[]{'A', 'E', 'D'})));
		System.out.println("Output #6: " + kiwiLand.numberOfTrips('C', 'C', 3, 0, false));
		System.out.println("Output #7: " + kiwiLand.numberOfTrips('A', 'C', 4, 0, true));
		System.out.println("Output #8: " + kiwiLand.shortestPath('A', 'C'));
		System.out.println("Output #9: " + kiwiLand.shortestPath('B', 'B'));
		System.out.println("Output #10: " + kiwiLand.numberOfRoutes('C', 'C', 30, 0));
		System.out.println("End Kiwiland");
	}
	
	public static void readInput() {
		
		input = new Scanner(System.in);
		System.out.print("Graph: ");
		// Graph: AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
		List<String> allMatches = new ArrayList<String>();
		Pattern p = Pattern.compile("\\w{2}\\d{1}");
		Matcher m = p.matcher(input.nextLine());
		while (m.find()) {
		   allMatches.add(m.group());
		}
		distances = allMatches.toArray(new String[0]);

	}

}
