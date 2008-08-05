package com.espertech.esper.support.view;

import java.util.LinkedList;
import java.util.List;

import com.espertech.esper.type.PrimitiveValue;
import com.espertech.esper.type.PrimitiveValueFactory;
import com.espertech.esper.epl.spec.ViewSpec;
import com.espertech.esper.view.ViewServiceImpl;
import com.espertech.esper.view.ViewFactoryChain;
import com.espertech.esper.view.ViewFactory;
import com.espertech.esper.event.EventType;

/**
 * Convenience class for making view specifications from class and string arrays.
 */
public class SupportViewSpecFactory
{
    public static List<ViewSpec> makeSpecListOne()
    {
        List<ViewSpec> specifications = new LinkedList<ViewSpec>();

        ViewSpec specOne = makeSpec("win", "length",
                new Class[] { Integer.class}, new String[] { "1000" } );
        ViewSpec specTwo = makeSpec("stat", "uni",
                new Class[] { String.class}, new String[] { "\"price\"" } );
        ViewSpec specThree = makeSpec("std", "lastevent", null, null);

        specifications.add(specOne);
        specifications.add(specTwo);
        specifications.add(specThree);

        return specifications;
    }

    public static List<ViewFactory> makeFactoryListOne(EventType parentEventType) throws Exception
    {
        return makeFactories(parentEventType, makeSpecListOne());
    }

    public static List<ViewSpec> makeSpecListTwo()
    {
        List<ViewSpec> specifications = new LinkedList<ViewSpec>();

        ViewSpec specOne = makeSpec("std", "groupby",
                new Class[] { String.class }, new String[] { "\"symbol\"" } );
        ViewSpec specTwo = makeSpec("win", "length",
                new Class[] { int.class }, new String[] { "100" } );

        specifications.add(specOne);
        specifications.add(specTwo);

        return specifications;
    }

    public static List<ViewFactory> makeFactoryListTwo(EventType parentEventType) throws Exception
    {
        return makeFactories(parentEventType, makeSpecListTwo());
    }

    public static List<ViewSpec> makeSpecListThree()
    {
        List<ViewSpec> specifications = new LinkedList<ViewSpec>();

        ViewSpec specOne = SupportViewSpecFactory.makeSpec("win", "length",
                new Class[] { Integer.class}, new String[] { "1000" } );
        ViewSpec specTwo = SupportViewSpecFactory.makeSpec("std", "unique",
                new Class[] { String.class}, new String[] { "\"symbol\"" } );

        specifications.add(specOne);
        specifications.add(specTwo);

        return specifications;
    }

    public static List<ViewFactory> makeFactoryListThree(EventType parentEventType) throws Exception
    {
        return makeFactories(parentEventType, makeSpecListThree());
    }

    public static List<ViewSpec> makeSpecListFour()
    {
        List<ViewSpec> specifications = new LinkedList<ViewSpec>();

        ViewSpec specOne = SupportViewSpecFactory.makeSpec("win", "length",
                new Class[] { Integer.class}, new String[] { "1000" } );
        ViewSpec specTwo = SupportViewSpecFactory.makeSpec("stat", "uni",
                new Class[] { String.class}, new String[] { "\"price\"" } );
        ViewSpec specThree = SupportViewSpecFactory.makeSpec("std", "size", null, null);

        specifications.add(specOne);
        specifications.add(specTwo);
        specifications.add(specThree);

        return specifications;
    }

    public static List<ViewFactory> makeFactoryListFour(EventType parentEventType) throws Exception
    {
        return makeFactories(parentEventType, makeSpecListFour());
    }

    public static List<ViewSpec> makeSpecListFive()
    {
        List<ViewSpec> specifications = new LinkedList<ViewSpec>();

        ViewSpec specOne = makeSpec("win", "time",
                new Class[] { Integer.class}, new String[] { "10000" } );
        specifications.add(specOne);

        return specifications;
    }

    public static List<ViewFactory> makeFactoryListFive(EventType parentEventType) throws Exception
    {
        return makeFactories(parentEventType, makeSpecListFive());
    }

    public static ViewSpec makeSpec(String namespace, String viewName, Class[] paramTypes, String[] paramValues)
    {
        return new ViewSpec(namespace, viewName, makeParams(paramTypes, paramValues));
    }

    private static LinkedList<Object> makeParams(Class clazz[], String[] values)
    {
        LinkedList<Object> params = new LinkedList<Object>();

        if (clazz == null)
        {
            return params;
        }

        for (int i = 0; i < clazz.length; i++)
        {
            PrimitiveValue placeholder = PrimitiveValueFactory.create(clazz[i]);
            placeholder.parse(values[i]);
            Object value = placeholder.getValueObject();
            params.add(value);
        }

        return params;
    }

    private static List<ViewFactory> makeFactories(EventType parentEventType, List<ViewSpec> viewSpecs) throws Exception
    {
        ViewServiceImpl svc = new ViewServiceImpl();
        ViewFactoryChain viewFactories = svc.createFactories(1, parentEventType, viewSpecs, SupportStatementContextFactory.makeContext());
        return viewFactories.getViewFactoryChain();
    }
}