package OnlineMallSystem.ShoppingPackage.Gui;

import OnlineMallSystem.Frame;
import OnlineMallSystem.ShoppingPackage.Mysql.ChangePasswordMysql;

import javax.swing.*;

public class ChangePassword extends Frame {
    JFrame frame;
    JButton changeButton;
    JPasswordField passwordField;
    JPasswordField currentPasswordField;
    JPasswordField compareField;
    String username;
    public ChangePassword(String username){
        this.username=username;
    }
    @Override
    public void show() {
       frame=new JFrame("修改密码");
       frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       frame.setSize(400,240);
       frame.setLocationRelativeTo(null);
       frame.setLayout(null);

       changeButton=new JButton("修改密码");
       JLabel password=new JLabel("请输入原来的密码");
       passwordField=new JPasswordField(20);
       JLabel currentPassword=new JLabel("请输入新密码");
       currentPasswordField=new JPasswordField(20);
       JLabel compare=new JLabel("再次输入");
       compareField=new JPasswordField(20);

       password.setBounds(0,20,130,30);
       passwordField.setBounds(150,20,200,30);
       currentPassword.setBounds(0,70,130,30);
       currentPasswordField.setBounds(150,70,200,30);
       compare.setBounds(0,120,130,30);
       compareField.setBounds(150,120,200,30);
       changeButton.setBounds(150,170,100,30);

       frame.add(changeButton);
       frame.add(password);
       frame.add(passwordField);
       frame.add(currentPassword);
       frame.add(currentPasswordField);
       frame.add(compare);
       frame.add(compareField);

       listener();

       frame.setVisible(true);
    }

    @Override
    public void listener() {
       changeButton.addActionListener(e -> {
           char[] password=passwordField.getPassword();
           char[] currentPassword=currentPasswordField.getPassword();
           char[] compare=compareField.getPassword();
           String passwordString=new String(password);
           String currentPasswordString=new String(currentPassword);
           String compareString=new String(compare);
           String passwordRegex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=[ ]{};':\"\\\\|,.<>?]).+$";
           ChangePasswordMysql changePasswordMysql=new ChangePasswordMysql();
           if(!changePasswordMysql.validatePassword(username,passwordString)){
               JOptionPane.showMessageDialog(frame,"原密码错误");
               passwordField.setText("");
           }
           else if(currentPasswordString.length()<=8){
               JOptionPane.showMessageDialog(frame,"新密码长度需大于八个字符");
               currentPasswordField.setText("");
               compareField.setText("");
           }
           else if (!currentPasswordString.matches(passwordRegex)) {
               // 密码不符合要求的处理
               JOptionPane.showMessageDialog(frame, "新密码必须包含至少一个小写字母、一个大写字母、一个数字和一个特殊字符");
               // 清除密码字段
               currentPasswordField.setText("");
               compareField.setText("");
           }
           else if (!currentPasswordString.equals(compareString)){
               JOptionPane.showMessageDialog(frame,"两次输入新密码不同！");
               // 清除密码字段
               compareField.setText("");
           }
           else {
             changePasswordMysql.changePassword(username,currentPasswordString);
             JOptionPane.showMessageDialog(frame,"修改成功");
             frame.dispose();
           }
       });
    }
}
