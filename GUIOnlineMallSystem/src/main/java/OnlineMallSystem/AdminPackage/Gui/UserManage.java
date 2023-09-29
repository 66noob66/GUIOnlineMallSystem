package OnlineMallSystem.AdminPackage.Gui;

import OnlineMallSystem.AdminPackage.Mysql.ProductMangeMysql;
import OnlineMallSystem.AdminPackage.Mysql.UserManageMysql;
import OnlineMallSystem.Frame;
import OnlineMallSystem.ShoppingPackage.Gui.SelfInformation;
import OnlineMallSystem.ShoppingPackage.Gui.ShoppingCart;
import OnlineMallSystem.ShoppingPackage.Mysql.ShoppingMysql;

import javax.swing.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
public class UserManage extends Frame {
    // 创建主界面窗口
    JFrame frame = new JFrame("客户信息管理");
    // 创建id查询按钮
    JButton idButton = new JButton("客户id查询");
    // 创建个人信息按钮
    JButton nameButton = new JButton("客户名查询");
    JButton deleteButton=new JButton("删除用户信息");
    JButton resetButton=new JButton("重置用户密码");
    JButton toProductManage=new JButton("商品管理");
    JTextField idField=new JTextField(20);
    JTextField nameField=new JTextField(20);
    JTable table=new JTable();
    DefaultTableModel model;
    @Override
    public void show(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 500);
        // 将窗口居中显示
        frame.setLocationRelativeTo(null);
        // 创建数据表
        model = new DefaultTableModel();
        table.setModel(model);

        UserManageMysql userManageMysql=new UserManageMysql();
        userManageMysql.inquireUserData(model);

        //创建输入布局并添加到主界面窗口中
        JPanel inputPanel=new JPanel();
        inputPanel.add(idField);
        inputPanel.add(idButton);
        inputPanel.add(nameField);
        inputPanel.add(nameButton);
        frame.getContentPane().add(inputPanel,BorderLayout.NORTH);

        // 创建按钮布局并将购物车按钮添加到主界面窗口中
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(deleteButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(toProductManage);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // 创建滚动窗格并将表格添加到主界面窗口中
        JScrollPane scrollPane = new JScrollPane(table);
        frame.getContentPane().add(scrollPane,BorderLayout.CENTER);
        //添加按钮侦听
        listener();

        frame.setVisible(true);
    }

    @Override
    public void listener() {
        UserManageMysql userManageMysql=new UserManageMysql();
        idButton.addActionListener(e -> {
            int id=Integer.parseInt(idField.getText());
            userManageMysql.idQueryInformation(id);
        });

        nameButton.addActionListener(e -> {
            String name=nameField.getText();
            userManageMysql.nameQueryInformation(name);
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int confirmResult = JOptionPane.showConfirmDialog(frame, "确认要删除该客户信息？", "确认删除", JOptionPane.YES_NO_OPTION);
                if (confirmResult == JOptionPane.YES_OPTION) {
                    // 移除客户信息
                    String username=model.getValueAt(selectedRow,1).toString();
                    userManageMysql.deleteUserInformation(username);
                    userManageMysql.deleteShoppingHistory(username);
                    model.removeRow(selectedRow);
                }
            }
            else {
                JOptionPane.showMessageDialog(frame, "请选择要删除的客户信息");
            }
        });

        resetButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow != -1){
                //重置密码
                String email=model.getValueAt(selectedRow,6).toString();
                String username=model.getValueAt(selectedRow,1).toString();
                userManageMysql.sentNewPassword(email,username);
            }
            else {
                JOptionPane.showMessageDialog(frame, "请选择要修改密码的客户");
            }
        });

        toProductManage.addActionListener(e -> {
            frame.dispose();
            ProductManage productMangeGUI=new ProductManage();
            productMangeGUI.show();
        });
    }

}
