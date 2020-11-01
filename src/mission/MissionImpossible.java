package mission;

import java.util.HashSet;

import data.Location;
import data.Soldier;

public class MissionImpossible {
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

	static void genGrid() {
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
		System.out.println(grid.toString());
		solve(grid.toString(), "", true);
	}

	public static void append(int x, int y) {
		grid.append(x).append(",").append(y).append(";");
	}

	public static void appendSoldier(int x, int y, Boolean comma) {
		grid.append(x).append(",").append(y);
		if (comma)
			grid.append(",");
	}

	public static void main(String[] args) {
		genGrid();
	}

	static String solve(String grid, String stratigy, boolean visualize) {
		parse(grid);
		String plan = ";";
		String death = ";";
		String health = ";";
		String node = ";";

		return plan + death + health + node;
	}

	private static void parse(String grid) {
		String[] splitter = grid.split(";");

		String[] gridSize = splitter[0].split(",");

		int m = Integer.parseInt(gridSize[0]);
		int n = Integer.parseInt(gridSize[1]);
		System.out.println("m: " + m + " n: " + n);

		String[] ethanLocation = splitter[1].split(",");
		Location ethan = new Location(Integer.parseInt(ethanLocation[0]), Integer.parseInt(ethanLocation[1]));
		System.out.println(ethan);

		String[] submarineLocation = splitter[2].split(",");
		Location submarine = new Location(Integer.parseInt(submarineLocation[0]),
				Integer.parseInt(submarineLocation[1]));
		System.out.println(submarine);

		int numOfSoldiers = splitter[3].length();
		Soldier[] soldiers = new Soldier[splitter[3].length()];
		String[] soldierLocations = splitter[3].split(",");
		String[] soldierHealths = splitter[4].split(",");
		for (int i = 0; i < numOfSoldiers; i += 2) {
			Soldier soldier = new Soldier(
					new Location(Integer.parseInt(soldierLocations[i]), Integer.parseInt(soldierLocations[i + 1])),
					Integer.parseInt(soldierHealths[(i + 1) / 2]));
			soldiers[(i+1)/2] = soldier;
		}
		
		System.out.println(soldiers.toString());
		
		int truckCapacity = Integer.parseInt(splitter[5]);
		System.out.println("Truck Capacity: " + truckCapacity);
	}
}
