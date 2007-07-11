using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.support.view.olap;
using net.esper.view.stat.olap;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.indicator.pretty
{
	[TestFixture]
	public class TestCubeCellStringRenderer
	{
		[Test]
		public void testRender()
		{
			Cube testCube = SupportCubeFactory.make2DimCube();

			IDictionary<String, double> result = CubeCellStringRenderer.RenderCube( testCube );

			Assert.IsTrue( result.Count == 12 * 3 ); // Times 3 because 3 derived values are part of the test cube

			foreach ( KeyValuePair<String, double> entry in result )
			{
				log.Debug( ".testRender " + entry.Key + "=" + entry.Value );
			}
		}

		private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
