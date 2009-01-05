package com.espertech.esper.event.util;

public class JSONOutputBase implements JSONOutput
{
    public void render(Object object, StringBuilder buf)
    {
        if (object == null)
        {
            buf.append("null");
            return;
        }
        
        buf.append(object.toString());
    }
}
