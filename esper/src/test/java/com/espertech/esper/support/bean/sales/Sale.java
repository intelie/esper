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
