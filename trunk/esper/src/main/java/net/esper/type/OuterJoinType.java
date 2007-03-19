/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.type;

/**
 * Enum for the type of outer join.
 */
public enum OuterJoinType
{
    /**
     * Left outer join.
     */
    LEFT,

    /**
     * Right outer join.
     */
    RIGHT,

    /**
     * Full outer join.
     */
    FULL;
}
