package MMP;

import java.util.ArrayList;

/**
 * @author Sam David Freeman sdf2@aber.ac.uk
 * @version 0.2
 * Multi objective solver Class used to take in and solve valid formatted sudoku puzzles, inherits from solver class
 */
public class MultiObjectiveSolver extends Solver{


    public Boolean compareFitness(Puzzle f, Puzzle g){
        return ((f.getPuzzleFitness()[0] >= g.getPuzzleFitness()[0]) && (f.getPuzzleFitness()[1] >= g.getPuzzleFitness()[1]) && ((f.getPuzzleFitness()[0] != g.getPuzzleFitness()[0]) || (f.getPuzzleFitness()[1] != g.getPuzzleFitness()[1])));
    }

    public void updateFitness(Puzzle puzzle){
        int squaresFilled = (puzzle.getPuzzleSize() * puzzle.getPuzzleSize()) - puzzle.getEmptySpaces().size();
        int violations = ((puzzle.getPuzzleSize()*puzzle.getPuzzleSize()) -puzzle.getAllViolations().size());
        puzzle.setPuzzleFitness(new int[] {squaresFilled,violations});
    }

    public int getSolution(Puzzle puzzle){
        generatePopulation(puzzle);
        int counter = 0;

        if (!isPuzzleComplete()) {
            while (counter < LOOP_SIZE && !isPuzzleComplete()) {
                counter++;
                if (!isPuzzleComplete()) {
                    mutatePopulation();
                }
                splitPopulation();

            }
        }
        if (!isPuzzleComplete()) {
            System.out.println("Program did not find a viable solution");
        }
        population.clear();
        return counter;
    }

    public void splitPopulation(){
        for (int i = 0; i < POPULATION_SIZE; i++) {
            if (!compareFitness(population.get(i),mutatedPopulation.get(i))) {
                population.set(i,mutatedPopulation.get(i));
            }
        }
        mutatedPopulation = null;
    }


    /**
     * Mutates each puzzle into 2 new puzzles with random mutations added
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
        }
        mutatedPopulation = popAdditions;
    }






}
