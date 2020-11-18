package mission;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

import core.Node;
import core.search.GeneralSearch;
import data.Location;
import data.Soldier;

public class Visualizer {

	static String visualize(MissionImpossible problem, Node goalNode, boolean visualizeSolutionGrids) {
		StringBuilder solution = new StringBuilder();
		Node head = goalNode;
		ArrayList<String> list = new ArrayList<>();
		Stack<String> stack = new Stack<>();
		PrintWriter printWriter = new PrintWriter(System.out);
		while (true) {
			if (head == null)
				break;
			list.add(head.getOperator());
			stack.push(head.getOperator());
			head = head.getParent();
		}

		int n = problem.getN();
		int m = problem.getM();
		Location ethanLocation = problem.getEthanLocation();
		Location submarineLocation = problem.getSubmarineLocation();
		Soldier[] soldiers = problem.getSoldiers();
		HashSet<Location> set = new HashSet<Location>();

		for (Soldier soldier : soldiers)
			set.add(soldier.getLocation());

		int truckCapacity = 0;
		int soldierHealths[] = new int[soldiers.length];
		int deathCount = 0;
		for (int i = list.size() - 1; i >= 0; i--) {
			if (visualizeSolutionGrids) {
				if (list.get(i) == null)
					printWriter.println("Action : Initial State");
				else
					printWriter.println("Action : " + list.get(i));
			}
			if (list.get(i) == "DROP") {
				truckCapacity = 0;
			} else if (list.get(i) == "PICK") {
				truckCapacity += 1;
				set.remove(ethanLocation);
				// Check that it is - i not - i + 1.
				for (int soldierIdx = 0; soldierIdx < soldiers.length; soldierIdx++) {
					Soldier soldier = soldiers[soldierIdx];
					if (soldier.getLocation().equals(ethanLocation)) {
						// Does the IMF soldier gain 2 more damage points while I pick them, or do they
						// stop bleeding once I reach their cell?
						soldierHealths[soldierIdx] = soldier.getInitalDamage() + 2 * (list.size() - 1 - i) - 2;
						deathCount += soldierHealths[soldierIdx] >= 100 ? 1 : 0;
					}
				}
			}

			if (list.get(i) != null)
				ethanLocation = Location.getNewLocation(ethanLocation, list.get(i));

			if (visualizeSolutionGrids) {
				System.err.println("wrong!");
				for (int r = 0; r < n; r++) {
					for (int c = 0; c < m; c++) {
						Location tempLocation = new Location(r, c);
						if (tempLocation.equals(ethanLocation)) {
							printWriter.print("E ");
						} else if (tempLocation.equals(submarineLocation)) {
							printWriter.print("S ");
						} else if (set.contains(tempLocation)) {
							printWriter.print("M ");
						} else {
							printWriter.print(". ");
						}
					}
					printWriter.println();
				}
				printWriter.println("Truck Capacity: " + truckCapacity);
				printWriter.println();
			}
		}

		printWriter.flush();

		// Plan.
		for (int i = list.size() - 2; i >= 0; i--)
			solution.append(list.get(i)).append(i == 0 ? ";" : ",");

		// Death count.
		solution.append(deathCount).append(";");

		// Soldiers health at goal state.
		for (int i = 0; i < soldierHealths.length; i++)
			solution.append(Math.min(100, soldierHealths[i])).append(i == soldierHealths.length - 1 ? ";" : ",");

		// Expanded nodes.
		solution.append(GeneralSearch.totalExpanded);
		return solution.toString();
	}

	private static int sumOfDamages(String str) {
		String[] damages = str.split(",");
		int sum = 0;
		for (String s : damages)
			sum += Integer.parseInt(s);
		return sum;
	}

	static void visualizeSolution(String solution) {
		String[] tokens = solution.split(";");
		System.out.println("Moves: " + tokens[0]);
		System.out.println(
				"Deaths: " + tokens[1] + " Damages: " + sumOfDamages(tokens[2]) + " ExpandedNodes: " + tokens[3]);
	}

}
