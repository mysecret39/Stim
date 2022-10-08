public class User {
	private String userId;
    private String username;
    private String password;
    private String gender;
    private String country;
    private String role;
    private boolean validasiLogin;

	User(String username, String password, String gender, String country, String role, boolean validasiLogin) {
//        this.userId = userId;
    	this.username = username;
        this.password = password;
        this.gender = gender;
        this.country = country;
        this.role = role;
        this.validasiLogin = validasiLogin;

    }
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

    public boolean isValidasiLogin() {
        return this.validasiLogin;
    }

    public boolean getValidasiLogin() {
        return this.validasiLogin;
    }

    public void setValidasiLogin(boolean validasiLogin) {
        this.validasiLogin = validasiLogin;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
