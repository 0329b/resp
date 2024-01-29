package view;

import com.alibaba.fastjson.JSON;
import pojo.Clock;
import pojo.Employee;
import pojo.TurnOver;
import pojo.Vip;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * 类名: employee
 * 功能描述:
 * 作者:没礼貌
 * 时间: 2023年08月20日
 * 版本: 1.0
 */
public class AdminView {
    private Scanner sc=new Scanner(System.in);
//增删改查
    public String command() throws ParseException {
        System.out.println("请选择功能\t\n1.收银员管理\t\n2.采购员管理\t\n3.员工考勤管理\t\n4.会员管理\t\n5.超市营业额\t\n6.员工信息查询\t\n7.上下班打卡\t\n0.退出");
        String select=sc.next();
        switch (select){
            case "1":
                String s = cashierView();
                return s ;
            case "2":
                String buyer=buyerView();
                return buyer;
            case "3":
                String check=staffView();
                return check;
            case "4":
                String vip=vipView();
                return vip;
            case "5":
                return turnoverView();
            case "6":
                return queryView();
            case "7":
                return clock_inView();
            case "0":
                System.exit(0);
            default:
                System.out.println("没有该操作");
                return command();
        }
    }
//打卡界面
    private String clock_inView() throws ParseException {
        System.out.println("请选择打卡功能\t\n1.上班打卡\t\n2.下班打卡\t\n0.退出");
        String select = sc.next();
        switch (select) {
            case "1":
            {
                SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date time = new Date();
                String today = sf1.format(time);
                String judge_time = today + " " + "18:00:00";
                if (time.after(sf2.parse(judge_time))) {
                    System.out.println("时间已过，无法打上班卡");
                    return clock_inView();
                }
                Clock clock = new Clock("clock_in", time, sf1.parse(today));
                return JSON.toJSONString(clock);
            }
            case "2":
            {
                SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date time = new Date();
                String today = sf1.format(time);
                String judge_time = today + " " + "18:00:00";
                if (time.before(sf2.parse(judge_time))) {
                    System.out.println("时间未到，无法打下班卡");
                    return clock_inView();
                }
                Clock clock = new Clock("clock_off", time, sf1.parse(today));
                return JSON.toJSONString(clock);
            }
            case "0":
                return command();
            default:
                System.out.println("没有该操作");
                return clock_inView();
        }
    }

    //员工查询界面
    private String queryView() throws ParseException {
        System.out.println("请选择员工查询功能\t\n1.指定员工查询\t\n2.模糊查询员工\t\n3.查询离职员工\t\n4.按角色查询员工在职\t\n5.员工薪水\t\n0.返回上一层");
        String select=sc.next();
        switch (select){
            case "1":{
                System.out.println("请你输入员工的工号或者手机号或者名字");
                String point=sc.next();
                Employee employee=new Employee("pointQuery",point);
                return JSON.toJSONString(employee);
            }
            case "2":{
                System.out.println("请你输入你要查询的关键字");
                String key=sc.next();
                Employee employee=new Employee("keyQuery",key);
                return JSON.toJSONString(employee);
            }
            case "3":{
                Employee employee=new Employee("depart");
                return JSON.toJSONString(employee);
            }
            case "4":{
                System.out.println("请输入员工的职位");
                String role=sc.next();
                Employee employee=new Employee("role",role);
                return JSON.toJSONString(employee);
            }
            case "5":
                return employeeSalaryView();
            case "0":
                return command();
            default:
                System.out.println("没有该操作");
                return queryView();
        }
    }
//员工薪水界面
    private String employeeSalaryView() throws ParseException {
        System.out.println("请选择员工薪水查询功能\t\n1.查询出TOP10的员工薪水\t\n2.查询指定区间段的员工薪水\t\n3.查询薪水最低的员工TOP5\t\n0.返回上一层");
        String select=sc.next();
        switch (select){
            case "1":{
                Employee employee=new Employee("top10");
                return JSON.toJSONString(employee);
            }
            case "2":{
                System.out.println("请输入要查询的开始区间");
                String start=sc.next();
                System.out.println("请输入要查询的结束区间");
                String end=sc.next();
                Employee employee=new Employee("rand",start,end);
                return JSON.toJSONString(employee);
            }
            case "3":{
                Employee employee=new Employee("top5");
                return JSON.toJSONString(employee);
            }
            case "0":
                return queryView();
            default:
                System.out.println("没有该操作");
                return employeeSalaryView();
        }
    }

    //营业额
    private String turnoverView() throws ParseException {
        System.out.println("请选择超市营业额功能\t\n1.日营业额\t\n2.月营业额\t\n3.季营业额\t\n4.年营业额\t\n5.指定时间段营业额\t\n0.返回上一层");
        String select=sc.next();
        switch (select){
            case "1": {
                System.out.println("请你输入要查询的日期(yyyy-MM-dd)");
                String dayPrice=sc.next();
                TurnOver turnOver=new TurnOver("day",dayPrice);
                return JSON.toJSONString(turnOver);
            }
            case "2":{
                System.out.println("请你输入开始查询的日期(yyyy-MM)");
                String dayPrice=sc.next();
                String start_time=dayPrice+"-01";
                int year = Integer.parseInt(dayPrice.substring(0, 4));
                int month = Integer.parseInt(dayPrice.substring(5));
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month - 1);
                int lastDay = calendar.getActualMaximum(Calendar.DATE);
                String end_time=dayPrice+"-"+lastDay;
                TurnOver turnOver=new TurnOver("month",start_time,end_time);
                return JSON.toJSONString(turnOver);
            }
            case "3": {
                System.out.println("请输入某一年");
                String years = getYear();
                String []yearArr=years.split(",");
                String firstDay=yearArr[0];
                String lastDay=yearArr[1];
                TurnOver turnOver=new TurnOver("quarter",firstDay,lastDay);
                return JSON.toJSONString(turnOver);
            }
            case "4":{
                System.out.println("请你输入要查询的年份");
                String years = getYear();
                String []yearArr=years.split(",");
                String firstDay=yearArr[0];
                String lastDay=yearArr[1];
                TurnOver turnOver=new TurnOver("year",firstDay,lastDay);
                return JSON.toJSONString(turnOver);
            }
            case "5":{
                System.out.println("请你输入开始的时间(yyyy-MM-dd)");
                String start_time=sc.next();
                System.out.println("请你输入结束时间(yyyy-MM-dd)");
                String end_time=sc.next();
                TurnOver turnOver=new TurnOver("queryRandom",start_time,end_time);
                return JSON.toJSONString(turnOver);
            }
            case "0":
                return command();
            default:
                System.out.println("没有该操作");
                return turnoverView();
        }
    }

    private String getYear() {
        int year=sc.nextInt();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        // 获取年份的第一天
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        Date firstDayOfYear = calendar.getTime();

        // 获取年份的最后一天
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        Date lastDayOfYear = calendar.getTime();

        // 指定输出日期的格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String firstDay = dateFormat.format(firstDayOfYear);
        String lastDay = dateFormat.format(lastDayOfYear);
        return firstDay+","+lastDay;
    }

    //vip管理界面
    private String vipView() throws ParseException {
        System.out.println("请选择管理VIP功能\t\n1.添加VIP\t\n2.修改VIP\t\n3.注销VIP\t\n0.返回上一层");
        String select=sc.next();
        switch (select){
            case "1": {
                SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
                System.out.println("请输入vip的账号--名字--积分--电话号码");
                String number=sc.next();
                String name=sc.next();
                int score=sc.nextInt();
                String phone=sc.next();
                Date time=new Date();
                Vip vip=new Vip("addvip",name,number,"6666",score,phone,sf.format(time));
                return JSON.toJSONString(vip);
            }
            case "2":
                System.out.println("请你输入vip账号");
                String v_number=sc.next();
                Vip vip=new Vip("reset",v_number,"6666");
                return JSON.toJSONString(vip);
            case "3":
                System.out.println("请你输入vip账号");
                String v_number2=sc.next();
                Vip vip1=new Vip("logout",v_number2,1);
                return JSON.toJSONString(vip1);
            case "0":
                return command();
            default:
                System.out.println("没有该操作");
                return vipView();
        }
    }

    //员工考勤界面
    private String staffView() throws ParseException {
        System.out.println("请选择员工考勤功能\t\n1.指定日期考勤信息\t\n2.指定日期考勤异常情况\t\n3.员工补卡功能(上班卡，下班卡)\t\n4.添加工作日\t\n0.返回上一层");
        String select=sc.next();
        switch (select){
            case "1":
                String date = clockView();
                return date;
            case "2":
                return anomaly();
            case "3":
                return reissue();
            case "4":
                return increase();
            case "0":
                return command();
            default:
                System.out.println("没有该操作");
                return staffView();
        }
    }
//添加工作日
    private String increase() throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("请你输入要添加的工作日(yyyy-MM-dd)");
        String date=sc.next();
        Clock clock=new Clock("addClock",sf.parse(date));
        return JSON.toJSONString(clock);
    }

    //补卡功能
    private String reissue() throws ParseException {
        System.out.println("请你选择你要补卡的时间点\t\n1.上班补卡\t\n2.下班补卡\t\n3.返回上一层");
        String select=sc.next();
        switch (select){
            case "1": {
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
                System.out.println("请你输入要补办的员工工号");
                String number=sc.next();
                System.out.println("请你输入补办日期(yyyy-MM-dd)");
                String date=sc.next();
                String new_date=date+" "+"09:00:00";
                Clock clock = new Clock("update1",number,sf2.parse(date),sf.parse(new_date));
                return JSON.toJSONString(clock);
            }
            case "2": {
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
                System.out.println("请你输入要补办的员工工号");
                String number=sc.next();
                System.out.println("请你输入补办日期(yyyy-MM-dd)");
                String date=sc.next();
                //修改时间
                String new_date=date+" "+"18:00:00";
                Clock clock = new Clock("update2",number,sf2.parse(date),sf.parse(new_date));
                return JSON.toJSONString(clock);
            }
            case "3":
                return staffView();
            default:
                System.out.println("没有该操作！");
                return reissue();
        }
    }

    //考勤异常
    private String anomaly() throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("请你输入指定日期(yyyy-MM-dd)");
        String date=sc.next();
        Employee an = new Employee("anomaly", sf.parse(date),sf.parse(date));
        System.out.println(an.getStart_Date());
        return JSON.toJSONString(an);
    }

    //查询指定日期信息选择页面
    private String clockView() throws ParseException {
        System.out.println("请选择指定日期信息考勤功能\t\n1.迟到的人数\t\n2.旷工人数\t\n3.早退人数\t\n4.正常考勤人数\t\n0.返回上一层");
        String select=sc.next();
        switch (select){
            case "1": {
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
                System.out.println("请输入查询的开始日期(yyyy-MM-dd)");
                String time1=sc.next();
                String time2=time1+" "+"09:00:00";
                Employee lateNum = new Employee("late",sf.parse(time2),sf2.parse(time1));
                return JSON.toJSONString(lateNum);
            }
            case "2": {
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
                System.out.println("请输入查询的开始日期(yyyy-MM-dd)");
                String start=sc.next();
                String end=start+" "+"11:00:00";
                Employee absentNum = new Employee("absent",sf.parse(end),sf2.parse(start));
                return JSON.toJSONString(absentNum);
            }
            case "3": {
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println("请输入查询的开始日期(yyyy-MM-dd)");
                String st=sc.next();
                String start=st+" "+"16:00:00";
                String end=st+" "+"18:00:00";
                Employee leaveNum = new Employee("leave",sf.parse(start),sf.parse(end));
                return JSON.toJSONString(leaveNum);
            }
            case "4": {
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println("请输入查询的开始日期(yyyy-MM-dd)");
                String st=sc.next();
                String start=st+" "+"09:00:00";
                String end=st+" "+"18:00:00";
                Employee normalNum = new Employee("normal",sf.parse(start),sf.parse(end));
                return JSON.toJSONString(normalNum);
            }
            case "0":
                return staffView();
            default:
                System.out.println("没有该操作");
                return clockView();
        }
    }

    //采购员管理界面
    private String buyerView() throws ParseException {
        System.out.println("请选择管理采购员功能\t\n1.添加采购员\t\n2.修改采购员\t\n3.注销采购员\t\n0.返回上一层");
        String select=sc.next();
        switch (select){
            case "1":
                System.out.println("请输入采购员的信息(用户名--账号--手机号--密码--性别--职位(数字表示)--工资");
                return addEmployee();
            case "2":
                return updateEmployee();
            case "3":
                return deleteEmployee();
            case "0":
                return command();
            default:
                System.out.println("没有该操作");
                return buyerView();
        }
    }

    //收银员管理界面
    private String cashierView() throws ParseException {
        System.out.println("请选择管理收银员功能\t\n1.添加收银员\t\n2.修改收银员\t\n3.注销收银员\t\n0.返回上一层");
        String select=sc.next();
        switch (select) {
            case "1":
                System.out.println("请输入收银员的信息(用户名--账号--手机号--密码--性别--职位(数字表示)--工资");
                return addEmployee();
            case "2":
                return updateEmployee();
            case "3":
                return deleteEmployee();
            case "0":
                return command();
            default:
                System.out.println("没有该操作");
                return cashierView();
        }
    }
//注销用户
    private String deleteEmployee() {
        System.out.println("请输入要注销的用户账号");
        String number = sc.next();
        Employee employee = new Employee("delete", number);
        return JSON.toJSONString(employee);
    }

    //修改密码
    private String updateEmployee() {
        System.out.println("请输入你要修改的账号以及修改的密码");
        String number = sc.next();
        String password = sc.next();
        Employee employee = new Employee("update", number,password);
        return JSON.toJSONString(employee);
    }

    //管理员添加操作
    private String addEmployee() throws ParseException {
        String regx1 = "S\\d{3,4}";//账号正则
        String regx2 = "[a-zA-Z]{2,4}[0-9]{2,7}|[0-9][a-zA-Z]{3,7}|[a-zA-Z][0-9]{3,7}";
        String regx3 = "1[^012]\\d{9}";//手机正则

        String userName = sc.next();
        //0返回原界面
        if(userName.equals("0")){
            return command();
        }
        String number;
        while (true) {
            number = sc.next();
            if (number.matches(regx1)) {
                break;
            }
            System.out.println("账号不规范,请重新输入");
        }
        String phone;
        while (true) {
            phone = sc.next();
            if (phone.matches(regx3)) {
                break;
            }
            System.out.println("手机号不规范,请重新输入");
        }
        String password;
        while (true) {
            password = sc.next();
            if (password.matches(regx2)) {
                break;
            }
            System.out.println("密码不安全,请重新输入");
        }
        String sex;
        while(true){
            sex = sc.next();
            if (sex.equals("男")||sex.equals("女")) {
                break;
            }
            System.out.println("输入不正确，请重新输入");
        }
        int role=sc.nextInt();
        //注册时间
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        BigDecimal big=sc.nextBigDecimal();
        Employee employee = new Employee("add", number, password, userName, phone, sex,role,sf.format(date),big);
        return JSON.toJSONString(employee);
    }
}
