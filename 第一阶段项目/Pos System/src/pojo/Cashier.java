package pojo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Cashier {
    private String order;
    private Map<String, Integer> productQuantityMap; // 商品名称与数量的映射
    //会员账号
    private String number;
    private String password;
    private String new_password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    //会员积分操作
    public Cashier(String order, String number) {
        this.order = order;
        this.number = number;
    }
//修改手机号

    public Cashier(String order, String number, String password) {
        this.order = order;
        this.number = number;
        this.password = password;
    }

    public Cashier(String order, String number, String password, String new_password) {
        this.order = order;
        this.number = number;
        this.password = password;
        this.new_password = new_password;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    public Cashier(String order, String number, Map<String, Integer> productQuantityMap) {
        this.order = order;
        this.number=number;
        this.productQuantityMap = productQuantityMap; // 初始化商品名称与数量的映射
    }
    public Map<String, Integer> getProductQuantityMap() {
        return productQuantityMap;
    }

    public Cashier() {
    }

    public void setProductQuantityMap(Map<String, Integer> productQuantityMap) {
        this.productQuantityMap = productQuantityMap;
    }


    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Cashier{" +
                "order='" + order + '\'' +
                ", productQuantityMap=" + productQuantityMap +
                ", number='" + number + '\'' +
                '}';
    }
}