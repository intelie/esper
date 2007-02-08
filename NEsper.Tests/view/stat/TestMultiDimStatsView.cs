using System;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.view;
using net.esper.view;
using net.esper.view.stat.olap;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.view.stat
{
	
	[TestFixture]
	public class TestMultiDimStatsView 
	{
		private SupportStreamImpl parentStream;
		private SupportSchemaNeutralView childView;
		private String[] derivedFields;
		
		[SetUp]
		public virtual void  setUp()
		{
			parentStream = new SupportStreamImpl(typeof(SupportBean), 3);
			childView = new SupportSchemaNeutralView();
			derivedFields = new String[]{ViewFieldEnum.UNIVARIATE_STATISTICS__COUNT.Name};
		}
		
		[Test]
		public virtual void  testOneDim()
		{
			MultiDimStatsView olapView = new MultiDimStatsView(derivedFields, "IntPrimitive", "enumValue");
			parentStream.AddView(olapView);
			olapView.AddView(childView);
			olapView.ViewServiceContext = SupportViewContextFactory.makeContext();
			
			insertEvents();
			
			// Check members and values
      MultidimCube<BaseStatisticsBean> cube = olapView.FactCube;
			Assert.AreEqual(3, cube.GetMembers(0).Count);
			Assert.AreEqual(SupportEnum.ENUM_VALUE_1, cube.GetMembers(0)[0]);
			Assert.AreEqual(SupportEnum.ENUM_VALUE_3, cube.GetMembers(0)[2]);
			Assert.AreEqual(3, cube.Cells.Length);
			Assert.AreEqual(45d, cube.Cells[0].XSum);
			Assert.AreEqual(0d, cube.Cells[1].XSum);
			Assert.AreEqual(- 1d, cube.Cells[2].XSum); // the 10 value was old data as stream depth is 3
			
			checkZero(cube.Cells, new int[]{0, 1, 2});
			
			// Check schema
			Assert.IsTrue(childView.LastNewData.Length == 1);
			EventBean postedData = childView.LastNewData[0];
			Assert.IsTrue(postedData[ViewFieldEnum.MULTIDIM_OLAP__CUBE.Name] is Cube);
		}
		
		[Test]
		public virtual void  testTwoDim()
		{
			MultiDimStatsView olapView = new MultiDimStatsView(derivedFields, "IntPrimitive", "string", "enumValue");
			parentStream.AddView(olapView);
			olapView.AddView(childView);
			olapView.ViewServiceContext = SupportViewContextFactory.makeContext();
			
			insertEvents();
			
			// Check members and values
      MultidimCube<BaseStatisticsBean> cube = olapView.FactCube;
			
			Assert.AreEqual(4, cube.GetMembers(0).Count);
			Assert.AreEqual("d", cube.GetMembers(0)[3]);
			Assert.AreEqual(3, cube.GetMembers(1).Count);
			Assert.AreEqual(SupportEnum.ENUM_VALUE_3, cube.GetMembers(1)[2]);
			
			Assert.AreEqual(12, cube.Cells.Length);
			Assert.AreEqual(- 2d, cube.Cells[0 + 2 * 4].XSum); // a-e3
			Assert.AreEqual(1d, cube.Cells[3 + 2 * 4].XSum); // d-e3
			Assert.AreEqual(45d, cube.Cells[2 + 0 * 4].XSum); // c-e1
			
			checkZero(cube.Cells, new int[]{0 + 2 * 4, 3 + 2 * 4, 2 + 0 * 4});
		}
		
		[Test]
		public virtual void  testThreeDim()
		{
			MultiDimStatsView olapView = new MultiDimStatsView(
                derivedFields, 
                "IntPrimitive",
                "BoolBoxed",
                "String",
                "EnumValue");
			parentStream.AddView(olapView);
			olapView.AddView(childView);
			olapView.ViewServiceContext = SupportViewContextFactory.makeContext();
			
			insertEvents();
			
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
			Assert.AreEqual(- 2d, cube.Cells[1 + 0 * 2 + 2 * 8].XSum); // true-a-e3
			Assert.AreEqual(1d, cube.Cells[0 + 3 * 2 + 2 * 8].XSum); // false-d-e3
			Assert.AreEqual(45d, cube.Cells[1 + 2 * 2 + 0 * 8].XSum); // true-c-e1
			
			checkZero(cube.Cells, new int[]{1 + 0 * 2 + 2 * 8, 0 + 3 * 2 + 2 * 8, 1 + 2 * 2 + 0 * 8});
		}
		
		[Test]
		public virtual void  testAttachesTo()
		{
			MultiDimStatsView olapView = new MultiDimStatsView(derivedFields, "IntPrimitive", "dummy");
			Assert.IsTrue(olapView.AttachesTo(parentStream) != null);
			
			olapView = new MultiDimStatsView(derivedFields, "dummy", "IntPrimitive");
			Assert.IsTrue(olapView.AttachesTo(parentStream) != null);

            olapView = new MultiDimStatsView(derivedFields, "StringValue", "IntPrimitive");
			Assert.IsTrue(olapView.AttachesTo(parentStream) != null);

            olapView = new MultiDimStatsView(derivedFields, "IntPrimitive", "StringValue", "dummy");
			Assert.IsTrue(olapView.AttachesTo(parentStream) != null);

            olapView = new MultiDimStatsView(derivedFields, "IntPrimitive", "StringValue", "DoublePrimitive", "dummy");
			Assert.IsTrue(olapView.AttachesTo(parentStream) != null);

            olapView = new MultiDimStatsView(derivedFields, "IntPrimitive", "StringValue", "DoubleBoxed", "DoublePrimitive");
			Assert.IsTrue(olapView.AttachesTo(parentStream) == null);
			
			// Try invalid derived fields
			olapView = new MultiDimStatsView(new String[]{"goodie"}, "IntPrimitive", "StringValue");
			Assert.IsTrue(olapView.AttachesTo(parentStream) != null);
			
			olapView = new MultiDimStatsView(new String[]{"count", "goodie"}, "IntPrimitive", "StringValue");
			Assert.IsTrue(olapView.AttachesTo(parentStream) != null);
		}
		
		private void  checkZero(BaseStatisticsBean[] facts, int[] exceptions)
		{
			System.Array.Sort(exceptions);
			for (int i = 0; i < facts.Length; i++)
			{
				if (System.Array.BinarySearch(exceptions, (Object) i) >= 0)
				{
					continue;
				}
				Assert.AreEqual(0d, facts[i].XSum);
			}
		}
		
		private void  insertEvents()
		{
            parentStream.Insert(makeBean(10, SupportEnum.ENUM_VALUE_3, "a", false));
            parentStream.Insert(makeBean(5, SupportEnum.ENUM_VALUE_2, "b", false));
            parentStream.Insert(makeBean(55, SupportEnum.ENUM_VALUE_1, "c", true));
            parentStream.Insert(makeBean(45, SupportEnum.ENUM_VALUE_1, "c", true));
            parentStream.Insert(makeBean(1, SupportEnum.ENUM_VALUE_3, "d", false));
            parentStream.Insert(makeBean(-2, SupportEnum.ENUM_VALUE_3, "a", true));
		}
		
		private EventBean makeBean(int intPrimitive, SupportEnum enumValue, String stringValue, bool boolValue)
		{
			SupportBean bean = new SupportBean();
			bean.IntPrimitive = intPrimitive;
			bean.EnumValue = enumValue;
			bean.StringValue = stringValue;
			bean.BoolBoxed = boolValue;
			return SupportEventBeanFactory.createObject(bean);
		}
	}
}
