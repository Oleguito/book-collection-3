package entity;

public class Credentials {
    private String login;
    
    public String getPassword() {
        return password;
    }
    
    private String password;
    
    public Credentials(String login, String password) {
        this.login = login;
        this.password = password;
    }
    
    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}
