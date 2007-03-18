/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.expression;

import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.ViewResourceDelegate;

/**
 * Validation interface for expression nodes.
 */
public interface ExprValidator
{
    /**
     * Validate node.
     * @param streamTypeService serves stream event type info
     * @param autoImportService - for resolving class names in library method invocations
     * @param viewResourceDelegate - delegates for view resources to expression nodes
     * @throws ExprValidationException thrown when validation failed
     */
    public void validate(StreamTypeService streamTypeService,
                         AutoImportService autoImportService,
                         ViewResourceDelegate viewResourceDelegate) throws ExprValidationException;

    /**
     * Returns the type that the node's evaluate method returns an instance of.
     * @return type returned when evaluated
     * @throws ExprValidationException thrown when validation failed
     */
    public Class getType() throws ExprValidationException;
}
