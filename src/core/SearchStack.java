package core;

import java.util.Stack;

@SuppressWarnings("unchecked")
public class SearchStack extends SearchTree {
	
	public SearchStack() {
		super();
		this.tree = new Stack<Node>();
	}

	@Override
	void push(Node node) {
		super.push(node);
		((Stack<Node>)this.tree).push(node);
	}

	@Override
	Node pop() {
		return ((Stack<Node>)this.tree).pop();
	}

	@Override
	boolean isEmpty() {
		return ((Stack<Node>)this.tree).isEmpty();
	}

}
