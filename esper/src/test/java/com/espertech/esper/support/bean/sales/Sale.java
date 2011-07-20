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

package com.espertech.esper.support.bean.sales;

public class Sale {
    private Person buyer;
    private Person seller;
    private double cost;

    public Sale(Person buyer, Person seller, double cost) {
        this.buyer = buyer;
        this.seller = seller;
        this.cost = cost;
    }

    public Person getBuyer() {
        return buyer;
    }

    public Person getSeller() {
        return seller;
    }

    public double getCost() {
        return cost;
    }
}
