using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.collection;
using net.esper.support.view.olap;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.view.stat.olap
{
    [TestFixture]
    public class TestMultidimCubeImpl
    {
        private class MultidimCubeCellFactoryImpl : MultidimCubeCellFactory<SupportCell>
        {
            public SupportCell NewCell()
            {
                return new SupportCell();
            }

            public SupportCell[] NewCells(int numElements)
            {
                return new SupportCell[numElements];
            }
        }

        private static MultidimCubeCellFactory<SupportCell> multidimCubeCellFactory = new MultidimCubeCellFactoryImpl();

        private IList<Object> testMembersWithNull = new Object[] { "a", "b", null };

        private IList<Object> membersOne = new Object[] { "a", "b", "c", "d" };
        private IList<Object> membersTwo = new Object[] { "x", "y", "z" };
        private IList<Object> membersThree = new Object[] { "p1", "p2", "p3", "p4", "p5" };
        private IList<Object> membersFour = new Object[] { "alpha", "beta" };

        private String[] dim1Names = { "cell", "abcd" };
        private String[] dim2Names = { "cell", "abcd", "xyz" };
        private String[] dim3Names = { "cell", "abcd", "xyz", "p1p2p3p4p5" };
        private String[] dim4Names = { "cell", "abcd", "xyz", "p1p2p3p4p5", "alphabeta" };

        [Test]
        public void testOneDimensional()
        {
            MultidimCube<SupportCell> cube = new MultidimCubeImpl<SupportCell>(dim1Names, multidimCubeCellFactory);
            cube.SetMembers(0, testMembersWithNull);
            Assert.AreEqual(1, cube.NumDimensions);

            IList<Object> dimensionKeys = cube.GetMembers(0);
            Assert.IsTrue(dimensionKeys.Count == 3);
            Assert.IsTrue(CollectionHelper.ContainsAll(dimensionKeys, testMembersWithNull));
            Assert.IsTrue(cube.Cells.Length == 3);

            cube.GetCellAddMembers(makeKey(new Object[] { "a" })).CellValue = 100;

            cube.GetCellAddMembers(makeKey(new Object[] { "b" })).CellValue = 200;
            cube.GetCellAddMembers(makeKey(new Object[] { null })).CellValue = -1;

            Assert.AreEqual(100, cube.Cells[0].CellValue);
            Assert.AreEqual(200, cube.Cells[1].CellValue);
            Assert.AreEqual(-1, cube.Cells[2].CellValue);

            cube.GetCellAddMembers(makeKey(new Object[] { "b" })).CellValue = 600;
            Assert.AreEqual(600, cube.Cells[1].CellValue);

            // Add a new member that wasn't there before
            cube.GetCellAddMembers(makeKey(new Object[] { "d" })).CellValue = -2;
            cube.GetCellAddMembers(makeKey(new Object[] { "d" })).CellValue = -2;
            cube.GetCellAddMembers(makeKey(new Object[] { "b" })).CellValue = -810;
            Assert.AreEqual(-810, cube.Cells[1].CellValue);
            Assert.AreEqual(-2, cube.Cells[3].CellValue);

            // Add another new member
            cube.GetCellAddMembers(makeKey(new Object[] { "e" })).CellValue = -3;
            Assert.AreEqual(-3, cube.Cells[4].CellValue);

            Assert.IsTrue(CollectionHelper.ContainsAll(
                cube.GetMembers(0),
                new Object[] { "a", "b", null, "d", "e" }
                ));
        }

        [Test]
        public void testOneDimGrow()
        {
            MultidimCube<SupportCell> cube = new MultidimCubeImpl<SupportCell>(dim1Names, multidimCubeCellFactory);

            cube.GetCellAddMembers(makeKey(new Object[] { "a" })).CellValue = 100;
            cube.GetCellAddMembers(makeKey(new Object[] { "b" })).CellValue = 200;
            cube.GetCellAddMembers(makeKey(new Object[] { "a" })).CellValue = -50;
            cube.GetCellAddMembers(makeKey(new Object[] { "b" })).CellValue = 100;
            cube.GetCellAddMembers(makeKey(new Object[] { null })).CellValue = 9;

            Assert.AreEqual(-50, cube.Cells[0].CellValue);
            Assert.AreEqual(100, cube.Cells[1].CellValue);
            Assert.AreEqual(9, cube.Cells[2].CellValue);

            Assert.IsTrue(CollectionHelper.ContainsAll(cube.GetMembers(0), testMembersWithNull));
        }

        [Test]
        public void testTwoDimensional()
        {
            MultidimCube<SupportCell> cube = new MultidimCubeImpl<SupportCell>(dim2Names, multidimCubeCellFactory);

            // In              a            b           c           d
            //      x       [0,0] 0     [1,0] 1     [2,0] 2     [3,0] 3
            //      y             4           5           6     [3,1] 7
            //      z             8           9          10     [3,2] 11

            cube.SetMembers(0, membersOne);
            cube.SetMembers(1, membersTwo);

            cube.GetCellAddMembers(makeKey(new Object[] { "d", "y" })).CellValue = 1;
            cube.GetCellAddMembers(makeKey(new Object[] { "a", "z" })).CellValue = 2;
            cube.GetCellAddMembers(makeKey(new Object[] { "a", "x" })).CellValue = 3;
            cube.GetCellAddMembers(makeKey(new Object[] { "c", "y" })).CellValue = 4;
            cube.GetCellAddMembers(makeKey(new Object[] { "d", "x" })).CellValue = 5;
            cube.GetCellAddMembers(makeKey(new Object[] { "d", "z" })).CellValue = 6;

            Assert.IsTrue(cube.Cells.Length == 12);
            Assert.AreEqual(1, cube.Cells[7].CellValue);
            Assert.AreEqual(2, cube.Cells[8].CellValue);
            Assert.AreEqual(3, cube.Cells[0].CellValue);
            Assert.AreEqual(4, cube.Cells[6].CellValue);
            Assert.AreEqual(5, cube.Cells[3].CellValue);
            Assert.AreEqual(6, cube.Cells[11].CellValue);

            Assert.IsTrue(CollectionHelper.ContainsAll(cube.GetMembers(0), membersOne));
            Assert.IsTrue(CollectionHelper.ContainsAll(cube.GetMembers(1), membersTwo));
        }

        [Test]
        public void testTwoDimensionalGrow()
        {
            MultidimCube<SupportCell> cube = new MultidimCubeImpl<SupportCell>(dim2Names, multidimCubeCellFactory);

            cube.GetCellAddMembers(makeKey(new Object[] { "a", "x" })).CellValue = 1;
            cube.GetCellAddMembers(makeKey(new Object[] { "a", "y" })).CellValue = 2;
            cube.GetCellAddMembers(makeKey(new Object[] { "a", "y" })).CellValue = 8;
            cube.GetCellAddMembers(makeKey(new Object[] { "b", "z" })).CellValue = 3;
            cube.GetCellAddMembers(makeKey(new Object[] { "b", "z" })).CellValue = 8;
            cube.GetCellAddMembers(makeKey(new Object[] { "c", "z" })).CellValue = 4;
            cube.GetCellAddMembers(makeKey(new Object[] { "c", "z" })).CellValue = 10;
            cube.GetCellAddMembers(makeKey(new Object[] { "d", "z" })).CellValue = 5;

            Assert.IsTrue(cube.Cells.Length == 12);
            Assert.AreEqual(1, cube.Cells[0].CellValue);
            Assert.AreEqual(8, cube.Cells[4].CellValue);
            Assert.AreEqual(8, cube.Cells[9].CellValue);
            Assert.AreEqual(10, cube.Cells[10].CellValue);
            Assert.AreEqual(5, cube.Cells[11].CellValue);

            Assert.IsTrue(CollectionHelper.ContainsAll(cube.GetMembers(0), membersOne));
            Assert.IsTrue(CollectionHelper.ContainsAll(cube.GetMembers(1), membersTwo));
        }

        [Test]
        public void testThreeDimensional()
        {
            MultidimCube<SupportCell> cube = new MultidimCubeImpl<SupportCell>(dim3Names, multidimCubeCellFactory);

            cube.SetMembers(0, membersOne);
            cube.SetMembers(1, membersTwo);
            cube.SetMembers(2, membersThree);

            cube.GetCellAddMembers(makeKey(new Object[] { "d", "y", "p2" })).CellValue = 1;
            cube.GetCellAddMembers(makeKey(new Object[] { "d", "y", "p2" })).CellValue = 4;
            cube.GetCellAddMembers(makeKey(new Object[] { "a", "z", "p3" })).CellValue = 2;

            Assert.AreEqual(60, cube.Cells.Length);
            Assert.AreEqual(4, cube.Cells[3 + 1 * 4 + 12].CellValue);
            Assert.AreEqual(2, cube.Cells[0 + 2 * 4 + 12 * 2].CellValue);
        }

        [Test]
        public void testThreeDimensionalGrow()
        {
            MultidimCube<SupportCell> cube = new MultidimCubeImpl<SupportCell>(dim3Names, multidimCubeCellFactory);

            cube.GetCellAddMembers(makeKey(new Object[] { "a", "x", "p1" })).CellValue = 1;
            cube.GetCellAddMembers(makeKey(new Object[] { "b", "y", "p1" })).CellValue = 2;
            cube.GetCellAddMembers(makeKey(new Object[] { "b", "z", "p2" })).CellValue = 3;
            cube.GetCellAddMembers(makeKey(new Object[] { "b", "y", "p3" })).CellValue = 4;
            cube.GetCellAddMembers(makeKey(new Object[] { "c", "x", "p2" })).CellValue = 5;
            cube.GetCellAddMembers(makeKey(new Object[] { "d", "z", "p3" })).CellValue = 6;
            cube.GetCellAddMembers(makeKey(new Object[] { "a", "x", "p4" })).CellValue = 7;
            cube.GetCellAddMembers(makeKey(new Object[] { "d", "z", "p5" })).CellValue = 8;

            Assert.AreEqual(60, cube.Cells.Length);
            Assert.AreEqual(1, cube.Cells[0 + 0 * 4 + 0 * 12].CellValue);
            Assert.AreEqual(2, cube.Cells[1 + 1 * 4 + 0 * 12].CellValue);
            Assert.AreEqual(3, cube.Cells[1 + 2 * 4 + 1 * 12].CellValue);
            Assert.AreEqual(4, cube.Cells[1 + 1 * 4 + 2 * 12].CellValue);
            Assert.AreEqual(5, cube.Cells[2 + 0 * 4 + 1 * 12].CellValue);
            Assert.AreEqual(6, cube.Cells[3 + 2 * 4 + 2 * 12].CellValue);
            Assert.AreEqual(7, cube.Cells[0 + 0 * 4 + 3 * 12].CellValue);
            Assert.AreEqual(8, cube.Cells[3 + 2 * 4 + 4 * 12].CellValue);

            Assert.IsTrue(CollectionHelper.ContainsAll(cube.GetMembers(0), membersOne));
            Assert.IsTrue(CollectionHelper.ContainsAll(cube.GetMembers(1), membersTwo));
            Assert.IsTrue(CollectionHelper.ContainsAll(cube.GetMembers(2), membersThree));
        }

        [Test]
        public void testFourDimensional()
        {
            MultidimCube<SupportCell> cube = new MultidimCubeImpl<SupportCell>(dim4Names, multidimCubeCellFactory);

            cube.SetMembers(0, membersOne);
            cube.SetMembers(1, membersTwo);
            cube.SetMembers(2, membersThree);
            cube.SetMembers(3, membersFour);

            cube.GetCellAddMembers(makeKey(new Object[] { "d", "y", "p2", "beta" })).CellValue = 10;
            cube.GetCellAddMembers(makeKey(new Object[] { "b", "z", "p3", "beta" })).CellValue = 90;
            cube.GetCellAddMembers(makeKey(new Object[] { "c", "x", "p3", "alpha" })).CellValue = 5;

            Assert.AreEqual(120, cube.Cells.Length);
            Assert.AreEqual(10, cube.Cells[3 + 1 * 4 + 12 + 60].CellValue);
            Assert.AreEqual(90, cube.Cells[1 + 2 * 4 + 2 * 12 + 60].CellValue);
            Assert.AreEqual(5, cube.Cells[2 + 0 * 4 + 2 * 12].CellValue);
        }

        [Test]
        public void testFourDimensionalGrow()
        {
            MultidimCube<SupportCell> cube = new MultidimCubeImpl<SupportCell>(dim4Names, multidimCubeCellFactory);

            cube.GetCellAddMembers(makeKey(new Object[] { "a", "x", "p1", "alpha" })).CellValue = 1;
            cube.GetCellAddMembers(makeKey(new Object[] { "b", "x", "p2", "beta" })).CellValue = 2;
            cube.GetCellAddMembers(makeKey(new Object[] { "b", "y", "p3", "alpha" })).CellValue = 3;
            cube.GetCellAddMembers(makeKey(new Object[] { "c", "y", "p2", "alpha" })).CellValue = 4;
            cube.GetCellAddMembers(makeKey(new Object[] { "d", "z", "p1", "beta" })).CellValue = 5;
            cube.GetCellAddMembers(makeKey(new Object[] { "b", "y", "p4", "alpha" })).CellValue = 6;
            cube.GetCellAddMembers(makeKey(new Object[] { "d", "z", "p5", "beta" })).CellValue = 7;

            Assert.AreEqual(120, cube.Cells.Length);
            Assert.AreEqual(1, cube.Cells[0 + 0 * 4 + 0 * 12 + 0 * 60].CellValue);
            Assert.AreEqual(2, cube.Cells[1 + 0 * 4 + 1 * 12 + 1 * 60].CellValue);
            Assert.AreEqual(3, cube.Cells[1 + 1 * 4 + 2 * 12 + 0 * 60].CellValue);
            Assert.AreEqual(4, cube.Cells[2 + 1 * 4 + 1 * 12 + 0 * 60].CellValue);
            Assert.AreEqual(5, cube.Cells[3 + 2 * 4 + 0 * 12 + 1 * 60].CellValue);
            Assert.AreEqual(6, cube.Cells[1 + 1 * 4 + 3 * 12 + 0 * 60].CellValue);
            Assert.AreEqual(7, cube.Cells[3 + 2 * 4 + 4 * 12 + 1 * 60].CellValue);

            Assert.IsTrue(CollectionHelper.ContainsAll(cube.GetMembers(0), membersOne));
            Assert.IsTrue(CollectionHelper.ContainsAll(cube.GetMembers(1), membersTwo));
            Assert.IsTrue(CollectionHelper.ContainsAll(cube.GetMembers(2), membersThree));
            Assert.IsTrue(CollectionHelper.ContainsAll(cube.GetMembers(3), membersFour));
        }

        [Test]
        public void testInvalid()
        {
            try
            {
                // Dimension out of bounds
                new MultidimCubeImpl<SupportCell>(new String[1], multidimCubeCellFactory);
                Assert.IsTrue(false);
            }
            catch (ArgumentException ex)
            {
                // Expected exception
            }

            try
            {
                // Dimension out of bounds
                MultidimCubeImpl<SupportCell> cube = new MultidimCubeImpl<SupportCell>(dim1Names, multidimCubeCellFactory);
                cube.SetMembers(1, testMembersWithNull);
                Assert.IsTrue(false);
            }
            catch (ArgumentException ex)
            {
                // Expected exception
            }

            try
            {
                // Setting members twice
                MultidimCube<SupportCell> cube = new MultidimCubeImpl<SupportCell>(dim1Names, multidimCubeCellFactory);
                cube.SetMembers(0, testMembersWithNull);
                cube.SetMembers(0, testMembersWithNull);
                Assert.IsTrue(false);
            }
            catch (ArgumentException ex)
            {
                // Expected exception
            }

            try
            {
                // Setting members twice
                MultidimCube<SupportCell> cube = new MultidimCubeImpl<SupportCell>(dim2Names, multidimCubeCellFactory);
                cube.SetMembers(1, testMembersWithNull);
                cube.SetMembers(0, testMembersWithNull);
                cube.SetMembers(1, testMembersWithNull);
                Assert.IsTrue(false);
            }
            catch (ArgumentException ex)
            {
                // Expected exception
            }

            try
            {
                // Wrong number of member coordinates
                MultidimCube<SupportCell> cube = new MultidimCubeImpl<SupportCell>(dim1Names, multidimCubeCellFactory);
                cube.GetCellAddMembers(makeKey(new Object[] { "d", "y" }));
                Assert.IsTrue(false);
            }
            catch (ArgumentException ex)
            {
                // Expected exception
            }
        }

        private MultiKeyUntyped makeKey(Object[] keys)
        {
            return new MultiKeyUntyped(keys);
        }
    }
}
