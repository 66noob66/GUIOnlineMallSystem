package OnlineMallSystem.LoginPackage.Mysql;

import java.sql.*;

public class AdminLoginMysql {

    public static boolean validateLogin(String username, String password) {
        String url = "jdbc:mysql://localhost:3306/user";
        String dbUsername = "root";
        String dbPassword = "Wzj.2003.2.27.";
        try {
            // 加载数据库驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 建立数据库连接
            Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);

            // 执行查询语句
            String sql = "SELECT * FROM admin WHERE name = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            boolean isValid = rs.next();

            // 关闭数据库连接
            rs.close();
            stmt.close();
            conn.close();

            return isValid;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // 处理数据库连接异常
            return false;
        }
    }
}

