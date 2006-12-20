package net.esper.indicator.jmx;

import junit.framework.TestCase;
import net.esper.support.view.olap.SupportCubeFactory;

public class TestJMXLastCubeElementDynamicMBean extends TestCase
{
    /**
     * These tests are smoke tests - they do not check actual rendering on a JMX console
     */
    public void testCubeSetup()
    {
        JMXLastCubeElementDynamicMBean mBean = new JMXLastCubeElementDynamicMBean();

        mBean.setCube(SupportCubeFactory.make1DimCube());
        mBean.setCube(SupportCubeFactory.make2DimCube());
        mBean.setCube(SupportCubeFactory.make3DimCube());
    }
}
