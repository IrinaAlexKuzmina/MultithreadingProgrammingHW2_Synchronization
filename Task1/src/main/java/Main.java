import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        final Manufacturer manufacturer = new Manufacturer();
        final Manufacture manufacture = new Manufacture(manufacturer, "Производитель");

        final ExecutorService customerPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        customerPool.submit(new Thread(null, manufacturer::sellCar, "Покупатель 1"));
        customerPool.submit(new Thread(null, manufacturer::sellCar, "Покупатель 2"));
        customerPool.submit(new Thread(null, manufacturer::sellCar, "Покупатель 3"));

        manufacture.start();
        customerPool.shutdown();

        boolean isStop = false;
        while (!isStop) {
            if (customerPool.isTerminated()) {
                manufacture.interrupt();
                isStop = true;
            }
        }
    }
}
