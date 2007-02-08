using System;

using net.esper.events;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.table
{
	
	[TestFixture]
	public class TestUnindexedEventTable 
	{
		[Test]
		public virtual void  testFlow()
		{
			UnindexedEventTable rep = new UnindexedEventTable(1);
			
			EventBean[] addOne = SupportEventBeanFactory.MakeEvents(new String[]{"a", "b"});
			rep.Add(addOne);
			rep.Remove(new EventBean[]{addOne[0]});
		}
	}
}
