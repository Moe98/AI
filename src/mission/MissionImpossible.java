package mission;

import java.util.Arrays;

import core.Node;
import core.Problem;
import core.State;
import core.Strategy;
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

	public MissionImpossible(String[] operators, State initialState, int n, int m, int c, Location ethanLocation, Location submarineLocation, Soldier[] soldiers) {
		super(operators, initialState);
		this.n = n;
		this.m = m;
		this.c = c;
		this.ethanLocation = ethanLocation;
		this.submarineLocation = submarineLocation;
		this.soldiers = soldiers;
	}

	public boolean goalTest(State state) {
		return state.getSoldiers().isAllRescued() && state.getTruckLoad() == 0
				&& state.getLocation().equals(submarineLocation);
	}

	public State stateAfterDrop(State state) {
		Location location = state.getLocation();
		int truckLoad = state.getTruckLoad();
		SoldiersMap soldiersMap = state.getSoldiers();

		SoldiersMap newSoldiers = new SoldiersMap(soldiersMap.getNumOfSoldiers(), soldiersMap.getBitmap());
		if (location.equals(submarineLocation) && truckLoad > 0)
			truckLoad = 0;
		return new State(Location.getNewLocation(location, "DROP"), truckLoad, newSoldiers);
	}

	public State stateAfterPick(State state) {
		Location location = state.getLocation();
		int truckLoad = state.getTruckLoad();
		SoldiersMap soldiersMap = state.getSoldiers();

		int newTruckLoad = truckLoad;
		SoldiersMap newSoldiersMap = new SoldiersMap(soldiersMap.getNumOfSoldiers(), soldiersMap.getBitmap());

		int soldierIdx = getSoldierIndexAtLocation(location);

		if (soldierIdx != -1 && truckLoad < c && !soldiersMap.isSoldierRescued(soldierIdx)) {
			newTruckLoad++;
			newSoldiersMap.pickupSoldier(soldierIdx);
		}

		return new State(Location.getNewLocation(location, "PICK"), newTruckLoad, newSoldiersMap);
	}

	public State transition(State state, String operator) {
		Location location = state.getLocation();
		int truckLoad = state.getTruckLoad();
		SoldiersMap soldiers = state.getSoldiers();

		Location newLocation;

		switch (operator) {
		case "UP":
			newLocation = Location.getNewLocation(location, "UP");
			if (!Location.locationInBounds(newLocation, n, m))
				newLocation = Location.getNewLocation(location, "DOWN");
			return new State(newLocation, truckLoad, soldiers);
		case "DOWN":
			newLocation = Location.getNewLocation(location, "DOWN");
			if (!Location.locationInBounds(newLocation, n, m))
				newLocation = Location.getNewLocation(location, "UP");
			return new State(newLocation, truckLoad, soldiers);
		case "LEFT":
			newLocation = Location.getNewLocation(location, "LEFT");
			if (!Location.locationInBounds(newLocation, n, m))
				newLocation = Location.getNewLocation(location, "RIGHT");
			return new State(newLocation, truckLoad, soldiers);
		case "RIGHT":
			newLocation = Location.getNewLocation(location, "RIGHT");
			if (!Location.locationInBounds(newLocation, n, m))
				newLocation = Location.getNewLocation(location, "LEFT");
			return new State(newLocation, truckLoad, soldiers);
		case "DROP":
			return stateAfterDrop(state);
		default:
			return stateAfterPick(state);
		}
	}

	public int pathCost(Node node) {
		if (node.getOperator().toString() != "PICK")
			return 0;

		int soldierIdx = getSoldierIndexAtLocation(node.getState().getLocation());

		int cost = node.getPathCost();
		int soldierHealth = 100 - soldiers[soldierIdx].getInitalDamage();
		if (soldierHealth <= node.getDepth() * 2)
			cost += soldierHealth + 10000;
		else
			cost += node.getDepth() * 2;

		return cost;
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

	@Override
	public String toString() {
		return "Problem [n=" + n + ", m=" + m + ", c=" + c + ", initialState=" + initialState + ", operators="
				+ Arrays.toString(operators) + ", ethanLocation=" + ethanLocation + ", submarineLocation="
				+ submarineLocation + ", soldiers=" + Arrays.toString(soldiers) + "]";
	}


	static String genGrid() {
		return MapGenerator.generate();
	}

	static String solve(String grid, Strategy strategy, boolean visualize) {
		MissionImpossible missionImpossibleProblem = MapGenerator.parse(grid);
		System.out.println(missionImpossibleProblem);
		Node goalNode = GeneralSearch.search(missionImpossibleProblem, strategy);
		System.out.println(goalNode);
		String plan = ";";
		String death = ";";
		String health = ";";
		String node = ";";
		return plan + death + health + node;
	}

	
	public static void main(String[] args) {
//		 String grid =
//		 "13,9;4,6;5,7;3,10,4,4,5,9,6,1,8,8,2,12,7,0;34,39,95,64,3,16,88;1";
//		 String grid = "2,2;0,0;1,1;0,1,1,0;1,96;1";
		solve(genGrid(), Strategy.UC, false);
//		 solve(grid, Strategy.BF, false);
	}
}
