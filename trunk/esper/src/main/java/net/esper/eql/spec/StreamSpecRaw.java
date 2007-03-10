package net.esper.eql.spec;

import net.esper.eql.core.AutoImportService;
import net.esper.eql.expression.ExprValidationException;
import net.esper.event.EventAdapterService;

public interface StreamSpecRaw extends StreamSpec
{
    public StreamSpecCompiled compile(EventAdapterService eventAdapterService,
                                      AutoImportService autoImportService)
        throws ExprValidationException;

}
