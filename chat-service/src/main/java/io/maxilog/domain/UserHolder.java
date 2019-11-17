package io.maxilog.domain;

public class UserHolder {

    private String userName;

    public UserHolder() {
    }

    public UserHolder(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
