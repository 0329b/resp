package server;

import pojo.Cashier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CashierServiceImpl implements CashierService{
    //会员结算
    @Override
    public String settle(Cashier s, Connection conn) throws SQLException {
        Map<String, Integer> map = s.getProductQuantityMap();
        double sum = 0.0;

        for (String productName : map.keySet()) {
            int quantity = map.get(productName);
            String sql = "SELECT SUM(vip_price) * ? AS total FROM goods WHERE c_name = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, quantity);
            statement.setString(2, productName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                sum += resultSet.getDouble("total");
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
        int rowsAffected = scoreStatement.executeUpdate();

        return "结算金额：" + sum + "元";
    }
//非会员结算
    @Override
    public String settle2(Cashier s, Connection conn) throws SQLException {
        Map<String, Integer> map = s.getProductQuantityMap();
        int size = map.size();
        String[] goods = new String[size]; // 创建与集合大小相同的数组
        Set<String> productNames = s.getProductQuantityMap().keySet();
        Iterator<String> iterator = productNames.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            goods[i] = iterator.next(); // 存储元素到数组中
            i++;
        }
        int n = 0;
        double sum = 0.0;
        while (n < size) {
            String sql = "SELECT SUM(c_price) * ? AS total FROM goods WHERE c_name = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, s.getProductQuantityMap().get(goods[n]));
            statement.setString(2, goods[n]);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                sum += resultSet.getDouble("total");
            }
            n++;
        }
        int m = 0;
        // 库存变动
        while (m < size) {
            String sql = "UPDATE `test`.`goods` SET `inventory` = (`inventory` - ?) WHERE `c_name` = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, s.getProductQuantityMap().get(goods[m]));
            statement.setString(2, goods[m]);
            int rowsAffected = statement.executeUpdate();
            m++;
        }
        return "结算金额：" + sum + "元";
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
            return "会员积分：" + vScore + "元";
        } else {
            return "未找到该会员的积分信息";
        }
    }
//修改密码
    @Override
    public String update1(Cashier s, Connection conn) throws SQLException {
        String sql = "UPDATE employee SET password=? WHERE number=? AND password=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, s.getNew_password());
        statement.setString(2, s.getNumber());
        statement.setString(3, s.getPassword());
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            return "修改密码成功！";
        } else {
            return "修改失败，请检查你的输入是否正确";
        }
    }
//修改手机号
    @Override
    public String update2(Cashier s, Connection conn) throws SQLException {
        String sql="UPDATE employee SET phone=? WHERE number=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1,s.getPassword());
        statement.setString(2,s.getNumber());
        int rowsAffected = statement.executeUpdate();
        if(rowsAffected>0){
            return "修改手机号成功！";
        }else {
            return "修改失败，请检查你的账号是否正确";
        }
    }

}
