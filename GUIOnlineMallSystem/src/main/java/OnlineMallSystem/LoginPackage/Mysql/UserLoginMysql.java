package OnlineMallSystem.LoginPackage.Mysql;
import java.sql.*;

public class UserLoginMysql {
    private Connection connection;
    public UserLoginMysql() {
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

     public boolean validateLogin(String username, String password) {
         try {
             // 执行查询语句
             String sql = "SELECT * FROM user WHERE name = ? AND password = ?";
             PreparedStatement stmt = connection.prepareStatement(sql);
             stmt.setString(1, username);
             stmt.setString(2, password);
             ResultSet rs = stmt.executeQuery();

             boolean isValid = rs.next();

             return isValid;
         } catch (SQLException e) {
             e.printStackTrace();
             // 处理数据库连接异常
             return false;
         }
     }

     public int valiLoginAttempts(String username){//查询尝试登录次数
         try {
             PreparedStatement statement=connection.prepareStatement("SELECT login_Attempts FROM user where name = ?");
             statement.setString(1,username);
             ResultSet resultSet=statement.executeQuery();
             resultSet.next();
             int number=resultSet.getInt("login_Attempts");
             return number;
         }catch (SQLException e){
             e.printStackTrace();
         }
         return 0;
     }

     public void resetLoginAttempts(String username){//重置尝试登录次数
         try {
             PreparedStatement statement=connection.prepareStatement("UPDATE user SET login_attempts = ? WHERE name = ?");
             statement.setInt(1,0);
             statement.setString(2,username);
             statement.executeUpdate();
         }catch (SQLException e){
             e.printStackTrace();
         }
     }

     public void updateLoginAttempts(String username){//更新尝试登录次数
        try {
            PreparedStatement statement=connection.prepareStatement("UPDATE user SET login_attempts=login_attempts+1 WHERE name = ?");
            statement.setString(1,username);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
     }

 }
