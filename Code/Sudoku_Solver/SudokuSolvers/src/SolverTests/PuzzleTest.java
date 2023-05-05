package SolverTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import MMP.Puzzle;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PuzzleTest {
    public Puzzle p;
    @BeforeEach
    void setPuzzle(){
        int[] examplePuzzle = {0,1,1,0,0,1,0,0,0,0,0,0,0,1,0,0};
        p = new Puzzle(4,examplePuzzle,2);
    }

    @Test
    public void isGetPuzzleRowValid(){
        int[] validRow = {0,1,1,0};
        assertArrayEquals(validRow, p.getRow(0));
    }

    @Test
    public void isGetPuzzleRowInvalid(){
        assertNull(p.getRow(5));
        assertNull(p.getRow(-1));
    }

    @Test
    public void isGetPuzzleColValid(){
        int[] validCol = {1,1,0,1};
        assertArrayEquals(validCol, p.getColumn(1));
    }
    @Test
    public void isGetPuzzleColInvalid(){
        assertNull(p.getColumn(5));
        assertNull(p.getColumn(-1));
    }

    @Test
    public void getPuzzleGrid(){
        int[][] grid = {{0,1},{0,1}};
        assertArrayEquals(grid,p.getGrid(0,0));
    }

    @Test
    public void isGetPuzzleGridInvalid(){
        assertNull(p.getGrid(0,3));
        assertNull(p.getGrid(-1,2));
        assertNotNull(p.getGrid(0,2));
    }

    @Test
    public void getCorrectRowViolation(){
        ArrayList<int[]> violationCoordinates = new ArrayList<>();
        violationCoordinates.add(new int[]{0,2});
        violationCoordinates.add(new int[]{0,1});
        assertArrayEquals(violationCoordinates.toArray(),p.getRowViolations(0).toArray());
    }

    @Test
    public void getCorrectColumnViolation(){
        ArrayList<int[]> violationCoordinates = new ArrayList<>();
        violationCoordinates.add(new int[]{1,1});
        violationCoordinates.add(new int[]{3,1});
        violationCoordinates.add(new int[]{0,1});
        violationCoordinates.add(new int[]{3,1});
        violationCoordinates.add(new int[]{0,1});
        violationCoordinates.add(new int[]{1,1});
        assertArrayEquals(violationCoordinates.toArray(),p.getColumnViolations(1).toArray());
    }

    @Test
    public void getCorrectGridViolation(){
        ArrayList<int[]> violationCoordinates = new ArrayList<>();
        violationCoordinates.add(new int[]{1,1});
        violationCoordinates.add(new int[]{0,1});
        assertArrayEquals(violationCoordinates.toArray(),p.getGridViolations(0,0).toArray());
    }

    @Test
    public void doesValueChange(){
        assertTrue(p.setSpaceValue(1,0,0));
        assertEquals(1,p.getSpaceValue(0,0));
    }

    @Test
    public void doesCountViolations(){
        ArrayList<int[]> correctViolation = new ArrayList<>();
        correctViolation.add(new int[]{1,1,2});
        correctViolation.add(new int[]{3,1,2});
        correctViolation.add(new int[]{0,1,2});
        assertArrayEquals(correctViolation.toArray(),p.countViolations().toArray());
    }




}
