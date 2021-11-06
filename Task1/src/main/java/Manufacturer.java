import java.util.ArrayList;
import java.util.List;

import static utils.Constants.*;
import static utils.Logger.log;

class Manufacturer {

    List<Car> car = new ArrayList<>(INITIAL_CAR_ARR_SIZE);

    List<Car> getCars() {
        return car;
    }

    public synchronized void makeCar() throws InterruptedException {
        log("Создаю машину");
        Thread.sleep(CAR_MAKING_TIME);
        this.getCars().add(new Car());
        log("Создание машины завершено. Свободных машин: " + getCars().size());
        notify();
        wait();
    }

    public synchronized void sellCar() {
        for (int i = 0; i <= PURCHASING_POWER; i++) {
            try {
                log("Дзынь-дзынь!!! Пришел покупатель!");
                while (getCars().size() == 0) {
                    log("Машин  нет!");
                    wait();
                }
                Thread.sleep(CAR_SALE_TIME);
                log("Покупатель уехал на новеньком авто");
                getCars().remove(0);
                notifyAll();
            } catch (InterruptedException e) {
                log("я спал");
            }
        }
    }
}