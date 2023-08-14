package org.souvenirshop.dao;

public enum FileNames {

    MANUFACTURERS("manufacturers.ser"), SOUVENIRS("souvenirs.ser"), USERS("users.ser");

    private final String fileName;

    FileNames(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
