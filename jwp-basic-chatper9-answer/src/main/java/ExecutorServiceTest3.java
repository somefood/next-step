import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceTest3 {

    public static void main(String[] args) {
        final int maxCore = Runtime.getRuntime().availableProcessors();
        System.out.println(maxCore);

        final ExecutorService executor = Executors.newFixedThreadPool(maxCore);
        final List<Future<String>> futures = new ArrayList<>();

        for (int i = 1; i < 5; i++) {
            final int index = i;
            futures.add(executor.submit(() -> {
                System.out.println("finished job" + index);
                return "job" + index + " " + Thread.currentThread().getName();
            }));
        }

        for (Future<String> future : futures) {
            String result = null;
            try {
                result = future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println(result);
        }
        executor.shutdownNow();
        System.out.println("end");
    }
}
