import java.util.Scanner;

public class ThreadTest {

    public static void main(String[] args) {

        Daemon daemon = new Daemon();
        daemon.setDaemon(true);

        daemon.start();

        Scanner scanner = new Scanner(System.in);
        String result = scanner.nextLine();
        System.out.println(result);
    }
}

class Daemon extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("3초마다 자동 저장 중");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
