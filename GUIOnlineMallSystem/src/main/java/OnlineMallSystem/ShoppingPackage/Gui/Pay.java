package OnlineMallSystem.ShoppingPackage.Gui;
import OnlineMallSystem.Frame;
import OnlineMallSystem.ShoppingPackage.Mysql.PayMysql;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.List;
public class Pay extends Frame {
    JFrame frame;
    JLabel totalPriceLabel;
    JTextField cardNumberField;
    JTextField cvvField;
    JButton payButton;
    double totalPrice;
    List<ShoppingCart.Product> products;
    String username;
    public  Pay(String username){
        this.username=username;
    }
    @Override
    public void show() {
        frame = new JFrame("付款");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout());

        // 计算总价标签
        totalPriceLabel = new JLabel("总价：$" + totalPrice);
        // 卡号输入框
        JLabel cardNumberLabel = new JLabel("卡号：");
        cardNumberField = new JTextField(16);
        // CVV输入框
        JLabel cvvLabel = new JLabel("CVV：");
        cvvField = new JTextField(3);
        // 付款按钮
        payButton = new JButton("确认付款");

        frame.add(totalPriceLabel);
        frame.add(cardNumberLabel);
        frame.add(cardNumberField);
        frame.add(cvvLabel);
        frame.add(cvvField);
        frame.add(payButton);
        listener();
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void listener() {
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PayMysql payMysql=new PayMysql();
                for(ShoppingCart.Product product:products){
                    String number=product.getProductNumber();
                    int quantity=product.getQuantity();
                    payMysql.updateProductQuantity(quantity,number);
                    payMysql.updateShoppingHistory(quantity,username,payMysql.getProductName(number));
                }
                payMysql.updateConsumption(totalPrice,username);
                double consumption=payMysql.getConsumption(username);
                if(consumption>100 && consumption<=1000){
                    payMysql.changeLevel(username,"铜牌客户");
                }
                else if(consumption>1000 && consumption<=10000){
                    payMysql.changeLevel(username,"银牌客户");
                }
                else if(consumption>10000){
                    payMysql.changeLevel(username,"金牌客户");
                }
                // 显示付款成功对话框
                JOptionPane.showMessageDialog(frame, "付款成功！");
                // 关闭付款界面
                frame.dispose();
            }
        });
    }

    public void gettotalPrice(double totalprice){
        this.totalPrice=totalprice;
    }

    public void getproducts(List<ShoppingCart.Product> products){
        this.products=products;
    }
}
