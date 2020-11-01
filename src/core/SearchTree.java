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
	
	boolean isVisited(Node node) {
		return visited.contains(node);
	}
	
	static SearchTree makeTree(Strategy strategy) {
		if(strategy == Strategy.DF)
			return new SearchStack();
		if(strategy == Strategy.BF)
			return new SearchQueue();
		return new SearchPriorityQueue();
	}
}
