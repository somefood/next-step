import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTest {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Job1 " + threadName);
        });

        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Job2 " + threadName);
        });

        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Job3 " + threadName);
        });

        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Job4 " + threadName);
        });

        executor.shutdown();

        if (executor.awaitTermination(20, TimeUnit.SECONDS)) {
            System.out.println(LocalTime.now() + " All jobs are terminated");
        } else {
            System.out.println(LocalTime.now() + " some jobs are not terminated");

            executor.shutdownNow();
        }

        System.out.println("end");
    }
}
