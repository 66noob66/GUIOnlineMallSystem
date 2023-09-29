package OnlineMallSystem.LoginPackage.Gui;

import OnlineMallSystem.Frame;
import OnlineMallSystem.LoginPackage.Mysql.ResertPasswordMysql;

import javax.swing.*;

public class ResetPassword extends Frame {
    JFrame frame=new JFrame("重置密码");
    JTextField usernameField=new JTextField(20);
    JTextField emailField=new JTextField(20);
    JButton resetButton=new JButton("重置密码");

    @Override
    public void show() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300,200);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        JLabel usernameLabel=new JLabel("请输入用户名");
        JLabel emailLabel=new JLabel("请输入预留邮箱");

        usernameLabel.setBounds(0,20,100,30);
        usernameField.setBounds(100,20,180,30);
        emailLabel.setBounds(0,90,100,30);
        emailField.setBounds(100,90,180,30);
        resetButton.setBounds(100,130,100,30);

        frame.add(usernameLabel);
        frame.add(usernameField);
        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(resetButton);
        listener();
        frame.setVisible(true);
    }

    @Override
    public void listener() {
        resetButton.addActionListener(e -> {
            ResertPasswordMysql resertPasswordMysql=new ResertPasswordMysql();
            String usernameString=usernameField.getText();
            String emailString=emailField.getText();
            if(!resertPasswordMysql.vailUsernameAndEmailData(usernameString,emailString)){
                JOptionPane.showMessageDialog(frame,"请检查用户名和邮箱是否输入正确");
            }
            else{
                resertPasswordMysql.sentNewPassword(emailString,usernameString);//更新数据库中的密码并发给用户
            }
        });
    }
}
