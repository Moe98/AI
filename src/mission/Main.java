package mission;

import java.io.IOException;

import core.Strategy;
import usage.Usage;

public class Main {

	public static void main(String[] args) {
//		String grid = MissionImpossible.genGrid();
//		System.out.println(grid);
//		String grid = "13,9;4,6;5,7;3,10,4,4,5,9,6,1,8,8,2,12,7,0;34,39,95,64,3,16,88;2";
		String grid = "2,2;0,0;1,1;1,0,0,1;1,96;1";
		try {
			Usage usage = new Usage();
			usage.startMeasure();
			String solution = MissionImpossible.solve(grid, Strategy.DF, true);
			System.out.println(solution);
			usage.endMeasure();
			usage.printResults();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
