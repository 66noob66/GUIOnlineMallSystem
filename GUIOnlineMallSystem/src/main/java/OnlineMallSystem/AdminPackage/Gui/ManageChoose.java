package OnlineMallSystem.AdminPackage.Gui;

import OnlineMallSystem.Frame;

import javax.swing.*;

public class ManageChoose extends Frame {
    // 创建管理选择界面窗口
    JFrame frame = new JFrame("管理选择");
    // 创建商品管理按钮
    JButton adminButton = new JButton("商品管理");
    // 创建用户管理按钮
    JButton userButton = new JButton("用户管理");
    //创建修改密码按钮
    JButton passwordManageButton=new JButton("修改密码");
    @Override
    public void show(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        // 将窗口居中显示
        frame.setLocationRelativeTo(null);

        //自定义布局
        frame.setLayout(null);
        adminButton.setBounds(10, 50, 110, 30);
        userButton.setBounds(160, 50, 110, 30);
        passwordManageButton.setBounds(100,100,100,30);
        //添加按钮侦听
        listener();

        frame.add(adminButton);
        frame.add(userButton);
        frame.add(passwordManageButton);
        frame.setVisible(true);
    }

    @Override
    public void listener() {
        adminButton.addActionListener(e -> {
            // 在这里处理管理员按钮点击事件
            frame.dispose();  // 关闭主界面窗口
            ProductManage productionManageGUI = new ProductManage(); // 创建管理员界面实例
            productionManageGUI.show(); // 显示管理员界面
        });

        userButton.addActionListener(e -> {
            // 在这里处理用户按钮点击事件
            frame.dispose();  // 关闭主界面窗口
            UserManage userManageGUI = new UserManage(); // 创建用户界面实例
            userManageGUI.show(); // 显示用户界面
        });

        passwordManageButton.addActionListener(e -> {
            // 在这里处理用户按钮点击事件
            AdminPassword adminPassword=new AdminPassword();
            adminPassword.show();
        });
    }
}
