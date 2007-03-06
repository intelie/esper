package net.esper.eql.spec;

import net.esper.view.ViewSpec;

import java.util.List;
import java.util.LinkedList;

public interface StreamSpec
{
    public String getOptionalStreamName();
    public List<ViewSpec> getViewSpecs();
}
