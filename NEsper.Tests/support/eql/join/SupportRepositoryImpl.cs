using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.eql.join.rep;
using net.esper.events;

namespace net.esper.support.eql.join
{
    public class SupportRepositoryImpl : Repository
    {
        private IList<Cursor> cursorList = new List<Cursor>();
        private IList<ISet<EventBean>> lookupResultsList = new List<ISet<EventBean>>();
        private IList<int> resultStreamList = new List<int>();

        public IEnumerator<Cursor> GetCursors(int lookupStream)
        {
            return new SingleCursorIterator(new Cursor(SupportJoinResultNodeFactory.MakeEvent(), 0, null));
        }

        public void AddResult(Cursor cursor, ISet<EventBean> lookupResults, int resultStream)
        {
            cursorList.Add(cursor);
            lookupResultsList.Add(lookupResults);
            resultStreamList.Add(resultStream);
        }

        public IList<Cursor> getCursorList()
        {
            return cursorList;
        }

        public IList<ISet<EventBean>> getLookupResultsList()
        {
            return lookupResultsList;
        }

        public IList<int> getResultStreamList()
        {
            return resultStreamList;
        }
    }
}
