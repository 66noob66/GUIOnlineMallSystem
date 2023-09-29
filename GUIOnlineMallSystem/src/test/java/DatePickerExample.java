import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePickerExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("表格示例");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建表格模型
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Quantity");
        model.addColumn("Fat");

        // 向表格添加示例数据
        model.addRow(new Object[]{"Apple", 5, "2023-08-01"});
        model.addRow(new Object[]{"Banana", 10, "2023-08-02"});
        model.addRow(new Object[]{"Orange", 8, "2023-08-03"});

        // 创建表格
        JTable table = new JTable(model);

        // 创建按钮
        JButton updateButton = new JButton("修改");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // 获取选中行的数据
                    String name = (String) model.getValueAt(selectedRow, 0);
                    int quantity = (int) model.getValueAt(selectedRow, 1);
                    String fat = (String) model.getValueAt(selectedRow, 2);

                    // 创建输入对话框
                    JTextField nameField = new JTextField(name);
                    JTextField quantityField = new JTextField(Integer.toString(quantity));
                    JDateChooser fatChooser = new JDateChooser();
                    fatChooser.setDate(stringToDate(fat));

                    JPanel panel = new JPanel(new GridLayout(0, 2));
                    panel.add(new JLabel("Name: "));
                    panel.add(nameField);
                    panel.add(new JLabel("Quantity: "));
                    panel.add(quantityField);
                    panel.add(new JLabel("Fat: "));
                    panel.add(fatChooser);

                    int result = JOptionPane.showConfirmDialog(null, panel, "修改信息",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (result == JOptionPane.OK_OPTION) {
                        // 更新选中行的数据
                        model.setValueAt(nameField.getText(), selectedRow, 0);
                        model.setValueAt(Integer.parseInt(quantityField.getText()), selectedRow, 1);
                        model.setValueAt(dateToString(fatChooser.getDate()), selectedRow, 2);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "请先选择一行数据！");
                }
            }
        });

        // 创建容器并添加组件
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JScrollPane(table), BorderLayout.CENTER);
        contentPane.add(updateButton, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
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
}