package OnlineMallSystem.ShoppingPackage.Gui;

import OnlineMallSystem.Frame;
import OnlineMallSystem.ShoppingPackage.Mysql.ShoppingMysql;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
public class Shopping extends Frame {
    // 创建主界面窗口
    JFrame frame = new JFrame("购物界面");
    // 创建购物车按钮
    JButton cartButton = new JButton("购物车");
    // 创建个人信息按钮
    JButton selfInformationButton = new JButton("个人信息");
    String username;
    public Shopping(String username){
        this.username=username;
    }
    @Override
    public void show(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        // 将窗口居中显示
        frame.setLocationRelativeTo(null);
        // 创建数据表
        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);

        ShoppingMysql.inquireProductionData(model);

        // 创建按钮布局并将购物车按钮添加到主界面窗口中
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(cartButton);
        buttonPanel.add(selfInformationButton);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // 创建滚动窗格并将表格添加到主界面窗口中
        JScrollPane scrollPane = new JScrollPane(table);
        frame.getContentPane().add(scrollPane);
        //添加按钮侦听
        listener();

        frame.setVisible(true);
    }

    @Override
    public void listener() {
        cartButton.addActionListener(e -> {
            ShoppingCart shoppingCartGUI=new ShoppingCart(username);
            shoppingCartGUI.show();
        });

        selfInformationButton.addActionListener(e -> {
            SelfInformation selfInformationGUI=new SelfInformation(username);
            selfInformationGUI.show();
        });
    }

}
