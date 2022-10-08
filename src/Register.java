
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JSpinner;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;

public class Register extends JFrame implements ActionListener {

	Connect db = Connect.getInstance();

	// Isi Variable Semuanya
	JLabel createAnAccount, username, password, gender, country, chooseARole, maleLabel, femaleLabel, kosong,
			playerLabel, developerLabel;
	JTextField usernameText;
	JPasswordField passwordText;
	JRadioButton male, female, player, developer;
	JButton back, register;
	JComboBox countryCombo;
	ButtonGroup bgGender = new ButtonGroup();
	ButtonGroup bgRole = new ButtonGroup();

	public Register() {
		frameSettings();

		JPanel southPanel = new JPanel(new GridLayout(6, 2, 10, 10));
		JPanel radioButtonPanel = new JPanel(new GridLayout(1, 4, 0, 0));
		JPanel rolePanel = new JPanel(new GridLayout(1, 2, 0, 0));

		JPanel south = new JPanel();

		back = new JButton("Back");
		back.setBackground(Color.darkGray);
		back.setForeground(Color.white);
		register = new JButton("Register");
		register.setBackground(Color.darkGray);
		register.setForeground(Color.white);

		kosong = new JLabel();

		// Create An Account
		createAnAccount = new JLabel("Create An Account");
		createAnAccount.setForeground(Color.white);
		Font font = new Font("Arial", Font.BOLD, 20);
		createAnAccount.setFont(font);

		// Username Label
		username = new JLabel("Username");
		username.setForeground(Color.white);

		// Password Label
		password = new JLabel("Password");
		password.setForeground(Color.white);

		// Gender Label
		gender = new JLabel("Gender");
		gender.setForeground(Color.white);

		// Country Label
		country = new JLabel("Country");
		country.setForeground(Color.white);

		// Choose a Role label
		chooseARole = new JLabel("Choose a role:");
		chooseARole.setForeground(Color.white);

		// Male Label
		maleLabel = new JLabel("Male");
		maleLabel.setForeground(Color.white);

		// Female Label
		femaleLabel = new JLabel("Female");
		femaleLabel.setForeground(Color.white);

		// Player Label
		playerLabel = new JLabel("Player");
		playerLabel.setForeground(Color.white);

		// Developer Label
		developerLabel = new JLabel("Developer");
		developerLabel.setForeground(Color.white);

		// Username Text Field
		usernameText = new JTextField();
		usernameText.setForeground(Color.white);
		usernameText.setBackground(Color.darkGray);

		// Password Text Field
		passwordText = new JPasswordField();
		passwordText.setForeground(Color.white);
		passwordText.setBackground(Color.darkGray);

		male = new JRadioButton();
		male.setBackground(Color.darkGray);
		female = new JRadioButton();
		female.setBackground(Color.darkGray);
		player = new JRadioButton();
		player.setBackground(Color.darkGray);
		developer = new JRadioButton();
		developer.setBackground(Color.darkGray);

		bgGender.add(male);
		bgGender.add(female);
		bgRole.add(player);
		bgRole.add(developer);

		String s1[] = { "Select a Country", "Indonesia", "America", "England", "Malaysia", "Singapore", "South Korea",
				"German" };
		countryCombo = new JComboBox(s1);
		countryCombo.setBackground(Color.darkGray);
		countryCombo.setForeground(Color.white);

		// Text

		southPanel.add(createAnAccount);
		southPanel.add(kosong);
		southPanel.add(username);
		southPanel.add(usernameText);
		southPanel.add(password);

		southPanel.add(passwordText);
		southPanel.add(gender);
		southPanel.add(radioButtonPanel);

		// Radio Buttton

		radioButtonPanel.add(male);
		radioButtonPanel.add(maleLabel);

		radioButtonPanel.add(female);
		radioButtonPanel.add(femaleLabel);

		southPanel.add(country);
		southPanel.add(countryCombo);
		southPanel.add(chooseARole);
		southPanel.add(rolePanel);
		rolePanel.add(player);

		rolePanel.add(playerLabel);
		rolePanel.add(developer);
		rolePanel.add(developerLabel);

		south.add(back);
		south.add(register);

		// Warna background
		south.setBackground(Color.darkGray);
		southPanel.setBackground(Color.darkGray);
		radioButtonPanel.setBackground(Color.darkGray);
		rolePanel.setBackground(Color.darkGray);

		southPanel.setBorder(new EmptyBorder(30, 30, 0, 30));
		south.setBorder(new EmptyBorder(0, 30, 30, 30));
		add(southPanel, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);

		// Fungsi button
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new LoginForm();
			}
		});
		
		register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String usernameValidation = usernameText.getText().toString();
				String passwordValidation = new String(passwordText.getPassword());
			
				if (existingUsername() == false) {
					JOptionPane.showMessageDialog(null, "Username already exists!", "Warning",
							JOptionPane.WARNING_MESSAGE);
				} else if (usernameValidation.length() < 5 || usernameValidation.length() > 15) {
					JOptionPane.showMessageDialog(null, "Username Length Must be at least 5-15 chars", "Warning",
							JOptionPane.WARNING_MESSAGE);
				} else if (passwordValidation.length() < 3 || passwordValidation.length() > 10) {
					JOptionPane.showMessageDialog(null, "Password Length Must be at  3-10 chars", "Warning",
							JOptionPane.WARNING_MESSAGE);
				} else if (bgGender.getSelection() == null) {
					JOptionPane.showMessageDialog(null, "Please select a gender", "Warning",
							JOptionPane.WARNING_MESSAGE);
				} else if (countryCombo.getSelectedItem().toString().equals("Select a Country")) {
					JOptionPane.showMessageDialog(null, "Please select a Country", "Warning",
							JOptionPane.WARNING_MESSAGE);
				} else if (bgRole.getSelection() == null) {
					JOptionPane.showMessageDialog(null, "Please select a role", "Warning", JOptionPane.WARNING_MESSAGE);
				} else {
					male.setActionCommand("Male");
					female.setActionCommand("Female");
					player.setActionCommand("Player");
					developer.setActionCommand("Developer");
					String pass = new String(passwordText.getPassword());
					User user = new User(usernameText.getText(), pass,
							bgGender.getSelection().getActionCommand(), countryCombo.getSelectedItem().toString(),
							bgRole.getSelection().getActionCommand(), false);
					db.insertNewUser(user);

					JOptionPane.showMessageDialog(null, "Successfully registered user!", "Success",
							JOptionPane.WARNING_MESSAGE);
					clear();
				}
			}
		});

		revalidate();
		pack();

	}
	
	public boolean existingUsername() {
		String usernameV = usernameText.getText().toString();
		
		String queryValidate = "SELECT username FROM user WHERE username = '" + usernameV + "'";
		ResultSet rs = db.executeQuery(queryValidate);
		try {
			while (rs.next()) {
				if (rs.getString(1).equals(usernameV)) {
					return false;
				}
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return true;
	}

	void clear() {
		usernameText.setText("");
		passwordText.setText("");
		bgGender.clearSelection();
		bgRole.clearSelection();
		countryCombo.setSelectedIndex(0);
	}

	public void frameSettings() {
		setTitle("Register");
		getContentPane().setLayout(null);
		setVisible(true);
		setSize(600, 450);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.DARK_GRAY);
		setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
