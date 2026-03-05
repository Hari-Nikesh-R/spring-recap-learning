package org.example;


// Refer dsa_missing_numers.md in document for explanation
public class Main {
    public static void main(String[] args) {
        int[] arr = {0,5,3,2,1};
        int n = arr.length;
        int missingCase = 0;
        for (int j : arr) {
            missingCase += j;
        }
        System.out.println((n * (n+1))/2 - missingCase);
    }
}