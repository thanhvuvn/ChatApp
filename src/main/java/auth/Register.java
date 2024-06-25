package auth;

import dao.UserDao;
import dao.impl.UserDaoImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends JFrame {

    private JTextField txtUsername;
    private JTextField txtPassword;
    private JTextField txtConfirmPassword;
    private JLabel lblError;
    private UserDao userDao;

    public Register() {
        initialize();
    }

    private void initialize() {
        userDao = new UserDaoImpl();
        setTitle("Register Form");
        setResizable(false);
        setBounds(100, 100, 517, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel lblWelcome = new JLabel("Register New Account");
        lblWelcome.setForeground(UIManager.getColor("RadioButtonMenuItem.selectionBackground"));
        lblWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblWelcome.setBounds(27, 13, 312, 48);
        getContentPane().add(lblWelcome);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblUsername.setBounds(47, 74, 86, 20);
        getContentPane().add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtUsername.setColumns(10);
        txtUsername.setBounds(128, 70, 366, 30);
        getContentPane().add(txtUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblPassword.setBounds(47, 134, 86, 20);
        getContentPane().add(lblPassword);

        txtPassword = new JTextField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtPassword.setColumns(10);
        txtPassword.setBounds(128, 130, 366, 30);
        getContentPane().add(txtPassword);

        JLabel lblConfirmPassword = new JLabel("Confirm Password");
        lblConfirmPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblConfirmPassword.setBounds(47, 194, 110, 20);
        getContentPane().add(lblConfirmPassword);

        txtConfirmPassword = new JTextField();
        txtConfirmPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtConfirmPassword.setColumns(10);
        txtConfirmPassword.setBounds(157, 190, 337, 30);
        getContentPane().add(txtConfirmPassword);

        lblError = new JLabel("");
        lblError.setBounds(66, 287, 399, 20);
        getContentPane().add(lblError);

        JButton btnRegister = new JButton("Register");
        btnRegister.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnRegister.setIcon(new javax.swing.ImageIcon(Register.class.getResource("/image/register.png")));
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String username = txtUsername.getText();
                String password = txtPassword.getText();
                String confirmPassword = txtConfirmPassword.getText();
                lblError.setVisible(false);

                if (!password.equals(confirmPassword)) {
                    lblError.setText("Passwords do not match!");
                    lblError.setVisible(true);
                } else if (userDao.checkIfUserExists(username)) {
                    lblError.setText("Username already exists!");
                    lblError.setVisible(true);
                } else {
                    userDao.registerUser(username, password);
                    lblError.setText("User registered successfully!");
                    lblError.setVisible(true);
                    dispose();

                    Login loginWindow = new Login();
                    loginWindow.setVisible(true);
                }
            }
        });

        btnRegister.setBounds(325, 230, 169, 63);
        getContentPane().add(btnRegister);
        lblError.setVisible(false);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Register window = new Register();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
