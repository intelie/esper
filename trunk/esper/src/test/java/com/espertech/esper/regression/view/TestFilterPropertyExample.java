package com.espertech.esper.regression.view;

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.support.bean.bookexample.OrderBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestFilterPropertyExample extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testExample()
    {
        ConfigurationEventTypeXMLDOM config = new ConfigurationEventTypeXMLDOM();
        String schemaUri = this.getClass().getClassLoader().getResource("regression/mediaOrderSchema.xsd").toString();
        config.setSchemaResource(schemaUri);
        config.setRootElementName("mediaorder");
        epService.getEPAdministrator().getConfiguration().addEventType("MediaOrder", config);
        epService.getEPAdministrator().getConfiguration().addEventType("Cancel", config);

        String stmtTextOne = "select orderId, items.item[0].itemId from MediaOrder";
        EPStatement stmtOne = epService.getEPAdministrator().createEPL(stmtTextOne);

        String stmtTextTwo = "select * from MediaOrder[books.book]";
        EPStatement stmtTwo = epService.getEPAdministrator().createEPL(stmtTextTwo);

        String stmtTextThree = "select * from MediaOrder(orderId='PO200901')[books.book]";
        EPStatement stmtThree = epService.getEPAdministrator().createEPL(stmtTextThree);

        String stmtTextFour = "select count(*) from MediaOrder[books.book].std:unique(bookId)";
        EPStatement stmtFour = epService.getEPAdministrator().createEPL(stmtTextFour);

        String stmtTextFive = "select * from MediaOrder[books.book][review]";
        EPStatement stmtFive = epService.getEPAdministrator().createEPL(stmtTextFive);

        String stmtTextSix = "select * from pattern [c=Cancel -> o=MediaOrder(orderId = c.orderId)[books.book]]";
        EPStatement stmtSix = epService.getEPAdministrator().createEPL(stmtTextSix);
        
        String stmtTextSeven = "select * from MediaOrder[select orderId, bookId from books.book][review]";
        EPStatement stmtSeven = epService.getEPAdministrator().createEPL(stmtTextSeven);

        String stmtTextEight = "select * from MediaOrder[select * from books.book][select reviewId, comment from review]";
        EPStatement stmtEight = epService.getEPAdministrator().createEPL(stmtTextEight);

        String stmtTextNine = "select * from MediaOrder[books.book as book][select book.*, reviewId, comment from review]";
        EPStatement stmtNine = epService.getEPAdministrator().createEPL(stmtTextNine);

        String stmtTextTen = "select * from MediaOrder[books.book as book][select mediaOrder.*, bookId, reviewId from review] as mediaOrder";
        EPStatement stmtTen = epService.getEPAdministrator().createEPL(stmtTextTen);

        String stmtTextEleven_0 = "insert into ReviewStream select * from MediaOrder[books.book as book]\n" +
                "    [select mediaOrder.* as mediaOrder, book.* as book, review.* as review from review as review] as mediaOrder";
        EPStatement stmtEleven_0 = epService.getEPAdministrator().createEPL(stmtTextEleven_0);
        String stmtTextEleven_1 = "select mediaOrder.orderId, book.bookId, review.reviewId from ReviewStream";
        EPStatement stmtEleven = epService.getEPAdministrator().createEPL(stmtTextEleven_1);

        String stmtTextTwelve = "select * from MediaOrder[books.book where author = 'Heinlein'][review]";
        EPStatement stmtTwelve = epService.getEPAdministrator().createEPL(stmtTextTwelve);
    }

    public void testJoins()
    {
        String stmtTextThirteen = "select * from MediaOrder[books.book], MediaOrder[items.item] where productId = bookId";
        EPStatement stmtThirteen = epService.getEPAdministrator().createEPL(stmtTextThirteen);

        
    }
}