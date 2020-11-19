package core.search;

import java.util.Stack;

import core.Node;

@SuppressWarnings("unchecked")
public class SearchStack extends SearchTree {

	public SearchStack() {
		super();
		this.tree = new Stack<Node>();
	}

	@Override
	public void push(Node node) {
		((Stack<Node>) this.tree).push(node);
	}

	@Override
	public Node pop() {
		return ((Stack<Node>) this.tree).pop();
	}

	@Override
	public boolean isEmpty() {
		return ((Stack<Node>) this.tree).isEmpty();
	}
	
	public boolean isVisited(Node node) {
		return visited.containsKey(node.getState());
	}
	
	public void markVisited(Node node) {
		visited.put(node.getState(), 0);
	}

}
