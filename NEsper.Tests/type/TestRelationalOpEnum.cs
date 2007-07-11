using System;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.type
{
    [TestFixture]
    public class TestRelationalOpEnum
    {
        internal bool[][] expected = new bool[][]{
            new bool[]{false, false, true},
            new bool[]{false, true, true},
            new bool[]{true, false, false},
            new bool[]{true, true, false}
        };

        [Test]
        public void testStringComputers()
        {
            String[][] _params = new String[][]{
                new String[]{"a", "b"}, 
                new String[]{"a", "a"},
                new String[]{"b", "a"}
            };

            int ordinal = 0;

            foreach (RelationalOpEnum op in RelationalOpEnum.Values)
            {
                for (int i = 0; i < _params.Length; i++)
                {
                    RelationalOpEnum.Computer computer = op.GetComputer(typeof(String));
                    bool result = computer(_params[i][0], _params[i][1]);
                    Assert.AreEqual( expected[ordinal][i], result,
                        "op=" + op.ToString() + ",i=" + i);
                }

                ordinal++;
            }
        }

        [Test]
        public void testLongComputers()
        {
            long[][] _params = new long[][]{
                new long[]{1, 2},
                new long[]{1, 1},
                new long[]{2, 1}
            };

            int ordinal = 0;

            foreach (RelationalOpEnum op in RelationalOpEnum.Values)
            {
                for (int i = 0; i < _params.Length; i++)
                {
                    RelationalOpEnum.Computer computer = op.GetComputer(typeof(long?));
                    bool result = computer(_params[i][0], _params[i][1]);
                    Assert.AreEqual(expected[ordinal][i], result,
                        "op=" + op.ToString() + ",i=" + i);
                }

                ordinal++;
            }
        }

        [Test]
        public void testDoubleComputers()
        {
            double[][] _params = new double[][]{
                new double[]{1, 2},
                new double[]{1, 1},
                new double[]{2, 1}
            };

            int ordinal = 0;

            foreach (RelationalOpEnum op in RelationalOpEnum.Values)
            {
                for (int i = 0; i < _params.Length; i++)
                {
                    RelationalOpEnum.Computer computer = op.GetComputer(typeof(double?));
                    bool result = computer(_params[i][0], _params[i][1]);
                    Assert.AreEqual(expected[ordinal][i], result,
                        "op=" + op.ToString() + ",i=" + i );
                }

                ordinal++;
            }
        }

        [Test]
        public void testInvalidGetComputer()
        {
            // Since we only do double?, Long and String compares
            tryInvalid(typeof(bool));
            tryInvalid(typeof(long));
            tryInvalid(typeof(short));
            tryInvalid(typeof(Int32));
        }

        private void tryInvalid(Type clazz)
        {
            try
            {
                RelationalOpEnum.GE.GetComputer(clazz);
                Assert.Fail();
            }
            catch (ArgumentException)
            {
                // Expected
            }
        }
    }
}
