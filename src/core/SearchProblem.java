package core;

public abstract class SearchProblem {
	private GeneralState initialState;
	private Operator [] operators;

	public SearchProblem(GeneralState initialState, Operator[] operators) {
		super();
		this.initialState = initialState;
		this.operators = operators;
	}
	
	public abstract boolean goalTest(GeneralState state);
	
	public abstract int pathCost();
	
	//state space represented as transition function
	public abstract GeneralState transition(GeneralState currentGeneralState, Operator action);

	public GeneralState getInitialGeneralState() {
		return initialState;
	}

	public void setInitialGeneralState(GeneralState initialState) {
		this.initialState = initialState;
	}

	public Operator[] getOperators() {
		return operators;
	}

	public void setOperators(Operator[] operators) {
		this.operators = operators;
	}

		
}
