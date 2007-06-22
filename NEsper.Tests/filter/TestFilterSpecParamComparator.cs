using System;

using net.esper.compat;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.filter
{

    [TestFixture]
    public class TestFilterSpecParamComparator
    {
        private FilterSpecParamComparator comparator;

        [SetUp]
        public virtual void setUp()
        {
            comparator = new FilterSpecParamComparator();
        }

        [Test]
        public virtual void testCompareOneByOne()
        {
            FilterValueSetParamImpl param1 = new FilterValueSetParamImpl("a", FilterOperator.EQUAL, null);
            FilterValueSetParamImpl param2 = new FilterValueSetParamImpl("b", FilterOperator.EQUAL, null);
            FilterValueSetParamImpl param3 = new FilterValueSetParamImpl("c", FilterOperator.EQUAL, null);
            FilterValueSetParamImpl param4 = new FilterValueSetParamImpl("d", FilterOperator.RANGE_CLOSED, null);
            FilterValueSetParamImpl param5 = new FilterValueSetParamImpl("e", FilterOperator.RANGE_CLOSED, null);
            FilterValueSetParamImpl param6 = new FilterValueSetParamImpl("e", FilterOperator.RANGE_CLOSED, null);
            FilterValueSetParamImpl param7 = new FilterValueSetParamImpl("f", FilterOperator.GREATER, null);
            FilterValueSetParamImpl param8 = new FilterValueSetParamImpl("g", FilterOperator.NOT_EQUAL, null);

            // Compare same comparison types
            Assert.IsTrue(comparator.Compare(param1, param2) == -1);
            Assert.IsTrue(comparator.Compare(param2, param1) == 1);
            Assert.IsTrue(comparator.Compare(param3, param2) == 1);
            Assert.IsTrue(comparator.Compare(param2, param2) == 0);
            Assert.IsTrue(comparator.Compare(param8, param1) == 1);
            Assert.IsTrue(comparator.Compare(param1, param8) == -1);

            Assert.IsTrue(comparator.Compare(param4, param5) == -1);
            Assert.IsTrue(comparator.Compare(param5, param4) == 1);
            Assert.IsTrue(comparator.Compare(param4, param4) == 0);
            Assert.IsTrue(comparator.Compare(param5, param6) == 0);

            // Compare across comparison types
            Assert.IsTrue(comparator.Compare(param7, param6) == 1);
            Assert.IsTrue(comparator.Compare(param6, param7) == -1);

            Assert.IsTrue(comparator.Compare(param7, param1) == 1);
            Assert.IsTrue(comparator.Compare(param1, param7) == -1);

            Assert.IsTrue(comparator.Compare(param4, param1) == 1);
            Assert.IsTrue(comparator.Compare(param1, param4) == -1);
        }

        [Test]
        public virtual void testCompareAll()
        {
            ETreeSet<FilterValueSetParam> sorted = new ETreeSet<FilterValueSetParam>(comparator);

            Array enumArray = Enum.Values(typeof(FilterOperator));

            FilterValueSetParam[] spec = new FilterValueSetParam[enumArray.Length];
            for (int i = 0; i < enumArray.Length; i++)
            {
                FilterOperator op = (FilterOperator)enumArray.Value(i);
                spec[i] = new FilterValueSetParamImpl("somename", op, null);

                // Add to sorted collection
                sorted.Add(spec[i]);
            }

            Assert.AreEqual(FilterOperator.EQUAL, sorted.First.FilterOperator);
            Assert.AreEqual(FilterOperator.NOT_EQUAL, sorted.Last.FilterOperator);

            log.Debug(".testCompareAll " + CollectionHelper.Render(sorted.ToArray()));
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
