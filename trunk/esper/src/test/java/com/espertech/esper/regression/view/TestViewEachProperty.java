package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;

public class TestViewEachProperty extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    // TODO: test multiple events posted by parent view  ==> must handle, such as batch view: "win:length_batch(10).std:each(book)"
    // TODO: test remove stream posted by parent view  ==> ext:sort(5, price asc).std:each(book)
    // TODO: test std:each.win:time(30)  ==> derived-value views can be placed in front of data window views
    // TODO: test join with another event
    // TODO: test staggered view, i.e. for each book for each bookNote of each book
    // TODO: test, there may not be an id, fact that nested means they are related, generate Map-in-Map

    /**
        <order>
          <id>P000212</id>
          <books>
           <book>
            <id>10045</id>
            <notes>
              <note>
               <from>name</from>
               <text>abc</text>
              </note>
            </notes>
           </book>
           <book>
            <id>10046</id>
            <notes>
              <note>
               <from>name</from>
               <text>abc</text>
              </note>
            </notes>
           </book>
          </books>
        </order>
     *
     * Lets say we want to book id and each from-entry as an individual event:
     *   on OrderEvent select * from [book as book][notes.note as note]
     *      => Returns a line per "note"
     *      => Wrapper with OrderEvent as the underlying event and that contains "book" and that contains "note" as a property
     *              { book =
     *                  { id=10045,
     *                    note= {...}}}
     *
     *   on OrderEvent select * from [book[0].notes as note]
     *      => Returns a line per "note"
     *      => Wrapper with OrderEvent as the underlying event and that contains "note" as a property
     *              { note= {...}}}
     *
     *   on OrderEvent select * from [book as book][notes.note as note] as notes, [orderdetail.items] as item
     *                          where notes.book.id = items.id
     *      => Returns a line per "note"
     *      => Wrapper with OrderEvent as the underlying event and that contains "note" as a property
     *              { note= {...}}}
     *
      */

    /**
     * The for-each
     *  - cannot be a data window view as it violates the contract: it does not produce the same output event type as input.
     *  - it can be a derived-data view provided that it is aware when it receives a remove stream and also posts a remove stream
     * Other options are the in-context join, which presents a nicer syntax:
     *   On OrderEvent select * from books, orderdetail.items where book.id = item.bookId order by book.id
     *  - can be staggered: OrderEvent.std:each(book).std:each(note).
     *
     * (1) Have the "each" view as a derived-value view and check if its alone, and if
     *          No : then keep a HashMap<EventBean, EventBean[]>
     *          yes : just keep the last posted event
     *
     * (2) Enhance "on-select" as above
     *
     * (3) Do not have a view, make it a stream option to extract properties
                   select * from 
                   OrderEvent(filter) unidirectional,
                   OrderEvent(filter)[books],
                   OrderEvent(filter)[orderdetail.items],
                   where book.id = item.bookId order by book.id asc, item.amount asc
     */

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testJoin()
    {
        String[] fields = "orderEvent.orderdetail.orderId,book.id,book.title,item.amount".split(",");
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("OrderEvent", SupportBeanBookOrder.class);
        String stmtText = "select * from " +
                      "OrderEvent orderEvent unidirectional, " +
                      "OrderEvent.std:each(books) book, " +
                      "OrderEvent.std:each(orderdetail.items) item " +
                      "where book.id = item.bookId order by book.id asc, item.amount asc";

        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(makeEventOne());
        assertEquals(3, listener.getLastNewData().length);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"PO200901", "10020", "Ender's Game", 10}, {"PO200901", "10020", "Ender's Game", 30}, {"PO200901", "10021", "Foundation 1", 25}});
        listener.reset();

        epService.getEPRuntime().sendEvent(makeEventTwo());
        assertEquals(1, listener.getLastNewData().length);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"PO200902", "10022", "Stranger in a Strange Land", 5}});

        epService.getEPRuntime().sendEvent(makeEventThree());
        assertEquals(1, listener.getLastNewData().length);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"PO200903", "10021", "Foundation 1", 50}});
    }

    public void testJoinCountUnidirectional()
    {
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("OrderEvent", SupportBeanBookOrder.class);
        String stmtText = "select count(*) from " +
                      "OrderEvent orderEvent unidirectional, " +
                      "OrderEvent.std:each(books) book, " +
                      "OrderEvent.std:each(orderdetail.items) item " +
                      "where book.id = item.bookId order by book.id asc, item.amount asc";

        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(makeEventOne());
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "count(*)".split(","), new Object[] {3L});

        epService.getEPRuntime().sendEvent(makeEventTwo());
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "count(*)".split(","), new Object[] {1L});

        epService.getEPRuntime().sendEvent(makeEventThree());
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "count(*)".split(","), new Object[] {1L});

        epService.getEPRuntime().sendEvent(makeEventFour());
        assertFalse(listener.isInvoked());
    }

    public void testJoinCount()
    {
        String[] fields = "count(*)".split(",");
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("OrderEvent", SupportBeanBookOrder.class);
        String stmtText = "select count(*) from " +
                      "OrderEvent.std:each(books) book, " +
                      "OrderEvent.std:each(orderdetail.items) item " +
                      "where book.id = item.bookId order by book.id asc, item.amount asc";

        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(makeEventOne());
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {3L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{3L}});

        epService.getEPRuntime().sendEvent(makeEventTwo());
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "count(*)".split(","), new Object[] {1L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{1L}});

        epService.getEPRuntime().sendEvent(makeEventThree());
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "count(*)".split(","), new Object[] {1L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{1L}});

        epService.getEPRuntime().sendEvent(makeEventFour());
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "count(*)".split(","), new Object[] {0L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{0L}});

        epService.getEPRuntime().sendEvent(makeEventOne());
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "count(*)".split(","), new Object[] {3L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{3L}});
    }

    public void testIRStreamAloneArray()
    {
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("OrderEvent", SupportBeanBookOrder.class);
        String stmtText = "select irstream id from OrderEvent.std:each(books)";

        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(makeEventOne());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), "id".split(","), new Object[][] {{"10020"}, {"10021"}, {"10022"}});
        assertNull(listener.getLastOldData());
        listener.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), "id".split(","), new Object[][] {{"10020"}, {"10021"}, {"10022"}});

        epService.getEPRuntime().sendEvent(makeEventFour());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastOldData(), "id".split(","), new Object[][] {{"10020"}, {"10021"}, {"10022"}});
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), "id".split(","), new Object[][] {{"10031"}, {"10032"}});
        listener.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), "id".split(","), new Object[][] {{"10031"}, {"10032"}});
    }

    public void testIRStreamAloneItem()
    {
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("OrderEvent", SupportBeanBookOrder.class);
        String stmtText = "select irstream id from OrderEvent.std:each(books[0])";

        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(makeEventOne());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), "id".split(","), new Object[][] {{"10020"}});
        assertNull(listener.getLastOldData());
        listener.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), "id".split(","), new Object[][] {{"10020"}});

        epService.getEPRuntime().sendEvent(makeEventFour());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastOldData(), "id".split(","), new Object[][] {{"10020"}});
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), "id".split(","), new Object[][] {{"10031"}});
        listener.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), "id".split(","), new Object[][] {{"10031"}});
    }

    public void testCountAlone()
    {
        String[] fields = "count(*)".split(",");
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("OrderEvent", SupportBeanBookOrder.class);
        String stmtText = "select count(*) from OrderEvent.std:each(books)";

        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(makeEventOne());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{3L}});
        assertNull(listener.getLastOldData());
        listener.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{3L}});

        epService.getEPRuntime().sendEvent(makeEventFour());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), "count(*)".split(","), new Object[][] {{2L}});
        listener.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{2L}});
    }

    private static SupportBeanBookOrder makeEventOne()
    {
        SupportBeanBookOrder.Order order = new SupportBeanBookOrder.Order("PO200901",
                new SupportBeanBookOrder.OrderItem[] {
                        new SupportBeanBookOrder.OrderItem("10020", 10, 11.95),
                        new SupportBeanBookOrder.OrderItem("10021", 25, 7.50),
                        new SupportBeanBookOrder.OrderItem("10020", 30, 10),
                        });
        return new SupportBeanBookOrder(getBookDesc(), order);
    }

    private static SupportBeanBookOrder makeEventTwo()
    {
        SupportBeanBookOrder.Order order = new SupportBeanBookOrder.Order("PO200902",
                new SupportBeanBookOrder.OrderItem[] {new SupportBeanBookOrder.OrderItem("10022", 5, 99.50)});

        return new SupportBeanBookOrder(getBookDesc(), order);
    }

    private static SupportBeanBookOrder makeEventThree()
    {
        SupportBeanBookOrder.Order order = new SupportBeanBookOrder.Order("PO200903",
                new SupportBeanBookOrder.OrderItem[] {
                        new SupportBeanBookOrder.OrderItem("10025", 52, 99.50),
                        new SupportBeanBookOrder.OrderItem("10024", 51, 41.50),
                        new SupportBeanBookOrder.OrderItem("10021", 50, 30.50)
                });

        return new SupportBeanBookOrder(getBookDesc(), order);
    }

    private static SupportBeanBookOrder makeEventFour()
    {
        SupportBeanBookOrder.Order order = new SupportBeanBookOrder.Order("PO200903",
                new SupportBeanBookOrder.OrderItem[0]);
        return new SupportBeanBookOrder(new SupportBeanBookOrder.BookDesc[] {
                new SupportBeanBookOrder.BookDesc("10031", "Foundation 2", "Isaac Asimov"),
                new SupportBeanBookOrder.BookDesc("10032", "Red Planet", "Robert A Heinlein"),
        }, order);
    }

    private static SupportBeanBookOrder.BookDesc[] getBookDesc()
    {
        return new SupportBeanBookOrder.BookDesc[] {
                new SupportBeanBookOrder.BookDesc("10020", "Ender's Game", "Orson Scott Card"),
                new SupportBeanBookOrder.BookDesc("10021", "Foundation 1", "Isaac Asimov"),
                new SupportBeanBookOrder.BookDesc("10022", "Stranger in a Strange Land", "Robert A Heinlein"),
        };
    }
}
