import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class QueryButtonExample {
    private JFrame frame;
    private JTextField idTextField;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    QueryButtonExample window = new QueryButtonExample();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public QueryButtonExample() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("ID:");
        lblNewLabel.setBounds(30, 30, 46, 14);
        frame.getContentPane().add(lblNewLabel);

        idTextField = new JTextField();
        idTextField.setBounds(80, 27, 86, 20);
        frame.getContentPane().add(idTextField);
        idTextField.setColumns(10);

        JButton queryButton = new JButton("Query");
        queryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idTextField.getText());
                queryData(id);
            }
        });
        queryButton.setBounds(180, 26, 89, 23);
        frame.getContentPane().add(queryButton);
    }

    private void queryData(int id) {
        String name = "";
        String level = "";
        double consumption = 0.0;

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "root", "Wzj.2003.2.27.")) {
            String sql = "SELECT name, level, consumption FROM user WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    name = resultSet.getString("name");
                    level = resultSet.getString("level");
                    consumption = resultSet.getDouble("consumption");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String message = "Name: " + name + "\nLevel: " + level + "\nConsumption: " + consumption;
        JOptionPane.showMessageDialog(frame, message);
    }
}