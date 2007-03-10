package net.esper.view.stat;

import junit.framework.TestCase;

import java.util.Arrays;

import net.esper.support.view.SupportStreamImpl;
import net.esper.support.view.SupportSchemaNeutralView;
import net.esper.support.view.SupportStatementContextFactory;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportEnum;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.view.ViewFieldEnum;
import net.esper.view.stat.olap.Cube;
import net.esper.view.stat.olap.MultidimCube;
import net.esper.event.EventBean;

public class TestMultiDimStatsView extends TestCase
{
    private SupportStreamImpl parentStream;
    private SupportSchemaNeutralView childView;
    private String[] derivedFields;

    public void setUp()
    {
        parentStream = new SupportStreamImpl(SupportBean.class, 3);
        childView = new SupportSchemaNeutralView();
        derivedFields = new String[] {ViewFieldEnum.UNIVARIATE_STATISTICS__COUNT.getName()};
    }

    public void testOneDim()
    {
        MultiDimStatsView olapView = new MultiDimStatsView(SupportStatementContextFactory.makeContext(), derivedFields, "intPrimitive", "enumValue", null, null);
        parentStream.addView(olapView);
        olapView.addView(childView);

        insertEvents();

        // Check members and values
        MultidimCube<BaseStatisticsBean> cube = olapView.getFactCube();
        assertEquals(3, cube.getMembers(0).size());
        assertEquals(SupportEnum.ENUM_VALUE_1, cube.getMembers(0).get(0));
        assertEquals(SupportEnum.ENUM_VALUE_3, cube.getMembers(0).get(2));
        assertEquals(3, cube.getCells().length);
        assertEquals(45d, cube.getCells()[0].getXSum());
        assertEquals(0d, cube.getCells()[1].getXSum());
        assertEquals(-1d, cube.getCells()[2].getXSum());    // the 10 value was old data as stream depth is 3

        checkZero(cube.getCells(), new int[] {0, 1, 2});

        // Check schema
        assertTrue(childView.getLastNewData().length == 1);
        EventBean postedData = childView.getLastNewData()[0];
        assertTrue(postedData.get(ViewFieldEnum.MULTIDIM_OLAP__CUBE.getName()) instanceof Cube);
    }

    public void testTwoDim()
    {
        MultiDimStatsView olapView = new MultiDimStatsView(SupportStatementContextFactory.makeContext(), derivedFields, "intPrimitive", "string", "enumValue", null);
        parentStream.addView(olapView);
        olapView.addView(childView);

        insertEvents();

        // Check members and values
        MultidimCube<BaseStatisticsBean> cube = olapView.getFactCube();

        assertEquals(4, cube.getMembers(0).size());
        assertEquals("d", cube.getMembers(0).get(3));
        assertEquals(3, cube.getMembers(1).size());
        assertEquals(SupportEnum.ENUM_VALUE_3, cube.getMembers(1).get(2));

        assertEquals(12, cube.getCells().length);
        assertEquals(-2d, cube.getCells()[0 + 2 * 4].getXSum());      // a-e3
        assertEquals(1d, cube.getCells()[3 + 2 * 4].getXSum());      // d-e3
        assertEquals(45d, cube.getCells()[2 + 0 * 4].getXSum());      // c-e1

        checkZero(cube.getCells(), new int[] {0 + 2 * 4, 3 + 2 * 4, 2 + 0 * 4});
    }

    public void testThreeDim()
    {
        MultiDimStatsView olapView = new MultiDimStatsView(SupportStatementContextFactory.makeContext(), derivedFields, "intPrimitive", "boolBoxed", "string", "enumValue");
        parentStream.addView(olapView);
        olapView.addView(childView);

        insertEvents();

        // Check members and values
        MultidimCube<BaseStatisticsBean> cube = olapView.getFactCube();

        assertEquals(2, cube.getMembers(0).size());
        assertEquals(false, cube.getMembers(0).get(0));
        assertEquals(true, cube.getMembers(0).get(1));
        assertEquals(4, cube.getMembers(1).size());
        assertEquals("c", cube.getMembers(1).get(2));
        assertEquals(3, cube.getMembers(2).size());
        assertEquals(SupportEnum.ENUM_VALUE_2, cube.getMembers(2).get(1));

        assertEquals(24, cube.getCells().length);
        assertEquals(-2d, cube.getCells()[1 + 0 * 2 + 2 * 8].getXSum());      // true-a-e3
        assertEquals(1d, cube.getCells()[0 + 3 * 2 + 2 * 8].getXSum());      // false-d-e3
        assertEquals(45d, cube.getCells()[1 + 2 * 2 + 0 * 8].getXSum());      // true-c-e1

        checkZero(cube.getCells(), new int[] {1 + 0 * 2 + 2 * 8,  0 + 3 * 2 + 2 * 8, 1 + 2 * 2 + 0 * 8});
    }

    private void checkZero (BaseStatisticsBean[] facts, int[] exceptions)
    {
        Arrays.sort(exceptions);
        for (int i = 0; i < facts.length; i++)
        {
            if (Arrays.binarySearch(exceptions, i) >= 0)
            {
                continue;
            }
            assertEquals(0d, facts[i].getXSum());
        }
    }

    private void insertEvents()
    {
        parentStream.insert(makeBean(10, SupportEnum.ENUM_VALUE_3, "a", false));
        parentStream.insert(makeBean(5, SupportEnum.ENUM_VALUE_2,  "b", false));
        parentStream.insert(makeBean(55, SupportEnum.ENUM_VALUE_1, "c", true));
        parentStream.insert(makeBean(45, SupportEnum.ENUM_VALUE_1, "c", true));
        parentStream.insert(makeBean(1, SupportEnum.ENUM_VALUE_3,  "d", false));
        parentStream.insert(makeBean(-2, SupportEnum.ENUM_VALUE_3, "a", true));
    }

    private EventBean makeBean(int intPrimitive, SupportEnum enumValue, String stringValue, boolean boolValue)
    {
        SupportBean bean = new SupportBean();
        bean.setIntPrimitive(intPrimitive);
        bean.setEnumValue(enumValue);
        bean.setString(stringValue);
        bean.setBoolBoxed(boolValue);
        return SupportEventBeanFactory.createObject(bean);
    }
}
