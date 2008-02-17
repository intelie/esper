package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;

public class TestViewGroupByTypes extends TestCase
{
    private EPServiceProvider epService;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testType()
    {
        String viewStmt = "select * from " + SupportBean.class.getName() +
                ".std:groupby(intPrimitive).win:length(4).std:groupby(longBoxed).std:size()";
        EPStatement stmt = epService.getEPAdministrator().createEPL(viewStmt);

        assertEquals(int.class, stmt.getEventType().getPropertyType("intPrimitive"));
        assertEquals(Long.class, stmt.getEventType().getPropertyType("longBoxed"));
        assertEquals(long.class, stmt.getEventType().getPropertyType("size"));
        assertEquals(3, stmt.getEventType().getPropertyNames().length);
    }
}
