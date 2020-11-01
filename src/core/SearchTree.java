package core;

import java.util.HashSet;

public abstract class SearchTree {
	protected Object tree;
	protected HashSet<State> visited;
	
	void push(Node node) {
		visited.add(node.getState());
	}
	
	abstract Node pop();
	
	abstract boolean isEmpty();
}
