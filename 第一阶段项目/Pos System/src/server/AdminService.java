package server;

import pojo.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 类名: server.AdminService
 * 功能描述:
 * 作者:没礼貌
 * 时间: 2023年08月19日
 * 版本: 1.0
 */
public interface AdminService {
    boolean adminLogin(Login loginKind, Connection conn) throws SQLException;

    boolean add(Employee employee, Connection conn) throws SQLException;

    boolean delete(Employee employee, Connection conn) throws SQLException;

    boolean update(Employee employee, Connection conn) throws SQLException;
//判断迟到人数
    String late(Employee employee, Connection conn) throws SQLException;

    String absent(Employee employee, Connection conn) throws SQLException;

    String leave(Employee employee, Connection conn) throws SQLException;

    String normal(Employee employee, Connection conn) throws SQLException;

    String anomaly(Employee employee, Connection conn) throws SQLException;

    boolean updateCard1(Clock clock, Connection conn) throws SQLException;

    boolean updateCard2(Clock clock, Connection conn) throws SQLException;

    boolean addDay(Clock clock, Connection conn) throws SQLException;

    boolean addVip(Vip vip, Connection conn) throws SQLException;

    boolean resetVip(Vip vip, Connection conn) throws SQLException;

    boolean logoutVip(Vip vip, Connection conn) throws SQLException;

    String queryDay(TurnOver tu, Connection conn) throws SQLException;

    String queryMonth(TurnOver tu, Connection conn) throws SQLException;

    String queryQuarter(TurnOver tu, Connection conn) throws SQLException;

    String queryYear(TurnOver tu, Connection conn) throws SQLException;

    String pointQuery(Employee employee, Connection conn) throws SQLException;

    String keyQuery(Employee employee, Connection conn) throws SQLException;

    String depart(Employee employee, Connection conn) throws SQLException;

    String role(Employee employee, Connection conn) throws SQLException;

    String top10(Connection conn) throws SQLException;

    String rand(Employee employee, Connection conn) throws SQLException;

    String top5(Connection conn) throws SQLException;

    boolean clock_in(Clock clock, Connection conn) throws SQLException;

    boolean clock_off(Clock clock, Connection conn) throws SQLException;
}
