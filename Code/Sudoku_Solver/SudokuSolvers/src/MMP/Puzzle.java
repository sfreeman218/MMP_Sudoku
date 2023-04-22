package MMP;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author Sam David Freeman sdf2@aber.ac.uk
 * @version 0.1
 * Puzzle Class used to represent sudoku puzzles
 */

public class Puzzle {

   private int[][] puzzleSpace;
   private final int puzzleSize;
   private final int gridSize;
   private int[] puzzleFitness;


   /**
    * Creates the puzzle
    * @param size The length or width of the puzzle
    * @param numbers The starting numbers of the puzzle
    */
   public Puzzle(int size, int[] numbers, int numOfObjectives){
      puzzleSize = size;
      puzzleSpace = new int[size][size];
      puzzleFitness = new int[numOfObjectives];
      int total = 0;
      for (int i=0; i < puzzleSize; i++){
         for (int j = 0; j < puzzleSize; j++) {
            puzzleSpace[i][j] = numbers[total];
            total++;
         }
      }
      gridSize = (int) Math.sqrt(puzzleSize);
   }

   /**
    * Creates puzzle using another puzzles information
    * @param initialPuzzle Puzzle being copied
    */
   public Puzzle(Puzzle initialPuzzle){
      this.puzzleSize = initialPuzzle.getPuzzleSize();
      this.puzzleSpace = new int[puzzleSize][puzzleSize];
      for (int i = 0; i < puzzleSize; i++) {
         for (int j = 0; j < puzzleSize; j++) {
            puzzleSpace[i][j] = initialPuzzle.getPuzzleSpace()[i][j];
         }
      }
      puzzleFitness = initialPuzzle.getPuzzleFitness();
      this.gridSize = initialPuzzle.gridSize;
   }

   /**
    * Retrieves a specified row of the puzzle
    * @param rowNum The row number
    * @return The specified row as an array of ints
    */
   public int[] getRow(int rowNum){
      if (rowNum > puzzleSize || rowNum < 0){return null;}
      int[] row = new int[puzzleSize];
      for (int i = 0; i < puzzleSize; i++){
         row[i] = puzzleSpace[rowNum][i];
      }
      return row;
   }

   /**
    * Retrieves a specified column of the puzzle
    * @param columnNum The column number
    * @return The specified column as an array of ints
    */
   public int[] getColumn(int columnNum){
      if (columnNum > puzzleSize || columnNum < 0){return null;}
      int[] column = new int[puzzleSize];
      for (int i = 0; i < puzzleSize; i++) {
         column[i] = puzzleSpace[i][columnNum];
      }
      return column;
   }

   /**
    * Retrieves a grid of the puzzle
    * @param gridRow Where the grid starts
    * @param gridColumn Where the grid ends
    * @return A grid as a 2d array of ints
    */
   public int[][] getGrid(int gridRow, int gridColumn){
      if (gridRow > puzzleSize-gridSize || gridColumn > puzzleSize-gridSize || gridRow < 0 || gridColumn < 0){return null;}
      int[][] grid = new int[gridSize][gridSize];

      for (int i = 0; i < gridSize; i++) {
         for (int j = 0; j < gridSize; j++) {
            grid[i][j] = puzzleSpace[i+gridColumn][j+gridRow];
         }
      }
      return grid;
   }

   /**
    * Finds the constraint violations in a specified row
    * @param rowNum The row number
    * @return The array values where constraint violations occur
    */
   public ArrayList<int[]> getRowViolations(int rowNum){
      int[] row = getRow(rowNum);
      if (row == null){return null;}
      ArrayList<int[]> violationCoordinates = new ArrayList<>();
      for (int i = 0; i < row.length; i++) {
         for (int j = 0; j < row.length; j++) {
            if (((row[i] == row[j]) && (i != j)) && (row[i] != 0)) {
               violationCoordinates.add(new int[]{rowNum,j});
            }
         }
      }
      return violationCoordinates;
   }

   /**
    * Finds the constraint violations in a specified column
    * @param colNum The column number
    * @return The array values where constraint violations occur
    */
   public ArrayList<int[]> getColumnViolations(int colNum){
      int[] column = getColumn(colNum);
      if (column == null){return null;}
      ArrayList<int[]> violationCoordinates = new ArrayList<>();
      for (int i = 0; i < column.length; i++) {
         for (int j = 0; j < column.length; j++) {
            if ((column[i] == column[j] && i != j) && column[i] != 0) {
               violationCoordinates.add(new int[]{j,colNum});
            }
         }
      }
      return violationCoordinates;
   }

   /**
    * Finds the constraint violations in a specified grid
    * @param gridRow The starting row of the grid
    * @param gridCol The starting column of the grid
    * @return The array values where constraint violations occur
    */
   public ArrayList<int[]> getGridViolations(int gridRow, int gridCol){
      int[][] grid =  getGrid(gridRow,gridCol);
      if (grid == null){return null;}
      ArrayList<int[]> violationCoordinates = new ArrayList<>();
      for (int i = 0; i < gridSize; i++) {
         for (int j = 0; j < gridSize; j++) {
            for (int k = 0; k < gridSize; k++) {
               for (int l = 0; l < gridSize; l++) {
                  if ((grid[k][l] == grid[i][j] && !(i == k && j == l) && grid[k][l] != 0)) {
                     violationCoordinates.add(new int[]{k+gridCol,l+gridRow});

                  }
               }
            }
         }
      }
      return violationCoordinates;
   }

   /**
    * Gets the size of each side of the puzzle
    * @return Size of the puzzle as an int
    */
   public int getPuzzleSize(){
      return puzzleSize;
   }

   /**
    * Gets the size of the grids
    * @return Size of the grid as an int
    */
   public int getGridSize(){return gridSize;}


   /**
    * Gets the current state of the puzzle
    * @return The current puzzle as a 2D int array
    */
   public int[][] getPuzzleSpace(){return puzzleSpace;}

   /**
    * Changes a space in the puzzle
    * @param newValue The new value for the space
    * @param row The first value of the array
    * @param column The second value of the array
    * @return Whether the space was successfully changed
    */
   public boolean setSpaceValue(int newValue, int row,int column){
      if (newValue >= 0 && newValue <= puzzleSize){
         puzzleSpace[row][column] = newValue;
         return true;
      }
      return false;
   }

   /**
    * Gets value from a specific space
    * @param row array row location
    * @param column array column location
    * @return The space value as an int
    */
   public int getSpaceValue(int row, int column){
      return puzzleSpace[row][column];
   }

   /**
    * Gets all constraint violations in the puzzle
    * @return An ArrayList of violation coordinates
    */
   public ArrayList<int[]> getAllViolations(){
      ArrayList<int[]> violationList = new ArrayList<>();
      for (int i = 0; i < puzzleSize; i++) {
         violationList.addAll(getRowViolations(i));
      }
      for (int i = 0; i < puzzleSize; i++) {
         violationList.addAll(getColumnViolations(i));
      }

      for (int i = 0; i < puzzleSize; i+= gridSize) {
         for (int j = 0; j < puzzleSize; j+= gridSize) {
            violationList.addAll(getGridViolations(i,j));
         }
      }

      return violationList;
   }


   /**
    * Counts the number of violations for each coordinate
    * @return An ArrayList of coordinates with their violation totals
    */
   public ArrayList<int[]> countViolations(){
      int location;
      ArrayList<int[]> violationArray = getAllViolations();
      ArrayList<int[]> totalViolationsArray = new ArrayList<>();
      for (int[] value:violationArray) {
         location = doesViolationExist(value,totalViolationsArray);
         if (location == -1){
            totalViolationsArray.add(new int[]{value[0],value[1],1});
         }
         else {
            int total = totalViolationsArray.get(location)[2];
            totalViolationsArray.set(location,new int[]{value[0],value[1],total+1});
         }

      }
      return totalViolationsArray;
   }

   /**
    * Sorts the violations by the total number of occurrences
    * @return Sorted ArrayList of int[] of violations
    */
   public ArrayList<int[]> sortViolations(){
      ArrayList<int[]> violationArray = countViolations();
      violationArray.sort(Comparator.comparing(a->a[2]));
      return violationArray;
   }

   private int doesViolationExist(int[] current, ArrayList<int[]> total){
      if (total.isEmpty()){return -1;}
      for (int i = 0; i < total.size(); i++) {
         if (total.get(i)[0] == current[0] && total.get(i)[1] == current[1]){
            return i;
         }
      }
      return -1;
   }

   /**
    * Finds if a space is empty
    * @param row Row number
    * @param column Column number
    * @return True is the space is needed, otherwise false
    */
   public boolean isSpaceEmpty(int row,int column){
      return puzzleSpace[row][column] == 0;
   }



   /**
    * Finds all empty spaces
    * @return ArrayList of empty spaces locations
    */
   public ArrayList<int[]> getEmptySpaces(){
      ArrayList<int[]> spaces = new ArrayList<>();
      for (int i = 0; i < puzzleSize; i++) {
         for (int j = 0; j < puzzleSize; j++) {
            if (isSpaceEmpty(i,j)){
               spaces.add(new int[]{i,j});
            }
         }
      }
      return spaces;
   }

   /**
    * Sets fitness value
    * @param fitness new value for the fitness
    */
   public void setPuzzleFitness(int[] fitness){
      puzzleFitness = fitness;
   }

   /**
    * Gets the puzzles fitness
    * @return Int value representing the fitness
    */
   public int[] getPuzzleFitness(){
      return puzzleFitness;
   }

   @Override
   public String toString() {
      StringBuilder stringBuilder = new StringBuilder();

      for (int i = 0; i < puzzleSize; i++) {
         for (int j = 0; j < puzzleSize; j++) {
            stringBuilder.append(puzzleSpace[i][j]).append(" ");
         }
         stringBuilder.append("\n");
      }
      return stringBuilder.toString();
   }
}
