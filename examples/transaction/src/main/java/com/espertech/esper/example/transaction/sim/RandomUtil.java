/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.transaction.sim;

import java.security.SecureRandom;
import java.util.Random;

/** Just so we can swap between Random and SecureRandom.
 *
 * @author Hans Gilde
 *
 */
public class RandomUtil {
    public static Random getNewInstance() {
        return new SecureRandom();
    }
}
