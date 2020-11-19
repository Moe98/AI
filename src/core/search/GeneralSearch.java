package core.search;

import java.util.ArrayList;

import core.Node;
import core.Problem;
import core.State;

public class GeneralSearch {

	public static int totalExpanded;

	private static boolean hasDepthLimit;
	private static int depthLimit;

	private static int calculatePathCost(Node a, Node b, Problem problem, String strategy) {
		int g = a.getPathCost() + problem.pathCost(b);
		switch (strategy) {
		case "GR1":
		case "GR2":
			return 0;
		default:
			return g;
		}
	}

	private static int calculateHeuristicCost(Node node, Problem problem, String strategy) {
		int h1 = problem.h1(node);
		int h2 = problem.h2(node);
		switch (strategy) {
		case "GR1":
		case "AS1":
			return h1;
		case "AS2":
		case "GR2":
			return h2;
		default:
			return 0;
		}
	}

	public static ArrayList<Node> expand(Node currNode, Problem problem, String strategy) {
		State currentState = currNode.getState();
		ArrayList<Node> expandedNodes = new ArrayList<>();
		for (String operator : problem.getOperators()) {
			State nextState = (State) problem.transition(currentState, operator);
			Node expandedNode = new Node(currNode, nextState, operator, currNode.getDepth() + 1);
			expandedNode.setPathCost(calculatePathCost(currNode, expandedNode, problem, strategy));
			expandedNode.setHeuristicCost(calculateHeuristicCost(expandedNode, problem, strategy));
			expandedNodes.add(expandedNode);
		}
		return expandedNodes;
	}

	private static Node performSearch(Problem problem, String strategy) {
		SearchTree tree = SearchTree.makeTree(strategy);
		Node root = new Node(null, problem.getInitialState(), null, 0);
		tree.push(root);
		while (!tree.isEmpty()) {
			Node current = tree.pop();
			if (problem.goalTest(current.getState()))
				return current;
			if (hasDepthLimit && current.getDepth() == depthLimit)
				continue;
			ArrayList<Node> children = expand(current, problem, strategy);
			totalExpanded++;
			for (Node node : children) {
				if (!tree.isVisited(node)) {
					tree.markVisited(node);
					tree.push(node);
				}
			}
		}
		return null;
	}

	public static Node search(Problem problem, String strategy) {
		totalExpanded = 0;
		if (strategy.equals("ID")) {
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
