package core.search;

import java.util.HashMap;

import core.Node;
import core.State;

public abstract class SearchTree {
	protected Object tree;
	protected HashMap<State, Integer> visited;

	public SearchTree() {
		visited = new HashMap<>();
	}

	public abstract void push(Node node);

	public abstract Node pop();

	public abstract boolean isEmpty();

	public abstract boolean isVisited(Node node);
	
	public abstract void markVisited(Node node);

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
