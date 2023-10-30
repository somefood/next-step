public class App {
    public static Object r1 = new Object();
    public static Object r2 = new Object();
    private static class ThreadDemo1 extends Thread {
        public void run() {
            synchronized (r1) {
                System.out.println("스레드1: 자원1 점유중");

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
                System.out.println("스레드1: 자원2 사용 대기 중");

                synchronized (r2) {
                    System.out.println("스레드1: 자원 1 & 2 점유 중");
                }
            }
        }
    }

    private static class ThreadDemo2 extends Thread {
        public void run() {
            synchronized (r2) {
                System.out.println("스레드2: 자원2 점유중");

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
                System.out.println("스레드2: 자원1 사용 대기 중");

                synchronized (r1) {
                    System.out.println("스레드2: 자원 1 & 2 점유 중");
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadDemo1 t1 = new ThreadDemo1();
        ThreadDemo2 t2 = new ThreadDemo2();
        t1.start();
        t2.start();

        Thread.sleep(1000);
        System.out.println(t1.getState());
        System.out.println(t2.getState());
    }
}
