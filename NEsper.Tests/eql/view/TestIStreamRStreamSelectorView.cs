using System;

using net.esper.eql.spec;
using net.esper.events;
using net.esper.support.events;
using net.esper.support.view;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.view
{
	[TestFixture]
	public class TestIStreamRStreamSelectorView 
	{
		private IStreamRStreamSelectorView viewIStream;
		private IStreamRStreamSelectorView viewRStream;
		private IStreamRStreamSelectorView viewBoth;
		private SupportMapView childView = new SupportMapView();
		
		[SetUp]
		public virtual void  setUp()
		{
			viewIStream = new IStreamRStreamSelectorView(SelectClauseStreamSelectorEnum.ISTREAM_ONLY);
			viewRStream = new IStreamRStreamSelectorView(SelectClauseStreamSelectorEnum.RSTREAM_ONLY);
			viewBoth = new IStreamRStreamSelectorView(SelectClauseStreamSelectorEnum.RSTREAM_ISTREAM_BOTH);
			
			childView = new SupportMapView();
			viewIStream.AddView(childView);
			viewRStream.AddView(childView);
			viewBoth.AddView(childView);
		}
		
		[Test]
		public virtual void  testUpdate()
		{
			EventBean[] eventsOld = SupportEventBeanFactory.MakeEvents(new String[]{"a", "b"});
			EventBean[] eventsNew = SupportEventBeanFactory.MakeEvents(new String[]{"c", "d"});
			
			viewIStream.Update(eventsNew, eventsOld);
			Assert.AreSame(eventsNew, childView.LastNewData);
			Assert.IsNull(childView.LastOldData);
			childView.Reset();
			
			viewRStream.Update(eventsNew, eventsOld);
			Assert.AreSame(eventsOld, childView.LastNewData);
			Assert.IsNull(childView.LastOldData);
			childView.Reset();
			
			viewBoth.Update(eventsNew, eventsOld);
			Assert.AreSame(eventsOld, childView.LastOldData);
			Assert.AreSame(eventsNew, childView.LastNewData);
			childView.Reset();
		}
	}
}
