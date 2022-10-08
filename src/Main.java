import java.sql.ResultSet;

import javax.swing.JFrame;

public class Main extends JFrame {
	
	Connect con = Connect.getInstance();
	
	public static void main(String[] args) {
		new LoginForm();
	}

}
