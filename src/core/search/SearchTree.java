package core.search;

import java.util.HashSet;

import core.Node;
import core.State;

public abstract class SearchTree {
	protected Object tree;
	protected HashSet<State> visited;

	public SearchTree() {
		visited = new HashSet<>();
	}

	public abstract void push(Node node);

	public abstract Node pop();

	public abstract boolean isEmpty();

	public boolean isVisited(Node node) {
		return visited.contains(node.getState());
	}
	
	public void markVisited(Node node) {
		visited.add(node.getState());
	}

	public static SearchTree makeTree(String strategy) {
		switch (strategy) {
		case "DF":
		case "ID":
			return new SearchStack();
		case "BF":
			return new SearchQueue();
		default:
			return new SearchPriorityQueue();
		}
	}
}
