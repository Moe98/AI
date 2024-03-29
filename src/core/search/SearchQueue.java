package core.search;

import java.util.LinkedList;

import core.Node;

@SuppressWarnings("unchecked")
public class SearchQueue extends SearchTree {

	public SearchQueue() {
		super();
		this.tree = new LinkedList<Node>();
	}

	@Override
	public void push(Node node) {
		((LinkedList<Node>) this.tree).push(node);
	}

	@Override
	public Node pop() {
		return ((LinkedList<Node>) this.tree).pop();
	}

	@Override
	public boolean isEmpty() {
		return ((LinkedList<Node>) this.tree).isEmpty();
	}
	
	public boolean isVisited(Node node) {
		return visited.containsKey(node.getState());
	}
	
	public void markVisited(Node node) {
		visited.put(node.getState(), 0);
	}

}
