package mission;

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
			if (list.get(i) == null)
				System.out.println("Action : Initial State");
			else
				System.out.println("Action : " + list.get(i));
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
				for (int r = 0; r < n; r++) {
					for (int c = 0; c < m; c++) {
						Location tempLocation = new Location(r, c);
						if (tempLocation.equals(ethanLocation)) {
							System.out.print("E ");
						} else if (tempLocation.equals(submarineLocation)) {
							System.out.print("S ");
						} else if (set.contains(tempLocation)) {
							System.out.print("M ");
						} else {
							System.out.print(". ");
						}
					}
					System.out.println();
				}
				System.out.println("Truck Capacity: " + truckCapacity);
				System.out.println();
			}
		}

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

}
