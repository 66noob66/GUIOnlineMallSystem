package OnlineMallSystem.LoginPackage.Mysql;

import java.sql.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.util.Date;
public class ResertPasswordMysql {
    private Connection connection;

    public ResertPasswordMysql() {
        establishConnection();
    }

    private void establishConnection() {
        try {
            // 使用MySQL数据库，加载并注册数据库驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 连接数据库
            String databaseUrl = "jdbc:mysql://localhost:3306/user";
            String username = "root";
            String password = "Wzj.2003.2.27.";
            connection = DriverManager.getConnection(databaseUrl, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean vailUsernameAndEmailData(String username,String email){//检查用户名和邮箱是否输入正确
        try {
            PreparedStatement statement=connection.prepareStatement("SELECT*FROM user WHERE name = ? AND email = ?");
            statement.setString(1,username);
            statement.setString(2,email);
            ResultSet resultSet=statement.executeQuery();
            boolean isvail=resultSet.next();
            return isvail;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
    String sendEmailSubject = "重置密码";
    // 邮件的内容
    String sendEmailContext = generateRandomPassword();
    // 收邮箱账号
    String receiveMailAccount;
    public void sentNewPassword(String email,String username) {
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
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭transport对象
                ts.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        updatePassword(username);
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
    public String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder password = new StringBuilder();
        int length = 8;

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }

    public void updatePassword(String username){//更新数据库中对应的密码
        try{
            PreparedStatement statement=connection.prepareStatement("UPDATE user SET password = ?,login_attempts=? WHERE name = ?");
            statement.setString(1,sendEmailContext);
            statement.setInt(2,0);
            statement.setString(3,username);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}