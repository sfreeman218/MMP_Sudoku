package MMP;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Sam David Freeman sdf2@aber.ac.uk
 * @version 0.1
 * Solver Class used to take in and solve valid formatted sudoku puzzles
 */

public class  Solver {


    public static void main(String[] args) throws FileNotFoundException {
        Solver solver = new Solver();
        solver.menuLoop();
    }

    public static final int LOOP_SIZE = 100000;
    public static final int POPULATION_SIZE = 10000;
    public static final int MUTATION_RATE = 2;


    private void menuLoop() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        boolean nextPuzzle = true;

        while (nextPuzzle) {
            System.out.println("Sudoku solver:\n1.Choose puzzle to solve\n2.Input own puzzle\n3.Exit system");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> {
                    population = new ArrayList<>();
                    getSolution(loadPopulation(scanner));
                }
                case "2" -> {
                    population = new ArrayList<>();
                    getSolution(manualPuzzlePopulation());
                }
                case "3" -> nextPuzzle = false;
                default -> System.out.println("Invalid input");
            }
        }
    }



    private ArrayList<Puzzle> population = new ArrayList<>();
    private ArrayList<int[]> initialCoordinates;

    public Solver(){

    }

    /**
     * Takes a puzzle object and tries to find a solution
     * @param puzzle The initial puzzle representation
     * @return The success of the method
     */
    public boolean getSolution(Puzzle puzzle) {
        generatePopulation(puzzle);
        int counter = 0;
        boolean puzzleComplete = isPuzzleComplete();
        if (!puzzleComplete) {
            while (counter < LOOP_SIZE && !puzzleComplete) {
                counter++;
                sortPopulation();
                System.out.println("Current generation " + counter + "\nBest fitness value = " + population.get(population.size()-1).getFitness() + " Worst fitness value = " + population.get(0).getFitness());
                splitPopulation();
                repairPopulation();
                puzzleComplete = isPuzzleComplete();
                if (!puzzleComplete) {
                    mutatePopulation();
                }
            }
        }
        if (puzzleComplete){
            return true;
        }
        else {System.out.println("Program did not find a viable solution"); return false;}
    }


    /**
     * Generates the initial population from the given puzzle
     * @param puzzle Puzzle currently being solved
     */
    public void generatePopulation(Puzzle puzzle){
        setInitialState(puzzle);
        puzzle.updateFitness();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            Puzzle newPop = new Puzzle(puzzle);
            mutatePuzzle(newPop);
            newPop.updateFitness();
            population.add(newPop);
        }
    }

    /**
     * Splits the population based on the population size divided by the number of children each mutation generates
     */
    public void splitPopulation(){
        population.subList(0,POPULATION_SIZE/MUTATION_RATE).clear();
    }

    /**
     * Sorts the population based on the fitness value of each puzzle
     */
    public void sortPopulation(){
        population.sort(Comparator.comparing(Puzzle::getFitness));
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
                newPop.updateFitness();
                popAdditions.add(newPop);
            }
            mutatePuzzle(p);
            p.updateFitness();
        }
        population.addAll(popAdditions);
    }

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
     * Mutates puzzle by adding random number to an empty space if one is available or filled space that is not permanent otherwise
     * @param puzzle Puzzle being mutated
     */
    private void mutatePuzzle(Puzzle puzzle) {
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
     * Checks if the puzzle violates any constraints
     * @param puzzle Puzzle being checked
     * @return True if puzzle violates no constraints
     */
    public boolean isPuzzleFeasible(Puzzle puzzle){
        return puzzle.getAllViolations().isEmpty();
    }

    // Look at different coordinate violations, take the highest violation count and remove that from the puzzle

    /**
     * For the puzzle, looks at the violations and replaces the most repeated violation that isn't in the starting puzzle with 0
     * @param p Puzzle being repaired
     */
    public void repairPuzzle(Puzzle p){
        ArrayList<int[]> violations = p.sortViolations();
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

    private Puzzle loadPopulation(Scanner scanner) throws FileNotFoundException {
        System.out.println("Enter name of valid puzzle file");
        int puzzleSize = 2;
        int[] puzzleInput = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        File puzzleDoc = new File(scanner.nextLine());
        if (puzzleDoc.exists()) {
            Scanner fileReader = new Scanner(puzzleDoc);
            ArrayList<Integer> intBuffer = new ArrayList<>();
            while (fileReader.hasNextInt()) {
                intBuffer.add(fileReader.nextInt());
            }
            puzzleSize = intBuffer.size();
            if (intBuffer.contains(Math.sqrt(puzzleSize) + 1)) {

            }
            fileReader.close();
        }
        Puzzle puzzle = new Puzzle(puzzleSize,puzzleInput);
        setInitialState(puzzle);
        return puzzle;
    }


    private Puzzle manualPuzzlePopulation() {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        int[] puzzleInput = new int[size*size];
        for (int i = 0; i < puzzleInput.length; i++) {
            puzzleInput[i] = scanner.nextInt();
        }
        Puzzle puzzle = new Puzzle(size,puzzleInput);
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

