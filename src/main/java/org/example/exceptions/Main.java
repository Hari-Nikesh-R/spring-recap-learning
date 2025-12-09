package org.example.exceptions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Checked exception -> IOException
        // Unchecked exception -> NullPointerException
        File file = new File("/Users/harinikeshr/Documents/2025/Projects/java-full-stack-training/data.txt");
        try (FileWriter fw = new FileWriter(file,  false)) {
            if (!file.exists()) {
               file.createNewFile();
           }

           fw.write("Hello Worldasdasd");
        } catch (ArithmeticException e) {
            System.out.println("Exception caught");
        } catch (NullPointerException e) {
            System.out.println("Exception caught due to null pointer");
        } catch (IOException e) {
            System.out.println("Exception caught due to io exception");
        }
    }
}
