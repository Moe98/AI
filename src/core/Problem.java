package core;

import java.util.ArrayList;
import java.util.Arrays;

import data.Location;
import data.Soldier;
import data.SoldiersMap;

public class Problem {
	private int n, m, c;
	private State initialState;
	private Location ethanLocation, submarineLocation;
	private Soldier[] soldiers;

	public Problem(int n, int m, int c, Location ethanLocation, Location submarineLocation, Soldier[] soldiers) {
		super();
		this.n = n;
		this.m = m;
		this.c = c;
		this.initialState = new State(ethanLocation, 0, new SoldiersMap(soldiers.length));
		this.ethanLocation = ethanLocation;
		this.submarineLocation = submarineLocation;
		this.soldiers = soldiers;
	}

	public boolean goalTest(Node node) {
		State nodeState = node.getState();
		return nodeState.getSoldiers().isAllRescued() && nodeState.getTruckLoad() == 0
				&& nodeState.getLocation().equals(submarineLocation);
	}

	public ArrayList<Node> expand(Node node) {
		State nodeState = node.getState();
		ArrayList<Node> children = new ArrayList<>();

		Action[] actions = { Action.DOWN, Action.UP, Action.LEFT, Action.RIGHT };
		int[] dx = { -1, 1, 0, 0 };
		int[] dy = { 0, 0, -1, 1 };
		for (int i = 0; i < 4; i++) {
			int x = nodeState.getLocation().getX() + dx[i];
			int y = nodeState.getLocation().getY() + dy[i];
			if (x >= 0 && x < n && y >= 0 && y < m)
				children.add(
						new Node(node, new State(new Location(x, y), nodeState.getTruckLoad(), nodeState.getSoldiers()),
								actions[i], node.getDepth() + 1, node.getPathCost()));
		}

		if (submarineLocation.equals(nodeState.getLocation()) && nodeState.getTruckLoad() > 0)
			children.add(new Node(node, new State(nodeState.getLocation(), 0, nodeState.getSoldiers()), Action.DROP,
					node.getDepth() + 1, node.getPathCost()));

		int soldierIdx = -1;
		for (int i = 0; i < soldiers.length && soldierIdx == -1; i++) {
			Soldier soldier = soldiers[i];
			if (soldier.getLocation().equals(nodeState.getLocation()))
				soldierIdx = i;
		}
		if (soldierIdx != -1 && nodeState.getTruckLoad() < c && !nodeState.getSoldiers().isSoldierRescued(soldierIdx)) {
			State newState = new State(nodeState.getLocation(), nodeState.getTruckLoad() + 1, nodeState.getSoldiers());
			newState.getSoldiers().pickupSoldier(soldierIdx);
			int cost = node.getPathCost();
			int soldierHealth = 100 - soldiers[soldierIdx].getInitalDamage();
			if (soldierHealth <= node.getDepth() * 2)
				cost += soldierHealth + 10000;
			else
				cost += node.getDepth() * 2;
			children.add(new Node(node, newState, Action.PICK, node.getDepth() + 1, cost));
		}

		return children;
	}

	@Override
	public String toString() {
		return "Problem [n=" + n + ", m=" + m + ", c=" + c + ", initialState=" + initialState + ", ethanLocation="
				+ ethanLocation + ", submarineLocation=" + submarineLocation + ", soldiers=" + Arrays.toString(soldiers)
				+ "]";
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
