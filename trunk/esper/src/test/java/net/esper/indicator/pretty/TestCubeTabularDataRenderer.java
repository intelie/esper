package net.esper.indicator.pretty;

import java.util.Map;

import javax.management.openmbean.TabularData;

import junit.framework.TestCase;
import net.esper.support.view.olap.SupportCubeFactory;
import net.esper.view.stat.olap.Cube;

public class TestCubeTabularDataRenderer extends TestCase
{
    /**
     * Basic smoke test only - this is mostly string rendering which is not checked individually
     */
    public void testRender()
    {
        Cube cube = SupportCubeFactory.make1DimCube();
        Map<String, TabularData> result = CubeTabularDataRenderer.renderCube(cube);
        assertTrue(result.size() == 1);

        cube = SupportCubeFactory.make2DimCube();
        result = CubeTabularDataRenderer.renderCube(cube);
        assertTrue(result.size() == 1);

        cube = SupportCubeFactory.make3DimCube();
        result = CubeTabularDataRenderer.renderCube(cube);
        assertTrue(result.size() == 3);
    }
}
