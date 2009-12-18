package com.espertech.esper.support.bean.bookexample;

public class OrderBean
{
    private Order orderdetail;
    private BookDesc[] books;
    private GameDesc[] games;

    public OrderBean(Order order, BookDesc[] books, GameDesc[] games)
    {
        this.books = books;
        this.games = games;
        this.orderdetail = order;
    }

    public BookDesc[] getBooks()
    {
        return books;
    }

    public Order getOrderdetail()
    {
        return orderdetail;
    }

    public GameDesc[] getGames()
    {
        return games;
    }
}
