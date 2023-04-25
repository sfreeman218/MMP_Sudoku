package MMP;

import java.util.ArrayList;

/**
 * @author Sam David Freeman sdf2@aber.ac.uk
 * @version 0.3
 * Hybrid Repair Solver Class used to take in and solve valid formatted sudoku puzzles
 */
public class RepairBasedSolver extends Solver{

    /**
     * Repairs each puzzle in the population
     */
    public void repairPopulation(ArrayList<Puzzle> pop){
        for (Puzzle p: pop) {
            while (!isPuzzleFeasible(p)) {
                repairPuzzle(p);
            }
        }
    }

    /**
     * For the puzzle, looks at the violations and replaces the most repeated violation that isn't in the starting puzzle with 0
     * @param p Puzzle being repaired
     */
    public void repairPuzzle(Puzzle p){
        ArrayList<int[]> violations = p.sortViolations();
        //ArrayList<int[]> violations = p.countViolations();
        int rowVal,colVal;
        for (int i = violations.size()-1; i >= 0; i--) {
            rowVal = violations.get(i)[0];
            colVal = violations.get(i)[1];
            if (!isSpacePermanent(rowVal,colVal)) {
                p.setSpaceValue(0,rowVal,colVal);

            }
        }
        updateFitness(p);
    }


    /**
     * Takes a puzzle object and tries to find a solution
     * @param puzzle The initial puzzle representation
     * @return The success of the method
     */
    public int[] getSolution(Puzzle puzzle) {
        repairPopulation(population);
        generatePopulation(puzzle);
        int counter = 0;
        if (!isPuzzleComplete()) {
            while (counter < LOOP_SIZE && !isPuzzleComplete()) {
                counter++;
                if (!isPuzzleComplete()) {
                    mutatePopulation();
                }
                repairPopulation(mutatedPopulation);
                splitPopulation();
                System.out.println("Current generation " + counter + "\nBest fitness value = " + population.get(population.size()-1).getPuzzleFitness()[0] + " Worst fitness value = " + population.get(0).getPuzzleFitness()[0]);
            }
        }
        if (isPuzzleComplete()){
            return new int[]{counter};
        }
        else {System.out.println("Program did not find a viable solution"); return null;}
    }

}
