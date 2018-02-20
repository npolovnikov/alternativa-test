package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Lobby {
    private static final int MAX_SIZE = 8;

    private List<User> userList = new ArrayList<>();

    public Lobby() {
        this.userList = new ArrayList<>();
    }

    public void addUser(User user) {
        if (userList.size() == MAX_SIZE) {
            throw new ArrayIndexOutOfBoundsException("Максимальное число пользователей в одном лобби = " + MAX_SIZE);
        }
        userList.add(user);
    }

    public boolean isComplite() {
        return userList.size() == MAX_SIZE;
    }

    public boolean isEmpty() {
        return userList.isEmpty();
    }

    public List<User> getUserList() {
        return userList;
    }

    @Override
    public String toString() {
        return "Lobby : " +
                userList.stream()
                        .map(User::toString)
                        .collect(Collectors.joining(", ", "[ ", " ]"));
    }
}
