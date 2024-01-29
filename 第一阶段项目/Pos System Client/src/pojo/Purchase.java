package pojo;

import java.math.BigDecimal;

public class Purchase {
    private String order;
    private int number;
    private String c_name;
    private BigDecimal c_price;
    private BigDecimal vip_price;
    private int inventory;

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public BigDecimal getC_price() {
        return c_price;
    }

    public void setC_price(BigDecimal c_price) {
        this.c_price = c_price;
    }

    public BigDecimal getVip_price() {
        return vip_price;
    }

    public void setVip_price(BigDecimal vip_price) {
        this.vip_price = vip_price;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Purchase(String order) {
        this.order = order;
    }

    public Purchase(String order, int number, int inventory) {
        this.order = order;
        this.number = number;
        this.inventory = inventory;
    }

    public Purchase(String order, int number) {
        this.order = order;
        this.number = number;
    }

    public Purchase(String order, String c_name, BigDecimal c_price, int inventory) {
        this.order = order;
        this.c_name = c_name;
        this.c_price = c_price;
        this.inventory = inventory;
    }

    public Purchase() {
    }

    @Override
    public String toString() {
        return "商品编号：" + number +
                ",商品名：" + c_name +
                ",商品价格：" + c_price +
                ",会员价格：" + vip_price +
                ",库存：" + inventory ;
    }
}
