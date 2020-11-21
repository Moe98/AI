package core;

public abstract class SearchTreeNode {
	private Node parent;
	private State state;
	private String operator;
	private int depth, pathCost, heuristicCost;

	public SearchTreeNode(Node parent, State state, String operator, int depth) {
		super();
		this.parent = parent;
		this.state = state;
		this.operator = operator;
		this.depth = depth;
		this.pathCost = 0;
		this.heuristicCost = 0;
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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
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

	public int getHeuristicCost() {
		return heuristicCost;
	}

	public void setHeuristicCost(int heuristicCost) {
		this.heuristicCost = heuristicCost;
	}

}
