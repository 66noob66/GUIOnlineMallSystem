package OnlineMallSystem.AdminPackage.Mysql;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Date;
import java.util.Properties;

public class UserManageMysql {
    private Connection connection;

    public UserManageMysql() {
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

    public void inquireUserData(DefaultTableModel model){//获取客户信息
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID, name, level, registerdate, consumption, phonenumber, email FROM user");

            // 获取列数
            int columnCount = resultSet.getMetaData().getColumnCount();

            // 设置表头
            for (int i = 1; i <= columnCount; i++) {
                String columnName = resultSet.getMetaData().getColumnName(i);
                model.addColumn(columnName);
            }

            // 设置表格内容
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = resultSet.getObject(i + 1);
                }
                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void idQueryInformation(int id){
        try {
            PreparedStatement statement=connection.prepareStatement("SELECT ID, name, level, registerdate, consumption, " +
                    "phonenumber, email FROM user WHERE ID=?");
            statement.setInt(1,id);
            ResultSet resultSet=statement.executeQuery();
            if(resultSet.next()){
                String message="ID:"+resultSet.getInt("ID")+"\n客户名:"+resultSet.getString("name")+
                        "\n客户等级:"+resultSet.getString("level")+"\n注册时间"+
                        resultSet.getTimestamp("registerdate")+"\n消费金额："+resultSet.getDouble("consumption")+
                        "\n手机号："+resultSet.getString("phonenumber")+"\n邮箱："+resultSet.getString("email");
                JOptionPane.showMessageDialog(null,message,"客户信息",JOptionPane.PLAIN_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null,"ID不存在！");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void nameQueryInformation(String name){
        try {
            PreparedStatement statement=connection.prepareStatement("SELECT ID, name, level, registerdate, consumption, " +
                    "phonenumber, email FROM user WHERE name = ?");
            statement.setString(1,name);
            ResultSet resultSet=statement.executeQuery();
            if(resultSet.next()){
                String message="ID:"+resultSet.getInt("ID")+"\n客户名:"+resultSet.getString("name")+
                        "\n客户等级:"+resultSet.getString("level")+"\n注册时间"+
                        resultSet.getTimestamp("registerdate")+"\n消费金额："+resultSet.getDouble("consumption")+
                        "\n手机号："+resultSet.getString("phonenumber")+"\n邮箱："+resultSet.getString("email");
                JOptionPane.showMessageDialog(null,message,"客户信息",JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null,"用户名不存在！");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteUserInformation(String username){//删除客户信息
        try{
            PreparedStatement statement=connection.prepareStatement("DELETE FROM user where name=?");
            statement.setString(1,username);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteShoppingHistory(String username){//删除客户购物历史
        try{
            PreparedStatement statement=connection.prepareStatement("DELETE FROM shoppinghistory where username=?");
            statement.setString(1,username);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
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
    String sendEmailSubject = "管理员为您重置了密码";
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
