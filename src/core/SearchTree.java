package core;

import java.util.HashSet;

public abstract class SearchTree {
	protected Object tree;
	//Hashing does not work
	protected HashSet<State> visited;
	
	public SearchTree() {
		visited = new HashSet<>();
	}
	
	void push(Node node) {
		visited.add(node.getState());
	}
	
	abstract Node pop();
	
	abstract boolean isEmpty();
	
	boolean isVisited(Node node) {
		return visited.contains(node.getState());
	}
	
	static SearchTree makeTree(Strategy strategy) {
		if(strategy == Strategy.DF)
			return new SearchStack();
		if(strategy == Strategy.BF)
			return new SearchQueue();
		return new SearchPriorityQueue();
	}
}
