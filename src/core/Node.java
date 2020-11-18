package core;

public class Node implements Comparable<Node> {
	private Node parent;
	private State state;
	private String operator;
	private int depth, pathCost, heuristicCost;

	public Node(Node parent, State state, String operator, int depth) {
		super();
		this.parent = parent;
		this.state = state;
		this.operator = operator;
		this.depth = depth;
		this.pathCost = 0;
		this.heuristicCost = 0;
	}

	@Override
	public int compareTo(Node o) {
		return (pathCost + heuristicCost) - (o.pathCost + o.heuristicCost);
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

	public void setAction(String operator) {
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

	@Override
	public String toString() {
		return "Node [parent=" + parent + ", state=" + state + ", operator=" + operator + ", depth=" + depth
				+ ", pathCost=" + pathCost + ", heuristicCost=" + heuristicCost + "]";
	}
}
