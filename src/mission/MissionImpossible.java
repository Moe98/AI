package mission;

import java.util.Arrays;

import core.Node;
import core.Problem;
import core.State;
import core.search.GeneralSearch;
import data.Location;
import data.Soldier;
import data.SoldiersMap;

public class MissionImpossible extends Problem {

	private int n, m, c;
	private State initialState;
	private Location ethanLocation, submarineLocation;
	private Soldier[] soldiers;
	private String[] operators;
	private final int MAX_STEPS = 51;

	public MissionImpossible(String[] operators, State initialState, int n, int m, int c, Location ethanLocation,
			Location submarineLocation, Soldier[] soldiers) {
		super(operators, initialState);
		this.n = n;
		this.m = m;
		this.c = c;
		this.ethanLocation = ethanLocation;
		this.submarineLocation = submarineLocation;
		this.soldiers = soldiers;
	}

	@Override
	public boolean goalTest(State state) {
		MIState miState = (MIState) state;
		return miState.getSoldiers().isAllRescued() && miState.getTruckLoad() == 0
				&& miState.getLocation().equals(submarineLocation);
	}

	public MIState stateAfterDrop(MIState state) {
		Location location = state.getLocation();
		int truckLoad = state.getTruckLoad();
		SoldiersMap soldiersMap = state.getSoldiers();

		int steps = state.getSteps();

		SoldiersMap newSoldiers = new SoldiersMap(soldiersMap.getNumOfSoldiers(), soldiersMap.getBitmap());
		if (location.equals(submarineLocation) && truckLoad > 0) {
			truckLoad = 0;
			steps = Math.min(steps + 1, MAX_STEPS);
		}
		return new MIState(Location.getNewLocation(location, "drop"), truckLoad, newSoldiers, steps);
	}

	public MIState stateAfterPick(MIState state) {
		Location location = state.getLocation();
		SoldiersMap soldiersMap = state.getSoldiers();

		int truckLoad = state.getTruckLoad();
		int steps = state.getSteps();
		SoldiersMap newSoldiersMap = new SoldiersMap(soldiersMap.getNumOfSoldiers(), soldiersMap.getBitmap());

		int soldierIdx = getSoldierIndexAtLocation(location);

		if (soldierIdx != -1 && truckLoad < c && !soldiersMap.isSoldierRescued(soldierIdx)) {
			truckLoad++;
			steps = Math.min(steps + 1, MAX_STEPS);
			newSoldiersMap.pickupSoldier(soldierIdx);
		}

		return new MIState(Location.getNewLocation(location, "carry"), truckLoad, newSoldiersMap, steps);
	}

	public State transition(State state, String operator) {
		MIState miState = (MIState) state;
		Location location = miState.getLocation();
		int truckLoad = miState.getTruckLoad();
		SoldiersMap soldiers = miState.getSoldiers();

		Location newLocation;
		int newSteps = Math.min(miState.getSteps() + 1, MAX_STEPS);

		switch (operator) {
		case "up":
			newLocation = Location.getNewLocation(location, "up");
			if (!Location.locationInBounds(newLocation, n, m)) {
				newSteps--;
				newLocation = Location.getNewLocation(newLocation, "down");
			}
			return new MIState(newLocation, truckLoad, soldiers, newSteps);
		case "down":
			newLocation = Location.getNewLocation(location, "down");
			if (!Location.locationInBounds(newLocation, n, m)) {
				newSteps--;
				newLocation = Location.getNewLocation(newLocation, "up");
			}
			return new MIState(newLocation, truckLoad, soldiers, newSteps);
		case "left":
			newLocation = Location.getNewLocation(location, "left");
			if (!Location.locationInBounds(newLocation, n, m)) {
				newSteps--;
				newLocation = Location.getNewLocation(newLocation, "right");
			}
			return new MIState(newLocation, truckLoad, soldiers, newSteps);
		case "right":
			newLocation = Location.getNewLocation(location, "right");
			if (!Location.locationInBounds(newLocation, n, m)) {
				newSteps--;
				newLocation = Location.getNewLocation(newLocation, "left");
			}
			return new MIState(newLocation, truckLoad, soldiers, newSteps);
		case "drop":
			return stateAfterDrop(miState);
		default:
			return stateAfterPick(miState);
		}
	}

	public int pathCostIncremental(Node node) {
		MIState state = (MIState) node.getState();
		if (state.getSteps() == MAX_STEPS)
			return 0;
		SoldiersMap soldiersMap = state.getSoldiers();

		int remainingSoldiers = 0;
		int justDied = 0;
		int damageBeforeDeath = 0;
		int isPickedUpSoldierIdx = getSoldierIndexAtLocation(((MIState) node.getState()).getLocation());

		if (!node.getOperator().toString().equals("carry"))
			isPickedUpSoldierIdx = -1;

		for (int i = 0; i < soldiers.length; i++) {
			if (!soldiersMap.isSoldierRescued(i) && i != isPickedUpSoldierIdx) {
				int soldierHealth = 100 - soldiers[i].getInitalDamage();
				int damageCaused = node.getDepth() * 2;
				if (soldierHealth > damageCaused) {
					remainingSoldiers++;
				} else if (soldierHealth == damageCaused) {
					justDied++;
					damageBeforeDeath += 2;
				} else if (soldierHealth == damageCaused - 1) {
					justDied++;
					damageBeforeDeath += 1;
				}
			}
		}
		return remainingSoldiers * 2 + justDied * 10000 + damageBeforeDeath;
	}

	public int pathCost(Node node) {
		MIState state = (MIState) node.getState();
		if (state.getSteps() == MAX_STEPS)
			return 0;
		SoldiersMap soldiersMap = state.getSoldiers();
		int cost = 0;
		int damageCaused = node.getDepth() * 2;
		for (int i = 0; i < soldiers.length; i++) {
			if (soldiersMap.isSoldierRescued(i))
				continue;
			int health = 100 - soldiers[i].getInitalDamage() - damageCaused;
			if (health % 2 == 0) {
				if (health >= 0)
					cost += 2;
				if (health == 0)
					cost += 10000;
			} else if (health % 2 != 0) {
				if (health > 0)
					cost += 2;
				if (health == -1)
					cost += 10000 + 1;
			}
		}
		return cost;
	}

	public int h1(Node node) {
		MIState state = (MIState) node.getState();
		if (state.getSteps() == MAX_STEPS)
			return 0;
		SoldiersMap solidersMap = state.getSoldiers();
		int remainingSoldiers = 0;
		for (int i = 0; i < soldiers.length; i++) {
			int soldierHealth = 100 - soldiers[i].getInitalDamage();
			int damageCaused = node.getDepth() * 2;
			if (!solidersMap.isSoldierRescued(i) && soldierHealth > damageCaused)
				remainingSoldiers++;
		}
		return remainingSoldiers;
	}

	public int h2(Node node) {
		int minDistance = Integer.MAX_VALUE;
		MIState state = (MIState) node.getState();
		SoldiersMap solidersMap = state.getSoldiers();
		Location ethanLocation = state.getLocation();

		for (int i = 0; i < soldiers.length; i++) {
			int soldierHealth = 100 - soldiers[i].getInitalDamage();
			int damageCaused = node.getDepth() * 2;
			if (!solidersMap.isSoldierRescued(i) && soldierHealth > damageCaused) {
				int distanceFromEthan = Location.getManhattanDistance(ethanLocation, soldiers[i].getLocation());
				minDistance = Math.min(minDistance, distanceFromEthan);
			}
		}

		if (minDistance == Integer.MAX_VALUE)
			minDistance = 0;

		return minDistance;
	}

	public int getSoldierIndexAtLocation(Location location) {
		int soldierIdx = -1;
		for (int i = 0; i < soldiers.length && soldierIdx == -1; i++) {
			Soldier soldier = soldiers[i];
			if (soldier.getLocation().equals(location))
				soldierIdx = i;
		}
		return soldierIdx;
	}

	static String genGrid() {
		return MapGenerator.generate();
	}

	public static String solve(String grid, String strategy, boolean visualize) {
		MissionImpossible missionImpossibleProblem = MapGenerator.parse(grid);
		Node goalNode = GeneralSearch.search(missionImpossibleProblem, strategy);
		return Visualizer.visualize(missionImpossibleProblem, goalNode, visualize);
	}

	public int getN() {
		return n;
	}

	public int getM() {
		return m;
	}

	public Location getEthanLocation() {
		return ethanLocation;
	}

	public Location getSubmarineLocation() {
		return submarineLocation;
	}

	public Soldier[] getSoldiers() {
		return soldiers;
	}

	@Override
	public String toString() {
		return "Problem [n=" + n + ", m=" + m + ", c=" + c + ", initialState=" + initialState + ", operators="
				+ Arrays.toString(operators) + ", ethanLocation=" + ethanLocation + ", submarineLocation="
				+ submarineLocation + ", soldiers=" + Arrays.toString(soldiers) + "]";
	}
}
