/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.support.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.HashSet;

/**
 * Singleton class for testing out multi-threaded code.
 * Allows reservation and de-reservation of any Object. Reserved objects are added to a HashSet and
 * removed from the HashSet upon de-reservation.
 */
public class ObjectReservationSingleton
{
    private HashSet<Object> reservedObjects = new HashSet<Object>();
    private Lock reservedIdsLock = new ReentrantLock();

    private static ObjectReservationSingleton ourInstance = new ObjectReservationSingleton();

    public static ObjectReservationSingleton getInstance()
    {
        return ourInstance;
    }

    private ObjectReservationSingleton()
    {
    }

    /**
     * Reserve an object, returning true when successfully reserved or false when the object is already reserved.
     * @param object - object to reserve
     * @return true if reserved, false to indicate already reserved
     */
    public boolean reserve(Object object)
    {
        reservedIdsLock.lock();

        if (reservedObjects.contains(object))
        {
            reservedIdsLock.unlock();
            return false;
        }

        reservedObjects.add(object);

        reservedIdsLock.unlock();
        return true;
    }

    /**
     * Unreserve an object. Logs a fatal error if the unreserve failed.
     * @param object - object to unreserve
     */
    public void unreserve(Object object)
    {
        reservedIdsLock.lock();

        if (!reservedObjects.contains(object))
        {
            log.fatal(".unreserve FAILED, object=" + object);
            reservedIdsLock.unlock();
            return;
        }

        reservedObjects.remove(object);

        reservedIdsLock.unlock();
    }

    private static final Log log = LogFactory.getLog(ObjectReservationSingleton.class);
}