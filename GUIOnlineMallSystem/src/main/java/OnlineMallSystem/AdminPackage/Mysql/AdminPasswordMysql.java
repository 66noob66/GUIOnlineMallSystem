package OnlineMallSystem.AdminPackage.Mysql;

import java.sql.*;

public class AdminPasswordMysql {
    private Connection connection;
    public AdminPasswordMysql() {
        establishConnection();
    }

    private void establishConnection() {
        try {
            // 使用MySQL数据库，加载并注册数据库驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 连接数据库
            String databaseUrl = "jdbc:mysql://localhost:3306/user";
            String username = "root";
            String password = "Wzj.2003.2.27.";
            connection = DriverManager.getConnection(databaseUrl, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean validateAdminPassword(String password){
        try {
            String sql = "SELECT * FROM admin WHERE name = ? AND password = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, "admin");
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public void changeAdminPassword(String currentPassword){
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE admin SET password=? WHERE name=?");
            statement.setString(1, currentPassword);
            statement.setString(2, "admin");
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
