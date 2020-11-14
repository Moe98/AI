package core;

public abstract class SearchProblem {
	private State initialState;
	private String[] operators;

	public SearchProblem(State initialState, String[] operators) {
		super();
		this.initialState = initialState;
		this.operators = operators;
	}
	
	public abstract boolean goalTest(State state);
	
	public abstract int pathCost(Node node);
	
	//state space represented as transition function
	public abstract State transition(State state, String operator);
	
	public State getInitialState() {
		return initialState;
	}

	public void setInitialState(State initialState) {
		this.initialState = initialState;
	}

	public String[] getOperators() {
		return operators;
	}

	public void setOperators(String[] operators) {
		this.operators = operators;
	}

		
}
