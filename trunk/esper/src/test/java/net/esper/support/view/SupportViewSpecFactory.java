package net.esper.support.view;

import java.util.LinkedList;
import java.util.List;

import net.esper.type.PrimitiveValue;
import net.esper.type.PrimitiveValueFactory;
import net.esper.view.ViewSpec;

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

    public static List<ViewSpec> makeSpecListTwo()
    {
        List<ViewSpec> specifications = new LinkedList<ViewSpec>();

        ViewSpec specOne = makeSpec("std", "groupby",
                new Class[] { String.class }, new String[] { "\"symbol\"" } );

        specifications.add(specOne);

        return specifications;
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

    public static List<ViewSpec> makeSpecListFive()
    {
        List<ViewSpec> specifications = new LinkedList<ViewSpec>();

        ViewSpec specOne = makeSpec("win", "time",
                new Class[] { Integer.class}, new String[] { "10000" } );
        specifications.add(specOne);

        return specifications;
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
}
