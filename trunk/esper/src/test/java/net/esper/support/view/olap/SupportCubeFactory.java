package net.esper.support.view.olap;

import net.esper.view.stat.olap.Cube;
import net.esper.view.stat.olap.*;
import net.esper.collection.MultiKeyUntyped;
import net.esper.view.stat.BaseStatisticsBean;

public class SupportCubeFactory
{
    private static String[] dim1Names = {"cell", "abcd"};
    private static String[] dim2Names = {"cell", "abcd", "xyz"};
    private static String[] dim3Names = {"cell", "abcd", "xyz", "p1p2p3p4p5"};

    private static MultidimCubeCellFactory<BaseStatisticsBean> multidimCubeCellFactory = new MultidimCubeCellFactory<BaseStatisticsBean>()
    {
        public BaseStatisticsBean newCell()
        {
            return new BaseStatisticsBean();
        }

        public BaseStatisticsBean[] newCells(int numElements)
        {
            return new BaseStatisticsBean[numElements];
        }
    };

    public static Cube make3DimCube()
    {
        MultidimCube<BaseStatisticsBean> testSchema = SupportCubeFactory.make3DimSchema();
        return deriveCube(testSchema);
    }

    public static Cube make2DimCube()
    {
        MultidimCube<BaseStatisticsBean> testSchema = SupportCubeFactory.make2DimSchema();
        return deriveCube(testSchema);
    }

    public static Cube make1DimCube()
    {
        MultidimCube<BaseStatisticsBean> testSchema = SupportCubeFactory.make1DimSchema();
        return deriveCube(testSchema);
    }

    public static MultidimCube<BaseStatisticsBean> make3DimSchema()
    {
        MultidimCube<BaseStatisticsBean> cube = new MultidimCubeImpl<BaseStatisticsBean>(dim3Names, multidimCubeCellFactory);

        cube.getCellAddMembers(makeKey(new Object[] {"a", "x", "p1"})).addPoint(1);
        cube.getCellAddMembers(makeKey(new Object[] {"a", "y", "p1"})).addPoint(2);
        cube.getCellAddMembers(makeKey(new Object[] {"a", "y", "p2"})).addPoint(8);
        cube.getCellAddMembers(makeKey(new Object[] {"b", "z", "p1"})).addPoint(3);
        cube.getCellAddMembers(makeKey(new Object[] {"b", "z", "p3"})).addPoint(8);
        cube.getCellAddMembers(makeKey(new Object[] {"c", "z", "p1"})).addPoint(4);
        cube.getCellAddMembers(makeKey(new Object[] {"c", "z", "p2"})).addPoint(10);
        cube.getCellAddMembers(makeKey(new Object[] {"d", "z", "p2"})).addPoint(5);

        return cube;
    }

    public static MultidimCube<BaseStatisticsBean> make2DimSchema()
    {
        MultidimCube<BaseStatisticsBean> cube = new MultidimCubeImpl<BaseStatisticsBean>(dim2Names, multidimCubeCellFactory);

        cube.getCellAddMembers(makeKey(new Object[] {"a", "x"})).addPoint(1);
        cube.getCellAddMembers(makeKey(new Object[] {"a", "y"})).addPoint(2);
        cube.getCellAddMembers(makeKey(new Object[] {"a", "y"})).addPoint(8);
        cube.getCellAddMembers(makeKey(new Object[] {"b", "z"})).addPoint(3);
        cube.getCellAddMembers(makeKey(new Object[] {"b", "z"})).addPoint(8);
        cube.getCellAddMembers(makeKey(new Object[] {"c", "z"})).addPoint(4);
        cube.getCellAddMembers(makeKey(new Object[] {"c", "z"})).addPoint(10);
        cube.getCellAddMembers(makeKey(new Object[] {"d", "z"})).addPoint(5);

        return cube;
    }

    public static MultidimCube<BaseStatisticsBean> make1DimSchema()
    {
        MultidimCube<BaseStatisticsBean> cube = new MultidimCubeImpl<BaseStatisticsBean>(dim1Names, multidimCubeCellFactory);

        cube.getCellAddMembers(makeKey(new Object[] {"a"})).addPoint(1);
        cube.getCellAddMembers(makeKey(new Object[] {"a"})).addPoint(2);
        cube.getCellAddMembers(makeKey(new Object[] {"b"})).addPoint(8);
        cube.getCellAddMembers(makeKey(new Object[] {"a"})).addPoint(3);
        cube.getCellAddMembers(makeKey(new Object[] {"b"})).addPoint(8);
        cube.getCellAddMembers(makeKey(new Object[] {"c"})).addPoint(4);
        cube.getCellAddMembers(makeKey(new Object[] {"d"})).addPoint(10);
        cube.getCellAddMembers(makeKey(new Object[] {"c"})).addPoint(5);

        return cube;
    }

    private static Cube deriveCube(MultidimCube<BaseStatisticsBean> cube)
    {
        CubeImpl cubeImpl = new CubeImpl(cube, new String[] {"count", "sum", "stddev"});
        return cubeImpl;
    }

    private static MultiKeyUntyped makeKey(Object[] keys)
    {
        return new MultiKeyUntyped(keys);
    }
}
