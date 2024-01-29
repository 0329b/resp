package view;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import pojo.Cashier;
import pojo.Clock;
import pojo.Vip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CashierView {
    private Scanner sc = new Scanner(System.in);

    //收银员主界面
    public String cashMain() throws ParseException {
        System.out.println("请选择收银员功能\t\n1.收银结算\t\n2.会员积分查询\t\n3.开通会员\t\n4.修改个人信息\t\n5.上班打卡\t\n6.下班打卡\t\n0.退出");
        String select = sc.next();
        switch (select) {
            case "1":
                return vipView();
            case "2": {
                System.out.println("请你输入会员账号");
                String number=sc.next();
                Cashier cashier = new Cashier("queryVip", number);
                return JSON.toJSONString(cashier);
            }
            case "3": {
                SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
                System.out.println("请输入vip的账号--名字--电话号码");
                String number=sc.next();
                String name=sc.next();
                String phone=sc.next();
                Date time=new Date();
                Vip vip=new Vip("addvip",name,number,"6666",0,phone,sf.format(time));
                return JSON.toJSONString(vip);
            }
            case "4":
                return personInfoView();
            case "5":
                {
                    SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date time = new Date();
                    String today = sf1.format(time);
                    String judge_time = today + " " + "18:00:00";
                    if (time.after(sf2.parse(judge_time))) {
                        System.out.println("时间已过，无法打上班卡");
                        return cashMain();
                    }
                    Clock clock = new Clock("clock_in", time, sf1.parse(today));
                    return JSON.toJSONString(clock);
                }
            case "6":
                {
                    SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date time = new Date();
                    String today = sf1.format(time);
                    String judge_time = today + " " + "18:00:00";
                    if (time.before(sf2.parse(judge_time))) {
                        System.out.println("时间未到，无法打下班卡");
                        return cashMain();
                    }
                    Clock clock = new Clock("clock_off", time, sf1.parse(today));
                    return JSON.toJSONString(clock);
                }
            case "0":
                System.exit(0);
            default:
                System.out.println("没有该操作");
                return cashMain();
        }
    }
//修改个人信息界面
    private String personInfoView() throws ParseException {
        System.out.println("请选择要修改的信息1.\t\n1.密码修改\t\n2.手机号修改\t\n3.返回上一层");
        String select = sc.next();
        switch (select) {
            case "1": {
                System.out.println("请输入旧密码");
                String password=sc.next();
                System.out.println("请输入新密码");
                String new_password=sc.next();
                Cashier cashier = new Cashier("update1", password, new_password);
                return JSON.toJSONString(cashier);
            }
            case "2":
                System.out.println("请输入新手机号");
                String regx3 = "1[^012]\\d{9}";//手机正则
                String phone;
                while (true) {
                    phone = sc.next();
                    if (phone.matches(regx3)) {
                        break;
                    }
                    System.out.println("手机号不规范,请重新输入");
                }
                Cashier cashier = new Cashier("update2",phone);
                return JSON.toJSONString(cashier);
            case "0":
                return cashMain();
            default:
                System.out.println("没有该操作");
                return  personInfoView();
        }
    }

    //会员结算
    private String vipView() throws ParseException {
        System.out.println("请选择功能1.\t\n1.会员结算\t\n2.非会员结算\t\n3.返回上一层");
        String select = sc.next();
        switch (select) {
            case "1": {
                Map<String, Integer> productQuantityMap = new HashMap<>();
                Scanner scanner = new Scanner(System.in);
                System.out.println("请输入商品及数量，输入exit结束：");

                while (true) {
                    System.out.print("请输入商品名称：");
                    String product = scanner.nextLine();
                    if (product.equalsIgnoreCase("exit")) {
                        break;
                    }

                    System.out.print("请输入商品数量：");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // 处理换行符
                    productQuantityMap.put(product, quantity);
                }
                System.out.println("请输入会员账号");
                String number = sc.next();
                Cashier settle = new Cashier("settle", number,productQuantityMap);
                return JSON.toJSONString(settle);
            }
            case "2":
                Map<String, Integer> productQuantityMap = new HashMap<>();
                Scanner scanner = new Scanner(System.in);
                System.out.println("请输入商品及数量，输入exit结束：");

                while (true) {
                    System.out.print("请输入商品名称：");
                    String product = scanner.nextLine();
                    if (product.equalsIgnoreCase("exit")) {
                        break;
                    }

                    System.out.print("请输入商品数量：");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // 处理换行符
                    productQuantityMap.put(product, quantity);
                }
                Cashier settle = new Cashier("!settle",null ,productQuantityMap);
                return JSON.toJSONString(settle, SerializerFeature.WriteNullStringAsEmpty);
            case "0":
                return cashMain();
            default:
                System.out.println("没有该操作");
                return vipView();
        }
    }
}
