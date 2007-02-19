package net.esper.support.bean;

public class SupportPriceEvent
{
    int price;
    String sym;

    public SupportPriceEvent(int price, String sym) {
        this.price = price;
        this.sym = sym;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSym() {
        return sym;
    }

    public void setSym(String sym) {
        this.sym = sym;
    }
}
