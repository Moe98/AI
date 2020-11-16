package mission;

import core.Strategy;

public class Main {

	public static void main(String[] args) {
		String randomGrid = MissionImpossible.genGrid();
		MissionImpossible.solve(randomGrid, Strategy.UC, false);
//		 String grid =
//		 "13,9;4,6;5,7;3,10,4,4,5,9,6,1,8,8,2,12,7,0;34,39,95,64,3,16,88;1";
//		 String grid = "2,2;0,0;1,1;0,1,1,0;1,96;1";
//		 solve(grid, Strategy.BF, false);
	}
}
