///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.view;
using net.esper.view;
using net.esper.view.stat.olap;

namespace net.esper.view.stat
{
	[TestFixture]
	public class TestMultiDimStatsView
	{
	    private SupportStreamImpl parentStream;
	    private SupportSchemaNeutralView childView;
	    private String[] derivedFields;

	    [SetUp]
	    public void SetUp()
	    {
	        parentStream = new SupportStreamImpl(typeof(SupportBean), 3);
	        childView = new SupportSchemaNeutralView();
            derivedFields = new String[] { ViewFieldEnum.UNIVARIATE_STATISTICS__COUNT.Name };
	    }

	    [Test]
	    public void TestOneDim()
	    {
	        MultiDimStatsView olapView = new MultiDimStatsView(SupportStatementContextFactory.MakeContext(), derivedFields, "intPrimitive", "enumValue", null, null);
	        parentStream.AddView(olapView);
	        olapView.AddView(childView);

	        InsertEvents();

	        // Check members and values
	        MultidimCube<BaseStatisticsBean> cube = olapView.FactCube;
	        Assert.AreEqual(3, cube.GetMembers(0).Count);
	        Assert.AreEqual(SupportEnum.ENUM_VALUE_1, cube.GetMembers(0)[0]);
	        Assert.AreEqual(SupportEnum.ENUM_VALUE_3, cube.GetMembers(0)[2]);
	        Assert.AreEqual(3, cube.Cells.Length);
	        Assert.AreEqual(45d, cube.Cells[0].XSum);
	        Assert.AreEqual(0d, cube.Cells[1].XSum);
	        Assert.AreEqual(-1d, cube.Cells[2].XSum);    // the 10 value was old data as stream depth is 3

	        CheckZero(cube.Cells, new int[] {0, 1, 2});

	        // Check schema
	        Assert.IsTrue(childView.LastNewData.Length == 1);
	        EventBean postedData = childView.LastNewData[0];
            Assert.IsTrue(postedData[ViewFieldEnum.MULTIDIM_OLAP__CUBE.Name] is Cube);
	    }

	    [Test]
	    public void TestTwoDim()
	    {
	        MultiDimStatsView olapView = new MultiDimStatsView(SupportStatementContextFactory.MakeContext(), derivedFields, "intPrimitive", "string", "enumValue", null);
	        parentStream.AddView(olapView);
	        olapView.AddView(childView);

	        InsertEvents();

	        // Check members and values
	        MultidimCube<BaseStatisticsBean> cube = olapView.FactCube;

	        Assert.AreEqual(4, cube.GetMembers(0).Count);
	        Assert.AreEqual("d", cube.GetMembers(0)[3]);
	        Assert.AreEqual(3, cube.GetMembers(1).Count);
	        Assert.AreEqual(SupportEnum.ENUM_VALUE_3, cube.GetMembers(1)[2]);

	        Assert.AreEqual(12, cube.Cells.Length);
	        Assert.AreEqual(-2d, cube.Cells[0 + 2 * 4].XSum);      // a-e3
	        Assert.AreEqual(1d, cube.Cells[3 + 2 * 4].XSum);      // d-e3
	        Assert.AreEqual(45d, cube.Cells[2 + 0 * 4].XSum);      // c-e1

	        CheckZero(cube.Cells, new int[] {0 + 2 * 4, 3 + 2 * 4, 2 + 0 * 4});
	    }

	    [Test]
	    public void TestThreeDim()
	    {
	        MultiDimStatsView olapView = new MultiDimStatsView(SupportStatementContextFactory.MakeContext(), derivedFields, "intPrimitive", "boolBoxed", "string", "enumValue");
	        parentStream.AddView(olapView);
	        olapView.AddView(childView);

	        InsertEvents();

	        // Check members and values
	        MultidimCube<BaseStatisticsBean> cube = olapView.FactCube;

	        Assert.AreEqual(2, cube.GetMembers(0).Count);
	        Assert.AreEqual(false, cube.GetMembers(0)[0]);
	        Assert.AreEqual(true, cube.GetMembers(0)[1]);
	        Assert.AreEqual(4, cube.GetMembers(1).Count);
	        Assert.AreEqual("c", cube.GetMembers(1)[2]);
	        Assert.AreEqual(3, cube.GetMembers(2).Count);
	        Assert.AreEqual(SupportEnum.ENUM_VALUE_2, cube.GetMembers(2)[1]);

	        Assert.AreEqual(24, cube.Cells.Length);
	        Assert.AreEqual(-2d, cube.Cells[1 + 0 * 2 + 2 * 8].XSum);      // true-a-e3
	        Assert.AreEqual(1d, cube.Cells[0 + 3 * 2 + 2 * 8].XSum);      // false-d-e3
	        Assert.AreEqual(45d, cube.Cells[1 + 2 * 2 + 0 * 8].XSum);      // true-c-e1

	        CheckZero(cube.Cells, new int[] {1 + 0 * 2 + 2 * 8,  0 + 3 * 2 + 2 * 8, 1 + 2 * 2 + 0 * 8});
	    }

	    private void CheckZero (BaseStatisticsBean[] facts, int[] exceptions)
	    {
	        Array.Sort(exceptions);
	        for (int i = 0; i < facts.Length; i++)
	        {
	            if (Array.BinarySearch(exceptions, i) >= 0)
	            {
	                continue;
	            }
	            Assert.AreEqual(0d, facts[i].XSum);
	        }
	    }

	    private void InsertEvents()
	    {
	        parentStream.Insert(MakeBean(10, SupportEnum.ENUM_VALUE_3, "a", false));
	        parentStream.Insert(MakeBean(5, SupportEnum.ENUM_VALUE_2,  "b", false));
	        parentStream.Insert(MakeBean(55, SupportEnum.ENUM_VALUE_1, "c", true));
	        parentStream.Insert(MakeBean(45, SupportEnum.ENUM_VALUE_1, "c", true));
	        parentStream.Insert(MakeBean(1, SupportEnum.ENUM_VALUE_3,  "d", false));
	        parentStream.Insert(MakeBean(-2, SupportEnum.ENUM_VALUE_3, "a", true));
	    }

	    private EventBean MakeBean(int intPrimitive, SupportEnum enumValue, String stringValue, bool boolValue)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetIntPrimitive(intPrimitive);
	        bean.SetEnumValue(enumValue);
	        bean.SetString(stringValue);
	        bean.SetBoolBoxed(boolValue);
	        return SupportEventBeanFactory.CreateObject(bean);
	    }
	}
} // End of namespace
