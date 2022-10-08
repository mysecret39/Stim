import java.awt.BorderLayout;
import java.awt.Color;
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
import java.sql.SQLException;
import java.util.Random;

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

public class ManageFormStim extends JFrame implements ActionListener, MouseListener {
	
	Connect con= Connect.getInstance();
	
	// panels
	JPanel topPanel, bottomPanel, centerBottom, bottomPanelKiri, bottomPanelKanan, centerPanel;
	int selectedIndex = -1;

	// top components
	DefaultTableModel dtm;
	JTable table;
	JScrollPane scrollPane;

	// center components
	JLabel idLbl, nameLbl, genreLbl,

			testLbl, testLbl2;

	JTextField idTxt, nameTxt, genreTxt;

	// bottom components
	JButton backBtn;
	JButton insertBtn;
	JButton updateBtn;
	JButton deleteBtn;

	int id;
	String username;
	String role;
	
	public ManageFormStim(int id, String username, String role) {
		this.id = id;
		this.username = username;
		this.role = role;
		
		// set layout
		frameSettings();
		// panels
		bottomPanelKiri = new JPanel();
		bottomPanelKanan = new JPanel();
		topPanel = makeContainer(new BorderLayout());
		bottomPanel = makeContainer(new GridLayout(1, 2));
		centerPanel = makeContainer(new BorderLayout());
		centerBottom = makeContainer(new GridLayout(2, 4, 20, 10));

		Object[] ColumnName = new Object[] { "Genre ID", "Genre Name" };
		Object[][] Data = new Object[][] { 
//			{ "GEN003", "Adventure" },
//				{ "GEN006", "Mystery" },
//				{ "GEN007", "FPS" },
				};


				
				
		dtm = new DefaultTableModel(Data, ColumnName);
		
		// @Override
		// public boolean isCellEditable(int row, int column) {
		// return false;
		// }
		// ;

		table = new JTable(dtm);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(30);

		table.setBackground(Color.darkGray);
		// table.setOpaque(false);
		table.getTableHeader().setBackground(Color.darkGray);
		table.getTableHeader().setForeground(Color.white);
		table.setForeground(Color.white);
		table.setPreferredSize(new Dimension(900, 277));

		bottomPanel.setBackground(Color.darkGray);
		bottomPanelKiri.setBackground(Color.darkGray);
		bottomPanelKanan.setBackground(Color.darkGray);
		
		refreshTable();
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBackground(Color.darkGray);
		topPanel.add(scrollPane);

		// bottom panel
		idLbl = makeLabel("Genre ID");
		nameLbl = makeLabel("Genre Name");
		genreLbl = makeLabel("New Genre Name");

		testLbl = makeLabel("");
		testLbl2 = makeLabel("");

		idTxt = makeTextfield();
		idTxt.setPreferredSize(new Dimension(30, 10));

		nameTxt = makeTextfield();
		genreTxt = makeTextfield();
		backBtn = new JButton("Back");
		insertBtn = new JButton("Insert");
		updateBtn = new JButton("Update");
		deleteBtn = new JButton("Delete");

		insertBtn.setBackground(Color.darkGray);
		insertBtn.setForeground(Color.white);
		updateBtn.setBackground(Color.darkGray);
		updateBtn.setForeground(Color.white);
		deleteBtn.setBackground(Color.darkGray);
		deleteBtn.setForeground(Color.white);
		backBtn.setBackground(Color.darkGray);
		backBtn.setForeground(Color.white);

		centerBottom.add(idLbl);
		centerBottom.add(idTxt);
		centerBottom.add(genreLbl);
		centerBottom.add(genreTxt);
		centerBottom.add(nameLbl);
		centerBottom.add(nameTxt);

		centerBottom.add(testLbl);
		centerBottom.add(testLbl2);

		centerBottom.setBorder(new EmptyBorder(20, 20, 20, 20));
		centerPanel.add(centerBottom, BorderLayout.CENTER);
		centerPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

		bottomPanelKanan.add(insertBtn);
		bottomPanelKiri.add(updateBtn);
		bottomPanelKiri.add(deleteBtn);
		bottomPanelKiri.add(backBtn);

		bottomPanel.add(bottomPanelKiri, FlowLayout.LEFT);
		bottomPanel.add(bottomPanelKanan);
		
		idTxt.setEditable(false);
		
		// topPanel.add(centerBottom, BorderLayout.SOUTH);
		topPanel.setPreferredSize(new Dimension(900, 300));
		// topPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

		// add panels
		add(topPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
		// setBorder(new EmptyBorder(10, 10, 10, 10));

		backBtn.addActionListener(this);
		table.addMouseListener(this);

		insertBtn.addActionListener(this);
		updateBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
		revalidate();
		pack();
	}

	public void frameSettings() {

		setBackground(Color.DARK_GRAY);
		setSize(1000, 800);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == insertBtn) {
			insertData();
		} else if (e.getSource() == updateBtn) {
			updateData();
		} else if (e.getSource() == deleteBtn) {
			deleteData();
		} else if (e.getSource() == backBtn) {
			this.dispose();
			new MainForm(id, username, role);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == table) {
			int row = table.getSelectedRow();
			selectedIndex = row;
			
			try {
				idTxt.setText(dtm.getValueAt(row, 0).toString());
				nameTxt.setText(dtm.getValueAt(row, 1).toString());
			} catch (Exception e2) {
				clearField();
			}
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

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	void clearField() {
		idTxt.setText("");
		genreTxt.setText("");

	}

	void insertData() {
		Random rand = new Random();
		String genre = genreTxt.getText();
		if (genre.equals("")) {
			JOptionPane.showMessageDialog(null, "Please enter a name!", "Warning",
					JOptionPane.WARNING_MESSAGE);
		} else {
			String b = "true";
			for (int i = 0; i < table.getRowCount(); i++) {
				if (genre.equals(dtm.getValueAt(i, 1))) {
					JOptionPane.showMessageDialog(null, "Genre already exists!", "Warning",
							JOptionPane.WARNING_MESSAGE);
					b = "false";
				}

			}
			if (b.equals("true")) {
				String id = "GEN" + rand.nextInt(10) + rand.nextInt(10) + rand.nextInt(10);
		
				
				con.insertGenre(id, genre);
				
				JOptionPane.showMessageDialog(null, "Insert Success", "Success",
						JOptionPane.WARNING_MESSAGE);

			}
			selectedIndex = -1;
			refreshTable();
		}
		clearField();
	}

	void updateData() {
		if (selectedIndex == -1) {
			JOptionPane.showMessageDialog(null, "You must chosee row to be updated", "Warning",
					JOptionPane.WARNING_MESSAGE);
		} else {
			String b = "true";
			String name = nameTxt.getText();
			for (int i = 0; i < table.getRowCount(); i++) {
				if (name.equals(dtm.getValueAt(i, 1))) {
					JOptionPane.showMessageDialog(null, "Genre already exists!", "Warning",
							JOptionPane.WARNING_MESSAGE);
					b = "false";
				}

			}
			if (b.equals("true")) {
				String id = idTxt.getText();
				dtm.setValueAt(name, selectedIndex, 1);
				
				con.updateGenre(name,id);
				JOptionPane.showMessageDialog(null, "Update Success", "Success",
						JOptionPane.WARNING_MESSAGE);

			}
			selectedIndex = -1;
			refreshTable();
		}
		clearField();
	}

	void deleteData() {
		if (selectedIndex == -1) {
			JOptionPane.showMessageDialog(null, "Please selecte a Genre", "Warning",
					JOptionPane.WARNING_MESSAGE);
		} else {
			String id = idTxt.getText();

			con.deleteGenre(id);
			JOptionPane.showMessageDialog(null, "Delete Success", "Success",
					JOptionPane.WARNING_MESSAGE);
			selectedIndex = -1;
		}
		refreshTable();
		clearField();
	}
	
	void refreshTable() {
		  dtm.setRowCount(0);
		  String query = "SELECT * FROM genre";
		  ResultSet res = con.executeQuery(query);
		  
		  try {
		   while (res.next()) {
		    dtm.addRow(new Object[] {res.getObject(1), 
		      res.getObject(2)});
		       
		   }
		  } catch (SQLException e) {
		   e.printStackTrace();
		  }
		 }

}
