package server;

import pojo.Cashier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class CashierServiceImpl implements CashierService{
    //会员结算
    @Override
    public String settle(Cashier s, Connection conn,String number){
        double sum;
        int s_c_number=0;
        int quantitysum=0;
        try {
            conn.setAutoCommit(false);
            Map<String, Integer> map = s.getProductQuantityMap();
            sum = 0.0;
            for (String productName : map.keySet()) {
                int quantity = map.get(productName);
                quantitysum+=quantity;
                String sql = "SELECT c_number,SUM(vip_price) * ? AS total FROM goods WHERE c_name = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, quantity);
                statement.setString(2, productName);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    sum += resultSet.getDouble("total");
                    s_c_number=resultSet.getInt("c_number");
                }

                String updateSql = "UPDATE `test`.`goods` SET `inventory` = (`inventory` - ?) WHERE `c_name` = ?";
                PreparedStatement updateStatement = conn.prepareStatement(updateSql);
                updateStatement.setInt(1, quantity);
                updateStatement.setString(2, productName);
                updateStatement.executeUpdate();
            }

            String updateScoreSql = "UPDATE `test`.`vip` SET `v_score` = (`v_score` + ?) WHERE `v_number` = ?";
            PreparedStatement scoreStatement = conn.prepareStatement(updateScoreSql);
            scoreStatement.setInt(1, (int) sum);
            scoreStatement.setString(2, s.getNumber());
            scoreStatement.executeUpdate();
            //插入销售记录
            Date date=new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time=sf.format(date);
            String insertSql="INSERT INTO sell_info(s_c_number, s_quantity, s_time, s_e_number, s_vip_number) VALUES (?,?,?,?,?)";
            PreparedStatement insertStatement = conn.prepareStatement(insertSql);
            insertStatement.setInt(1, s_c_number);
            insertStatement.setInt(2, quantitysum);
            insertStatement.setString(3,time);
            insertStatement.setString(4,number);
            insertStatement.setString(5,s.getNumber());
            insertStatement.executeUpdate();
            conn.commit(); // 提交事务
            return "结算金额：" + sum + "元";
        } catch (Exception e){
            try {
                conn.rollback();  // 出现异常时回滚事务
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return "操作失败";
    }
//非会员结算
    @Override
    public String settle2(Cashier s, Connection conn,String number) throws SQLException {
        double sum;
        int s_c_number=0;
        int quantitysum=0;

        try {
            conn.setAutoCommit(false);
            Map<String, Integer> map = s.getProductQuantityMap();
            sum = 0.0;
            for (String productName : map.keySet()) {
               int quantity = map.get(productName);
               quantitysum+=quantity;
                String sql = "SELECT c_number,SUM(vip_price) * ? AS total FROM goods WHERE c_name = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, quantity);
                statement.setString(2, productName);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    sum += resultSet.getDouble("total");
                    s_c_number=resultSet.getInt("c_number");
                }

                String updateSql = "UPDATE `test`.`goods` SET `inventory` = (`inventory` - ?) WHERE `c_name` = ?";
                PreparedStatement updateStatement = conn.prepareStatement(updateSql);
                updateStatement.setInt(1, quantity);
                updateStatement.setString(2, productName);
                updateStatement.executeUpdate();
            }
            //插入销售记录
            Date date=new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time=sf.format(date);
            String insertSql="INSERT INTO sell_info(s_c_number, s_quantity, s_time, s_e_number, s_vip_number) VALUES (?,?,?,?,?)";
            PreparedStatement insertStatement = conn.prepareStatement(insertSql);
            insertStatement.setInt(1, s_c_number);
            insertStatement.setInt(2, quantitysum);
            insertStatement.setString(3,time);
            insertStatement.setString(4,number);
            insertStatement.setString(5,null);
            insertStatement.executeUpdate();
            conn.commit(); // 提交事务
            return "结算金额：" + sum + "元";
        } catch (SQLException e) {
            try {
                conn.rollback();  // 出现异常时回滚事务
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        return "结算失败";
    }
//查询会员积分
    @Override
    public String queryVip(Cashier s, Connection conn) throws SQLException {
        String sql="SELECT v_score FROM vip WHERE v_number=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1,s.getNumber());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int vScore = resultSet.getInt("v_score");
            return "会员积分：" + vScore + "积分";
        } else {
            return "未找到该会员的积分信息";
        }
    }
//修改密码
    @Override
    public String update1(Cashier s, Connection conn, String number) throws SQLException {
        String sql = "UPDATE employee SET password=? WHERE number=? AND password=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, s.getPassword());
        statement.setString(2, number);
        statement.setString(3, s.getNumber());
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            return "修改密码成功！";
        } else {
            return "修改失败，请检查你的输入是否正确";
        }
    }
//修改手机号
    @Override
    public String update2(Cashier s, Connection conn, String number) throws SQLException {
        String sql="UPDATE employee SET phone=? WHERE number=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1,s.getNumber());
        statement.setString(2,number);
        int rowsAffected = statement.executeUpdate();
        if(rowsAffected>0){
            return "修改手机号成功！";
        }else {
            return "修改失败，请检查你的账号是否正确";
        }
    }

}
