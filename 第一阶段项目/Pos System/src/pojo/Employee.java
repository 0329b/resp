package pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 类名: Cashier
 * 功能描述:
 * 作者:没礼貌
 * 时间: 2023年08月21日
 * 版本: 1.0
 */
public class Employee {
    //功能类型
    private String order;
    private String number;
    private String password;
    private String userName;
    private String phone;
    private String sex;
    private int role;
    //查询日期所需
    private Date start_Date;
    private Date end_Date;
    private String rname;

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public Date getStart_Date() {
        return start_Date;
    }

    public void setStart_Date(Date start_Date) {
        this.start_Date = start_Date;
    }

    public Date getEnd_Date() {
        return end_Date;
    }

    public void setEnd_Date(Date end_Date) {
        this.end_Date = end_Date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    private String date;
    private BigDecimal salary;

    //注销用户使用
    public Employee(String order, String number) {
        this.order = order;
        this.number = number;
    }

    //修改密码
    public Employee(String order, String number, String password) {
        this.order = order;
        this.number = number;
        this.password = password;
    }

    //操作时间指令构造器
    public Employee(String order, Date start_Date, Date end_Date) {
        this.order = order;
        this.start_Date = start_Date;
        this.end_Date = end_Date;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Employee(String order, String number, String password, String userName, String phone, String sex, int role, String date, BigDecimal salary) {
        this.order = order;
        this.number = number;
        this.password = password;
        this.userName = userName;
        this.phone = phone;
        this.sex = sex;
        this.role = role;
        this.date = date;
        this.salary = salary;
    }

    public Employee(String number, String password, String userName, String phone, String sex, String rname, String date, BigDecimal salary) {
        this.number = number;
        this.password = password;
        this.userName = userName;
        this.phone = phone;
        this.sex = sex;
        this.rname = rname;
        this.date = date;
        this.salary = salary;
    }

    public Employee() {
    }

    @Override
    public String toString() {
        return "工号：" + number +
                ", 密码：" + password +
                ", 用户名：" + userName +
                ", 电话：" + phone +
                ", 性别：" + sex +
                ", 职位：" + role +
                ",注册日期：" + date +
                ",工资：" + salary;
    }
}
