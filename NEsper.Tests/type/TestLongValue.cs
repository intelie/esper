using System;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.type
{
    [TestFixture]
    public class TestLongValue
    {
        [Test]
        public void testLong()
        {
            LongValue lvp = new LongValue();

            Assert.IsTrue(lvp.ValueObject == null);
            lvp.Parse("10");
            Assert.IsTrue(lvp.ValueObject.Equals(10L));
            Assert.IsTrue(lvp.GetLong() == 10L);
            lvp._Long = 200L;
            Assert.IsTrue(lvp.GetLong() == 200L);
            Assert.IsTrue(lvp.ValueObject.Equals(200L));

            try
            {
                lvp._Boolean = false;
                Assert.IsTrue(false);
            }
            catch (System.Exception ex)
            {
                // Expected exception
            }

            try
            {
                lvp._Int = 20;
                Assert.IsTrue(false);
            }
            catch (System.Exception ex)
            {
                // Expected exception
            }

            try
            {
                lvp._String = "test";
                Assert.IsTrue(false);
            }
            catch (System.Exception ex)
            {
                // Expected exception
            }

            try
            {
                lvp = new LongValue();
                lvp.GetLong();
            }
            catch (System.Exception ex)
            {
                // Expected exception
            }
        }

        [Test]
        public void testParseLong()
        {
            tryValid("0", 0);
            tryValid("11", 11);
            tryValid("12l", 12);
            tryValid("+234", 234);
            tryValid("29349349L", 29349349);
            tryValid("+29349349L", 29349349);
            tryValid("-2993L", -2993);
            tryValid("-1l", -1);

            tryInvalid("++0");
            tryInvalid("-+0");
            tryInvalid("0s");
            tryInvalid("");
            tryInvalid("l");
            tryInvalid("L");
            tryInvalid(null);
        }

        private void tryValid(String strLong, long expected)
        {
            long result = LongValue.ParseString(strLong);
            Assert.IsTrue(result == expected);
        }

        private void tryInvalid(String strLong)
        {
            try
            {
                LongValue.ParseString(strLong);
                Assert.IsTrue(false);
            }
            catch (System.Exception ex)
            {
                log.Debug("Expected exception caught, msg=" + ex.Message);
            }
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
