import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("Teacher Login");
        setSize(380, 240);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245, 247, 250));

        // 🔷 Header
        JLabel title = new JLabel("Teacher Login Panel", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(new Color(63, 81, 181));
        title.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));
        add(title, BorderLayout.NORTH);

        // 🔷 Form Panel
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(245, 247, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        JLabel userLbl = new JLabel("Username:");
        JLabel passLbl = new JLabel("Password:");
        userLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JTextField userField = new JTextField(14);
        JPasswordField passField = new JPasswordField(14);

        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setBackground(new Color(76, 175, 80));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setFocusPainted(false);
        loginBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(userLbl, gbc);
        gbc.gridx = 1;
        form.add(userField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(passLbl, gbc);
        gbc.gridx = 1;
        form.add(passField, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        form.add(loginBtn, gbc);

        add(form, BorderLayout.CENTER);

        // 🔐 Login Logic
        loginBtn.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());

            if (user.equals("ajanto") && pass.equals("1234")) {
                dispose();
                new AttendanceGUI();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Username or Password",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        setVisible(true);
    }

    // ✅ MAIN METHOD (ONLY ENTRY POINT)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}
