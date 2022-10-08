import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JSpinner;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.*;

// variable back buat tombol back
// variable buyGame buat tombol Buy Game
// variable gameId buat JLabel
// variable gameName buat JLabel
// variable gamePrice buat JLabel
// variable gameGenre buat JLabel
// variable gameIdText buat JTextField
// variable gameNameText buat JTextField
// variable gameGenreText buat JTextField
// variable gamePriceText buat JTextField

public class buyGamesForm extends JFrame implements ActionListener, MouseListener {
	DefaultTableModel dtm;
	JTable table;
	JTextField gameIdText, gameNameText, gamePriceText, gameGenreText;
	JLabel gameId, gameName, gamePrice, gameGenre, gameQuantity, gameConfirm;
	JButton back, buyGame;
	String gameQuantityMax;
	JCheckBox checkBox;
	JSpinner spinner;
	Connect con = Connect.getInstance();
//	String query = "SELECT `gameId`, `name`, `price`, `genreName`, `quantity`"
//			+ "FROM game JOIN genre ON genre.genreId = game.genreId";
//	ResultSet rs = con.executeQuery(query);

	int id;
	String username;
	String role;
	
	public buyGamesForm(int id, String username, String role) {
		this.id = id;
		this.username = username;
		this.role = role;
		
		frameSettings();
		// Komponen Frame dan Layout
		JPanel topPanel = new JPanel();
		JPanel centerBottom = new JPanel(new GridBagLayout());

		setLayout(new BorderLayout());

		// Table dan DTM

		String[] columnNames = { "Game ID", "Game Name", "Game Price", "Genre", "Quantity" };

		// Komponen isi array data
		dtm = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		refreshData();
		table = new JTable(dtm);
		// table.setPreferredSize(new Dimension(800, 477));
		table.setRowHeight(30);
		table.setRowSelectionInterval(0, 0);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		table.getTableHeader().setReorderingAllowed(false);

		table.getTableHeader().setBackground(Color.darkGray);
		table.getTableHeader().setForeground(Color.white);
		// topPanel.setPreferredSize(new Dimension(800, 500));
		topPanel.add(scrollPane);

		// Komponen Button
		back = new JButton("Back");
		back.setForeground(Color.white);
		back.setBackground(Color.darkGray);

		buyGame = new JButton("Buy Game");
		buyGame.setBackground(Color.darkGray);
		buyGame.setForeground(Color.white);

		// Komponen Label
		gameId = new JLabel("Game ID");
		gameName = new JLabel("Game Name");
		gamePrice = new JLabel("Game Price");
		gameGenre = new JLabel("Game Genre");
		gameQuantity = new JLabel("How many do you want to buy?");
		gameConfirm = new JLabel("Once bought game cannot be returned?");

		// Komponen Text Field
		gameIdText = new JTextField(20);
		gameNameText = new JTextField(20);
		gamePriceText = new JTextField(20);
		gameGenreText = new JTextField(20);
		checkBox = new JCheckBox();
		SpinnerModel value = new SpinnerNumberModel(1, 0, 99, 1);
		spinner = new JSpinner(value);
		spinner.setPreferredSize(new Dimension(225, 20));

		gameIdText.setEditable(false);
		gameId.setForeground(Color.white);
		gameIdText.setBackground(Color.darkGray);
		gameIdText.setForeground(Color.white);

		gameNameText.setEditable(false);
		gameName.setForeground(Color.white);
		gameNameText.setBackground(Color.darkGray);
		gameNameText.setForeground(Color.white);

		gamePriceText.setEditable(false);
		gamePrice.setForeground(Color.white);
		gamePriceText.setBackground(Color.darkGray);
		gamePriceText.setForeground(Color.white);

		gameGenreText.setEditable(false);
		gameGenre.setForeground(Color.white);
		gameGenreText.setBackground(Color.darkGray);
		gameGenreText.setForeground(Color.white);

		gameQuantity.setForeground(Color.white);

		gameConfirm.setForeground(Color.white);

		topPanel.setBackground(Color.darkGray);

		GridBagConstraints in = new GridBagConstraints();
		in.insets = new Insets(5, 5, 5, 5);
		in.anchor = GridBagConstraints.WEST;

		in.gridx = 0;
		in.gridy = 0;
		centerBottom.add(gameId, in);

		in.gridy = 1;
		centerBottom.add(gameName, in);

		in.gridy = 2;
		centerBottom.add(gamePrice, in);

		in.gridy = 3;
		centerBottom.add(gameGenre, in);

		in.gridy = 4;
		centerBottom.add(gameQuantity, in);

		in.gridy = 5;
		centerBottom.add(gameConfirm, in);

		in.anchor = GridBagConstraints.CENTER;
		in.gridy = 6;
		centerBottom.add(back, in);

		in.anchor = GridBagConstraints.WEST;
		in.gridx = 1;
		in.gridy = 0;
		centerBottom.add(gameIdText, in);

		in.gridy = 1;
		centerBottom.add(gameNameText, in);

		in.gridy = 2;
		centerBottom.add(gamePriceText, in);

		in.gridy = 3;
		centerBottom.add(gameGenreText, in);

		in.gridy = 4;
		centerBottom.add(spinner, in);

		in.gridy = 5;
		centerBottom.add(checkBox, in);

		in.anchor = GridBagConstraints.CENTER;
		in.gridy = 6;
		centerBottom.add(buyGame, in);

		checkBox.setBackground(Color.darkGray);

		// back.setPreferredSize(new Dimension(200, 20));
		// buyGame.setPreferredSize(new Dimension(200, 20));

		centerBottom.setBorder(new EmptyBorder(40, 40, 40, 40));

		// Set posisi layout frame
		add(centerBottom, BorderLayout.CENTER);
		add(topPanel, BorderLayout.NORTH);

		// Ngubah warna
		table.setForeground(Color.white);
		table.setBackground(Color.darkGray);
		centerBottom.setBackground(Color.darkGray);

		// tambahan

		// Fungsi table
		table.addMouseListener(this);

		// Fungsi Button Back
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new MainForm(id, username, role);
			}
		});

		// Fungsi button Buy Game
		buyGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String valueSpin = spinner.getValue().toString();
				int valueSpinIntMax = Integer.parseInt(gameQuantityMax);
				int valueSpinInt = Integer.parseInt(valueSpin);
				int row = table.getSelectedRow();
				int gameQty = Integer.parseInt(spinner.getValue().toString());
				String gameId = dtm.getValueAt(row, 0).toString();
				boolean bool = true;
				
				if (valueSpinInt == 0 || valueSpinInt > valueSpinIntMax) {
					JOptionPane.showMessageDialog(null, "Game Quantity cannot be less than 0 or more than stock",
							"Warning", JOptionPane.WARNING_MESSAGE);
				} else if (!checkBox.isSelected()) {
					JOptionPane.showMessageDialog(null, "Check box must be checked", "Warning",
							JOptionPane.WARNING_MESSAGE);
				} else {
					int updateGameQuantity = Integer.parseInt(dtm.getValueAt(row, 4).toString()) - gameQty;
					int updateTransactionQty = 0;
					
					String queryDuplicate = "SELECT game.gameId, gameQuantity FROM game "
							+ "JOIN transaction ON game.gameId = transaction.gameId "
							+ "WHERE userId = '" + id + "' "
							+ "AND game.gameId = '" + gameId + "'";
					ResultSet rs = con.executeQuery(queryDuplicate);
					
					try {
						while (rs.next()) {
							
							if (rs.getString("gameId").equals(gameId)) {
								updateTransactionQty = rs.getInt("gameQuantity") + gameQty;
								con.updateTransaction(updateTransactionQty, gameId, id);
								
								bool = false;
							}
							
							
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					if (bool) {
						con.insertBuyGames(id, gameId, gameQty);
					}
					
					con.updateAfterBuy(updateGameQuantity, gameId);
					JOptionPane.showMessageDialog(null, "Game Bought", "Success", JOptionPane.WARNING_MESSAGE);

					clear();
					refreshData();

				}
			}
		});
		revalidate();
		pack();

	}

	void clear() {
		gameIdText.setText("");
		gameNameText.setText("");
		gameGenreText.setText("");
		gamePriceText.setText("");
		checkBox.setSelected(false);
	}

	public void refreshData() {
		dtm.setRowCount(0);
		String query = "SELECT `gameId`, `name`, `price`, `genreName`, `quantity` "
				+ "FROM `game` JOIN `genre` ON genre.genreId = game.genreId";
		ResultSet res = con.executeQuery(query);
		
		try {
			while (res.next()) {
				dtm.addRow(new Object[] {res.getObject(1), 
						res.getObject(2), res.getObject(3), 
						res.getObject(4), res.getObject(5)});
							
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void frameSettings() {
		setTitle("Buy Games Form");
		getContentPane().setLayout(null);
		setVisible(true);
		setSize(800, 720);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.DARK_GRAY);
		setResizable(false);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		int row = table.getSelectedRow();
		
		try {
			gameIdText.setText(dtm.getValueAt(row, 0).toString());
			gameNameText.setText(dtm.getValueAt(row, 1).toString());
			gamePriceText.setText(dtm.getValueAt(row, 2).toString());
			gameGenreText.setText(dtm.getValueAt(row, 3).toString());
			gameQuantityMax = dtm.getValueAt(row, 4).toString();
		} catch (Exception e2) {
			clear();
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}