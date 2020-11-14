package core;

public abstract class SearchTreeNode {
	private SearchTreeNode parent;
	private GeneralState state;
	private Operator action;
	private int depth, pathCost;
	
	public SearchTreeNode(SearchTreeNode parent, GeneralState state, Operator action, int depth, int pathCost) {
		super();
		this.parent = parent;
		this.state = state;
		this.action = action;
		this.depth = depth;
		this.pathCost = pathCost;
	}
	
	public SearchTreeNode getParent() {
		return parent;
	}
	
	public void setParent(SearchTreeNode parent) {
		this.parent = parent;
	}
	
	public GeneralState getGeneralState() {
		return state;
	}
	
	public void setGeneralState(GeneralState state) {
		this.state = state;
	}
	
	public Operator getOperator() {
		return action;
	}
	
	public void setOperator(Operator action) {
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
}
