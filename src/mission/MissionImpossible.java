package mission;

import java.util.HashSet;

import core.GeneralSearch;
import core.Node;
import core.Problem;
import core.Strategy;
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
		Problem missionImpossibleProblem = parse(grid);
		System.out.println(missionImpossibleProblem);
		Node goalNode = search(missionImpossibleProblem, strategy);
		System.out.println(goalNode);
		String plan = ";";
		String death = ";";
		String health = ";";
		String node = ";";
		return plan + death + health + node;
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
		// String grid = "2,2;0,0;1,1;0,1,1,0;1,96;2";
		solve(genGrid(), Strategy.BF, false);
		// solve(grid, Strategy.BF, false);
	}
}
