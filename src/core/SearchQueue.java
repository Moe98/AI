package core;

import java.util.LinkedList;

@SuppressWarnings("unchecked")
public class SearchQueue extends SearchTree {
	
	public SearchQueue() {
		super();
		this.tree = new LinkedList<Node>();
	}

	@Override
	void push(Node node) {
		super.push(node);
		((LinkedList<Node>)this.tree).push(node);
	}

	@Override
	Node pop() {
		return ((LinkedList<Node>)this.tree).pop();
	}

	@Override
	boolean isEmpty() {
		return ((LinkedList<Node>)this.tree).isEmpty();
	}

}
