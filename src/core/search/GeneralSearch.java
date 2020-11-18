package core.search;

import java.util.ArrayList;

import core.Node;
import core.Problem;
import core.State;
import core.Strategy;

public class GeneralSearch {

	public static int totalExpanded;

	private static boolean hasDepthLimit;
	private static int depthLimit;

	public static int calculatePathCost(Node a, Node b, Problem problem, Strategy strategy) {
		int g = a.getPathCost() + problem.pathCost(b);
		int h1 = problem.h1(b);
		int h2 = problem.h2(b);
		switch (strategy) {
		case GR1:
			return h1;
		case GR2:
			return h2;
		case AS1:
			return g + h1;
		case AS2:
			return g + h2;
		default:
			return g;
		}
	}

	public static ArrayList<Node> expand(Node currNode, Problem problem, Strategy strategy) {
		State currentState = currNode.getState();
		ArrayList<Node> expandedNodes = new ArrayList<>();
		for (String operator : problem.getOperators()) {
			State nextState = (State) problem.transition(currentState, operator);
			if (nextState.equals(currentState))
				continue;
			Node expandedNode = new Node(currNode, nextState, operator, currNode.getDepth() + 1, 0);
			int pathCost = calculatePathCost(currNode, expandedNode, problem, strategy);
			expandedNode.setPathCost(pathCost);
			expandedNodes.add(expandedNode);
		}
		return expandedNodes;
	}

	private static Node performSearch(Problem problem, Strategy strategy) {
		SearchTree tree = SearchTree.makeTree(strategy);
		tree.push(new Node(null, problem.getInitialState(), null, 0, 0));
		while (!tree.isEmpty()) {
			Node current = tree.pop();
			if (problem.goalTest(current.getState()))
				return current;
			if (hasDepthLimit && current.getDepth() > depthLimit)
				continue;
			ArrayList<Node> children = expand(current, problem, strategy);
			totalExpanded++;
			for (Node node : children)
				if (!tree.isVisited(node))
					tree.push(node);
		}
		return null;
	}

	public static Node search(Problem problem, Strategy strategy) {
		totalExpanded = 0;
		if (strategy == Strategy.ID) {
			hasDepthLimit = true;
			depthLimit = 0;
			while (true) {
				Node goalNode = performSearch(problem, strategy);
				if (goalNode != null)
					return goalNode;
				depthLimit++;
			}
		} else {
			hasDepthLimit = false;
			return performSearch(problem, strategy);
		}
	}
}
