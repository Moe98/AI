package mission;

import java.util.Arrays;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

import core.Action;
import core.GeneralSearch;
import core.Node;
import core.Problem;
import core.State;
import core.Strategy;
import core.search.GeneralSearch;
import core.Usage;
import data.Location;
import data.Soldier;
import data.SoldiersMap;

public class MissionImpossible extends Problem {

	private int n, m, c;
	private State initialState;
	private Location ethanLocation, submarineLocation;
	private Soldier[] soldiers;
	private String[] operators;

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
		MIState miState = (MIState)state;
		return miState.getSoldiers().isAllRescued() && miState.getTruckLoad() == 0
				&& miState.getLocation().equals(submarineLocation);
	}

	public MIState stateAfterDrop(MIState state) {
		Location location = state.getLocation();
		int truckLoad = state.getTruckLoad();
		SoldiersMap soldiersMap = state.getSoldiers();

		SoldiersMap newSoldiers = new SoldiersMap(soldiersMap.getNumOfSoldiers(), soldiersMap.getBitmap());
		if (location.equals(submarineLocation) && truckLoad > 0)
			truckLoad = 0;
		return new MIState(Location.getNewLocation(location, "DROP"), truckLoad, newSoldiers);
	}

	public MIState stateAfterPick(MIState state) {
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

		return new MIState(Location.getNewLocation(location, "PICK"), newTruckLoad, newSoldiersMap);
	}

	public State transition(State state, String operator) {
		MIState miState = (MIState) state;
		
		Location location = miState.getLocation();
		int truckLoad = miState.getTruckLoad();
		SoldiersMap soldiers = miState.getSoldiers();

		Location newLocation;

		switch (operator) {
		case "UP":
			newLocation = Location.getNewLocation(location, "UP");
			if (!Location.locationInBounds(newLocation, n, m))
				newLocation = Location.getNewLocation(location, "DOWN");
			return new MIState(newLocation, truckLoad, soldiers);
		case "DOWN":
			newLocation = Location.getNewLocation(location, "DOWN");
			if (!Location.locationInBounds(newLocation, n, m))
				newLocation = Location.getNewLocation(location, "UP");
			return new MIState(newLocation, truckLoad, soldiers);
		case "LEFT":
			newLocation = Location.getNewLocation(location, "LEFT");
			if (!Location.locationInBounds(newLocation, n, m))
				newLocation = Location.getNewLocation(location, "RIGHT");
			return new MIState(newLocation, truckLoad, soldiers);
		case "RIGHT":
			newLocation = Location.getNewLocation(location, "RIGHT");
			if (!Location.locationInBounds(newLocation, n, m))
				newLocation = Location.getNewLocation(location, "LEFT");
			return new MIState(newLocation, truckLoad, soldiers);
		case "DROP":
			return stateAfterDrop(miState);
		default:
			return stateAfterPick(miState);
		}
	}

	public int pathCost(Node node) {
		if (node.getOperator().toString() != "PICK")
			return 0;

		int soldierIdx = getSoldierIndexAtLocation(((MIState)node.getState()).getLocation());

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

	static String genGrid() {
		return MapGenerator.generate();
	}

	static String solve(String grid, Strategy strategy, boolean visualize) {
		MissionImpossible missionImpossibleProblem = MapGenerator.parse(grid);
		System.out.println(missionImpossibleProblem);
		Node goalNode = GeneralSearch.search(missionImpossibleProblem, strategy);
    return "";
	}
	
	@Override
	public String toString() {
		return "Problem [n=" + n + ", m=" + m + ", c=" + c + ", initialState=" + initialState + ", operators="
				+ Arrays.toString(operators) + ", ethanLocation=" + ethanLocation + ", submarineLocation="
				+ submarineLocation + ", soldiers=" + Arrays.toString(soldiers) + "]";
	}
}

//		// String grid =
//		// "13,9;4,6;5,7;3,10,4,4,5,9,6,1,8,8,2,12,7,0;34,39,95,64,3,16,88;1";
//		try {
//			Usage usage = new Usage();
//			usage.startMeasure();
//			String grid = "2,2;0,0;1,1;0,1,1,0;1,96;1";
//			System.out.println(solve(grid, Strategy.BF, true));
	//		usage.endMeasure();
	//		usage.printResults();
	//	} catch (IOException e) {
			// TODO Auto-generated catch block
	//		e.printStackTrace();
//		}
//
//		String grid = "2,2;0,0;1,1;0,1,1,0;1,96;2";
//		System.out.println(solve(grid, Strategy.BF, true));
		// solve(grid, Strategy.BF, false);
