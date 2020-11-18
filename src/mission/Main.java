package mission;

import java.io.IOException;

import core.Strategy;
import usage.Usage;

public class Main {

	public static void main(String[] args) {
//		String grid = MissionImpossible.genGrid();
//		System.out.println(grid);
//		String grid = "13,9;4,6;5,7;3,10,4,4,5,9,6,1,8,8,2,12,7,0;34,39,95,64,3,16,88;1";
//		String grid = "2,2;0,0;1,1;1,0,0,1;96,1;1";
//		String grid = "11,11;7,7;8,8;9,7,7,4,7,6,9,6,9,5,9,1,4,5,3,10,5,10;14,3,96,89,61,22,17,70,83;5";
		String grid = "15,15;5,10;14,14;0,0,0,1,0,2,0,3,0,4,0,5,0,6,0,7,0,8;81,13,40,38,52,63,66,36,13;1";
		try {
			Usage usage = new Usage();
			usage.startMeasure();
			String solutionUCS = MissionImpossible.solve(grid, Strategy.UC, false);
			String solutionAS1 = MissionImpossible.solve(grid, Strategy.AS1, false);
			String solutionAS2 = MissionImpossible.solve(grid, Strategy.AS2, false);
			System.out.println();
			System.out.println(
					solutionUCS.split(";")[1] + " " + solutionUCS.split(";")[2] + " " + solutionUCS.split(";")[3]);
			System.out.println(
					solutionAS1.split(";")[1] + " " + solutionAS1.split(";")[2] + " " + solutionAS1.split(";")[3]);
			System.out.println(
					solutionAS2.split(";")[1] + " " + solutionAS2.split(";")[2] + " " + solutionAS2.split(";")[3]);
			usage.endMeasure();
//			usage.printResults();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
