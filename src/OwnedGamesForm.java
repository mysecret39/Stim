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
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


public class OwnedGamesForm extends JFrame implements ActionListener, MouseListener {
	
	
	Connect con = Connect.getInstance();
	
	
	// panels
	JPanel northPnl, centerPnl, centerFormPnl;
	int selectedIndex = -1;

	// top components
	DefaultTableModel dtm;
	JTable table;
	JScrollPane scrollPane;

	// center components
	JLabel idLbl, nameLbl, priceLbl, genreLbl, qtyLbl, totalLbl;
	JTextField idTxt, nameTxt, priceTxt, genreTxt, qtyTxt, totalTxt;

	// bottom components
	JButton backBtn;

	int id;
	String username;
	String role;
	public OwnedGamesForm(int id, String username, String role) {

		this.id = id;
		this.username = username;
		this.role = role;
		// set layout
		frameSettings();
		// panels
		northPnl = makeContainer(new BorderLayout());
		centerPnl = makeContainer(new BorderLayout());
		centerFormPnl = makeContainer(new GridBagLayout());
	

		Object[] ColumnName = new Object[] { "Game ID", "Game Name", "Genre", "Quantity", "Price" };
		Object[][] Data = new Object[][] {
			
		};
	

		dtm = new DefaultTableModel(Data, ColumnName) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

	
		table = new JTable(dtm);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(30);
		table.getTableHeader().setBackground(Color.darkGray);
		table.getTableHeader().setForeground(Color.WHITE);
		table.setPreferredSize(new Dimension(800, 277));
		
		refreshData();
			  
		scrollPane = new JScrollPane(table);
		northPnl.add(scrollPane);

		// bottom panel
		idLbl = makeLabel("Game ID");
		nameLbl = makeLabel("Game Name");
		priceLbl = makeLabel("Price");
		genreLbl = makeLabel("Game Genre");
		qtyLbl = makeLabel("Owned Quantity");
		totalLbl = makeLabel("Total Spent on Games");

		idTxt = makeTextfield();
		nameTxt = makeTextfield();
		priceTxt = makeTextfield();
		genreTxt = makeTextfield();
		qtyTxt = makeTextfield();
		totalTxt = makeTextfield();

		backBtn = new JButton("Back");
		backBtn.setBackground(Color.darkGray);
		backBtn.setForeground(Color.WHITE);

		GridBagConstraints in = new GridBagConstraints();
		in.insets = new Insets(5, 5, 5, 5);
		in.anchor = GridBagConstraints.WEST;

		in.gridx = 0;
		in.gridy = 0;
		centerFormPnl.add(idLbl, in);

		in.gridy = 1;
		centerFormPnl.add(nameLbl, in);

		in.gridy = 2;
		centerFormPnl.add(priceLbl, in);

		in.gridy = 3;
		centerFormPnl.add(genreLbl, in);

		in.gridy = 4;
		centerFormPnl.add(qtyLbl, in);

		in.gridy = 5;
		centerFormPnl.add(totalLbl, in);

		in.gridy = 6;
		centerFormPnl.add(backBtn, in);

		in.gridx = 1;
		in.gridy = 0;
		centerFormPnl.add(idTxt, in);

		in.gridy = 1;
		centerFormPnl.add(nameTxt, in);

		in.gridy = 2;
		centerFormPnl.add(priceTxt, in);

		in.gridy = 3;
		centerFormPnl.add(genreTxt, in);

		in.gridy = 4;
		centerFormPnl.add(qtyTxt, in);

		in.gridy = 5;
		centerFormPnl.add(totalTxt, in);

		idTxt.setEditable(false);
		nameTxt.setEditable(false);
		priceTxt.setEditable(false);
		genreTxt.setEditable(false);
		qtyTxt.setEditable(false);
		totalTxt.setEditable(false);

		centerFormPnl.setBorder(new EmptyBorder(40, 40, 40, 40));
		centerPnl.add(centerFormPnl);

		// add panels
		northPnl.setPreferredSize(new Dimension(800, 300));
		add(northPnl, BorderLayout.NORTH);
		add(centerPnl, BorderLayout.CENTER);



		backBtn.addActionListener(this);
		table.addMouseListener(this);

		table.setForeground(Color.white);
		table.setBackground(Color.darkGray);

		pack();
		revalidate();
	}

	 public void refreshData() {
		 
		 String query = "SELECT game.gameId , `name`, `genreName`, `gameQuantity`, `price`"
				    + "FROM game "
				    + "JOIN genre ON genre.genreId = game.genreId "
				    + "JOIN transaction ON transaction.gameId = game.gameId "
				    + "JOIN user ON user.userId = transaction.userId "
				    + "WHERE transaction.userId = '" + id + "'";
		 ResultSet res = con.executeQuery(query);
				 
		  dtm.setRowCount(0);
		  try {
		   while (res.next()) {
		    dtm.addRow(new Object[] { res.getObject("gameId"), res.getObject("name"), res.getObject("genreName"), res.getObject("gameQuantity"),
		      res.getObject("price") });
		   }
		  } catch (SQLException e) {
		 
		   e.printStackTrace();
		  }
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

	public void frameSettings() {
		setTitle("Owned Games Form");
		getContentPane().setLayout(null);
		setVisible(true);
		setSize(1000, 800);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.DARK_GRAY);
		setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backBtn) {
			this.dispose();
			new MainForm(id, username, role);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() != backBtn) {
			int row = table.getSelectedRow();
			int price = 0;
			int totalPrice = 0;
			for (int i = 0; i < table.getRowCount(); i++) {
				price = Integer.parseInt(dtm.getValueAt(i, 4).toString())
						* Integer.parseInt(dtm.getValueAt(i, 3).toString());
				
				totalPrice = totalPrice + price;
			}
			
			selectedIndex = row;
			
			try {
				idTxt.setText(dtm.getValueAt(row, 0).toString());
				nameTxt.setText(dtm.getValueAt(row, 1).toString());
				genreTxt.setText(dtm.getValueAt(row, 2).toString());
				qtyTxt.setText(dtm.getValueAt(row, 3).toString());
				priceTxt.setText(dtm.getValueAt(row, 4).toString());

				totalTxt.setText(totalPrice + "");
			} catch (Exception e2) {
				
			}
			
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

}