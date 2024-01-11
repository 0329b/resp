package thread;

import com.alibaba.fastjson.JSON;
import pojo.*;
import view.Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

/**
 * 类名: Tread.ServerThread
 * 功能描述:接收数据
 * 作者:没礼貌
 * 时间: 2023年08月19日
 * 版本: 1.0
 */
public class ServerThread implements Runnable {
    private Connection conn;
    private DataInputStream dis;
    private DataOutputStream dos;
    private Controller view;
    public ServerThread(Socket socket, Connection conn) throws IOException {
            this.conn = conn;
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
            this.view=new Controller();
    }

    @Override
    public void run() {
        try {
            boolean login=false;
            while (!login) {
                 login= login();
            }
            if (view.getCharater().equals("管理员")) {
                while (true) {
                    String s = dis.readUTF();
                    System.out.println(s);
                    //员工的全部操作
                    try {
                        Employee employee = JSON.parseObject(s,Employee.class);
                        String order=employee.getOrder();
                        if(order.equals("update1") || order.equals("update2") || order.equals("add") || order.equals("clock_in") || order.equals("clock_off")) {
                            Clock clock = JSON.parseObject(s, Clock.class);
                            System.out.println("2");
                            System.out.println(clock.toString());
                            timeCard(clock);
                        }
                        if(order.equals("reset") || order.equals("logout") || order.equals("addvip")) {
                            Vip vip=JSON.parseObject(s,Vip.class);
                            VipFunction(vip);
                        }
                        if(order.equals("day") || order.equals("month") || order.equals("quarter")|| order.equals("year")) {
                            TurnOver tu=JSON.parseObject(s,TurnOver.class);
                            System.out.println("销售："+tu.toString());
                            queryPrice(tu);
                        }
                        allEmployee(employee);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
            if(view.getCharater().equals("收银员")){
                while (true){
                    String s = dis.readUTF();
                    System.out.println("结算数据："+s);
                    try {
                        Cashier product = JSON.parseObject(s, Cashier.class);
                        String order=product.getOrder();
                        if(!order.equals("settle") && !order.equals("!settle") && !order.equals("queryVip") && !order.equals("update1") && !order.equals("update2")){
                            Vip vip=JSON.parseObject(s,Vip.class);
                            System.out.println("1");
                            VipFunction(vip);
                        }
                        if(order.equals("clock_in")||order.equals("clock_off")){
                            Clock clock = JSON.parseObject(s, Clock.class);
                            System.out.println("3");
                            timeCard(clock);
                        }
                        System.out.println("2");
                        cashierView(product);
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
            }
            if(view.getCharater().equals("采购员")) {
                while (true) {
                    String s = dis.readUTF();
                    try {
                        Purchase purchase = JSON.parseObject(s, Purchase.class);
                        System.out.println(purchase);
                        if(purchase.getC_price()==null){
                            Clock clock = JSON.parseObject(s, Clock.class);
                            System.out.println(clock);
                            timeCard(clock);
                        }
                        PurchaseView(purchase);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if(view.getCharater().equals("vip")) {
                while (true) {
                    String s = dis.readUTF();
                    System.out.println(s);
                    try {
                        Vip vip = JSON.parseObject(s, Vip.class);
                        vipView(vip);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("系统错误");
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                dis.close();
                dos.close();
            } catch (IOException e) {
                System.out.println("系统错误");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }

    }
//返回VIP数据
    private void vipView(Vip vip) throws IOException, SQLException {
        String feed=view.vipMain(vip,conn);
        dos.writeUTF(feed);
    }

    //采购员返回用户端
    private void PurchaseView(Purchase purchase) throws IOException, SQLException {
        String feed=view.purchase(purchase,conn);
        dos.writeUTF(feed);
    }

    //收银员操作界面
    private void cashierView(Cashier s) throws IOException, SQLException {
        String feedCashier=view.cashier(s,conn);
        dos.writeUTF(feedCashier);
    }

    //营业额查询
    private void queryPrice(TurnOver tu) throws IOException, SQLException {
        String feedBack=view.adminPrice(tu,conn);
        dos.writeUTF(feedBack);
    }

    //vip操作
    private void VipFunction(Vip vip) throws IOException, SQLException {
        String feedBack=view.adminVIP(vip,conn);
        dos.writeUTF(feedBack);
    }

    //打卡表操作
    private void timeCard(Clock clock) throws IOException, SQLException {
        String time=view.adminTimeController(clock,conn);
        dos.writeUTF(time);
    }

    //员工操作界面
    private void allEmployee(Employee employee ) throws SQLException, IOException {
        System.out.println(employee);
        String add = view.adminController(employee, conn);//管理员控制器
        System.out.println(add);
        dos.writeUTF(add);
    }
//登录
    private boolean login() throws Exception {
        //获取json字符串
        String s = dis.readUTF();
        //解析json，变为对象
        Login loginKind = JSON.parseObject(s, Login.class);
        //服务端功能菜单
        String cli = view.LoginServerView(loginKind, conn);
        System.out.println(cli);
        dos.writeUTF(cli);
        return cli.equals("登陆成功");
    }
}
