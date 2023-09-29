package OnlineMallSystem.ShoppingPackage.Gui;
import OnlineMallSystem.Frame;
import OnlineMallSystem.ShoppingPackage.Mysql.ShoppingCartMysql;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart extends Frame {
    List<Product> products;
    DefaultTableModel cartTableModel;
    JFrame frame;
    JButton addToCartButton;
    JButton removeButton;
    JButton selectQuantityButton;
    JButton payButton;
    JTextField quantityField;
    JTextField productNumberField;
    JTable cartTable;
    String username;
    public ShoppingCart(String username) {
        products = new ArrayList<>();
        cartTableModel = new DefaultTableModel(new Object[]{"商品编号", "商品数量"}, 0);
        this.username=username;
    }

    public void show() {
        frame = new JFrame("购物车");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // 购物车表格
        cartTable = new JTable(cartTableModel);
        // 商品编号输入框
        productNumberField = new JTextField(10);
        // 商品数量输入框
        quantityField = new JTextField(5);
        // 添加到购物车按钮
        addToCartButton = new JButton("添加到购物车");
        // 移除按钮
        removeButton = new JButton("移除");
        // 选择数量按钮
        selectQuantityButton = new JButton("更改数量");
        //购买按钮
        payButton=new JButton("付款");

        //添加按钮侦听
        listener();

        // 按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(removeButton);
        buttonPanel.add(selectQuantityButton);
        buttonPanel.add(payButton);

        // 输入面板
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("商品编号："));
        inputPanel.add(productNumberField);
        inputPanel.add(new JLabel("商品数量："));
        inputPanel.add(quantityField);
        inputPanel.add(addToCartButton);

        // 添加组件到窗口
        frame.add(new JScrollPane(cartTable), BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    public void listener(){
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productNumber = productNumberField.getText();
                String quantityText = quantityField.getText();
                try{
                    int quantity = Integer.parseInt(quantityText);
                    if(quantity<=0){
                        JOptionPane.showMessageDialog(frame,"商品数需要为正数");
                        quantityField.setText("");
                    }
                    else {
                        ShoppingCartMysql shoppingCartMysql=new ShoppingCartMysql();
                        int currentnumber=shoppingCartMysql.getAvailableQuantity(productNumber);
                        if(!shoppingCartMysql.checkProductExists(productNumber)){
                            JOptionPane.showMessageDialog(frame,"不存在该商品");
                            productNumberField.setText("");
                        }
                        else if(quantity>currentnumber){
                            JOptionPane.showMessageDialog(frame,"抱歉，所需商品数大于库存,现在所剩货物数："+currentnumber);
                            quantityField.setText("");
                        }
                        else {
                            Product product = new Product(productNumber, quantity);
                            products.add(product);
                            cartTableModel.addRow(new Object[]{productNumber, quantity});
                            // 清空输入框
                            productNumberField.setText("");
                            quantityField.setText("");
                        }
                    }
                }catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(frame,"商品数需要为整数");
                    quantityField.setText("");
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = cartTable.getSelectedRow();
                if (selectedRow != -1) {
                    int confirmResult = JOptionPane.showConfirmDialog(frame, "确认要从购物车中移除该商品吗？", "确认移除", JOptionPane.YES_NO_OPTION);
                    if (confirmResult == JOptionPane.YES_OPTION) {
                        // 从购物车中移除商品
                        products.remove(selectedRow);
                        cartTableModel.removeRow(selectedRow);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "请选择要移除的商品行");
                }
            }
        });

        selectQuantityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = cartTable.getSelectedRow();
                if (selectedRow != -1) {
                    String currentQuantity = cartTableModel.getValueAt(selectedRow, 1).toString();
                    String newQuantity = JOptionPane.showInputDialog(
                            frame,
                            "请输入新的商品数量：",
                            currentQuantity
                    );

                    if (newQuantity != null) {
                        try {
                            int quantity = Integer.parseInt(newQuantity);
                            if (quantity > 0) {
                                // 更新购物车表格中的数量
                                cartTableModel.setValueAt(quantity, selectedRow, 1);
                                // 更新商品列表中的数量
                                products.get(selectedRow).setQuantity(quantity);
                            } else {
                                int confirmResult = JOptionPane.showConfirmDialog(
                                        frame,
                                        "商品数量小于等于0，确认要从购物车中移除该商品吗？",
                                        "确认移除",
                                        JOptionPane.YES_NO_OPTION
                                );

                                if (confirmResult == JOptionPane.YES_OPTION) {
                                    // 从购物车中移除商品
                                    products.remove(selectedRow);
                                    cartTableModel.removeRow(selectedRow);
                                }
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "请输入有效的商品数量");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "请选择要更改数量的商品行");
                }
            }
        });

        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double totalValue=0.0;
                for(Product product:products) {
                    String number=product.getProductNumber();
                    int quantity=product.getQuantity();
                    ShoppingCartMysql shoppingCartMysql = new ShoppingCartMysql();
                    shoppingCartMysql.getAvailableQuantity(number);
                    totalValue=totalValue+shoppingCartMysql.calculateTotalValue(number,quantity);
                }
                frame.dispose();
                Pay payGUI=new Pay(username);
                payGUI.getproducts(products);
                payGUI.gettotalPrice(totalValue);
                payGUI.show();
            }
        });
    }

    public static class Product {
        String productNumber;
        int quantity;

        public Product(String productNumber, int quantity) {
            this.productNumber = productNumber;
            this.quantity = quantity;
        }

        public String getProductNumber() {
            return productNumber;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
