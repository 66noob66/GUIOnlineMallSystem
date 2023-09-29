package OnlineMallSystem.ShoppingPackage.Mysql;

import java.sql.*;

public class ShoppingCartMysql {
    private Connection connection;

    public ShoppingCartMysql() {
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

    public boolean checkProductExists(String productNumber) {//查询商品是否存在
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT COUNT(*) FROM product WHERE number = ?");
            statement.setString(1, productNumber);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getAvailableQuantity(String productNumber) {//确认商品库存
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT quantity FROM product WHERE number = ?");
            statement.setString(1, productNumber);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int quantity = resultSet.getInt("quantity");
            return quantity;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double calculateTotalValue(String productNumber,int quantity) {//计算购物车中商品总价值
        double totalValue = 0.0;

        // 查询商品价格信息并计算总价值
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT retailprice FROM product WHERE number = ?");
            statement.setString(1, productNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                double price = resultSet.getDouble("retailprice");
                totalValue += price * quantity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalValue;
    }


}
