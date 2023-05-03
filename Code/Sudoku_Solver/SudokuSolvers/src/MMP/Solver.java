package MMP;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Sam David Freeman sdf2@aber.ac.uk
 * @version 0.3
 * Solver Class used to take in and solve valid formatted sudoku puzzles
 */

public class  Solver {


    public int getSolution(Puzzle p){return 0;}

    public static final int LOOP_SIZE = 100000;
    public static final int POPULATION_SIZE = 100;
    public static final int MUTATION_RATE = 2;



    protected ArrayList<Puzzle> population = new ArrayList<>();
    protected ArrayList<int[]> initialCoordinates;

    protected ArrayList<Puzzle> mutatedPopulation;

    public Solver(){

    }

    /**
     * Generates the initial population from the given puzzle
     * @param puzzle Puzzle currently being solved
     */
    public void generatePopulation(Puzzle puzzle){
        setInitialState(puzzle);
        updateFitness(puzzle);
        for (int i = 0; i < POPULATION_SIZE; i++) {
            Puzzle newPop = new Puzzle(puzzle);
            mutatePuzzle(newPop);
            updateFitness(newPop);
            population.add(newPop);
        }
    }

    /**
     * Splits the population based on the population size divided by the number of children each mutation generates
     */

    public void splitPopulation(){

    }


    /**
     * Sorts the population based on the fitness value of each puzzle
     */
    public void sortPopulation(ArrayList<Puzzle> pop){

    }

    /**
     * Mutates each puzzle into 2 new puzzles with random mutations added
     */
    public void mutatePopulation(){

    }




    /**
     * Mutates puzzle by adding random number to an empty space if one is available or filled space that is not permanent otherwise
     * @param puzzle Puzzle being mutated
     */
    protected void mutatePuzzle(Puzzle puzzle) {
        ArrayList<int[]> emptySpaces = puzzle.getEmptySpaces();
        Random rand = new Random();
        int[] space;
        int value;
        if (!emptySpaces.isEmpty()){
            space = emptySpaces.get(rand.nextInt(emptySpaces.size()));
            value = rand.nextInt(puzzle.getPuzzleSize())+1;
            puzzle.setSpaceValue(value,space[0],space[1]);
        }
        else {
            ArrayList<int[]> changeableSpace = getChangeableSpaces();
            space = changeableSpace.get(rand.nextInt(changeableSpace.size()));
            value = rand.nextInt(puzzle.getPuzzleSize()+1);
            puzzle.setSpaceValue(value,space[0],space[1]);
        }
    }


    /**
     * Updates the fitness value of the puzzle
     */
    public void updateFitness(Puzzle puzzle){
        puzzle.setPuzzleFitness(new int[] {(puzzle.getPuzzleSize() * puzzle.getPuzzleSize()) - puzzle.getEmptySpaces().size()});
    }



    /**
     * Checks if the puzzle violates any constraints
     * @param puzzle Puzzle being checked
     * @return True if puzzle violates no constraints
     */
    public boolean isPuzzleFeasible(Puzzle puzzle){
        return puzzle.getAllViolations().isEmpty();
    }

    // Look at different coordinate violations, take the highest violation count and remove that from the puzzle




    /**
     * Checks if the puzzle is completed based on number of violations and empty spaces
     * @return True is puzzle is complete
     */
    public boolean isPuzzleComplete() {
        for (Puzzle puzzle: population){
            if (puzzle.getAllViolations().isEmpty() && puzzle.getEmptySpaces().isEmpty()){
                printPuzzle(puzzle);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the space is one of the initial values
     * @param row The row number
     * @param column The column number
     * @return True if space is permanent
     */
    public boolean isSpacePermanent(int row, int column) {
        for (int[] i: initialCoordinates) {
            if (i[0] == row && i[1] == column) {return true;}
        }

        return false;
    }

    /**
     * Gets ArrayList of spaces that are changeable
     * @return ArrayList of int[] which are changeable
     */
    public ArrayList<int[]> getChangeableSpaces(){
        ArrayList<int[]> spaces = new ArrayList<>();
        for (int i = 0; i < population.get(0).getPuzzleSize(); i++) {
            for (int j = 0; j < population.get(0).getPuzzleSize(); j++) {
                if (!isSpacePermanent(i,j)){
                    spaces.add(new int[]{i,j});
                }
            }
        }
        return spaces;
    }

    /**
     * Takes initial puzzle and produces the positions of the initial puzzle
     * @param p Puzzle being solved
     */
    public void setInitialState(Puzzle p){
        initialCoordinates = new ArrayList<>();
        for (int i = 0; i < p.getPuzzleSize(); i++) {
            for (int j = 0; j < p.getPuzzleSize(); j++) {
                if (p.getSpaceValue(i,j) != 0) {
                    initialCoordinates.add(new int[]{i,j});
                }
            }
        }
    }

    public Puzzle loadPopulation(String fileName, int puzzleSize) throws FileNotFoundException {

        File puzzleDoc = new File(fileName);
        if (puzzleDoc.exists()) {
            Scanner scanner = new Scanner(puzzleDoc);
            int[] intBuffer = new int[puzzleSize*puzzleSize];
            for (int i = 0; i < (puzzleSize*puzzleSize); i++) {
                intBuffer[i] = scanner.nextInt();
            }
            Puzzle puzzle = new Puzzle(puzzleSize,intBuffer,1);
            setInitialState(puzzle);
            return puzzle;

        }
        return null;
    }


    private Puzzle manualPuzzlePopulation() {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        int[] puzzleInput = new int[size*size];
        for (int i = 0; i < puzzleInput.length; i++) {
            puzzleInput[i] = scanner.nextInt();
        }
        Puzzle puzzle = new Puzzle(size,puzzleInput,1);
        setInitialState(puzzle);
        return puzzle;
    }

    /**
     * Gets the population size
     * @return size of population as int
     */
    public int getPopulationSize(){
        return population.size();
    }

    public void printPuzzle(Puzzle puzzle) {
        System.out.println(puzzle.toString());
    }

}

