package core;

public class Node implements Comparable<Node>{
	private Node parent;
	private State state;
	private String operator;
	private int depth, pathCost;
	
	public Node(Node parent, State state, String operator, int depth, int pathCost) {
		super();
		this.parent = parent;
		this.state = state;
		this.operator = operator;
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

	@Override
	public String toString() {
		return "Node [parent=" + parent + ", state=" + state + ", operator=" + operator + ", depth=" + depth + ", pathCost="
				+ pathCost + "]";
	}

	@Override
	public int compareTo(Node o) {
		return pathCost - o.pathCost;
	}
}
