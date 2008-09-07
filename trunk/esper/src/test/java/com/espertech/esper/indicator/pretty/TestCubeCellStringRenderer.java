package com.espertech.esper.indicator.pretty;

import java.util.Map;

import junit.framework.TestCase;
import com.espertech.esper.view.stat.olap.Cube;
import com.espertech.esper.support.view.olap.SupportCubeFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestCubeCellStringRenderer extends TestCase
{
    public void testRender()
    {
        Cube testCube = SupportCubeFactory.make2DimCube();

        Map<String, Double> result = CubeCellStringRenderer.renderCube(testCube);

        assertTrue(result.size() == 12 * 3);    // Times 3 because 3 derived values are part of the test cube

        for (Map.Entry<String, Double> entry : result.entrySet())
        {
            log.debug(".testRender " + entry.getKey() + "=" + entry.getValue());
        }        
    }

    private static final Log log = LogFactory.getLog(TestCubeCellStringRenderer.class);
}
