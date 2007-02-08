using System;
using System.Collections.Generic;

using net.esper.compat;

namespace net.esper.regression.support
{
    public sealed class CaseList
    {
        public int NumTests
        {
            get { return results.Count; }
        }

		public IList<EventExpressionCase> Results
		{
			get { return results; }
		}

        private List<EventExpressionCase> results;

        public CaseList()
        {
            results = new List<EventExpressionCase>();
        }

        public void AddTest(EventExpressionCase desc)
        {
            results.Add(desc);
        }
    }
}
