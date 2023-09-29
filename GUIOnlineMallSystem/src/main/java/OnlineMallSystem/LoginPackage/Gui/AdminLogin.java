package OnlineMallSystem.LoginPackage.Gui;

import OnlineMallSystem.Frame;
import OnlineMallSystem.LoginPackage.Mysql.AdminLoginMysql;
import OnlineMallSystem.AdminPackage.Gui.ManageChoose;

import javax.swing.*;

public class AdminLogin extends Frame {
    JTextField adminnameField;
    JPasswordField passwordField;
    // 创建管理登录界面窗口
    JFrame frame = new JFrame("管理员界面");
    //创建管理员登录按钮
    JButton loginButton=new JButton("登录");
    @Override
    public void show() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        // 将窗口居中显示
        frame.setLocationRelativeTo(null);
        //绝对布局
        frame.setLayout(null);

        JLabel adminnameLabel = new JLabel("用户名");
        JLabel passwordLabel = new JLabel("密    码");
        adminnameField=new JTextField(20);
        passwordField = new JPasswordField(20);

        adminnameLabel.setBounds(10,10,50,30);
        passwordLabel.setBounds(10,50,50,30);
        adminnameField.setBounds(70,10,200,30);
        passwordField.setBounds(70,50,200,30);
        loginButton.setBounds(95, 90, 110, 30);

        frame.add(adminnameLabel);
        frame.add(adminnameField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(loginButton);
        //添加按钮侦听
        listener();
        frame.setVisible(true);
    }

    @Override
    public void listener() {
        loginButton.addActionListener(e -> {
            //在这里处理登录按钮点击事件
            String adminname = adminnameField.getText();
            String password = new String(passwordField.getPassword());
            if (adminname.isEmpty()||password.isEmpty()){
                JOptionPane.showMessageDialog(frame, "请注意用户名和密码都不能为空", "登录失败", JOptionPane.ERROR_MESSAGE);
            }
            else {
                // 进行数据库验证
                if (AdminLoginMysql.validateLogin(adminname, password)) {
                    frame.dispose();
                    ManageChoose manageChooseGUI = new ManageChoose();
                    manageChooseGUI.show();
                }
                else{
                    JOptionPane.showMessageDialog(frame, "登录失败，请检查用户名和密码。若无账号，请点击注册。若忘记密码请点击重置密码，管理审核后将在一到两个工作日内为您重置", "登录失败", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
