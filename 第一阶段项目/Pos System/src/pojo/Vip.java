package pojo;

import java.util.Map;

/**
 * 类名: Vip
 * 功能描述:
 * 作者:没礼貌
 * 时间: 2023年08月22日
 * 版本: 1.0
 */
public class Vip {
    private String order;
    private String v_name;
    private String v_number;
    private String v_password;
    private int v_score;
    private String v_phone;
    private String v_date;
    private int v_remark;

    public Vip() {
    }

    public String getV_name() {
        return v_name;
    }

    public void setV_name(String v_name) {
        this.v_name = v_name;
    }

    public String getV_number() {
        return v_number;
    }

    public void setV_number(String v_number) {
        this.v_number = v_number;
    }

    public int getV_score() {
        return v_score;
    }

    public void setV_score(int v_score) {
        this.v_score = v_score;
    }

    public String getV_phone() {
        return v_phone;
    }

    public void setV_phone(String v_phone) {
        this.v_phone = v_phone;
    }

    public String getV_date() {
        return v_date;
    }

    public void setV_date(String v_date) {
        this.v_date = v_date;
    }

    public String getV_password() {
        return v_password;
    }

    public void setV_password(String v_password) {
        this.v_password = v_password;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getV_remark() {
        return v_remark;
    }

    public void setV_remark(int v_remark) {
        this.v_remark = v_remark;
    }

    public Vip(String order, String v_name, String v_number, String v_password, int v_score, String v_phone, String v_date) {
        this.order=order;
        this.v_name = v_name;
        this.v_number = v_number;
        this.v_password=v_password;
        this.v_score = v_score;
        this.v_phone = v_phone;
        this.v_date = v_date;
    }

    public Vip(String order, String v_number, String v_password) {
        this.order = order;
        this.v_number=v_number;
        this.v_password = v_password;
    }

    public Vip(String order, String v_number , int v_remark) {
        this.order = order;
        this.v_number=v_number;
        this.v_remark = v_remark;
    }
    private Map<String, Integer> productQuantityMap; // 商品名称与数量的映射

    public Vip(String order, Map<String, Integer> productQuantityMap, String v_number) {
        this.order = order;
        this.productQuantityMap = productQuantityMap;
        this.v_number = v_number;
    }

    public Map<String, Integer> getProductQuantityMap() {
        return productQuantityMap;
    }

    public void setProductQuantityMap(Map<String, Integer> productQuantityMap) {
        this.productQuantityMap = productQuantityMap;
    }

    @Override
    public String toString() {
        return "VIP信息" +
                "姓名：" + v_name +
                ", 账号：" + v_number +
                ",密码："+v_password+
                ", 积分：" + v_score +
                ", 电话：" + v_phone +
                ", 注册日期：" + v_date ;
    }
}
