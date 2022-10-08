
public class genre {

	private String genreId;
	private String genreName;
	
	public genre(String genreName) {
		this.genreName = genreName;
	}
	
	public genre(String genreId, String genreName) {
		this.genreId = genreId;
		this.genreName = genreName;
	}

	public String getGenreId() {
		return genreId;
	}

	public void setGenreId(String genreId) {
		this.genreId = genreId;
	}

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}
	
	
}
