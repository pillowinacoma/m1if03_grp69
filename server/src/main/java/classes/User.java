package classes;

import java.util.Objects;

public class User {
    private final String login;
    private Boolean admin = false;

    public User(String login) {
        this.login = login;
    }

    public User(String login, Boolean admin) {
        this.login = login;
        this.admin = admin;
    }

    public String getLogin() {
        return login;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login);
    }
}
