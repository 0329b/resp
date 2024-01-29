package view;

import com.alibaba.fastjson.JSON;
import pojo.Clock;
import pojo.Purchase;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class PurchaserView {
    private Scanner sc = new Scanner(System.in);
    //主菜单
    public String purchaseMain() throws ParseException {
        System.out.println("请选择采购员功能\t\n1.查询进货单\t\n2.完成商品上架\t\n3.完成商品下架\t\n4.上班打卡\t\n5.下班打卡\t\n0.退出");
        String select = sc.next();
        switch (select) {
            case "1" :
                Purchase goods = new Purchase("queryGoods");
                return JSON.toJSONString(goods);
            case "2":
                 return shelveView();
            case "3": {
                System.out.println("请你输入要下架的商品编号");
                int number=sc.nextInt();
                Purchase delete = new Purchase("delete",number);
                return JSON.toJSONString(delete);
            }
            case "4":
            {
                SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date time = new Date();
                String today = sf1.format(time);
                String judge_time = today + " " + "18:00:00";
                if (time.after(sf2.parse(judge_time))) {
                    System.out.println("时间已过，无法打上班卡");
                    return purchaseMain();
                }
                Clock clock = new Clock("clock_in", time, sf1.parse(today));
                return JSON.toJSONString(clock);
            }
            case "5":
            {
                SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date time = new Date();
                String today = sf1.format(time);
                String judge_time = today + " " + "18:00:00";
                if (time.before(sf2.parse(judge_time))) {
                    System.out.println("时间未到，无法打下班卡");
                    return purchaseMain();
                }
                Clock clock = new Clock("clock_off", time, sf1.parse(today));
                return JSON.toJSONString(clock);
            }
            case "0":
                System.exit(0);
            default:
                System.out.println("没有该操作");
                return purchaseMain();
        }
    }
//商品上架界面
    private String shelveView() throws ParseException {
        System.out.println("请选择上架功能\t\n1.已有商品上架\t\n2.新商品上架\t\n0.返回上一层");
        String select = sc.next();
        switch (select) {
            case "1":
                System.out.println("请输入已有商品编号");
                int number=sc.nextInt();
                System.out.println("增加商品数量");
                int inventory=sc.nextInt();
                Purchase add = new Purchase("add", number, inventory);
                return JSON.toJSONString(add);
            case "2":
                System.out.println("输入薪增商品名");
                String name=sc.next();
                System.out.println("请输入新商品的非会员价格");
                BigDecimal c_price=sc.nextBigDecimal();
                System.out.println("请输入新商品库存数量");
                int inventorys=sc.nextInt();
                Purchase new_add = new Purchase("new_add", name, c_price, inventorys);
                return JSON.toJSONString(new_add);
            case "0":
                return purchaseMain();
            default:
                System.out.println("没有该操作");
                return shelveView();
        }
    }

}
