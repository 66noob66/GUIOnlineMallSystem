package OnlineMallSystem.ShoppingPackage.Gui;

import OnlineMallSystem.Frame;
import OnlineMallSystem.ShoppingPackage.Mysql.ShoppingHistoryMysql;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ShoppingHistory extends Frame {
    JFrame frame;
    String username;
    public ShoppingHistory(String username){
        this.username=username;
    }
    @Override
    public void show() {
        frame=new JFrame("购物历史");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        // 将窗口居中显示
        frame.setLocationRelativeTo(null);
        // 创建数据表
        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);

        ShoppingHistoryMysql.inquireHistoryData(model,username);

        // 创建滚动窗格并将表格添加到主界面窗口中
        JScrollPane scrollPane = new JScrollPane(table);
        frame.getContentPane().add(scrollPane);

        frame.setVisible(true);
    }

    @Override
    public void listener() {
    }
}
