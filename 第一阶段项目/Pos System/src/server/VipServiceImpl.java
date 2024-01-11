package server;

import com.alibaba.fastjson.JSON;
import pojo.Login;
import pojo.Vip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class VipServiceImpl implements VipService{
    //查积分
    @Override
    public String integral(Vip vip, Connection conn) throws SQLException {
        String sql="SELECT v_score FROM vip WHERE v_number=?";
        PreparedStatement statement= conn.prepareStatement(sql);
        statement.setString(1,vip.getV_number());
        ResultSet resultSet = statement.executeQuery();
        int k=0;
        if(resultSet.next()){
           k=resultSet.getInt("v_score");
        }
        return "您的积分为："+k;
    }
//修改密码
    @Override
    public String update1(Vip vip, Connection conn) throws SQLException {
        String sql="UPDATE vip SET v_password=? WHERE v_number =? AND v_password=?";
        PreparedStatement statement= conn.prepareStatement(sql);
        statement.setString(1,vip.getV_password());
        statement.setString(2,vip.getV_name());
        statement.setString(3, vip.getV_number());
        int i = statement.executeUpdate();
        if(i>0){
            return "修改成功";
        }
        return "修改失败";
    }
    //修改手机号
    @Override
    public String update2(Vip vip, Connection conn) throws SQLException {
        String sql="UPDATE vip SET v_phone=? WHERE v_number =?";
        PreparedStatement statement= conn.prepareStatement(sql);
        statement.setString(1,vip.getV_number());
        statement.setString(2,vip.getV_password());
        int i = statement.executeUpdate();
        if(i>0){
            return "修改成功";
        }
        return "修改失败";
    }
//vip登录
    @Override
    public boolean Login(Login loginKind, Connection conn) throws SQLException {
        String query = "SELECT COUNT(*) as count FROM vip WHERE v_number = ? AND v_password = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, loginKind.getNumber());
        statement.setString(2, loginKind.getPassword());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("count") > 0;
        }
        return false;
    }
//查询可以兑换的商品
    @Override
    public String query(Vip vip, Connection conn) throws SQLException {
        String sql = "SELECT v_score FROM vip WHERE v_number=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, vip.getV_number());
        ResultSet resultSet = statement.executeQuery();

        int vScore = 0; // 用于存储积分的变量
        if (resultSet.next()) {
            vScore = resultSet.getInt("v_score");
        }
        double money=vScore/100;
        String sql1 = "SELECT c_name, c_price FROM goods WHERE c_price <= ?";
        PreparedStatement statement1 = conn.prepareStatement(sql1);
        statement1.setDouble(1, money);
        ResultSet resultSet1 = statement1.executeQuery();
        Map<String, Double> productQuantityMap = new HashMap<>();

        while (resultSet1.next()) {
            String productId = resultSet1.getString("c_name");
            double price = resultSet1.getDouble("c_price");
            productQuantityMap.put(productId, price);
        }
// 创建用于传输给客户端的Map集合
        Map<String, Object> response = new HashMap<>();
        response.put("availableAmount", money);
        response.put("products", productQuantityMap);

// 将response集合转换为JSON格式，然后发送给客户端
        String jsonResponse = JSON.toJSONString(response);
        return jsonResponse;
    }
//兑换商品
    @Override
    public String v_settle(Vip vip, Connection conn) throws SQLException {
        Map<String, Integer> productQuantityMap = vip.getProductQuantityMap();
        String vNumber = vip.getV_number();
        String productName = null;
        int quantity = 0;

        for (Map.Entry<String, Integer> entry : productQuantityMap.entrySet()) {
            productName = entry.getKey();     // 获取商品名
            quantity = entry.getValue();      // 获取数量

            String sql = "SELECT COUNT(*) FROM goods WHERE c_name = ? AND inventory >= ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, productName);
                statement.setInt(2, quantity);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    String sql1 = "UPDATE goods SET inventory = inventory - ? WHERE c_name = ?";
                    try (PreparedStatement statement1 = conn.prepareStatement(sql1)) {
                        statement1.setInt(1, quantity);
                        statement1.setString(2, productName);
                        statement1.executeUpdate();

                        String priceQuery = "SELECT c_price FROM goods WHERE c_name = ?";
                        try (PreparedStatement priceStatement = conn.prepareStatement(priceQuery)) {
                            priceStatement.setString(1, productName);
                            ResultSet priceResultSet = priceStatement.executeQuery();
                            if (priceResultSet.next()) {
                                int price = priceResultSet.getInt("c_price");
                                int requiredPoints = price * quantity;

                                String updatePointsQuery = "UPDATE vip SET v_score = v_score - ? WHERE v_number = ?";
                                try (PreparedStatement updatePointsStatement = conn.prepareStatement(updatePointsQuery)) {
                                    updatePointsStatement.setInt(1, requiredPoints);
                                    updatePointsStatement.setString(2, vNumber);
                                    updatePointsStatement.executeUpdate();
                                } catch (SQLException e) {
                                    // 处理数据库异常
                                    return "修改积分失败";
                                }
                            }
                        } catch (SQLException e) {
                            // 处理数据库异常
                            return "查询价格失败";
                        }
                    } catch (SQLException e) {
                        // 处理数据库异常
                        return "修改库存失败";
                    }
                } else {
                    return "库存不足";
                }
            } catch (SQLException e) {
                // 处理数据库异常
                return "查询失败";
            }
        }
        return "商品兑换成功！";
    }

}
