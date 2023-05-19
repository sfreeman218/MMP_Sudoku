package SolverTests;

import MMP.MultiObjectiveSolver;
import MMP.Puzzle;
import MMP.RepairBasedSolver;
import MMP.Solver;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class TestSolverComparison {

    Solver solver;

    public static void main(String[] args) {

        TestSolverComparison t = new TestSolverComparison();


        t.testMultiObjSolverFor9x9Medium();
        t.testMultiObjSolverFor9x9Extreme();
        t.testMultiObjSolverFor9x9Hard();
        t.testRepairSolverFor9x9Medium();
        t.testRepairSolverFor9x9Hard();
        t.testRepairSolverFor9x9Expert();


    }


    public void testMultiObjSolverFor4x4() {
        solver = new MultiObjectiveSolver();
        int puzzleSize = 4;
        testStart(puzzleSize,solver,"" ,"MultObj");
    }


    public void testRepairSolverFor4x4() {
        int puzzleSize = 4;
        solver = new RepairBasedSolver();
        testStart(puzzleSize,solver,"" ,"Repair");
    }


    public void testMultiObjSolverFor9x9Easy() {
        int puzzleSize = 9;
        solver = new MultiObjectiveSolver();
        testStart(puzzleSize,solver,"Easy" ,"MultObj");
    }


    public void testMultiObjSolverFor9x9Medium() {
        int puzzleSize = 9;
        solver = new MultiObjectiveSolver();
        testStart(puzzleSize,solver,"Medium" ,"MultObj");
    }


    public void testMultiObjSolverFor9x9Hard() {
        int puzzleSize = 9;
        solver = new MultiObjectiveSolver();
        testStart(puzzleSize,solver,"Hard" ,"MultObj");
    }


    public void testMultiObjSolverFor9x9Extreme() {
        int puzzleSize = 9;
        solver = new MultiObjectiveSolver();
        testStart(puzzleSize,solver,"Extreme" ,"MultObj");
    }


    public void testRepairSolverFor9x9Easy() {
        int puzzleSize = 9;
        solver = new RepairBasedSolver();
        testStart(puzzleSize,solver,"Easy" ,"Repair");
    }


    public void testRepairSolverFor9x9Medium() {
        int puzzleSize = 9;
        solver = new RepairBasedSolver();
        testStart(puzzleSize,solver,"Medium" ,"Repair");
    }


    public void testRepairSolverFor9x9Hard() {
        int puzzleSize = 9;
        solver = new RepairBasedSolver();
        testStart(puzzleSize,solver,"Hard" ,"Repair");
    }


    public void testRepairSolverFor9x9Expert() {
        int puzzleSize = 9;
        solver = new RepairBasedSolver();
        testStart(puzzleSize,solver,"Expert","Repair");
    }



    private void testStart(int puzzleSize,Solver solver, String difficulty, String method) {
        String resultFile = "C:/Users/Sam/Documents/GitHub/MMP_Sudoku/Results/" + method + "/" + puzzleSize + difficulty;
        try {
            FileWriter myWriter;
            Puzzle currentPuzzle;
            String fileName;
            int[] generations;
            Duration[] times;
            Instant start;
            int failure;
            for (int i = 1; i < 11; i++) {
                myWriter = new FileWriter(resultFile + "/" + puzzleSize + "_"+i+".txt");
                myWriter.write("");
                fileName = "C:/Users/Sam/Documents/GitHub/MMP_Sudoku/Sample_Sudoku_Puzzles/" + puzzleSize + "x" + puzzleSize + "/" + difficulty + "/" + i + ".txt";
                currentPuzzle = solver.loadPopulation(fileName, puzzleSize);
                generations = new int[30];
                times = new Duration[30];
                failure = 0;
                for (int j = 0; j < 30; j++) {
                    start = Instant.now();
                    generations[j] = solver.getSolution(currentPuzzle);
                    times[j] = Duration.between(start, Instant.now());

                    myWriter.append("Try ").append(String.valueOf(j));
                    myWriter.append("\nGenerations:").append(String.valueOf(generations[j]));
                    myWriter.append("\nTime taken: ").append(String.valueOf(times[j].toMillis())).append("ms\n");
                    System.out.println(i + ":" +j);
                }
                myWriter.close();
                int averageTime = 0;
                int averageGeneration = 0;
                int avTiIncFailure = 0;
                for (int k = 0; k < 30; k++) {
                    avTiIncFailure += times[k].toMillis();
                    if (generations[k] !=100000) {
                        averageTime += times[k].toMillis();
                        averageGeneration += generations[k];
                    }
                    else {failure++;}
                }
                myWriter = new FileWriter(resultFile + "/" + puzzleSize + difficulty +".txt",true);
                if (failure != 30){
                avTiIncFailure = avTiIncFailure / 30;
                averageGeneration = averageGeneration / (30-failure);
                averageTime = averageTime / (30-failure);}
                myWriter.write("File " + i +":");
                myWriter.write("\nFailure: " + failure);
                myWriter.write("\nAverage Generations: " + averageGeneration);
                myWriter.write("\nAverage time: " + averageTime + "ms");
                myWriter.write("\nAverage time with failure: " + avTiIncFailure + "ms\n");
                myWriter.close();
            }


        }
        catch (IOException e) {System.out.println("File not found");e.printStackTrace();}


        }
    }
