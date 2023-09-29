package OnlineMallSystem.LoginPackage.Gui;

import OnlineMallSystem.Frame;
import OnlineMallSystem.LoginPackage.Mysql.UserLoginMysql;
import OnlineMallSystem.ShoppingPackage.Gui.Shopping;

import javax.swing.*;
import java.awt.*;

public class UserLogin extends Frame {
    JTextField usernameField;
    JPasswordField passwordField;
    // 创建用户界面窗口
    JFrame frame = new JFrame("用户界面");
    //创建登录按钮
    JButton loginButton=new JButton("登录");
    //创建注册按钮
    JButton registerButton=new JButton("注册");
    //创建忘记密码按钮
    JButton forggetButton=new JButton("忘记密码");
    @Override
    public void show() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        // 将窗口居中显示
        frame.setLocationRelativeTo(null);
        //绝对布局
        frame.setLayout(null);

        JLabel usernameLabel = new JLabel("用户名");
        usernameField=new JTextField(20);
        JLabel passwordLabel = new JLabel("密    码");
        passwordField = new JPasswordField(20);

        usernameLabel.setBounds(10,10,50,30);
        usernameField.setBounds(70,10,200,30);
        passwordLabel.setBounds(10,50,50,30);
        passwordField.setBounds(70,50,200,30);
        loginButton.setBounds(10, 90, 110, 30);
        registerButton.setBounds(160, 90, 110, 30);
        forggetButton.setBounds(100,120,100,30);
        forggetButton.setForeground(Color.BLUE);
        forggetButton.setBorderPainted(false);
        forggetButton.setContentAreaFilled(false);

        frame.add(usernameLabel);
        frame.add(usernameField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(loginButton);
        frame.add(registerButton);
        frame.add(forggetButton);
        //添加按钮侦听
        listener();
        frame.setVisible(true);
    }

    @Override
    public void listener() {
        loginButton.addActionListener(e -> {
            //在这里处理登录按钮点击事件
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            UserLoginMysql userLoginMysql=new UserLoginMysql();
            int login_Attempts=userLoginMysql.valiLoginAttempts(username);
            if (username.isEmpty()||password.isEmpty()){
                JOptionPane.showMessageDialog(frame, "请注意用户名和密码都不能为空", "登录失败", JOptionPane.ERROR_MESSAGE);
            }
            else if(login_Attempts==5){
                JOptionPane.showMessageDialog(frame,"账户已锁定，请点击忘记密码进行密码重置");
            }
            else {
                // 进行数据库验证
                if (userLoginMysql.validateLogin(username, password)) {
                    userLoginMysql.resetLoginAttempts(username);
                    frame.dispose();
                    Shopping shoppingGUI = new Shopping(username);
                    shoppingGUI.show();
                }
                else{
                    userLoginMysql.updateLoginAttempts(username);
                    JOptionPane.showMessageDialog(frame, "登录失败，请检查用户名和密码。若无账号，请点击注册。若忘记密码请点击重置密码，管理审核后将在一到两个工作日内为您重置", "登录失败", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(e -> {
            UserRegister userRegisterGUI = new UserRegister();
            userRegisterGUI.show();
        });

        forggetButton.addActionListener(e -> {
            ResetPassword resetPasswordGUI=new ResetPassword();
            resetPasswordGUI.show();
        });
    }
}