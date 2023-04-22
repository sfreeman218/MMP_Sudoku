package MMP;

import java.util.ArrayList;

/**
 * @author Sam David Freeman sdf2@aber.ac.uk
 * @version 0.2
 * Hybrid Repair Solver Class used to take in and solve valid formatted sudoku puzzles
 */
public class RepairBasedSolver extends Solver{

    /**
     * Repairs each puzzle in the population
     */
    public void repairPopulation(){
        for (Puzzle p: population) {
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
    }


    /**
     * Takes a puzzle object and tries to find a solution
     * @param puzzle The initial puzzle representation
     * @return The success of the method
     */
    public int[] getSolution(Puzzle puzzle) {
        generatePopulation(puzzle);
        int counter = 0;
        boolean puzzleComplete = isPuzzleComplete();
        if (!puzzleComplete) {
            while (counter < LOOP_SIZE && !puzzleComplete) {
                counter++;
                sortPopulation();
                System.out.println("Current generation " + counter + "\nBest fitness value = " + population.get(population.size()-1).getPuzzleFitness() + " Worst fitness value = " + population.get(0).getPuzzleFitness());
                splitPopulation();
                repairPopulation();
                puzzleComplete = isPuzzleComplete();
                if (!puzzleComplete) {
                    mutatePopulation();
                }
            }
        }
        if (puzzleComplete){
            return new int[]{counter};
        }
        else {System.out.println("Program did not find a viable solution"); return null;}
    }

}
