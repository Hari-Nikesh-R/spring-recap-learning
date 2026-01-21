package org.example.javafullstacktraining;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}

/**
 * Interface - we create only method declaration, -> implementation?
 * Which ever the blueprint implements this interface will have the implemention.
 * class -> Extends -> class
 * class -> Implement -> interface.
 * interface -> Extends -> interface.
 * */

//import java.util.Vector;

/**
 * Abtract class
 * An abstract class is a partially implemented class.
 * You cannot create object for abstract class unless you create anonymous class object with it.
 *
 */
//
//interface Vehicle {
//    void move(int speed);
//    default void start() {
//        System.out.println("Vehicle started");
//    }
//
//    static void stop() {
//        System.out.println("Vehicle stopped");
//    }
//}
//
//interface Abs {
//     void automaticBrake();
//
//     default void start() {
//        System.out.println("Vehicle started");
//    }
//}
//
//
//public class Main {
//    public static void main(String[] args) {
//        // If an interface has only one method declaration then that can be
//        // used as lambda
//        // 1. "() -> {}" => No parameter.
//        // 2. "(number_of_parameter) -> {}" => With parameter
////        Vehicle vehicle = (s) -> {
////            System.out.println("Vehicle started");
////            System.out.println("Vehicle started at speed of" + s);
////        };
////        vehicle.start();
////        vehicle.move(12);
//
//        Vehicle.stop(); // static methods cannot be overridden
//        Vehicle v = new Car();
//        v.start();
//    }
//}
//
//class Car implements Vehicle, Abs {
//
//    @Override
//    public void automaticBrake() {
//
//    }
//
//    @Override
//    public void move(int speed) {
//
//    }
//
//    @Override
//    public void start() {
//        System.out.println("This is Car");
//    }
//
//    static void stop() {
//        System.out.println("This is Car");
//    }
//
//}
//
//class Boat implements Vehicle {
//
//    @Override
//    public void move(int speed) {
//
//    }
//}