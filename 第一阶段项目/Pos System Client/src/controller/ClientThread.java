package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import pojo.Clock;
import pojo.Employee;
import pojo.Login;
import pojo.Purchase;
import view.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.ParseException;


/**
 * 类名: thread.ClientThread
 * 功能描述:
 * 作者:没礼貌
 * 时间: 2023年08月20日
 * 版本: 1.0
 */
public class ClientThread implements Runnable{
    private Socket socket;
    private String ip="127.0.0.1";
    private int port = 30029;
    private DataOutputStream dos;
    private DataInputStream dis;
    private boolean isLogin=false;  //判断是否已经登录
    private LoginView view=new LoginView();//登录主界面
    private AdminView adminView=new AdminView();
    private CashierView cashierView=new CashierView();
    private PurchaserView purchaserView=new PurchaserView();
    private VipView vip=new VipView();
    public ClientThread() throws IOException {
        this.socket = new Socket(ip,port);
        this.dos = new DataOutputStream(socket.getOutputStream());
        this.dis = new DataInputStream(socket.getInputStream());
    }

    public void run()  {
        //返回一个登录对象
        String judge = null;
        Login str = null;
        while (!isLogin) {
            str= view.employee();
            login(str);
            //接受服务端的返回信息
            try {
                judge = dis.readUTF();
                // 如果服务器返回登录成功，则退出循环
                if (judge.equals("登陆成功")) {
                    isLogin = true;
                }else {
                    System.out.println(judge + " 请检查输入是否正确");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(str.getSortName().equals("管理员")&&judge.equals("登陆成功")){
            String s ;
            try {
                while (true) {
                    s = adminView.command();
                    dos.writeUTF(s);
                    dos.flush();
                    String result= dis.readUTF();
                    try{
                        //解析json字符串
                        JSONObject json =JSONObject.parseObject(result);
                        String key= json.getString("isList");
                        String jsonString = json.getString("data");
                        //将json数据转化成数组
                        JSONArray objects = JSON.parseArray(jsonString);
                        for (int i = 0; i < objects.size(); i++) {
                            JSONObject jo = objects.getJSONObject(i);
                            //解析json对象
                            if(key.equals("em")) {
                                Employee employee = JSON.toJavaObject(jo, Employee.class);
                                System.out.println(employee.toString());
                            }else {
                                Clock clock=JSON.toJavaObject(jo,Clock.class);
                                System.out.println(clock.toString());
                            }
                        }
                    }catch (Exception e){
                        String word = result;
                        System.out.println(word);
                    }
                }
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        }
        if(str.getSortName().equals("收银员")&&judge.equals("登陆成功")) {
            String s;
            while(true){
                try {
                    s=cashierView.cashMain();
                    System.out.println("传输数据"+s);
                    dos.writeUTF(s);
                    dos.flush();
                    String result= dis.readUTF();
                    System.out.println(result);
                } catch (IOException | ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if(str.getSortName().equals("采购员")&&judge.equals("登陆成功")) {
            String s;
            while(true){
                try {
                    s=purchaserView.purchaseMain();
                    dos.writeUTF(s);
                    dos.flush();
                    String result= dis.readUTF();
                    try {
                        JSONObject json =JSONObject.parseObject(result);
                        String jsonString = json.getString("data");
                        //将json数据转化成数组
                        JSONArray objects = JSON.parseArray(jsonString);
                        for (int i = 0; i < objects.size(); i++) {
                            JSONObject jo = objects.getJSONObject(i);
                            //解析json对象
                            Purchase clock = JSON.toJavaObject(jo, Purchase.class);
                            System.out.println(clock.toString());
                        }
                    } catch (Exception e) {
                        System.out.println(result);
                    }
                } catch (IOException | ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if(str.getSortName().equals("vip")&&judge.equals("登陆成功")) {
            String s;
            while (true) {
                try {
                    s =vip.vipMainView(dos,dis);
                    dos.writeUTF(s);
                    dos.flush();
                    String result = dis.readUTF();
                    System.out.println(result);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void login(Login str) {
        String s = JSON.toJSONString(str);
        try {
            dos.writeUTF(s);
            dos.flush();
        } catch (IOException e) {
            System.out.println("系统错误");
        }
    }
}
