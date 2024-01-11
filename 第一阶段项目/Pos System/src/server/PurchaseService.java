package server;

import pojo.Purchase;

import java.sql.Connection;
import java.sql.SQLException;

public interface PurchaseService {
    String queryGoods(Purchase purchase, Connection conn) throws SQLException;

    String addGoods(Purchase purchase, Connection conn) throws SQLException;

    String new_addGoods(Purchase purchase, Connection conn) throws SQLException;

    String deleteGoods(Purchase purchase, Connection conn) throws SQLException;
}
