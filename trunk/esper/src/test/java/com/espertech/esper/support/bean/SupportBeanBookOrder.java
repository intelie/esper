package com.espertech.esper.support.bean;

public class SupportBeanBookOrder
{
    private BookDesc[] books;
    private Order orderdetail;

    public SupportBeanBookOrder(BookDesc[] books, Order order)
    {
        this.books = books;
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

    public static class Order
    {
        private String orderId;
        private OrderItem[] items;

        public Order(String orderId, OrderItem[] items)
        {
            this.items = items;
            this.orderId = orderId;
        }

        public OrderItem[] getItems()
        {
            return items;
        }

        public String getOrderId()
        {
            return orderId;
        }
    }

    public static class OrderItem
    {
        private String bookId;
        private int amount;
        private double price;

        public OrderItem(String bookId, int amount, double price)
        {
            this.amount = amount;
            this.bookId = bookId;
            this.price = price;
        }

        public int getAmount()
        {
            return amount;
        }

        public String getBookId()
        {
            return bookId;
        }

        public double getPrice()
        {
            return price;
        }
    }

    public static class BookDesc
    {
        private final String id;
        private final String title;
        private final String author;

        public BookDesc(String id, String title, String author)
        {
            this.author = author;
            this.id = id;
            this.title = title;
        }

        public String getAuthor()
        {
            return author;
        }

        public String getId()
        {
            return id;
        }

        public String getTitle()
        {
            return title;
        }
    }
}
