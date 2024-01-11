import thread.ServerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 类名: ServerTest
 * 功能描述:
 * 作者:没礼貌
 * 时间: 2023年08月19日
 * 版本: 1.0
 */
public class ServerTest {
    public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/test";
            ExecutorService pool = Executors.newFixedThreadPool(5);
            ServerSocket ss = new ServerSocket(30029);
            while (true) {
                Connection conn = DriverManager.getConnection(url, "root", "aA010608");
                Socket socket = ss.accept();
                ServerThread st = new ServerThread(socket, conn);
                pool.submit(st);
            }
    }
}
