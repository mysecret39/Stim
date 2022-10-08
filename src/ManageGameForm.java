//project ManageGame, berhasil
import java.util.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ManageGameForm extends JFrame implements ActionListener, MouseListener{

	JPanel northPanel, southPanel, centerPnl, centerMidPnl
	,southBtnPnl
//	, centerRightPnl, centerLeftPnl
//	, northBigPanel
	;
	
	JLabel gameIDLbl, gameNameLbl, gamePriceLbl, gameGenreLbl, gameQtyLbl, 
	NGameNameLbl, NGamePriceLbl, NGameGenreLbl, NGameQtyLbl
	, emptyLbl1, emptyLbl2
	;
	
	JButton backBtn, deleteBtn, updateBtn, insertBtn;
	
	JTextField gameIDtxt, gameNametxt, gamePricetxt, 
	NGameNametxt, NGamePricetxt;
	
	JTable table;
	JComboBox<String> genreBox, NGenreBox;
	JSpinner qtySpinner, NqtySpinner;
	DefaultTableModel dtm;
	JScrollPane scrollPane;
	
	Connect con = Connect.getInstance();
	
	int selectedIndex = -1;
			
	int id;
	String username;
	String role;
	
	public ManageGameForm(int id, String username, String role) {
		this.id = id;
		this.username = username;
		this.role = role;
		
//		setSize(1000, 800);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		getContentPane().setBackground(Color.darkGray);
		setLayout(new BorderLayout());
		
		//Panels
		northPanel = makeContainer(new BorderLayout());
		centerPnl = makeContainer(new BorderLayout());
		centerMidPnl = makeContainer(new GridBagLayout());
		southPanel = makeContainer(new BorderLayout());
		southBtnPnl = makeContainer(new GridBagLayout());

		
		Object [] header = new Object [] {"Game ID", "Game Name", "Game Price", "Genre", "Quantity"};
		Object [][] data = new Object [][] {
//			{"GAME002", "World Of Warcraft", 9999, "Mystery", 5},
//			{"GAME004","Dota 2", 123, "Adventure", 6},
//			{"GAME005","ARK: Survival Evolved", 149999,"Adventure", 986},
//			{"GAME006", "Valorant", 1,"FPS", 998}
		};
		
		dtm = new DefaultTableModel(data, header);
		table = new JTable(dtm);
		table.setRowHeight(20);
		
		table.setBackground(Color.darkGray);
		table.getTableHeader().setBackground(Color.darkGray);
		table.getTableHeader().setForeground(Color.white);	
		table.setForeground(Color.white);
		
		table.addMouseListener(this);
		
		refreshTable();
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBackground(Color.darkGray);
		northPanel.add(scrollPane);
		northPanel.setPreferredSize(new Dimension(800, 300));
		northPanel.setBackground(Color.darkGray);
		
//		scrollPane.setBackground(Color.darkGray);
		
		table.setPreferredSize(new Dimension(800, 277));
		table.getTableHeader().setReorderingAllowed(false);
//		northBigPanel.add(northPanel);
		
		//center panel
		//Button
		backBtn = new JButton("Back");
		backBtn.setBackground(Color.darkGray);
		backBtn.setForeground(Color.white);
		deleteBtn = new JButton("Delete");
		deleteBtn.setBackground(Color.darkGray);
		deleteBtn.setForeground(Color.white);
		updateBtn = new JButton("Update");
		updateBtn.setBackground(Color.darkGray);
		updateBtn.setForeground(Color.white);
		insertBtn = new JButton("Insert");
		insertBtn.setBackground(Color.darkGray);
		insertBtn.setForeground(Color.white);
		
		updateBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
		insertBtn.addActionListener(this);
		backBtn.addActionListener(this);
		
		//Labels
		gameIDLbl = makeLabel("Game ID");
		gameNameLbl = makeLabel("Game Name");
		gamePriceLbl = makeLabel("Game Price");
		gameGenreLbl = makeLabel("Game Genre");
		gameQtyLbl = makeLabel("Game Quantity");
		NGameNameLbl = makeLabel("New Game Name");
		NGamePriceLbl = makeLabel("New Game Price");
		NGameGenreLbl = makeLabel("New Game Genre");
		NGameQtyLbl = makeLabel("New Game Quantity");
		emptyLbl1 = makeLabel("");
		emptyLbl2 = makeLabel("");
		
		
		//Fields
		gameIDtxt = makeTextfield();
		gameIDtxt.setEditable(false);
		
//		gameIDtxt.setBackground(Color.darkGray);
//		gameIDtxt.setForeground(Color.white);
		
		gameNametxt = makeTextfield();
		gamePricetxt = makeTextfield();
		NGamePricetxt = makeTextfield();
		NGameNametxt = makeTextfield();
		
		Vector<genre> genres = new Vector<>();
		Vector<String> genreName = new Vector<>();
		
		String queryGenreName = "SELECT genreName FROM genre";
		ResultSet res1 = con.executeQuery(queryGenreName);
		try {
			while (res1.next()) {
				genres.add(new genre(res1.getString("genreName")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (genre g : genres) {
			genreName.add(g.getGenreName());
		}
				
		NGenreBox = new JComboBox(genreName);
		NGenreBox.setBackground(Color.darkGray);
		NGenreBox.setForeground(Color.white);
		NGenreBox.setPreferredSize(new Dimension(170, 20));
		
		genreBox = new JComboBox(genreName);
		genreBox.setBackground(Color.darkGray);
		genreBox.setForeground(Color.white);
		genreBox.setPreferredSize(new Dimension(170, 20));

		
		NqtySpinner = new JSpinner();
		NqtySpinner.setPreferredSize(new Dimension(170, 20));
		qtySpinner = new JSpinner();
		qtySpinner.setPreferredSize(new Dimension(170, 20));
		
		
		//left
		GridBagConstraints in = new GridBagConstraints();
		in.insets = new Insets(50,80,5,5);
		in.anchor = GridBagConstraints.WEST;
//		in.fill = GridBagConstraints.HORIZONTAL;
		
		in.gridx = 0;
		in.gridy = 0;
//		in.gridwidth = 1;
		centerMidPnl.add(gameIDLbl, in);

		in.insets = new Insets(5,80,5,5);
		in.gridy = 1;
		centerMidPnl.add(gameNameLbl, in);

		in.gridy = 2;
		centerMidPnl.add(gamePriceLbl, in);

		in.gridy = 3;
		centerMidPnl.add(gameGenreLbl, in);

		in.gridy = 4;
		centerMidPnl.add(gameQtyLbl, in);
		
//		in.gridy = 5;
////		in.anchor = GridBagConstraints.LINE_START;
//		centerMidPnl.add(backBtn, in);
		
		in.insets = new Insets(50, 5, 5, 80);
		in.gridx = 1;
		in.gridy = 0;
//		in.gridwidth = 2;
		centerMidPnl.add(gameIDtxt, in);
		
		in.insets = new Insets(5, 5, 5, 80);
		in.gridy = 1;
		centerMidPnl.add(gameNametxt, in);
			
		in.gridy = 2;
		centerMidPnl.add(gamePricetxt, in);

		in.gridy = 3;
		centerMidPnl.add(genreBox, in);

		in.gridy = 4;
		centerMidPnl.add(qtySpinner, in);
		
		//right
		
		in.insets = new Insets(50, 80, 5, 5);
		in.gridx = 2;
		in.gridy = 0;
//		in.gridwidth = 1;
		centerMidPnl.add(NGameNameLbl, in);
		
		in.insets = new Insets(5,80,5,5);
		in.gridy = 1;
		centerMidPnl.add(NGamePriceLbl, in);
		
		in.gridy = 2;
		centerMidPnl.add(NGameGenreLbl, in);
		
		in.gridy = 3;
		centerMidPnl.add(NGameQtyLbl, in);
		
		in.gridy = 4;
		centerMidPnl.add(emptyLbl1, in);
		
		in.insets = new Insets(50, 5, 5, 80);
		in.gridx = 3;
		in.gridy = 0;
		centerMidPnl.add(NGameNametxt, in);
		
		in.insets = new Insets(5, 5, 5, 80);
		in.gridy = 1;
		centerMidPnl.add(NGamePricetxt, in);
		
		in.gridy = 2;
		centerMidPnl.add(NGenreBox, in);
		
		in.gridy = 3;
		centerMidPnl.add(NqtySpinner, in);
		
		in.gridy = 4;
		centerMidPnl.add(emptyLbl2, in);

				
		centerPnl.add(centerMidPnl);
//		centerPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
		
		//south panel
		
		in.insets = new Insets(5, 5, 50, 5);
		in.gridx = 0;
		in.gridy = 0;
		in.anchor = GridBagConstraints.FIRST_LINE_START;
		southBtnPnl.add(backBtn, in);
		
		in.gridx = 1;
		in.anchor = GridBagConstraints.PAGE_START;
		southBtnPnl.add(deleteBtn, in);
		
		in.insets = new Insets(5, 5, 50, 200);
		in.gridx = 2;
		in.anchor = GridBagConstraints.FIRST_LINE_END;
		southBtnPnl.add(updateBtn, in);
		
		in.insets = new Insets(5, 193, 50, 5);
		in.gridx = 3;
		in.anchor = GridBagConstraints.LINE_END;
		southBtnPnl.add(insertBtn, in);
		
		southPanel.add(southBtnPnl);
		southBtnPnl.setBorder(new EmptyBorder(0, 20, 0, 0));
		
		add(centerPnl, BorderLayout.CENTER);
		add(northPanel, BorderLayout.NORTH);
		add(southPanel, BorderLayout.SOUTH);
		
		pack();
		revalidate();
	}
	
	public JPanel makeContainer(LayoutManager layout) {
		JPanel panel = new JPanel(layout);
		panel.setBackground(Color.darkGray);
		return panel;
	}
	
	public JLabel makeLabel(String txt) {
		JLabel label = new JLabel(txt);
		label.setFont(new Font("Times New Romans", Font.BOLD, 11));
		label.setForeground(Color.white);
		return label;	
	}
	
	public JTextField makeTextfield() {
		JTextField txtField = new JTextField(15);
		txtField.setBackground(Color.darkGray);
		txtField.setForeground(Color.white);
		return txtField;
	}
	
	void clearField() {
		gameIDtxt.setText("");
		gameNametxt.setText("");
		gamePricetxt.setText("");
		genreBox.setSelectedIndex(0);
		qtySpinner.setValue(0);
		
		NGameNametxt.setText("");
		NGamePricetxt.setText("");
		NGenreBox.setSelectedIndex(0);
		NqtySpinner.setValue(0);
	}
	
	void refreshTable() {
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
	
	void updateData() {
		
		int[] p = {0, 0, 0, 0, 0};
		
		if (selectedIndex == -1) {
			JOptionPane.showMessageDialog(null, "Select a game first!", "Warning", 
					JOptionPane.WARNING_MESSAGE);
		}
		else {
			String gameID = gameIDtxt.getText();
			String gameName = gameNametxt.getText();
			String gamePrice = gamePricetxt.getText();
			String gameGenre = genreBox.getSelectedItem().toString();
			Integer gameQty = Integer.parseInt(qtySpinner.getValue().toString());
			
			if (gameName.length() < 5 || gameName.length() > 35) {
				p[0] = 1;
//				System.out.println("trigger 1");
				
			}
//			for (int i = 0; i < table.getRowCount(); i++) {
//				if (gameName.equals(dtm.getValueAt(i, 1))) {
////					JOptionPane.showMessageDialog(null, "Game name already exists", "Warning", 
////							JOptionPane.WARNING_MESSAGE);
//					p[1] = 2;
////					System.out.println("trigger 2");
//				}
//			}
		
			
			try {
				Integer.parseInt(gamePrice);
			} catch (NumberFormatException e) {
				p[2] = 3;		
				
			}
			
			if (p[2] == 0) {
				if (Integer.parseInt(gamePrice) <= 0) {
					p[3] = 4;
				}
			}
			
			if (gameQty <= 0) {
				p[4] = 5;
			}
			
			boolean b = true;
			for (int i = 0; i < p.length && b; i++) {
				if (p[i] == 1) {
					JOptionPane.showMessageDialog(null, "Name must be between 5-30 Characters!", "Warning", 
							JOptionPane.WARNING_MESSAGE);					
					b = false;
				}
				else if (p[i] == 2) {
					JOptionPane.showMessageDialog(null, "Game name already exists", "Warning", 
							JOptionPane.WARNING_MESSAGE);
					b = false;
				}
				else if (p[i] == 3) {
					JOptionPane.showMessageDialog(null, "Price must be numeric", "Warning", 
							JOptionPane.WARNING_MESSAGE);
					b = false;
				}
				else if (p[i] == 4) {
					JOptionPane.showMessageDialog(null, "Price must be > 0", "Warning", 
							JOptionPane.WARNING_MESSAGE);
				}
				else if (p[i] == 5) {
					JOptionPane.showMessageDialog(null, "Quantity must be > 0", "Warning", 
							JOptionPane.WARNING_MESSAGE);
				}
				
			}
			
			boolean t = true;
			if (p[0] == 0 && p[1] == 0 && p[2] == 0 && p[3] == 0 && p[4] == 0) {				
				for (int i = 0; i < gamePrice.length() && t; i++) {
					char index = gamePrice.charAt(i);
					if (!(index == '0')) {
						gamePrice = gamePrice.substring(i);					
						t = false;
					}				
				}
				
				String genreID = "";
				String queryIDcheck = "SELECT genreId from genre WHERE genreName = '" + gameGenre + "'";
				ResultSet rs = con.executeQuery(queryIDcheck);				
				try {
					while (rs.next()) {
						genreID = rs.getObject(1).toString();
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				int gamePriceInt = Integer.parseInt(gamePrice);
				con.updatePrepareStatement(gameName, gamePriceInt, genreID, gameQty, gameID);
				refreshTable();
				JOptionPane.showMessageDialog(null, "Update Success", "Success", 
						JOptionPane.WARNING_MESSAGE);
				selectedIndex = -1;
			}
		}
		clearField();
	}
	
	void deleteData() {		
		if (selectedIndex == -1) {
			JOptionPane.showMessageDialog(null, "Select a game first!", "Warning", 
					JOptionPane.WARNING_MESSAGE);
		}
		else {			
			String id = gameIDtxt.getText();
			con.deletePrepareStatement(id);
			refreshTable();
			selectedIndex = -1;
			JOptionPane.showMessageDialog(null, "Delete Success", "Success", 
					JOptionPane.WARNING_MESSAGE);
		}
		clearField();
	}
	
	void insertDataNew() {
		Random rand = new Random();
		String newGameID = "";
		String NgameName = NGameNametxt.getText();
//		int gamePrice = Integer.parseInt(NGamePricetxt.getText());
		String NgamePrice = NGamePricetxt.getText();
		String NewGameGenre = NGenreBox.getSelectedItem().toString();
		int NewGameQty = (Integer)NqtySpinner.getValue();
		String newGenreID = "";
		
		int[] p = {0, 0, 0, 0, 0};
		
		if (NgameName.hashCode() == 0 || NgamePrice.hashCode() == 0 || NewGameGenre.hashCode() == 0) {
			JOptionPane.showMessageDialog(null, "All fields must be filled", "Warning", 
					JOptionPane.WARNING_MESSAGE);	
		}
		else {
			
			if (NgameName.length() < 5 || NgameName.length() > 35) {
				p[0] = 1;
				
			}
			for (int i = 0; i < table.getRowCount(); i++) {
				if (NgameName.equals(dtm.getValueAt(i, 1))) {
					p[1] = 2;
				}
			}
		
			try {
				Integer.parseInt(NgamePrice);
			} catch (NumberFormatException e) {
				p[2] = 3;						
			}
			
			if (p[2] == 0) {
				if (Integer.parseInt(NgamePrice) <= 0) {
					p[3] = 4;
				}
			}
			
			if (NewGameQty <= 0) {
				p[4] = 5;
			}
			
			boolean b = true;
			for (int i = 0; i < p.length && b; i++) {
				if (p[i] == 1) {
					JOptionPane.showMessageDialog(null, "Name must be between 5-30 Characters!", "Warning", 
							JOptionPane.WARNING_MESSAGE);					
					b = false;
				}
				else if (p[i] == 2) {
					JOptionPane.showMessageDialog(null, "Game name already exists", "Warning", 
							JOptionPane.WARNING_MESSAGE);
					b = false;
				}
				else if (p[i] == 3) {
					JOptionPane.showMessageDialog(null, "Price must be numeric", "Warning", 
							JOptionPane.WARNING_MESSAGE);
					b = false;
				}
				else if (p[i] == 4) {
					JOptionPane.showMessageDialog(null, "Price must be > 0", "Warning", 
							JOptionPane.WARNING_MESSAGE);
				}
				else if (p[i] == 5) {
					JOptionPane.showMessageDialog(null, "Quantity must be > 0", "Warning", 
							JOptionPane.WARNING_MESSAGE);
				}
				
			}
			
			if (p[0] == 0 && p[1] == 0 && p[2] == 0 && p[3] == 0 && p[4] == 0) {
				
				boolean t = true;
				for (int i = 0; i < NgamePrice.length() && t; i++) {
					char index = NgamePrice.charAt(i);
					if (!(index == '0')) {
						NgamePrice = NgamePrice.substring(i);					
						t = false;
					}				
				}
				
				newGameID = "GAME" + rand.nextInt(10) + rand.nextInt(10) + rand.nextInt(10);				
				String query = String.format("SELECT gameId FROM game");
				ResultSet res = con.executeQuery(query);				
				try {
					while (res.next()) {
						if (res.getObject(1).equals(newGameID)) {
							newGameID = "GAME" + rand.nextInt(10) + rand.nextInt(10) + rand.nextInt(10);
							res.beforeFirst();
						}
					}
				} catch (SQLException e) {					
					e.printStackTrace();
				}
				
				String queryGenreCheck = "SELECT genreId from genre WHERE genreName = '" + NewGameGenre + "'";
				ResultSet rs = con.executeQuery(queryGenreCheck);				
				try {
					while (rs.next()) {
						newGenreID = rs.getObject(1).toString();
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
								
				int NGamePriceint = Integer.parseInt(NgamePrice);
				
				con.insertPrepareStatement(newGameID, NgameName, NGamePriceint, newGenreID, NewGameQty);
				refreshTable();
				
				JOptionPane.showMessageDialog(null, "Insert Success", "Success", 
						JOptionPane.WARNING_MESSAGE);
			}
		}
		clearField();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == insertBtn) {
			insertDataNew();
		}
		else if (arg0.getSource() == updateBtn) {	
			updateData();
		}
		else if (arg0.getSource() == deleteBtn) {
			deleteData();
		}
		else if (arg0.getSource() == backBtn) {						
			dispose();
			new MainForm(id, username, role);
			
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getSource() == table) {
			int row = table.getSelectedRow();
			selectedIndex = row;
			
			try {
				gameIDtxt.setText(dtm.getValueAt(row, 0).toString());
				gameNametxt.setText(dtm.getValueAt(row, 1).toString());
				gamePricetxt.setText(dtm.getValueAt(row, 2).toString());
				genreBox.setSelectedItem(dtm.getValueAt(row, 3).toString());
				qtySpinner.setValue(Integer.parseInt(dtm.getValueAt(row, 4).toString()));
			} catch (Exception e) {
				clearField();
			}
			

		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
