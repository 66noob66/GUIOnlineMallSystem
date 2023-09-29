package OnlineMallSystem.AdminPackage.Mysql;

import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ProductMangeMysql {

    private Connection connection;

    public ProductMangeMysql() {
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

    public void inquireProductionData(DefaultTableModel model){//获取商品信息
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT number, name, manufacturer, date,  model, retailprice, restockingprice, quantity FROM product");

            // 获取列数
            int columnCount = resultSet.getMetaData().getColumnCount();

            // 设置表头
            for (int i = 1; i <= columnCount; i++) {
                String columnName = resultSet.getMetaData().getColumnName(i);
                model.addColumn(columnName);
            }

            // 设置表格内容
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = resultSet.getObject(i + 1);
                }
                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateProduct(DefaultTableModel model,int selectedRow,String oldNumber){//修改商品信息
        try {
            String number=model.getValueAt(selectedRow,0).toString();
            String name=model.getValueAt(selectedRow,1).toString();
            String manufacturer=model.getValueAt(selectedRow,2).toString();
            String dateString=model.getValueAt(selectedRow,3).toString();
            String modelString=model.getValueAt(selectedRow,4).toString();
            Double retailPrice=(Double) model.getValueAt(selectedRow,5);
            Double restockingPrice=(Double) model.getValueAt(selectedRow,6);
            int quantity=(Integer) model.getValueAt(selectedRow,7);
            PreparedStatement statement=connection.prepareStatement("UPDATE product SET number=?, name=?, manufacturer=?, " +
                    "date=?, model=?, retailprice=?, restockingprice=?, quantity=? WHERE number=?");
            statement.setString(1,number);
            statement.setString(2,name);
            statement.setString(3,manufacturer);
            statement.setString(4,dateString);
            statement.setString(5,modelString);
            statement.setDouble(6,retailPrice);
            statement.setDouble(7,restockingPrice);
            statement.setInt(8,quantity);
            statement.setString(9,oldNumber);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void addProudct(DefaultTableModel model,int newRow){//添加商品
        try {
            String number=model.getValueAt(newRow,0).toString();
            String name=model.getValueAt(newRow,1).toString();
            String manufacturer=model.getValueAt(newRow,2).toString();
            String dateString=model.getValueAt(newRow,3).toString();
            String modelString=model.getValueAt(newRow,4).toString();
            Double retailPrice=(Double) model.getValueAt(newRow,5);
            Double restockingPrice=(Double) model.getValueAt(newRow,6);
            int quantity=(Integer) model.getValueAt(newRow,7);
            PreparedStatement statement=connection.prepareStatement("INSERT INTO product(number, name, manufacturer, " +
                    "date, model, restockingprice, retailprice, quantity) VALUES (?,?,?,?,?,?,?,?)");
            statement.setString(1,number);
            statement.setString(2,name);
            statement.setString(3,manufacturer);
            statement.setString(4,dateString);
            statement.setString(5,modelString);
            statement.setDouble(6,retailPrice);
            statement.setDouble(7,restockingPrice);
            statement.setInt(8,quantity);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteProduct(String number){//删除商品
        try {
            PreparedStatement statement=connection.prepareStatement("DELETE FROM product WHERE number=?");
            statement.setString(1,number);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean validateNumber(String number){
        try{
            PreparedStatement statement=connection.prepareStatement("SELECT * FROM product WHERE number=?");
            statement.setString(1,number);
            ResultSet resultSet=statement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        }catch (SQLException e){
           e.printStackTrace();
        }
        return false;
    }
}
