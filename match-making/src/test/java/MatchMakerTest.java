import model.User;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.testng.Assert.*;

public class MatchMakerTest {
    private static final Random RND = new Random();
    @Test
    public void testing() {
        final ExecutorService executorService = Executors.newFixedThreadPool(10);
        final MatchMaker matchMaker = new MatchMaker();
        for (int i=0; i<100; i++) {
            int finalI = i;
            executorService.execute(() ->{
                final User user = generateRandomUser(finalI);
                matchMaker.addUser(user);

                try {
                    Thread.sleep(RND.nextInt(5) + 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
        try {
            Thread.sleep(RND.nextInt(3) + 1000L * 60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private User generateRandomUser(int id) {
        final User user = new User();
        user.setId(id);
        user.setRank(RND.nextInt(30));
        return user;
    }
}