
public class transaction {
	
	private int transactionId;
	private int userId;
	private String gameId;
	private int gameQuantity;

	public transaction(int transactionId, int userId, String gameId, int gameQuantity) {
		this.transactionId = transactionId;
		this.userId = userId;
		this.gameId = gameId;
		this.gameQuantity = gameQuantity;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public int getGameQuantity() {
		return gameQuantity;
	}

	public void setGameQuantity(int gameQuantity) {
		this.gameQuantity = gameQuantity;
	}
	
}
