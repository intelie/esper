using System;

using net.esper.eql.join.rep;
using net.esper.events;
using net.esper.support.eql.join;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.assemble
{
	
	[TestFixture]
	public class TestSingleRequiredAssemblyNode 
	{
		private SupportJoinProcNode parentNode;
		private BranchRequiredAssemblyNode reqNode;
		
		[SetUp]
		public virtual void  setUp()
		{
			reqNode = new BranchRequiredAssemblyNode(1, 3);
			parentNode = new SupportJoinProcNode(- 1, 3);
			parentNode.AddChild(reqNode);
		}
		
		[Test]
		public virtual void  testProcess()
		{
			// the node does nothing when asked to process as it doesn't originate events
		}
		
		[Test]
		public virtual void  testChildResult()
		{
			TestSingleOptionalAssemblyNode.testChildResult(reqNode, parentNode);
		}
	}
}
