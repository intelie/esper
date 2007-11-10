package net.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Create a named window, defining the parameter of the named window such as window name and data window view name(s).
 */
public class CreateWindowClause implements Serializable
{
    private static final long serialVersionUID = 0L;

    private String windowName;
    private List<View> views;

    /**
     * Creates a clause to create a named window.
     * @param windowName is the name of the named window
     * @param view is a data window view
     * @return create window clause
     */
    public static CreateWindowClause create(String windowName, View view)
    {
        return new CreateWindowClause(windowName, new View[] {view});
    }

    /**
     * Creates a clause to create a named window.
     * @param windowName is the name of the named window
     * @param views is the data window views
     * @return create window clause
     */
    public static CreateWindowClause create(String windowName, View... views)
    {
        return new CreateWindowClause(windowName, views);
    }

    /**
     * Adds an un-parameterized view to the stream.
     * @param namespace is the view namespace, for example "win" for most data windows
     * @param name is the view name, for example "length" for a length window
     * @return stream
     */
    public CreateWindowClause addView(String namespace, String name)
    {
        views.add(View.create(namespace, name));
        return this;
    }

    /**
     * Adds a parameterized view to the stream.
     * @param namespace is the view namespace, for example "win" for most data windows
     * @param name is the view name, for example "length" for a length window
     * @param parameters is a list of view parameters
     * @return stream
     */
    public CreateWindowClause addView(String namespace, String name, List<Object> parameters)
    {
        views.add(View.create(namespace, name, parameters));
        return this;
    }

    /**
     * Ctor.
     * @param windowName is the stream name to insert into
     * @param viewArr is the list of data window views
     */
    public CreateWindowClause(String windowName, View[] viewArr)
    {
        this.windowName = windowName;
        views = new ArrayList<View>();
        if (viewArr != null)
        {
            for (View view : viewArr)
            {
                views.add(view);
            }
        }
    }

    /**
     * Ctor.
     * @param windowName is the stream name to insert into
     */
    public CreateWindowClause(String windowName, List<View> views)
    {
        this.windowName = windowName;
        this.views = views;
    }

    /**
     * Renders the clause in textual representation.
     * @param writer to output to
     */
    public void toEQL(StringWriter writer)
    {
        writer.write("create window ");
        writer.write(windowName);
        ProjectedStream.toEQLViews(writer, views);
    }

    public String getWindowName()
    {
        return windowName;
    }

    public void setWindowName(String windowName)
    {
        this.windowName = windowName;
    }

    public List<View> getViews()
    {
        return views;
    }

    public void setViews(List<View> views)
    {
        this.views = views;
    }
}
