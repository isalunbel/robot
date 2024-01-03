import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DeliveryRobot {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        int numThreads = 5;

        for (int i = 0; i < numThreads; i++) {
            Thread thread = new Thread(new RouteGenerator());
            thread.start();
        }
    }

    public static class RouteGenerator implements Runnable {
        @Override
        public void run() {
            String route = generateRoute("RLRFR", 100);
            int countR = countCommands(route, 'R');
            synchronized (sizeToFreq) {
                sizeToFreq.put(countR, sizeToFreq.getOrDefault(countR, 0) + 1);
                System.out.println("Путь " + Thread.currentThread().getId() + ": Число 'R' команд: " + countR);
            }
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static int countCommands(String route, char command) {
        int count = 0;
        for (char c : route.toCharArray()) {
            if (c == command) {
                count++;
            }
        }
        return count;
    }
}