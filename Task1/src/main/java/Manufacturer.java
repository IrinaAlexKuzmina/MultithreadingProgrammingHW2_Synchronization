import java.util.ArrayList;
import java.util.List;

import static utils.Constants.*;
import static utils.Logger.log;

class Manufacturer {

    List<Car> car = new ArrayList<>(INITIAL_CAR_ARR_SIZE);

    List<Car> getCars() {
        return car;
    }

    public synchronized void makingCar() throws InterruptedException {
        this.getCars().add(new Car());
        log("Создание машины завершено. Свободных машин: " + getCars().size());
        notify();
        wait();
    }

    public void makeCar() throws InterruptedException {
        log("Создаю машину");
        Thread.sleep(CAR_MAKING_TIME);
        makingCar();
    }

    public synchronized void checkCar() throws InterruptedException {
        while (getCars().size() == 0) {
            log("Машин  нет!");
            wait();
        }
    }

    public synchronized void buyCar() throws InterruptedException {
        log("Покупатель уехал на новеньком авто");
        getCars().remove(0);
        notifyAll();
    }

    public void sellCar() {
        for (int i = 0; i < PURCHASING_POWER; i++) {
            try {
                log("Дзынь-дзынь!!! Пришел покупатель!");
                checkCar();
                Thread.sleep(CAR_SALE_TIME);
                buyCar();
            } catch (InterruptedException e) {
                log("я спал");
            }
        }
    }
}