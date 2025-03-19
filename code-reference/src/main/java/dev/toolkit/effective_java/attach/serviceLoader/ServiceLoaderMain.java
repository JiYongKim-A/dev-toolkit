package dev.toolkit.effective_java.attach.serviceLoader;

import java.util.ServiceLoader;

public class ServiceLoaderMain {
    public static void main(String[] args) {
        ServiceLoader<Car> Cars = ServiceLoader.load(Car.class);
        for (Car car : Cars) {
            car.horn();
        }
    }
}
