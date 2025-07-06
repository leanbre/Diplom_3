package model;

public class UserCredentials {

    private final String email;
    private final String password;

    public UserCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserCredentials getCredentialsFromUser(User user) {
        return new UserCredentials(
                user.getEmail(),
                user.getPassword()
        );
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
