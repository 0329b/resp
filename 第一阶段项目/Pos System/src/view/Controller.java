package view;
import pojo.*;
import server.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 类名: View
 * 功能描述:连接服务端与实现功能体
 * 作者:没礼貌
 * 时间: 2023年08月19日
 * 版本: 1.0
 */
public class Controller {
    private boolean isLogin;
    private String charater;

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getCharater() {
        return charater;
    }

    public void setCharater(String charater) {
        this.charater = charater;
    }

    private AdminService admin = new AdminServiceImpl();
    private CashierService cash=new CashierServiceImpl();
    private PurchaseService purchases=new PurchaseServiceIMpl();
    private VipService vipService=new VipServiceImpl();

    //服务端功能菜单
    public String LoginServerView(Login loginKind, Connection conn) throws SQLException {
        switch (loginKind.getSortName()) {
            case "管理员": {
                boolean judge = admin.adminLogin(loginKind, conn);
                System.out.println(judge);
                charater = "管理员";
                return judge ? "登陆成功" : "登陆失败";
            }
            case "收银员": {
                boolean judge = admin.adminLogin(loginKind, conn);
                System.out.println(judge);
                charater = "收银员";
                return judge ? "登陆成功" : "登陆失败";
            }
            case "采购员":{
                boolean judge = admin.adminLogin(loginKind, conn);
                System.out.println(judge);
                charater = "采购员";
                return judge ? "登陆成功" : "登陆失败";
            }
            case "vip":{
                boolean judge = vipService.Login(loginKind, conn);
                System.out.println(judge);
                charater = "vip";
                return judge ? "登陆成功" : "登陆失败";
            }
            default:
                return "不存在角色";
        }
    }

    //管理员给你操作员工界面
    public String adminController(Employee employee, Connection conn) throws SQLException {
        String order = employee.getOrder();
        switch (order) {
            case "add":
                return admin.add(employee, conn) ? "添加成功" : "添加失败";
            case "update":
                return admin.update(employee, conn) ? "修改密码成功" : "修改密码失败";
            case "delete":
                return admin.delete(employee, conn) ? "注销成功" : "注销失败";
            case "late":
                return admin.late(employee, conn);
            case "absent":
                return admin.absent(employee, conn);
            case "leave":
                return admin.leave(employee, conn);
            case "normal":
                return admin.normal(employee, conn);
            case "anomaly":
                return admin.anomaly(employee, conn);
             //查询函数
            case "pointQuery":
                return admin.pointQuery(employee,conn);
            case "keyQuery":
                return admin.keyQuery(employee,conn);
            case "depart":
                return admin.depart(employee,conn);
            case "role":
                return admin.role(employee,conn);
            case "top10":
                return admin.top10(conn);
            case "rand":
                return admin.rand(employee,conn);
            case "top5":
                return admin.top5(conn);
            default:
                return "没有这个操作";
        }
    }

    //补卡操作
    public String adminTimeController(Clock clock, Connection conn) throws SQLException {
        String order = clock.getOrder();
        switch (order) {
            case "update1":
                return admin.updateCard1(clock, conn)?"补卡成功":"补卡失败";
            case "update2":
                return admin.updateCard2(clock, conn)?"补卡成功":"补卡失败";
            case "add":
                return admin.addDay(clock,conn)?"添加成功":"添加失败";
            case "clock_in":
                return admin.clock_in(clock,conn)?"打卡成功":"打卡失败";
            case "clock_off":
                return admin.clock_off(clock,conn)?"打卡成功":"打卡失败";
            default:
                return "没有该操作";
        }
    }
//添加会员
    public String adminVIP(Vip vip, Connection conn) throws SQLException {
        String order=vip.getOrder();
        switch (order){
            case "addvip":
                return admin.addVip(vip,conn)?"添加成功":"添加失败";
            case "reset":
                return admin.resetVip(vip,conn)?"重置成功":"重置失败";
            case "logout":
                return admin.logoutVip(vip,conn)?"注销成功":"注销失败";
            default:
                return "没有该操作";
        }
    }
//查询营业额
    public String adminPrice(TurnOver tu, Connection conn) throws SQLException {
        String type = tu.getOrder();
        switch (type){
            case "day":
                return admin.queryDay(tu,conn);
            case "month":
                return admin.queryMonth(tu,conn);
            case "quarter":
                return admin.queryQuarter(tu,conn);
            case "year":
                return admin.queryYear(tu,conn);
            default:
                return "没有该操作";
        }
    }
//收银员界面
    public String cashier(Cashier s, Connection conn) throws SQLException {
        String type=s.getOrder();
        switch (type){
            case "settle":
                return cash.settle(s,conn);
            case "!settle":
                return cash.settle2(s,conn);
            case "queryVip":
                return cash.queryVip(s,conn);
            case "update1":
                return cash.update1(s,conn);
            case "update2":
                return cash.update2(s,conn);
            default:
                return "没有该操作";
        }

    }
//采购员
    public String purchase(Purchase purchase, Connection conn) throws SQLException {
        String type=purchase.getOrder();
        switch (type){
            case "queryGoods":
                return purchases.queryGoods(purchase,conn);
            case "add":
                return purchases.addGoods(purchase,conn);
            case "new_add":
                return purchases.new_addGoods(purchase,conn);
            case "delete":
                return purchases.deleteGoods(purchase,conn);
            default:
                return "没有该操作";
        }
    }
//vip操作界面
    public String vipMain(Vip vip, Connection conn) throws SQLException {
        String type=vip.getOrder();
        switch (type){
            case "integral":
                return vipService.integral(vip,conn);
            case "update1":
                return vipService.update1(vip,conn);
            case "update2":
                return vipService.update2(vip,conn);
            case "query":
                return vipService.query(vip,conn);
            case "v_settle":
                return vipService.v_settle(vip,conn);
            default:
                return "没有该操作";
        }
    }
}