package MMP;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author Sam David Freeman sdf2@aber.ac.uk
 * @version 1.0
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
        //ArrayList<int[]> violations = p.sortViolations();
        ArrayList<int[]> violations = p.countViolations();
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
     *
     * @param puzzle The initial puzzle representation
     * @return Number of generations taken
     */
    public int getSolution(Puzzle puzzle) {
        generatePopulation(puzzle);
        int counter = 0;
        while (counter < LOOP_SIZE && !isPuzzleComplete()) {
            counter++;
            repairPopulation(population);
            sortPopulation(population);
            splitPopulation();
            mutatePopulation();
            //System.out.println("Current generation " + counter + "\nBest fitness value = " + population.get(population.size()-1).getPuzzleFitness()[0] + " Worst fitness value = " + population.get(0).getPuzzleFitness()[0]);
        }

        if (!isPuzzleComplete()) {
            System.out.println("Program did not find a viable solution");
        }
        population.clear();
        return counter;
    }


    /**
     *
     */
    public void mutatePopulation(){
        ArrayList<Puzzle> popAdditions = new ArrayList<>();
        for (Puzzle p: population) {
            for (int i = 0; i < MUTATION_RATE-1; i++) {
                Puzzle newPop = new Puzzle(p);
                mutatePuzzle(newPop);
                updateFitness(newPop);
                popAdditions.add(newPop);
            }
            mutatePuzzle(p);
            updateFitness(p);
        }
        if (population.size() % 2 != 0) {population.add(new Puzzle(population.get(population.size()-1)));}
        population.addAll(popAdditions);
    }

    /**
     *
     */
    public void splitPopulation() {
        population.subList(0, (POPULATION_SIZE - (POPULATION_SIZE / MUTATION_RATE))).clear();
    }

    /**
     *
     * @param pop
     */
    public void sortPopulation(ArrayList<Puzzle> pop){
        pop.sort(Comparator.comparing(a->a.getPuzzleFitness()[0]));
    }

    /**
     *
     * @param puzzle
     */
    public void updateFitness(Puzzle puzzle){
        puzzle.setPuzzleFitness(new int[] {(puzzle.getPuzzleSize() * puzzle.getPuzzleSize()) - puzzle.getEmptySpaces().size()});
    }
}
