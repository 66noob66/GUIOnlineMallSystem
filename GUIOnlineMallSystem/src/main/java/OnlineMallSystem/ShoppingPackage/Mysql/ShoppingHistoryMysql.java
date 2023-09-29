package OnlineMallSystem.ShoppingPackage.Mysql;

import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ShoppingHistoryMysql {
    public static void inquireHistoryData(DefaultTableModel model,String username){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user",
                    "root", "Wzj.2003.2.27.");
            PreparedStatement statement=connection.prepareStatement("SELECT productname,purchasequantity,date FROM shoppinghistory where username=?");
            statement.setString(1,username);
            ResultSet resultSet = statement.executeQuery();

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

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
