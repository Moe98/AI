package core.search;

import java.util.ArrayList;

import core.Node;
import core.Problem;
import core.State;
import core.Strategy;

public class GeneralSearch {
	
	public static int totalExpanded;

	public static ArrayList<Node> expand(Node currNode, Problem problem) {
		State currentState = currNode.getState();
		ArrayList<Node> expandedNodes = new ArrayList<>();
		for (String operator : problem.getOperators()) {
			State nextState = (State) problem.transition(currentState, operator);
			if (nextState.equals(currentState))
				continue;
			Node expandedNode = new Node(currNode, nextState, operator, currNode.getDepth() + 1, 0);
			expandedNode.setPathCost(currNode.getPathCost() + problem.pathCost(expandedNode));
			expandedNodes.add(expandedNode);
		}
		return expandedNodes;
	}

	public static Node search(Problem problem, Strategy strategy) {
		totalExpanded = 0;
		SearchTree tree = SearchTree.makeTree(strategy);
		tree.push(new Node(null, problem.getInitialState(), null, 0, 0));
		while (!tree.isEmpty()) {
			Node current = tree.pop();
			if (problem.goalTest(current.getState()))
				return current;
			ArrayList<Node> children = expand(current, problem);
			totalExpanded++;
			for (Node node : children)
				if (!tree.isVisited(node))
					tree.push(node);
		}
		return null;
	}
}
