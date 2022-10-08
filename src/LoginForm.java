import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.ResultSet;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.protocol.Resultset;

public class LoginForm extends JFrame implements ActionListener {

	// Variable
	JPanel centerPnl, titlePnl, submitPnl, usernamePnl, passwordPnl, usernamePnl2, passwordPnl2;
	JLabel titleLbl, usernameLbl, passwordLbl, emailLbl, confirmLbl;
	JButton loginBtn, registerBtn;
	JTextField nameTxt;
	JPasswordField passTxt;
	Connect con = Connect.getInstance();

	public LoginForm() {

		// set layout
		frameSettings();
		setLayout(new BorderLayout());
		// flow layout
		centerPnl = makeContainer(new GridLayout(2, 2));
		titlePnl = makeContainer(new BorderLayout());
		submitPnl = makeContainer(new FlowLayout());
		usernamePnl = makeContainer(new FlowLayout());
		passwordPnl = makeContainer(new FlowLayout());
		usernamePnl2 = makeContainer(new FlowLayout());
		passwordPnl2 = makeContainer(new FlowLayout());

		// top title

		titleLbl = new JLabel("Login");
		titleLbl.setFont(new Font("Times New Roman", Font.BOLD, 48));
		titleLbl.setForeground(Color.WHITE);
		titleLbl.setVerticalAlignment(titleLbl.CENTER);
		titleLbl.setHorizontalAlignment(titleLbl.CENTER);

		nameTxt = makeInputField();
		loginBtn = new JButton("Login");
		registerBtn = new JButton("Register");
		usernameLbl = makeLabel("Username :");

		// label password

		passwordLbl = makeLabel("Password  :");
		passTxt = makePasswordField();

		// isi centerPanel

		usernamePnl.add(usernameLbl);
		passwordPnl.add(passwordLbl);
		usernamePnl2.add(nameTxt);
		passwordPnl2.add(passTxt);

		// top
		titlePnl.add(titleLbl);

		// center
		centerPnl.add(usernamePnl);
		centerPnl.add(usernamePnl2);
		centerPnl.add(passwordPnl);
		centerPnl.add(passwordPnl2);

		// bottom
		submitPnl.add(loginBtn);
		submitPnl.add(registerBtn);

		loginBtn.setBackground(Color.darkGray);
		loginBtn.setForeground(Color.white);
		registerBtn.setBackground(Color.darkGray);
		registerBtn.setForeground(Color.white);

		centerPnl.setBorder(new EmptyBorder(0, 40, 0, 40));
		titlePnl.setBorder(new EmptyBorder(40, 40, 0, 40));
		submitPnl.setBorder(new EmptyBorder(0, 40, 40, 40));

		add(titlePnl, BorderLayout.NORTH);
		add(submitPnl, BorderLayout.SOUTH);
		add(centerPnl, BorderLayout.CENTER);

		loginBtn.addActionListener(this);
		registerBtn.addActionListener(this);
		revalidate();
		pack();
	}

	void frameSettings() {
		setBackground(Color.DARK_GRAY);
		setSize(800, 400);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public JLabel makeLabel(String txt) {
		JLabel label = new JLabel(txt);
		label.setFont(new Font("Roboto", Font.BOLD, 15));
		label.setForeground(Color.white);
		return label;
	}

	public JPanel makeContainer(LayoutManager layout) {
		JPanel panel = new JPanel(layout);
		panel.setBackground(Color.darkGray);
		return panel;
	}

	public JTextField makeInputField() {
		JTextField field = new JTextField();
		field.setBackground(Color.darkGray);
		field.setForeground(Color.white);
		field.setPreferredSize(new Dimension(200, 30));
		return field;
	}

	public JPasswordField makePasswordField() {
		JPasswordField field = new JPasswordField();
		field.setBackground(Color.darkGray);
		field.setForeground(Color.white);
		field.setPreferredSize(new Dimension(200, 30));
		return field;
	}

	void clearField() {
		nameTxt.setText("");
		passTxt.setText("");

	}

	void loginValidation() {
		String usernameValidation = nameTxt.getText().toString();
		String passwordValidation = new String(passTxt.getPassword());
	
//		boolean validasiLogin = true;
//		String role = "";
//		try {
//			String query = "SELECT * FROM user WHERE username='" + usernameValidation + "' AND password='"
//					+ passwordValidation + "'";
//			ResultSet res = con.executeQuery(query);
//			if (!(res.next())) {
//				validasiLogin = false;
//			} else {
//				int id = res.getInt("userId");
//				role = res.getString("role");
//				String username = res.getString("username");
//				validasiLogin = true;
//
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}

		if (nameTxt.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Username cannot be empty", "Warning", JOptionPane.WARNING_MESSAGE);
		} else if (passTxt.getPassword().length == 0) {
			JOptionPane.showMessageDialog(null, "Password cannot be empty", "Warning", JOptionPane.WARNING_MESSAGE);
		} else {
			try {
				String query = "SELECT * FROM user WHERE username = '" + usernameValidation + "' AND password = '"
						+ passwordValidation + "'";
				ResultSet res = con.executeQuery(query);
				
				if (!(res.next())) {
					JOptionPane.showMessageDialog(null, "Username / Password is Wrong", "Warning", JOptionPane.WARNING_MESSAGE);
				} else {
					int id = res.getInt("userId");
					String role = res.getString("role");
					String username = res.getString("username");

					this.dispose();
					new MainForm(id, username, role);
				}
				
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		} 
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginBtn) {
			loginValidation();
		} else if (e.getSource() == registerBtn) {
			this.dispose();
			new Register();
		}
	}

}
