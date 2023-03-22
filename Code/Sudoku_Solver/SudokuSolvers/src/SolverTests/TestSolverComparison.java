package SolverTests;

import MMP.MultiObjectiveSolver;
import MMP.Puzzle;
import MMP.RepairBasedSolver;
import MMP.Solver;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class TestSolverComparison {

    Solver solver;

    @Test
    public void testMultiObjSolverFor4x4() throws FileNotFoundException {
        solver = new MultiObjectiveSolver();
        int puzzleSize = 4;
        testStart(puzzleSize,solver);
    }

    @Test
    public void testRepairSolverFor4x4() throws FileNotFoundException {
        int puzzleSize = 4;
        solver = new RepairBasedSolver();
        testStart(puzzleSize,solver);
    }

    @Test
    public void testMultiObjSolverFor9x9() throws FileNotFoundException {
        int puzzleSize = 9;
        solver = new MultiObjectiveSolver();
        testStart(puzzleSize,solver);
    }

    @Test
    public void testRepairSolverFor9x9() throws FileNotFoundException {
        int puzzleSize = 9;
        solver = new RepairBasedSolver();
        testStart(puzzleSize,solver);
    }

    private void testStart(int puzzleSize,Solver solver) throws FileNotFoundException {
        Puzzle currentPuzzle;
        String fileName;
        int[] resultsAverage;
        for (int i = 0; i < 100; i++) {
            fileName = i + "_" + puzzleSize;
            currentPuzzle = solver.loadPopulation(fileName,puzzleSize);
            resultsAverage = new int[2];
            for (int j = 0; j < 30; j++) {
                resultsAverage = solver.getSolution(currentPuzzle);

            }
            resultsAverage[0] = resultsAverage[0]/30;
            resultsAverage[1] = resultsAverage[1]/30;
            System.out.println("Res" + resultsAverage[0]);
        }
    }
}
