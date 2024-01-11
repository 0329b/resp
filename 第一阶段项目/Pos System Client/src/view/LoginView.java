package view;

import pojo.Login;

import java.util.Scanner;

/**
 * 类名: view.View
 * 功能描述:
 * 作者:没礼貌
 * 时间: 2023年08月20日
 * 版本: 1.0
 */
public class LoginView {
private Scanner sc=new Scanner(System.in);
//员工登录功能
    public Login employee() {
        System.out.println("请你输入登录身份(如管理员)--账号--密码");
        String sortName=sc.next();
        String number=sc.next();
        String password=sc.next();
        Login admin=new Login(sortName,number,password);
        return admin;
    }
}
