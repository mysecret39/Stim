
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainForm extends JFrame implements ActionListener {

	JDesktopPane desktopPane;
	JMenuBar menuBar;
	JMenu account, manage, games;
	JMenuItem logOut, buyGames, ownedGames, manageGames, manageGenres;
	JPanel centerPnl;
	JLabel title;

	int id;
	String username;
	String role;
	
	public MainForm(int id, String username, String role) {
		this.id = id;
		this.username = username;
		this.role = role;
		
		desktopPane = new JDesktopPane();
		menuBar = new JMenuBar();
		account = new JMenu("Account");
		games = new JMenu("Games");
		manage = new JMenu("Manage");

		logOut = new JMenuItem("Log Out");
		buyGames = new JMenuItem("Buy Games");
		ownedGames = new JMenuItem("Owned Games");
		manageGames = new JMenuItem("Manage Games");
		manageGenres = new JMenuItem("Manage Genres");

		centerPnl = new JPanel(new BorderLayout());
		title = new JLabel("Stim");
		title.setFont(new Font("Calibri Italic", Font.PLAIN, 90));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);

		centerPnl.add(title);
		add(centerPnl);

		// centerPnl.setVisible(true); // 1. ini kenapa ga muncul yaa panelnya?

		// ini rolenya tergantung data di DB

//		String role = "Player";

		menuBar.setBackground(Color.darkGray);

		games.setForeground(Color.WHITE);
		account.setForeground(Color.WHITE);
		manage.setForeground(Color.WHITE);

		buyGames.setBackground(Color.darkGray);
		buyGames.setForeground(Color.WHITE);

		ownedGames.setBackground(Color.darkGray);
		ownedGames.setForeground(Color.WHITE);

		manageGames.setBackground(Color.darkGray);
		manageGames.setForeground(Color.WHITE);

		manageGenres.setBackground(Color.darkGray);
		manageGenres.setForeground(Color.WHITE);

		logOut.setBackground(Color.darkGray);
		logOut.setForeground(Color.WHITE);

		if (role.equals("Player")) {

			account.add(logOut);
			menuBar.add(account);
			games.add(buyGames);
			games.add(ownedGames);
			menuBar.add(games);

		} else if (role.equals("Developer")) {
			account.add(logOut);
			menuBar.add(account);
			manage.add(manageGames);
			manage.add(manageGenres);
			menuBar.add(manage);

		}

		logOut.addActionListener(this);
		buyGames.addActionListener(this);
		ownedGames.addActionListener(this);
		manageGames.addActionListener(this);
		manageGenres.addActionListener(this);

		// setContentPane(desktopPane);
		setJMenuBar(menuBar);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(700, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);

		revalidate();
		// pack();
	}
	// 2. Ini kenapa pas diganti variablenya error yaa? warnanya jadi gak bisa masuk

	public JMenu makeJMenu(String txt) { // Warnain jadi putih buat tulisannya di menu bar
		JMenu x = new JMenu(txt);
		x.setFont(new Font("Roboto", Font.BOLD, 20));
		x.setForeground(Color.white);
		return x;

	}

	public JMenuItem makeJMenuItem(String txt) { // Warnain jadi putih buat tulisannya di dalamnya menu bar
		JMenuItem y = new JMenu(txt);
		y.setFont(new Font("Roboto", Font.BOLD, 20));
		y.setForeground(Color.white);
		return y;

	}

	public JMenuBar makeJBar(String txt) { // Warnain jadi abu-abu untuk warna menu bar
		JMenuBar z = new JMenuBar();
		z.setBackground(Color.DARK_GRAY);
		return z;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == logOut) {
			this.dispose();
			new LoginForm();

		} else if (e.getSource() == buyGames) {

			this.dispose();
			new buyGamesForm(id, username, role);
		} else if (e.getSource() == ownedGames) {

			this.dispose();
			new OwnedGamesForm(id, username, role);

		} else if (e.getSource() == manageGames) {
			this.dispose();
			new ManageGameForm(id, username, role);
		} else if (e.getSource() == manageGenres) {

			this.dispose();
			new ManageFormStim(id, username, role);
		}

	}
}
