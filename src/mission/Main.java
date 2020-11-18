package mission;

import java.io.IOException;

import usage.Usage;

public class Main {

	public static void main(String[] args) {
		while (true) {
			String grid = MissionImpossible.genGrid();
//			System.out.println(grid);
//		String grid = "3,3;1,0;2,2;0,0,1,1,2,0;69,51,57;1";
//		String grid = "13,9;4,6;5,7;3,10,4,4,5,9,6,1,8,8,2,12,7,0;34,39,95,64,3,16,88;1";
//		String grid = "2,2;0,0;1,1;1,0,0,1;96,1;1";
//		String grid = "11,11;7,7;8,8;9,7,7,4,7,6,9,6,9,5,9,1,4,5,3,10,5,10;14,3,96,89,61,22,17,70,83;5";
//		String grid = "15,15;5,10;14,14;0,0,0,1,0,2,0,3,0,4,0,5,0,6,0,7,0,8;81,13,40,38,52,63,66,36,13;1";
//		String grid = "15,15;5,10;14,14;0,0,0,1,0,2,0,3,0,4,0,5,0,6,0,7,0,8;81,13,40,38,52,63,66,36,13;1";
//		String grid = "5,5;3,4;4,4;2,2,3,1,1,1,0,3,3,0,0,2,4,3,2,4;30,19,13,28,47,15,33,67;5";

//		String grid = "5,5;2,1;1,0;1,3,4,2,4,1,3,1;54,31,39,98;2";
//		String grid = "6,6;1,1;3,3;3,5,0,1,2,4,4,3,1,5;4,43,94,40,92;3";
//		String grid = "7,7;1,6;5,4;2,2,1,4,0,3,2,3,0,1,4,5;6,44,82,49,24,54;4";
//		String grid = "8,8;4,2;7,4;5,1,7,7,4,0,6,7;93,85,72,78;1";
//		String grid = "9,9;8,7;5,0;0,8,2,6,5,6,1,7,5,5,8,3,2,2,2,5,0,7;11,13,75,50,56,44,26,77,18;2";
//		String grid = "10,10;6,3;4,8;9,1,2,4,4,0,3,9,6,4,3,4,0,5,1,6,1,9;97,49,25,17,94,3,96,35,98;3";
//		String grid = "11,11;7,7;8,8;9,7,7,4,7,6,9,6,9,5,9,1,4,5,3,10,5,10;14,3,96,89,61,22,17,70,83;5";
//		String grid = "12,12;7,7;10,6;0,4,2,2,1,3,8,2,4,2,9,3;95,4,68,2,94,91;5";
//		String grid = "13,13;7,4;4,0;9,3,3,9,12,7,7,9,3,12,11,8,4,2,12,6;22,62,74,56,43,70,17,14;4";
//		String grid = "14,14;13,9;1,13;5,3,9,7,11,10,8,3,10,7,13,6,11,1,5,2;76,30,2,49,63,43,72,1;6";
//		String grid = "15,15;5,10;14,14;0,0,0,1,0,2,0,3,0,4,0,5,0,6,0,7,0,8;81,13,40,38,52,63,66,36,13;1";

//		String grid = "2,2;0,0;0,1;1,1,1,0;90,93;1";

			try {
				Usage usage = new Usage();
				usage.startMeasure();
				String solutionUCS = MissionImpossible.solve(grid, "UC", false);
				String solutionAS1 = MissionImpossible.solve(grid, "AS1", false);
//				String solutionAS2 = MissionImpossible.solve(grid, "AS2", false);
//			System.out.println();
				String[] damageUC = solutionUCS.split(";");
				String[] damageAS1 = solutionAS1.split(";");
//				String[] damageAS2 = solutionAS2.split(";");
				int sum1 = sumOfDamages(damageUC[2]);
				int sum2 = sumOfDamages(damageAS1[2]);
//				int sum3 = sumOfDamages(damageAS2[2]);
				if (Math.abs(sum1 - sum2) > 1 ) {
					System.out.println(grid);
					Visualizer.visualizeSolution(solutionUCS);
					Visualizer.visualizeSolution(solutionAS1);
//					Visualizer.visualizeSolution(solutionAS2);
					break;
				}

//			System.out.println();
//				System.out.println();
//			Visualizer.visualizeSolution(solutionAS2);
				usage.endMeasure();
//			usage.printResults();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static int sumOfDamages(String str) {
		String[] damages = str.split(",");
		int sum = 0;
		for (String s : damages)
			sum += Integer.parseInt(s);
		return sum;
	}
}
