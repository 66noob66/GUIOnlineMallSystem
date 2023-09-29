package OnlineMallSystem.LoginPackage.Gui;

import OnlineMallSystem.Frame;
import OnlineMallSystem.LoginPackage.Mysql.UserRegisterMysql;


import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;

public class UserRegister extends Frame {
    // 创建注册界面窗口
    JFrame frame = new JFrame("注册界面");
    //创建注册按钮
    JButton registerButton=new JButton("注册");
    JTextField usernameField;
    JPasswordField passwordField;
    JPasswordField passwordCompareField;
    JTextField emailField;
    JTextField phoneNumberField;
    @Override
    public void show(){

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 320);
        // 将窗口居中显示
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        JLabel usernameLabel=new JLabel("用户名");
        usernameField=new JTextField(20);
        JLabel passwordLabel=new JLabel("密码");
        passwordField=new JPasswordField(20);
        JLabel passwordCompareLabel=new JLabel("再次输入");
        passwordCompareField=new JPasswordField();
        JLabel emailLabel = new JLabel("邮箱");
        emailField = new JTextField();
        JLabel phoneNumberLabel=new JLabel("手机号");
        phoneNumberField=new JTextField();

        usernameLabel.setBounds(0,30,50,30);
        usernameField.setBounds(70,30,200,30);
        passwordLabel.setBounds(0,70,50,30);
        passwordField.setBounds(70,70,200,30);
        passwordCompareLabel.setBounds(0,110,70,30);
        passwordCompareField.setBounds(70,110,200,30);
        phoneNumberLabel.setBounds(0,150,50,30);
        phoneNumberField.setBounds(70,150,200,30);
        emailLabel.setBounds(0, 190, 50, 30);
        emailField.setBounds(70, 190, 200, 30);

        registerButton.setBounds(100,240,100,30);
        //添加侦听
        listener();

        frame.add(usernameLabel);
        frame.add(usernameField);
        frame.add(registerButton);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(passwordCompareLabel);
        frame.add(passwordCompareField);
        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(phoneNumberLabel);
        frame.add(phoneNumberField);
        frame.setVisible(true);
    }

    @Override
    public void listener(){
        registerButton.addActionListener(e -> {
            char[] password=passwordField.getPassword();
            char[] passwordCompare=passwordCompareField.getPassword();
            String username=usernameField.getText();
            String passwordString=new String(password);
            String passwordCompareString=new String(passwordCompare);
            String email=emailField.getText();
            String phoneNumberString=phoneNumberField.getText();
            String passwordRegex="^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=[ ]{};':\"\\\\|,.<>?]).+$";
            String emailRegex="^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            String phoneNumberRegex = "^1[3-9]\\d{9}$";
            UserRegisterMysql userRegisterMysql=new UserRegisterMysql();
            if(username.length()<5){
                JOptionPane.showMessageDialog(frame,"用户名长度不得少于五个字符！");
                usernameField.setText("");
            }
            else if(userRegisterMysql.valiUsername(username)){
                JOptionPane.showMessageDialog(frame,"用户名已经存在！");
                usernameField.setText("");
            }
            else if(passwordString.length()<=8){
                JOptionPane.showMessageDialog(frame,"密码长度需大于八个字符！");
                passwordField.setText("");
                passwordCompareField.setText("");
            }
            else if (!passwordString.matches(passwordRegex)) {
                // 密码不符合要求的处理
                JOptionPane.showMessageDialog(frame, "密码必须包含至少一个小写字母、一个大写字母、一个数字和一个特殊字符！");
                // 清除密码字段
                passwordField.setText("");
                passwordCompareField.setText("");
            }
            else if (!passwordString.equals(passwordCompareString)){
                JOptionPane.showMessageDialog(frame,"两次输入密码不同！");
                passwordCompareField.setText("");
            }
            else if(!phoneNumberString.matches(phoneNumberRegex)){
                JOptionPane.showMessageDialog(frame,"请输入正确的手机号!");
                phoneNumberField.setText("");
            }
            else if(!email.matches(emailRegex)){
                JOptionPane.showMessageDialog(frame,"请输入正确的邮箱格式");
                emailField.setText("");
            }
            else if(userRegisterMysql.valiEmailMysql(email)){
                JOptionPane.showMessageDialog(frame,"邮箱已被注册");
                emailField.setText("");
            }
            else if(!valiEmail(email)){
                JOptionPane.showMessageDialog(frame,"不存在该邮箱！");
                emailField.setText("");
            }
            else {
                JPanel panel=new JPanel();
                JTextField verCodeField=new JTextField(20);
                panel.add(new JLabel("请输入邮箱验证码："));
                panel.add(verCodeField);
                int result=JOptionPane.showConfirmDialog(frame,panel,"验证码",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
                if(result==JOptionPane.OK_OPTION) {
                    String verCodeString=verCodeField.getText();
                    if(valiVerCode(verCodeString)){
                        userRegisterMysql.insertUserData(username, passwordString, email, phoneNumberString);
                        JOptionPane.showMessageDialog(frame, "注册成功！");
                        frame.dispose();
                    }
                    else{
                        JOptionPane.showMessageDialog(frame,"验证码错误，请重新输入邮箱！");
                        emailField.setText("");
                    }
                }
            }
        });
    }

    // 发邮件账号
    String sendEmailAccount = "wzjwmy@qq.com";
    // 发邮箱账号授权码
    String sendEmailPassword = "qpscjkhyeucqchdd";
    // 发邮箱账号主机
    String sendEmailSMTPHost = "smtp.qq.com";
    // 发邮箱协议
    String sendEmailProtocol = "smtp";
    // 邮件的标题
    String sendEmailSubject = "邮箱验证";
    // 邮件的内容
    String sendEmailContext = verificationCode();
    // 收邮箱账号
    String receiveMailAccount;
    public boolean valiEmail(String email) {
        receiveMailAccount=email;
        // 配置参数
        Properties prop = new Properties();
        // 发件人的邮箱的SMTP 服务器地址
        prop.setProperty("mail.host", sendEmailSMTPHost);
        // 使用的协议（JavaMail规范要求）
        prop.setProperty("mail.transport.protocol", sendEmailProtocol);
        // 需要请求认证
        prop.setProperty("mail.smtp.auth", "true");
        // 创建session
        Session session = Session.getInstance(prop);
        // 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        Transport ts = null;
        try {
            // 2 通过session得到transport对象
            ts = session.getTransport();
            // 3 使用账户和授权码
            ts.connect(sendEmailSMTPHost, sendEmailAccount, sendEmailPassword);
            // 4 创建邮件
            Message message = createSimpleMail(session);
            // 5 发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                // 关闭transport对象
                ts.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    public MimeMessage createSimpleMail(Session session)
            throws MessagingException {
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 指明发件人
        message.setFrom(new InternetAddress(sendEmailAccount));
        // 指明收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiveMailAccount));

        // 邮件的标题
        message.setSubject(sendEmailSubject,"UTF-8");
        // 邮件的文本内容
        message.setContent(sendEmailContext, "text/html;charset=UTF-8");

        // 设置显示发件时间
        message.setSentDate(new Date());

        return message;
    }

    // 生成随机密码的方法
    public String verificationCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder password = new StringBuilder();
        int length = 10;

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }

    public boolean valiVerCode(String verificationCode){//检验验证码是否正确
        return verificationCode.equals(sendEmailContext);
    }
}
