package view;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import pojo.Cashier;
import pojo.Vip;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class VipView {
    private DataOutputStream dos;
    private DataInputStream dis;
    private Scanner sc = new Scanner(System.in);
//主页面
    public String vipMainView(DataOutputStream dos, DataInputStream dis) throws IOException {
        System.out.println("请选择vip功能\t\n1.查询积分\t\n2.修改个人信息\t\n3.积分兑换商品\t\n0.退出");
        String select = sc.next();
        switch (select) {
            case "1":
                Vip vip = new Vip("integral");
                return JSON.toJSONString(vip);
            case "2":
                return personalView();
            case "3":
                Vip vip1 = new Vip("integral");
                dos.writeUTF(JSON.toJSONString(vip1));
                dos.flush();
                String s=dis.readUTF();
                System.out.println("你的积分："+s);
                System.out.println("你能兑换的商品如下：");
                Vip query = new Vip("query");
                dos.writeUTF(JSON.toJSONString(query));
                dos.flush();
                String responseJson=dis.readUTF();
                JSONObject responseObj = JSON.parseObject(responseJson);
                JSONObject productsObj = responseObj.getJSONObject("products");
                System.out.println("商品价格信息：");
                for (String product : productsObj.keySet()) {
                    double quantity = productsObj.getDoubleValue(product);
                    System.out.println("商品：" + product + "，价格：" + quantity);
                }
                System.out.println("请你输入你要兑换的商品名以及数量，exit退出");
                Map<String, Integer> productQuantityMap = new HashMap<>();
                while (true) {
                    System.out.print("请输入商品名称：");
                    String product = sc.next();
                    if (product.equalsIgnoreCase("exit")) {
                        break;
                    }
                    System.out.print("请输入商品数量：");
                    int quantity = sc.nextInt();
                    sc.nextLine(); // 处理换行符
                    productQuantityMap.put(product, quantity);
                }
                Vip settle = new Vip("v_settle", productQuantityMap);
                dos.writeUTF(JSON.toJSONString(settle));
                dos.flush();
                System.out.println(dis.readUTF());;
                return vipMainView(dos,dis);
            case "0":
                System.exit(0);
            default:
                System.out.println("没有该操作");
                return vipMainView(dos,dis);
        }
    }
//个人信息
    private String personalView() throws IOException {
        System.out.println("请选择你要修改的信息\t\n1.修改密码\t\n2.修改手机号\t\n0.返回上一层");
        String select = sc.next();
        switch (select) {
            case "1": {
                System.out.println("请你输入旧密码");
                String old_password=sc.next();
                System.out.println("请你输入新密码");
                String new_password=sc.next();
                Vip vip = new Vip("update1",old_password, new_password);
                return JSON.toJSONString(vip);
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
                Vip vip1 = new Vip("update2", phone);
                return JSON.toJSONString(vip1);
            case "0":
                return vipMainView(dos, dis);
            default:
                System.out.println("没有该操作");
                return personalView();
        }
    }
}
