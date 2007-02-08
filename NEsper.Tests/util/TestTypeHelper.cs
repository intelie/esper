using System;

using net.esper.support.bean;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.util
{
    [TestFixture]
    public class TestTypeHelper
    {
        [Test]
        public virtual void testCoerceNumber()
        {
            Assert.AreEqual(1d, (double) TypeHelper.CoerceNumber(1d, typeof(double)));
            Assert.AreEqual(5d, (double) TypeHelper.CoerceNumber(5, typeof(double)));
            Assert.AreEqual(6d, (double) TypeHelper.CoerceNumber((sbyte)6, typeof(double)));
            Assert.AreEqual(3f, (float) TypeHelper.CoerceNumber(3L, typeof(float)));
            Assert.AreEqual((short)2, (short) TypeHelper.CoerceNumber(2L, typeof(short)));
            Assert.AreEqual(4, TypeHelper.CoerceNumber(4L, typeof(int)));
            Assert.AreEqual((sbyte)5, TypeHelper.CoerceNumber(5L, typeof(sbyte)));
            Assert.AreEqual(8L, TypeHelper.CoerceNumber(8L, typeof(long)));

            try
            {
                TypeHelper.CoerceNumber(10, typeof(int));
                Assert.Fail();
            }
            catch (ArgumentException ex)
            {
                // Expected
            }
        }

        [Test]
        public virtual void testIsNumeric()
        {
            Type[] numericClasses = new Type[]
			{
				typeof(float),
				typeof(double), 
				typeof(sbyte), 
				typeof(short), 
				typeof(int), 
				typeof(long), 
			};

            Type[] nonnumericClasses = new Type[]
			{
				typeof(string),
				typeof(bool),
				typeof(TestFixture)
			};

            foreach (Type type in numericClasses)
            {
                Assert.IsTrue(TypeHelper.IsNumeric(type));
            }

            foreach (Type type in nonnumericClasses)
            {
                Assert.IsFalse(TypeHelper.IsNumeric(type));
            }
        }

        [Test]
        public virtual void testIsBoolean()
        {
            Assert.IsTrue(TypeHelper.IsBoolean(typeof(bool)));
            Assert.IsTrue(TypeHelper.IsBoolean(typeof(bool)));
            Assert.IsFalse(TypeHelper.IsBoolean(typeof(String)));
        }

        [Test]
        public virtual void testGetCoercionType()
        {
            Assert.AreEqual(typeof(Double), TypeHelper.GetArithmaticCoercionType(typeof(Double), typeof(int)));
            Assert.AreEqual(typeof(Double), TypeHelper.GetArithmaticCoercionType(typeof(sbyte), typeof(double)));
            Assert.AreEqual(typeof(Int64), TypeHelper.GetArithmaticCoercionType(typeof(sbyte), typeof(long)));
            Assert.AreEqual(typeof(Int64), TypeHelper.GetArithmaticCoercionType(typeof(sbyte), typeof(long)));
            Assert.AreEqual(typeof(System.Single), TypeHelper.GetArithmaticCoercionType(typeof(float), typeof(long)));
            Assert.AreEqual(typeof(System.Single), TypeHelper.GetArithmaticCoercionType(typeof(sbyte), typeof(float)));
            Assert.AreEqual(typeof(Int32), TypeHelper.GetArithmaticCoercionType(typeof(sbyte), typeof(int)));
            Assert.AreEqual(typeof(Int32), TypeHelper.GetArithmaticCoercionType(typeof(Int32), typeof(int)));

            try
            {
                TypeHelper.GetArithmaticCoercionType(typeof(String), typeof(float));
                Assert.Fail();
            }
            catch (CoercionException ex)
            {
                // Expected
            }

            try
            {
                TypeHelper.GetArithmaticCoercionType(typeof(int), typeof(bool));
                Assert.Fail();
            }
            catch (CoercionException ex)
            {
                // Expected
            }
        }

        [Test]
        public virtual void testIsFloatingPointNumber()
        {
            Assert.IsTrue(TypeHelper.IsFloatingPointNumber(1d));
            Assert.IsTrue(TypeHelper.IsFloatingPointNumber(1f));
            Assert.IsTrue(TypeHelper.IsFloatingPointNumber((double)1));
            Assert.IsTrue(TypeHelper.IsFloatingPointNumber((float)1));

            Assert.IsFalse(TypeHelper.IsFloatingPointNumber(1));
            Assert.IsFalse(TypeHelper.IsFloatingPointNumber(1));
        }

        [Test]
        public virtual void testIsFloatingPointClass()
        {
            Assert.IsTrue(TypeHelper.IsFloatingPointClass(typeof(double)));
            Assert.IsTrue(TypeHelper.IsFloatingPointClass(typeof(float)));
            Assert.IsTrue(TypeHelper.IsFloatingPointClass(typeof(Double)));
            Assert.IsTrue(TypeHelper.IsFloatingPointClass(typeof(System.Single)));

            Assert.IsFalse(TypeHelper.IsFloatingPointClass(typeof(String)));
            Assert.IsFalse(TypeHelper.IsFloatingPointClass(typeof(int)));
            Assert.IsFalse(TypeHelper.IsFloatingPointClass(typeof(Int32)));
        }

        [Test]
        public virtual void testGetCompareToCoercionType()
        {
            Assert.AreEqual(typeof(String), TypeHelper.GetCompareToCoercionType(typeof(String), typeof(String)));
            Assert.AreEqual(typeof(bool), TypeHelper.GetCompareToCoercionType(typeof(bool), typeof(bool)));
            Assert.AreEqual(typeof(bool), TypeHelper.GetCompareToCoercionType(typeof(bool), typeof(bool)));
            Assert.AreEqual(typeof(bool), TypeHelper.GetCompareToCoercionType(typeof(bool), typeof(bool)));
            Assert.AreEqual(typeof(bool), TypeHelper.GetCompareToCoercionType(typeof(bool), typeof(bool)));

            Assert.AreEqual(typeof(Double), TypeHelper.GetCompareToCoercionType(typeof(int), typeof(float)));
            Assert.AreEqual(typeof(Double), TypeHelper.GetCompareToCoercionType(typeof(double), typeof(sbyte)));
            Assert.AreEqual(typeof(Double), TypeHelper.GetCompareToCoercionType(typeof(float), typeof(float)));
            Assert.AreEqual(typeof(Double), TypeHelper.GetCompareToCoercionType(typeof(float), typeof(Double)));

            Assert.AreEqual(typeof(Int64), TypeHelper.GetCompareToCoercionType(typeof(int), typeof(int)));
            Assert.AreEqual(typeof(Int64), TypeHelper.GetCompareToCoercionType(typeof(Int16), typeof(Int32)));

            tryInvalidGetRelational(typeof(String), typeof(int));
            tryInvalidGetRelational(typeof(Int64), typeof(String));
            tryInvalidGetRelational(typeof(Int64), typeof(bool));
            tryInvalidGetRelational(typeof(bool), typeof(int));
        }

        [Test]
        public virtual void testIsBuiltinDataType()
        {
            Type[] classesDataType = new Type[]
			{
                typeof(short),
				typeof(int),
				typeof(long), 
				typeof(double), 
				typeof(bool), 
				typeof(char), 
				typeof(string)
			};

            Type[] classesNotDataType = new Type[]
			{
				typeof(SupportBean),
				typeof(Math),
				typeof(Type)
			};

            for (int i = 0; i < classesDataType.Length; i++)
            {
                Assert.IsTrue(TypeHelper.IsBuiltinDataType(classesDataType[i]));
            }
            for (int i = 0; i < classesNotDataType.Length; i++)
            {
                Assert.IsFalse(TypeHelper.IsBuiltinDataType(classesNotDataType[i]));
            }
        }

        private void tryInvalidGetRelational(Type classOne, Type classTwo)
        {
            try
            {
                TypeHelper.GetCompareToCoercionType(classOne, classTwo);
                Assert.Fail();
            }
            catch (ArgumentException ex)
            {
                // Expected
            }
        }

        [Test]
        public virtual void testGetCommonCoercionType()
        {
            Assert.AreEqual(typeof(String), TypeHelper.GetCommonCoercionType(new Type[] { typeof(String) }));
            Assert.AreEqual(typeof(bool), TypeHelper.GetCommonCoercionType(new Type[] { typeof(bool) }));
            Assert.AreEqual(typeof(Int64), TypeHelper.GetCommonCoercionType(new Type[] { typeof(long) }));

            Assert.AreEqual(typeof(String), TypeHelper.GetCommonCoercionType(new Type[] { typeof(String), null }));
            Assert.AreEqual(typeof(String), TypeHelper.GetCommonCoercionType(new Type[] { typeof(String), typeof(String) }));
            Assert.AreEqual(typeof(String), TypeHelper.GetCommonCoercionType(new Type[] { typeof(String), typeof(String), typeof(String) }));
            Assert.AreEqual(typeof(String), TypeHelper.GetCommonCoercionType(new Type[] { typeof(String), typeof(String), null }));
            Assert.AreEqual(typeof(String), TypeHelper.GetCommonCoercionType(new Type[] { null, typeof(String), null }));
            Assert.AreEqual(typeof(String), TypeHelper.GetCommonCoercionType(new Type[] { null, typeof(String), typeof(String) }));
            Assert.AreEqual(typeof(String), TypeHelper.GetCommonCoercionType(new Type[] { null, null, typeof(String), typeof(String) }));

            Assert.AreEqual(typeof(bool), TypeHelper.GetCommonCoercionType(new Type[] { typeof(bool), typeof(bool) }));
            Assert.AreEqual(typeof(bool), TypeHelper.GetCommonCoercionType(new Type[] { typeof(bool), typeof(bool) }));
            Assert.AreEqual(typeof(bool), TypeHelper.GetCommonCoercionType(new Type[] { typeof(bool), typeof(bool) }));
            Assert.AreEqual(typeof(bool), TypeHelper.GetCommonCoercionType(new Type[] { typeof(bool), typeof(bool) }));
            Assert.AreEqual(typeof(bool), TypeHelper.GetCommonCoercionType(new Type[] { typeof(bool), typeof(bool), typeof(bool) }));
            Assert.AreEqual(typeof(Int32), TypeHelper.GetCommonCoercionType(new Type[] { typeof(int), typeof(sbyte), typeof(int) }));
            Assert.AreEqual(typeof(Int32), TypeHelper.GetCommonCoercionType(new Type[] { typeof(Int32), typeof(System.SByte), typeof(Int16) }));
            Assert.AreEqual(typeof(Int32), TypeHelper.GetCommonCoercionType(new Type[] { typeof(sbyte), typeof(short), typeof(short) }));
            Assert.AreEqual(typeof(Double), TypeHelper.GetCommonCoercionType(new Type[] { typeof(Int32), typeof(System.SByte), typeof(Double) }));
            Assert.AreEqual(typeof(Double), TypeHelper.GetCommonCoercionType(new Type[] { typeof(Int64), typeof(Double), typeof(Double) }));
            Assert.AreEqual(typeof(Double), TypeHelper.GetCommonCoercionType(new Type[] { typeof(double), typeof(sbyte) }));
            Assert.AreEqual(typeof(Double), TypeHelper.GetCommonCoercionType(new Type[] { typeof(double), typeof(sbyte), null }));
            Assert.AreEqual(typeof(System.Single), TypeHelper.GetCommonCoercionType(new Type[] { typeof(float), typeof(float) }));
            Assert.AreEqual(typeof(System.Single), TypeHelper.GetCommonCoercionType(new Type[] { typeof(float), typeof(int) }));
            Assert.AreEqual(typeof(System.Single), TypeHelper.GetCommonCoercionType(new Type[] { typeof(Int32), typeof(int), typeof(System.Single) }));
            Assert.AreEqual(typeof(Int64), TypeHelper.GetCommonCoercionType(new Type[] { typeof(Int32), typeof(int), typeof(long) }));
            Assert.AreEqual(typeof(Int64), TypeHelper.GetCommonCoercionType(new Type[] { typeof(long), typeof(int) }));
            Assert.AreEqual(typeof(Int64), TypeHelper.GetCommonCoercionType(new Type[] { typeof(long), typeof(int), typeof(int), typeof(int), typeof(sbyte), typeof(short) }));
            Assert.AreEqual(typeof(Int64), TypeHelper.GetCommonCoercionType(new Type[] { typeof(long), null, typeof(int), null, typeof(int), typeof(int), null, typeof(sbyte), typeof(short) }));
            Assert.AreEqual(typeof(Int64), TypeHelper.GetCommonCoercionType(new Type[] { typeof(Int32), typeof(int), typeof(long) }));
            Assert.AreEqual(typeof(System.Char), TypeHelper.GetCommonCoercionType(new Type[] { typeof(char), typeof(char), typeof(char) }));
            Assert.AreEqual(typeof(Int64), TypeHelper.GetCommonCoercionType(new Type[] { typeof(int), typeof(int), typeof(int), typeof(long), typeof(int), typeof(int) }));
            Assert.AreEqual(typeof(Double), TypeHelper.GetCommonCoercionType(new Type[] { typeof(int), typeof(long), typeof(int), typeof(double), typeof(int), typeof(int) }));
            Assert.AreEqual(null, TypeHelper.GetCommonCoercionType(new Type[] { null, null }));
            Assert.AreEqual(null, TypeHelper.GetCommonCoercionType(new Type[] { null, null, null }));
            Assert.AreEqual(typeof(SupportBean), TypeHelper.GetCommonCoercionType(new Type[] { typeof(SupportBean), null, null }));
            Assert.AreEqual(typeof(SupportBean), TypeHelper.GetCommonCoercionType(new Type[] { null, typeof(SupportBean), null }));
            Assert.AreEqual(typeof(SupportBean), TypeHelper.GetCommonCoercionType(new Type[] { null, typeof(SupportBean) }));
            Assert.AreEqual(typeof(SupportBean), TypeHelper.GetCommonCoercionType(new Type[] { null, null, typeof(SupportBean) }));
            Assert.AreEqual(typeof(SupportBean), TypeHelper.GetCommonCoercionType(new Type[] { typeof(SupportBean), null, typeof(SupportBean), typeof(SupportBean) }));

            tryInvalidGetCommonCoercionType(new Type[] { typeof(String), typeof(bool) });
            tryInvalidGetCommonCoercionType(new Type[] { typeof(String), typeof(String), typeof(bool) });
            tryInvalidGetCommonCoercionType(new Type[] { typeof(bool), typeof(String), typeof(bool) });
            tryInvalidGetCommonCoercionType(new Type[] { typeof(bool), typeof(bool), typeof(String) });
            tryInvalidGetCommonCoercionType(new Type[] { typeof(long), typeof(bool), typeof(String) });
            tryInvalidGetCommonCoercionType(new Type[] { typeof(double), typeof(long), typeof(String) });
            tryInvalidGetCommonCoercionType(new Type[] { null, typeof(double), typeof(long), typeof(String) });
            tryInvalidGetCommonCoercionType(new Type[] { typeof(String), typeof(String), typeof(long) });
            tryInvalidGetCommonCoercionType(new Type[] { typeof(String), typeof(SupportBean) });
            tryInvalidGetCommonCoercionType(new Type[] { typeof(bool), null, null, typeof(String) });
            tryInvalidGetCommonCoercionType(new Type[] { typeof(int), null, null, typeof(String) });
            tryInvalidGetCommonCoercionType(new Type[] { typeof(SupportBean), typeof(bool) });
            tryInvalidGetCommonCoercionType(new Type[] { typeof(String), typeof(SupportBean) });
            tryInvalidGetCommonCoercionType(new Type[] { typeof(SupportBean), typeof(String), typeof(SupportBean) });

            try
            {
                TypeHelper.GetCommonCoercionType(new Type[0]);
                Assert.Fail();
            }
            catch (ArgumentException ex)
            {
                // expected
            }
        }

        private void tryInvalidGetCommonCoercionType(Type[] types)
        {
            try
            {
                TypeHelper.GetCommonCoercionType(types);
                Assert.Fail();
            }
            catch (CoercionException ex)
            {
                // expected
            }
        }
    }
}
