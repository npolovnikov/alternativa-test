import model.Lobby;
import model.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MatchMaker {
    private static final int DELAY = 1000;
    private static final Object SEMAPHORE = new Object();

    private final ScheduledExecutorService executorService;
    private final List<Lobby> lobbyList;

    public MatchMaker() {
        this.lobbyList = new ArrayList<>();
        this.executorService = Executors.newSingleThreadScheduledExecutor();
//        this.executorService.scheduleWithFixedDelay(this::merge, DELAY, DELAY, TimeUnit.MILLISECONDS);
    }

    public void addUser(User user) {
        synchronized (SEMAPHORE) {
            for (Lobby lobby: lobbyList) {
                if (tryAddToLobby(user, lobby)) {
                    //Успешное добавленние
                    break;
                }
            }
        }
    }

    /**
     * Попытка добавить дользователя в конктерное лобби
     * @param user пользователь
     * @param lobby лобби
     * @return TRUE при успешном добавлнении
     */
    private boolean tryAddToLobby(User user, Lobby lobby) {
        return false;
    }
}
