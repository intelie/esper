using System;

using net.esper.events;
using net.esper.filter;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.filter;
using net.esper.view;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.view.stream
{
	
	[TestFixture]
	public class TestStreamServiceImpl 
	{
		private StreamReuseService streamReuseService;
		private SupportFilterServiceImpl supportFilterService;
		
		private FilterSpec[] filterSpecs;
		private EventStream[] streams;
		
		[SetUp]
		public virtual void  setUp()
		{
			streamReuseService = new StreamReuseServiceImpl();
			EventType eventType = SupportEventTypeFactory.createBeanType(typeof(SupportBean));
			supportFilterService = new SupportFilterServiceImpl();
			
			filterSpecs = new FilterSpec[3];
			filterSpecs[0] = SupportFilterSpecBuilder.build(eventType, new Object[]{"StringValue", FilterOperator.EQUAL, "a"});
			filterSpecs[1] = SupportFilterSpecBuilder.build(eventType, new Object[]{"StringValue", FilterOperator.EQUAL, "a"});
			filterSpecs[2] = SupportFilterSpecBuilder.build(eventType, new Object[]{"StringValue", FilterOperator.EQUAL, "b"});
			
			streams = new EventStream[4];
			streams[0] = streamReuseService.createStream(filterSpecs[0], supportFilterService);
			streams[1] = streamReuseService.createStream(filterSpecs[0], supportFilterService);
			streams[2] = streamReuseService.createStream(filterSpecs[1], supportFilterService);
			streams[3] = streamReuseService.createStream(filterSpecs[2], supportFilterService);
		}
		
		[Test]
		public virtual void  testCreate()
		{
			// Streams are reused
			Assert.AreSame(streams[0], streams[1]);
			Assert.AreSame(streams[0], streams[2]);
			Assert.AreNotSame(streams[0], streams[3]);
			
			// Type is ok
			Assert.AreEqual(typeof(SupportBean), streams[0].EventType.UnderlyingType);
			
			// 2 filters are active now
			Assert.AreEqual(2, supportFilterService.getAdded().Count);
		}
		
		[Test]
		public virtual void  testDrop()
		{
			streamReuseService.dropStream(filterSpecs[0], supportFilterService);
			streamReuseService.dropStream(filterSpecs[1], supportFilterService);
			Assert.AreEqual(0, supportFilterService.getRemoved().Count);
			
			// Filter removed
			streamReuseService.dropStream(filterSpecs[0], supportFilterService);
			Assert.AreEqual(1, supportFilterService.getRemoved().Count);
			
			streamReuseService.dropStream(filterSpecs[2], supportFilterService);
			Assert.AreEqual(2, supportFilterService.getRemoved().Count);
			
			// Something already removed
			try
			{
				streamReuseService.dropStream(filterSpecs[2], supportFilterService);
				Assert.Fail();
			}
			catch (System.SystemException ex)
			{
				// Expected
			}
		}
	}
}
