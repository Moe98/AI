package mission;

import java.util.HashSet;
import core.State;
import data.Location;
import data.Soldier;
import data.SoldiersMap;

public class MapGenerator {
	private static StringBuilder grid;
	private static final int minGridRange = 5;
	private static final int maxGridRange = 15;
	private static final int minSoldierCount = 1;
	private static final int maxSoldierCount = 10;
	private static final int minHealth = 1;
	private static final int maxHealth = 99;

	private static int generateNumberWithinRange(int min, int max) {
		return (int) (Math.random() * (max - min + 1)) + min;
	}

	private static int generateNumber(int max) {
		return (int) (Math.random() * max);
	}

	public static String generate() {
		StringBuilder health = new StringBuilder();
		grid = new StringBuilder();
		int m = generateNumberWithinRange(minGridRange, maxGridRange);
		int n = generateNumberWithinRange(minGridRange, maxGridRange);
		HashSet<Integer> fullCells = new HashSet<Integer>();
		Location ethan = new Location(generateNumber(n), generateNumber(m));
		fullCells.add(ethan.getX() * m + ethan.getY());
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
		append(ethan.getX(), ethan.getY());
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

	private static void append(int x, int y) {
		grid.append(x).append(",").append(y).append(";");
	}

	private static void appendSoldier(int x, int y, Boolean comma) {
		grid.append(x).append(",").append(y);
		if (comma)
			grid.append(",");
	}

	public static MissionImpossible parse(String grid) {
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

		String[] operators = { "UP", "DOWN", "LEFT", "RIGHT", "DROP", "PICK" };
		State intialState = new MIState(ethanLocation, 0, new SoldiersMap(numOfSoldiers));

		return new MissionImpossible(operators, intialState, n, m, truckCapacity, ethanLocation, submarineLocation,
				soldiers);
	}

}
