package net.esper.eql.spec;

import net.esper.eql.core.AutoImportService;
import net.esper.eql.expression.ExprValidationException;
import net.esper.event.EventAdapterService;

/**
 * An uncompiled, unoptimize for of stream specification created by a parser.
 */
public interface StreamSpecRaw extends StreamSpec
{
    /**
     * Compiles a raw stream specification consisting event type information and filter expressions
     * to an validated, optimized form for use with filter service
     * @param eventAdapterService supplies type information
     * @param autoImportService for resolving imports
     * @return compiled stream
     * @throws ExprValidationException to indicate validation errors
     */
    public StreamSpecCompiled compile(EventAdapterService eventAdapterService,
                                      AutoImportService autoImportService)
        throws ExprValidationException;

}
