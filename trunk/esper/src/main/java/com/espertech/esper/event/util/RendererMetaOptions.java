package com.espertech.esper.event.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.espertech.esper.client.util.JSONRenderingOptions;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.EventPropertyDescriptor;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.FragmentEventType;

import java.util.Stack;
import java.util.ArrayList;

public class RendererMetaOptions
{
    private final boolean isXMLOutput;
    private final boolean preventLooping;

    public RendererMetaOptions(boolean preventLooping, boolean isXMLOutput)
    {
        this.preventLooping = preventLooping;
        this.isXMLOutput = isXMLOutput;
    }

    public boolean isPreventLooping()
    {
        return preventLooping;
    }

    public boolean isXMLOutput()
    {
        return isXMLOutput;
    }
}
