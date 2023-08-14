package org.souvenirshop.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileOperation<T> {

    public void writeIntoFile(List<T> records, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(records);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> readFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return ((ArrayList<T>) ois.readObject());
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return new ArrayList<>();
    }
}