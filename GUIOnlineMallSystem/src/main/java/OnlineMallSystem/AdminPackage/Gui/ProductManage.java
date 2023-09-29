package OnlineMallSystem.AdminPackage.Gui;

import OnlineMallSystem.AdminPackage.Mysql.ProductMangeMysql;
import OnlineMallSystem.Frame;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductManage extends Frame {
    // 创建主界面窗口
    JFrame frame = new JFrame("商品管理界面");
    //创建输入框
    JTextField productNumberField=new JTextField(10);
    JTextField productNameField=new JTextField(10);
    JTextField factoryField=new JTextField(10);
    JTextField productModelField=new JTextField(10);
    JTextField retailPriceField=new JTextField(10);
    JTextField restockingPriceField=new JTextField(10);
    JTextField quantityField=new JTextField(10);
    JDateChooser dateChooser=new JDateChooser();
    // 创建按钮
    JButton toUserManageButton=new JButton("客户管理");
    JButton addButton = new JButton("添加");
    JButton deleteButton = new JButton("删除");
    JButton changeButton = new JButton("修改");
    JTable table= new JTable();
    DefaultTableModel model;
    @Override
    public void show(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 500);
        // 将窗口居中显示
        frame.setLocationRelativeTo(null);
        // 创建数据表
        model = new DefaultTableModel();
        table.setModel(model);
        ProductMangeMysql productMangeMysql=new ProductMangeMysql();
        productMangeMysql.inquireProductionData(model);
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setPreferredSize(new Dimension(100, dateChooser.getPreferredSize().height));

        //创建输入布局并添加到主界面窗口中
        JPanel inputPanel=new JPanel();
        inputPanel.add(new JLabel("商品编号"));
        inputPanel.add(productNumberField);
        inputPanel.add(new JLabel("商品名称"));
        inputPanel.add(productNameField);
        inputPanel.add(new JLabel("生产厂家"));
        inputPanel.add(factoryField);
        inputPanel.add(new JLabel("生产日期"));
        inputPanel.add(dateChooser);
        inputPanel.add(new JLabel("型号"));
        inputPanel.add(productModelField);
        inputPanel.add(new JLabel("零售价"));
        inputPanel.add(retailPriceField);
        inputPanel.add(new JLabel("进价"));
        inputPanel.add(restockingPriceField);

        inputPanel.add(new JLabel("数量"));
        inputPanel.add(quantityField);
        inputPanel.add(addButton);
        frame.getContentPane().add(inputPanel,BorderLayout.NORTH);

        // 创建滚动窗格并将表格添加到主界面窗口中
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane,BorderLayout.CENTER);

        // 创建按钮布局并将购物车按钮添加到主界面窗口中
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(changeButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(toUserManageButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        //添加按钮侦听
        listener();

        frame.setVisible(true);
    }

    @Override
    public void listener() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int confirmResult = JOptionPane.showConfirmDialog(frame, "确认要移除该商品吗？", "确认移除", JOptionPane.YES_NO_OPTION);
                    if (confirmResult == JOptionPane.YES_OPTION) {
                        // 移除商品
                        String number=model.getValueAt(selectedRow,0).toString();
                        ProductMangeMysql productMangeMysql=new ProductMangeMysql();
                        productMangeMysql.deleteProduct(number);
                        model.removeRow(selectedRow);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "请选择要移除的商品行");
                }
            }
        });

        dateChooser.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    // 选择了新日期，执行相应的操作
                    System.out.println("选择的日期: " + evt.getNewValue());
                }
            }
        });

        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // 获取选中行的数据
                    String number= model.getValueAt(selectedRow,0).toString();
                    String name =  model.getValueAt(selectedRow, 1).toString();
                    String manufacturer = model.getValueAt(selectedRow, 2).toString();
                    String dateString =  model.getValueAt(selectedRow, 3).toString();
                    String modelString =  model.getValueAt(selectedRow, 4).toString();
                    String retailPrice = model.getValueAt(selectedRow, 5).toString();
                    String restockingPrice = model.getValueAt(selectedRow, 6).toString();
                    String quantity =  model.getValueAt(selectedRow, 7).toString();
                    // 创建输入对话框
                    JTextField numberField = new JTextField(number);
                    JTextField nameField = new JTextField(name);
                    JTextField manufacturerField = new JTextField(manufacturer);
                    JDateChooser DateChooser = new JDateChooser();
                    DateChooser.setDate(stringToDate(dateString));
                    JTextField modelField = new JTextField(modelString);
                    JTextField retailPriceField = new JTextField(retailPrice);
                    JTextField restockingPriceField = new JTextField(restockingPrice);
                    JTextField quantityField = new JTextField(quantity);

                    JPanel panel = new JPanel(new GridLayout(0, 2));
                    panel.add(new JLabel("Number: "));
                    panel.add(numberField);
                    panel.add(new JLabel("Name: "));
                    panel.add(nameField);
                    panel.add(new JLabel("Manufacturer: "));
                    panel.add(manufacturerField);
                    panel.add(new JLabel("Date: "));
                    panel.add(DateChooser);
                    panel.add(new JLabel("Model: "));
                    panel.add(modelField);
                    panel.add(new JLabel("RetailPrice: "));
                    panel.add(retailPriceField);
                    panel.add(new JLabel("RestockingPrice: "));
                    panel.add(restockingPriceField);
                    panel.add(new JLabel("Quantity: "));
                    panel.add(quantityField);

                    int result = JOptionPane.showConfirmDialog(null, panel, "修改信息",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (result == JOptionPane.OK_OPTION) {
                        // 更新选中行的数据
                        model.setValueAt(numberField.getText(), selectedRow, 0);
                        model.setValueAt(nameField.getText(), selectedRow, 1);
                        model.setValueAt(manufacturerField.getText(), selectedRow, 2);
                        model.setValueAt(dateToString(DateChooser.getDate()), selectedRow, 3);
                        model.setValueAt(modelField.getText(), selectedRow, 4);
                        model.setValueAt(Double.parseDouble(retailPriceField.getText()), selectedRow, 5);
                        model.setValueAt(Double.parseDouble(restockingPriceField.getText()), selectedRow, 6);
                        model.setValueAt(Integer.parseInt(quantityField.getText()), selectedRow, 7);
                    }
                    ProductMangeMysql productMangeMysql=new ProductMangeMysql();
                    productMangeMysql.updateProduct(model,selectedRow,number);
                } else {
                    JOptionPane.showMessageDialog(frame, "请先选择一行数据！");
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validateInput(productNumberField.getText(),productNameField.getText(),factoryField.getText(),
                        productModelField.getText(),
                        retailPriceField.getText(),restockingPriceField.getText(),
                        quantityField.getText())){
                    // 更新选中行的数据
                    model.addRow(new Object[]{productNumberField.getText(), productNameField.getText(), factoryField.getText(),
                            dateToString(dateChooser.getDate()), productModelField.getText(),
                            Double.parseDouble(retailPriceField.getText()), Double.parseDouble(restockingPriceField.getText()),
                            Integer.parseInt(quantityField.getText())});
                    ProductMangeMysql productMangeMysql = new ProductMangeMysql();
                    int rowcount = model.getRowCount();
                    productMangeMysql.addProudct(model, rowcount - 1);
                    productNumberField.setText("");
                    productNameField.setText("");
                    factoryField.setText("");
                    dateChooser.setDate(null); productModelField.setText("");
                    retailPriceField.setText(""); restockingPriceField.setText("");
                    quantityField.setText("");
                }
            }
        });

        toUserManageButton.addActionListener(e -> {
            frame.dispose();
            UserManage userManageGUI=new UserManage();
            userManageGUI.show();
        });
    }

    private static String dateToString(Date date) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        String time = ft.format(date);
        return time;
    }

    private static Date stringToDate(String dateString) {
        Date date=null;
        try {
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            date = ft.parse(dateString);
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return date;
    }

    private boolean validateInput(String number,String name,String manufacturer,String model,String retailPrice,String restockingPrice,String quantity){
        if(number.isEmpty()) {JOptionPane.showMessageDialog(frame,"编号不得为空！");return false;}
        else {
            ProductMangeMysql productMangeMysql=new ProductMangeMysql();
            if(productMangeMysql.validateNumber(number)){JOptionPane.showMessageDialog(frame,"存在相同编号！！");return false;}
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "名称不得为空！");
                return false;
            } else if (manufacturer.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "生产厂家不得为空！");
                return false;
            } else if (model.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "型号不得为空！");
                return false;
            } else {
                try {
                    Date date = dateChooser.getDate();
                    String dateString = dateToString(date);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    dateFormat.parse(dateString);
                } catch (NullPointerException | ParseException ex) {
                    JOptionPane.showMessageDialog(frame, "日期不得为空，且请输入正确的格式(yyyy-MM-dd)");
                    return false;
                }
                if (retailPrice.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "售价不得为空！");
                    return false;
                } else {
                    try {
                        Double.parseDouble(retailPrice);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(frame, "请输入有效的售价！");
                        return false;
                    }
                    if (restockingPrice.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "进货价不得为空！");
                        return false;
                    } else {
                        try {
                            Double.parseDouble(restockingPrice);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(frame, "请输入有效的进货价！");
                            return false;
                        }
                        if (quantity.isEmpty()) {
                            JOptionPane.showMessageDialog(frame, "商品数量不得为空！");
                            return false;
                        } else {
                            try {
                                Integer.parseInt(quantity);
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(frame, "请输入有效的商品数量！");
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
