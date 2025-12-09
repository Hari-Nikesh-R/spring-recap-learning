package org.example.collectionsexample;

import java.util.ArrayList;
import java.util.List;

public class StreamApiExample {
    public static void main(String[] args) {
        // Created array list
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

        // 1. Need to filter even numbers
        // 2. Need to take a square of the even numbers.
        // 3. Sum all the squared value of even numbers.

        // 1 -> [2, 4, 6, 8]
        // 2 -> [4, 16, 36, 64]
        // 3 -> 120

//        int sum = 0;
//        for (int i = 0; i < numbers.size(); i++) {
//            if (numbers.get(i) % 2 == 0) {
//                int square = numbers.get(i) * numbers.get(i);
//                sum = sum + square;
//            }
//        }
//        System.out.println(sum);

        int result = numbers.stream().filter(n -> n % 2 == 0)
                .map(n -> n * n).reduce(0, Integer::sum);
        System.out.println(result);
    }
}
