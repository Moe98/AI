package core.search;

import java.util.HashSet;

import core.Node;
import core.State;
import core.Strategy;

public abstract class SearchTree {
	protected Object tree;
	protected HashSet<State> visited;

	public SearchTree() {
		visited = new HashSet<>();
	}

	public void push(Node node) {
		visited.add(node.getState());
	}

	public abstract Node pop();

	public abstract boolean isEmpty();

	public boolean isVisited(Node node) {
		return visited.contains(node.getState());
	}

	public static SearchTree makeTree(Strategy strategy) {
		if (strategy == Strategy.DF || strategy == Strategy.ID)
			return new SearchStack();
		if (strategy == Strategy.BF)
			return new SearchQueue();
		return new SearchPriorityQueue();
	}
}
