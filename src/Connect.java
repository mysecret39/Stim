//finalProject connection database

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {

	private final String USERNAME = "root";
	private final String PASSWORD = "";
	private final String DATABASE_NAME = "stim";
	private final String HOST = "localhost:3306";

	private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE_NAME);

	private Connection con;
	private Statement st;
	private ResultSet rs;
	private PreparedStatement ps;

	private static Connect instance = null;

	public static Connect getInstance() {
		if (instance == null)
			instance = new Connect();
		return instance;
	}

	private Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
			st = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error connect");
			System.exit(0);
		}
	}

	public ResultSet executeQuery(String query) {
		rs = null;
		try {
			rs = st.executeQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;

	}

	public int executeUpdate(String query) {
		try {
			return st.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public PreparedStatement preparedStatement(String query) {
		ps = null;

		try {
			ps = con.prepareStatement(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ps;
	}

	public void insertPrepareStatement(String newGameID, String NgameName, int NgamePrice, String NewGenreID,
			int NewGameQty) {
		try {
			ps = con.prepareStatement(
					"INSERT INTO `game`(`gameId`, `name`, `price`, `genreId`, `quantity`) " + "VALUES (?,?,?,?,?)");
			ps.setString(1, newGameID);
			ps.setString(2, NgameName);
			ps.setInt(3, NgamePrice);
			ps.setString(4, NewGenreID);
			ps.setInt(5, NewGameQty);

			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updatePrepareStatement(String gameName, int gamePrice, String GenreID, int GameQty, String gameID) {
		try {
			ps = con.prepareStatement("UPDATE `game` SET `name`=?, `price`=?, `genreId`=?, `quantity`=? "
					+ "WHERE gameId =?");
			ps.setString(1, gameName);
			ps.setInt(2, gamePrice);
			ps.setString(3, GenreID);
			ps.setInt(4, GameQty);
			ps.setString(5, gameID);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deletePrepareStatement(String id) {
		try {
			ps = con.prepareStatement("DELETE FROM `game` WHERE gameId = ?");
			ps.setString(1, id);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insertNewUser(User user) {

		try {
			ps = con.prepareStatement(
					"INSERT INTO user (username, password, gender, country, role)" + "VALUES (?,?,?,?,?)");

			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getGender());
			ps.setString(4, user.getCountry());
			ps.setString(5, user.getRole());

			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertGenre(String genreID, String genreName) {
		try {
			ps = con.prepareStatement("INSERT INTO `genre`(`genreId`, `genreName`) " + "VALUES (?, ?)");
			ps.setString(1, genreID);
			ps.setString(2, genreName);

			ps.execute();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void deleteGenre(String genreId) {
		try {
			ps = con.prepareStatement("DELETE FROM genre WHERE genreId = ?");
			ps.setString(1, genreId);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateGenre(String genreName, String genreId) {
		try {
			ps = con.prepareStatement("UPDATE genre SET `genreName`=? " + "WHERE genreId =?");
			ps.setString(1, genreName);
			ps.setString(2, genreId);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insertBuyGames(int userId, String gameId, int gameQuantity) {
		try {
			ps = con.prepareStatement("INSERT INTO `transaction`(`userId`, `gameId`, `gameQuantity`) "
					+ "VALUES (?, ?, ?)");

			ps.setInt(1, userId);
			ps.setString(2, gameId);
			ps.setInt(3, gameQuantity);

			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateAfterBuy(int quantity, String gameId) {
		try {
			ps = con.prepareStatement("UPDATE `game` SET `quantity`=? "
					+ "WHERE gameId =?");
			ps.setInt(1, quantity);
			ps.setString(2, gameId);
			
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateTransaction(int quantity, String gameId, int userId) {
		try {
			ps = con.prepareStatement("UPDATE `transaction` SET `gameQuantity`=? "
					+ "WHERE gameId =? AND userId = ?");
			ps.setInt(1, quantity);
			ps.setString(2, gameId);
			ps.setInt(3, userId);
			
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
