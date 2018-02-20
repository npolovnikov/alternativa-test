import model.Lobby;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MatchMaker {
    private static final Logger LOG = LoggerFactory.getLogger(MatchMaker.class);
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private static final int DELAY = 1000;
    private static final Object SEMAPHORE = new Object();

    private final ScheduledExecutorService executorService;
    private final List<Lobby> lobbyList;

    public MatchMaker() {
        LOG.info("Constructor");
        this.lobbyList = new ArrayList<>();
        this.executorService = Executors.newSingleThreadScheduledExecutor();
        this.executorService.scheduleWithFixedDelay(this::joinLobby, DELAY, DELAY, TimeUnit.MILLISECONDS);
    }

    /**
     * Объединение Лобби каждый с каждым
     */
    private void joinLobby() {
        synchronized (SEMAPHORE) {
            LOG.debug("Start joining, lobby size = {}", lobbyList.size());
            for (int i = 0; i < lobbyList.size() - 1; i++) {
                for (int j = i + 1; j < lobbyList.size(); j++) {
                    join(lobbyList.get(i), lobbyList.get(j));
                }
            }

            lobbyList.removeIf(Lobby::isEmpty);
        }
    }

    /**
     * Объединение 2х Лобби
     * @param from Откуда берем Игроков
     * @param to Куда, возможно, их переместим
     */
    private void join(Lobby from, Lobby to) {
//        synchronized (SEMAPHORE) {
            LOG.debug("Joining from {}, to {}", from, to);
            final List<User> toBeAdded = from.getUserList().stream()
                    .filter(user -> tryAddToLobby(user, to))
                    .collect(Collectors.toList());

            LOG.debug("Users to be Added {}", toBeAdded);
            for (User user : toBeAdded) {
                to.addUser(user);
                from.getUserList().remove(user);

                if (to.isComplite()) {
                    startGame(to);
                    break;
                }
            }
//        }
    }

    public void addUser(User user) {
        synchronized (SEMAPHORE) {
            LOG.debug("Adding new User");
            final Lobby lobby = lobbyList
                    .stream()
                    .filter(l -> tryAddToLobby(user, l))
                    .findFirst()
                    .orElseGet(this::createNewLobby);

            lobby.addUser(user);
            LOG.debug("New used {}, added to {}", user, lobby);
            if (lobby.isComplite()) {
                startGame(lobby);
            }
        }
    }

    /**
     * Вывести в консоль результат работы матчмейкера.
     * Набор строк вида (time user[0] user[1] ... user[7]),
     * где time - время создания матча
     * user[0] user[1] ... user[7] - список игроков, которые отобраны для данного матча
     * @param lobby
     */
    private void startGame(Lobby lobby) {
        lobbyList.remove(lobby);
        LOG.info("{} {}", SDF.format(new Date()), lobby.getUserList());
    }

    private Lobby createNewLobby() {
        LOG.debug("Creating new Lobby");
        Lobby lobby = new Lobby();
        lobbyList.add(lobby);
        return lobby;
    }

    /**
     * Попытка добавить дользователя в конктерное лобби
     * @param user пользователь
     * @param lobby лобби
     * @return TRUE при успешном добавлнении
     */
    private boolean tryAddToLobby(User user, Lobby lobby) {
        return lobby.getUserList().stream().allMatch(user1 -> match(user, user1));
    }

    /**
     * Для каждой пары игроков (A,B) в созданном матче должны выполняться условия
     * abs(A.rank - B.rank) <= waiting_time(A) / 5000 + waiting_time(B) / 5000 ,
     * где waiting_time(A) = (текущее время) - enter_time(A)
     * @param a Пользователь 1
     * @param b Пользователь 2
     * @return
     */
    private boolean match(User a, User b) {
        return Math.abs(a.getRank() - b.getRank()) <=
                a.getWaitingTime() + b.getWaitingTime();
    }
}
