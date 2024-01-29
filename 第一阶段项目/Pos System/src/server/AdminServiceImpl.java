package server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import pojo.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 类名: AdminServiceImpl
 * 功能描述:
 * 作者:没礼貌
 * 时间: 2023年08月19日
 * 版本: 1.0
 */
public class AdminServiceImpl implements AdminService {
    //登录模式(所有用户)
    @Override
    public boolean adminLogin(Login loginKind, Connection conn){
        //判断职位
        int judge=0;
        if(loginKind.getSortName().equals("管理员")){
            judge=1;
        } else if (loginKind.getSortName().equals("收银员")) {
            judge=2;
        } else if (loginKind.getSortName().equals("采购员")) {
            judge=3;
        }
        try {
            String query = "SELECT COUNT(*) as count FROM employee WHERE number = ? AND password = ? AND role=?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, loginKind.getNumber());
            statement.setString(2, loginKind.getPassword());
            statement.setInt(3, judge);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("count") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //添加员工
    @Override
    public boolean add(Employee employee, Connection conn){
        int rowsAffected = 0;
        try {
            String sql = "INSERT INTO employee (number, userName, password, sex, phone,salary,date,role, remark) VALUES (?, ?, ?, ?, ?, ?,?,?, 1)";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, employee.getNumber());
            statement.setString(2, employee.getUserName());
            statement.setString(3, employee.getPassword());
            statement.setString(4, employee.getSex());
            statement.setString(5, employee.getPhone());
            statement.setBigDecimal(6, employee.getSalary());
            statement.setString(7, employee.getDate());
            statement.setInt(8, employee.getRole());

            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected > 0;

    }

    //修改remark备注
    @Override
    public boolean delete(Employee employee, Connection conn){
        int rowsAffected = 0;
        try {
            String sql = "update employee set remark=0 where number=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, employee.getNumber());
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected > 0;
    }

    //修改员工
    @Override
    public boolean update(Employee employee, Connection conn){
        int rowsAffected = 0;
        try {
            String sql = "update employee set password=? where number=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, employee.getPassword());
            statement.setString(2, employee.getNumber());
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected > 0;
    }

    //查询迟到人数
    @Override
    public String late(Employee employee, Connection conn){
        int k = 0;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sql = "SELECT COUNT(*) as 迟到人数 FROM clock_info WHERE clock_in_time>=? AND clock_date=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, sf.format(employee.getStart_Date()));
            statement.setString(2, sf.format(employee.getEnd_Date()));
            ResultSet resultSet = statement.executeQuery();
            k = 0;
            if (resultSet.next()) {
                k = resultSet.getInt("迟到人数");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "迟到人数:" + k;
    }

    //旷工人数
    @Override
    public String absent(Employee employee, Connection conn){
        int k = 0;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sql = "SELECT COUNT(*) as 旷工人数 FROM clock_info WHERE clock_in_time>=? AND clock_date=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, sf.format(employee.getStart_Date()));
            statement.setString(2, sf.format(employee.getEnd_Date()));
            ResultSet resultSet = statement.executeQuery();
            k = 0;
            if (resultSet.next()) {
                k = resultSet.getInt("旷工人数");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "旷工人数:" + k;
    }

    //早退人数
    @Override
    public String leave(Employee employee, Connection conn){
        int k = 0;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sql = "SELECT COUNT(*) as 早退人数 FROM clock_info WHERE clock_in_time>=? AND clock_off_time<=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, sf.format(employee.getStart_Date()));
            statement.setString(2, sf.format(employee.getEnd_Date()));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                k = resultSet.getInt("早退人数");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "早退人数:" + k;
    }

    //考勤正常
    @Override
    public String normal(Employee employee, Connection conn){
        int k = 0;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sql = "SELECT COUNT(*) as 考勤正常人数 FROM clock_info WHERE clock_in_time>=? AND clock_off_time>=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, sf.format(employee.getStart_Date()));
            statement.setString(2, sf.format(employee.getEnd_Date()));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                k = resultSet.getInt("考勤正常人数");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "考勤正常人数:" + k;
    }

    //考勤异常
    @Override
    public String anomaly(Employee employee, Connection conn) throws SQLException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String sql="  SELECT *,\n" +
                "                CASE\n" +
                "        WHEN clock_in_time IS NULL or clock_off_time IS NULL THEN '未打卡'\n" +
                "        WHEN clock_in_time >? THEN '迟到'\n" +
                "        WHEN clock_off_time <? THEN '早退'\n" +
                "        ELSE '正常'\n" +
                "        END AS attendance_status\n" +
                "        FROM clock_info\n" +
                "        WHERE (clock_date = ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        //需要放string类型进去
        statement.setString(1,sf.format(employee.getStart_Date())+" "+"09:00:00");
        statement.setString(2,sf.format(employee.getStart_Date())+" "+"18:00:00");
        statement.setString(3,sf.format(employee.getEnd_Date()));
        ResultSet resultSet = statement.executeQuery();
        List<Clock> clockInfoList = new ArrayList<>();
        while (resultSet.next()) {
            Clock clockInfo = new Clock();
            clockInfo.setClock_id(resultSet.getInt("clock_id"));
            clockInfo.setEmployee_no(resultSet.getNString("employee_no"));
            clockInfo.setClock_in_time(resultSet.getTimestamp("clock_in_time"));
            clockInfo.setClock_off_time(resultSet.getTimestamp("clock_off_time"));
            clockInfo.setClock_date(resultSet.getDate("clock_date"));
            clockInfo.setRemark(resultSet.getString("attendance_status"));
            clockInfoList.add(clockInfo);
        }
        String jsonString;
        String isList; // 标记是否为集合类型
        if (!clockInfoList.isEmpty()) {
            isList = "clock";
            jsonString = JSON.toJSONString(clockInfoList);
        } else {
            isList = "false";
            jsonString = "没有异常信息"; // 非集合类型的数据
        }
        JSONObject json = new JSONObject();
        json.put("data", jsonString);
        json.put("isList", isList);
        return JSON.toJSONString(json);
    }

    //修改补卡功能
    @Override
    public boolean updateCard1(Clock clock, Connection conn){
        int rowsAffected=0;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sql = "UPDATE clock_info SET  clock_in_time = ? WHERE employee_no =? AND clock_date =?";
            PreparedStatement statement = conn.prepareStatement(sql);
            //需要放string类型进去
            statement.setString(1, sf.format(clock.getUpdateTime()));
            statement.setString(2, clock.getEmployee_no());
            statement.setString(3, sf.format(clock.getClock_date()));
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected > 0;
    }
//补卡功能
    @Override
    public boolean updateCard2(Clock clock, Connection conn){
        int rowsAffected = 0;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sql = "UPDATE clock_info SET  clock_off_time = ? WHERE employee_no =? AND clock_date =?";
            PreparedStatement statement = conn.prepareStatement(sql);
            //需要放string类型进去
            statement.setString(1, sf.format(clock.getUpdateTime()));
            statement.setString(2, clock.getEmployee_no());
            statement.setString(3, sf.format(clock.getClock_date()));
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected > 0;
    }

    //添加工作日
    @Override
    public boolean addDay(Clock clock, Connection conn){
        PreparedStatement statement = null;
        int rowsAffected=0;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            String sql = "INSERT INTO work_date(work_date) VALUES (?)";
            statement = conn.prepareStatement(sql);
            //需要放string类型进去
            statement.setString(1, sf.format(clock.getWork_date()));
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected > 0;
    }

    //添加会员
    @Override
    public boolean addVip(Vip vip, Connection conn){
        PreparedStatement statement = null;
        int rowsAffected=0;
        try {
            String sql = "INSERT INTO vip VALUES (?,?,?,?,?,?,?);";
            statement = conn.prepareStatement(sql);
            //需要放string类型进去
            statement.setString(1, vip.getV_number());
            statement.setString(2, vip.getV_password());
            statement.setString(3, vip.getV_name());
            statement.setInt(4, vip.getV_score());
            statement.setString(5, vip.getV_phone());
            statement.setString(6, vip.getV_date());
            statement.setInt(7, 1);
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected > 0;
    }

    //重置密码
    @Override
    public boolean resetVip(Vip vip, Connection conn){
        int rowsAffected = 0;
        try {
            String sql = "UPDATE vip SET v_password= ? WHERE v_number =?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, "6666");
            statement.setString(2, vip.getV_number());
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected > 0;
    }

    //注销vip
    @Override
    public boolean logoutVip(Vip vip, Connection conn){
        int rowsAffected = 0;
        try {
            String sql = "UPDATE vip SET v_remark= ? WHERE v_number =?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, 0);
            statement.setString(2, vip.getV_number());
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected > 0;
    }

    //查询日营业额
    @Override
    public String queryDay(TurnOver tu, Connection conn){
        try {
            String sql = "SELECT  SUM(s_quantity * c_price )as 营业额 FROM goods JOIN sell_info on s_c_number=c_number\n" +
                    "WHERE s_time>=? AND s_time<?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, tu.getStart_time() + " " + "00:00:00");
            statement.setString(2, tu.getStart_time() + " " + "23:59:00");
            ResultSet resultSet = statement.executeQuery();
            double k = 0;
            if (resultSet.next()) {
                k = resultSet.getDouble("营业额");
            }
            return tu.getStart_time() + "到" + tu.getEnd_time() + "的日营业额:" + k + "元";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "查询失败";
    }

    //月营业额
    @Override
    public String queryMonth(TurnOver tu, Connection conn){
        try {
            String sql = "SELECT  SUM(s_quantity * c_price )as 营业额 FROM goods JOIN sell_info on s_c_number=c_number\n" +
                    "WHERE s_time>=? AND s_time<=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, tu.getStart_time() + " " + "00:00:00");
            statement.setString(2, tu.getStart_time() + " " + "23:59:00");
            ResultSet resultSet = statement.executeQuery();
            double k = 0;
            if (resultSet.next()) {
                k = resultSet.getDouble("营业额");
                return tu.getStart_time() + "到" + tu.getEnd_time() + "的月营业额:" + k + "元";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "没有查到";
    }

    //季度和任意时间段营业额
    @Override
    public String queryRandom(TurnOver tu, Connection conn){
        try {
            String sql = "SELECT  SUM(s_quantity * c_price )as 营业额 FROM goods JOIN sell_info on s_c_number=c_number\n" +
                    "WHERE s_time>=? AND s_time<=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, tu.getStart_time() + " " + "00:00:00");
            statement.setString(2, tu.getStart_time() + " " + "23:59:00");
            ResultSet resultSet = statement.executeQuery();
            double k = 0.0;
            if (resultSet.next()) {
                k = resultSet.getDouble("营业额");
                return tu.getStart_time() + "到" + tu.getEnd_time() + "的营业额:" + k + "元";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "没有查到";
    }
//季度营业额

    @Override
    public String quarter(TurnOver tu, Connection conn) {
        // 定义一个 Map 用于存储每个季度的营业额
        Map<String, Double> quarterlyRevenueMap = new HashMap<>();
        try {
            // 查询销售记录的日期、数量和价格
            String querySql = "SELECT si.s_time, si.s_quantity, g.c_price FROM sell_info si JOIN goods g ON si.s_c_number = g.c_number WHERE s_time>=? AND s_time<=? ";
            PreparedStatement statement = conn.prepareStatement(querySql);
            statement.setString(1, tu.getStart_time() + " " + "00:00:00");
            statement.setString(2, tu.getEnd_time() + " " + "23:59:00");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String salesDate = resultSet.getString("s_time");
                int quantity = resultSet.getInt("s_quantity");
                double price = resultSet.getDouble("c_price");
                // 根据销售记录的日期，计算出对应的季度
                String quarter = calculateQuarter(salesDate);

                // 计算销售额
                double amount = quantity * price;

                // 将销售金额归类到对应的季度
                if (quarterlyRevenueMap.containsKey(quarter)) {
                    quarterlyRevenueMap.put(quarter, quarterlyRevenueMap.get(quarter) + amount);
                } else {
                    quarterlyRevenueMap.put(quarter, amount);
                }
            }

            JSONObject json = new JSONObject();
            String jsonString;
            String isList; // 标记是否为集合类型
            if (!quarterlyRevenueMap.isEmpty()) {
                isList ="map";
                jsonString = JSON.toJSONString(quarterlyRevenueMap);
            } else {
                isList = "un";
                jsonString = "没有查到"; // 非集合类型的数据
            }
            json.put("data", jsonString);
            json.put("isList", isList);
            return JSON.toJSONString(json);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "查询失败";
    }
//判断那个季度
    private String calculateQuarter(String salesDate) {
        String []data=salesDate.split(" ")[0].split("-");
        int month=Integer.parseInt(data[1]);
        String quarter;
        if(month>=1&&month<=3){
            quarter="第一季度";
        }else if(month>=4&&month<=6){
            quarter="第二季度";
        }else if(month>=7&&month<=9){
                quarter="第三季度";
        }else {
            quarter="第四季度";
        }
        return quarter;
    }

    //年营业额
    @Override
    public String queryYear(TurnOver tu, Connection conn){
        try {
            String sql = "SELECT  SUM(s_quantity * c_price )as 营业额 FROM goods JOIN sell_info on s_c_number=c_number\n" +
                    "WHERE s_time>=? AND s_time<=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, tu.getStart_time() + " " + "00:00:00");
            statement.setString(2, tu.getEnd_time() + " " + "23:59:00");
            ResultSet resultSet = statement.executeQuery();
            double k = 0.0;
            if (resultSet.next()) {
                k = resultSet.getDouble("营业额");
                return tu.getStart_time() + "到" + tu.getEnd_time() + "的年营业额:" + k + "元";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "没有查到";
    }

    //查询指定员工
    @Override
    public String pointQuery(Employee employee, Connection conn){
        try {
            String sql = "SELECT number,username,`password`,sex,phone,r_name,remark,salary,date FROM employee RIGHT JOIN role on role=id\n" +
                    "WHERE  number=? OR phone=? OR username=?;";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, employee.getNumber());
            statement.setString(2, employee.getNumber());
            statement.setString(3, employee.getNumber());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String number = rs.getString("number");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String sex = rs.getString("sex");
                String phone = rs.getString("phone");
                String rName = rs.getString("r_name");
    //            String remark = rs.getString("remark");
                BigDecimal salary = rs.getBigDecimal("salary");
                Date date = rs.getDate("date");
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = sf.format(date);
                Employee employee1 = new Employee(number, password, username, phone, sex, rName, time, salary);
                ArrayList<Employee> list = new ArrayList<>();
                list.add(employee1);
                JSONObject json = new JSONObject();
                String jsonString;
                String isList; // 标记是否为集合类型
                if (!list.isEmpty()) {
                    isList ="em";
                    jsonString = JSON.toJSONString(list);
                } else {
                    isList = "un";
                    jsonString = "Not a list"; // 非集合类型的数据
                }
                json.put("data", jsonString);
                json.put("isList", isList);
                return JSON.toJSONString(json);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "没有该员工";
    }
//模糊查询
    @Override
    public String keyQuery(Employee employee, Connection conn){
        JSONObject json = null;
        try {
            String keyword = "%" + employee.getNumber() + "%";
            String sql = "SELECT number, username, `password`, sex, phone, r_name, remark, salary, date " +
                    "FROM employee RIGHT JOIN role ON role = id " +
                    "WHERE CONCAT(number, username, password, sex, phone, r_name, remark, salary, date) LIKE ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, keyword);
            ResultSet rs = statement.executeQuery();
            ArrayList<Employee> list = new ArrayList<>();
            while (rs.next()) {
                String number = rs.getString("number");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String sex = rs.getString("sex");
                String phone = rs.getString("phone");
                String rName = rs.getString("r_name");
    //            String remark = rs.getString("remark");
                BigDecimal salary = rs.getBigDecimal("salary");
                Date date = rs.getDate("date");
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = sf.format(date);
                Employee employee1 = new Employee(number, password, username, phone, sex, rName, time, salary);
                list.add(employee1);
            }
            json = new JSONObject();
            String jsonString;
            String isList; // 标记是否为集合类型
            if (!list.isEmpty()) {
                isList ="em";
                jsonString = JSON.toJSONString(list);
            } else {
                isList = "un";
                jsonString = "没有查到数据"; // 非集合类型的数据
            }
            json.put("isList", isList);
            json.put("data", jsonString);
            return JSON.toJSONString(json);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "没有查到";
    }
//查询离职
    @Override
    public String depart(Employee employee, Connection conn){
        JSONObject json = null;
        try {
            String sql = "SELECT number, username, password, sex, phone, r_name, salary, date " +
                    "FROM employee RIGHT JOIN role ON role = id " +
                    "WHERE remark = 0";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            ArrayList<Employee> list = new ArrayList<>();
            while (rs.next()) {
                String number = rs.getString("number");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String sex = rs.getString("sex");
                String phone = rs.getString("phone");
                String rName = rs.getString("r_name");
    //            String remark = rs.getString("remark");
                BigDecimal salary = rs.getBigDecimal("salary");
                Date date = rs.getDate("date");
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = sf.format(date);
                Employee employee1 = new Employee(number, password, username, phone, sex, rName, time, salary);
                list.add(employee1);
            }
            json = new JSONObject();
            String jsonString;
            String isList; // 标记是否为集合类型
            if (!list.isEmpty()) {
                isList ="em";
                jsonString = JSON.toJSONString(list);
            } else {
                isList = "un";
                jsonString = "Not a list"; // 非集合类型的数据
            }
            json.put("data", jsonString);
            json.put("isList", isList);
            return JSON.toJSONString(json);
        } catch (SQLException e) {
            e.printStackTrace();
        }
       return "输入有误";
    }
//职位查询
    @Override
    public String role(Employee employee, Connection conn){
        try {
            String keyWord=employee.getNumber();
            String sql = "SELECT number, username, `password`, sex, phone, r_name, salary, date " +
                    "FROM employee RIGHT JOIN role ON role = id " +
                    "WHERE role = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, keyWord);
            ResultSet rs = statement.executeQuery();
            ArrayList<Employee> list = new ArrayList<>();
            while (rs.next()) {
                String number = rs.getString("number");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String sex = rs.getString("sex");
                String phone = rs.getString("phone");
                String rName = rs.getString("r_name");
    //            String remark = rs.getString("remark");
                BigDecimal salary = rs.getBigDecimal("salary");
                Date date = rs.getDate("date");
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = sf.format(date);
                Employee employee1 = new Employee(number, password, username, phone, sex, rName, time, salary);
                list.add(employee1);
            }
            JSONObject json = new JSONObject();
            String jsonString;
            String isList; // 标记是否为集合类型
            if (!list.isEmpty()) {
                isList ="em";
                jsonString = JSON.toJSONString(list);
            } else {
                isList = "un";
                jsonString = "Not a list"; // 非集合类型的数据
            }
            json.put("data", jsonString);
            json.put("isList", isList);
            return JSON.toJSONString(json);
        } catch (SQLException e) {
            e.printStackTrace();
        }
       return "输入有误";
    }
//前10工资
    @Override
    public String top10(Connection conn){
        JSONObject json = null;
        try {
            String sql = "SELECT number, username, `password`, sex, phone, r_name, salary, date " +
                    "FROM employee RIGHT JOIN role ON role = id " +
                    "ORDER BY salary DESC LIMIT 10";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            ArrayList<Employee> list = new ArrayList<>();
            while (rs.next()) {
                String number = rs.getString("number");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String sex = rs.getString("sex");
                String phone = rs.getString("phone");
                String rName = rs.getString("r_name");
    //            String remark = rs.getString("remark");
                BigDecimal salary = rs.getBigDecimal("salary");
                Date date = rs.getDate("date");
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = sf.format(date);
                Employee employee1 = new Employee(number, password, username, phone, sex, rName, time, salary);
                list.add(employee1);
            }
            json = new JSONObject();
            String jsonString;
            String isList; // 标记是否为集合类型
            if (!list.isEmpty()) {
                isList ="em";
                jsonString = JSON.toJSONString(list);
            } else {
                isList = "un";
                jsonString = "Not a list"; // 非集合类型的数据
            }
            json.put("data", jsonString);
            json.put("isList", isList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(json);
    }
//范围查询工资
    @Override
    public String rand(Employee employee, Connection conn){
        try {
            String sql = "SELECT number, username, `password`, sex, phone, r_name, salary, date " +
                    "FROM employee RIGHT JOIN role ON role = id " +
                    "WHERE salary >= ? AND salary <= ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, employee.getNumber());
            statement.setString(2,employee.getPassword());
            ResultSet rs = statement.executeQuery();
            ArrayList<Employee> list = new ArrayList<>();
            while (rs.next()) {
                String number = rs.getString("number");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String sex = rs.getString("sex");
                String phone = rs.getString("phone");
                String rName = rs.getString("r_name");
    //            String remark = rs.getString("remark");
                BigDecimal salary = rs.getBigDecimal("salary");
                Date date = rs.getDate("date");
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = sf.format(date);
                Employee employee1 = new Employee(number, password, username, phone, sex, rName, time, salary);
                list.add(employee1);
            }
            JSONObject json = new JSONObject();
            String jsonString;
            String isList; // 标记是否为集合类型
            if (!list.isEmpty()) {
                isList ="em";
                jsonString = JSON.toJSONString(list);
            } else {
                isList = "un";
                jsonString = "Not a list"; // 非集合类型的数据
            }
            json.put("data", jsonString);
            json.put("isList", isList);
            return JSON.toJSONString(json);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "查询失败";
    }
//最低工资
    @Override
    public String top5(Connection conn){
        try {
            String sql = "SELECT number, username, `password`, sex, phone, r_name, salary, date " +
                    "FROM employee RIGHT JOIN role ON role = id " +
                    "ORDER BY salary Asc LIMIT 5";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            ArrayList<Employee> list = new ArrayList<>();
            while (rs.next()) {
                String number = rs.getString("number");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String sex = rs.getString("sex");
                String phone = rs.getString("phone");
                String rName = rs.getString("r_name");
    //            String remark = rs.getString("remark");
                BigDecimal salary = rs.getBigDecimal("salary");
                Date date = rs.getDate("date");
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = sf.format(date);
                Employee employee1 = new Employee(number, password, username, phone, sex, rName, time, salary);
                list.add(employee1);
            }
            JSONObject json = new JSONObject();
            String jsonString;
            String isList; // 标记是否为集合类型
            if (!list.isEmpty()) {
                isList ="em";
                jsonString = JSON.toJSONString(list);
            } else {
                isList = "un";
                jsonString = "Not a list"; // 非集合类型的数据
            }
            json.put("data", jsonString);
            json.put("isList", isList);
            return JSON.toJSONString(json);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "查询失败";
    }
//打上班卡
    @Override
    public boolean clock_in(Clock clock, Connection conn, String number) throws SQLException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "INSERT INTO `clock_info`(`employee_no`, `clock_in_time`, clock_off_time,`clock_date`) VALUES (?, ?,null,?);";
        PreparedStatement statement = conn.prepareStatement(sql);
        //需要放string类型进去
        statement.setString(1,number);
        statement.setString(2, sf.format(clock.getClock_date()));
        statement.setString(3, sf.format(clock.getUpdateTime()));
        int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0;
    }
//打下班卡
    @Override
    public boolean clock_off(Clock clock, Connection conn, String number) throws SQLException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "UPDATE `clock_info` SET `clock_off_time` = ? WHERE employee_no=? AND clock_date=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        //需要放string类型进去
        statement.setString(1, sf.format(clock.getClock_date()));
        statement.setString(2,number);
        statement.setString(3, sf.format(clock.getUpdateTime()));
        int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0;
    }

}