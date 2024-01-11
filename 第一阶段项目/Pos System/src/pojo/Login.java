package pojo;

/**
 * 类名: Login
 * 功能描述:json的对象转化登陆类
 * 作者:没礼貌
 * 时间: 2023年08月19日
 * 版本: 1.0
 */
public class Login {
    private String sortName;
    private String password;
    private String number;

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
