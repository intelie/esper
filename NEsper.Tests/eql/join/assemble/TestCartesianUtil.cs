using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.support.eql.join;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.assemble
{

	[TestFixture]
    public class TestCartesianUtil
    {
        private const int NUM_COL = 4;

        private int[] substreamsA;
        private int[] substreamsB;
        private IList<EventBean[]> results;

        [SetUp]
        public virtual void setUp()
        {
            substreamsA = new int[] { 0, 3 };
            substreamsB = new int[] { 1 };
            results = new List<EventBean[]>();
        }

        [Test]
        public void testCompute()
        {
            // test null
            IList<EventBean[]> rowsA = null;
            IList<EventBean[]> rowsB = null;
            tryCompute(rowsA, rowsB);
            Assert.IsTrue(results.Count == 0);

            // test no rows A
            rowsA = new List<EventBean[]>();
            tryCompute(rowsA, rowsB);
            Assert.IsTrue(results.Count == 0);

            // test no rows B
            rowsA = null;
            rowsB = new List<EventBean[]>();
            tryCompute(rowsA, rowsB);
            Assert.IsTrue(results.Count == 0);

            // test side A one row, B empty
            rowsA = makeRowsA(1);
            rowsB = null;
            tryCompute(rowsA, rowsB);
            Assert.AreEqual(1, results.Count);
            ArrayAssertionUtil.AreEqualExactOrder(rowsA[0], results[0]);

            // test side B one row, A empty
            rowsA = null;
            rowsB = makeRowsB(1);
            tryCompute(rowsA, rowsB);
            Assert.AreEqual(1, results.Count);
            ArrayAssertionUtil.AreEqualExactOrder(rowsB[0], results[0]);

            // test A and B one row
            rowsA = makeRowsA(1);
            rowsB = makeRowsB(1);
            tryCompute(rowsA, rowsB);
            Assert.AreEqual(1, results.Count);
            ArrayAssertionUtil.AreEqualExactOrder(
				new EventBean[] {
					rowsA[0][0],
					rowsB[0][1],
					null,
					rowsA[0][3]
				},
				results[0]);

            // test A=2 rows and B=1 row
            rowsA = makeRowsA(2);
            rowsB = makeRowsB(1);
            tryCompute(rowsA, rowsB);
            Assert.AreEqual(2, results.Count);
            ArrayAssertionUtil.AreEqualAnyOrder(
				new EventBean[][] {
					new EventBean[] {
						rowsA[0][0],
						rowsB[0][1],
						null,
						rowsA[0][3]
					},
					new EventBean[] {
						rowsA[1][0],
						rowsB[0][1],
						null,
						rowsA[1][3]
					}
				},
				SupportJoinResultNodeFactory.convertTo2DimArr(results));

            // test A=1 rows and B=2 row
            rowsA = makeRowsA(1);
            rowsB = makeRowsB(2);
            tryCompute(rowsA, rowsB);
            Assert.AreEqual(2, results.Count);
            ArrayAssertionUtil.AreEqualAnyOrder(
				new EventBean[][] {
					new EventBean[] {
						rowsA[0][0],
						rowsB[0][1],
						null,
						rowsA[0][3]
					},
					new EventBean[] {
						rowsA[0][0],
						rowsB[1][1],
						null,
						rowsA[0][3]
					}
				},
				SupportJoinResultNodeFactory.convertTo2DimArr(results));

            // test A=2 rows and B=2 row
            rowsA = makeRowsA(2);
            rowsB = makeRowsB(2);
            tryCompute(rowsA, rowsB);
            Assert.AreEqual(4, results.Count);
            ArrayAssertionUtil.AreEqualAnyOrder(
				new EventBean[][] {
					new EventBean[] {
						rowsA[0][0],
						rowsB[0][1],
						null,
						rowsA[0][3]
					},
					new EventBean[] {
						rowsA[0][0],
						rowsB[1][1],
						null,
						rowsA[0][3]
					},
					new EventBean[] {
						rowsA[1][0],
						rowsB[0][1],
						null,
						rowsA[1][3]
					},
					new EventBean[] {
						rowsA[1][0],
						rowsB[1][1],
						null,
						rowsA[1][3]
					}
				},
				SupportJoinResultNodeFactory.convertTo2DimArr(results));

            // test A=2 rows and B=3 row
            rowsA = makeRowsA(2);
            rowsB = makeRowsB(3);
            tryCompute(rowsA, rowsB);
            Assert.AreEqual(6, results.Count);
            ArrayAssertionUtil.AreEqualAnyOrder(
				new EventBean[][] {
					new EventBean[] {
						rowsA[0][0],
						rowsB[0][1],
						null,
						rowsA[0][3]
					},
					new EventBean[] {
						rowsA[0][0],
						rowsB[1][1],
						null,
						rowsA[0][3]
					},
					new EventBean[] {
						rowsA[0][0],
						rowsB[2][1],
						null,
						rowsA[0][3]
					},
					new EventBean[] {
						rowsA[1][0],
						rowsB[0][1],
						null,
						rowsA[1][3]
					},
					new EventBean[] {
						rowsA[1][0],
						rowsB[1][1],
						null,
						rowsA[1][3]
					},
					new EventBean[] {
						rowsA[1][0],
						rowsB[2][1],
						null,
						rowsA[1][3]
					}
				},
				SupportJoinResultNodeFactory.convertTo2DimArr(results));
        }

        private void tryCompute(IList<EventBean[]> rowsOne, IList<EventBean[]> rowsTwo)
        {
            results.Clear();
            CartesianUtil.ComputeCartesian(rowsOne, substreamsA, rowsTwo, substreamsB, results);
        }


        private IList<EventBean[]> makeRowsA(int numRows)
        {
            return makeRows(numRows, substreamsA);
        }

        private IList<EventBean[]> makeRowsB(int numRows)
        {
            return makeRows(numRows, substreamsB);
        }

        private static IList<EventBean[]> makeRows(int numRows, int[] substreamsPopulated)
        {
            IList<EventBean[]> result = new List<EventBean[]>();
            for (int i = 0; i < numRows; i++)
            {
                EventBean[] row = new EventBean[NUM_COL];
                for (int j = 0; j < substreamsPopulated.Length; j++)
                {
                    int index = substreamsPopulated[j];
                    row[index] = SupportJoinResultNodeFactory.MakeEvent();
                }
                result.Add(row);
            }
            return result;
        }
    }
}
