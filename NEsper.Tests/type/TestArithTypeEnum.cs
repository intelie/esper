using System;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.type
{

    [TestFixture]
    public class TestArithTypeEnum
    {
        [Test]
        public virtual void testAddDouble()
        {
            MathArithTypeEnum.Computer computer = MathArithTypeEnum.ADD.getComputer(typeof(Double));
            Assert.AreEqual(12.1d, computer(5.5, 6.6));
        }

        [Test]
        public virtual void testInvalidGetComputer()
        {
            // Since we only do Double, Float, Integer and Long as results
            tryInvalid(typeof(String));
            tryInvalid(typeof(long));
            tryInvalid(typeof(short));
            tryInvalid(typeof(sbyte));
        }

        [Test]
        public virtual void testAllComputers()
        {
            Type[] testClasses = new Type[]{
                typeof(Single),
                typeof(Double), 
                typeof(Int32), 
                typeof(Int64)
            };

            foreach (Type testType in testClasses)
            {
                foreach (MathArithTypeEnum type in MathArithTypeEnum.Values)
                {
                    MathArithTypeEnum.Computer computer = type.getComputer(testType);
                    ValueType result = computer(3, 4);
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
                        if ((testType == typeof(Int32)) || (testType == typeof(Int64)))
                        {
                            Assert.AreEqual(0d, Convert.ToDouble(result), "clazz=" + testType);
                        }
                        else
                        {
                            Assert.AreEqual(3 / 4d, Convert.ToDouble(result), "clazz=" + testType);
                        }
                    }
                }
            }
        }

        private void tryInvalid(Type clazz)
        {
            try
            {
                MathArithTypeEnum.ADD.getComputer(clazz);
                Assert.Fail();
            }
            catch (ArgumentException ex)
            {
                // Expected
            }
        }
    }
}
