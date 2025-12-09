package org.example.collectionsexample;

import java.util.*;

public class CollectionExample {
    public static void main(String[] args) {
        List<String> name = new ArrayList<>();
        name.add("Jack");
        name.add("Sam");
        name.add("Jack");
        name.add("Sam");
        System.out.println(name);

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "Admin");
        map.put(2, "Guest");
        System.out.println(map);

        Set<String> set = new HashSet<>();
        set.add("Jack");
        set.add("Sam");
        set.add("Jack");
        set.add("Sam");
        System.out.println(set);
    }
}
