package MMP;

/**
 * @author Sam David Freeman sdf2@aber.ac.uk
 * @version 0.2
 * Multi objective solver Class used to take in and solve valid formatted sudoku puzzles, inherits from solver class
 */
public class MultiObjectiveSolver extends Solver{


    public void sortPopulation(){

    }

    public Boolean compareFitness(Puzzle f, Puzzle g){
        return ((f.getPuzzleFitness()[0] >= g.getPuzzleFitness()[0]) && (f.getPuzzleFitness()[1] >= g.getPuzzleFitness()[1]) && ((f.getPuzzleFitness()[0] != g.getPuzzleFitness()[0]) || (f.getPuzzleFitness()[1] != g.getPuzzleFitness()[1])));
    }

    public void updateFitness(Puzzle puzzle){
        int squaresFilled = (puzzle.getPuzzleSize() * puzzle.getPuzzleSize()) - puzzle.getEmptySpaces().size();
        int violations = ((puzzle.getPuzzleSize()*puzzle.getPuzzleSize()) -puzzle.getAllViolations().size());
        puzzle.setPuzzleFitness(new int[] {squaresFilled,violations});
    }

    public int[] getSolution(Puzzle puzzle){
        generatePopulation(puzzle);
        int counter = 0;

        if (!isPuzzleComplete()) {
            while (counter < LOOP_SIZE && !isPuzzleComplete()) {
                counter++;
                if (!isPuzzleComplete()) {
                    mutatePopulation();
                }
                splitPopulation();
                System.out.println("Current generation " + counter + "\nBest fitness value = " + population.get(population.size()-1).getPuzzleFitness()[0] + "," + population.get(population.size()-1).getPuzzleFitness()[1] + " Worst fitness value = " + + population.get(0).getPuzzleFitness()[0] + "," + population.get(0).getPuzzleFitness()[1]);

            }
        }
        if (isPuzzleComplete()){
            return new int[]{counter};
        }
        else {System.out.println("Program did not find a viable solution"); return null;}
    }

    public void splitPopulation(){
        for (int i = 0; i < POPULATION_SIZE; i++) {
            if (!compareFitness(population.get(i),mutatedPopulation.get(i))) {
                population.set(i,mutatedPopulation.get(i));
            }
        }
        mutatedPopulation = null;
        sortPopulation();
    }
}
