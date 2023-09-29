package OnlineMallSystem.LoginPackage.Gui;
import OnlineMallSystem.Frame;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import java.awt.*;

public class IdentityChoose extends Frame {
    // 创建主界面窗口
    JFrame frame = new JFrame("登录身份选择");
    // 创建用户按钮
    JButton userButton = new JButton("用户");
    // 创建管理员按钮
    JButton adminButton = new JButton("管理员");

    @Override
    public void show(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 700);

        // 将窗口居中显示
        frame.setLocationRelativeTo(null);

        //绝对布局
        frame.setLayout(null);

        adminButton.setBounds(300, 300, 300, 100);
        // 创建字体对象
        Font font = new Font("管理员", Font.BOLD, 20);
        // 设置按钮字体和大小
        adminButton.setFont(font);

        userButton.setBounds(700, 300, 300, 100);
        // 创建字体对象
        Font font2 = new Font("用户", Font.BOLD, 20);
        // 设置按钮字体和大小
        userButton.setFont(font2);

        // 加载背景图像
       /* BufferedImage image = null;
        try {
            image = ImageIO.read(new File("C:\\Users\\86152\\Desktop\\ynu.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        // 创建显示图像的 JLabel
        JLabel backgroundLabel = new JLabel(new ImageIcon(image));
        backgroundLabel.setBounds(0, 0, 1300, 700); // 设置标签的位置和大小
        //创建按钮侦听
        listener();

        frame.add(adminButton);
        frame.add(userButton);
        /*frame.add(backgroundLabel);*/
        frame.setVisible(true);
    }

    @Override
    public void listener() {
        userButton.addActionListener(e -> {
            // 在这里处理用户按钮点击事件
            frame.dispose();  // 关闭主界面窗口
            UserLogin userGUI = new UserLogin(); // 创建用户界面实例
            userGUI.show(); // 显示用户界面
        });

        adminButton.addActionListener(e -> {
            // 在这里处理管理员按钮点击事件
            frame.dispose();  // 关闭主界面窗口
            AdminLogin managerGUI = new AdminLogin(); // 创建管理员界面实例
            managerGUI.show(); // 显示管理员界面
        });
    }
}
