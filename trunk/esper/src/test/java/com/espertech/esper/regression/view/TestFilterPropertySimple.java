package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.bean.bookexample.*;
import com.espertech.esper.support.client.SupportConfigFactory;

public class TestFilterPropertySimple extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    /**
     *
     * An order document has multiple order items and a list of books and musicCDs.
     * The product id correlates, based on the product type, to either book or musicCD.
     *
     * Syntax:
     *      ==> Simple property select: from property_name [as alias]
     *              from books                           // means take all books, one row per book
     *              from books[0].notes                 // take all notes from first book
     *              from [cds]                          // alternative syntax
     *              from [cds:*]                        // alternative syntax
     *              from books as mybook                // apply alias mybook
     *     
     *      ==> Property sub-select: from [property_name: column, column]
     *              from [cds: cdid, notes] as cd       // for each musiccd select cdid and notes as cd
     *              from [cds: OrderEvent.id, cdid, notes] as cd       // columns can contain parent columns
     *              from [cds: OrderEvent.*, cdid as idcd, notes] as cd      // columns can use wildcard and alias
     *              from [cds][notes: *.*, notes] as cd      // *.* includes all parent records
     *      ==> Property-in-property select:
     *              from [books][notes] as booknotes         // means take all books, and for each book take all notes
     * 
     *      ==> Parenthesis means union, i.e.:
     *              from (books, cds) as prop12        // means all prop1 and prop2 properties which must have a common supertype
     *              from (books, cds)[notes] as notes   // means all names properties for prop1 and prop2
     *              from ([books: bookid][notes], [cds:cdid][notes]) as notes    // means take all books notes and all cd notes, equivalent to above
     *
     * Examples:
     *   (1) Give me the notes for the first order item
     *         on OrderEvent select * from orderitems[0] as item0, (books, cds) as product
     *         where item0.productId = products.id
     *       => Populates two properties: "item0" with the order detail, "product" with the product detail
     *       => Note that this is a form of duck-typing, as we only know that the product has an id but don't know what the product actually is
     *
     * Lets say we want to book id and each from-entry as an individual event:
     *   on OrderEvent select * from ([books as book],[cds as cd]) [notes.note as note]
     *      => Returns a line per note for each book
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


    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testUnidirectionalJoin()
    {
        String[] fields = "orderEvent.orderdetail.orderId,book.bookId,book.title,item.amount".split(",");
        epService.getEPAdministrator().getConfiguration().addEventType("OrderEvent", OrderBean.class);
        String stmtText = "select * from " +
                      "OrderEvent orderEvent unidirectional, " +
                      "OrderEvent[books] book, " +
                      "OrderEvent[orderdetail.items] item " +
                      "where book.bookId = item.productId order by book.bookId asc, item.amount asc";

        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(makeEventOne());
        assertEquals(3, listener.getLastNewData().length);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"PO200901", "10020", "Ender's Game", 10}, {"PO200901", "10020", "Ender's Game", 30}, {"PO200901", "10021", "Foundation 1", 25}});
        listener.reset();

        epService.getEPRuntime().sendEvent(makeEventTwo());
        assertEquals(1, listener.getLastNewData().length);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"PO200902", "10022", "Stranger in a Strange Land", 5}});
        listener.reset();

        epService.getEPRuntime().sendEvent(makeEventThree());
        assertEquals(1, listener.getLastNewData().length);
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"PO200903", "10021", "Foundation 1", 50}});
    }

    public void testUnidirectionalJoinCount()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("OrderEvent", OrderBean.class);
        String stmtText = "select count(*) from " +
                      "OrderEvent orderEvent unidirectional, " +
                      "OrderEvent[books] as book, " +
                      "OrderEvent[orderdetail.items] item " +
                      "where book.bookId = item.productId order by book.bookId asc, item.amount asc";

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
        epService.getEPAdministrator().getConfiguration().addEventType("OrderEvent", OrderBean.class);
        String stmtText = "select count(*) from " +
                      "OrderEvent[books].std:unique(bookId) book, " +
                      "OrderEvent[orderdetail.items].win:keepall() item " +
                      "where book.bookId = item.productId";

        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(makeEventOne());
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {3L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{3L}});

        epService.getEPRuntime().sendEvent(makeEventTwo());
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "count(*)".split(","), new Object[] {4L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{4L}});

        epService.getEPRuntime().sendEvent(makeEventThree());
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "count(*)".split(","), new Object[] {5L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{5L}});

        epService.getEPRuntime().sendEvent(makeEventFour());
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(makeEventOne());
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "count(*)".split(","), new Object[] {8L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{8L}});
    }

    public void testJoin()
    {
        String[] fields = "book.bookId,item.itemId,amount".split(",");
        epService.getEPAdministrator().getConfiguration().addEventType("OrderEvent", OrderBean.class);
        String stmtText = "select book.bookId,item.itemId,amount from " +
                      "OrderEvent[books].std:firstunique(bookId) book, " +
                      "OrderEvent[orderdetail.items].win:keepall() item " +
                      "where book.bookId = item.productId " +
                      "order by book.bookId, item.itemId";

        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(makeEventOne());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"10020","A001",10}, {"10020","A003", 30}, {"10021","A002", 25}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"10020","A001",10}, {"10020","A003", 30}, {"10021","A002", 25}});
        listener.reset();

        epService.getEPRuntime().sendEvent(makeEventTwo());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"10022","B001", 5}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"10020","A001",10}, {"10020","A003", 30}, {"10021","A002", 25}, {"10022","B001", 5}});
        listener.reset();

        epService.getEPRuntime().sendEvent(makeEventThree());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"10021","C001",50}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"10020","A001",10}, {"10020","A003", 30}, {"10021","A002", 25}, {"10021","C001", 50}, {"10022","B001", 5}});
        listener.reset();

        epService.getEPRuntime().sendEvent(makeEventFour());
        assertFalse(listener.isInvoked());
    }

    public void testAloneCount()
    {
        String[] fields = "count(*)".split(",");
        epService.getEPAdministrator().getConfiguration().addEventType("OrderEvent", OrderBean.class);

        String stmtText = "select count(*) from OrderEvent[books]";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(makeEventOne());
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {3L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{3L}});

        epService.getEPRuntime().sendEvent(makeEventFour());
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {5L});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{5L}});
    }

    public void testPropertyAccess()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("OrderEvent", OrderBean.class);

        EPStatement stmtOne = epService.getEPAdministrator().createEPL("select bookId from OrderEvent[books]");
        stmtOne.addListener(listener);
        EPStatement stmtTwo = epService.getEPAdministrator().createEPL("select books[0].author as val from OrderEvent(books[0].bookId = '10020')");

        epService.getEPRuntime().sendEvent(makeEventOne());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), "bookId".split(","), new Object[][] {{"10020"}, {"10021"}, {"10022"}});
        listener.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtOne.iterator(), "bookId".split(","), new Object[][] {{"10020"}, {"10021"}, {"10022"}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtTwo.iterator(), "val".split(","), new Object[][] {{"Orson Scott Card"}});

        epService.getEPRuntime().sendEvent(makeEventFour());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), "bookId".split(","), new Object[][] {{"10031"}, {"10032"}});
        listener.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmtOne.iterator(), "bookId".split(","), new Object[][] {{"10031"}, {"10032"}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtTwo.iterator(), "val".split(","), new Object[][] {{"Orson Scott Card"}});

        // add where clause
        stmtOne.destroy();
        stmtTwo.destroy();
        stmtOne = epService.getEPAdministrator().createEPL("select bookId from OrderEvent[books where author='Orson Scott Card']");
        stmtOne.addListener(listener);
        epService.getEPRuntime().sendEvent(makeEventOne());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), "bookId".split(","), new Object[][] {{"10020"}});
        listener.reset();
    }

    public void testIRStreamArrayItem()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("OrderEvent", OrderBean.class);
        String stmtText = "select irstream bookId from OrderEvent[books[0]]";

        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(makeEventOne());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), "bookId".split(","), new Object[][] {{"10020"}});
        assertNull(listener.getLastOldData());
        listener.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), "bookId".split(","), new Object[][] {{"10020"}});

        epService.getEPRuntime().sendEvent(makeEventFour());
        assertNull(listener.getLastOldData());
        ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), "bookId".split(","), new Object[][] {{"10031"}});
        listener.reset();
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), "bookId".split(","), new Object[][] {{"10031"}});
    }

    public static OrderBean makeEventOne()
    {
        Order order = new Order("PO200901",
                new OrderItem[] {
                        new OrderItem("A001", "10020", 10, 11.95),
                        new OrderItem("A002", "10021", 25, 7.50),
                        new OrderItem("A003", "10020", 30, 10),
                        });
        return new OrderBean(order, getBookDesc(), new GameDesc[0]);
    }

    public static OrderBean makeEventTwo()
    {
        Order order = new Order("PO200902",
                new OrderItem[] {new OrderItem("B001", "10022", 5, 99.50)});

        return new OrderBean(order, getBookDesc(), new GameDesc[0]);
    }

    public static OrderBean makeEventThree()
    {
        Order order = new Order("PO200903",
                new OrderItem[] {
                        new OrderItem("C001", "10025", 52, 99.50),
                        new OrderItem("C001", "10024", 51, 41.50),
                        new OrderItem("C001", "10021", 50, 30.50)
                });

        return new OrderBean(order, getBookDesc(),
                new GameDesc[] {new GameDesc("GA01", "Castlevania", "Eidos",
                        new Review[] {
                                new Review(100, "best game ever"),
                                new Review(101, "good platformer")
                        })
                });
    }

    public static OrderBean makeEventFour()
    {
        Order order = new Order("PO200904",
                new OrderItem[0]);
        return new OrderBean(order, new BookDesc[] {
                new BookDesc("10031", "Foundation 2", "Isaac Asimov",
                        new Review[] {
                                new Review(201, "great book")
                        }),
                new BookDesc("10032", "Red Planet", "Robert A Heinlein", new Review[0]),
        }, new GameDesc[0]);
    }

    private static BookDesc[] getBookDesc()
    {
        return new BookDesc[] {
                new BookDesc("10020", "Enders Game", "Orson Scott Card",
                        new Review[] {
                                new Review(1, "best book ever"),
                                new Review(2, "good science fiction")
                        }),
                new BookDesc("10021", "Foundation 1", "Isaac Asimov",
                        new Review[] {
                                new Review(10, "great book")
                        }),
                new BookDesc("10022", "Stranger in a Strange Land", "Robert A Heinlein", new Review[0])
        };
    }
}
