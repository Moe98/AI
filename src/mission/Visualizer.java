package mission;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

import core.Action;
import core.Node;
import core.Problem;
import data.Location;
import data.Soldier;

public class Visualizer {

	static String visualize(Problem missionImpossibleProblem, Node goalNode, boolean visualize) {
		StringBuilder solution = new StringBuilder();
		Node head = goalNode;
		ArrayList<Action> list = new ArrayList<>();
		Stack<Action> stack = new Stack<Action>();
		while (true) {
			if (head == null)
				break;
			list.add(head.getAction());
			stack.push(head.getAction());
			head = head.getParent();
		}

		int n = missionImpossibleProblem.getN();
		int m = missionImpossibleProblem.getM();
		Location ethanLocation = missionImpossibleProblem.getEthanLocation();
		Location submarineLocation = missionImpossibleProblem.getSubmarineLocation();
		Soldier[] soldiers = missionImpossibleProblem.getSoldiers();
		HashSet<Location> set = new HashSet<Location>();

		for (Soldier soldier : soldiers) {
			set.add(soldier.getLocation());
		}

		int truckCapacity = 0;
		int soldierHealths[] = new int[soldiers.length];
		int deathCount = 0;

		for (int i = list.size() - 1; i >= 0; i--) {
			if (list.get(i) == null) {

			} else if (list.get(i) == Action.DROP)
				truckCapacity = 0;
			else if (list.get(i) == Action.PICK) {
				truckCapacity += 1;
				set.remove(ethanLocation);
				// Check that it is - i not - i + 1.
				for (int soldierIdx = 0; soldierIdx < soldiers.length; soldierIdx++) {
					Soldier soldier = soldiers[soldierIdx];
					if (soldier.getLocation().equals(ethanLocation)) {
						soldierHealths[soldierIdx] = soldier.getInitalDamage() + 2 * (list.size() - 1 - i) - 2;
						deathCount += soldierHealths[soldierIdx] >= 100 ? 1 : 0;
					}
				}
			}

			if (list.get(i) != null) {
				switch ((Action) list.get(i)) {
				case RIGHT:
					ethanLocation.setY(ethanLocation.getY() + 1);
					break;
				case LEFT:
					ethanLocation.setY(ethanLocation.getY() - 1);
					break;
				case DOWN:
					ethanLocation.setX(ethanLocation.getX() + 1);
					break;
				case UP:
					ethanLocation.setX(ethanLocation.getX() - 1);
					break;
				default:
					break;
				}
			}
			if (visualize) {
				for (int c = 0; c < m; c++) {
					for (int r = 0; r < n; r++) {
						Location tempLocation = new Location(c, r);
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

		for (int i = list.size() - 2; i >= 0; i--)
			solution.append(list.get(i)).append(i == 0 ? "" : ","); // Plan.
		solution.substring(0, solution.length() - 1);
		solution.append(";");
		solution.append('\n');
		solution.append(deathCount).append(";"); // Death count.
		for (int i = 0; i < soldierHealths.length; i++)
			// Soldier healths at goal state.
			solution.append(Math.min(100, soldierHealths[i])).append(i == soldierHealths.length - 1 ? "" : ",");
		solution.substring(0, solution.length() - 1); // Expanded nodes.
		solution.append(";");
		// ###########################REPLACE WITH ACTUAL
		// EXPANDED###########################
		solution.append(list.size() - 1);
		// ###########################REPLACE WITH ACTUAL
		// EXPANDED###########################
		return solution.toString();
	}

}
