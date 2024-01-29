package server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import pojo.Purchase;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseServiceIMpl implements PurchaseService{
//查询库存低于100的商品
    @Override
    public String queryGoods(Purchase purchase, Connection conn){
        try {
            String sql = "SELECT * FROM goods WHERE inventory<100";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Purchase> purchaseArrayList = new ArrayList<>();
            while (resultSet.next()){
                Purchase purchase1 = new Purchase();
                purchase1.setNumber(resultSet.getInt("c_number"));
                purchase1.setC_name(resultSet.getString("c_name"));
                purchase1.setC_price(resultSet.getBigDecimal("c_price"));
                purchase1.setVip_price(resultSet.getBigDecimal("c_price").multiply(new BigDecimal("0.8")));
                purchase1.setInventory(resultSet.getInt("inventory"));
                purchaseArrayList.add(purchase1);
            }
            String jsonString;
            jsonString = JSON.toJSONString(purchaseArrayList);
            JSONObject json = new JSONObject();
            json.put("data", jsonString);
            return JSON.toJSONString(json);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "查询失败";
    }
//已有商品添加
    @Override
    public String addGoods(Purchase purchase, Connection conn, String number) throws SQLException {
        String sql="UPDATE goods SET inventory =? WHERE c_number =?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1,purchase.getInventory());
        statement.setInt(2,purchase.getNumber());
        int i = statement.executeUpdate();
        if(i>0){
            return "添加成功";
        }
        return "添加失败";
    }
//添加商品
    @Override
    public String new_addGoods(Purchase purchase, Connection conn, String number) throws SQLException {
        String sql="INSERT INTO goods(c_name, c_price, vip_price, inventory) VALUES (?,?,?,?)";
        PreparedStatement statement=conn.prepareStatement(sql);
        statement.setString(1,purchase.getC_name());
        statement.setBigDecimal(2,purchase.getC_price());
        statement.setBigDecimal(3,purchase.getC_price().multiply(new BigDecimal("0.8")));
        statement.setInt(4,purchase.getInventory());
        int i = statement.executeUpdate();
        if(i>0){
            return "添加成功";
        }
        return "添加失败";
    }
//下架商品
    @Override
    public String deleteGoods(Purchase purchase, Connection conn, String number) throws SQLException {
        String sql="UPDATE goods SET inventory = 0 WHERE c_number =?";
        PreparedStatement statement=conn.prepareStatement(sql);
        statement.setInt(1,purchase.getNumber());
        int i = statement.executeUpdate();
        if(i>0){
            return "下架成功";
        }
        return "下架失败";
    }
}
