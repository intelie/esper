package net.esper.client.soda;

import java.util.List;
import java.io.Serializable;

public abstract class ProjectedStream implements Serializable
{
    private List<View> views;
    private String optStreamName;

    protected ProjectedStream(List<View> views, String optStreamName)
    {
        this.optStreamName = optStreamName;
        this.views = views;
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
}
