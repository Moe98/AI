package core.search;

import java.util.PriorityQueue;

import core.Node;

@SuppressWarnings("unchecked")
public class SearchPriorityQueue extends SearchTree {

	public SearchPriorityQueue() {
		super();
		this.tree = new PriorityQueue<Node>();
	}

	@Override
	public void push(Node node) {
		((PriorityQueue<Node>) this.tree).add(node);
	}

	@Override
	public Node pop() {
		return ((PriorityQueue<Node>) this.tree).poll();
	}

	@Override
	public boolean isEmpty() {
		return ((PriorityQueue<Node>) this.tree).isEmpty();
	}
	
	public boolean isVisited(Node node) {
		if(visited.containsKey(node.getState()))
			return visited.get(node.getState()) <= node.getPathCost();
		return false;
	}
	
	public void markVisited(Node node) {
		visited.put(node.getState(), node.getPathCost());
	}
}
