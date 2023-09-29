package OnlineMallSystem.LoginPackage.Mysql;
import java.sql.*;
import java.util.Date;

public class UserRegisterMysql {
    private Connection connection;
    public UserRegisterMysql() {establishConnection();}

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

    public boolean valiUsername(String username) {//验证是否为已注册的用户名
        try {
            // 执行查询语句
            String sql = "SELECT * FROM user WHERE name = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            boolean isValid = rs.next();
            return isValid;
        } catch (SQLException e) {
            e.printStackTrace();
            // 处理数据库连接异常
            return false;
        }
    }

    public boolean valiEmailMysql(String email) {//验证是否为已注册的邮箱
        try {
            // 执行查询语句
            String sql = "SELECT * FROM user WHERE email = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            boolean isValid = rs.next();

            return isValid;
        } catch (SQLException e) {
            e.printStackTrace();
            // 处理数据库连接异常
            return false;
        }
    }

    public void insertUserData(String username, String password, String email, String phoneNumber) {
        PreparedStatement statement ;
        try {
            // 获取当前时间并转换为Timestamp对象
            java.util.Date currentDate = new Date();
            Timestamp timestamp = new Timestamp(currentDate.getTime());
            String query = "INSERT INTO user (name, password, email, registerdate, phonenumber) VALUES (?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);

            // 设置SQL语句中的参数
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, email);
            statement.setTimestamp(4,timestamp);
            statement.setString(5,phoneNumber);
            // 执行插入操作
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
