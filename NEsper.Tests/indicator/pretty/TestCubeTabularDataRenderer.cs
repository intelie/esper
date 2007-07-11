using System;
using System.Collections.Generic;
using System.Data;

using net.esper.compat;
using net.esper.support.view.olap;
using net.esper.view.stat.olap;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.indicator.pretty
{
	[TestFixture]
	public class TestCubeTabularDataRenderer
	{
		/// <summary>
		/// Basic smoke test only - this is mostly string rendering which is not checked individually
		/// </summary>

		[Test]
		public void testRender()
		{
			Cube cube = SupportCubeFactory.make1DimCube();
			IDictionary<String, DataTable> result = CubeTabularDataRenderer.RenderCube( cube );
			Assert.IsTrue( result.Count == 1 );

			cube = SupportCubeFactory.make2DimCube();
			result = CubeTabularDataRenderer.RenderCube( cube );
			Assert.IsTrue( result.Count == 1 );

			cube = SupportCubeFactory.make3DimCube();
			result = CubeTabularDataRenderer.RenderCube( cube );
			Assert.IsTrue( result.Count == 3 );
		}
	}
}
