package OnlineMallSystem.ShoppingPackage.Gui;

import OnlineMallSystem.Frame;

import javax.swing.*;

public class SelfInformation extends Frame {
    JFrame frame;
    JButton historyButton;
    JButton changePasswordButton;
    String username;
    public SelfInformation(String username){
        this.username=username;
    }
    @Override
    public void show() {
        frame = new JFrame("个人信息");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300,300);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        historyButton=new JButton("购物历史");
        changePasswordButton=new JButton("修改密码");

        historyButton.setBounds(100,50,100,50);
        changePasswordButton.setBounds(100,200,100,50);

        frame.add(historyButton);
        frame.add(changePasswordButton);

        listener();

        frame.setVisible(true);
    }

    @Override
    public void listener() {
        historyButton.addActionListener(e -> {
           ShoppingHistory shoppingHistoryGUI=new ShoppingHistory(username);
           shoppingHistoryGUI.show();
        });
        changePasswordButton.addActionListener(e -> {
            ChangePassword changePasswordGUI=new ChangePassword(username);
            changePasswordGUI.show();
        });
    }
}
