package model;

public class User {
    private static final int DELAY = 5000;

    private int id;
    private int rank;
    private long enterTime;
    private long waitingTime;

    public User() {
        enterTime = System.currentTimeMillis();
    }

    public User(int id, int rank) {
        this();
        this.id = id;
        this.rank = rank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public long getEnterTime() {
        return enterTime;
    }

    public long getWaitingTime() {
        updateWaitingTime();
        return waitingTime;
    }

    private void updateWaitingTime() {
        waitingTime = (System.currentTimeMillis() - enterTime) / DELAY;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", rank=" + rank +
                '}';
    }
}
