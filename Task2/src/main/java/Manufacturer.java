import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static utils.Constants.*;
import static utils.Logger.log;

class Manufacturer {

    Lock lock = new ReentrantLock(true);
    Condition condition = lock.newCondition();

    List<Car> car = new ArrayList<>(INITIAL_CAR_ARR_SIZE);

    List<Car> getCars() {
        return car;
    }

    public void makeCar() {
        try {
            while (true) {
                lock.lockInterruptibly();
                try {
                    log("Создаю машину");
                    Thread.sleep(CAR_MAKING_TIME);
                    this.getCars().add(new Car());
                    log("Создание машины завершено. Свободных машин: " + getCars().size());
                    condition.signal();
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException err) { // выходим, если получили сигнал прерывания
            log("я спал");
        } finally {
            log("Поток завершен");
        }
    }

    public void sellCar() {
        for (int i = 0; i <= PURCHASING_POWER; i++) {
            lock.lock();
            try {
                log("Дзынь-дзынь!!! Пришел покупатель!");
                while (getCars().size() == 0) {
                    log("Машин  нет!");
                    condition.await();
                }
                Thread.sleep(CAR_SALE_TIME);
                log("Покупатель уехал на новеньком авто");
                getCars().remove(0);
                condition.signalAll();
            } catch (InterruptedException e) {
                log("я спал");
            } finally {
                lock.unlock();
            }
        }
    }
}