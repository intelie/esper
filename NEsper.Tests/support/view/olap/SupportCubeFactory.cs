using System;

using net.esper.collection;
using net.esper.view.stat;
using net.esper.view.stat.olap;

namespace net.esper.support.view.olap
{

    public class SupportCubeFactory
    {
        private static String[] dim1Names = new String[] { "cell", "abcd" };
        private static String[] dim2Names = new String[] { "cell", "abcd", "xyz" };
        private static String[] dim3Names = new String[] { "cell", "abcd", "xyz", "p1p2p3p4p5" };

        private class MultidimCubeCellFactoryImpl : MultidimCubeCellFactory<BaseStatisticsBean>
        {
            public virtual BaseStatisticsBean newCell()
            {
                return new BaseStatisticsBean();
            }

            public virtual BaseStatisticsBean[] newCells(int numElements)
            {
                return new BaseStatisticsBean[numElements];
            }
        }

        private static MultidimCubeCellFactory<BaseStatisticsBean> multidimCubeCellFactory = new MultidimCubeCellFactoryImpl();

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

			cube.GetCellAddMembers( makeKey( new Object[] { "a", "x", "p1" } ) ).AddPoint( 1 );
			cube.GetCellAddMembers( makeKey( new Object[] { "a", "y", "p1" } ) ).AddPoint( 2 );
			cube.GetCellAddMembers( makeKey( new Object[] { "a", "y", "p2" } ) ).AddPoint( 8 );
			cube.GetCellAddMembers( makeKey( new Object[] { "b", "z", "p1" } ) ).AddPoint( 3 );
			cube.GetCellAddMembers( makeKey( new Object[] { "b", "z", "p3" } ) ).AddPoint( 8 );
			cube.GetCellAddMembers( makeKey( new Object[] { "c", "z", "p1" } ) ).AddPoint( 4 );
			cube.GetCellAddMembers( makeKey( new Object[] { "c", "z", "p2" } ) ).AddPoint( 10 );
			cube.GetCellAddMembers( makeKey( new Object[] { "d", "z", "p2" } ) ).AddPoint( 5 );

            return cube;
        }

        public static MultidimCube<BaseStatisticsBean> make2DimSchema()
        {
            MultidimCube<BaseStatisticsBean> cube = new MultidimCubeImpl<BaseStatisticsBean>(dim2Names, multidimCubeCellFactory);

            cube.GetCellAddMembers(makeKey(new Object[] { "a", "x" })).AddPoint(1);
            cube.GetCellAddMembers(makeKey(new Object[] { "a", "y" })).AddPoint(2);
            cube.GetCellAddMembers(makeKey(new Object[] { "a", "y" })).AddPoint(8);
            cube.GetCellAddMembers(makeKey(new Object[] { "b", "z" })).AddPoint(3);
            cube.GetCellAddMembers(makeKey(new Object[] { "b", "z" })).AddPoint(8);
            cube.GetCellAddMembers(makeKey(new Object[] { "c", "z" })).AddPoint(4);
            cube.GetCellAddMembers(makeKey(new Object[] { "c", "z" })).AddPoint(10);
            cube.GetCellAddMembers(makeKey(new Object[] { "d", "z" })).AddPoint(5);

            return cube;
        }

        public static MultidimCube<BaseStatisticsBean> make1DimSchema()
        {
            MultidimCube<BaseStatisticsBean> cube = new MultidimCubeImpl<BaseStatisticsBean>(dim1Names, multidimCubeCellFactory);

            cube.GetCellAddMembers(makeKey(new Object[] { "a" })).AddPoint(1);
            cube.GetCellAddMembers(makeKey(new Object[] { "a" })).AddPoint(2);
            cube.GetCellAddMembers(makeKey(new Object[] { "b" })).AddPoint(8);
            cube.GetCellAddMembers(makeKey(new Object[] { "a" })).AddPoint(3);
            cube.GetCellAddMembers(makeKey(new Object[] { "b" })).AddPoint(8);
            cube.GetCellAddMembers(makeKey(new Object[] { "c" })).AddPoint(4);
            cube.GetCellAddMembers(makeKey(new Object[] { "d" })).AddPoint(10);
            cube.GetCellAddMembers(makeKey(new Object[] { "c" })).AddPoint(5);

            return cube;
        }

        private static Cube deriveCube(MultidimCube<BaseStatisticsBean> cube)
        {
            CubeImpl cubeImpl = new CubeImpl(cube, new String[] { "count", "sum", "stddev" });
            return cubeImpl;
        }

        private static MultiKeyUntyped makeKey(Object[] keys)
        {
            return new MultiKeyUntyped(keys);
        }
    }
}
