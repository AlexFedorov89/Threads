import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Threads {
    private static volatile char currentLetter = 'A';
    private static final Object mon = new Object();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.execute(() -> {
            synchronized (mon) {

                for (int i = 0; i < 3; i++) {
                    while (currentLetter != 'A') {
                        try {
                            mon.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    printLetter(currentLetter);

                    currentLetter = 'B';

                    mon.notifyAll();
                }
            }
        });

        executorService.execute(() -> {
            synchronized (mon) {

                for (int i = 0; i < 3; i++) {
                    while (currentLetter != 'B') {
                        try {
                            mon.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    printLetter(currentLetter);

                    currentLetter = 'C';

                    mon.notifyAll();
                }
            }
        });

        executorService.execute(() -> {
            synchronized (mon) {

                for (int i = 0; i < 3; i++) {
                    while (currentLetter != 'C') {
                        try {
                            mon.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    printLetter(currentLetter);

                    currentLetter = 'A';

                    mon.notifyAll();
                }
            }
        });

        executorService.shutdown();
    }

    private static void printLetter(char letter) {
        System.out.println(letter);
    }
}
