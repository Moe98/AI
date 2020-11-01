package core;

import java.util.PriorityQueue;

@SuppressWarnings("unchecked")
public class SearchPriorityQueue extends SearchTree {
	
	public SearchPriorityQueue() {
		super();
		this.tree = new PriorityQueue<Node>();
	}

	@Override
	void push(Node node) {
		super.push(node);
		((PriorityQueue<Node>)this.tree).add(node);
	}

	@Override
	Node pop() {
		return ((PriorityQueue<Node>)this.tree).poll();
	}

	@Override
	boolean isEmpty() {
		return ((PriorityQueue<Node>)this.tree).isEmpty();
	}

}
