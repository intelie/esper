using System;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.type
{

    [TestFixture]
    public class TestArithTypeEnum
    {
        [Test]
        public void testAddDouble()
        {
            MathArithTypeEnum.Computer computer = MathArithTypeEnum.ADD.GetComputer(typeof(double?));
            Assert.AreEqual(12.1d, computer(5.5, 6.6));
        }

        [Test]
        public void testInvalidGetComputer()
        {
            // Since we only do double?, Float, Integer and Long as results
            tryInvalid(typeof(String));
            tryInvalid(typeof(long));
            tryInvalid(typeof(short));
            tryInvalid(typeof(sbyte));
        }

        [Test]
        public void testAllComputers()
        {
            Type[,] testClasses = new Type[,]{
                { typeof(float), typeof(float?) },
                { typeof(double), typeof(double?) },
                { typeof(int), typeof(int?) },
                { typeof(long), typeof(long?) }
            };

            int length = testClasses.Length / 2;
            for( int ii = 0 ; ii < length ; ii++ )
            {
                Type testType = testClasses[ii,0] ;
                Type nullType = testClasses[ii,1];

                foreach (MathArithTypeEnum type in MathArithTypeEnum.Values)
                {
                    MathArithTypeEnum.Computer computer = type.GetComputer(nullType);
                    Object result = computer(3, 4);
                    Assert.AreEqual(testType, result.GetType());

                    if (type == MathArithTypeEnum.ADD)
                    {
                        Assert.AreEqual(7d, Convert.ToDouble(result));
                    }
                    if (type == MathArithTypeEnum.SUBTRACT)
                    {
                        Assert.AreEqual(-1d, Convert.ToDouble(result));
                    }
                    if (type == MathArithTypeEnum.MULTIPLY)
                    {
                        Assert.AreEqual(12d, Convert.ToDouble(result));
                    }
                    if (type == MathArithTypeEnum.DIVIDE)
                    {
                        if ((testType == typeof(int)) || (testType == typeof(long)))
                        {
                            Assert.AreEqual(0d, Convert.ToDouble(result), "clazz=" + testType);
                        }
                        else
                        {
                            Assert.AreEqual(3.0d / 4.0d, Convert.ToDouble(result), "clazz=" + testType);
                        }
                    }
                }
            }
        }

        private void tryInvalid(Type clazz)
        {
            try
            {
                MathArithTypeEnum.ADD.GetComputer(clazz);
                Assert.Fail();
            }
            catch (ArgumentException ex)
            {
                // Expected
            }
        }
    }
}
