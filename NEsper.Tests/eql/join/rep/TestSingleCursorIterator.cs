using System;

using net.esper.collection;
using net.esper.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.rep
{
    [TestFixture]
    public class TestSingleCursorIterator
    {
        private class AnonymousClassCursor : Cursor
        {
            public AnonymousClassCursor(EventBean _event, int stream, Node node) :
                base( _event, stream, node )
            {
            }

            virtual public EventBean LookupEvent
            {
                get { return null; }
            }

            virtual public int LookupStream
            {
                get { return 0; }
            }

            virtual public int IndexedStream
            {
                get { return 0; }
            }
        }

        private SingleCursorIterator filledIterator;
        private SingleCursorIterator emptyIterator;
        private Cursor cursor;

        [SetUp]
        public virtual void setUp()
        {
            cursor = makeAnonymousCursor();
            filledIterator = new SingleCursorIterator(cursor);
            emptyIterator = new SingleCursorIterator(null);
        }

        [Test]
        public void testNext()
        {
            filledIterator.MoveNext();
            Assert.AreSame(cursor, filledIterator.Current);
            try
            {
                filledIterator.MoveNext();
                Object temp = filledIterator.Current;
                Assert.Fail();
            }
            catch (System.ArgumentOutOfRangeException ex)
            {
                // Expected exception
            }

            try
            {
                emptyIterator.MoveNext();
                Object temp = emptyIterator.Current;
                Assert.Fail();
            }
            catch (System.ArgumentOutOfRangeException ex)
            {
                // Expected exception
            }
        }

        [Test]
        public void testHasNext()
        {
            Assert.IsTrue(filledIterator.MoveNext());
            Object temp = filledIterator.Current;
            Assert.IsFalse(filledIterator.MoveNext());

            Assert.IsFalse(emptyIterator.MoveNext());
        }
        
        private Cursor makeAnonymousCursor()
        {
            return new AnonymousClassCursor(null, 0, null);
        }
    }
}
