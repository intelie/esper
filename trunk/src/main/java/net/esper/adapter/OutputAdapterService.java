package net.esper.adapter;

import net.esper.client.UpdateListener;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Dec 8, 2006
 * Time: 8:08:08 AM
 * To change this template use File | Settings | File Templates.
 */
public interface OutputAdapterService
{    
    public List<UpdateListener> getMatchingOutputAdapter(String outputStreamAlias);
}
