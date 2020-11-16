package core;

public abstract class Problem {
	private String[] operators;
	private State initialState;

	public Problem(String[] operators, State initialState) {
		super();
		this.operators = operators;
		this.initialState = initialState;

  public abstract boolean goalTest(State state);

	public abstract int pathCost(Node node);

	// state space represented as transition function
	public abstract State transition(State state, String operator);

	public State getInitialState() {
		return initialState;
	}

	public String[] getOperators() {
		return operators;
	}

}
