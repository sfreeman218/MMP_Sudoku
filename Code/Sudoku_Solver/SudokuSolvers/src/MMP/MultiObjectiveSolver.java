package MMP;

public class MultiObjectiveSolver extends Solver{


    public void updateFitness(Puzzle puzzle){
        int squaresFilled = (puzzle.getPuzzleSize() * puzzle.getPuzzleSize()) - puzzle.getEmptySpaces().size();
        int violations = ((puzzle.getPuzzleSize()*puzzle.getPuzzleSize()) -puzzle.getAllViolations().size());
        puzzle.setPuzzleFitness((squaresFilled+violations)/2);
    }

    public int[] getSolution(Puzzle puzzle){
        generatePopulation(puzzle);
        int counter = 0;
        boolean puzzleComplete = isPuzzleComplete();
        if (!puzzleComplete) {
            while (counter < LOOP_SIZE && !puzzleComplete) {
                counter++;
                sortPopulation();
                System.out.println("Current generation " + counter + "\nBest fitness value = " + population.get(population.size()-1).getPuzzleFitness() + " Worst fitness value = " + population.get(0).getPuzzleFitness());
                splitPopulation();
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
