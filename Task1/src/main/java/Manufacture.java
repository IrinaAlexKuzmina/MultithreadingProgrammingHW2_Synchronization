import static utils.Logger.log;

public class Manufacture extends Thread {

    private final Manufacturer manufacturer;

    public Manufacture(Manufacturer manufacturer, String threadName) {
        this.manufacturer = manufacturer;
        this.setName(threadName);
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                manufacturer.makeCar();
            }
        } catch (InterruptedException err) {
            log("я спал");

        } finally {
            log("Поток завершен");
        }
    }
}
