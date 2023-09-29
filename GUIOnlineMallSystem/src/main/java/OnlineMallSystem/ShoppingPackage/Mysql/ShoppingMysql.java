package OnlineMallSystem.ShoppingPackage.Mysql;

import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ShoppingMysql {
    public static void inquireProductionData(DefaultTableModel model){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user",
                    "root", "Wzj.2003.2.27.");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT number, name, retailprice, date, manufacturer FROM product");

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
}
