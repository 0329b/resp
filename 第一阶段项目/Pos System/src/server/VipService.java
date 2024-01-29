package server;

import pojo.Login;
import pojo.Vip;

import java.sql.Connection;
import java.sql.SQLException;

public interface VipService {
    String integral(Vip vip, Connection conn, String number) throws SQLException;

    String update1(Vip vip, Connection conn, String number) throws SQLException;

    String update2(Vip vip, Connection conn, String number) throws SQLException;

    boolean Login(Login loginKind, Connection conn) throws SQLException;

    String query(Vip vip, Connection conn, String number) throws SQLException;

    String v_settle(Vip vip, Connection conn, String number) throws SQLException;
}
