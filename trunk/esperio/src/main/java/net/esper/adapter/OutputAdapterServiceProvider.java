package net.esper.adapter;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Dec 9, 2006
 * Time: 9:35:44 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Static factory for implementations of the {@link net.esper.adapter.OutputAdapterService} interface.
 */

public final class OutputAdapterServiceProvider
{
    /**
     * Creates an implementation of the OutputAdapterService interface.
     * @return implementation
     */
    public static OutputAdapterService newService(String configuration)
    {
        return new OutputAdapterServiceImpl(configuration);
    }

}
