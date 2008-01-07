/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client.soda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A view provides a projection upon a stream, such as a data window, grouping or unique.
 */
public class View extends EPBaseNamedObject
{
    /**
     * Creates a view.
     * @param namespace is thie view namespace, i.e. "win" for data windows
     * @param name is the view name, i.e. "length" for length window
     * @return view
     */
    public static View create(String namespace, String name)
    {
        return new View(namespace, name, new ArrayList<Object>());
    }

    /**
     * Creates a view.
     * @param namespace is thie view namespace, i.e. "win" for data windows
     * @param name is the view name, i.e. "length" for length window
     * @param parameters is a list of view parameters, or empty if there are no parameters for the view
     * @return view
     */
    public static View create(String namespace, String name, List<Object> parameters)
    {
        return new View(namespace, name, parameters);
    }

    /**
     * Creates a view.
     * @param namespace is thie view namespace, i.e. "win" for data windows
     * @param name is the view name, i.e. "length" for length window
     * @param parameters is a list of view parameters, or empty if there are no parameters for the view
     * @return view
     */
    public static View create(String namespace, String name, Object ...parameters)
    {
        if (parameters != null)
        {
            return new View(namespace, name, Arrays.asList(parameters));
        }
        else
        {
            return new View(namespace, name, new ArrayList<Object>());
        }
    }

    /**
     * Creates a view.
     * @param namespace is thie view namespace, i.e. "win" for data windows
     * @param name is the view name, i.e. "length" for length window
     * @param parameters is a list of view parameters, or empty if there are no parameters for the view
     */
    public View(String namespace, String name, List<Object> parameters)
    {
        super(namespace, name, parameters);
    }
}
