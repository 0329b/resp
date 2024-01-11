package server;

import pojo.Cashier;

import java.sql.Connection;
import java.sql.SQLException;

public interface CashierService {
    String settle(Cashier s, Connection conn) throws SQLException;

    String settle2(Cashier s, Connection conn) throws SQLException;

    String queryVip(Cashier s, Connection conn) throws SQLException;

    String update1(Cashier s, Connection conn) throws SQLException;

    String update2(Cashier s, Connection conn) throws SQLException;
}
