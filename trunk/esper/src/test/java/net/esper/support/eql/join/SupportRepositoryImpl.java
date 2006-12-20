package net.esper.support.eql.join;

import net.esper.eql.join.rep.Repository;
import net.esper.eql.join.rep.Cursor;
import net.esper.eql.join.rep.SingleCursorIterator;
import net.esper.event.EventBean;

import java.util.Iterator;
import java.util.Set;
import java.util.List;
import java.util.LinkedList;

public class SupportRepositoryImpl implements Repository
{
    private List<Cursor> cursorList = new LinkedList<Cursor>();
    private List<Set<EventBean>> lookupResultsList = new LinkedList<Set<EventBean>>();
    private List<Integer> resultStreamList = new LinkedList<Integer>();

    public Iterator<Cursor> getCursors(int lookupStream)
    {
        return new SingleCursorIterator(new Cursor(SupportJoinResultNodeFactory.makeEvent(), 0, null));
    }

    public void addResult(Cursor cursor, Set<EventBean> lookupResults, int resultStream)
    {
        cursorList.add(cursor);
        lookupResultsList.add(lookupResults);
        resultStreamList.add(resultStream);
    }

    public List<Cursor> getCursorList()
    {
        return cursorList;
    }

    public List<Set<EventBean>> getLookupResultsList()
    {
        return lookupResultsList;
    }

    public List<Integer> getResultStreamList()
    {
        return resultStreamList;
    }
}
