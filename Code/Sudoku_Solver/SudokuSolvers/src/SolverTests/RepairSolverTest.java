package SolverTests;

import MMP.RepairBasedSolver;
import org.junit.jupiter.api.Test;
import MMP.Puzzle;
import MMP.Solver;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RepairSolverTest {
    Solver solver = new RepairBasedSolver();

    // 0 0 0 2
    // 0 0 4 0
    // 1 0 0 0
    // 2 0 0 3
    @Test
    public void solve4x4Puzzle(){
        int[] values = {0,0,0,2,0,0,4,0,1,0,0,0,2,0,0,3};
        Puzzle p = new Puzzle(4,values,1);
        assertNotNull(solver.getSolution(p));
    }

    // 0 4 2 3
    // 0 3 0 0
    // 0 0 4 0
    // 4 1 3 0
    @Test
    public void solve4x4Puzzle1(){
        int[] values = {0,4,2,3,0,3,0,0,0,0,4,0,4,1,3,0};
        Puzzle p = new Puzzle(4,values,1);
        assertNotNull(solver.getSolution(p));
    }

    // 0 0 3 0
    // 3 4 0 2
    // 2 0 4 1
    // 0 1 0 0
    @Test
    public void solve4x4Puzzle2(){
        int[] values = {0,0,3,0,3,4,0,2,2,0,4,1,0,1,0,0};
        Puzzle p = new Puzzle(4,values,1);
        assertNotNull(solver.getSolution(p));
    }


    // 3 0 0 1
    // 0 2 4 0
    // 0 1 3 0
    // 4 0 0 2
    @Test
    public void solve4x4Puzzle3(){
        int[] values = {3,0,0,1,0,2,4,0,0,1,3,0,4,0,0,2};
        Puzzle p = new Puzzle(4,values,1);
        assertNotNull(solver.getSolution(p));
    }

    // 6 9 0 0 0 0 0 8 7
    // 0 0 0 0 0 4 0 9 0
    // 5 0 3 0 7 0 0 0 1
    // 2 8 5 9 6 0 1 7 0
    // 9 1 0 7 4 5 3 2 0
    // 0 0 0 0 0 8 6 5 0
    // 8 0 9 4 0 1 0 0 0
    // 0 3 0 0 0 0 0 0 5
    // 0 0 0 3 9 0 0 1 0
    @Test
    public void solve9x9Puzzle(){
        int[] values = {6,9,0,0,0,0,0,8,7,0,0,0,0,0,4,0,9,0,5,0,3,0,7,0,0,0,1,2,8,5,9,6,0,1,7,0,9,1,0,7,4,5,3,2,0,0,0,0,0,0,8,6,5,0,8,0,9,4,0,1,0,0,0,0,3,0,0,0,0,0,0,5,0,0,0,3,9,0,0,1,0};
        Puzzle p = new Puzzle(9,values,1);
        assertNotNull(solver.getSolution(p));
    }


    @Test
    public void solve9x9Puzzle1(){
        int[] values = {4,0,0,0,3,0,0,0,1,9,0,5,0,0,7,6,2,0,0,8,0,9,0,5,0,3,0,3,0,0,0,2,1,0,0,0,0,2,0,0,8,0,0,7,4,0,1,4,0,7,6,3,9,2,7,0,8,0,1,0,2,0,9,6,9,0,0,5,4,8,1,3,0,4,0,2,0,8,0,6,0};
        Puzzle p = new Puzzle(9,values,1);
        assertNotNull(solver.getSolution(p));
    }

    @Test
    public void isPopulationGenerated(){
        int[] values = {0,0,0,2,0,0,4,0,1,0,0,0,2,0,0,3};
        Puzzle p = new Puzzle(4,values,1);
        solver.generatePopulation(p);
        assertEquals(100, solver.getPopulationSize());
    }

    @Test
    public void doesPopulationSplit(){
        int[] values = {0,0,0,2,0,0,4,0,1,0,0,0,2,0,0,3};
        Puzzle p = new Puzzle(4,values,1);
        solver.generatePopulation(p);
        solver.splitPopulation();
        assertEquals(50, solver.getPopulationSize());
    }

    @Test
    public void isPopulationMutated(){
        int[] values = {0,0,0,2,0,0,4,0,1,0,0,0,2,0,0,3};
        Puzzle p = new Puzzle(4,values,1);
        solver.generatePopulation(p);
        solver.mutatePopulation();
        assertEquals(200, solver.getPopulationSize());
    }

    @Test
    public void checkSpacePermanent(){
        int[] values = {0,0,0,2,0,0,4,0,1,0,0,0,2,0,0,3};
        Puzzle p = new Puzzle(4,values,1);
        solver.generatePopulation(p);
        assertFalse(solver.isSpacePermanent(0,0));
        assertTrue(solver.isSpacePermanent(0,3));
    }

    @Test
    public void isChangeableSpacesCorrect(){
        ArrayList<int[]> spaces = new ArrayList<>();
        spaces.add(new int[]{});
    }

    @Test
    public void isInitialCoordsCorrect(){

    }


}
