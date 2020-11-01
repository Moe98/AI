package core;

public class Node {
	private Node parent;
	private State state;
	private Action action;
	private int depth, pathCost;
	
	public Node(Node parent, State state, Action action, int depth, int pathCost) {
		super();
		this.parent = parent;
		this.state = state;
		this.action = action;
		this.depth = depth;
		this.pathCost = pathCost;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getPathCost() {
		return pathCost;
	}

	public void setPathCost(int pathCost) {
		this.pathCost = pathCost;
	}

	@Override
	public String toString() {
		return "Node [parent=" + parent + ", state=" + state + ", action=" + action + ", depth=" + depth + ", pathCost="
				+ pathCost + "]";
	}
}
