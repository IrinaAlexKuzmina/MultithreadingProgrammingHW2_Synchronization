import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        final Manufacturer manufacturer = new Manufacturer();
        final Manufacture manufacture = new Manufacture(manufacturer, "Производитель");

        final ExecutorService customerPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        customerPool.submit(manufacturer::sellCar);
        customerPool.submit(manufacturer::sellCar);
        customerPool.submit(manufacturer::sellCar);

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
