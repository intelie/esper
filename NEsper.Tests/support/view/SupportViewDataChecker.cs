using System;

using net.esper.events;
using net.esper.support.util;

namespace net.esper.support.view
{
	
	public class SupportViewDataChecker
	{
		/// <summary> Compare the new data captured by the child against expected values in the exact same order.
		/// Clears the last new data in the test child view after comparing.
		/// </summary>
		/// <param name="testChildView">is the child view
		/// </param>
		/// <param name="expectedValues">are the expected values
		/// </param>
		public static void  checkNewData(SupportBaseView testChildView, EventBean[] expectedValues)
		{
			EventBean[] newData = testChildView.LastNewData;
			ArrayAssertionUtil.assertEqualsExactOrder(newData, expectedValues);
			testChildView.clearLastNewData();
		}
		
		/// <summary> Compare the old data captured by the child against expected values in the exact same order.
		/// Clears the last old data in the test child view after comparing.
		/// </summary>
		/// <param name="testChildView">is the child view
		/// </param>
		/// <param name="expectedValues">are the expected values
		/// </param>
		public static void  checkOldData(SupportBaseView testChildView, EventBean[] expectedValues)
		{
			EventBean[] oldData = testChildView.LastOldData;
			ArrayAssertionUtil.assertEqualsExactOrder(oldData, expectedValues);
			testChildView.clearLastOldData();
		}
		
		/// <summary> Compare the new data captured by the child against expected values in the exact same order.
		/// Clears the last new data in the test child view after comparing.
		/// </summary>
		/// <param name="updateListener">is the update listener caching the results
		/// </param>
		/// <param name="expectedValues">are the expected values
		/// </param>
		public static void  checkNewData(SupportUpdateListener updateListener, EventBean[] expectedValues)
		{
			EventBean[] newData = updateListener.LastNewData;
			ArrayAssertionUtil.assertEqualsExactOrder(newData, expectedValues);
			updateListener.LastNewData = null;
		}
		
		/// <summary> Compare the old data captured by the child against expected values in the exact same order.
		/// Clears the last old data in the test child view after comparing.
		/// </summary>
		/// <param name="updateListener">is the update listener caching the results
		/// </param>
		/// <param name="expectedValues">are the expected values
		/// </param>
		public static void  checkOldData(SupportUpdateListener updateListener, EventBean[] expectedValues)
		{
			EventBean[] oldData = updateListener.LastOldData;
			ArrayAssertionUtil.assertEqualsExactOrder(oldData, expectedValues);
			updateListener.LastOldData = null;
		}
	}
}
