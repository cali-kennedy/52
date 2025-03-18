package main.java.javase.t52.core;

public class User {
    String username = "";
    String password = "";
    int chipCount = 0;

    public User(String username, String password, int chipCount){
        this.username = username;
        this.password = password;
        this.chipCount = chipCount;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getChipCount() {
        return chipCount;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setChipCount(int chipCount) {
        this.chipCount = chipCount;
    }
}
