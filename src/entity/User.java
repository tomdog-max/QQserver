package entity;

import java.io.Serializable;

public class User implements Serializable {


    private String userId;
    private String pwd;

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
