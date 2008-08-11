package com.espertech.esper.view.stat.olap;

import junit.framework.TestCase;
import java.util.*;

import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.support.view.olap.SupportCell;

public class TestMultidimCubeImpl extends TestCase
{
    private static MultidimCubeCellFactory<SupportCell> multidimCubeCellFactory = new MultidimCubeCellFactory<SupportCell>()
    {
        public SupportCell newCell()
        {
            return new SupportCell();
        }

        public SupportCell[] newCells(int numElements)
        {
            return new SupportCell[numElements];
        }
    };

    private List<Object> testMembersWithNull = Arrays.asList(new Object[] {"a", "b", null});

    private List<Object> membersOne = Arrays.asList(new Object[] {"a", "b", "c", "d"});
    private List<Object> membersTwo = Arrays.asList(new Object[] {"x", "y", "z"});
    private List<Object> membersThree = Arrays.asList(new Object[] {"p1", "p2", "p3", "p4", "p5"});
    private List<Object> membersFour = Arrays.asList(new Object[] {"alpha", "beta"});

    private String[] dim1Names = {"cell", "abcd"};
    private String[] dim2Names = {"cell", "abcd", "xyz"};
    private String[] dim3Names = {"cell", "abcd", "xyz", "p1p2p3p4p5"};
    private String[] dim4Names = {"cell", "abcd", "xyz", "p1p2p3p4p5", "alphabeta"};

    public void testOneDimensional()
    {
        MultidimCube<SupportCell> cube = new MultidimCubeImpl<SupportCell>(dim1Names, multidimCubeCellFactory);
        cube.setMembers(0, testMembersWithNull);
        assertEquals(1, cube.getNumDimensions());

        List<Object> dimensionKeys = cube.getMembers(0);
        assertTrue(dimensionKeys.size() == 3);
        assertTrue(dimensionKeys.containsAll(testMembersWithNull));
        assertTrue(cube.getCells().length == 3);

        cube.getCellAddMembers(makeKey(new Object[] {"a"})).setCellValue(100);

        cube.getCellAddMembers(makeKey(new Object[] {"b"})).setCellValue(200);
        cube.getCellAddMembers(makeKey(new Object[] {null})).setCellValue(-1);

        assertEquals(100, cube.getCells()[0].getCellValue());
        assertEquals(200, cube.getCells()[1].getCellValue());
        assertEquals(-1, cube.getCells()[2].getCellValue());

        cube.getCellAddMembers(makeKey(new Object[] {"b"})).setCellValue(600);
        assertEquals(600, cube.getCells()[1].getCellValue());

        // Add a new member that wasn't there before
        cube.getCellAddMembers(makeKey(new Object[] {"d"})).setCellValue(-2);
        cube.getCellAddMembers(makeKey(new Object[] {"d"})).setCellValue(-2);
        cube.getCellAddMembers(makeKey(new Object[] {"b"})).setCellValue(-810);
        assertEquals(-810, cube.getCells()[1].getCellValue());
        assertEquals(-2, cube.getCells()[3].getCellValue());

        // Add another new member
        cube.getCellAddMembers(makeKey(new Object[] {"e"})).setCellValue(-3);
        assertEquals(-3, cube.getCells()[4].getCellValue());

        assertTrue(cube.getMembers(0).containsAll(Arrays.asList(new Object[] {"a", "b", null, "d", "e"})));
    }

    public void testOneDimGrow()
    {
        MultidimCube<SupportCell> cube = new MultidimCubeImpl<SupportCell>(dim1Names, multidimCubeCellFactory);

        cube.getCellAddMembers(makeKey(new Object[] {"a"})).setCellValue(100);
        cube.getCellAddMembers(makeKey(new Object[] {"b"})).setCellValue(200);
        cube.getCellAddMembers(makeKey(new Object[] {"a"})).setCellValue(-50);
        cube.getCellAddMembers(makeKey(new Object[] {"b"})).setCellValue(100);
        cube.getCellAddMembers(makeKey(new Object[] {null})).setCellValue(9);

        assertEquals(-50, cube.getCells()[0].getCellValue());
        assertEquals(100, cube.getCells()[1].getCellValue());
        assertEquals(9, cube.getCells()[2].getCellValue());

        assertTrue(cube.getMembers(0).containsAll(testMembersWithNull));
    }

    public void testTwoDimensional()
    {
        MultidimCube<SupportCell> cube = new MultidimCubeImpl<SupportCell>(dim2Names, multidimCubeCellFactory);

        // In              a            b           c           d
        //      x       [0,0] 0     [1,0] 1     [2,0] 2     [3,0] 3
        //      y             4           5           6     [3,1] 7
        //      z             8           9          10     [3,2] 11

        cube.setMembers(0, membersOne);
        cube.setMembers(1, membersTwo);

        cube.getCellAddMembers(makeKey(new Object[] {"d", "y"})).setCellValue(1);
        cube.getCellAddMembers(makeKey(new Object[] {"a", "z"})).setCellValue(2);
        cube.getCellAddMembers(makeKey(new Object[] {"a", "x"})).setCellValue(3);
        cube.getCellAddMembers(makeKey(new Object[] {"c", "y"})).setCellValue(4);
        cube.getCellAddMembers(makeKey(new Object[] {"d", "x"})).setCellValue(5);
        cube.getCellAddMembers(makeKey(new Object[] {"d", "z"})).setCellValue(6);

        assertTrue(cube.getCells().length == 12);
        assertEquals(1, cube.getCells()[7].getCellValue());
        assertEquals(2, cube.getCells()[8].getCellValue());
        assertEquals(3, cube.getCells()[0].getCellValue());
        assertEquals(4, cube.getCells()[6].getCellValue());
        assertEquals(5, cube.getCells()[3].getCellValue());
        assertEquals(6, cube.getCells()[11].getCellValue());

        assertTrue(cube.getMembers(0).containsAll(membersOne));
        assertTrue(cube.getMembers(1).containsAll(membersTwo));
    }

    public void testTwoDimensionalGrow()
    {
        MultidimCube<SupportCell> cube = new MultidimCubeImpl<SupportCell>(dim2Names, multidimCubeCellFactory);

        cube.getCellAddMembers(makeKey(new Object[] {"a", "x"})).setCellValue(1);
        cube.getCellAddMembers(makeKey(new Object[] {"a", "y"})).setCellValue(2);
        cube.getCellAddMembers(makeKey(new Object[] {"a", "y"})).setCellValue(8);
        cube.getCellAddMembers(makeKey(new Object[] {"b", "z"})).setCellValue(3);
        cube.getCellAddMembers(makeKey(new Object[] {"b", "z"})).setCellValue(8);
        cube.getCellAddMembers(makeKey(new Object[] {"c", "z"})).setCellValue(4);
        cube.getCellAddMembers(makeKey(new Object[] {"c", "z"})).setCellValue(10);
        cube.getCellAddMembers(makeKey(new Object[] {"d", "z"})).setCellValue(5);

        assertTrue(cube.getCells().length == 12);
        assertEquals(1, cube.getCells()[0].getCellValue());
        assertEquals(8, cube.getCells()[4].getCellValue());
        assertEquals(8, cube.getCells()[9].getCellValue());
        assertEquals(10, cube.getCells()[10].getCellValue());
        assertEquals(5, cube.getCells()[11].getCellValue());

        assertTrue(cube.getMembers(0).containsAll(membersOne));
        assertTrue(cube.getMembers(1).containsAll(membersTwo));
    }

    public void testThreeDimensional()
    {
        MultidimCube<SupportCell> cube = new MultidimCubeImpl<SupportCell>(dim3Names, multidimCubeCellFactory);

        cube.setMembers(0, membersOne);
        cube.setMembers(1, membersTwo);
        cube.setMembers(2, membersThree);

        cube.getCellAddMembers(makeKey(new Object[] {"d", "y", "p2"})).setCellValue(1);
        cube.getCellAddMembers(makeKey(new Object[] {"d", "y", "p2"})).setCellValue(4);
        cube.getCellAddMembers(makeKey(new Object[] {"a", "z", "p3"})).setCellValue(2);

        assertEquals(60, cube.getCells().length);
        assertEquals(4, cube.getCells()[3 + 1 * 4 + 12].getCellValue());
        assertEquals(2, cube.getCells()[0 + 2 * 4 + 12 * 2].getCellValue());
    }

    public void testThreeDimensionalGrow()
    {
        MultidimCube<SupportCell> cube = new MultidimCubeImpl<SupportCell>(dim3Names, multidimCubeCellFactory);

        cube.getCellAddMembers(makeKey(new Object[] {"a", "x", "p1"})).setCellValue(1);
        cube.getCellAddMembers(makeKey(new Object[] {"b", "y", "p1"})).setCellValue(2);
        cube.getCellAddMembers(makeKey(new Object[] {"b", "z", "p2"})).setCellValue(3);
        cube.getCellAddMembers(makeKey(new Object[] {"b", "y", "p3"})).setCellValue(4);
        cube.getCellAddMembers(makeKey(new Object[] {"c", "x", "p2"})).setCellValue(5);
        cube.getCellAddMembers(makeKey(new Object[] {"d", "z", "p3"})).setCellValue(6);
        cube.getCellAddMembers(makeKey(new Object[] {"a", "x", "p4"})).setCellValue(7);
        cube.getCellAddMembers(makeKey(new Object[] {"d", "z", "p5"})).setCellValue(8);

        assertEquals(60, cube.getCells().length);
        assertEquals(1, cube.getCells()[0 + 0 * 4 + 0 * 12].getCellValue());
        assertEquals(2, cube.getCells()[1 + 1 * 4 + 0 * 12].getCellValue());
        assertEquals(3, cube.getCells()[1 + 2 * 4 + 1 * 12].getCellValue());
        assertEquals(4, cube.getCells()[1 + 1 * 4 + 2 * 12].getCellValue());
        assertEquals(5, cube.getCells()[2 + 0 * 4 + 1 * 12].getCellValue());
        assertEquals(6, cube.getCells()[3 + 2 * 4 + 2 * 12].getCellValue());
        assertEquals(7, cube.getCells()[0 + 0 * 4 + 3 * 12].getCellValue());
        assertEquals(8, cube.getCells()[3 + 2 * 4 + 4 * 12].getCellValue());

        assertTrue(cube.getMembers(0).containsAll(membersOne));
        assertTrue(cube.getMembers(1).containsAll(membersTwo));
        assertTrue(cube.getMembers(2).containsAll(membersThree));
    }

    public void testFourDimensional()
    {
        MultidimCube<SupportCell> cube = new MultidimCubeImpl<SupportCell>(dim4Names, multidimCubeCellFactory);

        cube.setMembers(0, membersOne);
        cube.setMembers(1, membersTwo);
        cube.setMembers(2, membersThree);
        cube.setMembers(3, membersFour);

        cube.getCellAddMembers(makeKey(new Object[] {"d", "y", "p2", "beta"})).setCellValue(10);
        cube.getCellAddMembers(makeKey(new Object[] {"b", "z", "p3", "beta"})).setCellValue(90);
        cube.getCellAddMembers(makeKey(new Object[] {"c", "x", "p3", "alpha"})).setCellValue(5);

        assertEquals(120, cube.getCells().length);
        assertEquals(10, cube.getCells()[3 + 1 * 4 + 12 + 60].getCellValue());
        assertEquals(90, cube.getCells()[1 + 2 * 4 + 2 * 12 + 60].getCellValue());
        assertEquals(5, cube.getCells()[2 + 0 * 4 + 2 * 12].getCellValue());
    }

    public void testFourDimensionalGrow()
    {
        MultidimCube<SupportCell> cube = new MultidimCubeImpl<SupportCell>(dim4Names, multidimCubeCellFactory);

        cube.getCellAddMembers(makeKey(new Object[] {"a", "x", "p1", "alpha"})).setCellValue(1);
        cube.getCellAddMembers(makeKey(new Object[] {"b", "x", "p2", "beta"})).setCellValue(2);
        cube.getCellAddMembers(makeKey(new Object[] {"b", "y", "p3", "alpha"})).setCellValue(3);
        cube.getCellAddMembers(makeKey(new Object[] {"c", "y", "p2", "alpha"})).setCellValue(4);
        cube.getCellAddMembers(makeKey(new Object[] {"d", "z", "p1", "beta"})).setCellValue(5);
        cube.getCellAddMembers(makeKey(new Object[] {"b", "y", "p4", "alpha"})).setCellValue(6);
        cube.getCellAddMembers(makeKey(new Object[] {"d", "z", "p5", "beta"})).setCellValue(7);

        assertEquals(120, cube.getCells().length);
        assertEquals(1, cube.getCells()[0 + 0 * 4 + 0 * 12 + 0 * 60].getCellValue());
        assertEquals(2, cube.getCells()[1 + 0 * 4 + 1 * 12 + 1 * 60].getCellValue());
        assertEquals(3, cube.getCells()[1 + 1 * 4 + 2 * 12 + 0 * 60].getCellValue());
        assertEquals(4, cube.getCells()[2 + 1 * 4 + 1 * 12 + 0 * 60].getCellValue());
        assertEquals(5, cube.getCells()[3 + 2 * 4 + 0 * 12 + 1 * 60].getCellValue());
        assertEquals(6, cube.getCells()[1 + 1 * 4 + 3 * 12 + 0 * 60].getCellValue());
        assertEquals(7, cube.getCells()[3 + 2 * 4 + 4 * 12 + 1 * 60].getCellValue());

        assertTrue(cube.getMembers(0).containsAll(membersOne));
        assertTrue(cube.getMembers(1).containsAll(membersTwo));
        assertTrue(cube.getMembers(2).containsAll(membersThree));
        assertTrue(cube.getMembers(3).containsAll(membersFour));
    }

    public void testInvalid()
    {
        try
        {
            // Dimension out of bounds
            new MultidimCubeImpl<SupportCell>(new String[1], multidimCubeCellFactory);
            assertTrue(false);
        }
        catch (IllegalArgumentException ex)
        {
            // Expected exception
        }

        try
        {
            // Dimension out of bounds
            MultidimCubeImpl<SupportCell> cube = new MultidimCubeImpl<SupportCell>(dim1Names, multidimCubeCellFactory);
            cube.setMembers(1, testMembersWithNull);
            assertTrue(false);
        }
        catch (IllegalArgumentException ex)
        {
            // Expected exception
        }

        try
        {
            // Setting members twice
            MultidimCube<SupportCell> cube = new MultidimCubeImpl<SupportCell>(dim1Names, multidimCubeCellFactory);
            cube.setMembers(0, testMembersWithNull);
            cube.setMembers(0, testMembersWithNull);
            assertTrue(false);
        }
        catch (IllegalArgumentException ex)
        {
            // Expected exception
        }

        try
        {
            // Setting members twice
            MultidimCube<SupportCell> cube = new MultidimCubeImpl<SupportCell>(dim2Names, multidimCubeCellFactory);
            cube.setMembers(1, testMembersWithNull);
            cube.setMembers(0, testMembersWithNull);
            cube.setMembers(1, testMembersWithNull);
            assertTrue(false);
        }
        catch (IllegalArgumentException ex)
        {
            // Expected exception
        }

        try
        {
            // Wrong number of member coordinates
            MultidimCube<SupportCell> cube = new MultidimCubeImpl<SupportCell>(dim1Names, multidimCubeCellFactory);
            cube.getCellAddMembers(makeKey(new Object[] {"d", "y"}));
            assertTrue(false);
        }
        catch (IllegalArgumentException ex)
        {
            // Expected exception
        }
    }

    private MultiKeyUntyped makeKey(Object[] keys)
    {
        return new MultiKeyUntyped(keys);
    }
}
