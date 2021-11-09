import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static utils.Logger.log;

public class Main {

    public static void main(String[] args) {
        final Manufacturer manufacturer = new Manufacturer();
        Thread manufacturerThread = new Thread(null, manufacturer::makeCar, "Производитель");

        final ExecutorService customerPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        customerPool.submit(manufacturer::sellCar);
        customerPool.submit(manufacturer::sellCar);
        customerPool.submit(manufacturer::sellCar);

        manufacturerThread.start();

        customerPool.shutdown();

        boolean isStop = false;
        while (!isStop) {
            if (customerPool.isTerminated()) {
                log("Останавливаем производство...");
                manufacturerThread.interrupt();
                isStop = true;
            }
        }
    }
}
