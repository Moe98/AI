package mission;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

import core.Action;
import core.GeneralSearch;
import core.Node;
import core.Problem;
import core.Strategy;
import core.Usage;
import data.Location;
import data.Soldier;

public class MissionImpossible extends GeneralSearch {
	static StringBuilder grid;
	static final int minGridRange = 5;
	static final int maxGridRange = 15;
	static final int minSoldierCount = 1;
	static final int maxSoldierCount = 10;
	static final int minHealth = 1;
	static final int maxHealth = 99;

	public static int generateNumberWithinRange(int min, int max) {
		return (int) (Math.random() * (max - min + 1)) + min;
	}

	public static int generateNumber(int max) {
		return (int) (Math.random() * max);
	}

	static String genGrid() {
		StringBuilder health = new StringBuilder();
		grid = new StringBuilder();
		int m = generateNumberWithinRange(minGridRange, maxGridRange);
		int n = generateNumberWithinRange(minGridRange, maxGridRange);
		HashSet<Integer> fullCells = new HashSet<Integer>();
		Location ethen = new Location(generateNumber(n), generateNumber(m));
		fullCells.add(ethen.getX() * m + ethen.getY());
		Location submarine;
		while (true) {
			int x = generateNumber(n);
			int y = generateNumber(m);
			int unique = x * m + y;
			if (!fullCells.contains(unique)) {
				submarine = new Location(x, y);
				fullCells.add(unique);
				break;
			}
		}

		int soldiers = generateNumberWithinRange(minSoldierCount, maxSoldierCount);
		int c = generateNumberWithinRange(minSoldierCount, maxSoldierCount);
		append(m, n);
		append(ethen.getX(), ethen.getY());
		append(submarine.getX(), submarine.getY());
		Soldier s;
		for (int i = 0; i < soldiers; i++) {
			while (true) {
				int x = generateNumber(n);
				int y = generateNumber(m);
				int unique = x * m + y;
				if (!fullCells.contains(unique)) {
					s = new Soldier(new Location(x, y), generateNumberWithinRange(minHealth, maxHealth));
					fullCells.add(unique);
					break;
				}
			}

			fullCells.add(s.getLocation().getX() * m + s.getLocation().getY());
			appendSoldier(s.getLocation().getX(), s.getLocation().getY(), i + 1 < soldiers);
			health.append(s.getInitalDamage());
			if (i + 1 < soldiers)
				health.append(",");
		}
		grid.append(";").append(health.toString()).append(";").append(c);
		return grid.toString();
	}

	public static void append(int x, int y) {
		grid.append(x).append(",").append(y).append(";");
	}

	public static void appendSoldier(int x, int y, Boolean comma) {
		grid.append(x).append(",").append(y);
		if (comma)
			grid.append(",");
	}

	static String solve(String grid, Strategy strategy, boolean visualize) {
		StringBuilder solution = new StringBuilder();

		Problem missionImpossibleProblem = parse(grid);
		Node goalNode = search(missionImpossibleProblem, strategy);
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

	private static Problem parse(String grid) {
		String[] splitter = grid.split(";");

		String[] gridSize = splitter[0].split(",");

		int m = Integer.parseInt(gridSize[0]);
		int n = Integer.parseInt(gridSize[1]);

		String[] ethan = splitter[1].split(",");
		Location ethanLocation = new Location(Integer.parseInt(ethan[0]), Integer.parseInt(ethan[1]));

		String[] submarine = splitter[2].split(",");
		Location submarineLocation = new Location(Integer.parseInt(submarine[0]), Integer.parseInt(submarine[1]));

		String[] soldierLocations = splitter[3].split(",");
		String[] soldierHealths = splitter[4].split(",");
		int numOfSoldiers = soldierLocations.length / 2;
		Soldier[] soldiers = new Soldier[numOfSoldiers];

		for (int i = 0; i < 2 * numOfSoldiers; i += 2) {
			Soldier soldier = new Soldier(
					new Location(Integer.parseInt(soldierLocations[i]), Integer.parseInt(soldierLocations[i + 1])),
					Integer.parseInt(soldierHealths[i / 2]));
			soldiers[i / 2] = soldier;
		}

		int truckCapacity = Integer.parseInt(splitter[5]);

		return new Problem(n, m, truckCapacity, ethanLocation, submarineLocation, soldiers);
	}

	public static void main(String[] args) {
		// String grid =
		// "13,9;4,6;5,7;3,10,4,4,5,9,6,1,8,8,2,12,7,0;34,39,95,64,3,16,88;1";
		try {
			Usage usage = new Usage();
			usage.startMeasure();
			String grid = "2,2;0,0;1,1;0,1,1,0;1,96;1";
			System.out.println(solve(grid, Strategy.BF, true));
			usage.endMeasure();
			usage.printResults();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		String grid = "2,2;0,0;1,1;0,1,1,0;1,96;2";
//		System.out.println(solve(grid, Strategy.BF, true));
		// solve(grid, Strategy.BF, false);
	}
}
