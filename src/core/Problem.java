package core;

import java.util.Arrays;

import data.Location;
import data.Soldier;
import data.SoldiersMap;

public class Problem {
	private int n, m, c;
	private State initialState;
	private Location ethanLocation, submarineLocation;
	private Soldier[] soldiers;
	private String[] operators;

	public Problem(int n, int m, int c, Location ethanLocation, Location submarineLocation, Soldier[] soldiers,
			String[] operators) {
		super();
		this.n = n;
		this.m = m;
		this.c = c;
		this.initialState = new State(ethanLocation, 0, new SoldiersMap(soldiers.length));
		this.operators = operators;
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

	public String[] getOperators() {
		return operators;
	}

	public void setOperators(String[] operators) {
		this.operators = operators;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public State getInitialState() {
		return initialState;
	}

	public void setInitialState(State initialState) {
		this.initialState = initialState;
	}

	public Location getEthanLocation() {
		return ethanLocation;
	}

	public void setEthanLocation(Location ethanLocation) {
		this.ethanLocation = ethanLocation;
	}

	public Location getSubmarineLocation() {
		return submarineLocation;
	}

	public void setSubmarineLocation(Location submarineLocation) {
		this.submarineLocation = submarineLocation;
	}

	public Soldier[] getSoldiers() {
		return soldiers;
	}

	public void setSoldiers(Soldier[] soldiers) {
		this.soldiers = soldiers;
	}

}
