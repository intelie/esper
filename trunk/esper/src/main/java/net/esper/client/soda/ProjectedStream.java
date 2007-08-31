package net.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;

public abstract class ProjectedStream implements Serializable
{
    private List<View> views;
    private String optStreamName;

    public abstract void toEQLStream(StringWriter writer);

    protected ProjectedStream(List<View> views, String optStreamName)
    {
        this.optStreamName = optStreamName;
        this.views = views;
    }

    public ProjectedStream addView(String namespace, String name)
    {
        views.add(View.create(namespace, name));
        return this;
    }

    public ProjectedStream addView(String namespace, String name, List<Object> parameters)
    {
        views.add(View.create(namespace, name, parameters));
        return this;
    }

    public ProjectedStream addView(String namespace, String name, Object ...parameters)
    {
        views.add(View.create(namespace, name, parameters));
        return this;
    }

    public ProjectedStream addView(View view)
    {
        views.add(view);
        return this;
    }

    public List<View> getViews()
    {
        return views;
    }

    public void setViews(List<View> views)
    {
        this.views = views;
    }

    public String getOptStreamName()
    {
        return optStreamName;
    }

    public void setOptStreamName(String optStreamName)
    {
        this.optStreamName = optStreamName;
    }

    public void toEQL(StringWriter writer)
    {
        toEQLStream(writer);

        if ((views != null) && (views.size() != 0))
        {
            writer.write('.');
            String delimiter = "";
            for (View view : views)
            {
                writer.write(delimiter);
                view.toEQL(writer);
                delimiter = ".";
            }
        }

        if (optStreamName != null)
        {
            writer.write(" as ");
            writer.write(optStreamName);
        }
    }
}
