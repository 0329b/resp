package pojo;

/**
 * 类名: AdminLogin
 * 功能描述:
 * 作者:没礼貌
 * 时间: 2023年08月20日
 * 版本: 1.0
 */
public class Login {
    private String sortName;
    private String number;
    private String password;

    public Login(String sortName, String number, String password) {
        this.sortName = sortName;
        this.number = number;
        this.password = password;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
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
}
