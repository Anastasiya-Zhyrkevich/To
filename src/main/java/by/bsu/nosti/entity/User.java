package by.bsu.nosti.entity;

public class User {
    private int userId;
    private String login;
    private String email;
    private String password;
    private int role;
    private String k;
    private String publicKey;
      
    public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public User() {
        super();
    }

    public User(String login, String email, String password, int role, String k) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.role = role;
        this.k = k;
    }

    public User(int userId, String login, String email, String password, int role, String k) {
        this.userId = userId;
        this.login = login;
        this.email = email;
        this.password = password;
        this.role = role;
        this.k = k;
    }

    public int getUserId() {
        return userId;
    }
    

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    
    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (role != user.role) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        return email != null ? email.equals(user.email) : user.email == null;

    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + role;
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
