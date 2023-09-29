package OnlineMallSystem.ShoppingPackage.Mysql;
import java.sql.*;
import java.util.Date;
public class PayMysql {
    private Connection connection;
    public PayMysql() {
        establishConnection();
    }

    private void establishConnection() {
        try {
            //使用MySQL数据库，加载并注册数据库驱动程序
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

    public void updateProductQuantity(int quantity,String productNumber) {
            // 更新数据库中商品数量
            try {
                PreparedStatement statement = connection.prepareStatement("UPDATE product SET quantity = quantity - ? WHERE number = ?");
                statement.setInt(1, quantity);
                statement.setString(2, productNumber);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public void updateConsumption(double consumption,String username){
        // 更新数据库中消费金额
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE user SET consumption = consumption + ? WHERE name = ?");
            statement.setDouble(1, consumption);
            statement.setString(2, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getConsumption(String username){
        // 得到数据库中消费金额
        double consumption=0.0;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT consumption FROM user WHERE name = ?");
            statement.setString(1, username);
            ResultSet resultSet=statement.executeQuery();
            if(resultSet.next()) {
                consumption=resultSet.getDouble("consumption");
            }
            return consumption;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void changeLevel(String username,String level){
        // 更新数据库中用户等级
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE user SET level = ? WHERE name = ?");
            statement.setString(1,level);
            statement.setString(2, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getProductName(String productNumber){
        String productName=null;
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT name FROM product where number = ?");
            statement.setString(1,productNumber);
            ResultSet resultSet= statement.executeQuery();
            if(resultSet.next()){
                productName=resultSet.getString("name");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return productName;
    }

    public void updateShoppingHistory(int quantity,String username,String productName){
           //更新数据库中购物历史
           try{
               // 获取当前时间并转换为Timestamp对象
               Date currentDate = new Date();
               Timestamp timestamp = new Timestamp(currentDate.getTime());
               PreparedStatement statement=connection.prepareStatement("INSERT INTO shoppinghistory(username, productname, purchasequantity,date) VALUES (?,?,?,?)");
               statement.setString(1,username);
               statement.setString(2,productName);
               statement.setInt(3,quantity);
               statement.setTimestamp(4,timestamp);
               // 执行插入操作
               statement.executeUpdate();
           }catch (SQLException e){
               e.printStackTrace();
           }
    }
}
