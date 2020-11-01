package core;

import java.util.ArrayList;

public class GeneralSearch {
	
	public Node search(Problem problem, Strategy strategy) {
		SearchTree tree = SearchTree.makeTree(strategy);
		tree.push(new Node(null, problem.getInitialState(), null, 0, 0));
		while(!tree.isEmpty()) {
			Node current = tree.pop();
			if(problem.goalTest(current)) return current;
			ArrayList<Node> children = problem.expand(current);
			for(Node node : children)
				if(!tree.isVisited(node))
					tree.push(node);
		}
		return null;
	}
}
