package model;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
    private static final int MAX_SIZE = 8;

    private List<User> userList = new ArrayList<>();

    public Lobby() {
        this.userList = new ArrayList<>();
    }

    public void addUser(User user) {
        userList.add(user);
    }

    public boolean isComplite() {
        return userList.size() == MAX_SIZE;
    }

    public List<User> getUserList() {
        return userList;
    }
}
